package com.fullstackmall.contract.user;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserQueryRequest {
    @Min(value = 1, message = "页面不能小于 1")
    private Integer pageNum = 1;
    @Min(value = 1, message = "每页数量不能小于 1")
    @Max(value = 100, message = "每页数量不能大于100")
    private Integer pageSize = 10;
    @Size(max = 50, message = "查询关键字不能超过 50个字符")
    private String keyword;
    @Min(value = 0, message = "用户状态只能是0 或 1")
    @Max(value = 1, message = "用户状态只能是0 或 1")
    private Integer status;
}
