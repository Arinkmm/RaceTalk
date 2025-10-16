package com.racetalk.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DataScheduler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final RaceImportService raceImportService;
    private final int year;

    public DataScheduler(RaceImportService raceImportService, int year) {
        this.raceImportService = raceImportService;
        this.year = year;
    }

    public void start() {
        scheduler.scheduleAtFixedRate(() -> {
            try {
                raceImportService.importSeasonRacesAndResults(year);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 24, TimeUnit.HOURS);
    }

    public void stop() {
        scheduler.shutdown();
    }
}
