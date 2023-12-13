package io.github.pakiruchinoike.persistencia;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.github.pakiruchinoike.modelo.User;

@Repository
public class UserRepository {
    
    private static String INSERT = "insert into user values(null, ?, ?)";
    private static String SELECT_ALL = "select * from user";
    private static String UPDATE = "update user set username=?, set password=? where user_id=?";
    private static String DELETE = "delete from user where id=?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User save(User user) {
        jdbcTemplate.update(INSERT, new Object[]{user.getUsername(), user.getPassword()});
        return user;
    }

    public User update(User user) {
        jdbcTemplate.update(UPDATE, new Object[]{user.getUsername(), user.getPassword(), user.getId()});
        return user;
    }

    public User delete(User user) {
        jdbcTemplate.update(DELETE, new Object[]{user.getId()});
        return user;
    }

    public List<User> selectByUsername(String username) {
        return jdbcTemplate.query(SELECT_ALL.concat(" where username like ?"), 
        new Object[]{"%" + username + "%"}, 
        getUserMapper());
    }

    public List<User> selectAll(){
        return jdbcTemplate.query(SELECT_ALL, getUserMapper());
    }

    private RowMapper<User> getUserMapper() {
        return new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("user_id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                return new User(id, username, password);
            }
        };
    }

}
