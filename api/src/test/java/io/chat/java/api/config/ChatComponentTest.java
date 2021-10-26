package io.chat.java.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TestRepository
@ComponentScan(basePackages = {"io.chat.java.api.entity"})
@Import({WebConfig.class})
public @interface ChatComponentTest {
}
