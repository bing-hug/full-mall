package com.fullstackmall.contract.inventory;

import java.util.List;

public interface ISkuFacade {
    SkuResponse createSku(Long productId, SkuCreateRequest request);

    /**
     * 查询指定商品的全部SKU，仅供管理员维护
     * @param productId
     * @return
     */
    List<SkuResponse> queryAdminSkus(Long productId);

    List<SkuResponse> queryPublishedSkus(Long productId);

    SkuResponse updatePrice(Long skuId, SkuPriceUpdateRequest request);

    SkuResponse adjustStock(Long skuId, SkuStockAdjustRequest request);
}
