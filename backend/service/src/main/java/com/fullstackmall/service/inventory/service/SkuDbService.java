package com.fullstackmall.service.inventory.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.service.common.excetion.BusinessException;
import com.fullstackmall.service.inventory.entity.SkuEntity;
import com.fullstackmall.service.inventory.mapper.SkuMapper;
import com.fullstackmall.service.product.entity.ProductEntity;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SkuDbService extends ServiceImpl<SkuMapper, SkuEntity> {

    public SkuEntity requireById(Long skuId) {
        SkuEntity sku = getById(skuId);
        if (sku == null) {
            throw new BusinessException(
                    ApiCode.SKU_NOT_FOUND,
                    ApiCode.SKU_NOT_FOUND.defaultMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
        return sku;
    }

    public List<SkuEntity> listByProductId(Long productId) {
        return lambdaQuery()
                .eq(SkuEntity::getProductId, productId)
                .orderByAsc(SkuEntity::getId)
                .list();
    }

    public List<SkuEntity> listExistingByIds(Collection<Long> skuIds) {
        if (skuIds == null || skuIds.isEmpty()) {
            return Collections.emptyList();
        }
        return lambdaQuery().in(SkuEntity::getId, skuIds).list();
    }

    public boolean existsBySkuCode(String skuCode) {
        return lambdaQuery()
                .eq(SkuEntity::getSkuCode, skuCode)
                .count() > 0;
    }

    public int updatePriceByVersion(Long skuId, BigDecimal salePrice, Integer expectedVersion) {
        return baseMapper.updatePriceByVersion(skuId, salePrice, expectedVersion);
    }

    public int adjustAvailableStockByVersion(Long skuId, Integer delta, Integer expectedVersion) {
        return baseMapper.adjustAvailableStockByVersion(skuId, delta, expectedVersion);
    }

    public void requireSaleableSku(Long productId) {
        boolean exists = lambdaQuery()
                .eq(SkuEntity::getProductId, productId)
                .gt(SkuEntity::getSalePrice, BigDecimal.ZERO)
                .gt(SkuEntity::getAvailableStock, 0)
                .count() > 0;
        if (!exists) {
            throw new BusinessException(
                    ApiCode.PRODUCT_NOT_READY_FOR_SALE,
                    ApiCode.PRODUCT_NOT_READY_FOR_SALE.defaultMessage()
            );
        }
    }

    /**
     * 创建订单时把可售库存原子转为锁定库存；库存不足时 SQL 不更新并返回 0。
     */
    public int lockStock(Long skuId, Integer quantity) {
        return baseMapper.lockStock(skuId, quantity);
    }

    /**
     * 取消或关闭订单时把锁定库存退回可售库存；返回 0 通常表示锁定库存已不满足释放条件。
     */
    public int releaseLockedStock(Long skuId, Integer quantity) {
        return baseMapper.releaseLockedStock(skuId, quantity);
    }

    /**
     * 支付成功后只减少锁定库存，不再增加可售库存，表示这部分商品已经真正售出。
     */
    public int confirmLockedStock(Long skuId, Integer quantity) {
        return baseMapper.confirmLockedStock(skuId, quantity);
    }
}

