package io.chat.java.api.domain.chat;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.chat.java.api.domain.chat.model.ChatMessageRequest;
import io.chat.java.api.domain.chat.model.ChatMessageView;
import io.chat.java.api.domain.chat.model.ChatRoomView;
import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.UserView;
import io.chat.java.api.domain.websocket.MessageHandlerService;
import io.chat.java.api.entity.chat.*;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.entity.user.UserRepository;
import io.chat.java.api.support.ApiException;
import io.chat.java.api.support.ApiStatus;
import io.chat.java.api.util.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserService userService;
    private final MessageHandlerService messageHandlerService;

    public ChatService(ChatRoomRepository chatRoomRepository, ChatMessageRepository chatMessageRepository, UserService userService, MessageHandlerService messageHandlerService) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userService = userService;
        this.messageHandlerService = messageHandlerService;
    }

    public ChatRoomView create(Long id) {
        User user = userService.findByUserId(id);

        ChatRoom chatRoom = ChatRoom.builder()
                .createDate(LocalDateTime.now())
                .createUser(user)
                .status(ChatRoomStatus.ACTIVE)
                .users(Arrays.asList(user))
                .build();
        chatRoom = chatRoomRepository.save(chatRoom);


        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sendTime(LocalDateTime.now())
                .sendUser(user)
                .context(textMessage(String.format("[%s] ?????? ??????????????????.", user.getName())))
                .build();
        chatMessageRepository.save(message);

        return chatRoom.convertChatRoomView();
    }

    @Transactional
    public List<ChatRoomView> findChatList() {
        return chatRoomRepository.findChatRoomByStatus(ChatRoomStatus.ACTIVE)
                .orElseGet(ArrayList::new)
                .stream()
                .map(ChatRoom::convertChatRoomView)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ChatMessageView> open(Long chatRoomId, Long userId) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(chatRoomId)
                .orElseThrow(() -> new ApiException(ApiStatus.CHAT_ROOM_NOT_FOUND));

        boolean isMappingUser = chatRoom.getUsers().stream()
                .filter(user -> user.getId().equals(userId))
                .findAny()
                .isPresent();

        if (isMappingUser) {
            return chatRoom.getChatMessages()
                    .stream()
                    .map(ChatMessage::toChatMessage)
                    .collect(Collectors.toList());
        } else {
            List<User> users = chatRoom.getUsers();

            User user = userService.findByUserId(userId);
            users.add(user);
            chatRoom.setUsers(users);

            ChatMessage message = ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .sendTime(LocalDateTime.now())
                    .sendUser(user)
                    .context(textMessage(String.format("[%s] ?????? ??????????????????.", user.getName())))
                    .build();
            chatMessageRepository.save(message);

            return chatRoom.getChatMessages()
                    .stream()
                    .map(ChatMessage::toChatMessage)
                    .collect(Collectors.toList());
        }
    }

    public void send(ChatMessageRequest request) {
        ChatRoom chatRoom = chatRoomRepository.findChatRoomById(request.getChatRoomId())
                .orElseThrow(() -> new ApiException(ApiStatus.CHAT_ROOM_NOT_FOUND));
        User user = userService.findByUserId(request.getUserId());

        ChatMessage message = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sendTime(LocalDateTime.now())
                .sendUser(user)
                .context(textMessage(request.getText()))
                .build();

        ChatMessageView cmv = chatMessageRepository.save(message).toChatMessage();

        messageHandlerService.messageSend(chatRoom.getId(), cmv);

        List<Long> userIds = chatRoom.getUsers()
                .stream()
                .map(User::getId)
                .collect(Collectors.toList());

        messageHandlerService.userMessageSend(userIds, cmv);
    }

    private String textMessage (String txt) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", txt);
        jsonObject.addProperty("type", "TEXT");

        return JsonHelper.toJson(jsonObject);
    }
}
