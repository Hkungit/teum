package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.community.entity.CommunityPost;

import java.util.List;

/**
 * @Description: 社区帖子
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
public interface ICommunityPostService extends IService<CommunityPost> {

    /**
     * 发布帖子
     * @param post 帖子信息
     * @return 是否成功
     */
    boolean publishPost(CommunityPost post);

    /**
     * 审核帖子
     * @param id 帖子ID
     * @param status 审核状态(1-通过,2-拒绝)
     * @return 是否成功
     */
    boolean auditPost(String id, Integer status);

    /**
     * 置顶/取消置顶帖子
     * @param id 帖子ID
     * @param isTop 是否置顶
     * @return 是否成功
     */
    boolean toggleTop(String id, boolean isTop);

    /**
     * 加精/取消加精帖子
     * @param id 帖子ID
     * @param isEssence 是否加精
     * @return 是否成功
     */
    boolean toggleEssence(String id, boolean isEssence);

    /**
     * 增加浏览次数
     * @param id 帖子ID
     * @return 是否成功
     */
    boolean increaseViewCount(String id);

    /**
     * 更新帖子点赞数
     * @param id 帖子ID
     * @param increment 增量(可为负数)
     * @return 是否成功
     */
    boolean updateLikeCount(String id, int increment);

    /**
     * 更新帖子评论数
     * @param id 帖子ID
     * @param increment 增量(可为负数)
     * @return 是否成功
     */
    boolean updateCommentCount(String id, int increment);

    /**
     * 获取帖子列表(支持多条件筛选)
     * @param page 分页参数
     * @param keyword 关键词
     * @param topicId 话题ID
     * @param type 帖子类型
     * @param status 帖子状态
     * @param orderBy 排序字段
     * @param isAsc 是否升序
     * @return 分页结果
     */
    IPage<CommunityPost> getPostList(Page<CommunityPost> page, String keyword, String topicId,
                                   Integer type, Integer status, String orderBy, Boolean isAsc);

    /**
     * 获取用户的帖子列表
     * @param page 分页参数
     * @param userId 用户ID
     * @param type 帖子类型
     * @return 分页结果
     */
    IPage<CommunityPost> getUserPosts(Page<CommunityPost> page, String userId, Integer type);

    /**
     * 获取热门帖子
     * @param limit 限制数量
     * @return 帖子列表
     */
    List<CommunityPost> getHotPosts(Integer limit);

    /**
     * 获取推荐帖子
     * @param limit 限制数量
     * @return 帖子列表
     */
    List<CommunityPost> getRecommendPosts(Integer limit);

    /**
     * 获取置顶帖子
     * @param limit 限制数量
     * @return 帖子列表
     */
    List<CommunityPost> getTopPosts(Integer limit);

    /**
     * 获取精华帖子
     * @param limit 限制数量
     * @return 帖子列表
     */
    List<CommunityPost> getEssencePosts(Integer limit);

    /**
     * 逻辑删除帖子
     * @param id 帖子ID
     * @return 是否成功
     */
    boolean logicDeleteById(String id);
} 