package com.ilibed.exception;

public class ServiceException extends Exception {
    public ServiceException() {
    }

    /**
     * @param message
     * @param exception
     */
    public ServiceException(String message, Throwable exception) {
        super(message, exception);
    }

    /**
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * @param exception
     */
    public ServiceException(Throwable exception) {
        super(exception);
    }
}
