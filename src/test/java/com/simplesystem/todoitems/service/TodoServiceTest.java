package com.simplesystem.todoitems.service;


import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import com.simplesystem.todoitems.exception.InvalidDataException;
import com.simplesystem.todoitems.exception.InvalidDateException;
import com.simplesystem.todoitems.exception.ItemNotFoundException;
import com.simplesystem.todoitems.helper.Todos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        verify(todoRepository, atLeastOnce()).save(item);
    }

    @Test
    void save_expiredDueDate_shouldThrowInvalidDateException() {
        Todo invalidItem = Todos.anInvalidItem();

        assertThrows(InvalidDateException.class, () -> todoService.save(invalidItem));

        verify(todoRepository, never()).save(invalidItem);
    }

    @Test
    void save_pastDueItem_shouldThrowInvalidDataException() {
        Todo pastDueItem = Todos.pastDueItem();

        assertThrows(InvalidDataException.class, () -> todoService.save(pastDueItem));

        verify(todoRepository, never()).save(pastDueItem);
    }

    @Test
    void findById_itemFound_shouldReturnItem() {
        Long itemId = 1L;
        Todo item = Todos.anItem();
        when(todoRepository.findById(itemId)).thenReturn(Optional.of(item));

        Optional<Todo> itemFound = todoService.findById(itemId);

        assertTrue(itemFound.isPresent());
        assertEquals(item.getId(), itemFound.get().getId());
        verify(todoRepository, atLeastOnce()).findById(itemId);
    }

    @Test
    void findById_itemNotFound_shouldThrowItemNotFoundException() {
        Long itemId = 99L;
        when(todoRepository.findById(itemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> todoService.findById(itemId));

        verify(todoRepository, atLeastOnce()).findById(itemId);
    }

    @Test
    void deleteById_idExists_shouldDeleteItem() {
        long itemId = 1L;
        Todo validItem = Todos.anItem();
        when(todoRepository.findById(itemId)).thenReturn(Optional.ofNullable(validItem));

        todoService.deleteById(itemId);

        verify(todoRepository, atLeastOnce()).delete(validItem);
    }

    @Test
    void deleteById_idNotExists_shouldThrowItemNotFoundException() {
        Long invalidItemId = 1000L;
        Todo invalidItem = Todos.anInvalidItem();
        when(todoRepository.findById(invalidItemId)).thenReturn(Optional.empty());

        assertThrows(ItemNotFoundException.class, () -> todoService.deleteById(invalidItemId));

        verify(todoRepository, never()).delete(invalidItem);
    }

    @Test
    void findByStatus_validStatus_shouldReturnItemsByStatus() {
        int notDone = TodoStatus.NOT_DONE.getValue();
        List<Todo> itemsByStatus = Todos.listOfItems();
        when(todoService.findByStatus(notDone)).thenReturn(itemsByStatus);

        List<Todo> itemsFound = todoService.findByStatus(notDone);

        assertFalse(itemsFound.isEmpty());
        assertTrue(itemsFound.stream().allMatch(item -> item.getStatus() == TodoStatus.NOT_DONE));
    }

    @Test
    void findByStatus_invalidStatus_shouldThrowInvalidDataException() {
        int invalidStatus = -1;

        assertThrows(InvalidDataException.class, () -> todoService.findByStatus(invalidStatus));

        verify(todoRepository, never()).findByStatus(TodoStatus.fromOrdinal(invalidStatus));
    }

    @Test
    void update_invalidId_shouldThrowItemNotFoundException() {
        Long invalidId = -1L;
        Todo invalidItem = Todos.anInvalidItem();

        assertThrows(ItemNotFoundException.class, ()-> todoService.update(invalidId));

        verify(todoRepository,never()).save(invalidItem);
    }
}
