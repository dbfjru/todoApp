package io.hysena.todoapp.dto;

import lombok.Getter;

@Getter
public class ModifyCommentRequestDto {
    private Long commentId;
    private String text;
}
