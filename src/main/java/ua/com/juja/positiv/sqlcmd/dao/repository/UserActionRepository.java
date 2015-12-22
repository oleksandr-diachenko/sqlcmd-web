package ua.com.juja.positiv.sqlcmd.dao.repository;

import org.springframework.data.repository.CrudRepository;
import ua.com.juja.positiv.sqlcmd.dao.entity.UserAction;

import java.util.List;

/**
 * Created by POSITIV on 21.12.2015.
 */
public interface UserActionRepository extends CrudRepository<UserAction, Integer> {

    List<UserAction> findByUserName(String userName);
}
