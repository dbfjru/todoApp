package io.hysena.todoapp.controller;

import io.hysena.todoapp.dto.CommonResponseDto;
import io.hysena.todoapp.dto.CreateTodoRequestDto;
import io.hysena.todoapp.dto.ModifyRequestDto;
import io.hysena.todoapp.dto.TodoResponseDto;
import io.hysena.todoapp.entity.Todo;
import io.hysena.todoapp.impl.UserDetailsImpl;
import io.hysena.todoapp.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/todos")
@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    // 할일 작성
    @PostMapping
    public ResponseEntity<CommonResponseDto<TodoResponseDto>> createTodo(@RequestBody CreateTodoRequestDto todoRequestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo = todoService.createTodo(todoRequestDto,userDetails.getUser());
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new CommonResponseDto<>("TODO 생성 완료", HttpStatus.CREATED.value(), responseDto));
    }

    // 특정 할일 조회
    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<TodoResponseDto>> getTodo(@PathVariable Long todoId
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo;
        try {
            todo = todoService.getTodo(todoId, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.ok().body(new CommonResponseDto<>("TODO 조회 성공", HttpStatus.OK.value(), responseDto));
    }

    //할일 목록 조회
    @GetMapping
    public ResponseEntity<CommonResponseDto<List<TodoResponseDto>>> getTodoList(@AuthenticationPrincipal UserDetailsImpl userDetails){
        List<Todo> todoList = todoService.getTodoList(userDetails.getUser());
        List<TodoResponseDto> responseTodoList = todoList.stream().map(TodoResponseDto::new).toList();
        return ResponseEntity.ok().body(new CommonResponseDto<>("TODO 목록 조회 성공", HttpStatus.OK.value(), responseTodoList));
    }

    // 할일 수정 기능
    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto<TodoResponseDto>> modifyTodo(@PathVariable Long todoId, @RequestBody ModifyRequestDto modifyRequestDto
            , @AuthenticationPrincipal UserDetailsImpl userDetails){
        Todo todo;
        try {
            todo = todoService.modifyTodo(todoId, modifyRequestDto, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return ResponseEntity.ok().body(new CommonResponseDto<>("할일 수정 완료", HttpStatus.OK.value(), responseDto));
    }

    // 할일 완료 기능
    @PutMapping("/{todoId}/status")
    public ResponseEntity<CommonResponseDto<Boolean>> changeTodoStatus(@PathVariable Long todoId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Boolean status;
        try{
            status = todoService.changeTodoStatus(todoId, userDetails.getUser());
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto<>(e.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
        }
        return ResponseEntity.ok().body(new CommonResponseDto<>("현재 상태 : " + status, HttpStatus.OK.value(), null));
    }
}
