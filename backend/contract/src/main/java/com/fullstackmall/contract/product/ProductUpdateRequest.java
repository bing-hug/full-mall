package com.fullstackmall.contract.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
    @NotNull(message = "商品类型不能为空")
    @Positive(message = "商品分类 ID 必须大于 0")
    private Integer categoryId;

    @NotBlank(message = "商品标题不能为空")
    @Size(max = 100, message = "商品标题不能大于 100 个字符")
    private String title;

    @Size(max = 2000, message = "商品描述不能超过 2000 个字符")
    private String description;
}
