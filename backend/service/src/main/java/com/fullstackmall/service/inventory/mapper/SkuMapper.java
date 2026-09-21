package com.fullstackmall.service.inventory.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fullstackmall.service.inventory.entity.SkuEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

public interface SkuMapper extends BaseMapper<SkuEntity>{
    @Update("""
            UPDATE mall_product_sku
            set sale_price = #{salePrice},
                version = version + 1,
                updated_at = CURRENT_TIMESTAMP(3)
            where id = #{skuId} and version = #{expectedVersion}
            """)
    int updatePriceByVersion(
            @Param("skuId") Long skuId,
            @Param("salePrice")BigDecimal salePrice,
            @Param("expectedVersion") Integer expectedVersion
    );


    @Update(
            """
            update mall_product_sku 
            set available_stock = available_stock + #(delta),
                version = version + 1,
                updated_at = CURRENT_TIMESTAMP(3)
            where id = #{skuId} and version = #{expectedVersion}
            """)
    int adjustAvailableStockByVersion(
            @Param("skuId") Long skuId,
            @Param("delta") Integer delta,
            @Param("expectedVersion") Integer expectedVersion
    );

    @Update("""
            update mall_product_sku
            set available_stock = available_stock - #{quantity},
                locaked_stock = locked_stock + #{quantity},
                version = version + 1,
                updated_at = CURRENT_TIMESTAMP(3)
            """)
    int lockStock(
            @Param("skuId") Long skuId,
            @Param("quantity") Integer quantity
    );

    /**
     * 原子释放锁定库存：取消或关单时恢复可售数量。
     */
    @Update("""
            UPDATE mall_product_sku
            SET available_stock = available_stock + #{quantity},
                locked_stock = locked_stock - #{quantity},
                version = version + 1,
                updated_at = CURRENT_TIMESTAMP(3)
            WHERE id = #{skuId}
              AND locked_stock >= #{quantity}
            """)
    int releaseLockedStock(
            @org.apache.ibatis.annotations.Param("skuId") Long skuId,
            @org.apache.ibatis.annotations.Param("quantity") Integer quantity
    );

    /**
     * 原子确认售出：支付成功后仅扣减锁定库存。
     */
    @Update("""
            UPDATE mall_product_sku
            SET locked_stock = locked_stock - #{quantity},
                version = version + 1,
                updated_at = CURRENT_TIMESTAMP(3)
            WHERE id = #{skuId}
              AND locked_stock >= #{quantity}
            """)
    int confirmLockedStock(
            @org.apache.ibatis.annotations.Param("skuId") Long skuId,
            @org.apache.ibatis.annotations.Param("quantity") Integer quantity
    );
}
