package ua.com.juja.positiv.sqlcmd.dao.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ua.com.juja.positiv.sqlcmd.dao.entity.UserAction;

import java.util.List;

/**
 * Created by POSITIV on 21.12.2015.
 */
public interface UserActionRepository extends CrudRepository<UserAction, Integer>, UserActionRepositoryCustom {

    @Query(value = "SELECT ua FROM UserAction ua WHERE ua.connection.userName = :userName")
    List<UserAction> findByUserName(@Param("userName") String userName);
}
