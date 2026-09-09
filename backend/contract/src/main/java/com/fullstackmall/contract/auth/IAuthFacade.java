package com.fullstackmall.contract.auth;

public interface IAuthFacade {
    /**
     * 注册普通用户：秘密会在service 种换位BCCrypt 哈希后在写库后写库。
     * @param request 注册信息
     * @return 注册成功后安全用户信息
     */
    CurrentUserResponse register(RegisterRequest request);
    AuthTokenResponse login(LoginRequest request);
}


