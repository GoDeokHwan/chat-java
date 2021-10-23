package io.chat.java.api.config;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "chat.db")
public class PropertiesLoader {

    @NotNull
    private Properties hikari;

    @NotNull
    private Properties jpa;

    @NotNull
    private Properties transction;

}
