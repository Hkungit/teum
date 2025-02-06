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
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 用户关注
 * @Author: jeecg-boot
 * @Date: 2024-02-05
 * @Version: V1.0
 */
@Data
@TableName("community_user_follow")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="community_user_follow对象", description="用户关注")
public class CommunityUserFollow implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private String userId;
    
    /** 关注的用户ID */
    @ApiModelProperty(value = "关注的用户ID")
    private String followUserId;
    
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