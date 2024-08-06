package org.example.taskmanagmentsystem.exeption_handing;

public class YouCantChangeStatusOfNotYourTask extends RuntimeException {
    @Override
    public String getMessage() {
        return "Вы не можете менять статус задачи на которую вы не назначены или не являетесь автором";
    }
}
