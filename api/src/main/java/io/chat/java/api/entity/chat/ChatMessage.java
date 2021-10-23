package io.chat.java.api.entity.chat;

import io.chat.java.api.entity.user.User;
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

}
