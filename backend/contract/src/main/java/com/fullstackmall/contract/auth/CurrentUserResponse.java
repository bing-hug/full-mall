package com.fullstackmall.contract.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserResponse {
    /** 用户主键，由数据库生成，也是后端判断数据归属时使用的可信身份标识 */
    private Long id;
    /** 登录用户名， 来源于数据库 */
    private String username;
    /** 用户展示昵称， 可以用于页面展示 */
    private String nickname;
    /** 当前用户角色，由服务端根据根据数据库角色码转换，前端不能通过请求自行提高权限 */
    private UserRole role;
}
