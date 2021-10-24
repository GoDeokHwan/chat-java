package io.chat.java.api.config.security.ajax;

import io.chat.java.api.config.security.exception.AlreadyLoginException;
import io.chat.java.api.config.security.jwt.JwtPropertiesLoader;
import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Slf4j
@Component
@Transactional
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final JwtPropertiesLoader jwtPropertiesLoader;

    private final UserService userService;

    @Autowired
    public AjaxAuthenticationProvider(JwtPropertiesLoader jwtPropertiesLoader, UserService userService) {
        this.jwtPropertiesLoader = jwtPropertiesLoader;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");

        String loginId = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        AuthenticationUserDetails userDetails = null;
        try {
            log.info("[로그인 요청] loginId : {}", loginId);
            userDetails = userService.loadUserDetails(loginId, password);
        } catch (Exception e) {
            throw new AlreadyLoginException(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
