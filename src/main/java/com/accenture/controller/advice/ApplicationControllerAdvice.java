package com.accenture.controller.advice;

import com.accenture.exception.AdminException;
import com.accenture.exception.ClientException;
import com.accenture.exception.VehicleException;
import com.accenture.model.ResponseError;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ApplicationControllerAdvice {

//    @ExceptionHandler(AdminException.class)
//    public ResponseEntity<ResponseError> adminExceptionManagement(AdminException ex) {
//        ResponseError re = new ResponseError(LocalDateTime.now(), "Functional error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
//    }
//
//    @ExceptionHandler(ClientException.class)
//    public ResponseEntity<ResponseError> clientExceptionManagement(ClientException ex) {
//        ResponseError re = new ResponseError(LocalDateTime.now(), "Functional error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
//    }
//
//    @ExceptionHandler(AdminException.class)
//    public ResponseEntity<ResponseError> vehicleExceptionManagement(VehicleException ex) {
//        ResponseError re = new ResponseError(LocalDateTime.now(), "Functional error", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(re);
//    }
//
//    @ExceptionHandler(EntityNotFoundException.class)
//    public ResponseEntity<ResponseError> enfExceptionManagement(AdminException ex) {
//        ResponseError re = new ResponseError(LocalDateTime.now(), "Bad request", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(re);
//    }
}
