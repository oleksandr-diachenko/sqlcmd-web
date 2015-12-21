package ua.com.juja.positiv.sqlcmd.databasemanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by POSITIV on 21.12.2015.
 */
@Repository
public class UserActionsDaoImpl implements UserActionsDao {

    private JdbcTemplate template;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void log(String userName, String dbName, String userAction) {
        template.update("INSERT INTO public.user_actions" +
                "(user_name, db_name, action)" +
                " values (?, ?, ?)",
                userName, dbName, userAction);
    }

    @Override
    public List<UserAction> getAllFor(String userName) {
        return template.query("SELECT * FROM public.user_actions " +
                "WHERE user_name = ?",new Object[] {userName},
                    new RowMapper<UserAction>() {
                        public UserAction mapRow(ResultSet resultSet, int rowNum)
                                throws SQLException {
                            UserAction result = new UserAction();
                            result.setId(resultSet.getInt("id"));
                            result.setUserName(resultSet.getString("userName"));
                            result.setDbName(resultSet.getString("dbName"));
                            result.setUserAction(resultSet.getString("userAction"));
                            return result;
                        }
                });
    }
}
