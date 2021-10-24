package io.chat.java.api.entity.user;

import io.chat.java.api.entity.chat.ChatMessage;
import io.chat.java.api.entity.chat.ChatRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name= "user", schema = "chatdb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String loginId;
    @Column
    private String password;
    @Column
    private String token;
    @Column
    private String name;

    @ManyToMany
    @JoinTable(name = "chat_room_user_mapping", schema = "chatdb")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<ChatMessage> chatMessages = new ArrayList<>();

    @Builder
    public User(Long id, String loginId, String password, String token, String name) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.token = token;
        this.name = name;
    }
}
