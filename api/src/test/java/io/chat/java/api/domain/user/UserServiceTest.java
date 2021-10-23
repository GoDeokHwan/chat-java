package io.chat.java.api.domain.user;

import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void 상담사_조회() {
        Optional<User> user = userRepository.findById(1l);

    }

}