package com.example.alert_system.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class APIRuntimeException extends RuntimeException{
    private HttpStatus status;
    private String message;

    public APIRuntimeException(String message, HttpStatus status){
        super();
        this.status = status;
        this.message = message;
    }

    public APIRuntimeException(String message){
        super();
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

    public APIRuntimeException(Exception e){
        super();
        this.status = e instanceof APIRuntimeException ? ((APIRuntimeException) e).getStatus() : HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = e.getMessage() != null ? e.getMessage() : e.toString();
    }
}
