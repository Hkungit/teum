package org.jeecg.modules.community.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.community.entity.CommunityTopic;
import org.jeecg.modules.community.service.ICommunityTopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 社区话题
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="社区话题")
@RestController
@RequestMapping("/community/topic")
public class CommunityTopicController extends JeecgController<CommunityTopic, ICommunityTopicService> {

    @Autowired
    private ICommunityTopicService communityTopicService;

    /**
     * 创建话题
     */
    @AutoLog(value = "社区话题-创建")
    @ApiOperation(value="社区话题-创建", notes="社区话题-创建")
    @PostMapping(value = "/create")
    public Result<?> create(@RequestBody CommunityTopic topic) {
        if (communityTopicService.createTopic(topic)) {
            return Result.OK("创建成功！");
        }
        return Result.error("创建失败！");
    }

    /**
     * 更新话题状态
     */
    @AutoLog(value = "社区话题-更新状态")
    @ApiOperation(value="社区话题-更新状态", notes="社区话题-更新状态")
    @PutMapping(value = "/status")
    public Result<?> updateStatus(@RequestParam(name="id") String id,
                                @RequestParam(name="status") Integer status) {
        if (communityTopicService.updateStatus(id, status)) {
            return Result.OK("状态更新成功！");
        }
        return Result.error("状态更新失败！");
    }

    /**
     * 更新推荐状态
     */
    @AutoLog(value = "社区话题-更新推荐状态")
    @ApiOperation(value="社区话题-更新推荐状态", notes="社区话题-更新推荐状态")
    @PutMapping(value = "/recommend")
    public Result<?> updateRecommend(@RequestParam(name="id") String id,
                                   @RequestParam(name="isRecommend") Boolean isRecommend) {
        if (communityTopicService.updateRecommend(id, isRecommend)) {
            return Result.OK(isRecommend ? "设为推荐成功！" : "取消推荐成功！");
        }
        return Result.error(isRecommend ? "设为推荐失败！" : "取消推荐失败！");
    }

    /**
     * 分页列表查询
     */
    @AutoLog(value = "社区话题-分页列表查询")
    @ApiOperation(value="社区话题-分页列表查询", notes="社区话题-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(
            @RequestParam(name="keyword", required=false) String keyword,
            @RequestParam(name="status", required=false) Integer status,
            @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
            @RequestParam(name="pageSize", defaultValue="10") Integer pageSize) {
        Page<CommunityTopic> page = new Page<>(pageNo, pageSize);
        IPage<CommunityTopic> pageList = communityTopicService.getTopicList(page, keyword, status);
        return Result.OK(pageList);
    }

    /**
     * 获取推荐话题
     */
    @AutoLog(value = "社区话题-获取推荐话题")
    @ApiOperation(value="社区话题-获取推荐话题", notes="社区话题-获取推荐话题")
    @GetMapping(value = "/recommend")
    public Result<?> getRecommendTopics(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityTopic> list = communityTopicService.getRecommendTopics(limit);
        return Result.OK(list);
    }

    /**
     * 获取热门话题
     */
    @AutoLog(value = "社区话题-获取热门话题")
    @ApiOperation(value="社区话题-获取热门话题", notes="社区话题-获取热门话题")
    @GetMapping(value = "/hot")
    public Result<?> getHotTopics(@RequestParam(name="limit", defaultValue="10") Integer limit) {
        List<CommunityTopic> list = communityTopicService.getHotTopics(limit);
        return Result.OK(list);
    }

    /**
     * 通过id删除
     */
    @AutoLog(value = "社区话题-通过id删除")
    @ApiOperation(value="社区话题-通过id删除", notes="社区话题-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name="id") String id) {
        if (communityTopicService.logicDeleteById(id)) {
            return Result.OK("删除成功!");
        }
        return Result.error("删除失败!");
    }
} 