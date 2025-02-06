package org.jeecg.modules.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 社区帖子
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Data
@TableName("community_post")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="community_post对象", description="社区帖子")
public class CommunityPost implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 标题 */
    @Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private String title;
    
    /** 内容 */
    @ApiModelProperty(value = "内容")
    private String content;
    
    /** 作者ID */
    @ApiModelProperty(value = "作者ID")
    private String authorId;
    
    /** 作者名称 */
    @Excel(name = "作者名称", width = 15)
    @ApiModelProperty(value = "作者名称")
    private String authorName;
    
    /** 话题ID */
    @ApiModelProperty(value = "话题ID")
    private String topicId;
    
    /** 话题名称 */
    @Excel(name = "话题名称", width = 15)
    @ApiModelProperty(value = "话题名称")
    private String topicName;
    
    /** 封面图 */
    @ApiModelProperty(value = "封面图")
    private String coverImage;
    
    /** 图片列表 */
    @ApiModelProperty(value = "图片列表")
    private String images;
    
    /** 视频链接 */
    @ApiModelProperty(value = "视频链接")
    private String videoUrl;
    
    /** 点赞数 */
    @Excel(name = "点赞数", width = 15)
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;
    
    /** 评论数 */
    @Excel(name = "评论数", width = 15)
    @ApiModelProperty(value = "评论数")
    private Integer commentCount;
    
    /** 浏览数 */
    @Excel(name = "浏览数", width = 15)
    @ApiModelProperty(value = "浏览数")
    private Integer viewCount;
    
    /** 类型(1-普通帖子,2-投票帖,3-问答帖) */
    @Excel(name = "类型", width = 15, dicCode = "post_type")
    @ApiModelProperty(value = "类型")
    private Integer type;
    
    /** 状态(0-待审核,1-已发布,2-已拒绝) */
    @Excel(name = "状态", width = 15, dicCode = "post_status")
    @ApiModelProperty(value = "状态")
    private Integer status;
    
    /** 是否置顶(0-否,1-是) */
    @Excel(name = "是否置顶", width = 15, dicCode = "yes_no")
    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;
    
    /** 是否加精(0-否,1-是) */
    @Excel(name = "是否加精", width = 15, dicCode = "yes_no")
    @ApiModelProperty(value = "是否加精")
    private Integer isEssence;
    
    /** 创建人 */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    
    /** 创建时间 */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    
    /** 更新人 */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    
    /** 更新时间 */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    
    /** 所属部门 */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    
    /** 删除状态 */
    @ApiModelProperty(value = "删除状态")
    private Integer delFlag;
} 