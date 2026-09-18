package com.fullstackmall.service.product.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.contract.product.ProductStatus;
import com.fullstackmall.service.common.excetion.BusinessException;
import com.fullstackmall.service.product.entity.ProductEntity;
import com.fullstackmall.service.product.mapper.ProductMapper;
import org.springframework.http.HttpStatus;

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

    private BusinessException productNotFound() {
        return new BusinessException(
                ApiCode.PRODUCT_NOT_FOUND,
                ApiCode.PRODUCT_NOT_FOUND.defaultMessage(),
                HttpStatus.NOT_FOUND
        );
    }

}
