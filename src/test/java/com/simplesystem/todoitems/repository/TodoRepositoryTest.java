package com.simplesystem.todoitems.repository;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import com.simplesystem.todoitems.helper.Todos;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DataJpaTest
class TodoRepositoryTest {

    @MockBean
    private TodoRepository todoRepository;

    @Test
    void givenExpiredItemsExists_whenFindByDueDateBeforeAndStatusNot_thenReturnExpiredItems() {
        List<Todo> items = Todos.listOfItems();
        LocalDateTime now = LocalDateTime.now();
        when(todoRepository.findByDueDateBeforeAndStatusNot(Mockito.any(LocalDateTime.class), Mockito.any(TodoStatus.class))).thenReturn(items);

        List<Todo> expired = todoRepository.findByDueDateBeforeAndStatusNot(now, TodoStatus.PAST_DUE);

        verify(todoRepository, atLeastOnce()).findByDueDateBeforeAndStatusNot(now, TodoStatus.PAST_DUE);
    }
}