package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.community.entity.CommunityComment;
import org.jeecg.modules.community.service.ICommunityCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 社区评论
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="社区评论")
@RestController
@RequestMapping("/community/comment")
public class CommunityCommentController extends JeecgController<CommunityComment, ICommunityCommentService> {

    @Autowired
    private ICommunityCommentService communityCommentService;

    /**
     * 发表评论
     */
    @AutoLog(value = "社区评论-发表")
    @ApiOperation(value="社区评论-发表", notes="社区评论-发表")
    @PostMapping(value = "/publish")
    public Result<?> publish(@RequestBody CommunityComment comment) {
        if (communityCommentService.publishComment(comment)) {
            return Result.OK("发表成功！");
        }
        return Result.error("发表失败！");
    }

    /**
     * 审核评论
     */
    @AutoLog(value = "社区评论-审核")
    @ApiOperation(value="社区评论-审核", notes="社区评论-审核")
    @PutMapping(value = "/audit")
    public Result<?> audit(@RequestParam(name="id") String id,
                          @RequestParam(name="status") Integer status) {
        if (communityCommentService.auditComment(id, status)) {
            return Result.OK("审核成功！");
        }
        return Result.error("审核失败！");
    }

    /**
     * 获取帖子评论列表
     */
    @AutoLog(value = "社区评论-获取帖子评论列表")
    @ApiOperation(value="社区评论-获取帖子评论列表", notes="社区评论-获取帖子评论列表")
    @GetMapping(value = "/list")
    public Result<?> getCommentList(
            @RequestParam(name="postId") String postId,
            @RequestParam(name="parentId", required=false) String parentId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityComment> page = new Page<>(pageNo, pageSize);
        IPage<CommunityComment> pageList = communityCommentService.getCommentList(page, postId, parentId);
        return Result.OK(pageList);
    }

    /**
     * 获取用户评论列表
     */
    @AutoLog(value = "社区评论-获取用户评论列表")
    @ApiOperation(value="社区评论-获取用户评论列表", notes="社区评论-获取用户评论列表")
    @GetMapping(value = "/user")
    public Result<?> getUserComments(
            @RequestParam(name="userId") String userId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityComment> page = new Page<>(pageNo, pageSize);
        IPage<CommunityComment> pageList = communityCommentService.getUserComments(page, userId);
        return Result.OK(pageList);
    }

    /**
     * 获取评论回复列表
     */
    @AutoLog(value = "社区评论-获取评论回复列表")
    @ApiOperation(value="社区评论-获取评论回复列表", notes="社区评论-获取评论回复列表")
    @GetMapping(value = "/reply")
    public Result<?> getReplyList(@RequestParam(name="commentId") String commentId) {
        List<CommunityComment> list = communityCommentService.getReplyList(commentId);
        return Result.OK(list);
    }

    /**
     * 获取热门评论
     */
    @AutoLog(value = "社区评论-获取热门评论")
    @ApiOperation(value="社区评论-获取热门评论", notes="社区评论-获取热门评论")
    @GetMapping(value = "/hot")
    public Result<?> getHotComments(
            @RequestParam(name="postId") String postId,
            @RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityComment> list = communityCommentService.getHotComments(postId, limit);
        return Result.OK(list);
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "社区评论-通过id删除")
    @ApiOperation(value="社区评论-通过id删除", notes="社区评论-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id") String id) {
        if (communityCommentService.logicDeleteById(id)) {
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }
} 