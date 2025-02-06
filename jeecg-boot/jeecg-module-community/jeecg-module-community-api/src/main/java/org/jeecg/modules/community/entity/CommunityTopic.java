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
 * @Description: 社区话题
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Data
@TableName("community_topic")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="community_topic对象", description="社区话题")
public class CommunityTopic implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 话题名称 */
    @Excel(name = "话题名称", width = 15)
    @ApiModelProperty(value = "话题名称")
    private String name;
    
    /** 话题描述 */
    @Excel(name = "话题描述", width = 30)
    @ApiModelProperty(value = "话题描述")
    private String description;
    
    /** 话题图标 */
    @ApiModelProperty(value = "话题图标")
    private String icon;
    
    /** 背景图片 */
    @ApiModelProperty(value = "背景图片")
    private String bgImage;
    
    /** 帖子数量 */
    @Excel(name = "帖子数量", width = 15)
    @ApiModelProperty(value = "帖子数量")
    private Integer postCount;
    
    /** 关注数量 */
    @Excel(name = "关注数量", width = 15)
    @ApiModelProperty(value = "关注数量")
    private Integer followCount;
    
    /** 排序 */
    @Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
    
    /** 状态(0-禁用,1-启用) */
    @Excel(name = "状态", width = 15, dicCode = "topic_status")
    @ApiModelProperty(value = "状态")
    private Integer status;
    
    /** 是否推荐(0-否,1-是) */
    @Excel(name = "是否推荐", width = 15, dicCode = "yes_no")
    @ApiModelProperty(value = "是否推荐")
    private Integer isRecommend;
    
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