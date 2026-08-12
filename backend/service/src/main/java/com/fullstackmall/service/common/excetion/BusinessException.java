package com.fullstackmall.service.common.excetion;


import com.fullstackmall.contract.common.ApiCode;
import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {
    /** 业务错误码*/
    private final ApiCode code;

    /** http错误码 */
    private final HttpStatus httpStatus;

    public BusinessException(ApiCode code, String message) {
        this(code, message, HttpStatus.CONFLICT);
    }

    public BusinessException(ApiCode code, String message, HttpStatus httpStatus) {
        super(message);
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public ApiCode getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
