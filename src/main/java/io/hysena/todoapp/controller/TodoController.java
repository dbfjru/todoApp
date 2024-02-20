package io.hysena.todoapp.controller;

import io.hysena.todoapp.dto.CommonResponseDto;
import io.hysena.todoapp.dto.CreateTodoRequestDto;
import io.hysena.todoapp.dto.TodoResponseDto;
import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.impl.UserDetailsImpl;
import io.hysena.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/todos")
@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<TodoResponseDto>> createTodo(@RequestBody CreateTodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo = todoService.createTodo(todoRequestDto,userDetails.getUser());
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto<>("TODO 생성 완료", HttpStatus.CREATED.value(), responseDto));
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<TodoResponseDto>> getTodo(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo;
        try {
            todo = todoService.getTodo(todoId, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.ok().body(new CommonResponseDto<>("TODO 조회 성공", HttpStatus.OK.value(), responseDto));
    }

}
