package com.racetalk.web.servlet;

import com.racetalk.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "UserEditServlet", urlPatterns = "/user/edit")
public class UserEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        req.setAttribute("user", currentUser);
        req.getRequestDispatcher("/templates/user_edit.ftl").forward(req, resp);
    }
}