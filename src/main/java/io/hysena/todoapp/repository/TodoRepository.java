package io.hysena.todoapp.repository;

import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByUserOrderByCreatedAtDesc(User user);
}
