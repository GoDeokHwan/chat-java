package io.chat.java.api.config.security.jwt;

import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import io.chat.java.api.util.JsonHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtAuthenticationProvider(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.notNull(authentication, "No authentication data provided");
        String token = (String) authentication.getCredentials();

        Claims claims = jwtUtil.extractAllClaims(token);
        AuthenticationUserDetails authenticationUserDetails = JsonHelper.fromJson(claims.get("details").toString(), AuthenticationUserDetails.class);

        if (userService.validTokenKey(authenticationUserDetails.getLoginId(), token)) {
            return new JwtAuthenticationToken(null, authenticationUserDetails);
        } else {
            throw new AuthenticationServiceException("");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
