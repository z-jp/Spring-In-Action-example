package spittr.db;

import spittr.domain.Spitter;

import java.util.List;

public interface SpitterRepository {

    Spitter save(Spitter spitter);

    long count();

    List<Spitter> findAll();

    Spitter findByUsername(String username);

    Spitter findById(long id);
}
