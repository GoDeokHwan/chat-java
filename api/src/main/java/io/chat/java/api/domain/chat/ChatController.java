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

    @GetMapping("/v1/chat")
    public ApiResult findChatList() {
        return ApiResult.of(ApiStatus.SUCCESS, chatService.findChatList());
    }

    @PostMapping("/v1/chat")
    public ApiResult create(
            @RequestBody ChatRoomCreateRequest request
            ) {
        return ApiResult.of(ApiStatus.SUCCESS, chatService.create(request.getUserId()));
    }

    @GetMapping("/v1/chat/{chatRoomId}")
    public ApiResult open(
            @PathVariable(name = "chatRoomId") Long chatRoomId,
            @RequestParam(name = "userId") Long userId
    ) {
        return ApiResult.of(ApiStatus.SUCCESS, chatService.open(chatRoomId, userId));
    }

}
