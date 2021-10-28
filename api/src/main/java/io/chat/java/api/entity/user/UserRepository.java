package io.chat.java.api.entity.user;

import io.chat.java.api.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLoginId(String loginId);
    Optional<User> findUserById(Long id);

}
