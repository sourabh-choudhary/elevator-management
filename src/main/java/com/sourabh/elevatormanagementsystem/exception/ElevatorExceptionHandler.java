package com.sourabh.elevatormanagementsystem.exception;

import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ElevatorExceptionHandler {
        @ExceptionHandler(value = ElevatorException.class)
        public ResponseEntity<Object> exception(ElevatorException exception) {
            return new ResponseEntity<>("No Free Elevator", HttpStatus.NOT_FOUND);
        }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> exception(IllegalArgumentException exception) {
        return new ResponseEntity<>("Invalid Floor", HttpStatus.BAD_REQUEST);
    }

}
