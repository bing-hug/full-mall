package com.fullstackmall.contract.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[A-Za-z0-9_]{4, 30}$", message = "用户名只能包含字母、数字、下划线， 长度为4~30")
    private String username;

    @NotBlank(message = "昵称不能为空")
    @Size(min = 2, max = 30, message = "昵称长度必须在2 ~ 30 个字符")
    private String nickname;

    @NotBlank(message = "密码不能为空")
    @Size(min = 8, max = 64, message = "密码长度必须在8 ~ 64个字符")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "密码必须包含密码和数字")
    private String password;

}
