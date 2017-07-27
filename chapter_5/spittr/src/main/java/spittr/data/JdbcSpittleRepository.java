package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spittr.Spittle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSpittleRepository implements SpittleRepository {

    @Autowired
    private JdbcOperations operations;

    @Override
    public List<Spittle> findSpittles(long max, int count) {
        return operations.query("select id, message, created_at, latitude, longitude " +
                "from Spittle " +
                "where id < ? order by created_at desc limit 20", new spittleRowMapper(), max);
    }

    @Override
    public Spittle findSpittle(long id) {
        return operations.queryForObject("select id, message, created_at, latitude, longitude from Spittle " +
                "where id = ?", new spittleRowMapper(), id);
    }

    @Override
    public void save(Spittle spittle) {
        operations.update("insert into Spittle(message, created_at, latitude, longitude) " +
                        "values(?,?,?,?)",
                spittle.getMessage(),
                spittle.getTime(),
                spittle.getLatitude(),
                spittle.getLongitude());
    }

    private class spittleRowMapper implements RowMapper<Spittle> {
        @Override
        public Spittle mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Spittle(resultSet.getLong("id"),
                    resultSet.getString("message"),
                    resultSet.getTime("created_at"),
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"));
        }
    }
}
