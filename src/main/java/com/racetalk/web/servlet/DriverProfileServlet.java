package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.entity.RaceResult;
import com.racetalk.entity.Team;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "DriverProfile", urlPatterns = "/driver/*")
public class DriverProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DriverProfileServlet.class);

    private DriverService driverService;
    private RaceResultService raceResultService;
    private UserService userService;
    private TeamService teamService;

    @Override
    public void init() {
        driverService = (DriverService) getServletContext().getAttribute("driverService");
        raceResultService = (RaceResultService) getServletContext().getAttribute("raceResultService");
        userService = (UserService) getServletContext().getAttribute("userService");
        teamService = (TeamService) getServletContext().getAttribute("teamService");
        if (driverService == null || raceResultService == null || userService == null || teamService == null) {
            throw new IllegalStateException("Services are not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "Номер гонщика не найден");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String driverNumberStr = pathInfo.substring(1);
        int driverNumber;
        try {
            driverNumber = Integer.parseInt(driverNumberStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат номера гонщика");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            Optional<Driver> driverOptional = driverService.getByDriverNumber(driverNumber);
            if (driverOptional.isEmpty()) {
                req.setAttribute("errorMessage", "Гонщик не найден");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }


            Driver driver = driverOptional.get();
            req.setAttribute("driver", driver);

            List<RaceResult> raceResults = raceResultService.getResultsByDriverNumber(driverNumber);
            req.setAttribute("raceResults", raceResults);

            List<Team> teams = teamService.getAllTeams();
            req.setAttribute("teams", teams);

            User loggedUser = (User) req.getSession().getAttribute("user");
            req.setAttribute("user", loggedUser);

            boolean isAdmin = userService.isAdmin(loggedUser);
            req.setAttribute("isAdmin", isAdmin);


            req.getRequestDispatcher("/templates/driver_profile.ftl").forward(req, resp);
        } catch (ServiceException e) {
            logger.error("Error loading driver profile for driver number {}", driverNumber, e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null || !userService.isAdmin(currentUser)) {
            req.setAttribute("errorMessage", "Доступ запрещён");
            req.setAttribute("statusCode", 403);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "Номер гонщика не найден");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String driverNumberStr = pathInfo.substring(1);
        int driverNumber;
        try {
            driverNumber = Integer.parseInt(driverNumberStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат номера гонщика");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String newTeamIdStr = req.getParameter("team");

        try {
            int newTeamId = Integer.parseInt(newTeamIdStr);
            driverService.updateDriverTeam(driverNumber, newTeamId);
            resp.sendRedirect(req.getContextPath() + "/driver/" + driverNumber);
        } catch (ServiceException e) {
            logger.error("Error updating driver team", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
