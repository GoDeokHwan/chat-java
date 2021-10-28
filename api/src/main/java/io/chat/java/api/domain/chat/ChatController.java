package io.chat.java.api.domain.chat;

import io.chat.java.api.domain.chat.model.ChatRoomCreateRequest;
import io.chat.java.api.support.ApiResult;
import io.chat.java.api.support.ApiStatus;
import org.springframework.web.bind.annotation.*;

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
        return ApiResult.of(ApiStatus.SUCCESS, chatService.listByUserId(id));
    }

    @PostMapping("/v1/chat")
    public ApiResult create(
            @RequestBody ChatRoomCreateRequest request
            ) {
        return ApiResult.of(ApiStatus.SUCCESS, chatService.create(request.getUserId()));
    }

}
