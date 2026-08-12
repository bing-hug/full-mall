package com.fullstackmall.service.common.excetion;

import com.fullstackmall.contract.common.ApiResponse;
import com.fullstackmall.service.common.trace.TraceIdContext;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(
            BusinessException exception,
            HttpServletRequest request
    ) {
        ApiResponse<Void> response = ApiResponse.error(
                exception.getCode(),
                exception.getMessage(),
                null,
                TraceIdContext.get(request)
        );
        return ResponseEntity.status(exception.getHttpStatus()).body(response);
    }
}
