package io.hysena.todoapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL) //Json으로 만들 때 NULL 이 아닌 값만 만든다.
public class CommonResponseDto<T> {
    private String message;
    private Integer statusCode;
    private T data;
}
