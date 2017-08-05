package spittr.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import spittr.domain.Spitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    private static final String SELECT_SPITTER = "select id, username, password, fullname, email, updateByEmail from Spitter ";
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private JdbcTemplate template;

    @Autowired
    public JdbcSpitterRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate template) {
        this.template = template;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Spitter save(Spitter spitter) {
        if (spitter.getId() == null) {
            long id = insertSpitter(spitter);
            return new Spitter(id, spitter.getUsername(), spitter.getPassword(),
                    spitter.getFullName(), spitter.getEmail(), spitter.isUpdateByEmail());
        } else {
            SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(spitter);
            namedParameterJdbcTemplate.update("update Spitter set username= :username, password= :password, fullName= :fullName, email= :email, updateByEmail= :updateByEmail " +
                    " where id = :id", parameterSource);
        }
        return spitter;
    }

    private long insertSpitter(Spitter spitter) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("INSERT into spitter (username, email, fullName, password, updateByEmail)" +
                        "values(?,?,?,?,?)");
                ps.setString(1, spitter.getUsername());
                ps.setString(2, spitter.getEmail());
                ps.setString(3, spitter.getFullName());
                ps.setString(4, spitter.getPassword());
                ps.setBoolean(5, spitter.isUpdateByEmail());
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }

    @Override
    public long count() {
        return template.queryForObject("select count(id) from Spitter", Long.TYPE);
    }

    @Override
    public List<Spitter> findAll() {
        return template.query(SELECT_SPITTER, new SpitterRowMapper());
    }

    @Override
    public Spitter findByUsername(String username) {
        return template.queryForObject(SELECT_SPITTER + " where username = ?", new SpitterRowMapper(), username);
    }

    @Override
    public Spitter findById(long id) {
        return template.queryForObject(SELECT_SPITTER + " where id = ?", new SpitterRowMapper(), id);
    }

    private final class SpitterRowMapper implements RowMapper<Spitter> {

        public Spitter mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String fullName = rs.getString("fullname");
            String email = rs.getString("email");
            boolean updateByEmail = rs.getBoolean("updateByEmail");
            return new Spitter(id, username, password, fullName, email, updateByEmail);
        }
    }
}
