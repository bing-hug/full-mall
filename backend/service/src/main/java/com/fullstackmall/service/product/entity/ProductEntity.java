package com.fullstackmall.service.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mall_product")
public class ProductEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("category_id")
    private Long categoryId;

    private String title;

    private String subtitle;

    private String description;

    @TableField("status_code")
    private String statusCode;

    @TableField("created_by")
    private LocalDateTime createdBy;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
