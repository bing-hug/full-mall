package com.fullstackmall.contract.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSummaryResponse {
    private long id;
    private String username;
    private String nickname;
    private Integer status;
    private LocalDateTime createdAt;
}
