package com.racetalk.web.servlet;

import com.racetalk.util.PasswordValidator;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "PasswordValidation", urlPatterns = "/validate/password")
public class PasswordValidationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String password = req.getParameter("password");

        boolean valid = PasswordValidator.isValid(password);

        resp.setContentType("application/json");
        resp.getWriter().write("{\"valid\": " + valid + "}");
    }
}
