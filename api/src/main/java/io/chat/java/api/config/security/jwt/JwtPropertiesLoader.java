package io.chat.java.api.config.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "chat.jwt")
public class JwtPropertiesLoader {

    private String header;
    private String secret;
    private String expiration;
    private String authPath;

}
