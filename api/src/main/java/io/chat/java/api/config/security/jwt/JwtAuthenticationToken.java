package io.chat.java.api.config.security.jwt;

import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = 2877954820905567501L;

    private String token;
    private AuthenticationUserDetails authenticationUserDetails;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, AuthenticationUserDetails authenticationUserDetails) {
        super(authorities);
        this.authenticationUserDetails = authenticationUserDetails;
        this.eraseCredentials();
        super.setAuthenticated(true);
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        if (authenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.authenticationUserDetails;
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.token = null;
    }
}
