package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.community.entity.CommunityUserFollow;

import java.util.List;

/**
 * @Description: 用户关注
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
public interface ICommunityUserFollowService extends IService<CommunityUserFollow> {

    /**
     * 关注用户
     * @param userId 用户ID
     * @param followUserId 被关注的用户ID
     * @return 是否成功
     */
    boolean followUser(String userId, String followUserId);

    /**
     * 取消关注
     * @param userId 用户ID
     * @param followUserId 被关注的用户ID
     * @return 是否成功
     */
    boolean unfollowUser(String userId, String followUserId);

    /**
     * 获取用户关注列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<CommunityUserFollow> getFollowList(Page<CommunityUserFollow> page, String userId);

    /**
     * 获取用户粉丝列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<CommunityUserFollow> getFansList(Page<CommunityUserFollow> page, String userId);

    /**
     * 检查是否已关注
     * @param userId 用户ID
     * @param followUserId 被关注的用户ID
     * @return 是否已关注
     */
    boolean checkIsFollowed(String userId, String followUserId);

    /**
     * 获取用户关注数量
     * @param userId 用户ID
     * @return 关注数量
     */
    long getFollowCount(String userId);

    /**
     * 获取用户粉丝数量
     * @param userId 用户ID
     * @return 粉丝数量
     */
    long getFansCount(String userId);

    /**
     * 获取互相关注的用户列表
     * @param userId 用户ID
     * @return 用户ID列表
     */
    List<String> getMutualFollowUserIds(String userId);
} 