package com.simplesystem.todoitems.service;


import com.simplesystem.todoitems.dao.entity.Todo;

import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.exception.InvalidDataException;
import com.simplesystem.todoitems.exception.InvalidDateException;
import com.simplesystem.todoitems.helper.Todos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void save_validItem_shouldSaveItem() {
        Todo item = Todos.anItem();
        when(todoRepository.save(item)).thenReturn(item);

        Todo savedItem = todoService.save(item);

        assertNotNull(savedItem);
        assertSame(item, savedItem);
        verify(todoRepository, times(1)).save(item);
    }

    @Test
    void save_invalidItem_shouldThrowInvalidDateException() {
        Todo invalidItem = Todos.anInvalidItem();

        assertThrows(InvalidDateException.class, () -> todoService.save(invalidItem));

        verify(todoRepository, times(0)).save(invalidItem);
    }

    @Test
    void save_pastDueItem_shouldThrowInvalidDataException() {
        Todo pastDueItem = Todos.pastDueItem();

        assertThrows(InvalidDataException.class, () -> todoService.save(pastDueItem));

        verify(todoRepository, times(0)).save(pastDueItem);
    }

    @Test
    void findById_validId_shouldReturnItem() {

        Long itemId = 1L;
        Todo item = Todos.anItem();
        when(todoRepository.findById(itemId)).thenReturn(Optional.of(item));

        Optional<Todo> itemFound = todoService.findById(itemId);

        assertTrue(itemFound.isPresent());
        assertEquals(item.getId(), itemFound.get().getId());
        verify(todoRepository).findById(itemId);
    }
}
