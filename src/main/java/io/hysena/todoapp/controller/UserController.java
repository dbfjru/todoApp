package io.hysena.todoapp.controller;

import io.hysena.todoapp.dto.CommonResponseDto;
import io.hysena.todoapp.dto.SignupRequestDto;
import io.hysena.todoapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String message = "";
            for(FieldError fieldError : fieldErrors){
                message += "<< " +fieldError.getField() + " : " + fieldError.getDefaultMessage() + ">> ";
            }
            return ResponseEntity.badRequest().body(new CommonResponseDto(message,HttpStatus.BAD_REQUEST.value()));
        }
        try{
            userService.signup(requestDto);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(new CommonResponseDto("회원가입 완료", HttpStatus.CREATED.value()));
    }
}
