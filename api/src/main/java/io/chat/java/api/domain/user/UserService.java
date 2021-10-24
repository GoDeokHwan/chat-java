package io.chat.java.api.domain.user;

import io.chat.java.api.domain.user.model.UserRequest;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserService {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserRequest request) {
        User user = User.builder()
                .loginId(request.getLoginId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        if (userRepository.findByLoginId(request.getLoginId()).isPresent()) {
            log.error("이미 존재하는 ID");
            return;
        }
        userRepository.save(user);
    }

}
