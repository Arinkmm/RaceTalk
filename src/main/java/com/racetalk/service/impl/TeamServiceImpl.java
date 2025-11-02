package com.racetalk.service.impl;

import com.cloudinary.Cloudinary;
import com.racetalk.dao.TeamDao;
import com.racetalk.entity.Team;
import com.racetalk.exception.DataAccessException;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TeamServiceImpl implements TeamService {
    private static final Logger logger = LoggerFactory.getLogger(TeamServiceImpl.class);
    private final TeamDao teamDao;
    private final Cloudinary cloudinary;

    public TeamServiceImpl(TeamDao teamDao, Cloudinary cloudinary) {
        this.teamDao = teamDao;
        this.cloudinary = cloudinary;
    }

    @Override
    public void createTeam(Team team, InputStream photoInputStream) {
        Map uploadResult;
        try {
            if (photoInputStream != null && photoInputStream.available() > 0) {
                byte[] photoBytes = photoInputStream.readAllBytes();

                uploadResult = cloudinary.uploader().upload(photoBytes, new HashMap<>());

                String photoUrl = (String) uploadResult.get("secure_url");
                team.setPhoto(photoUrl);
            }
            teamDao.create(team);
        } catch (Exception e) {
            logger.error("Failed to create team", e);
            throw new ServiceException("Failed to create team with photo", e);
        }
    }

    @Override
    public List<Team> getAllTeams() {
        try {
            return teamDao.findAll();
        } catch (DataAccessException e) {
            logger.error("Failed to get all teams", e);
            throw new ServiceException("Unable to get all teams", e);
        }
    }

    @Override
    public Optional<Team> getById(int id) {
        try {
            return teamDao.findById(id);
        } catch (DataAccessException e) {
            logger.error("Failed to get team by id {}", id, e);
            throw new ServiceException("Unable to get team by id " + id, e);
        }
    }
}

