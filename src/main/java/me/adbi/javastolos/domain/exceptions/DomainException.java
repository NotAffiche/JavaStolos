package me.adbi.javastolos.domain.exceptions;

public class DomainException extends Exception {
    public DomainException(String errorMessage) {
        super(errorMessage);
    }
    public DomainException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
