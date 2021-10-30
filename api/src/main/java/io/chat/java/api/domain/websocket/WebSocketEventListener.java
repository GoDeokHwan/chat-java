package io.chat.java.api.support.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.messaging.support.NativeMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        MessageHeaderAccessor accessor = NativeMessageHeaderAccessor.getAccessor(event.getMessage(), SimpMessageHeaderAccessor.class);
        GenericMessage genericMessage = (GenericMessage) accessor.getHeader("simpConnectMessage");
        Map nativeHeader = (Map) genericMessage.getHeaders().get("nativeHeaders");
        Long chatRoomId = (Long) ((List) nativeHeader.get("chatRoomId")).get(0);
        Long userId = (Long)((List) nativeHeader.get("userId")).get(0);
        String sessionId = (String) genericMessage.getHeaders().get("simpSessionId");

        log.info("[Connected] room id : {} | user id : {} | websocket session id : {}", chatRoomId, userId, sessionId);

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String sessionId = headerAccessor.getSessionId();

        log.info("[Disconnected] websocket session id : {}", sessionId);
    }

}
