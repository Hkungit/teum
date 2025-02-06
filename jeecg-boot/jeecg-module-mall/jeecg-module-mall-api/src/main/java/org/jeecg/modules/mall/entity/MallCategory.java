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
 * @Description: 商品分类
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Data
@TableName("mall_category")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_category对象", description="商品分类")
public class MallCategory implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 分类名称 */
    @Excel(name = "分类名称", width = 15)
    @ApiModelProperty(value = "分类名称")
    private String name;
    
    /** 父级ID */
    @ApiModelProperty(value = "父级ID")
    private String parentId;
    
    /** 分类编码 */
    @Excel(name = "分类编码", width = 15)
    @ApiModelProperty(value = "分类编码")
    private String code;
    
    /** 分类图标 */
    @ApiModelProperty(value = "分类图标")
    private String icon;
    
    /** 排序 */
    @Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
    
    /** 分类描述 */
    @Excel(name = "分类描述", width = 30)
    @ApiModelProperty(value = "分类描述")
    private String description;
    
    /** 状态(0-禁用,1-启用) */
    @Excel(name = "状态", width = 15, dicCode = "category_status")
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