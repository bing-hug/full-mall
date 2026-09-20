package com.fullstackmall.contract.product;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatusChangeRequest {

    @NotNull(message = "目标状态不能为空")
    private ProductStatus productStatus;
}
