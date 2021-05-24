package com.example.airport.web.rest.errorhandler;

import com.example.airport.persistance.exception.IllegalIndexEntity;
import com.example.airport.persistance.exception.NoFoundEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(GlobalControllerAdvice.class);


    @ExceptionHandler(NoFoundEntity.class)
    public ResponseEntity<Error> noFoundExceptionHandler(NoFoundEntity err){
        String message = "no found " + err.getMessage();
        logger.error(message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error(404,message));
    }

    @ExceptionHandler(IllegalIndexEntity.class)
    public ResponseEntity<Error> illegalEntityIndexHandler(IllegalIndexEntity err){
        String message = "Illegal " + err.getMessage();
        logger.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(400,message));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Error> illegalArgumentExceptionHandler(IllegalArgumentException err){
        logger.error(err.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error(400,err.getMessage()));
    }



}
