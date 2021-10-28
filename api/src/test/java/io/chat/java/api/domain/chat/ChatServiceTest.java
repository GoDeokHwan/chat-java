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


import javax.transaction.Transactional;
import java.util.List;

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
        assertThat(chatRoomView.getMappingUsers().size()).isEqualTo(1);
    }


    @Test
    @Transactional
    public void 사용자_채팅_리스트 () throws Exception {
        // given
        Long userId = 1l;

        // when
        List<ChatRoomView> chatRoomViews = chatService.listByUserId(userId);

        // then
        assertThat(chatRoomViews.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void 채팅_상세내용() throws Exception {

    }

}