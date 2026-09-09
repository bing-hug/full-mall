package com.fullstackmall.service.auth;

import com.fullstackmall.contract.auth.*;
import com.fullstackmall.contract.common.ApiResponse;
import com.fullstackmall.service.common.trace.TraceIdContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证")
public class AuthController {

    @Resource
    private IAuthFacade authFacade;

    @PostMapping("/register")
    @Operation(summary = "注册普通用户")
    public ResponseEntity<ApiResponse<CurrentUserResponse>> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletRequest servletRequest
            ) {
        CurrentUserResponse data = authFacade.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(data, TraceIdContext.get(servletRequest)));
    }

    @PostMapping("/login")
    @Operation(summary = "注册普通用户")
    public ResponseEntity<ApiResponse<AuthTokenResponse>> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletRequest servletRequest
            ) {
        AuthTokenResponse data = authFacade.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(data, TraceIdContext.get(servletRequest)));
    }
}
