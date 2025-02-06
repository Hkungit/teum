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
import org.jeecg.modules.mall.entity.MallGoodsSku;
import org.jeecg.modules.mall.service.IMallGoodsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品SKU
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品SKU")
@RestController
@RequestMapping("/mall/sku")
public class MallGoodsSkuController extends JeecgController<MallGoodsSku, IMallGoodsSkuService> {
    @Autowired
    private IMallGoodsSkuService mallGoodsSkuService;

    /**
     * 分页列表查询
     *
     * @param mallGoodsSku
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "商品SKU-分页列表查询")
    @ApiOperation(value="商品SKU-分页列表查询", notes="商品SKU-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MallGoodsSku mallGoodsSku,
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<MallGoodsSku> queryWrapper = QueryGenerator.initQueryWrapper(mallGoodsSku, req.getParameterMap());
        Page<MallGoodsSku> page = new Page<MallGoodsSku>(pageNo, pageSize);
        IPage<MallGoodsSku> pageList = mallGoodsSkuService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 获取商品的SKU列表
     *
     * @param goodsId
     * @return
     */
    @AutoLog(value = "商品SKU-获取商品的SKU列表")
    @ApiOperation(value="商品SKU-获取商品的SKU列表", notes="商品SKU-获取商品的SKU列表")
    @GetMapping(value = "/listByGoodsId")
    public Result<?> listByGoodsId(@RequestParam(name="goodsId",required=true) String goodsId) {
        List<MallGoodsSku> list = mallGoodsSkuService.list(new QueryWrapper<MallGoodsSku>()
                .eq("goods_id", goodsId)
                .orderByAsc("create_time"));
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param mallGoodsSku
     * @return
     */
    @AutoLog(value = "商品SKU-添加")
    @ApiOperation(value="商品SKU-添加", notes="商品SKU-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MallGoodsSku mallGoodsSku) {
        mallGoodsSkuService.save(mallGoodsSku);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mallGoodsSku
     * @return
     */
    @AutoLog(value = "商品SKU-编辑")
    @ApiOperation(value="商品SKU-编辑", notes="商品SKU-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MallGoodsSku mallGoodsSku) {
        mallGoodsSkuService.updateById(mallGoodsSku);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品SKU-通过id删除")
    @ApiOperation(value="商品SKU-通过id删除", notes="商品SKU-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        mallGoodsSkuService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品SKU-批量删除")
    @ApiOperation(value="商品SKU-批量删除", notes="商品SKU-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.mallGoodsSkuService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品SKU-通过id查询")
    @ApiOperation(value="商品SKU-通过id查询", notes="商品SKU-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        MallGoodsSku mallGoodsSku = mallGoodsSkuService.getById(id);
        return Result.OK(mallGoodsSku);
    }
} 