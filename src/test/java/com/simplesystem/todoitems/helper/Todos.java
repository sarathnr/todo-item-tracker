package com.simplesystem.todoitems.helper;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dto.TodoDto;
import com.simplesystem.todoitems.enums.TodoStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Todos {

    public static Todo anItem() {
        return Todo.anItem()
                .withId(1L)
                .withDescription("Finish reading book")
                .withDueDate(LocalDateTime.now().plusDays(3L))
                .withStatus(TodoStatus.NOT_DONE)
                .withCreatedAt(LocalDateTime.now()).build();
    }

    public static Todo anInvalidItem() {
        return Todo.anItem()
                .withId(1000L)
                .withDescription("Visit friends")
                .withDueDate(LocalDateTime.now().minusDays(1L))
                .withStatus(TodoStatus.NOT_DONE)
                .withCreatedAt(LocalDateTime.now()).build();
    }

    public static TodoDto anInvalidItemDto() {
        return TodoDto.anItemDto()
                .withId(1000L)
                .withDescription("Visit friends")
                .withDueDate(LocalDateTime.now().minusDays(1L))
                .withStatus(TodoStatus.PAST_DUE)
                .withCreatedAt(LocalDateTime.now()).build();
    }

    public static Todo pastDueItem() {
        return Todo.anItem()
                .withId(1L)
                .withDescription("Finish reading book")
                .withDueDate(LocalDateTime.now().plusDays(1L))
                .withStatus(TodoStatus.PAST_DUE)
                .withCreatedAt(LocalDateTime.now()).build();
    }

    public static List<Todo> listOfItems() {
        Todo itemForJan = Todo.anItem()
                .withId(1L)
                .withDescription("Join language class")
                .withDueDate(LocalDateTime.now().plusMonths(1L))
                .withStatus(TodoStatus.NOT_DONE)
                .withCreatedAt(LocalDateTime.now()).build();
        Todo itemForFeb = Todo.anItem()
                .withId(2L)
                .withDescription("Learn python")
                .withDueDate(LocalDateTime.now().plusDays(2L))
                .withStatus(TodoStatus.NOT_DONE)
                .withCreatedAt(LocalDateTime.now()).build();
        return List.of(itemForJan, itemForFeb);
    }

    public static TodoDto anItemDto() {
        return TodoDto.anItemDto()
                .withDescription("Finish reading book")
                .withDueDate(LocalDateTime.now().plusDays(3L))
                .withStatus(TodoStatus.NOT_DONE)
                .withCreatedAt(LocalDateTime.now()).build();
    }
}