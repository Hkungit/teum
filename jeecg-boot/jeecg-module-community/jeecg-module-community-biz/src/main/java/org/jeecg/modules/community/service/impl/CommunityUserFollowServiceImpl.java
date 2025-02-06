package org.jeecg.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.community.entity.CommunityUserFollow;
import org.jeecg.modules.community.mapper.CommunityUserFollowMapper;
import org.jeecg.modules.community.service.ICommunityUserFollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 用户关注服务实现类
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Service
@Slf4j
public class CommunityUserFollowServiceImpl extends ServiceImpl<CommunityUserFollowMapper, CommunityUserFollow> implements ICommunityUserFollowService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean followUser(String userId, String followUserId) {
        // 检查是否已关注
        if (checkIsFollowed(userId, followUserId)) {
            return false;
        }
        
        // 创建关注记录
        CommunityUserFollow follow = new CommunityUserFollow();
        follow.setUserId(userId);
        follow.setFollowUserId(followUserId);
        follow.setCreateTime(new Date());
        follow.setDelFlag(0);
        
        return save(follow);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unfollowUser(String userId, String followUserId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getUserId, userId)
                .eq(CommunityUserFollow::getFollowUserId, followUserId)
                .eq(CommunityUserFollow::getDelFlag, 0);
        
        return remove(queryWrapper);
    }

    @Override
    public IPage<CommunityUserFollow> getFollowList(Page<CommunityUserFollow> page, String userId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0)
                .orderByDesc(CommunityUserFollow::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public IPage<CommunityUserFollow> getFansList(Page<CommunityUserFollow> page, String userId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getFollowUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0)
                .orderByDesc(CommunityUserFollow::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public boolean checkIsFollowed(String userId, String followUserId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getUserId, userId)
                .eq(CommunityUserFollow::getFollowUserId, followUserId)
                .eq(CommunityUserFollow::getDelFlag, 0);
        
        return count(queryWrapper) > 0;
    }

    @Override
    public long getFollowCount(String userId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0);
        
        return count(queryWrapper);
    }

    @Override
    public long getFansCount(String userId) {
        LambdaQueryWrapper<CommunityUserFollow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityUserFollow::getFollowUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0);
        
        return count(queryWrapper);
    }

    @Override
    public List<String> getMutualFollowUserIds(String userId) {
        // 获取我关注的用户
        LambdaQueryWrapper<CommunityUserFollow> followQueryWrapper = new LambdaQueryWrapper<>();
        followQueryWrapper.eq(CommunityUserFollow::getUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0)
                .select(CommunityUserFollow::getFollowUserId);
        List<String> followUserIds = list(followQueryWrapper).stream()
                .map(CommunityUserFollow::getFollowUserId)
                .collect(Collectors.toList());
        
        if (followUserIds.isEmpty()) {
            return followUserIds;
        }
        
        // 获取互相关注的用户
        LambdaQueryWrapper<CommunityUserFollow> mutualQueryWrapper = new LambdaQueryWrapper<>();
        mutualQueryWrapper.eq(CommunityUserFollow::getFollowUserId, userId)
                .eq(CommunityUserFollow::getDelFlag, 0)
                .in(CommunityUserFollow::getUserId, followUserIds)
                .select(CommunityUserFollow::getUserId);
        
        return list(mutualQueryWrapper).stream()
                .map(CommunityUserFollow::getUserId)
                .collect(Collectors.toList());
    }
} 