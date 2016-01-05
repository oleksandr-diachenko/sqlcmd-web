package ua.com.juja.positiv.sqlcmd.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.com.juja.positiv.sqlcmd.dao.entity.DatabaseConnection;
import ua.com.juja.positiv.sqlcmd.dao.entity.UserAction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by POSITIV on 05.01.2016.
 */
public class UserActionRepositoryImpl implements UserActionRepositoryCustom {

    @Autowired
    private DatabaseConnectionRepository databaseConnection;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = this.databaseConnection
                .findByUserNameAndDbName(user, database);
        if(databaseConnection == null) {
            databaseConnection = this.databaseConnection.save(
                    new DatabaseConnection(user, database));
        }
        entityManager.persist(new UserAction(action, databaseConnection));
    }
}
