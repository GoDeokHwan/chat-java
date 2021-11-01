package io.chat.java.api.domain.websocket;

import com.google.common.collect.Lists;
import io.chat.java.api.domain.chat.model.ChatMessageView;
import io.chat.java.api.util.JsonHelper;
import io.chat.java.api.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MessageHandlerService {

    private final SimpMessagingTemplate template;

    @Autowired
    public MessageHandlerService(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void messageSend(Long chatRoomId, ChatMessageView cmv) {
        String subscribeUrl = WebUtil.createRequestUrl(SubscribeUrls.CHAT_MESSAGE_SUBSCRIBE_URL.getUrl(), Lists.newArrayList(Pair.of("chatRoomId", chatRoomId)));
        log.info("publishUrl : {}, body : {}", subscribeUrl, JsonHelper.toJson(cmv));
        this.template.convertAndSend(
                subscribeUrl,
                JsonHelper.toJson(cmv)
        );
    }

    public void userMessageSend(List<Long> userIds, ChatMessageView cmv) {
        for (Long userId: userIds) {
            String subscribeUrl = WebUtil.createRequestUrl(SubscribeUrls.CHAT_USER_SUBSCRIBE_URL.getUrl(), Lists.newArrayList(Pair.of("userId", userId)));
            log.info("publishUrl : {}, body : {}", subscribeUrl, JsonHelper.toJson(cmv));
            this.template.convertAndSend(
                    subscribeUrl,
                    JsonHelper.toJson(cmv)
            );
        }
    }

}
