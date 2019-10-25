package com.watch.aiface.base.watch;


import com.watch.aiface.config.RabbitMqSender;
import de.helmbold.rxfilewatcher.PathObservables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class RxFileWatcher {

    private static Logger logger = LoggerFactory.getLogger(RxFileWatcher.class);

    @Resource
    private RabbitMqSender rabbitMqSender;

    public void run(String path) throws IOException {
        PathObservables
                .watchRecursive(Paths.get(path)).filter(event ->
                event.kind().name().equals("ENTRY_CREATE")
        ).subscribe(event -> {
            Path context = (Path) event.context();
            logger.info("监听文件：" + context.toString());
            try {
                rabbitMqSender.sendRabbitmqDirect(context);
            }catch (Exception e){
                e.printStackTrace();
                logger.error("发送消息失败！"+e.getMessage());
            }
        });

    }
}
