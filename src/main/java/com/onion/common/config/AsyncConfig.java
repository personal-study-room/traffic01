package com.onion.common.config;


import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * 비동기 서비스는 다음으로...
 */

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncUncaughtExceptionHandler();
    }

    @Bean(name = "defaultAsyncTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setThreadNamePrefix("async-");
        return executor;
    }

    @Slf4j
    private static class CustomAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {

        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            String paramsString = Arrays.stream(params).reduce("", (p1, p2) -> {
                StringBuffer sb = new StringBuffer();
                sb.append(p1).append(", ").append(p2.toString());
                return sb.toString();
            }).toString();
            log.error("method name={}  parameters={}  AsyncException message={}", method.getName(), paramsString,
                    ex.getMessage());
        }
    }

    // 비동기에서 context가 필요한 경우 세팅을 위해서 만들어 놓음
    private static class AsyncContextCopyDecorator implements TaskDecorator {

        @Override
        public Runnable decorate(Runnable runnable) {
            SecurityContext context = SecurityContextHolder.getContext();

            return () -> {
                try {
                    SecurityContextHolder.setContext(context);
                    runnable.run();
                } finally {
                    SecurityContextHolder.clearContext();
                }
            };

        }
    }
}
