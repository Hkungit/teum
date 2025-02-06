package org.jeecg.modules.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.mall.entity.MallGoods;
import org.jeecg.modules.mall.entity.MallGoodsReview;
import org.jeecg.modules.mall.mapper.MallGoodsMapper;
import org.jeecg.modules.mall.service.IMallGoodsService;
import org.jeecg.modules.mall.service.IMallGoodsReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.jeecg.modules.mall.mapper.MallGoodsReviewMapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Service
public class MallGoodsServiceImpl extends ServiceImpl<MallGoodsMapper, MallGoods> implements IMallGoodsService {

    @Autowired
    private MallGoodsReviewMapper mallGoodsReviewMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean putOnSale(String id) {
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("status", 1)  // 1表示上架状态
                .gt("stock", 0);   // 只有有库存的商品才能上架
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean putOffSale(String id) {
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("status", 0);  // 0表示下架状态
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStock(String id, Integer stock) {
        if (stock < 0) {
            return false;
        }
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("stock", stock);
        boolean result = update(updateWrapper);
        
        // 如果库存为0，自动下架商品
        if (result && stock == 0) {
            putOffSale(id);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseStock(String id, Integer amount) {
        if (amount <= 0) {
            return false;
        }
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .setSql("stock = stock + " + amount);
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean decreaseStock(String id, Integer amount) {
        if (amount <= 0) {
            return false;
        }
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .ge("stock", amount)  // 确保库存足够
                .setSql("stock = stock - " + amount);
        boolean result = update(updateWrapper);
        
        // 检查是否需要自动下架
        MallGoods goods = getById(id);
        if (result && goods != null && goods.getStock() == 0) {
            putOffSale(id);
        }
        return result;
    }

    @Override
    public IPage<MallGoods> search(Page<MallGoods> page, String keyword, String categoryId,
                                  BigDecimal minPrice, BigDecimal maxPrice, String orderBy, Boolean isAsc) {
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        
        // 只查询上架商品
        queryWrapper.eq("status", 1);
        
        // 关键词搜索（商品名称、商品编码、商品描述）
        if (StringUtils.isNotBlank(keyword)) {
            queryWrapper.and(wrapper -> wrapper
                    .like("name", keyword)
                    .or()
                    .like("code", keyword)
                    .or()
                    .like("description", keyword));
        }
        
        // 分类查询
        if (StringUtils.isNotBlank(categoryId)) {
            queryWrapper.eq("category_id", categoryId);
        }
        
        // 价格区间
        if (minPrice != null) {
            queryWrapper.ge("price", minPrice);
        }
        if (maxPrice != null) {
            queryWrapper.le("price", maxPrice);
        }
        
        // 排序
        if (StringUtils.isNotBlank(orderBy)) {
            queryWrapper.orderBy(true, isAsc != null && isAsc, orderBy);
        } else {
            // 默认按创建时间倒序
            queryWrapper.orderByDesc("create_time");
        }
        
        return page(page, queryWrapper);
    }

    @Override
    public IPage<MallGoods> getByCategory(Page<MallGoods> page, String categoryId, String orderBy, Boolean isAsc) {
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1)
                   .eq("category_id", categoryId);
        
        // 排序
        if (StringUtils.isNotBlank(orderBy)) {
            queryWrapper.orderBy(true, isAsc != null && isAsc, orderBy);
        } else {
            queryWrapper.orderByDesc("create_time");
        }
        
        return page(page, queryWrapper);
    }

    @Override
    public List<MallGoods> getHotGoods(Integer limit) {
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1)
                   .orderByDesc("sales")
                   .last("limit " + limit);
        return list(queryWrapper);
    }

    @Override
    public List<MallGoods> getNewGoods(Integer limit) {
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1)
                   .orderByDesc("create_time")
                   .last("limit " + limit);
        return list(queryWrapper);
    }

    @Override
    public List<MallGoods> getRecommendGoods(Integer limit) {
        // 这里可以根据具体的推荐算法来实现
        // 示例实现：按销量和好评率排序
        QueryWrapper<MallGoods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1)
                   .orderByDesc("sales")
                   .last("limit " + limit);
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateGoodsRating(String goodsId) {
        // 直接使用 ReviewMapper 获取平均评分
        double avgRating = mallGoodsReviewMapper.getAverageRating(goodsId);
        
        // 更新商品评分
        UpdateWrapper<MallGoods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", goodsId)
                    .set("rating", avgRating);
        return update(updateWrapper);
    }

    @Override
    public boolean canReview(String goodsId, String userId, String orderId) {
        // 检查商品是否存在
        MallGoods goods = getById(goodsId);
        if (goods == null) {
            return false;
        }
        
        // 直接使用 ReviewMapper 检查是否已评价
        QueryWrapper<MallGoodsReview> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_id", goodsId)
                   .eq("user_id", userId)
                   .eq("order_id", orderId);
        return mallGoodsReviewMapper.selectCount(queryWrapper) == 0;
    }
} 