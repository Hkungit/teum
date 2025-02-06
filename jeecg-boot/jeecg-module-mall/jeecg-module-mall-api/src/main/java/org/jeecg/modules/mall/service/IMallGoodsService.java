package org.jeecg.modules.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.mall.entity.MallGoods;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
public interface IMallGoodsService extends IService<MallGoods> {
    
    /**
     * 商品上架
     * @param id 商品ID
     * @return 是否成功
     */
    boolean putOnSale(String id);
    
    /**
     * 商品下架
     * @param id 商品ID
     * @return 是否成功
     */
    boolean putOffSale(String id);
    
    /**
     * 更新商品库存
     * @param id 商品ID
     * @param stock 新的库存数量
     * @return 是否成功
     */
    boolean updateStock(String id, Integer stock);
    
    /**
     * 增加商品库存
     * @param id 商品ID
     * @param amount 增加的数量
     * @return 是否成功
     */
    boolean increaseStock(String id, Integer amount);
    
    /**
     * 减少商品库存
     * @param id 商品ID
     * @param amount 减少的数量
     * @return 是否成功
     */
    boolean decreaseStock(String id, Integer amount);

    /**
     * 搜索商品（多条件）
     * @param page 分页参数
     * @param keyword 关键词
     * @param categoryId 分类ID
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param orderBy 排序字段
     * @param isAsc 是否升序
     * @return 分页结果
     */
    IPage<MallGoods> search(Page<MallGoods> page, String keyword, String categoryId,
                           BigDecimal minPrice, BigDecimal maxPrice, String orderBy, Boolean isAsc);

    /**
     * 按分类ID查询商品
     * @param page 分页参数
     * @param categoryId 分类ID
     * @param orderBy 排序字段
     * @param isAsc 是否升序
     * @return 分页结果
     */
    IPage<MallGoods> getByCategory(Page<MallGoods> page, String categoryId, String orderBy, Boolean isAsc);

    /**
     * 获取热门商品
     * @param limit 限制数量
     * @return 商品列表
     */
    List<MallGoods> getHotGoods(Integer limit);

    /**
     * 获取新品
     * @param limit 限制数量
     * @return 商品列表
     */
    List<MallGoods> getNewGoods(Integer limit);

    /**
     * 获取推荐商品
     * @param limit 限制数量
     * @return 商品列表
     */
    List<MallGoods> getRecommendGoods(Integer limit);

    /**
     * 更新商品评分信息
     * @param goodsId 商品ID
     * @return 是否成功
     */
    boolean updateGoodsRating(String goodsId);

    /**
     * 检查用户是否可以评价商品
     * @param goodsId 商品ID
     * @param userId 用户ID
     * @param orderId 订单ID
     * @return 是否可以评价
     */
    boolean canReview(String goodsId, String userId, String orderId);
} 