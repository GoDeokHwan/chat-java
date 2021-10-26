package io.chat.java.api.domain.chat;

import io.chat.java.api.config.ChatComponentTest;
import io.chat.java.api.domain.chat.model.ChatRoomView;
import io.chat.java.api.entity.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ChatComponentTest
@ActiveProfiles("memory")
@Transactional
class ChatServiceTest {

    @Autowired
    private ChatService chatService;
    @Autowired
    private UserRepository userRepository;
//
//    @Autowired
//    public ChatServiceTest(ChatService chatService, UserRepository userRepository) {
//        this.chatService = chatService;
//        this.userRepository = userRepository;
//    }

    @Test
    public void 사용자_채팅_추가 () {
        // given
        Long userId = 1l;

        // when
        ChatRoomView chatRoomView = chatService.create(userId);

        // then
        assertThat(chatRoomView.getUserView().getId()).isEqualTo(userId);
    }


    @Test
    public void 사용자_채팅_리스트 () throws Exception {

    }

    @Test
    public void 채팅_상세내용() throws Exception {

    }

}