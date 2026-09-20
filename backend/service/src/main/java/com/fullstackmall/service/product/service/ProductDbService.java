package com.fullstackmall.service.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.contract.product.ProductQueryRequest;
import com.fullstackmall.contract.product.ProductStatus;
import com.fullstackmall.service.common.excetion.BusinessException;
import com.fullstackmall.service.product.entity.ProductEntity;
import com.fullstackmall.service.product.mapper.ProductMapper;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class ProductDbService extends ServiceImpl<ProductMapper, ProductEntity> {

    public ProductEntity requireById(Long productId) {
        ProductEntity product = getById(productId);
        if (product == null) {
            throw productNotFound();
        }
        return product;
    }

    public ProductEntity requirePublishedById(Long productId) {
        ProductEntity product = lambdaQuery()
                .eq(ProductEntity::getId, productId)
                .eq(ProductEntity::getStatusCode, ProductStatus.ON_SALE)
                .one();
        if (product == null) {
            throw productNotFound();
        }
        return product;
    }

    public Page<ProductEntity> queryAdminPage(ProductQueryRequest request) {
        return queryPage(request, request.getStatus());
    }

    public Page<ProductEntity> queryPublishedPage(ProductQueryRequest request) {
        return queryPage(request, ProductStatus.ON_SALE);
    }

    public ProductEntity updateBasicInfo(Long productId, Long categoryId, String title, String description) {
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<ProductEntity> wrapper = Wrappers.lambdaUpdate(ProductEntity.class);
        wrapper.eq(ProductEntity::getId, productId)
                .set(ProductEntity::getCategoryId, categoryId)
                .set(ProductEntity::getTitle, title)
                .set(ProductEntity::getDescription, description)
                .set(ProductEntity::getUpdatedAt, now);
        int affectedRows = baseMapper.update(null, wrapper);
        if (affectedRows == 0) {
            throw productNotFound();
        }
        return requireById(productId);
    }

    public ProductEntity updateSubtitle(Long productId, String subtitle) {
        LocalDateTime now = LocalDateTime.now();
        LambdaUpdateWrapper<ProductEntity> wrapper = Wrappers.lambdaUpdate(ProductEntity.class);
        wrapper.eq(ProductEntity::getId, productId).set(ProductEntity::getSubtitle, subtitle);
        int effectedRows = baseMapper.update(null, wrapper);
        if (effectedRows == 0) {
            throw productNotFound();
        }
        return requireById(productId);
    }

    private Page<ProductEntity> queryPage(ProductQueryRequest request, ProductStatus forcedStatus) {
        LambdaQueryWrapper<ProductEntity> wrapper = Wrappers.lambdaQuery(ProductEntity.class);
        String keyword = normalizeKeyword(request.getKeyword());
        if (StringUtils.hasText(keyword)) {
            wrapper.and(condition -> condition
                    .eq(ProductEntity::getTitle, keyword)
                    .or()
                    .eq(ProductEntity::getDescription, keyword)
            );
        }
        if (request.getCategoryId() != null) {
            wrapper.eq(ProductEntity::getCategoryId, request.getCategoryId());
        }

        if (forcedStatus != null ){
            wrapper.eq(ProductEntity::getStatusCode, forcedStatus);
        }
        wrapper.orderByDesc(ProductEntity::getCreatedBy).orderByDesc(ProductEntity::getId);
        return page(new Page<>(request.getPageNum(), request.getPageSize()), wrapper);
    }

    private BusinessException productNotFound() {
        return new BusinessException(
                ApiCode.PRODUCT_NOT_FOUND,
                ApiCode.PRODUCT_NOT_FOUND.defaultMessage(),
                HttpStatus.NOT_FOUND
        );
    }
    private String normalizeKeyword(String keyword) {
        return keyword == null ? null : keyword.trim();
    }
}
