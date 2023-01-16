package com.simplesystem.todoitems.service;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import com.simplesystem.todoitems.exception.InvalidDataException;
import com.simplesystem.todoitems.exception.InvalidDateException;
import com.simplesystem.todoitems.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todoRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Item with id: %s not found", id))));
    }

    public Todo add(Todo item) {
        checkIsValid(item);
        return todoRepository.save(item);
    }

    private static boolean isExpired(LocalDateTime dueDate) {
        return dueDate.isBefore(LocalDateTime.now());
    }

    private boolean hasPastDue(TodoStatus status) {
        return TodoStatus.PAST_DUE == status;
    }

    public void deleteById(Long itemId) {
        Todo todo = todoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Todo with id  %s  not found", itemId)));
        todoRepository.delete(todo);
    }

    public List<Todo> findByStatus(int status) {
        TodoStatus todoStatus = TodoStatus.fromOrdinal(status);
        if (todoStatus == null)
            throw new InvalidDataException(String.format("Invalid status requested - %s", status));

        return todoRepository.findByStatus(todoStatus);
    }

    private void checkIsValid(Todo item) {

        if (isExpired(item.getDueDate())) {
            throw new InvalidDateException("Due date cannot be an expired date");
        }

        if (hasPastDue(item.getStatus())) {
            throw new InvalidDataException(String.format("Cannot save item %s status", item.getStatus()));
        }
    }

    public Todo update(Long itemId, Todo latest) {
        Todo itemFound = todoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Todo with id  %s  not found", itemId)));

        Todo updatedItem = mapUpdates(latest, itemFound);

        return todoRepository.save(updatedItem);
    }

    private Todo mapUpdates(Todo latest, Todo itemFound) {

        itemFound.setDescription(latest.getDescription());
        itemFound.setDueDate(latest.getDueDate());

        if (latest.getStatus() == TodoStatus.DONE) {
            itemFound.setCompletedAt(LocalDateTime.now());
            itemFound.setStatus(TodoStatus.DONE);
        } else {
            itemFound.setCompletedAt(null);
            itemFound.setStatus(TodoStatus.NOT_DONE);
        }
        return itemFound;
    }
}
