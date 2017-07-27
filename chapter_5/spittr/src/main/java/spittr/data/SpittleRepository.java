package spittr.data;

import spittr.Spittle;

import java.util.List;

public interface SpittleRepository {

    List<Spittle> findSpittles(long max, int count);

    Spittle findSpittle(long id);

    void save(Spittle spittle);

}
