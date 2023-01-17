package com.simplesystem.todoitems.controller;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dto.TodoDto;
import com.simplesystem.todoitems.service.TodoService;
import com.simplesystem.todoitems.util.TodoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/todos", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;
    private final TodoMapper map;


    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllIfNotByStatus(@RequestParam(required = false) Integer status) {
        List<Todo> items = todoService.findAllIfNotByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(map.toDtoList(items));
    }

    @PostMapping
    public ResponseEntity addTodo(@RequestBody TodoDto todoDto) {
        Todo createdItem = todoService.saveTodo(map.toEntity(todoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(map.toDto(createdItem));
    }



    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findById(@PathVariable Long id) {
        Optional<Todo> item = todoService.findByTodoId(id);
        return ResponseEntity.status(HttpStatus.OK).body(map.toDto(item.get()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateById(@PathVariable long id, @Valid @RequestBody TodoDto todoDto) {
        Todo item = todoService.updateTodo(id, map.toEntity(todoDto));
        return ResponseEntity.status(HttpStatus.OK).body(map.toDto(item));
    }
}