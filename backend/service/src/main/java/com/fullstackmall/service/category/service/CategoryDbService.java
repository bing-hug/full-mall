package com.fullstackmall.service.category.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.service.category.entity.CategoryEntity;
import com.fullstackmall.service.category.mapper.CategoryMapper;
import com.fullstackmall.service.common.excetion.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryDbService extends ServiceImpl<CategoryMapper, CategoryEntity> {
    private static final int ENABLED = 1;

    public CategoryEntity requireCategory(Long categoryId) {
        CategoryEntity category = getById(categoryId);
        if (category == null) {
            throw categoryNotFound();

        }
        return category;
    }

    public List<CategoryEntity> listAdminCategories() {
     return lambdaQuery().orderByAsc(CategoryEntity::getSortOrder)
             .orderByAsc(CategoryEntity::getId).list();
    }



    private BusinessException categoryNotFound() {
        return new BusinessException(
                ApiCode.NOT_FOUND,
                ApiCode.NOT_FOUND.defaultMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}
