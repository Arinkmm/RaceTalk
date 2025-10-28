package com.racetalk.dao.impl;

import com.racetalk.dao.DriverDao;
import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Team;
import com.racetalk.util.DatabaseConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverDaoImpl implements DriverDao {
    private final DatabaseConnectionUtil databaseConnection;
    private final TeamDao teamDao;

    public DriverDaoImpl(DatabaseConnectionUtil databaseConnection, TeamDao teamDao) {
        this.databaseConnection = databaseConnection;
        this.teamDao = teamDao;
    }

    @Override
    public void create(Driver driver) {
        String sql = "INSERT INTO drivers (driver_number, team_id, first_name, last_name, date_of_birth, country, photoUrl) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driver.getDriverNumber());
            if (driver.getTeam() != null) {
                ps.setInt(2, driver.getTeam().getId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setString(3, driver.getFirstName());
            ps.setString(4, driver.getLastName());
            ps.setDate(5, Date.valueOf(driver.getDateOfBirth()));
            ps.setString(6, driver.getCountry());
            ps.setString(7, driver.getPhoto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Driver> findByDriverNumber(int driverNumber) {
        String sql = "SELECT * FROM drivers WHERE driver_number = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, driverNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Optional.of(createDriverFromResultSet(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Driver> findAll() {
        String sql = "SELECT * FROM drivers ORDER BY driver_number";
        List<Driver> drivers = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                drivers.add(createDriverFromResultSet(rs));
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Driver> findDriversByTeamId(int teamId) {
        String sql = "SELECT * FROM drivers WHERE team_id = ?";
        List<Driver> drivers = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, teamId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                drivers.add(createDriverFromResultSet(rs));
            }
            return drivers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Driver createDriverFromResultSet(ResultSet rs) {
        Driver driver = new Driver();
        try {
            driver.setDriverNumber(rs.getInt("driver_number"));
            int teamId = rs.getInt("team_id");
            Team team = teamDao.findById(teamId).orElse(null);
            driver.setTeam(team);

            driver.setFirstName(rs.getString("first_name"));
            driver.setLastName(rs.getString("last_name"));

            Date dob = rs.getDate("date_of_birth");
            driver.setDateOfBirth(dob.toLocalDate());

            driver.setCountry(rs.getString("country"));
            if (!rs.wasNull()) {
                driver.setPhoto(rs.getString("photo"));
            } else {
                driver.setPhoto(null);
            }
            return driver;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

