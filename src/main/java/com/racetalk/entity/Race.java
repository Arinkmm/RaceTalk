package com.racetalk.entity;

import java.time.LocalDate;

public class Race {
    private int id;
    private int sessionKey;
    private String name;
    private String location;
    private LocalDate raceDate;
    private boolean isFinished;

    public Race() {}

    public Race(int id, int sessionKey, String name, String location, LocalDate raceDate, boolean isFinished) {
        this.id = id;
        this.sessionKey = sessionKey;
        this.name = name;
        this.location = location;
        this.raceDate = raceDate;
        this.isFinished = isFinished;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getSessionKey() { return sessionKey; }
    public void setSessionKey(int sessionKey) { this.sessionKey = sessionKey; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public LocalDate getRaceDate() { return raceDate; }
    public void setRaceDate(LocalDate raceDate) { this.raceDate = raceDate; }

    public boolean isFinished() { return isFinished; }
    public void setFinished(boolean finished) { isFinished = finished; }
}
