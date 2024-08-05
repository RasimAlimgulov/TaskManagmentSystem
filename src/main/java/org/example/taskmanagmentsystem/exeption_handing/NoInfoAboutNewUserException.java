package org.example.taskmanagmentsystem.exeption_handing;

public class NoInfoAboutNewUserException extends RuntimeException{
    @Override
    public String getMessage() {
      return "Вы ввели не правильные данные для добавления нового юзера";
    }
}
