package org.jeecg.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.community.entity.CommunityComment;
import org.jeecg.modules.community.mapper.CommunityCommentMapper;
import org.jeecg.modules.community.service.ICommunityCommentService;
import org.jeecg.modules.community.service.ICommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区评论服务实现类
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Service
@Slf4j
public class CommunityCommentServiceImpl extends ServiceImpl<CommunityCommentMapper, CommunityComment> implements ICommunityCommentService {

    @Autowired
    private ICommunityPostService postService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishComment(CommunityComment comment) {
        // 设置初始状态
        comment.setStatus(0); // 待审核
        comment.setLikeCount(0);
        comment.setCreateTime(new Date());
        comment.setDelFlag(0);
        
        // 保存评论
        boolean success = save(comment);
        
        // 更新帖子评论数
        if (success) {
            postService.updateCommentCount(comment.getPostId(), 1);
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditComment(String id, Integer status) {
        if (status != 1 && status != 2) {
            return false;
        }
        
        LambdaUpdateWrapper<CommunityComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityComment::getId, id)
                .eq(CommunityComment::getDelFlag, 0)
                .set(CommunityComment::getStatus, status)
                .set(CommunityComment::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLikeCount(String id, int increment) {
        LambdaUpdateWrapper<CommunityComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityComment::getId, id)
                .eq(CommunityComment::getDelFlag, 0)
                .setSql("like_count = like_count + " + increment);
        
        return update(updateWrapper);
    }

    @Override
    public IPage<CommunityComment> getCommentList(Page<CommunityComment> page, String postId, String parentId) {
        LambdaQueryWrapper<CommunityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityComment::getDelFlag, 0)
                .eq(CommunityComment::getPostId, postId)
                .eq(StringUtils.isNotBlank(parentId), CommunityComment::getParentId, parentId)
                .orderByDesc(CommunityComment::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public IPage<CommunityComment> getUserComments(Page<CommunityComment> page, String userId) {
        LambdaQueryWrapper<CommunityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityComment::getDelFlag, 0)
                .eq(CommunityComment::getUserId, userId)
                .orderByDesc(CommunityComment::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<CommunityComment> getReplyList(String commentId) {
        LambdaQueryWrapper<CommunityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityComment::getDelFlag, 0)
                .eq(CommunityComment::getParentId, commentId)
                .orderByAsc(CommunityComment::getCreateTime);
        
        return list(queryWrapper);
    }

    @Override
    public List<CommunityComment> getHotComments(String postId, Integer limit) {
        LambdaQueryWrapper<CommunityComment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityComment::getDelFlag, 0)
                .eq(CommunityComment::getPostId, postId)
                .eq(CommunityComment::getStatus, 1) // 已发布
                .orderByDesc(CommunityComment::getLikeCount)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteById(String id) {
        // 获取评论信息
        CommunityComment comment = getById(id);
        if (comment == null) {
            return false;
        }
        
        // 更新评论状态
        LambdaUpdateWrapper<CommunityComment> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityComment::getId, id)
                .set(CommunityComment::getDelFlag, 1)
                .set(CommunityComment::getUpdateTime, new Date());
        
        boolean success = update(updateWrapper);
        
        // 更新帖子评论数
        if (success) {
            postService.updateCommentCount(comment.getPostId(), -1);
        }
        
        return success;
    }
} 