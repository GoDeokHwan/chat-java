package io.chat.java.api.entity.chat;


import io.chat.java.api.domain.chat.model.ChatRoomView;
import io.chat.java.api.domain.user.model.UserView;
import io.chat.java.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name= "chat_room", schema = "chatdb")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "create_user_id")
    private User createUser;

    @Column
    private LocalDateTime createDate;

    @Column
    @Enumerated(value = EnumType.STRING)
    private ChatRoomStatus status;

    @Column
    private LocalDateTime endDate;

    @ManyToMany(mappedBy = "chatRooms")
    private List<User> mappingUsers = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chatRoom")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Builder
    public ChatRoom(Long id, User createUser, LocalDateTime createDate, ChatRoomStatus status, LocalDateTime endDate, List<User> mappingUsers, List<ChatMessage> chatMessages) {
        this.id = id;
        this.createUser = createUser;
        this.createDate = createDate;
        this.status = status;
        this.endDate = endDate;
        this.mappingUsers = mappingUsers;
        this.chatMessages = chatMessages;
    }

    public ChatRoomView convertChatRoomView() {
        ChatMessage chatMessage = (chatMessages != null && !chatMessages.isEmpty()) ? chatMessages.get(chatMessages.size()-1) : new ChatMessage();

        return ChatRoomView.builder()
                .chatRoomId(id)
                .chatRoomStatus(status)
                .userView(createUser.convertUserView())
                .createDate(createDate)
                .mappingUsers(mappingUsers.stream().map(User::convertUserView).collect(Collectors.toList()))
                .lastMessage(chatMessage.getContext())
                .lastMessageTime(chatMessage.getSendTime())
                .build();
    }
}
