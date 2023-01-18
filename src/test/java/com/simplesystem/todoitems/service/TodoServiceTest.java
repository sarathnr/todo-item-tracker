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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.simplesystem.todoitems.helper.Todos.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Spy
    private Todos todos;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void givenValidItem_whenSave_thenSaveItem() {
        Todo item = anItem();
        when(todoRepository.save(item)).thenReturn(item);

        Todo savedItem = todoService.saveTodo(item);

        assertNotNull(savedItem);
        assertSame(item, savedItem);
        verify(todoRepository, atLeastOnce()).save(item);
    }

    @Test
    void givenExpiredDueDate_whenSave_thenThrowInvalidDateException() {
        Todo pastDueItem = anInvalidItem();

        assertThrows(InvalidDateException.class, () -> todoService.updateTodoById(pastDueItem.getId(),pastDueItem));

        verify(todoRepository, never()).save(pastDueItem);
    }

    @Test
    void givenPastDueItem_whenUpdate_thenThrowInvalidDataException() {
        Todo pastDueItem = pastDueItem();

        assertThrows(InvalidDataException.class, () -> todoService.updateTodoById(pastDueItem.getId(),pastDueItem));

        verify(todoRepository, never()).save(pastDueItem);
    }

    @Test
    void givenItemFound_whenFindById_thenReturnItem() {
        Long itemId = 1L;
        Todo item = anItem();
        when(todoRepository.findById(itemId)).thenReturn(Optional.of(item));

        Optional<Todo> itemFound = todoService.findByTodoId(itemId);

        assertTrue(itemFound.isPresent());
        assertEquals(item.getId(), itemFound.get().getId());
        verify(todoRepository, atLeastOnce()).findById(itemId);
    }

    @Test
    void givenNotItemFound_whenFindById_thenThrowItemNotFoundException() {
        Long itemId = 99L;

        assertThrows(ItemNotFoundException.class, () -> todoService.findByTodoId(itemId));

        verify(todoRepository, atLeastOnce()).findById(itemId);
    }

    @Test
    void givenValidStatus_whenFindByStatus_thenReturnItemsByStatus() {
        Integer validStatus = TodoStatus.NOT_DONE.getValue();
        List<Todo> itemsByStatus = listOfItems();
        when(todoService.findAllIfNotByStatus(validStatus)).thenReturn(itemsByStatus);

        List<Todo> itemsFound = todoService.findAllIfNotByStatus(validStatus);

        assertFalse(itemsFound.isEmpty());
        assertTrue(itemsFound.stream().allMatch(item -> item.getStatus() == TodoStatus.NOT_DONE));
    }

    @Test
    void givenInValidStatus_whenFindByStatus_thenThrowInvalidDataException() {
        Integer invalidStatus = -1;

        assertThrows(InvalidDataException.class, () -> todoService.findAllIfNotByStatus(invalidStatus));

        verify(todoRepository, never()).findAll();
    }

    @Test
    void givenStatusNull_whenFindByStatus_thenThrowInvalidDataException() {

        assertThrows(InvalidDataException.class, () -> todoService.findAllIfNotByStatus(null));

        verify(todoRepository, never()).findAll();
    }

    @Test
    void givenInvalidId_whenUpdate_thenThrowItemNotFoundException() {
        Long invalidId = -1L;
        Todo item = Todos.anItem();

        assertThrows(ItemNotFoundException.class, () -> todoService.updateTodoById(invalidId, item));

        verify(todoRepository, never()).save(item);
    }
}