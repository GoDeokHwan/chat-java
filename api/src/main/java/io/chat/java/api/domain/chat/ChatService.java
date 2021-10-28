package io.chat.java.api.domain.chat;

import io.chat.java.api.domain.chat.model.ChatRoomView;
import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.UserView;
import io.chat.java.api.entity.chat.*;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, UserService userService) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
    }

    public ChatRoomView create(Long id) {
        User user = userService.findByUserId(id);

        ChatRoom chatRoom = ChatRoom.builder()
                .createDate(LocalDateTime.now())
                .createUser(user)
                .status(ChatRoomStatus.ACTIVE)
                .users(Arrays.asList(user))
                .build();

        return chatRoomRepository.save(chatRoom).convertChatRoomView();
    }

    @Transactional
    public List<ChatRoomView> listByUserId(Long id) {
        User user = userService.findByUserId(id);
        List<ChatRoom> chatRooms = user.getChatRooms();

        return chatRooms
                .stream()
                .map(ChatRoom::convertChatRoomView)
                .collect(Collectors.toList());
    }

}
