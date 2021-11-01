package io.chat.java.api.domain.chat;

import io.chat.java.api.config.ChatComponentTest;
import io.chat.java.api.config.security.jwt.JwtPropertiesLoader;
import io.chat.java.api.config.security.jwt.JwtUtil;
import io.chat.java.api.domain.user.UserService;
import io.chat.java.api.domain.user.model.AuthenticationUserDetails;
import io.chat.java.api.util.JsonHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ChatComponentTest
@ActiveProfiles("memory")
@Transactional
class ChatControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserService userService;

    private String token;

    @BeforeEach
    public void before() {
        AuthenticationUserDetails details = AuthenticationUserDetails.builder()
                .loginId("user01")
                .name("유저01")
                .build();

        Claims claims = Jwts.claims().setSubject(details.getLoginId());
        claims.put("details", JsonHelper.toJson(details));

        token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, "@chat-java-secret")
                .compact();
        userService.saveToken(details.getLoginId(), token);
    }

    @Test
    public void 사용자_채팅_리스트() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/chat")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        ;
    }

    @Test
    public void 사용자_채팅_생성 () throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/chat")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Authorization", "Bearer " + token)
                .content("{\"userId\": 1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        ;
    }

    @Test
    public void 채팅방_오픈 () throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/chat/1")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Authorization", "Bearer " + token)
                .content("{\"userId\": 1}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        ;
    }

    @Test
    public void 채팅_메시지 () throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/v1/chat/1")
                .header("X-Requested-With", "XMLHttpRequest")
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .content("{\"userId\": 1,\"text\":\"메시지가 왔어요 메시지가\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
        ;
    }

}