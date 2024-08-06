package org.example.taskmanagmentsystem.exeption_handing;

public class NoAuthorWithThisId extends RuntimeException{
    @Override
    public String getMessage() {
        return "С введеным id нет автора";
    }
}
