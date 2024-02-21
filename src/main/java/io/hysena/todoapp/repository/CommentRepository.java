package io.hysena.todoapp.repository;

import io.hysena.todoapp.entity.Comment;
import io.hysena.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByTodo(Todo todo);
}
