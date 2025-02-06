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
import org.jeecg.modules.mall.entity.MallCategory;
import org.jeecg.modules.mall.service.IMallCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 商品分类
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品分类")
@RestController
@RequestMapping("/mall/category")
public class MallCategoryController extends JeecgController<MallCategory, IMallCategoryService> {
    @Autowired
    private IMallCategoryService mallCategoryService;

    /**
     * 分页列表查询
     *
     * @param mallCategory
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "商品分类-分页列表查询")
    @ApiOperation(value="商品分类-分页列表查询", notes="商品分类-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(MallCategory mallCategory,
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<MallCategory> queryWrapper = QueryGenerator.initQueryWrapper(mallCategory, req.getParameterMap());
        Page<MallCategory> page = new Page<MallCategory>(pageNo, pageSize);
        IPage<MallCategory> pageList = mallCategoryService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 获取全部分类列表
     *
     * @return
     */
    @AutoLog(value = "商品分类-获取全部分类列表")
    @ApiOperation(value="商品分类-获取全部分类列表", notes="商品分类-获取全部分类列表")
    @GetMapping(value = "/listAll")
    public Result<?> listAll() {
        List<MallCategory> list = mallCategoryService.list(new QueryWrapper<MallCategory>().orderByAsc("sort"));
        return Result.OK(list);
    }

    /**
     * 添加
     *
     * @param mallCategory
     * @return
     */
    @AutoLog(value = "商品分类-添加")
    @ApiOperation(value="商品分类-添加", notes="商品分类-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody MallCategory mallCategory) {
        mallCategoryService.save(mallCategory);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param mallCategory
     * @return
     */
    @AutoLog(value = "商品分类-编辑")
    @ApiOperation(value="商品分类-编辑", notes="商品分类-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody MallCategory mallCategory) {
        mallCategoryService.updateById(mallCategory);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品分类-通过id删除")
    @ApiOperation(value="商品分类-通过id删除", notes="商品分类-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id",required=true) String id) {
        // 检查是否有子分类
        long count = mallCategoryService.count(new QueryWrapper<MallCategory>().eq("parent_id", id));
        if (count > 0) {
            return Result.error("该分类下有子分类，不能删除!");
        }
        mallCategoryService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "商品分类-批量删除")
    @ApiOperation(value="商品分类-批量删除", notes="商品分类-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
        this.mallCategoryService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "商品分类-通过id查询")
    @ApiOperation(value="商品分类-通过id查询", notes="商品分类-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
        MallCategory mallCategory = mallCategoryService.getById(id);
        return Result.OK(mallCategory);
    }
} 