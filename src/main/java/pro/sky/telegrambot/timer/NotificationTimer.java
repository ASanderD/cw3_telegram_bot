package pro.sky.telegrambot.timer;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pro.sky.telegrambot.service.NotificationTaskService;
import pro.sky.telegrambot.service.TelegramBotClient;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationTimer {
    private final NotificationTaskService notificationTaskService;
    private final TelegramBotClient telegramBotClient;

    public NotificationTimer(NotificationTaskService notificationTaskService,
                             TelegramBotClient telegramBotClient) {
        this.notificationTaskService = notificationTaskService;
        this.telegramBotClient = telegramBotClient;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void task() {
        notificationTaskService.findTaskForNotifying().forEach(notificationTask -> {
            telegramBotClient.sendMessage(
                    notificationTask.getUserId(),
                            """
                Сообщение: %s
                            """.formatted(notificationTask.getMessage()));
        });
    }
}
