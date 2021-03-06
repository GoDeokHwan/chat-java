package io.chat.java.api.domain.chat.model;

import io.chat.java.api.domain.user.model.UserView;
import io.chat.java.api.entity.chat.ChatRoomStatus;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ChatRoomView {

    private Long chatRoomId;
    private UserView userView;
    private String createDate;
    private ChatRoomStatus chatRoomStatus;
    private List<UserView> mappingUsers;
    private String lastMessage;
    private String lastMessageTime;

    @Builder
    public ChatRoomView(Long chatRoomId, UserView userView, LocalDateTime createDate, ChatRoomStatus chatRoomStatus, List<UserView> mappingUsers, String lastMessage, LocalDateTime lastMessageTime) {
        this.chatRoomId = chatRoomId;
        this.userView = userView;
        this.createDate = DateUtil.toYYYYMMddHHmmss(createDate);
        this.chatRoomStatus = chatRoomStatus;
        this.mappingUsers = mappingUsers;
        this.lastMessage = lastMessage;
        this.lastMessageTime = DateUtil.toYYYYMMddHHmmss(lastMessageTime);
    }
}
