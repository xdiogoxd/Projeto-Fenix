package com.Projeto.Fenix.infra;

import com.Projeto.Fenix.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CategoryAlreadyExistException.class)
    private ResponseEntity<RestErrorMessage> categoryAlreadyExistException(CategoryAlreadyExistException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    private ResponseEntity<RestErrorMessage> categoryNotFoundException(CategoryNotFoundException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    private ResponseEntity<RestErrorMessage> itemAlreadyExistException(ItemAlreadyExistException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    private ResponseEntity<RestErrorMessage> itemNotFoundException(ItemNotFoundException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(UsernameOrEmailAlreadyInUseException.class)
    private ResponseEntity<RestErrorMessage> usernameOrEmailAlreadyInUseException(UsernameOrEmailAlreadyInUseException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.FORBIDDEN,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<RestErrorMessage> userNotFoundHandler(UserNotFoundException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.NOT_FOUND,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    private ResponseEntity<RestErrorMessage> userUnauthorizedException(UserUnauthorizedException exception){
        RestErrorMessage treatedResponse = new RestErrorMessage(HttpStatus.UNAUTHORIZED,exception.getMessage());
        return ResponseEntity.status(treatedResponse.getStatus()).body(treatedResponse);
    }




}
