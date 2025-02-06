package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.community.entity.CommunityComment;

import java.util.List;

/**
 * @Description: 社区评论
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
public interface ICommunityCommentService extends IService<CommunityComment> {

    /**
     * 发表评论
     * @param comment 评论信息
     * @return 是否成功
     */
    boolean publishComment(CommunityComment comment);

    /**
     * 审核评论
     * @param id 评论ID
     * @param status 审核状态(1-通过,2-拒绝)
     * @return 是否成功
     */
    boolean auditComment(String id, Integer status);

    /**
     * 更新评论点赞数
     * @param id 评论ID
     * @param increment 增量(可为负数)
     * @return 是否成功
     */
    boolean updateLikeCount(String id, int increment);

    /**
     * 获取帖子的评论列表
     * @param page 分页参数
     * @param postId 帖子ID
     * @param parentId 父评论ID
     * @return 分页结果
     */
    IPage<CommunityComment> getCommentList(Page<CommunityComment> page, String postId, String parentId);

    /**
     * 获取用户的评论列表
     * @param page 分页参数
     * @param userId 用户ID
     * @return 分页结果
     */
    IPage<CommunityComment> getUserComments(Page<CommunityComment> page, String userId);

    /**
     * 获取评论的回复列表
     * @param commentId 评论ID
     * @return 回复列表
     */
    List<CommunityComment> getReplyList(String commentId);

    /**
     * 获取热门评论
     * @param postId 帖子ID
     * @param limit 限制数量
     * @return 评论列表
     */
    List<CommunityComment> getHotComments(String postId, Integer limit);

    /**
     * 逻辑删除评论
     * @param id 评论ID
     * @return 是否成功
     */
    boolean logicDeleteById(String id);
} 