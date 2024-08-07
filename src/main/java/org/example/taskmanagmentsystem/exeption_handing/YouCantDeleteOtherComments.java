package org.example.taskmanagmentsystem.exeption_handing;

public class YouCantDeleteOtherComments extends RuntimeException{
    @Override
    public String getMessage() {
        return "Нельзя удалять чужие комменты";
    }
}
