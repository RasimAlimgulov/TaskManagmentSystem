package org.example.taskmanagmentsystem.exeption_handing;

public class IncorrectStatus extends RuntimeException{
    @Override
    public String getMessage() {
        return "Вы ввели не правильно статус. Возможны только PENDING,IN_PROGRESS,COMPLETED";
    }
}
