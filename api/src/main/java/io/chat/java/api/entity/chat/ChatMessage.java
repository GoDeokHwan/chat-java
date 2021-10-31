package io.chat.java.api.entity.chat;

import io.chat.java.api.domain.chat.model.ChatMessageView;
import io.chat.java.api.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name= "chat_message", schema = "chatdb")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    private LocalDateTime sendTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id", referencedColumnName = "id")
    private User sendUser;

    @Column
    private String context;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "id")
    private ChatRoom chatRoom;

    @Builder
    public ChatMessage(Long id, LocalDateTime sendTime, User sendUser, String context, ChatRoom chatRoom) {
        this.id = id;
        this.sendTime = sendTime;
        this.sendUser = sendUser;
        this.context = context;
        this.chatRoom = chatRoom;
    }

    public ChatMessageView toChatMessage() {
        return ChatMessageView.builder()
                .id(id)
                .sendTime(sendTime)
                .chatRoom(chatRoom.convertChatRoomView())
                .context(context)
                .sendUser(sendUser.convertUserView())
                .build();
    }
}
