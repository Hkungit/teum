package org.jeecg.modules.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.mall.entity.MallGoods;
import org.jeecg.modules.mall.service.IMallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品信息")
@RestController
@RequestMapping("/mall/goods")
public class MallGoodsController extends JeecgController<MallGoods, IMallGoodsService> {
    @Autowired
    private IMallGoodsService mallGoodsService;

    /**
     * 分页列表查询
     *
     * @param mallGoods
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "商品信息-分页列表查询")
    @ApiOperation(value="商品信息-分页列表查询", notes="商品信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MallGoods mallGoods,
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<MallGoods> queryWrapper = QueryGenerator.initQueryWrapper(mallGoods, req.getParameterMap());
        Page<MallGoods> page = new Page<MallGoods>(pageNo, pageSize);
        IPage<MallGoods> pageList = mallGoodsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param mallGoods
     * @return
     */
    @AutoLog(value = "商品信息-添加")
    @ApiOperation(value="商品信息-添加", notes="商品信息-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MallGoods mallGoods) {
        mallGoodsService.save(mallGoods);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mallGoods
     * @return
     */
    @AutoLog(value = "商品信息-编辑")
    @ApiOperation(value="商品信息-编辑", notes="商品信息-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MallGoods mallGoods) {
        mallGoodsService.updateById(mallGoods);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品信息-通过id删除")
    @ApiOperation(value="商品信息-通过id删除", notes="商品信息-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        mallGoodsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品信息-批量删除")
    @ApiOperation(value="商品信息-批量删除", notes="商品信息-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.mallGoodsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品信息-通过id查询")
    @ApiOperation(value="商品信息-通过id查询", notes="商品信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        MallGoods mallGoods = mallGoodsService.getById(id);
        return Result.OK(mallGoods);
    }

    @AutoLog(value = "商品信息-上架")
    @ApiOperation(value="商品信息-上架", notes="商品信息-上架")
    @PutMapping(value = "/putOnSale")
    public Result<?> putOnSale(@RequestParam(name="id",required=true) String id) {
        if (mallGoodsService.putOnSale(id)) {
            return Result.OK("商品上架成功!");
        }
        return Result.error("商品上架失败，请确保商品存在且有库存!");
    }

    @AutoLog(value = "商品信息-下架")
    @ApiOperation(value="商品信息-下架", notes="商品信息-下架")
    @PutMapping(value = "/putOffSale")
    public Result<?> putOffSale(@RequestParam(name="id",required=true) String id) {
        if (mallGoodsService.putOffSale(id)) {
            return Result.OK("商品下架成功!");
        }
        return Result.error("商品下架失败!");
    }

    @AutoLog(value = "商品信息-更新库存")
    @ApiOperation(value="商品信息-更新库存", notes="商品信息-更新库存")
    @PutMapping(value = "/updateStock")
    public Result<?> updateStock(@RequestParam(name="id",required=true) String id,
                               @RequestParam(name="stock",required=true) Integer stock) {
        if (mallGoodsService.updateStock(id, stock)) {
            return Result.OK("库存更新成功!");
        }
        return Result.error("库存更新失败!");
    }

    @AutoLog(value = "商品信息-增加库存")
    @ApiOperation(value="商品信息-增加库存", notes="商品信息-增加库存")
    @PutMapping(value = "/increaseStock")
    public Result<?> increaseStock(@RequestParam(name="id",required=true) String id,
                                 @RequestParam(name="amount",required=true) Integer amount) {
        if (mallGoodsService.increaseStock(id, amount)) {
            return Result.OK("库存增加成功!");
        }
        return Result.error("库存增加失败!");
    }

    @AutoLog(value = "商品信息-减少库存")
    @ApiOperation(value="商品信息-减少库存", notes="商品信息-减少库存")
    @PutMapping(value = "/decreaseStock")
    public Result<?> decreaseStock(@RequestParam(name="id",required=true) String id,
                                 @RequestParam(name="amount",required=true) Integer amount) {
        if (mallGoodsService.decreaseStock(id, amount)) {
            return Result.OK("库存减少成功!");
        }
        return Result.error("库存减少失败，请确保库存充足!");
    }

    @AutoLog(value = "商品信息-搜索")
    @ApiOperation(value="商品信息-搜索", notes="商品信息-搜索")
    @GetMapping(value = "/search")
    public Result<?> search(
            @RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="categoryId", required=false) String categoryId,
            @RequestParam(name="minPrice", required=false) BigDecimal minPrice,
            @RequestParam(name="maxPrice", required=false) BigDecimal maxPrice,
            @RequestParam(name="orderBy", required=false) String orderBy,
            @RequestParam(name="isAsc", required=false) Boolean isAsc,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<MallGoods> page = new Page<>(pageNo, pageSize);
        IPage<MallGoods> pageList = mallGoodsService.search(page, keyword, categoryId, minPrice, maxPrice, orderBy, isAsc);
        return Result.OK(pageList);
    }

    @AutoLog(value = "商品信息-按分类查询")
    @ApiOperation(value="商品信息-按分类查询", notes="商品信息-按分类查询")
    @GetMapping(value = "/listByCategory")
    public Result<?> listByCategory(
            @RequestParam(name="categoryId", required=true) String categoryId,
            @RequestParam(name="orderBy", required=false) String orderBy,
            @RequestParam(name="isAsc", required=false) Boolean isAsc,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<MallGoods> page = new Page<>(pageNo, pageSize);
        IPage<MallGoods> pageList = mallGoodsService.getByCategory(page, categoryId, orderBy, isAsc);
        return Result.OK(pageList);
    }

    @AutoLog(value = "商品信息-热门商品")
    @ApiOperation(value="商品信息-热门商品", notes="商品信息-热门商品")
    @GetMapping(value = "/hot")
    public Result<?> hotGoods(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<MallGoods> list = mallGoodsService.getHotGoods(limit);
        return Result.OK(list);
    }

    @AutoLog(value = "商品信息-新品")
    @ApiOperation(value="商品信息-新品", notes="商品信息-新品")
    @GetMapping(value = "/new")
    public Result<?> newGoods(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<MallGoods> list = mallGoodsService.getNewGoods(limit);
        return Result.OK(list);
    }

    @AutoLog(value = "商品信息-推荐商品")
    @ApiOperation(value="商品信息-推荐商品", notes="商品信息-推荐商品")
    @GetMapping(value = "/recommend")
    public Result<?> recommendGoods(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<MallGoods> list = mallGoodsService.getRecommendGoods(limit);
        return Result.OK(list);
    }
} 