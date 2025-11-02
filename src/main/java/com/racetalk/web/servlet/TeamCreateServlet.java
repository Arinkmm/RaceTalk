package com.racetalk.web.servlet;

import com.racetalk.entity.Team;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.TeamService;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "TeamCreateServlet", urlPatterns = "/team/create")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class TeamCreateServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(TeamCreateServlet.class);

    private TeamService teamService;
    private UserService userService;

    @Override
    public void init() {
        teamService = (TeamService) getServletContext().getAttribute("teamService");
        userService = (UserService) getServletContext().getAttribute("userService");
        if (teamService == null || userService == null) {
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
        req.getRequestDispatcher("/templates/team_create.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (!userService.isAdmin(user)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        try {
            String name = req.getParameter("name");
            String country = req.getParameter("country");

            Team team = new Team();
            team.setName(name);
            team.setCountry(country);

            Part photoPart = req.getPart("photo");
            InputStream photoInputStream = photoPart.getInputStream();

            teamService.createTeam(team, photoInputStream);

            resp.sendRedirect(req.getContextPath() + "/teams");
        } catch (ServiceException e) {
            logger.error("Failed to create team", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}