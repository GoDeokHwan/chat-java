package io.chat.java.api.config.security.ajax;

import io.chat.java.api.config.security.jwt.JwtUtil;
import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import io.chat.java.api.util.JsonHelper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AjaxAuthenticationSuccessHandler(JwtUtil jwtUtil, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        AuthenticationUserDetails authenticationUserDetails = (AuthenticationUserDetails) authentication.getPrincipal();
        String token = jwtUtil.createJwtToken(authenticationUserDetails);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(Charset.forName("UTF-8").displayName());
        response.getWriter().write(JsonHelper.toJson(tokenMap));

        userService.saveToken(jwtUtil.extractLoginId(token), token);

        clearAuthenticationAttributes(request);
    }

    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
