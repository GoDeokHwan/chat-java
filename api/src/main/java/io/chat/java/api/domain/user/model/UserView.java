package io.chat.java.api.domain.user.model;

import io.chat.java.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserView {

    private Long id;
    private String name;

    @Builder
    public UserView(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
