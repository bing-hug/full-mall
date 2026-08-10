package com.fullstackmall.contract.user;

import com.fullstackmall.contract.common.PageResponse;

public interface IUserFacade {
    PageResponse<UserSummaryResponse> queryUsers(UserQueryRequest request);
}
