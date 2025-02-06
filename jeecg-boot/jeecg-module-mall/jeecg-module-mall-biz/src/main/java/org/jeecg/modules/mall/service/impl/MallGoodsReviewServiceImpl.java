package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.mall.entity.MallGoodsReview;
import org.jeecg.modules.mall.entity.MallGoods;
import org.jeecg.modules.mall.mapper.MallGoodsReviewMapper;
import org.jeecg.modules.mall.mapper.MallGoodsMapper;
import org.jeecg.modules.mall.service.IMallGoodsReviewService;
import org.jeecg.modules.mall.service.IMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 商品评价
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Service
public class MallGoodsReviewServiceImpl extends ServiceImpl<MallGoodsReviewMapper, MallGoodsReview> implements IMallGoodsReviewService {

    @Autowired
    private IMallGoodsService mallGoodsService;

    @Autowired
    private MallGoodsMapper mallGoodsMapper;

    @Override
    public IPage<MallGoodsReview> getReviewList(Page<MallGoodsReview> page, String goodsId, Integer rating, Integer status) {
        QueryWrapper<MallGoodsReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId)
                   .eq(rating != null, "rating", rating)
                   .eq(status != null, "status", status)
                   .eq("del_flag", 0)  // 只查询未删除的评价
                   .orderByDesc("create_time");
        return page(page, queryWrapper);
    }

    @Override
    public IPage<MallGoodsReview> getUserReviews(Page<MallGoodsReview> page, String userId) {
        QueryWrapper<MallGoodsReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("del_flag", 0)  // 只查询未删除的评价
                   .orderByDesc("create_time");
        return page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditReview(String id, Integer status) {
        if (status != 1 && status != 2) {
            return false;
        }
        
        MallGoodsReview review = getById(id);
        if (review == null) {
            return false;
        }
        
        // 更新评价状态
        review.setStatus(status);
        boolean result = updateById(review);
        
        // 如果审核通过，更新商品评分
        if (result && status == 1) {
            updateGoodsRating(review.getGoodsId());
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean replyReview(String id, String reply) {
        if (StringUtils.isBlank(reply)) {
            return false;
        }
        
        MallGoodsReview review = getById(id);
        if (review == null) {
            return false;
        }
        
        review.setReply(reply);
        return updateById(review);
    }

    @Override
    public List<Integer> getRatingStats(String goodsId) {
        List<Integer> stats = new ArrayList<>(5);
        for (int i = 1; i <= 5; i++) {
            QueryWrapper<MallGoodsReview> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("goods_id", goodsId)
                       .eq("rating", i)
                       .eq("status", 1)  // 只统计已通过的评价
                       .eq("del_flag", 0);  // 只统计未删除的评价
            long count = count(queryWrapper);
            stats.add((int) count);
        }
        return stats;
    }

    @Override
    public double getAverageRating(String goodsId) {
        return getBaseMapper().getAverageRating(goodsId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(MallGoodsReview entity) {
        // 检查商品是否存在
        MallGoods goods = mallGoodsMapper.selectById(entity.getGoodsId());
        if (goods == null) {
            return false;
        }
        
        // 检查是否已经评价过
        QueryWrapper<MallGoodsReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", entity.getGoodsId())
                   .eq("user_id", entity.getUserId())
                   .eq("order_id", entity.getOrderId());
        if (baseMapper.selectCount(queryWrapper) > 0) {
            return false;
        }
        
        // 设置初始状态
        entity.setStatus(0);  // 待审核
        entity.setDelFlag(0); // 未删除
        
        boolean result = super.save(entity);
        
        // 如果保存成功，更新商品评分
        if (result) {
            updateGoodsRating(entity.getGoodsId());
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteById(String id) {
        MallGoodsReview review = getById(id);
        if (review == null) {
            return false;
        }
        
        review.setDelFlag(1);
        boolean result = updateById(review);
        
        // 如果是已通过的评价，需要更新商品评分
        if (result && review.getStatus() == 1) {
            updateGoodsRating(review.getGoodsId());
        }
        
        return result;
    }

    private void updateGoodsRating(String goodsId) {
        // 获取商品平均评分
        double avgRating = baseMapper.getAverageRating(goodsId);
        
        // 更新商品评分
        MallGoods goods = new MallGoods();
        goods.setId(goodsId);
        goods.setRating(avgRating);
        mallGoodsMapper.updateById(goods);
    }
} 