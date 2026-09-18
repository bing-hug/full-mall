package com.fullstackmall.contract.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDraftResponse {
    private String name;
    private String description;
    private Integer sortOrder;
}
