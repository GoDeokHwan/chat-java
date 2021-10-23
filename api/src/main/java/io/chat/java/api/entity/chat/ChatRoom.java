package io.chat.java.api.entity.chat;


import io.chat.java.api.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
}
