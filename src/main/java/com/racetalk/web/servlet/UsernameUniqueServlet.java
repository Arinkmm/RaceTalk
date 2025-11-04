package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "UsernameUnique", urlPatterns = "/validate/username-unique")
public class UsernameUniqueServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UsernameUniqueServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new ServletException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            String username = req.getParameter("username");
            boolean unique = userService.isUsernameUnique(username);

            resp.setContentType("application/json;charset=UTF-8");

            Map<String, Boolean> result = Map.of("unique", unique);
            mapper.writeValue(resp.getWriter(), result);
        } catch (ServiceException e) {
            logger.error("Failed to check username unique", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
