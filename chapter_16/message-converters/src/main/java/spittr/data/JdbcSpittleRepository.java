package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import spittr.Spittle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

    private static final String SELECT_SPITTLES = "select id, message, spitterId, postedTime from Spittle ";
    private JdbcTemplate template;

    @Autowired
    public JdbcSpittleRepository(JdbcTemplate jdbcTemplate) {
        this.template = jdbcTemplate;
    }

    @Override
    public Spittle save(Spittle spittle) {
        long id = insertSpittle(spittle);
        return new Spittle(id, spittle.getSpitterId(), spittle.getMessage(), spittle.getPostedTime());
    }

    @Override
    public List<Spittle> findRecent() {
        return findRecent(Integer.MAX_VALUE, 10);
    }

    @Override
    public List<Spittle> findRecent(int max, int count) {
        return template.query(SELECT_SPITTLES + "where id < ? order by id desc limit ?", new SpittleRowMapper(), max, count);
    }

    @Override
    public Spittle findOne(long id) {
        try {
            return template.queryForObject(SELECT_SPITTLES + "where id = ?", new SpittleRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new SpittleNotFoundException(id);
        }
    }

    private long insertSpittle(Spittle spittle) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template).withTableName("Spittle");
        insert.setGeneratedKeyName("id");
        HashMap<String, Object> args = new HashMap<>(5);
        args.put("message", spittle.getMessage());
        args.put("spitterId", spittle.getSpitterId());
        args.put("postedTime", spittle.getPostedTime());
        return insert.executeAndReturnKey(args).longValue();
    }

    private final class SpittleRowMapper implements RowMapper<Spittle> {

        public Spittle mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String message = rs.getString("message");
            Date postedTime = rs.getTimestamp("postedTime");
            long spitterId = rs.getLong("spitterId");
            return new Spittle(id, spitterId, message, postedTime);
        }
    }

}
