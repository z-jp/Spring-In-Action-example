package spittr.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import spittr.domain.Spittle;

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
    public JdbcSpittleRepository(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Spittle save(Spittle spittle) {
        long id = insertSpittle(spittle);
        return new Spittle(id, spittle.getSpitterId(), spittle.getMessage(), spittle.getPostedTime());
    }

    @Override
    public long count() {
        return template.queryForObject("select count(id) from Spittle", Long.TYPE);
    }

    @Override
    public void delete(long id) {
        template.update("delete from Spittle where id = ?", id);
    }

    @Override
    public List<Spittle> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Spittle> findRecent(int count) {
        return template.query(SELECT_SPITTLES + " order by id desc limit ?", new SpittleRowMapper(), count);
    }

    @Override
    public List<Spittle> findBySpitterId(long spitterId) {
        return template.query(SELECT_SPITTLES + " where spitterId = ? ", new SpittleRowMapper(), spitterId);
    }

    @Override
    public Spittle findOne(long id) {
        try {
            return template.queryForObject(SELECT_SPITTLES + "where id = ?", new SpittleRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
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
