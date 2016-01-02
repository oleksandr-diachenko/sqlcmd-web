package ua.com.juja.positiv.sqlcmd.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.positiv.sqlcmd.dao.entity.DatabaseConnection;

/**
 * Created by POSITIV on 21.12.2015.
 */
public interface DatabaseConnectionRepository extends CrudRepository<DatabaseConnection, Integer> {

    DatabaseConnection findByUserNameAndDbName(String username, String dbName);
}
