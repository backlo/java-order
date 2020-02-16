package net.class101.server1.support.config;

public class RequestExceptionHandler implements Thread.UncaughtExceptionHandler {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        errorMessage = e.getMessage();
    }
}
