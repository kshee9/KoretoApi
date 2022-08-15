package com.example.koreto.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {

    private T result;

    private  String message;


    public ResponseDto(String message, T t) {
        this.message = message;
        this.result = t;
    }

    public ResponseDto(String message) {
        this.message= message;
    }
}
