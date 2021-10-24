package io.chat.java.api.domain.user.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserRequest {

    @NotNull
    private String loginId;
    @NotNull
    private String password;
    @NotNull
    private String name;

}
