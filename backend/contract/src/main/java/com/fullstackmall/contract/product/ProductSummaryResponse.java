package com.fullstackmall.contract.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryResponse {
    private Long id;
    private Long categoryId;
    private String categoryName;
    private ProductStatus status;
    private LocalDateTime createdAt;
}
