package com.cv.exception;

public class NotFoundException extends Exception {
    public NotFoundException() {
        super("Instance not found [404]");
    }
}
