package com.racetalk.web.servlet;

import com.racetalk.entity.Race;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.RaceService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "RaceCreate", urlPatterns = "/race/create")
public class RaceCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(RaceCreateServlet.class);

    private RaceService raceService;
    private UserService userService;

    @Override
    public void init() {
        raceService = (RaceService) getServletContext().getAttribute("raceService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (raceService == null || userService == null) {
            throw new IllegalStateException("Services not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (!userService.isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        req.getRequestDispatcher("/templates/race_create.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (!userService.isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String location = req.getParameter("location");
        String dateStr = req.getParameter("raceDate");

        try {
            LocalDate raceDate = LocalDate.parse(dateStr);
            Race race = new Race();
            race.setLocation(location);
            race.setRaceDate(raceDate);

            raceService.addRace(race);

            resp.sendRedirect(req.getContextPath() + "/main");
        } catch (ServiceException e) {
            logger.error("Failed to create race", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}