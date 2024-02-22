package io.hysena.todoapp.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class SignupRequestDtoTest {
    String passUsername = "username";
    String passPassword = "password";
    String failUsername = "username123";
    String failPassword = "pass";
    @Test
    @DisplayName("회원가입 DTO Validation 성공")
    void validTestPass(){
        //given

        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername(passUsername);
        requestDto.setPassword(passPassword);

        //when
        Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);
        //then
        assertThat(violations).isEmpty();
    }
    @Test
    @DisplayName("회원가입 DTO Validation 실패 - username case")
    void validTestFailUsername(){
        //given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername(failUsername);
        requestDto.setPassword(passPassword);

        //when
        Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

        //then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains("\"^[a-z0-9]{4,10}$\"와 일치해야 합니다");
    }

    @Test
    @DisplayName("회원가입 DTO Validation 실패 - password case")
    void validTestFailPassword(){
        //given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername(passUsername);
        requestDto.setPassword(failPassword);

        //when
        Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

        //then
        assertThat(violations).hasSize(1);
        assertThat(violations).extracting("message").contains("\"^[a-zA-Z0-9]{8,15}$\"와 일치해야 합니다");
    }
    @Test
    @DisplayName("회원가입 DTO Validation 실패 - both case")
    void validTestFailBoth(){
        //given
        SignupRequestDto requestDto = new SignupRequestDto();
        requestDto.setUsername(failUsername);
        requestDto.setPassword(failPassword);

        //when
        Set<ConstraintViolation<SignupRequestDto>> violations = validate(requestDto);

        //then
        assertThat(violations).hasSize(2);
        assertThat(violations).extracting("message")
                .contains("\"^[a-z0-9]{4,10}$\"와 일치해야 합니다")
                .contains("\"^[a-zA-Z0-9]{8,15}$\"와 일치해야 합니다");
    }
    private Set<ConstraintViolation<SignupRequestDto>> validate(SignupRequestDto requestDto){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator.validate(requestDto);
    }
}