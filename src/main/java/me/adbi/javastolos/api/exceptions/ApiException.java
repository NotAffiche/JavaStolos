package me.adbi.javastolos.api.exceptions;

public class ApiException extends Exception {
    public ApiException(String errorMessage) {
        super(errorMessage);
    }
    public ApiException(String message, Throwable err) {
        super(message, err);
    }
}
