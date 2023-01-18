package com.simplesystem.todoitems.job;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class StatusUpdateJob {

    @Autowired
    private TodoRepository todoRepository;

    @Scheduled(cron = "0 */1 * ? * *")
    public void updateExpiredItems() {
        log.info("{} in progress", getClass().getSimpleName());

        List<Todo> expiredItems = todoRepository.findByDueDateBeforeAndStatusNot(LocalDateTime.now(), TodoStatus.PAST_DUE);
        expiredItems.forEach(item -> item.setStatus(TodoStatus.PAST_DUE));

        todoRepository.saveAll(expiredItems);

        log.info("{} items updated with PAST_DUE status", expiredItems.stream().count());
        log.info("{} successfully completed.", getClass().getSimpleName());
    }
}
