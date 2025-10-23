package com.racetalk.web.servlet;

import com.racetalk.entity.Driver;
import com.racetalk.service.DriverService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "DriverProfile", urlPatterns = "/driver/*")
public class DriverProfileServlet extends HttpServlet {
    private DriverService driverService;

    @Override
    public void init() {
        driverService = (DriverService) getServletContext().getAttribute("driverService");
        if (driverService == null) {
            throw new IllegalStateException("DriverService not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String driverNumberInput = req.getParameter("driverNumber");
        if (driverNumberInput == null || driverNumberInput.trim().isEmpty()) {
            req.setAttribute("errorMessage", "Driver number is missing");
            req.setAttribute("errorCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        int driverNumber;
        try {
            driverNumber = Integer.parseInt(driverNumberInput.trim());
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "Invalid Driver number format");
            req.setAttribute("errorCode", 400);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        Optional<Driver> driverOptional = driverService.getByDriverNumber(driverNumber);
        if (driverOptional.isEmpty()) {
            req.setAttribute("errorMessage", "Driver not found");
            req.setAttribute("errorCode", 404);
            req.getRequestDispatcher("/templates/error.ftl").forward(req, resp);
            return;
        }

        Driver driver = driverOptional.get();

        req.setAttribute("driver", driver);

        req.getRequestDispatcher("/templates/driver_profile.ftl").forward(req, resp);
    }
}
