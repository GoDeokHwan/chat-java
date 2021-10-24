package io.chat.java.api.domain.user.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String loginId;
    private String password;

}
