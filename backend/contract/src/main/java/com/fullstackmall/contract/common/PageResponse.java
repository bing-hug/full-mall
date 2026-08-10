package com.fullstackmall.contract.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 通用分页响应。
 * @param <T> 当前页记录类型
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private long PageNum;
    private long PageSize;
    private long total;
    private long pages;
    private List<T> records;
}
