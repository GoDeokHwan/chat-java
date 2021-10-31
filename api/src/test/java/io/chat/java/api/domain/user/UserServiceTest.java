package io.chat.java.api.domain.user;

import io.chat.java.api.domain.user.model.UserRequest;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Test
    public void 상담사_조회() {
        Optional<User> user = userRepository.findById(1l);
    }

    @Test
    public void 상담사_생성 () {
        // given
        UserRequest request = new UserRequest();
        request.setLoginId("user02");
        request.setPassword("1234");
        request.setName("사용자2");

        // when
        userService.save(request);
        Optional<User> user = userRepository.findUserByLoginId(request.getLoginId());

        // then
        assertThat(user.get().getLoginId()).isEqualTo(request.getLoginId());
    }

}