package com.watch.aiface.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池的配置--用于异步任务
 *
 */
@Configuration
@Component
@ConfigurationProperties(prefix = "thread.executor")
public class ThreadExecutorConfig {
    private int corePoolSize = 30;// 线程池维护线程的最少数量
    private int maxPoolSize = 60;// 线程池维护线程的最大数量
    private int queueCapacity = 8; // 缓存队列
    private int keepAlive = 60;// 允许的空闲时间

    private final String THREAD_NAME = "JpathWatchExecutor-";

    @Bean
    public Executor laputaExecutor(ThreadExecutorConfig executorConfig) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(executorConfig.getCorePoolSize());
        executor.setMaxPoolSize(executorConfig.getMaxPoolSize());
        executor.setQueueCapacity(executorConfig.getQueueCapacity());
        executor.setKeepAliveSeconds(executorConfig.getKeepAlive());

        executor.setThreadNamePrefix(THREAD_NAME);
        // 对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setAllowCoreThreadTimeOut(true);
        executor.initialize();
        return executor;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

}
