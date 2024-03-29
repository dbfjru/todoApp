package io.hysena.todoapp.dto;

import io.hysena.todoapp.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {
    private Long todoId;
    private String title;
    private String contents;
    private String username;
    private Boolean complete;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.title = todo.getTitle();
        this.contents = todo.getContents();
        this.username = todo.getUser().getUsername();
        this.complete = todo.getComplete();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
    }
}
