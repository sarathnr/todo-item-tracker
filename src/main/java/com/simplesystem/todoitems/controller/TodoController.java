package com.simplesystem.todoitems.controller;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dto.TodoDto;
import com.simplesystem.todoitems.service.TodoService;
import com.simplesystem.todoitems.util.TodoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Create a Todo item")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Created the Todo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Todo supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Validation failed",
                    content = @Content)})
    @PostMapping
    public ResponseEntity saveTodo(@RequestBody TodoDto todoDto) {
        Todo createdItem = todoService.saveTodo(map.toEntity(todoDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(map.toDto(createdItem));
    }

    @Operation(summary = "Update a Todo by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned the updated Todo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid updates supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content)})

    @PutMapping("/{id}")
    public ResponseEntity<TodoDto> updateTodoById(@PathVariable long id, @Valid @RequestBody TodoDto todoDto) {
        Todo item = todoService.updateTodoById(id, map.toEntity(todoDto));
        return ResponseEntity.status(HttpStatus.OK).body(map.toDto(item));
    }

    @Operation(summary = "Find all Todos")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returns all Todos filtered with status, if provided",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid status supplied",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<TodoDto>> findAllIfNotByStatus(@RequestParam(required = false) Integer status) {
        List<Todo> items = todoService.findAllIfNotByStatus(status);
        return ResponseEntity.status(HttpStatus.OK).body(map.toDtoList(items));
    }

    @Operation(summary = "Get a Todo by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Retrieved a Todo",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TodoDto.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid updates supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Todo not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> findByTodoId(@PathVariable Long id) {
        Optional<Todo> item = todoService.findByTodoId(id);
        return ResponseEntity.status(HttpStatus.OK).body(map.toDto(item.get()));
    }

}