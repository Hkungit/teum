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
 * @Description: 社区评论
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Data
@TableName("community_comment")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="community_comment对象", description="社区评论")
public class CommunityComment implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 帖子ID */
    @ApiModelProperty(value = "帖子ID")
    private String postId;
    
    /** 评论内容 */
    @Excel(name = "评论内容", width = 30)
    @ApiModelProperty(value = "评论内容")
    private String content;
    
    /** 评论人ID */
    @ApiModelProperty(value = "评论人ID")
    private String userId;
    
    /** 评论人名称 */
    @Excel(name = "评论人名称", width = 15)
    @ApiModelProperty(value = "评论人名称")
    private String userName;
    
    /** 父评论ID */
    @ApiModelProperty(value = "父评论ID")
    private String parentId;
    
    /** 回复的评论ID */
    @ApiModelProperty(value = "回复的评论ID")
    private String replyId;
    
    /** 回复的用户ID */
    @ApiModelProperty(value = "回复的用户ID")
    private String replyUserId;
    
    /** 回复的用户名称 */
    @Excel(name = "回复的用户名称", width = 15)
    @ApiModelProperty(value = "回复的用户名称")
    private String replyUserName;
    
    /** 图片列表 */
    @ApiModelProperty(value = "图片列表")
    private String images;
    
    /** 点赞数 */
    @Excel(name = "点赞数", width = 15)
    @ApiModelProperty(value = "点赞数")
    private Integer likeCount;
    
    /** 状态(0-待审核,1-已发布,2-已拒绝) */
    @Excel(name = "状态", width = 15, dicCode = "comment_status")
    @ApiModelProperty(value = "状态")
    private Integer status;
    
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