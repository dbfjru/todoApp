package io.hysena.todoapp.dto;

import io.hysena.todoapp.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String text;
    private Long userId;
    private Long todoId;

    public CommentResponseDto(Comment comment){
        this.commentId = comment.getCommentId();
        this.text = comment.getText();;
        this.userId = comment.getUser().getUserId();
        this.todoId = comment.getTodo().getTodoId();
    }
}
