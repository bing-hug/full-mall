package com.fullstackmall.service.auth;

import com.fullstackmall.contract.auth.*;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.service.common.excetion.BusinessException;
import com.fullstackmall.service.user.entity.UserEntity;
import com.fullstackmall.service.user.service.UserDbService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Locale;

@Service
public class AuthService {
    private static final int USER_ENABLED = 1;

    @Resource
    private UserDbService userDbService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private JwtTokenService jwtTokenService;

    public CurrentUserResponse register(RegisterRequest request) {
        String username = normalizeUsername(request.getUsername());

        if (userDbService.usernameExists(username)) {
            throw new BusinessException(ApiCode.USERNAME_ALREADY_EXISTS, ApiCode.USERNAME_ALREADY_EXISTS.defaultMessage());
        }

        LocalDateTime now = LocalDateTime.now();
        UserEntity user =  new UserEntity();
        user.setUsername(username);
        user.setNickname(request.getNickname().trim());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRoleCode(UserRole.USER.name());
        user.setStatus(USER_ENABLED);
        user.setCreatedAt(now);
        user.setUpdatedAt(now);

        try {
            userDbService.save(user);
        } catch (DuplicateKeyException exception) {
            throw new BusinessException(ApiCode.USERNAME_ALREADY_EXISTS, ApiCode.USERNAME_ALREADY_EXISTS.defaultMessage());
        }
        return toResponse(user);
    }

    public AuthTokenResponse login(LoginRequest request) {
        System.out.println(request);
        if (StringUtils.isBlank(request.getUsername()) || StringUtils.isBlank(request.getPassword())) {
            throw new BusinessException(ApiCode.INVALID_CREDENTIALS, ApiCode.INVALID_CREDENTIALS.defaultMessage());
        }

        String username =  normalizeUsername(request.getUsername());
        UserEntity user = userDbService.findByUsername(username).orElseThrow(() -> new BusinessException(ApiCode.INVALID_CREDENTIALS, ApiCode.INVALID_CREDENTIALS.defaultMessage()));

        // 密码
        if(!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ApiCode.INVALID_CREDENTIALS, ApiCode.INVALID_CREDENTIALS.defaultMessage());
        }

        // 状态
        if (!user.getStatus().equals(USER_ENABLED)) {
            throw new BusinessException(ApiCode.ACCOUNT_DISABLED, ApiCode.ACCOUNT_DISABLED.defaultMessage());
        }

        AccessToken token = jwtTokenService.createAccessToken(user);
        return new AuthTokenResponse(
                token.getValue(),
                "Bearer",
                token.getExpiresInSeconds(),
                toResponse(user)
        );
    }

    private CurrentUserResponse toResponse(UserEntity user) {
        return new CurrentUserResponse(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                UserRole.fromCode(user.getRoleCode())
        );
    }

    private String normalizeUsername(String username) {
        return username.trim().toLowerCase(Locale.ROOT);
    }
}
