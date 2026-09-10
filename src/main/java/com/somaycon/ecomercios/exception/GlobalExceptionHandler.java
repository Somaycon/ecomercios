package com.somaycon.ecomercios.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUsuarioAlreadyExistsException(UsuarioAlreadyExistsException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflito de Dados");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }
}
