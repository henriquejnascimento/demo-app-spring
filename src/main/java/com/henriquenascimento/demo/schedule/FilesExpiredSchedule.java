package com.henriquenascimento.demo.schedule;

import com.henriquenascimento.demo.properties.FileExpirationScheduleProperties;
import com.henriquenascimento.demo.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Log4j2
@RequiredArgsConstructor
public class FilesExpiredSchedule {

    private final FileExpirationScheduleProperties fileExpirationScheduleProperties;
    private final FileService fileService;

    @Scheduled(cron = "${demo-app-spring.schedule.upload-file-expiration.cron}")
    public void filesDeleteAllByCreatedAtGreaterThan() {
        if (fileExpirationScheduleProperties.isEnabled()) {
            log.debug("Starting expired files purge task...");
            StopWatch watch = new StopWatch();
            watch.start();
            fileService.purgeExpiredFiles();
            watch.stop();
            log.debug("Finished expired file purge task. Done in {}s", watch.getTotalTimeSeconds());
        }
    }

}
