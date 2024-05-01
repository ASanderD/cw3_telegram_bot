package pro.sky.telegrambot.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class NotificationTaskService {
    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    @Transactional
    public void save(String message, Long chatId, LocalDateTime notificationDateTime) {
        NotificationTask notificationTask = new NotificationTask();
        notificationTask.setNotificationDateTime(notificationDateTime.truncatedTo(ChronoUnit.MINUTES));
        notificationTask.setMessage(message);
        notificationTask.setUserId(chatId);
        notificationTaskRepository.save(notificationTask);
    }

    @Transactional (readOnly = true)
    public List<NotificationTask> findTaskForNotifying() {
        return notificationTaskRepository.findALlByNotificationDateTime(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
    }
}
