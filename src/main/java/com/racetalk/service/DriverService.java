package com.racetalk.service;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverService {
    void updateDriverTeam(int driverNumber, int newTeam);

    List<Driver> getAllDrivers();

    Optional<Driver> getByDriverNumber(int driverNumber);

    List<Driver> getDriversByTeamId(int teamId);
}
