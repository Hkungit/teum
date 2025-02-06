package org.jeecg.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.community.entity.CommunityLike;
import org.jeecg.modules.community.mapper.CommunityLikeMapper;
import org.jeecg.modules.community.service.ICommunityCommentService;
import org.jeecg.modules.community.service.ICommunityLikeService;
import org.jeecg.modules.community.service.ICommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 点赞服务实现类
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Service
@Slf4j
public class CommunityLikeServiceImpl extends ServiceImpl<CommunityLikeMapper, CommunityLike> implements ICommunityLikeService {

    @Autowired
    private ICommunityPostService postService;

    @Autowired
    private ICommunityCommentService commentService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean like(String userId, Integer type, String targetId) {
        // 检查是否已点赞
        if (checkIsLiked(userId, type, targetId)) {
            return false;
        }
        
        // 创建点赞记录
        CommunityLike like = new CommunityLike();
        like.setUserId(userId);
        like.setType(type);
        like.setTargetId(targetId);
        like.setCreateTime(new Date());
        like.setDelFlag(0);
        
        boolean success = save(like);
        
        // 更新目标对象点赞数
        if (success) {
            if (type == 1) {
                postService.updateLikeCount(targetId, 1);
            } else if (type == 2) {
                commentService.updateLikeCount(targetId, 1);
            }
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unlike(String userId, Integer type, String targetId) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getUserId, userId)
                .eq(CommunityLike::getType, type)
                .eq(CommunityLike::getTargetId, targetId)
                .eq(CommunityLike::getDelFlag, 0);
        
        boolean success = remove(queryWrapper);
        
        // 更新目标对象点赞数
        if (success) {
            if (type == 1) {
                postService.updateLikeCount(targetId, -1);
            } else if (type == 2) {
                commentService.updateLikeCount(targetId, -1);
            }
        }
        
        return success;
    }

    @Override
    public IPage<CommunityLike> getLikeList(Page<CommunityLike> page, String userId, Integer type) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getUserId, userId)
                .eq(type != null, CommunityLike::getType, type)
                .eq(CommunityLike::getDelFlag, 0)
                .orderByDesc(CommunityLike::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public IPage<CommunityLike> getLikeUserList(Page<CommunityLike> page, Integer type, String targetId) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getType, type)
                .eq(CommunityLike::getTargetId, targetId)
                .eq(CommunityLike::getDelFlag, 0)
                .orderByDesc(CommunityLike::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public boolean checkIsLiked(String userId, Integer type, String targetId) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getUserId, userId)
                .eq(CommunityLike::getType, type)
                .eq(CommunityLike::getTargetId, targetId)
                .eq(CommunityLike::getDelFlag, 0);
        
        return count(queryWrapper) > 0;
    }

    @Override
    public long getLikeCount(Integer type, String targetId) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getType, type)
                .eq(CommunityLike::getTargetId, targetId)
                .eq(CommunityLike::getDelFlag, 0);
        
        return count(queryWrapper);
    }

    @Override
    public long getUserLikeCount(String userId, Integer type) {
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getUserId, userId)
                .eq(type != null, CommunityLike::getType, type)
                .eq(CommunityLike::getDelFlag, 0);
        
        return count(queryWrapper);
    }

    @Override
    public List<String> batchGetLikeStatus(String userId, Integer type, List<String> targetIds) {
        if (targetIds == null || targetIds.isEmpty()) {
            return targetIds;
        }
        
        LambdaQueryWrapper<CommunityLike> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityLike::getUserId, userId)
                .eq(CommunityLike::getType, type)
                .in(CommunityLike::getTargetId, targetIds)
                .eq(CommunityLike::getDelFlag, 0)
                .select(CommunityLike::getTargetId);
        
        return list(queryWrapper).stream()
                .map(CommunityLike::getTargetId)
                .collect(Collectors.toList());
    }
} 