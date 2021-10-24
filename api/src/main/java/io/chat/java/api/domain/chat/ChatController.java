package io.chat.java.api.domain.chat;

import io.chat.java.api.support.ApiResult;
import io.chat.java.api.support.ApiStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/v1/chat/{id}")
    public ApiResult findChatList(
            @PathVariable(name = "id") Long id
    ) {
        return ApiResult.of(ApiStatus.SUCCESS);
    }

}
