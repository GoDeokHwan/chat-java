package io.chat.java.api.domain.chat.model;

import io.chat.java.api.domain.user.model.UserView;
import io.chat.java.api.entity.chat.ChatRoom;
import io.chat.java.api.entity.user.User;
import io.chat.java.api.util.DateUtil;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessageView {
    private Long id;
    private String sendTime;
    private UserView sendUser;
    private String context;
    private ChatRoomView chatRoom;

    @Builder
    public ChatMessageView(Long id, LocalDateTime sendTime, UserView sendUser, String context, ChatRoomView chatRoom) {
        this.id = id;
        this.sendTime = DateUtil.toYYYYMMddHHmmss(sendTime);
        this.sendUser = sendUser;
        this.context = context;
        this.chatRoom = chatRoom;
    }
}
