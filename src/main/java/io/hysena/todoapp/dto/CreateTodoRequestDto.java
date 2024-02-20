package io.hysena.todoapp.dto;

import lombok.Getter;

@Getter
public class CreateTodoRequestDto {
    private String title;
    private String contents;
}
