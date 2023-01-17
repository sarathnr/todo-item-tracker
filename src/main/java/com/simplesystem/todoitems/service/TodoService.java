package com.simplesystem.todoitems.service;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dao.repository.TodoRepository;
import com.simplesystem.todoitems.enums.TodoStatus;
import com.simplesystem.todoitems.exception.InvalidDataException;
import com.simplesystem.todoitems.exception.InvalidDateException;
import com.simplesystem.todoitems.exception.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public Optional<Todo> findByTodoId(Long id) {
        return Optional.ofNullable(todoRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Item with id: %s not found", id))));
    }

    public Todo saveTodo(Todo item) {
        return todoRepository.save(item);
    }

    public List<Todo> findAllIfNotByStatus(Integer status) {

        TodoStatus requested = TodoStatus.fromOrdinal(status);
        List<Todo> all = todoRepository.findAll();
        if (requested != null) {
            return all.stream().filter(o -> o.getStatus() == requested).collect(Collectors.toList());
        }
        return all;
    }
    public Todo updateTodo(Long itemId, Todo latest) {

        checkIsValid(latest);

        Todo itemFound = todoRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException(String.format("Todo with id  %s  not found", itemId)));

        Todo updatedItem = mapUpdates(latest, itemFound);

        return saveTodo(updatedItem);
    }

    private void checkIsValid(Todo item) {

        if (isExpired(item.getDueDate())) {
            throw new InvalidDateException("Due date cannot be an expired date");
        }

        if (hasPastDue(item.getStatus())) {
            throw new InvalidDataException(String.format("Cannot save item %s status", item.getStatus()));
        }
    }

    private static boolean isExpired(LocalDateTime dueDate) {
        return dueDate.isBefore(LocalDateTime.now());
    }

    private boolean hasPastDue(TodoStatus status) {
        return TodoStatus.PAST_DUE == status;
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
