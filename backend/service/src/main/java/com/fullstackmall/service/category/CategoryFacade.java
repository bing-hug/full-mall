package com.fullstackmall.service.category;

import com.fullstackmall.contract.category.CategoryDraftRequest;
import com.fullstackmall.contract.category.CategoryDraftResponse;
import com.fullstackmall.contract.category.CategorySummaryResponse;
import com.fullstackmall.contract.category.ICategoryFacade;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.service.category.entity.CategoryEntity;
import com.fullstackmall.service.category.service.CategoryDbService;
import com.fullstackmall.service.common.excetion.BusinessException;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryFacade implements ICategoryFacade {

    private static final String RESERVED_CATEGORY_NAME = "全部";

    @Resource
    private CategoryDbService categoryDbService;

    @Override
    public CategoryDraftResponse validateDraft(CategoryDraftRequest request) {
        String normalizedName = request.getName().strip();
        String normalizedDescription = normalizeOptionalText(request.getDescription());
        if (RESERVED_CATEGORY_NAME.equals(normalizedName)) {
            throw new BusinessException(
                    ApiCode.CATEGORY_NAME_RESERVED,
                    "分类名师”全部“ 是系统保留名称"
            );
        }

        return new CategoryDraftResponse(
                normalizedName,
                normalizedDescription,
                request.getSortOrder()
        );
    }

    @Override
    public List<CategorySummaryResponse> listAdminCategories() {
        return categoryDbService.listAdminCategories().stream().map(this::toSummaryResponse).toList();
    }

    private CategorySummaryResponse toSummaryResponse(CategoryEntity category) {
        return new CategorySummaryResponse(
                category.getId(),
                category.getName(),
                category.getStatus(),
                category.getSortOrder(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    private String normalizeOptionalText(String text){
        if (text == null) {
            return null;
        }
        String normalized = text.strip();
        return normalized.isEmpty() ? null : normalized;
    }
}
