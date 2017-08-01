package spittr.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import spittr.domain.Spitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcSpitterRepository implements SpitterRepository {

    private static final String SELECT_SPITTER = "select id, username, password, fullname, email, updateByEmail from Spitter ";
    private JdbcTemplate template;

    @Autowired
    public JdbcSpitterRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Spitter save(Spitter spitter) {
        if (spitter.getId() == null) {
            long id = insertSpitter(spitter);
            return new Spitter(id, spitter.getUsername(), spitter.getPassword(),
                    spitter.getFullName(), spitter.getEmail(), spitter.isUpdateByEmail());
        } else {
            template.update("update Spitter set username= ?, password= ?, fullName= ?, email= ?, updateByEmail= ? " +
                            " where id = ?",
                    spitter.getUsername(),
                    spitter.getPassword(),
                    spitter.getFullName(),
                    spitter.getEmail(),
                    spitter.isUpdateByEmail(),
                    spitter.getId());
        }
        return spitter;
    }

    private long insertSpitter(Spitter spitter) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template).withTableName("spitter");
        insert.setGeneratedKeyName("id");
        HashMap<String, Object> args = new HashMap<>();
        args.put("username", spitter.getUsername());
        args.put("email", spitter.getEmail());
        args.put("fullName", spitter.getFullName());
        args.put("password", spitter.getPassword());
        args.put("updateByEmail", spitter.isUpdateByEmail());
        return insert.executeAndReturnKey(args).longValue();
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
