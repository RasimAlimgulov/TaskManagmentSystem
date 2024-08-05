package com.rasimalimgulov.userservice.user_service.exeption_handing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<UserIncorrectData> handlerException(NoInfoAboutNewUserException exception){
        UserIncorrectData incorrectData=new UserIncorrectData();
        incorrectData.setMessage(exception.getMessage());
        return  new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
