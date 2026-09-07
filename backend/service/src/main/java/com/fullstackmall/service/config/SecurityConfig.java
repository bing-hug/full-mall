package com.fullstackmall.service.config;

import com.fullstackmall.service.security.JwtAuthenticationFilter;
import com.fullstackmall.service.security.RestAccessDeniedHandler;
import com.fullstackmall.service.security.RestAuthenticationEntryPoint;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 安全配置：目前只提供密码编码器，后续 JWT 过滤器、放行规则在这里扩展。
 */
@Configuration
public class SecurityConfig {

    @Resource
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Resource
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .sessionManagement(sessionManagement -> sessionManagement.disable())
                .logout(logout -> logout.disable())
                .httpBasic(httpBasicAuth -> httpBasicAuth.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(restAuthenticationEntryPoint).accessDeniedHandler(restAccessDeniedHandler))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(
                        "/api/health",
                        "/api/auth/register",
                        "/api/categories/validate",
                        "/api/products",
                        "/api/products/**",
                        "/api/mock-payments/callback",
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/error"
                ).permitAll().requestMatchers(HttpMethod.OPTIONS, "/**").permitAll().requestMatchers("/api/users/**", "/api/admin/**").permitAll().requestMatchers("/api/**").authenticated().anyRequest().permitAll()
                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    /**
     * 需要什么
     * 1. 禁用csrc
     * 2. 放行cors
     * 3. 禁用表单登录
     * 4. 禁用会话
     * 5. 禁用会话登出
     * 6. http Basic(不使用基础登录，使用jwt)
     * 7. 放行path
     * 8. 添加JWT过滤器(顺序
     */

    /**
     * 只允许本地前端开发服务器跨域访问，并显示声明可用请求头
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Trace-Id", "Idempotency-Key", "X-Mock-Payment-Secret"));
        configuration.setExposedHeaders(List.of("X-Trace-Id"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


