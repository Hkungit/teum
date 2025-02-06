package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.community.entity.CommunityLike;

import java.util.List;

/**
 * @Description: 点赞记录
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
public interface ICommunityLikeService extends IService<CommunityLike> {

    /**
     * 点赞
     * @param userId 用户ID
     * @param type 点赞类型(1-帖子,2-评论)
     * @param targetId 点赞对象ID
     * @return 是否成功
     */
    boolean like(String userId, Integer type, String targetId);

    /**
     * 取消点赞
     * @param userId 用户ID
     * @param type 点赞类型(1-帖子,2-评论)
     * @param targetId 点赞对象ID
     * @return 是否成功
     */
    boolean unlike(String userId, Integer type, String targetId);

    /**
     * 获取用户点赞列表
     * @param page 分页参数
     * @param userId 用户ID
     * @param type 点赞类型
     * @return 分页结果
     */
    IPage<CommunityLike> getLikeList(Page<CommunityLike> page, String userId, Integer type);

    /**
     * 获取对象的点赞用户列表
     * @param page 分页参数
     * @param type 点赞类型
     * @param targetId 点赞对象ID
     * @return 分页结果
     */
    IPage<CommunityLike> getLikeUserList(Page<CommunityLike> page, Integer type, String targetId);

    /**
     * 检查是否已点赞
     * @param userId 用户ID
     * @param type 点赞类型
     * @param targetId 点赞对象ID
     * @return 是否已点赞
     */
    boolean checkIsLiked(String userId, Integer type, String targetId);

    /**
     * 获取点赞数量
     * @param type 点赞类型
     * @param targetId 点赞对象ID
     * @return 点赞数量
     */
    long getLikeCount(Integer type, String targetId);

    /**
     * 获取用户点赞数量
     * @param userId 用户ID
     * @param type 点赞类型
     * @return 点赞数量
     */
    long getUserLikeCount(String userId, Integer type);

    /**
     * 批量获取点赞状态
     * @param userId 用户ID
     * @param type 点赞类型
     * @param targetIds 点赞对象ID列表
     * @return 已点赞的对象ID列表
     */
    List<String> batchGetLikeStatus(String userId, Integer type, List<String> targetIds);
} 