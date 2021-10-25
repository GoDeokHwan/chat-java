package io.chat.java.api.config;

import io.chat.java.api.support.ApiException;
import io.chat.java.api.support.ApiResult;
import io.chat.java.api.support.ApiStatus;
import io.chat.java.api.support.ApiStatusResponsible;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ApiResult handleException(HttpServletRequest req, Exception e) {
        log.info("requestURL :: {}", req.getRequestURL());
        if (e instanceof ApiException) {
            ApiStatusResponsible responsible = ((ApiException) e).getApiStatus();
            if (!responsible.isShort()) {
                log.error(e.getMessage(), e);
            } else {
                log.info("{}", e.getMessage());
            }
            return ApiResult.of(((ApiException) e).getApiStatus());
        } else if (e instanceof HttpMediaTypeNotAcceptableException) {
            log.info("웹소캣에 의한 예외 발생");
            return ApiResult.of(ApiStatus.SUCCESS);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return ApiResult.of(ApiStatus.REQUEST_METHOD_NOT_SUPPORTED);
        } else if (e instanceof HttpMessageNotReadableException) {
            log.info("HttpMessageNotReadableException 예외 발생.");
            return ApiResult.of(ApiStatus.BAD_REQUEST_ERROR);
        } else if (e instanceof MissingServletRequestParameterException) {
            log.info("필수 파라미터가 없어서 예외 발생.");
            return ApiResult.of(ApiStatus.MISSING_REQUEST_PARAMETER);
        } else if (e instanceof MethodArgumentNotValidException) {
            return ApiResult.of(e.getMessage());
        }
        log.error(e.getMessage(), e);
        return ApiResult.of(ApiStatus.ERROR);
    }

}
