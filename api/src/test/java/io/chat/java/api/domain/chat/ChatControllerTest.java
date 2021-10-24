package io.chat.java.api.domain.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ChatControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void findChatList() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/chat/1")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMDMiLCJkZXRhaWxzIjoie1wibG9naW5JZFwiOlwidXNlcjAzXCIsXCJuYW1lXCI6XCLsgqzsmqnsnpAzXCJ9IiwiaWF0IjoxNjM1MDY2OTM0LCJleHAiOjE2MzUxNTMzMzR9.VZi2xx3-fQidIQIhQjs-QSG90EO63MRIkOKbwWjtMCQ")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        ;
    }
}