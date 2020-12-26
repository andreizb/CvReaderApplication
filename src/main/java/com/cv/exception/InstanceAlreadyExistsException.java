package com.cv.exception;

public class InstanceAlreadyExistsException extends Exception {
    public InstanceAlreadyExistsException() {
        super("Instance already exists [409]");
    }
}
