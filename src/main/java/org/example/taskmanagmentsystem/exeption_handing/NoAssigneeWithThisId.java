package org.example.taskmanagmentsystem.exeption_handing;

public class NoAssigneeWithThisId extends RuntimeException{
    @Override
    public String getMessage() {
        return "С введеным id нет Связанного Юзера";
    }
}
