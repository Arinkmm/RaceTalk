package com.racetalk.service;

import com.racetalk.entity.Race;

import java.util.List;
import java.util.Optional;

public interface RaceService {
    public List<Race> getUpcomingRaces();

    public List<Race> getAll();

    public Optional<Race> getById(int id);
}
