package com.fullstackmall.service.auth;

import com.fullstackmall.contract.auth.CurrentUserResponse;
import com.fullstackmall.contract.auth.IAuthFacade;
import com.fullstackmall.contract.auth.RegisterRequest;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthFacade implements IAuthFacade {
    @Resource
    private AuthService authService;

    @Override
    public CurrentUserResponse register(RegisterRequest request) {
        return authService.register(request);
    }
}
