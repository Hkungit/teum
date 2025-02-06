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
 * @Description: 商品信息
 * @Author: jeecg-boot
 * @Date: 2024-01-01
 * @Version: V1.0
 */
@Data
@TableName("mall_goods")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="mall_goods对象", description="商品信息")
public class MallGoods implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    
    /** 商品名称 */
    @Excel(name = "商品名称", width = 15)
    @ApiModelProperty(value = "商品名称")
    private String name;
    
    /** 商品编码 */
    @Excel(name = "商品编码", width = 15)
    @ApiModelProperty(value = "商品编码")
    private String code;
    
    /** 商品分类ID */
    @ApiModelProperty(value = "商品分类ID")
    private String categoryId;
    
    /** 商品价格 */
    @Excel(name = "商品价格", width = 15)
    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;
    
    /** 商品库存 */
    @Excel(name = "商品库存", width = 15)
    @ApiModelProperty(value = "商品库存")
    private Integer stock;
    
    /** 商品描述 */
    @Excel(name = "商品描述", width = 30)
    @ApiModelProperty(value = "商品描述")
    private String description;
    
    /** 商品图片 */
    @ApiModelProperty(value = "商品图片")
    private String images;
    
    /** 商品状态 (0-下架 1-上架) */
    @Excel(name = "商品状态", width = 15, dicCode = "goods_status")
    @ApiModelProperty(value = "商品状态")
    private Integer status;
    
    /** 商品评分 */
    @Excel(name = "商品评分", width = 15)
    @ApiModelProperty(value = "商品评分")
    private Double rating;
    
    /** 供应商ID */
    @ApiModelProperty(value = "供应商ID")
    private String supplierId;
    
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