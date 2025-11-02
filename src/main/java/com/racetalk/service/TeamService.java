package com.racetalk.service;

import com.racetalk.entity.Team;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface TeamService {
    void createTeam(Team team, InputStream photoInputStream);

    List<Team> getAllTeams();

    Optional<Team> getById(int id);
}
