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
import org.jeecg.modules.mall.entity.MallGoodsSpecs;
import org.jeecg.modules.mall.service.IMallGoodsSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品规格
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品规格")
@RestController
@RequestMapping("/mall/specs")
public class MallGoodsSpecsController extends JeecgController<MallGoodsSpecs, IMallGoodsSpecsService> {
    @Autowired
    private IMallGoodsSpecsService mallGoodsSpecsService;

    /**
     * 分页列表查询
     *
     * @param mallGoodsSpecs
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "商品规格-分页列表查询")
    @ApiOperation(value="商品规格-分页列表查询", notes="商品规格-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MallGoodsSpecs mallGoodsSpecs,
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<MallGoodsSpecs> queryWrapper = QueryGenerator.initQueryWrapper(mallGoodsSpecs, req.getParameterMap());
        Page<MallGoodsSpecs> page = new Page<MallGoodsSpecs>(pageNo, pageSize);
        IPage<MallGoodsSpecs> pageList = mallGoodsSpecsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 获取商品的规格列表
     *
     * @param goodsId
     * @return
     */
    @AutoLog(value = "商品规格-获取商品的规格列表")
    @ApiOperation(value="商品规格-获取商品的规格列表", notes="商品规格-获取商品的规格列表")
    @GetMapping(value = "/listByGoodsId")
    public Result<?> listByGoodsId(@RequestParam(name="goodsId",required=true) String goodsId) {
        List<MallGoodsSpecs> list = mallGoodsSpecsService.list(new QueryWrapper<MallGoodsSpecs>()
                .eq("goods_id", goodsId)
                .orderByAsc("sort"));
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param mallGoodsSpecs
     * @return
     */
    @AutoLog(value = "商品规格-添加")
    @ApiOperation(value="商品规格-添加", notes="商品规格-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MallGoodsSpecs mallGoodsSpecs) {
        mallGoodsSpecsService.save(mallGoodsSpecs);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mallGoodsSpecs
     * @return
     */
    @AutoLog(value = "商品规格-编辑")
    @ApiOperation(value="商品规格-编辑", notes="商品规格-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MallGoodsSpecs mallGoodsSpecs) {
        mallGoodsSpecsService.updateById(mallGoodsSpecs);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品规格-通过id删除")
    @ApiOperation(value="商品规格-通过id删除", notes="商品规格-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        mallGoodsSpecsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品规格-批量删除")
    @ApiOperation(value="商品规格-批量删除", notes="商品规格-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.mallGoodsSpecsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品规格-通过id查询")
    @ApiOperation(value="商品规格-通过id查询", notes="商品规格-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        MallGoodsSpecs mallGoodsSpecs = mallGoodsSpecsService.getById(id);
        return Result.OK(mallGoodsSpecs);
    }
} 