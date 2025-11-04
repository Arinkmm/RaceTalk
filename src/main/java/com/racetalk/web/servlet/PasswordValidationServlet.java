package com.racetalk.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.racetalk.exception.ServiceException;
import com.racetalk.service.UserService;
import com.racetalk.util.PasswordValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "PasswordValidation", urlPatterns = "/validate/password")
public class PasswordValidationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(PasswordValidationServlet.class);
    private final ObjectMapper mapper = new ObjectMapper();

    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
        if (userService == null) {
            throw new IllegalStateException("UserService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
        String password = req.getParameter("password");

        boolean valid = userService.validatePassword(password);

        resp.setContentType("application/json;charset=UTF-8");

        Map<String, Boolean> result = Map.of("valid", valid);
        mapper.writeValue(resp.getWriter(), result);
        } catch (ServiceException e) {
            logger.error("Failed to check password validation", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/error").forward(req, resp);
        }
    }
}
