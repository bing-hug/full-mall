package com.fullstackmall.contract.auth;

public enum UserRole {
    /** 普通用户，可以访自己的购物车、订单和支付信息*/
    USER,
    /** 管理员，可以访问用户管理和商品后台接口*/
    ADMIN;

    public static UserRole fromCode(String code ){
        for (UserRole role : values()) {
            if (role.name().equalsIgnoreCase(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("未知用户角色：" + code);
    }
}
