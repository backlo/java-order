package net.class101.server1.support.exception;

import java.io.IOException;

public class CanNotReadFileException extends RuntimeException {
    public CanNotReadFileException(IOException e) {
        super(e.getMessage(), e.getCause());
    }
}
