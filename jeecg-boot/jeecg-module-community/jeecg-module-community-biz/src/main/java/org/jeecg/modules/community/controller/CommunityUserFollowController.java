package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.community.entity.CommunityUserFollow;
import org.jeecg.modules.community.service.ICommunityUserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 用户关注
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="用户关注")
@RestController
@RequestMapping("/community/userFollow")
public class CommunityUserFollowController extends JeecgController<CommunityUserFollow, ICommunityUserFollowService> {

    @Autowired
    private ICommunityUserFollowService communityUserFollowService;

    /**
     * 关注用户
     */
    @AutoLog(value = "用户关注-关注")
    @ApiOperation(value="用户关注-关注", notes="用户关注-关注")
    @PostMapping(value = "/follow")
    public Result<?> follow(@RequestParam(name="userId") String userId,
                          @RequestParam(name="followUserId") String followUserId) {
        if (communityUserFollowService.followUser(userId, followUserId)) {
            return Result.OK("关注成功！");
        }
        return Result.error("关注失败！");
    }

    /**
     * 取消关注
     */
    @AutoLog(value = "用户关注-取消关注")
    @ApiOperation(value="用户关注-取消关注", notes="用户关注-取消关注")
    @DeleteMapping(value = "/unfollow")
    public Result<?> unfollow(@RequestParam(name="userId") String userId,
                            @RequestParam(name="followUserId") String followUserId) {
        if (communityUserFollowService.unfollowUser(userId, followUserId)) {
            return Result.OK("取消关注成功！");
        }
        return Result.error("取消关注失败！");
    }

    /**
     * 获取关注列表
     */
    @AutoLog(value = "用户关注-获取关注列表")
    @ApiOperation(value="用户关注-获取关注列表", notes="用户关注-获取关注列表")
    @GetMapping(value = "/followList")
    public Result<?> getFollowList(
            @RequestParam(name="userId") String userId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityUserFollow> page = new Page<>(pageNo, pageSize);
        IPage<CommunityUserFollow> pageList = communityUserFollowService.getFollowList(page, userId);
        return Result.OK(pageList);
    }

    /**
     * 获取粉丝列表
     */
    @AutoLog(value = "用户关注-获取粉丝列表")
    @ApiOperation(value="用户关注-获取粉丝列表", notes="用户关注-获取粉丝列表")
    @GetMapping(value = "/fansList")
    public Result<?> getFansList(
            @RequestParam(name="userId") String userId,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityUserFollow> page = new Page<>(pageNo, pageSize);
        IPage<CommunityUserFollow> pageList = communityUserFollowService.getFansList(page, userId);
        return Result.OK(pageList);
    }

    /**
     * 检查是否已关注
     */
    @AutoLog(value = "用户关注-检查是否已关注")
    @ApiOperation(value="用户关注-检查是否已关注", notes="用户关注-检查是否已关注")
    @GetMapping(value = "/checkFollow")
    public Result<?> checkIsFollowed(@RequestParam(name="userId") String userId,
                                   @RequestParam(name="followUserId") String followUserId) {
        boolean isFollowed = communityUserFollowService.checkIsFollowed(userId, followUserId);
        return Result.OK(isFollowed);
    }

    /**
     * 获取关注数量
     */
    @AutoLog(value = "用户关注-获取关注数量")
    @ApiOperation(value="用户关注-获取关注数量", notes="用户关注-获取关注数量")
    @GetMapping(value = "/followCount")
    public Result<?> getFollowCount(@RequestParam(name="userId") String userId) {
        long count = communityUserFollowService.getFollowCount(userId);
        return Result.OK(count);
    }

    /**
     * 获取粉丝数量
     */
    @AutoLog(value = "用户关注-获取粉丝数量")
    @ApiOperation(value="用户关注-获取粉丝数量", notes="用户关注-获取粉丝数量")
    @GetMapping(value = "/fansCount")
    public Result<?> getFansCount(@RequestParam(name="userId") String userId) {
        long count = communityUserFollowService.getFansCount(userId);
        return Result.OK(count);
    }

    /**
     * 获取互相关注的用户列表
     */
    @AutoLog(value = "用户关注-获取互相关注的用户列表")
    @ApiOperation(value="用户关注-获取互相关注的用户列表", notes="用户关注-获取互相关注的用户列表")
    @GetMapping(value = "/mutualFollow")
    public Result<?> getMutualFollowUserIds(@RequestParam(name="userId") String userId) {
        List<String> userIds = communityUserFollowService.getMutualFollowUserIds(userId);
        return Result.OK(userIds);
    }
} 