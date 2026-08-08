package com.fullstackmall.contract.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(max = 30, message = "用户名不能超过30个字符")
    private String username;

    @NotBlank
    @Size(max = 64, message = "密码不能超过64个字符")
    private String password;
}
