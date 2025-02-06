package org.jeecg.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.modules.community.entity.CommunityTopic;
import org.jeecg.modules.community.mapper.CommunityTopicMapper;
import org.jeecg.modules.community.service.ICommunityTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区话题服务实现类
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Service
@Slf4j
public class CommunityTopicServiceImpl extends ServiceImpl<CommunityTopicMapper, CommunityTopic> implements ICommunityTopicService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createTopic(CommunityTopic topic) {
        // 设置初始状态
        topic.setStatus(1); // 启用
        topic.setPostCount(0);
        topic.setFollowCount(0);
        topic.setIsRecommend(0);
        topic.setCreateTime(new Date());
        topic.setDelFlag(0);
        
        return save(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(String id, Integer status) {
        if (status != 0 && status != 1) {
            return false;
        }
        
        LambdaUpdateWrapper<CommunityTopic> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityTopic::getId, id)
                .eq(CommunityTopic::getDelFlag, 0)
                .set(CommunityTopic::getStatus, status)
                .set(CommunityTopic::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRecommend(String id, boolean isRecommend) {
        LambdaUpdateWrapper<CommunityTopic> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityTopic::getId, id)
                .eq(CommunityTopic::getDelFlag, 0)
                .set(CommunityTopic::getIsRecommend, isRecommend ? 1 : 0)
                .set(CommunityTopic::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updatePostCount(String id, int increment) {
        LambdaUpdateWrapper<CommunityTopic> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityTopic::getId, id)
                .eq(CommunityTopic::getDelFlag, 0)
                .setSql("post_count = post_count + " + increment);
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateFollowCount(String id, int increment) {
        LambdaUpdateWrapper<CommunityTopic> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityTopic::getId, id)
                .eq(CommunityTopic::getDelFlag, 0)
                .setSql("follow_count = follow_count + " + increment);
        
        return update(updateWrapper);
    }

    @Override
    public IPage<CommunityTopic> getTopicList(Page<CommunityTopic> page, String keyword, Integer status) {
        LambdaQueryWrapper<CommunityTopic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityTopic::getDelFlag, 0)
                .like(StringUtils.isNotBlank(keyword), CommunityTopic::getName, keyword)
                .eq(status != null, CommunityTopic::getStatus, status)
                .orderByDesc(CommunityTopic::getSort)
                .orderByDesc(CommunityTopic::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<CommunityTopic> getRecommendTopics(Integer limit) {
        LambdaQueryWrapper<CommunityTopic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityTopic::getDelFlag, 0)
                .eq(CommunityTopic::getStatus, 1) // 启用
                .eq(CommunityTopic::getIsRecommend, 1) // 推荐
                .orderByDesc(CommunityTopic::getSort)
                .orderByDesc(CommunityTopic::getCreateTime)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    public List<CommunityTopic> getHotTopics(Integer limit) {
        LambdaQueryWrapper<CommunityTopic> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityTopic::getDelFlag, 0)
                .eq(CommunityTopic::getStatus, 1) // 启用
                .orderByDesc(CommunityTopic::getPostCount)
                .orderByDesc(CommunityTopic::getFollowCount)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteById(String id) {
        LambdaUpdateWrapper<CommunityTopic> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityTopic::getId, id)
                .set(CommunityTopic::getDelFlag, 1)
                .set(CommunityTopic::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }
} 