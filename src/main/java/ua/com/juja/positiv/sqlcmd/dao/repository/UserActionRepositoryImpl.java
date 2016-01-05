package ua.com.juja.positiv.sqlcmd.dao.repository;

import org.springframework.beans.factory.annotation.Autowired;
import ua.com.juja.positiv.sqlcmd.dao.entity.DatabaseConnection;
import ua.com.juja.positiv.sqlcmd.dao.entity.UserAction;

/**
 * Created by POSITIV on 05.01.2016.
 */
public class UserActionRepositoryImpl implements UserActionRepositoryCustom {

    @Autowired
    private DatabaseConnectionRepository databaseConnectionRepository;

    @Autowired
    private UserActionRepository userActionRepository;

    @Override
    public void saveAction(String action, String user, String database) {
        DatabaseConnection databaseConnection = databaseConnectionRepository
                .findByUserNameAndDbName(user, database);
        if(databaseConnection == null) {
            databaseConnection = databaseConnectionRepository.save(
                    new DatabaseConnection(user, database));
        }
        userActionRepository.save(new UserAction(action, databaseConnection));
    }
}
