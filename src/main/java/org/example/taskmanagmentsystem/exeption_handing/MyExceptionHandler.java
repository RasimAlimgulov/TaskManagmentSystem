package org.example.taskmanagmentsystem.exeption_handing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> handlerException(NoInfoAboutNewUserException exception){
        MessageIncorrectData incorrectData=new MessageIncorrectData();
        incorrectData.setMessage(exception.getMessage());
        return  new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> anotherTask(YouCantChangeOtherTask exception){
       MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> noAuthorWithId(NoAuthorWithThisId exception){
        MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> noAssigneeWithId(NoAssigneeWithThisId exception){
        MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> noPermitForChangeStatus(YouCantChangeStatusOfNotYourTask exception){
        MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NO_CONTENT);
    }
    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> incorrectStatus(IncorrectStatus exception){
        MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler
    public ResponseEntity<MessageIncorrectData> deleteComment(YouCantDeleteOtherComments exception){
        MessageIncorrectData data=new MessageIncorrectData();
        data.setMessage(exception.getMessage());
        return new ResponseEntity<>(data,HttpStatus.NO_CONTENT);
    }
}
