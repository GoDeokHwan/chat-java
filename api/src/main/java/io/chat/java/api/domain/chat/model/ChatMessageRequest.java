package io.chat.java.api.domain.chat.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageRequest {
    private Long userId;
    private Long chatRoomId;
    private String text;

    @Builder
    public ChatMessageRequest(Long userId, Long chatRoomId, String text) {
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.text = text;
    }
}
