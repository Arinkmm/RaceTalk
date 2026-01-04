package com.racetalk.service.impl;

import com.racetalk.dao.RaceDao;
import com.racetalk.dao.RaceResultDao;
import com.racetalk.dao.DriverDao;
import com.racetalk.dto.RaceDto;
import com.racetalk.dto.RaceResultDto;
import com.racetalk.entity.Driver;
import com.racetalk.entity.Race;
import com.racetalk.entity.RaceResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;

public class RaceImportServiceImpl implements RaceImportService {
    private static final Logger logger = LoggerFactory.getLogger(RaceImportServiceImpl.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private final RaceDao raceDao;
    private final RaceResultDao raceResultDao;
    private final DriverDao driverDao;

    public RaceImportServiceImpl(RaceDao raceDao, RaceResultDao raceResultDao, DriverDao driverDao) {
        this.raceDao = raceDao;
        this.raceResultDao = raceResultDao;
        this.driverDao = driverDao;
    }

    @Override
    public void importSeasonRacesAndResults(int year) {
        String racesUrl = "https://api.openf1.org/v1/sessions?year=" + year + "&session_name=Race";

        try {
            String racesJson = sendGetRequest(racesUrl);
            RaceDto[] races = mapper.readValue(racesJson, RaceDto[].class);

            for (RaceDto raceDto : races) {
                Optional<Race> existingRace = raceDao.findBySessionKey(raceDto.getSession_key());

                Race race = existingRace.orElseGet(Race::new);

                race.setSessionKey(raceDto.getSession_key());
                race.setLocation(raceDto.getCircuit_short_name());
                race.setRaceDate(LocalDate.parse(raceDto.getDate_start().split("T")[0]));
                race.setFinished(true);

                if (existingRace.isPresent()) {
                    raceDao.updatePastRace(race);
                } else {
                    raceDao.createPastRace(race);
                }

                String resultsUrl = "https://api.openf1.org/v1/session_result?session_key=" + raceDto.getSession_key();
                String resultsJson = sendGetRequest(resultsUrl);
                Thread.sleep(1000);

                RaceResultDto[] results = mapper.readValue(resultsJson, RaceResultDto[].class);

                for (RaceResultDto resDto : results) {
                    Driver driver = driverDao.findByDriverNumber(resDto.getDriver_number()).orElse(null);

                    Optional<RaceResult> existingResult = raceResultDao.findResultsByRaceIdAndDriverNumber(race.getId(), driver.getDriverNumber());

                    RaceResult result = existingResult.orElseGet(RaceResult::new);
                    result.setRace(race);
                    result.setDriver(driver);
                    result.setPosition(resDto.getPosition());
                    result.setPoints(resDto.getPoints());

                    if (existingResult.isPresent()) {
                        raceResultDao.update(result);
                    } else {
                        raceResultDao.create(result);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error importing season races and results for year {}", year, e);
            throw new ServiceException("Failed to import season data", e);
        }
    }

    private String sendGetRequest(String url) throws InterruptedException {
        int maxRetries = 3;

        for (int attempt = 0; attempt < maxRetries; attempt++) {
            try {
                URL getUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(45000);

                try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                    return response.toString();
                }
            } catch (SocketTimeoutException e) {
                if (attempt < maxRetries - 1) {
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                logger.error("Unexpected error for URL: {}", url, e);
                throw new ServiceException("Unexpected error", e);
            }
        }
        logger.error("Failed after {} retries for URL: {}", maxRetries, url);
        throw new ServiceException("Failed to send GET request to " + url + " after retries");
    }
}