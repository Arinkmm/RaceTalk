package com.racetalk.service;

import com.racetalk.entity.RaceResult;

import java.util.List;

public interface RaceResultService {
    List<RaceResult> getResultsByDriverNumber(int driverNumber);

    List<RaceResult> getResultsByRaceId(int raceId);

    List<RaceResult> getResultsByTeamId(int teamId);
}
