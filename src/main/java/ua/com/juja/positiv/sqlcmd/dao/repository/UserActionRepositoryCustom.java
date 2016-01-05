package ua.com.juja.positiv.sqlcmd.dao.repository;

/**
 * Created by POSITIV on 05.01.2016.
 */
public interface UserActionRepositoryCustom {

    void saveAction(String action, String user, String database);
}
