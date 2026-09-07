package com.fullstackmall.service.security;

import com.fullstackmall.contract.auth.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserPrincipal {
    private Long id;
    private String username;
    private String password;
    private UserRole role;
}
