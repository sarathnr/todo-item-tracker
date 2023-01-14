package com.simplesystem.todoitems.helper;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.enums.TodoStatus;

import java.time.LocalDateTime;

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
                .withId(1L)
                .withDescription("Finish reading book")
                .withDueDate(LocalDateTime.now().minusDays(1L))
                .withStatus(TodoStatus.NOT_DONE)
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
}