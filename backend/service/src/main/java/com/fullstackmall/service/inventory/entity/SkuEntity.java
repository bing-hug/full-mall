package com.fullstackmall.service.inventory.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("mall_product_sku")
public class SkuEntity {

    /**
     * SKU 主键，由MySql 自增生成
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * SKU 所属商品 ID，一个商品有不同的规格的SKU
     */
    private Long productId;

    /**
     *  SKU 业务编码，用于唯一识别具体规格，不能只依赖展示文案
     */
    @TableField("sku_code")
    private String skuCode;

    /**
     *  规格展示文本，例如“黑色” 不是唯一值
     */
    @TableField("spec_text")
    private String specText;

    /**
     * 当前销售价格，金额用BigDecimal, 避免浮点类型的二进制精度问题
     */
    @TableField("sale_price")
    private BigDecimal salePrice;

    /**
     * 当前还能被新订单锁定的库存
     */
    @TableField("available_stock")
    private Integer availableStock;

    /**
     * 已被待支付订单占用，但尚未最终成交的库存
     */
    @TableField("locked_stock")
    private Integer lockedStock;

    /**
     * MyBatis-Plus 乐观锁版本号，每次受保护的更新成功后自动递增
     */
    @Version
    private Integer version;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;

}
