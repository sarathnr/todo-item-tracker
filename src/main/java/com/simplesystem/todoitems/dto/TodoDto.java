package com.simplesystem.todoitems.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.simplesystem.todoitems.enums.TodoStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "anItemDto", setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TodoDto {
    private Long id;
    @NonNull
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @NonNull
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private TodoStatus status;
}
