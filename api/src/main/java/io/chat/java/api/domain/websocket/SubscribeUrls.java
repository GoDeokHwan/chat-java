package io.chat.java.api.domain.websocket;

import lombok.Getter;

@Getter
public enum SubscribeUrls {
    CHAT_MESSAGE_SUBSCRIBE_URL("채팅 메시지 발송", "/api/subscribe/message/{chatRoomId}"),
    CHAT_USER_SUBSCRIBE_URL("유저 채팅 메시지", "/api/subscribe/user/{userId}");

    private String label;
    private String url;

    SubscribeUrls(String label, String url) {
        this.label = label;
        this.url = url;
    }
}
