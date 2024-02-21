package io.hysena.todoapp.controller;

import io.hysena.todoapp.dto.CommentResponseDto;
import io.hysena.todoapp.dto.CommonResponseDto;
import io.hysena.todoapp.dto.CreateCommentRequestDto;
import io.hysena.todoapp.dto.ModifyCommentRequestDto;
import io.hysena.todoapp.entity.Comment;
import io.hysena.todoapp.impl.UserDetailsImpl;
import io.hysena.todoapp.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> createComment(@RequestBody CreateCommentRequestDto requestDto
            ,@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("todoId") Long todoId){
        Comment comment;
        try{
            comment = commentService.createComment(requestDto, userDetails.getUser(), todoId);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(),null));
        }
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto<>("댓글 작성 성공", HttpStatus.CREATED.value(),responseDto));
    }
    @PutMapping
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> modifyComment(@RequestBody ModifyCommentRequestDto requestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        Comment comment;
        try{
            comment = commentService.modifyComment(requestDto, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(),null));
        }
        CommentResponseDto responseDto = new CommentResponseDto(comment);
        return ResponseEntity.ok().body(new CommonResponseDto<>("댓글 수정 성공", HttpStatus.OK.value(),responseDto));
    }
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto<Void>> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try{
            commentService.deleteComment(commentId, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(),null));
        }
        return ResponseEntity.ok().body(new CommonResponseDto<>("댓글 삭제 성공", HttpStatus.OK.value(), null));
    }
}
