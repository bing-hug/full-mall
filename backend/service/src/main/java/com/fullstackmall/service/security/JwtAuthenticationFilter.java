package com.fullstackmall.service.security;

import com.fullstackmall.contract.auth.UserRole;
import com.fullstackmall.service.auth.JwtTokenService;
import com.fullstackmall.service.user.entity.UserEntity;
import com.fullstackmall.service.user.service.UserDbService;
import io.jsonwebtoken.JwtException;
import jakarta.annotation.Resource;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    @Resource
    private JwtTokenService jwtTokenService;

    @Resource
    private UserDbService userDbService;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Override
    protected void doFilterInternal (
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.substring(BEARER_PREFIX.length()).trim();
            if (token.isEmpty()) {
                throw new BadCredentialsException("Bearer Token is empty");
            }

            Long userId = jwtTokenService.parseUserId(token);
            UserEntity user = userDbService.findUserById(userId).filter(candidate -> Integer.valueOf(1).equals(candidate.getStatus())).orElseThrow(()-> new BadCredentialsException("Token 对应用户不存在或已停用"));
            UserRole role = UserRole.fromCode(user.getRoleCode());

            CurrentUserPrincipal principal = new CurrentUserPrincipal(
                    user.getId(), user.getUsername(), user.getNickname(), role
            );
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    principal,
                    null,
                    List.of(new SimpleGrantedAuthority("ROLE_" + role.name()))
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException | BadCredentialsException exception) {
            SecurityContextHolder.clearContext();
            restAuthenticationEntryPoint.commence(
                    request,
                    response,
                    new BadCredentialsException ("Access Token 无效", exception)
            );

        }
    }
}
