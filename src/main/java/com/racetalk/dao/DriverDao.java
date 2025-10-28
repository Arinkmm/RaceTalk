package com.racetalk.dao;

import com.racetalk.entity.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverDao {
    void create(Driver driver);

    Optional<Driver> findByDriverNumber(int driverNumber);

    List<Driver> findAll();

    List<Driver> findDriversByTeamId(int teamId);
}
