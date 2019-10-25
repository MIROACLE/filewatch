package com.watch.aiface.config;

import com.watch.aiface.base.watch.RxFileWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class RxFileWatcherConfig implements ApplicationRunner, Ordered {
    private static Logger logger = LoggerFactory.getLogger(RxFileWatcherConfig.class);

    @Autowired
    private RxFileWatcher rxFileWatcher;

    @Value("${WatchPath}")
    private String WatchPath;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.info("初始化");
        File[] files = new File(WatchPath).listFiles();
        if (files == null) {
            logger.info("错误！当前监听目录下没有子目录！");
            return;
        }
        rxFileWatcher.run(WatchPath);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
