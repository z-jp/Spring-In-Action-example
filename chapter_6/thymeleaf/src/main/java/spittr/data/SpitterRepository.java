package spittr.data;

import spittr.Spitter;

public interface SpitterRepository {
    Spitter findSpitterByUsername(String username);

    void save(Spitter spitter);

}
