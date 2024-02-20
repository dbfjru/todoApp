package io.hysena.todoapp.service;

import io.hysena.todoapp.dto.CreateTodoRequestDto;
import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.entity.User;
import io.hysena.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public Todo createTodo(CreateTodoRequestDto todoRequestDto, User user) {
        Todo todo = new Todo(todoRequestDto, user);
        todoRepository.save(todo);
        return todo;
    }
}
