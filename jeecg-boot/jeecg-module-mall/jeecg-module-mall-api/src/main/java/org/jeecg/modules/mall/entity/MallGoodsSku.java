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
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 商品SKU
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Data
@TableName("mall_goods_sku")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_goods_sku对象", description="商品SKU")
public class MallGoodsSku implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 商品ID */
    @ApiModelProperty(value = "商品ID")
    private String goodsId;
    
    /** SKU编码 */
    @Excel(name = "SKU编码", width = 15)
    @ApiModelProperty(value = "SKU编码")
    private String skuCode;
    
    /** 规格值JSON */
    @ApiModelProperty(value = "规格值JSON")
    private String specValues;
    
    /** SKU图片 */
    @ApiModelProperty(value = "SKU图片")
    private String image;
    
    /** 原价 */
    @Excel(name = "原价", width = 15)
    @ApiModelProperty(value = "原价")
    private BigDecimal originalPrice;
    
    /** 售价 */
    @Excel(name = "售价", width = 15)
    @ApiModelProperty(value = "售价")
    private BigDecimal price;
    
    /** 库存 */
    @Excel(name = "库存", width = 15)
    @ApiModelProperty(value = "库存")
    private Integer stock;
    
    /** 预警库存 */
    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;
    
    /** 销量 */
    @Excel(name = "销量", width = 15)
    @ApiModelProperty(value = "销量")
    private Integer sales;
    
    /** 锁定库存 */
    @ApiModelProperty(value = "锁定库存")
    private Integer lockStock;
    
    /** 状态(0-禁用,1-启用) */
    @Excel(name = "状态", width = 15, dicCode = "sku_status")
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