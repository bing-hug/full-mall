package com.fullstackmall.contract.product;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductQueryRequest {

    /** 请求页码，默认从第 1 页开始 */
    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = 1;

    /** 每页数量，默认10， 最多100*/
    @Min(value = 1, message = "每页数量不能低于1")
    @Max(value = 100, message = "每页数量不能大于100")
    private Integer pageSize = 10;

    /** 可选标题关键字； */
    @Size(max = 100, message = "查询关键字不能超过 100 个字符")
    private String keyword;

    /** 可选分类筛选条件 */
    @Positive(message = "商品分类 ID 必须大于 0")
    private Long categoryId;

    /** 管理端可用的状态筛选；公开查询会由服务端强制为 ON_SALE, 不能前端覆盖*/
    private ProductStatus status;
}
