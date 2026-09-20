package com.fullstackmall.contract.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSubTitleUpdateRequest {
    @NotBlank(message = "商品副标题不能为空")
    @Size(max = 100, message = "商品副标题不能超过 100 个字符")
    private String subTitle;
}
