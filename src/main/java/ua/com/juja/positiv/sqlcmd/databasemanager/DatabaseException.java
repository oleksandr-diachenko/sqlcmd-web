package ua.com.juja.positiv.sqlcmd.databasemanager;

/**
 * Created by POSITIV on 27.11.2015.
 */
public class DatabaseException extends Exception {

    public DatabaseException(){}

    public DatabaseException(String message, Exception e){
       super(message, e);
    }
}
