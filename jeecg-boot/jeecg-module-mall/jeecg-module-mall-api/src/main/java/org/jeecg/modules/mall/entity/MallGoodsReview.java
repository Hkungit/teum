package org.jeecg.modules.mall.entity;

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
 * @Description: 商品评价
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Data
@TableName("mall_goods_review")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_goods_review对象", description="商品评价")
public class MallGoodsReview implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 商品ID */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    
    /** 订单ID */
    @ApiModelProperty(value = "订单ID")
    private String orderId;
    
    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private String userId;
    
    /** 用户名 */
    @Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private String username;
    
    /** 评分(1-5) */
    @Excel(name = "评分", width = 15)
    @ApiModelProperty(value = "评分")
    private Integer rating;
    
    /** 评价内容 */
    @Excel(name = "评价内容", width = 30)
    @ApiModelProperty(value = "评价内容")
    private String content;
    
    /** 评价图片 */
    @ApiModelProperty(value = "评价图片")
    private String images;
    
    /** 商家回复 */
    @Excel(name = "商家回复", width = 30)
    @ApiModelProperty(value = "商家回复")
    private String reply;
    
    /** 是否匿名(0-否,1-是) */
    @Excel(name = "是否匿名", width = 15, dicCode = "review_anonymous")
    @ApiModelProperty(value = "是否匿名")
    private Integer anonymous;
    
    /** 状态(0-待审核,1-已通过,2-已拒绝) */
    @Excel(name = "状态", width = 15, dicCode = "review_status")
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
    
    /** 删除标识 */
    @ApiModelProperty(value = "删除标识")
    private Integer delFlag;
} 