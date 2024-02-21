package io.hysena.todoapp.service;

import io.hysena.todoapp.dto.CreateCommentRequestDto;
import io.hysena.todoapp.dto.ModifyCommentRequestDto;
import io.hysena.todoapp.entity.Comment;
import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.entity.User;
import io.hysena.todoapp.repository.CommentRepository;
import io.hysena.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public Comment createComment(CreateCommentRequestDto requestDto, User user, Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 TODO 입니다."));
        Comment comment = new Comment(requestDto, user, todo);
        return commentRepository.save(comment);
    }
    @Transactional
    public Comment modifyComment(ModifyCommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(requestDto.getCommentId()).orElseThrow(()->new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }
        String text = requestDto.getText();
        comment.setText(text);
        return comment;
    }

    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));
        if(!comment.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentListOfTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new IllegalArgumentException("할일이 존재하지 않습니다."));
        return commentRepository.findAllByTodo(todo);
    }
}
