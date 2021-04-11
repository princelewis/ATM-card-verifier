package com.threeLine.atmcardverifier.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = CardInfoServiceException.class)
    public ResponseEntity<Object> clientServiceExceptionHandler(CardInfoServiceException e, WebRequest request){

        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage(), request.getDescription(false));

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> otherExceptionHandler(Exception e, WebRequest request){
        ErrorMessage errorMessage = new ErrorMessage(new Date(), e.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
}
