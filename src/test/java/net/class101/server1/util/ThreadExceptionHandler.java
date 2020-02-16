package net.class101.server1.util;

public class ThreadExceptionHandler implements Thread.UncaughtExceptionHandler {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        errorMessage = e.getMessage();
    }
}
