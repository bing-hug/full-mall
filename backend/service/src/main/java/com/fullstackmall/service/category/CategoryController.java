package com.fullstackmall.service.category;

import com.fullstackmall.contract.category.CategoryDraftRequest;
import com.fullstackmall.contract.category.CategoryDraftResponse;
import com.fullstackmall.contract.category.ICategoryFacade;
import com.fullstackmall.contract.common.ApiResponse;
import com.fullstackmall.service.common.trace.TraceIdContext;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Resource
    private ICategoryFacade categoryFacade;

    @Operation(
            summary = "校验分类草稿",
            description = "校验并规范分类草稿"
    )

    @PostMapping("/validate")
    public ApiResponse<CategoryDraftResponse> validateCategory(
            @Valid @RequestBody CategoryDraftRequest request,
            HttpServletRequest httpRequest
    ){
        CategoryDraftResponse result = categoryFacade.validateDraft(request);
        return ApiResponse.success(result, TraceIdContext.get(httpRequest));

    }
}
