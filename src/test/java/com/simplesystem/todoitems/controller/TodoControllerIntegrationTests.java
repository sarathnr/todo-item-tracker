package com.simplesystem.todoitems.controller;

import com.simplesystem.todoitems.dto.TodoDto;
import com.simplesystem.todoitems.helper.Todos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TodoControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void givenPost_whenAddTodo_thenReturn201() {
        TodoDto todoDto = Todos.anItemDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TodoDto> request = new HttpEntity<>(todoDto, headers);

        ResponseEntity<TodoDto> response = restTemplate.exchange("/api/v1/todos", HttpMethod.POST, request, TodoDto.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void givenGet_whenFindAllIfNotByStatus_thenReturn200() {
        Integer status = 1;

        ResponseEntity<List<TodoDto>> response = restTemplate.exchange("/api/v1/todos?status="+ status, HttpMethod.GET, null, new ParameterizedTypeReference<List<TodoDto>>(){});

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void givenGet_whenFindById_thenReturn200() {
        Long todoId = 1L;

        ResponseEntity<TodoDto> response = restTemplate.exchange("/api/v1/todos/{id}", HttpMethod.GET, null, TodoDto.class, todoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void givenPut_whenUpdate_thenReturn200() {
        Long todoId = 1L;
        TodoDto todoDto = Todos.anItemDto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TodoDto> request = new HttpEntity<>(todoDto, headers);

        ResponseEntity<TodoDto> response = restTemplate.exchange("/api/v1/todos/{id}", HttpMethod.PUT, request, TodoDto.class, todoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


}
