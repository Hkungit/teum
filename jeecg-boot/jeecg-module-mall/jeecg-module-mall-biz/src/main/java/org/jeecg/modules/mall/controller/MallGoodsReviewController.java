package org.jeecg.modules.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.mall.entity.MallGoods;
import org.jeecg.modules.mall.entity.MallGoodsReview;
import org.jeecg.modules.mall.service.IMallGoodsReviewService;
import org.jeecg.modules.mall.service.IMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品评价
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品评价")
@RestController
@RequestMapping("/mall/review")
public class MallGoodsReviewController extends JeecgController<MallGoodsReview, IMallGoodsReviewService> {
    @Autowired
    private IMallGoodsReviewService mallGoodsReviewService;
    
    @Autowired
    private IMallGoodsService mallGoodsService;

    /**
     * 获取商品评价列表
     */
    @AutoLog(value = "商品评价-获取商品评价列表")
    @ApiOperation(value="商品评价-获取商品评价列表", notes="商品评价-获取商品评价列表")
    @GetMapping(value = "/list")
    public Result<?> getReviewList(
            @RequestParam(name="goodsId",required=true) String goodsId,
            @RequestParam(name="rating",required=false) Integer rating,
            @RequestParam(name="status",required=false) Integer status,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        // 验证商品是否存在
        MallGoods goods = mallGoodsService.getById(goodsId);
        if (goods == null) {
            return Result.error("商品不存在!");
        }
        
        Page<MallGoodsReview> page = new Page<>(pageNo, pageSize);
        IPage<MallGoodsReview> pageList = mallGoodsReviewService.getReviewList(page, goodsId, rating, status);
        return Result.OK(pageList);
    }

    /**
     * 获取用户评价列表
     */
    @AutoLog(value = "商品评价-获取用户评价列表")
    @ApiOperation(value="商品评价-获取用户评价列表", notes="商品评价-获取用户评价列表")
    @GetMapping(value = "/user")
    public Result<?> getUserReviews(
            @RequestParam(name="userId",required=true) String userId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<MallGoodsReview> page = new Page<>(pageNo, pageSize);
        IPage<MallGoodsReview> pageList = mallGoodsReviewService.getUserReviews(page, userId);
        return Result.OK(pageList);
    }

    /**
     * 添加评价
     */
    @AutoLog(value = "商品评价-添加")
    @ApiOperation(value="商品评价-添加", notes="商品评价-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MallGoodsReview mallGoodsReview) {
        // 验证必填字段
        if (StringUtils.isAnyBlank(mallGoodsReview.getGoodsId(), 
                                 mallGoodsReview.getOrderId(),
                                 mallGoodsReview.getUserId(),
                                 mallGoodsReview.getContent()) ||
            mallGoodsReview.getRating() == null ||
            mallGoodsReview.getRating() < 1 ||
            mallGoodsReview.getRating() > 5) {
            return Result.error("参数错误!");
        }
        
        // 检查是否可以评价
        if (!mallGoodsService.canReview(mallGoodsReview.getGoodsId(), 
                                      mallGoodsReview.getUserId(),
                                      mallGoodsReview.getOrderId())) {
            return Result.error("您已经评价过该商品或无权评价!");
        }
        
        if (mallGoodsReviewService.save(mallGoodsReview)) {
            return Result.OK("评价成功，等待审核!");
        }
        return Result.error("评价失败!");
    }

    /**
     * 编辑评价
     */
    @AutoLog(value = "商品评价-编辑")
    @ApiOperation(value="商品评价-编辑", notes="商品评价-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MallGoodsReview mallGoodsReview) {
        // 只允许修改未审核的评价
        MallGoodsReview existingReview = mallGoodsReviewService.getById(mallGoodsReview.getId());
        if (existingReview == null || existingReview.getStatus() != 0) {
            return Result.error("只能修改待审核的评价!");
        }
        
        mallGoodsReviewService.updateById(mallGoodsReview);
        return Result.OK("编辑成功!");
    }

    /**
     * 删除评价
     */
    @AutoLog(value = "商品评价-通过id删除")
    @ApiOperation(value="商品评价-通过id删除", notes="商品评价-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        if (mallGoodsReviewService.logicDeleteById(id)) {
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }

    /**
     * 批量删除评价
     */
    @AutoLog(value = "商品评价-批量删除")
    @ApiOperation(value="商品评价-批量删除", notes="商品评价-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        boolean success = true;
        for (String id : ids.split(",")) {
            if (!mallGoodsReviewService.logicDeleteById(id)) {
                success = false;
            }
        }
        return success ? Result.OK("批量删除成功！") : Result.error("部分删除失败！");
    }

    /**
     * 审核评价
     */
    @AutoLog(value = "商品评价-审核")
    @ApiOperation(value="商品评价-审核", notes="商品评价-审核")
    @PutMapping(value = "/audit")
    public Result<?> audit(
            @RequestParam(name="id",required=true) String id,
            @RequestParam(name="status",required=true) Integer status) {
        if (status != 1 && status != 2) {
            return Result.error("无效的审核状态!");
        }
        
        if (mallGoodsReviewService.auditReview(id, status)) {
            return Result.OK(status == 1 ? "审核通过!" : "审核拒绝!");
        }
        return Result.error("审核失败!");
    }

    /**
     * 商家回复评价
     */
    @AutoLog(value = "商品评价-商家回复")
    @ApiOperation(value="商品评价-商家回复", notes="商品评价-商家回复")
    @PutMapping(value = "/reply")
    public Result<?> reply(
            @RequestParam(name="id",required=true) String id,
            @RequestParam(name="reply",required=true) String reply) {
        if (StringUtils.isBlank(reply)) {
            return Result.error("回复内容不能为空!");
        }
        
        if (mallGoodsReviewService.replyReview(id, reply)) {
            return Result.OK("回复成功!");
        }
        return Result.error("回复失败!");
    }

    /**
     * 获取商品评分统计
     */
    @AutoLog(value = "商品评价-获取商品评分统计")
    @ApiOperation(value="商品评价-获取商品评分统计", notes="商品评价-获取商品评分统计")
    @GetMapping(value = "/stats")
    public Result<?> getRatingStats(@RequestParam(name="goodsId",required=true) String goodsId) {
        // 验证商品是否存在
        MallGoods goods = mallGoodsService.getById(goodsId);
        if (goods == null) {
            return Result.error("商品不存在!");
        }
        
        List<Integer> stats = mallGoodsReviewService.getRatingStats(goodsId);
        return Result.OK(stats);
    }

    /**
     * 获取商品平均评分
     */
    @AutoLog(value = "商品评价-获取商品平均评分")
    @ApiOperation(value="商品评价-获取商品平均评分", notes="商品评价-获取商品平均评分")
    @GetMapping(value = "/average")
    public Result<?> getAverageRating(@RequestParam(name="goodsId",required=true) String goodsId) {
        // 验证商品是否存在
        MallGoods goods = mallGoodsService.getById(goodsId);
        if (goods == null) {
            return Result.error("商品不存在!");
        }
        
        double avgRating = mallGoodsReviewService.getAverageRating(goodsId);
        return Result.OK(avgRating);
    }
} 