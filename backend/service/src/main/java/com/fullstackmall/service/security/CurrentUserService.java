package com.fullstackmall.service.security;
import com.fullstackmall.contract.common.ApiCode;
import com.fullstackmall.service.common.excetion.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 从 Spring SecurityContext 读取可信当前用户
 */

public class CurrentUserService {
    public CurrentUserPrincipal requireCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof CurrentUserPrincipal principal)) {
            throw new BusinessException(
                    ApiCode.UNAUTHORIZED,
                    ApiCode.UNAUTHORIZED.defaultMessage(),
                    HttpStatus.UNAUTHORIZED
            );

        }
        return principal;
    }

    public Long requireCurrentUserId() {
        return requireCurrentUser().getId();
    }
}
