package com.racetalk.web.servlet;

import com.cloudinary.utils.ObjectUtils;
import com.racetalk.entity.User;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordHasherUtil;
import com.racetalk.util.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@WebServlet(name = "UserProfile", urlPatterns = "/user/profile/*")
public class UserProfileServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UserProfileServlet.class);

    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new IllegalStateException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo == null) {
            req.setAttribute("errorMessage", "Id пользователя не найдено");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        String userIdStr = pathInfo.substring(1);
        int userId;
        try {
            userId = Integer.parseInt(userIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Неверный формат ID пользователя");
            req.setAttribute("statusCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        try {
            Optional<User> currentUserOpt = userService.getById(userId);
            if (currentUserOpt.isEmpty()) {
                req.setAttribute("errorMessage", "Пользователь не найден");
                req.setAttribute("statusCode", 404);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            User currentUser = currentUserOpt.get();
            req.setAttribute("user", currentUser);

            User loggedUser = (User) req.getSession().getAttribute("user");

            boolean isOwner = false;
            boolean canDelete = false;
            boolean canEdit = false;

            if (loggedUser != null) {
                isOwner = loggedUser.getId() == currentUser.getId();
                canEdit = isOwner;
                canDelete = userService.isAdmin(loggedUser) || isOwner;
            }

            req.setAttribute("isOwner", isOwner);
            req.setAttribute("canDelete", canDelete);
            req.setAttribute("canEdit", canEdit);

            req.getRequestDispatcher("/templates/user_profile.ftl").forward(req, resp);

        } catch (ServiceException e) {
            logger.error("Error loading user profile", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("delete".equals(action)) {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null) {
                req.setAttribute("errorMessage", "Id пользователя не найдено");
                req.setAttribute("statusCode", 400);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            int userId;
            try {
                userId = Integer.parseInt(pathInfo.substring(1));
            } catch (NumberFormatException e) {
                req.setAttribute("errorMessage", "Неверный формат ID пользователя");
                req.setAttribute("statusCode", 400);
                req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                return;
            }

            try {
                Optional<User> currentUserOpt = userService.getById(userId);
                if (currentUserOpt.isEmpty()) {
                    req.setAttribute("errorMessage", "Пользователь не найден");
                    req.setAttribute("statusCode", 404);
                    req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
                    return;
                }

                userService.deleteUser(userId);
                resp.sendRedirect(req.getContextPath() + "/");
            } catch (Exception e) {
                logger.error("Error deleting user", e);
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                req.getRequestDispatcher("/error").forward(req, resp);
            }
        }
    }
}