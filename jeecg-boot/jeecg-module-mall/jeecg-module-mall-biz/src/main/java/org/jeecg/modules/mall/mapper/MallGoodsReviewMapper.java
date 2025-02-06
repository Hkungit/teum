package org.jeecg.modules.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.mall.entity.MallGoodsReview;

/**
 * @Description: 商品评价
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Mapper
public interface MallGoodsReviewMapper extends BaseMapper<MallGoodsReview> {
    
    /**
     * 获取商品平均评分
     * @param goodsId 商品ID
     * @return 平均评分
     */
    @Select("SELECT IFNULL(AVG(rating), 0) FROM mall_goods_review WHERE goods_id = #{goodsId} AND status = 1")
    double getAverageRating(@Param("goodsId") String goodsId);
} 