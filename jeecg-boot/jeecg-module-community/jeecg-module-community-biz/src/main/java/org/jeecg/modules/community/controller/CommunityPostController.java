package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.community.entity.CommunityPost;
import org.jeecg.modules.community.service.ICommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 社区帖子
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="社区帖子")
@RestController
@RequestMapping("/community/post")
public class CommunityPostController extends JeecgController<CommunityPost, ICommunityPostService> {

    @Autowired
    private ICommunityPostService communityPostService;

    /**
     * 发布帖子
     */
    @AutoLog(value = "社区帖子-发布")
    @ApiOperation(value="社区帖子-发布", notes="社区帖子-发布")
    @PostMapping(value = "/publish")
    public Result<?> publish(@RequestBody CommunityPost post) {
        if (communityPostService.publishPost(post)) {
            return Result.OK("发布成功！");
        }
        return Result.error("发布失败！");
    }

    /**
     * 审核帖子
     */
    @AutoLog(value = "社区帖子-审核")
    @ApiOperation(value="社区帖子-审核", notes="社区帖子-审核")
    @PutMapping(value = "/audit")
    public Result<?> audit(@RequestParam(name="id") String id,
                          @RequestParam(name="status") Integer status) {
        if (communityPostService.auditPost(id, status)) {
            return Result.OK("审核成功！");
        }
        return Result.error("审核失败！");
    }

    /**
     * 置顶/取消置顶
     */
    @AutoLog(value = "社区帖子-置顶/取消置顶")
    @ApiOperation(value="社区帖子-置顶/取消置顶", notes="社区帖子-置顶/取消置顶")
    @PutMapping(value = "/toggleTop")
    public Result<?> toggleTop(@RequestParam(name="id") String id,
                             @RequestParam(name="isTop") Boolean isTop) {
        if (communityPostService.toggleTop(id, isTop)) {
            return Result.OK(isTop ? "置顶成功！" : "取消置顶成功！");
        }
        return Result.error(isTop ? "置顶失败！" : "取消置顶失败！");
    }

    /**
     * 加精/取消加精
     */
    @AutoLog(value = "社区帖子-加精/取消加精")
    @ApiOperation(value="社区帖子-加精/取消加精", notes="社区帖子-加精/取消加精")
    @PutMapping(value = "/toggleEssence")
    public Result<?> toggleEssence(@RequestParam(name="id") String id,
                                 @RequestParam(name="isEssence") Boolean isEssence) {
        if (communityPostService.toggleEssence(id, isEssence)) {
            return Result.OK(isEssence ? "加精成功！" : "取消加精成功！");
        }
        return Result.error(isEssence ? "加精失败！" : "取消加精失败！");
    }

    /**
     * 增加浏览次数
     */
    @AutoLog(value = "社区帖子-增加浏览次数")
    @ApiOperation(value="社区帖子-增加浏览次数", notes="社区帖子-增加浏览次数")
    @PutMapping(value = "/view")
    public Result<?> view(@RequestParam(name="id") String id) {
        if (communityPostService.increaseViewCount(id)) {
            return Result.OK("浏览次数更新成功！");
        }
        return Result.error("浏览次数更新失败！");
    }

    /**
     * 分页列表查询
     */
    @AutoLog(value = "社区帖子-分页列表查询")
    @ApiOperation(value="社区帖子-分页列表查询", notes="社区帖子-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(
            @RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="topicId", required=false) String topicId,
            @RequestParam(name="type", required=false) Integer type,
            @RequestParam(name="status", required=false) Integer status,
            @RequestParam(name="orderBy", required=false) String orderBy,
            @RequestParam(name="isAsc", required=false) Boolean isAsc,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityPost> page = new Page<>(pageNo, pageSize);
        IPage<CommunityPost> pageList = communityPostService.getPostList(page, keyword, topicId, type, status, orderBy, isAsc);
        return Result.OK(pageList);
    }

    /**
     * 获取用户帖子列表
     */
    @AutoLog(value = "社区帖子-获取用户帖子列表")
    @ApiOperation(value="社区帖子-获取用户帖子列表", notes="社区帖子-获取用户帖子列表")
    @GetMapping(value = "/user")
    public Result<?> getUserPosts(
            @RequestParam(name="userId") String userId,
            @RequestParam(name="type", required=false) Integer type,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityPost> page = new Page<>(pageNo, pageSize);
        IPage<CommunityPost> pageList = communityPostService.getUserPosts(page, userId, type);
        return Result.OK(pageList);
    }

    /**
     * 获取热门帖子
     */
    @AutoLog(value = "社区帖子-获取热门帖子")
    @ApiOperation(value="社区帖子-获取热门帖子", notes="社区帖子-获取热门帖子")
    @GetMapping(value = "/hot")
    public Result<?> getHotPosts(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityPost> list = communityPostService.getHotPosts(limit);
        return Result.OK(list);
    }

    /**
     * 获取推荐帖子
     */
    @AutoLog(value = "社区帖子-获取推荐帖子")
    @ApiOperation(value="社区帖子-获取推荐帖子", notes="社区帖子-获取推荐帖子")
    @GetMapping(value = "/recommend")
    public Result<?> getRecommendPosts(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityPost> list = communityPostService.getRecommendPosts(limit);
        return Result.OK(list);
    }

    /**
     * 获取置顶帖子
     */
    @AutoLog(value = "社区帖子-获取置顶帖子")
    @ApiOperation(value="社区帖子-获取置顶帖子", notes="社区帖子-获取置顶帖子")
    @GetMapping(value = "/top")
    public Result<?> getTopPosts(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityPost> list = communityPostService.getTopPosts(limit);
        return Result.OK(list);
    }

    /**
     * 获取精华帖子
     */
    @AutoLog(value = "社区帖子-获取精华帖子")
    @ApiOperation(value="社区帖子-获取精华帖子", notes="社区帖子-获取精华帖子")
    @GetMapping(value = "/essence")
    public Result<?> getEssencePosts(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityPost> list = communityPostService.getEssencePosts(limit);
        return Result.OK(list);
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "社区帖子-通过id删除")
    @ApiOperation(value="社区帖子-通过id删除", notes="社区帖子-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id") String id) {
        if (communityPostService.logicDeleteById(id)) {
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }
} 