package com.agustosoftware.storechecker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity handleUnauthorizedException(UnauthorizedException e){
        Fault fault = new Fault();
        fault.setCode(HttpStatus.UNAUTHORIZED.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(fault);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        Fault fault = new Fault();
        fault.setCode(HttpStatus.BAD_REQUEST.value());
        fault.setMessage("Invalid JSON");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fault);
    }

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

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = ForbiddenException.class)
    public ResponseEntity handleNotFoundException(ForbiddenException e){
        Fault fault = new Fault();
        fault.setCode(HttpStatus.FORBIDDEN.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(fault);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleException(Exception e) {
        Fault fault = new Fault();
        fault.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        fault.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fault);
    }

}
