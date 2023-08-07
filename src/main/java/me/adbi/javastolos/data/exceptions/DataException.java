package me.adbi.javastolos.data.exceptions;

public class DataException extends Exception {
    public DataException(String errorMessage) {
        super(errorMessage);
    }
    public DataException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
