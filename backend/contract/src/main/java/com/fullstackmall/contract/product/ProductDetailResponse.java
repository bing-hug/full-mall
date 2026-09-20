package com.fullstackmall.contract.product;

import java.time.LocalDateTime;

public class ProductDetailResponse {
    /** 商品逐渐，由数据库生成*/
    private Long id;
    /** 商品所属分类主键*/
    private Long categoryId;
    private String categoryName;
    private String title;
    private String subTitle;
    private String description;
    private ProductStatus status;
    private Long createBy;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
