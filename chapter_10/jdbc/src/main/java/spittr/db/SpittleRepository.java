package spittr.db;

import spittr.domain.Spittle;

import java.util.List;

public interface SpittleRepository {

    Spittle save(Spittle spittle);

    long count();

    void delete(long id);

    List<Spittle> findRecent();

    List<Spittle> findRecent(int count);

    List<Spittle> findBySpitterId(long spitterId);

    Spittle findOne(long id);

}
