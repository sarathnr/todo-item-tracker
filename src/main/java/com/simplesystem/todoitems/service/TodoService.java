package com.simplesystem.todoitems.service;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import com.simplesystem.todoitems.exception.InvalidDataException;
import com.simplesystem.todoitems.exception.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item with id: " + id + " not found")));
    }

    public Todo save(Todo item) {

        if (isExpired(item.getDueDate())) {
            throw new InvalidDateException("Due date cannot be an expired date");
        }

        if (isPastDue(item.getStatus())) {
            throw new InvalidDataException("Cannot save item PAST_DUE status");
        }
        return todoRepository.save(item);
    }

    private static boolean isExpired(LocalDateTime dueDate) {
        return dueDate.isBefore(LocalDateTime.now());
    }

    private boolean isPastDue(TodoStatus status) {
        return TodoStatus.PAST_DUE == status;
    }
}
