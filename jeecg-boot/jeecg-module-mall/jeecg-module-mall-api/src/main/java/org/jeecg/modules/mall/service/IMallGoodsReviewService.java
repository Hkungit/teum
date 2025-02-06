package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.MallGoodsReview;

import java.util.List;

/**
 * @Description: 商品评价
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
public interface IMallGoodsReviewService extends IService<MallGoodsReview> {
    
    /**
     * 获取商品评价列表
     * @param page 分页参数
     * @param goodsId 商品ID
     * @param rating 评分
     * @param status 状态
     * @return 分页结果
     */
    IPage<MallGoodsReview> getReviewList(Page<MallGoodsReview> page, String goodsId, Integer rating, Integer status);
    
    /**
     * 获取用户的评价列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<MallGoodsReview> getUserReviews(Page<MallGoodsReview> page, String userId);
    
    /**
     * 审核评价
     * @param id 评价ID
     * @param status 审核状态(1-通过,2-拒绝)
     * @return 是否成功
     */
    boolean auditReview(String id, Integer status);
    
    /**
     * 商家回复评价
     * @param id 评价ID
     * @param reply 回复内容
     * @return 是否成功
     */
    boolean replyReview(String id, String reply);
    
    /**
     * 获取商品评分统计
     * @param goodsId 商品ID
     * @return 各评分数量
     */
    List<Integer> getRatingStats(String goodsId);
    
    /**
     * 计算商品平均评分
     * @param goodsId 商品ID
     * @return 平均评分
     */
    double getAverageRating(String goodsId);

    /**
     * 逻辑删除评价
     * @param id 评价ID
     * @return 是否成功
     */
    boolean logicDeleteById(String id);
} 