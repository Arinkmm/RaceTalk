package com.racetalk.dao;

import com.racetalk.entity.Race;

import java.util.List;
import java.util.Optional;

public interface RaceDao {
    public void create(Race race);

    public void update(Race race);

    public boolean existsById(int id);

    public List<Race> findUpcomingRaces();

    Optional<Race> findBySessionKey(int sessionKey);
}
