package org.jeecg.modules.community.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.community.entity.CommunityTopic;

import java.util.List;

/**
 * @Description: 社区话题
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
public interface ICommunityTopicService extends IService<CommunityTopic> {

    /**
     * 创建话题
     * @param topic 话题信息
     * @return 是否成功
     */
    boolean createTopic(CommunityTopic topic);

    /**
     * 更新话题状态
     * @param id 话题ID
     * @param status 状态(0-禁用,1-启用)
     * @return 是否成功
     */
    boolean updateStatus(String id, Integer status);

    /**
     * 更新话题推荐状态
     * @param id 话题ID
     * @param isRecommend 是否推荐
     * @return 是否成功
     */
    boolean updateRecommend(String id, boolean isRecommend);

    /**
     * 更新话题帖子数
     * @param id 话题ID
     * @param increment 增量(可为负数)
     * @return 是否成功
     */
    boolean updatePostCount(String id, int increment);

    /**
     * 更新话题关注数
     * @param id 话题ID
     * @param increment 增量(可为负数)
     * @return 是否成功
     */
    boolean updateFollowCount(String id, int increment);

    /**
     * 获取话题列表
     * @param page 分页参数
     * @param keyword 关键词
     * @param status 状态
     * @return 分页结果
     */
    IPage<CommunityTopic> getTopicList(Page<CommunityTopic> page, String keyword, Integer status);

    /**
     * 获取推荐话题
     * @param limit 限制数量
     * @return 话题列表
     */
    List<CommunityTopic> getRecommendTopics(Integer limit);

    /**
     * 获取热门话题
     * @param limit 限制数量
     * @return 话题列表
     */
    List<CommunityTopic> getHotTopics(Integer limit);

    /**
     * 逻辑删除话题
     * @param id 话题ID
     * @return 是否成功
     */
    boolean logicDeleteById(String id);
} 