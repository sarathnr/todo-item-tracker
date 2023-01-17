package com.simplesystem.todoitems.util;

import com.simplesystem.todoitems.dao.entity.Todo;
import com.simplesystem.todoitems.dto.TodoDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TodoDto toDto(Todo item) {
        return modelMapper.map(item, TodoDto.class);
    }

    public List<TodoDto> toDtoList(List<Todo> items) {
        return items.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Todo toEntity(TodoDto todoDto) {
        return modelMapper.map(todoDto, Todo.class);
    }
}
