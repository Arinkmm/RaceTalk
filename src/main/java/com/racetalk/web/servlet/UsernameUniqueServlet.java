package com.racetalk.web.servlet;

import com.racetalk.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UsernameUnique", urlPatterns = "/validate/username-unique")
public class UsernameUniqueServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new ServletException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");

        boolean unique = userService.isUsernameUnique(username);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"unique\": " + unique + "}");
    }
}
