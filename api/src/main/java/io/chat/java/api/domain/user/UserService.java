package io.chat.java.api.domain.user;

import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import io.chat.java.api.domain.user.model.UserRequest;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import io.chat.java.api.support.ApiException;
import io.chat.java.api.support.ApiStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@Transactional
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

        if (userRepository.findUserByLoginId(request.getLoginId()).isPresent()) {
            log.error("이미 존재하는 ID");
            return;
        }
        userRepository.save(user);
    }

    public AuthenticationUserDetails loadUserDetails(String loginId, String password) {
        User user = userRepository.findUserByLoginId(loginId).orElseGet(User::new);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return AuthenticationUserDetails.builder()
                    .loginId(user.getLoginId())
                    .name(user.getName())
                    .id(user.getId())
                    .build();
        } else {
            throw new ApiException(ApiStatus.AUTHENTICATION);
        }
    }

    public void saveToken(String loginId, String token) {
        User user = userRepository.findUserByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ApiStatus.USER_NOT_FOUND));

        user.setToken(token);
        userRepository.save(user);
    }

    public boolean validTokenKey(String loginId, String token) {
        User user = userRepository.findUserByLoginId(loginId)
                .orElseThrow(() -> new ApiException(ApiStatus.USER_NOT_FOUND));

        return token.equals(user.getToken());
    }

    public User findByUserId(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new ApiException(ApiStatus.USER_NOT_FOUND));
    }


}
