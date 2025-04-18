package com.ada.pedidocompra.infraestrutura.configuracao.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<List<FieldErrorMessage>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

        var errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new FieldErrorMessage(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<ErrorMessage> negocioException(NegocioException negocioException) {

        var errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), negocioException.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ErrorMessage> exception(Exception validacaoException) {

        log.error(validacaoException.getMessage(), validacaoException);

        var errorMessage = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                validacaoException.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
    }

}
