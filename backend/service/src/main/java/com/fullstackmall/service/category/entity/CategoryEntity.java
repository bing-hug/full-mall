package com.fullstackmall.service.category.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("mall_category")
public class CategoryEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    /** 分类启用状态：1 表示启用，0 表示停用；公开商品只能使用启用分类。 */
    private Integer status;

    /** 分类排序值，数值越小越靠前，属于后台配置字段。 */
    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("created_at")
    private LocalDateTime createdAt;

    @TableField("updated_at")
    private LocalDateTime updatedAt;
}
