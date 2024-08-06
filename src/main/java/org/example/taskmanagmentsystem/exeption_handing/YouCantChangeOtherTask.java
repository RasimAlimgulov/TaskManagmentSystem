package org.example.taskmanagmentsystem.exeption_handing;

public class YouCantChangeOtherTask extends RuntimeException {
    @Override
    public String getMessage() {
        return "Вы не можете изменять чужие задачи, только свои.";
    }
}
