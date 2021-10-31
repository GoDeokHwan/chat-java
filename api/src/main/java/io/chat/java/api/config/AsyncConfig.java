package io.chat.java.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Value(value = "${chat.thread.corePoolSize}")
    private Integer corePoolSize;
    @Value(value = "${chat.thread.maxPoolSize}")
    private Integer maxPoolSize;
    @Value(value = "${chat.thread.queueCapacity}")
    private Integer queueCapacity;
    @Value(value = "${chat.thread.threadName}")
    private String threadName;

    public Executor asyncThreadPool() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();

        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setThreadNamePrefix(threadName);
        taskExecutor.setDaemon(true);
        taskExecutor.initialize();

        return taskExecutor;
    }

}
