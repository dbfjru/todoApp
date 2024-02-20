package io.hysena.todoapp.controller;

import io.hysena.todoapp.dto.CreateTodoRequestDto;
import io.hysena.todoapp.dto.TodoResponseDto;
import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.impl.UserDetailsImpl;
import io.hysena.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/todos")
@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<TodoResponseDto> createTodo(@RequestBody CreateTodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo = todoService.createTodo(todoRequestDto,userDetails.getUser());
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

}
