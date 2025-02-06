package org.jeecg.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.community.entity.CommunityPost;
import org.jeecg.modules.community.mapper.CommunityPostMapper;
import org.jeecg.modules.community.service.ICommunityPostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description: 社区帖子服务实现类
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Service
@Slf4j
public class CommunityPostServiceImpl extends ServiceImpl<CommunityPostMapper, CommunityPost> implements ICommunityPostService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean publishPost(CommunityPost post) {
        // 设置初始状态
        post.setStatus(0); // 待审核
        post.setLikeCount(0);
        post.setCommentCount(0);
        post.setViewCount(0);
        post.setIsTop(0);
        post.setIsEssence(0);
        post.setCreateTime(new Date());
        post.setDelFlag(0);
        
        // 保存帖子
        return save(post);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditPost(String id, Integer status) {
        if (status != 1 && status != 2) {
            return false;
        }
        
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .set(CommunityPost::getStatus, status)
                .set(CommunityPost::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleTop(String id, boolean isTop) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .set(CommunityPost::getIsTop, isTop ? 1 : 0)
                .set(CommunityPost::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean toggleEssence(String id, boolean isEssence) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .set(CommunityPost::getIsEssence, isEssence ? 1 : 0)
                .set(CommunityPost::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean increaseViewCount(String id) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .setSql("view_count = view_count + 1");
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLikeCount(String id, int increment) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .setSql("like_count = like_count + " + increment);
        
        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateCommentCount(String id, int increment) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .eq(CommunityPost::getDelFlag, 0)
                .setSql("comment_count = comment_count + " + increment);
        
        return update(updateWrapper);
    }

    @Override
    public IPage<CommunityPost> getPostList(Page<CommunityPost> page, String keyword, String topicId,
                                          Integer type, Integer status, String orderBy, Boolean isAsc) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        
        // 构建查询条件
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .like(StringUtils.isNotBlank(keyword), CommunityPost::getTitle, keyword)
                .eq(StringUtils.isNotBlank(topicId), CommunityPost::getTopicId, topicId)
                .eq(type != null, CommunityPost::getType, type)
                .eq(status != null, CommunityPost::getStatus, status);
        
        // 排序
        if (StringUtils.isNotBlank(orderBy)) {
            switch (orderBy) {
                case "createTime":
                    queryWrapper.orderBy(true, isAsc, CommunityPost::getCreateTime);
                    break;
                case "likeCount":
                    queryWrapper.orderBy(true, isAsc, CommunityPost::getLikeCount);
                    break;
                case "commentCount":
                    queryWrapper.orderBy(true, isAsc, CommunityPost::getCommentCount);
                    break;
                case "viewCount":
                    queryWrapper.orderBy(true, isAsc, CommunityPost::getViewCount);
                    break;
                default:
                    queryWrapper.orderByDesc(CommunityPost::getIsTop)
                            .orderByDesc(CommunityPost::getCreateTime);
            }
        } else {
            // 默认排序：置顶优先，然后按创建时间倒序
            queryWrapper.orderByDesc(CommunityPost::getIsTop)
                    .orderByDesc(CommunityPost::getCreateTime);
        }
        
        return page(page, queryWrapper);
    }

    @Override
    public IPage<CommunityPost> getUserPosts(Page<CommunityPost> page, String userId, Integer type) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .eq(CommunityPost::getAuthorId, userId)
                .eq(type != null, CommunityPost::getType, type)
                .orderByDesc(CommunityPost::getCreateTime);
        
        return page(page, queryWrapper);
    }

    @Override
    public List<CommunityPost> getHotPosts(Integer limit) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .eq(CommunityPost::getStatus, 1) // 已发布
                .orderByDesc(CommunityPost::getViewCount)
                .orderByDesc(CommunityPost::getLikeCount)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    public List<CommunityPost> getRecommendPosts(Integer limit) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .eq(CommunityPost::getStatus, 1) // 已发布
                .eq(CommunityPost::getIsEssence, 1) // 精华帖
                .orderByDesc(CommunityPost::getCreateTime)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    public List<CommunityPost> getTopPosts(Integer limit) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .eq(CommunityPost::getStatus, 1) // 已发布
                .eq(CommunityPost::getIsTop, 1) // 置顶
                .orderByDesc(CommunityPost::getCreateTime)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    public List<CommunityPost> getEssencePosts(Integer limit) {
        LambdaQueryWrapper<CommunityPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommunityPost::getDelFlag, 0)
                .eq(CommunityPost::getStatus, 1) // 已发布
                .eq(CommunityPost::getIsEssence, 1) // 精华
                .orderByDesc(CommunityPost::getCreateTime)
                .last("LIMIT " + limit);
        
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean logicDeleteById(String id) {
        LambdaUpdateWrapper<CommunityPost> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(CommunityPost::getId, id)
                .set(CommunityPost::getDelFlag, 1)
                .set(CommunityPost::getUpdateTime, new Date());
        
        return update(updateWrapper);
    }
} 