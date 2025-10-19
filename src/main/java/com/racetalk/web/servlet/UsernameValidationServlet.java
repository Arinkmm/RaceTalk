package com.racetalk.web.servlet;

import com.racetalk.util.UsernameValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "UsernameValidation", urlPatterns = "/validate/username")
public class UsernameValidationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");

        boolean valid = UsernameValidator.isValid(username);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"valid\": " + valid + "}");
    }
}
