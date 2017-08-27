package com.agustosoftware.storechecker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity handleBadRequestException(BadRequestException e){
        Fault fault = new Fault();
        fault.setCode(HttpStatus.BAD_REQUEST.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fault);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException e){
        Fault fault = new Fault();
        fault.setCode(HttpStatus.NOT_FOUND.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(fault);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception e) {
        Fault fault = new Fault();
        fault.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fault);
    }

}
