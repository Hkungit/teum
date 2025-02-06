package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.community.entity.CommunityLike;
import org.jeecg.modules.community.service.ICommunityLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 点赞记录
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="点赞记录")
@RestController
@RequestMapping("/community/like")
public class CommunityLikeController extends JeecgController<CommunityLike, ICommunityLikeService> {

    @Autowired
    private ICommunityLikeService communityLikeService;

    /**
     * 点赞
     */
    @AutoLog(value = "点赞记录-点赞")
    @ApiOperation(value="点赞记录-点赞", notes="点赞记录-点赞")
    @PostMapping(value = "/like")
    public Result<?> like(@RequestParam(name="userId") String userId,
                         @RequestParam(name="type") Integer type,
                         @RequestParam(name="targetId") String targetId) {
        if (communityLikeService.like(userId, type, targetId)) {
            return Result.OK("点赞成功！");
        }
        return Result.error("点赞失败！");
    }

    /**
     * 取消点赞
     */
    @AutoLog(value = "点赞记录-取消点赞")
    @ApiOperation(value="点赞记录-取消点赞", notes="点赞记录-取消点赞")
    @DeleteMapping(value = "/unlike")
    public Result<?> unlike(@RequestParam(name="userId") String userId,
                          @RequestParam(name="type") Integer type,
                          @RequestParam(name="targetId") String targetId) {
        if (communityLikeService.unlike(userId, type, targetId)) {
            return Result.OK("取消点赞成功！");
        }
        return Result.error("取消点赞失败！");
    }

    /**
     * 获取用户点赞列表
     */
    @AutoLog(value = "点赞记录-获取用户点赞列表")
    @ApiOperation(value="点赞记录-获取用户点赞列表", notes="点赞记录-获取用户点赞列表")
    @GetMapping(value = "/userLikes")
    public Result<?> getLikeList(
            @RequestParam(name="userId") String userId,
            @RequestParam(name="type", required=false) Integer type,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityLike> page = new Page<>(pageNo, pageSize);
        IPage<CommunityLike> pageList = communityLikeService.getLikeList(page, userId, type);
        return Result.OK(pageList);
    }

    /**
     * 获取点赞用户列表
     */
    @AutoLog(value = "点赞记录-获取点赞用户列表")
    @ApiOperation(value="点赞记录-获取点赞用户列表", notes="点赞记录-获取点赞用户列表")
    @GetMapping(value = "/likeUsers")
    public Result<?> getLikeUserList(
            @RequestParam(name="type") Integer type,
            @RequestParam(name="targetId") String targetId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityLike> page = new Page<>(pageNo, pageSize);
        IPage<CommunityLike> pageList = communityLikeService.getLikeUserList(page, type, targetId);
        return Result.OK(pageList);
    }

    /**
     * 检查是否已点赞
     */
    @AutoLog(value = "点赞记录-检查是否已点赞")
    @ApiOperation(value="点赞记录-检查是否已点赞", notes="点赞记录-检查是否已点赞")
    @GetMapping(value = "/checkLike")
    public Result<?> checkIsLiked(@RequestParam(name="userId") String userId,
                                 @RequestParam(name="type") Integer type,
                                 @RequestParam(name="targetId") String targetId) {
        boolean isLiked = communityLikeService.checkIsLiked(userId, type, targetId);
        return Result.OK(isLiked);
    }

    /**
     * 获取点赞数量
     */
    @AutoLog(value = "点赞记录-获取点赞数量")
    @ApiOperation(value="点赞记录-获取点赞数量", notes="点赞记录-获取点赞数量")
    @GetMapping(value = "/likeCount")
    public Result<?> getLikeCount(@RequestParam(name="type") Integer type,
                                 @RequestParam(name="targetId") String targetId) {
        long count = communityLikeService.getLikeCount(type, targetId);
        return Result.OK(count);
    }

    /**
     * 获取用户点赞数量
     */
    @AutoLog(value = "点赞记录-获取用户点赞数量")
    @ApiOperation(value="点赞记录-获取用户点赞数量", notes="点赞记录-获取用户点赞数量")
    @GetMapping(value = "/userLikeCount")
    public Result<?> getUserLikeCount(@RequestParam(name="userId") String userId,
                                    @RequestParam(name="type", required=false) Integer type) {
        long count = communityLikeService.getUserLikeCount(userId, type);
        return Result.OK(count);
    }

    /**
     * 批量获取点赞状态
     */
    @AutoLog(value = "点赞记录-批量获取点赞状态")
    @ApiOperation(value="点赞记录-批量获取点赞状态", notes="点赞记录-批量获取点赞状态")
    @PostMapping(value = "/batchCheckLike")
    public Result<?> batchGetLikeStatus(@RequestParam(name="userId") String userId,
                                      @RequestParam(name="type") Integer type,
                                      @RequestBody List<String> targetIds) {
        List<String> likedIds = communityLikeService.batchGetLikeStatus(userId, type, targetIds);
        return Result.OK(likedIds);
    }
} 