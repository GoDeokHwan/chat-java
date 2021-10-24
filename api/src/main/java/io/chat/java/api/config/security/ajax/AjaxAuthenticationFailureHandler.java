package io.chat.java.api.config.security.ajax;

import com.google.common.collect.Lists;
import io.chat.java.api.config.security.exception.AlreadyLoginException;
import io.chat.java.api.support.ApiResult;
import io.chat.java.api.support.ApiStatus;
import io.chat.java.api.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Charset.forName("UTF-8").displayName());

        log.info("{}", e.getMessage());
        if (e instanceof BadCredentialsException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION)));
        } else if (e instanceof AuthenticationException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.JWT_TOKEN_EXPIRED)));
        } else if (e instanceof AuthenticationServiceException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION)));
        } else if (e instanceof InsufficientAuthenticationException) {
            ApiStatus apiStatus = ApiStatus.valueOf(e.getMessage());
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(apiStatus)));
        } else if (e instanceof DisabledException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.
                    DUPLICATION_ACCESS_EXCEPTION, Lists.newArrayList(Pair.of("tokenKey", e.getMessage())))));
        } else if (e instanceof AlreadyLoginException) {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.DUPLICATION_LOGIN_EXCEPTION)));
        } else {
            response.getWriter().write(JsonHelper.toJson(ApiResult.of(ApiStatus.AUTHENTICATION_FAILED)));
        }
    }
}
