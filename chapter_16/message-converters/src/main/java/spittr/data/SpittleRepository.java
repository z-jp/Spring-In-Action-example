package spittr.data;

import spittr.Spittle;

import java.util.List;

public interface SpittleRepository {

    Spittle save(Spittle spittle);

    List<Spittle> findRecent();

    List<Spittle> findRecent(int max, int count);

    Spittle findOne(long id);

}
