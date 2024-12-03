package com.example.plantezemobileapplication.utils;

public class TaskResult {
    private boolean isSuccess;
    private String message;

    public TaskResult(boolean isSuccess, String message) {
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMessage() {
        return message;
    }
}
