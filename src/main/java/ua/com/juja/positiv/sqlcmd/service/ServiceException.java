package ua.com.juja.positiv.sqlcmd.service;

/**
 * Created by POSITIV on 27.11.2015.
 */
public class ServiceException extends Exception {

    public ServiceException() {}

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}
