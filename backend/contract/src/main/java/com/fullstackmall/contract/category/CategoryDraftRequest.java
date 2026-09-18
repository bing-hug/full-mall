package com.fullstackmall.contract.category;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDraftRequest {
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 30, message = "分类名称不能超过 30 个字符")
    private String name;

    @Size(max = 200, message = "分类描述不能超过 200 个字符")
    private String description;

    @NotNull(message = "排序值不能为空")
    @Min(value = 0, message = "排序值不能小于0")
    @Max(value = 9999, message = "排序值不能大于 9999")
    private Integer sortOrder;
}
