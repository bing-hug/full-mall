package com.fullstackmall.contract.inventory;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkuCreateRequest {
    /**
     * 前端提交的SKU 业务编码，服务端会trim,并由数据库唯一约束防止重复
     */
    @NotBlank(message = "SKU 编码不能为空")
    @Size(max = 64, message = "SKU编码不能超过 64 个 字符")
    private String skuCode;

    /**
     * 前端提交的 SpecText 规格描述
     */
    @NotBlank(message = "规格描述不能为空")
    @Size(max = 200, message = "规格描述不能超过 200 个字符")
    private String specText;

    /**
     * SKU 初始销售价，金额必须使用BigDecimal
     */
    @NotNull(message = "销售价不能为空")
    @DecimalMin(value = "0.01", message = "销售价必须大于0")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;

    @NotNull(message = "初始库存不能为空")
    @Min(value = 0, message = "初始化库存不能小于0")
    private Integer initialStock;
}
