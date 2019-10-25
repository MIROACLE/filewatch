package com.watch.aiface.task;

import com.watch.aiface.base.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.ParseException;
import java.util.Date;

@Configuration
@EnableScheduling
public class DeleteFtpOneDayAgoTask {
    private static Logger logger = LoggerFactory.getLogger(DeleteFtpOneDayAgoTask.class);

    @Value("${DestPath}")
    private String BASE_DIR;

    @Scheduled(cron = "0 0 3 * * ?")
    public void deleteFtpOneDayAgo() {
        try {
            Files.walkFileTree(Paths.get(BASE_DIR)
                    , new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        // 在访问文件时触发该方法
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            String[] str1 = file.toString().split("_");
                            Date fileDate = DateUtil.str2Date(str1[2], DateUtil.FORMAT_YMDHMSS);
                            try {
                                Date sevenDaysAgo = DateUtil.str2Date(DateUtil.getStatetime(-1), DateUtil.FORMAT_Y_M_D);
                                // 在1天前
                                if (fileDate.before(sevenDaysAgo)) {
                                    logger.info("删除图片：{}", file.toString());
                                    Files.deleteIfExists(file);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            return FileVisitResult.CONTINUE;
                        }

                        // 在访问失败时触发该方法
                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        // 在访问目录之后触发该方法
                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
