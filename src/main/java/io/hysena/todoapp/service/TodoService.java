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

    public Todo getTodo(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()->new IllegalArgumentException("존재하지 않는 TODO 입니다."));
        if(!todo.getUser().getUserId().equals(user.getUserId())){
            throw new IllegalArgumentException("작성자 정보가 일치하지 않습니다.");
        }
        return todo;
    }
}
