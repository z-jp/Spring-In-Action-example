package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spittr.Spitter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    @Autowired
    private JdbcOperations operations;

    @Override
    public Spitter findSpitterByUsername(String username) {
        return operations.queryForObject("select id, username, password, first_name, last_name, email " +
                " from Spitter where username = ?", new SpitterRowMapper(), username);
    }

    @Override
    public void save(Spitter spitter) {
        operations.update("insert into Spitter (username, password, first_name, last_name, email)" +
                        "values (?,?,?,?,?)",
                spitter.getUsername(),
                spitter.getPassword(),
                spitter.getFirstName(),
                spitter.getLastName(),
                spitter.getEmail());
    }

    private class SpitterRowMapper implements RowMapper<Spitter> {
        @Override
        public Spitter mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Spitter(resultSet.getLong("id"),
                    resultSet.getString("username"),
                    null,
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("email"));
        }
    }

}
