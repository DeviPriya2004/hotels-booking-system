package com.hotel.servlet;

import com.hotel.model.User;
import com.hotel.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

// Handles login, register, logout
@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();

        if ("/login".equals(action)) {
            handleLogin(req, resp);
        } else if ("/register".equals(action)) {
            handleRegister(req, resp);
        } else if ("/logout".equals(action)) {
            handleLogout(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        if ("/logout".equals(action)) {
            handleLogout(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
        }
    }

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = userService.login(req.getParameter("username"), req.getParameter("password"));
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            resp.sendRedirect(req.getContextPath() + "/index.jsp"); // or dashboard
        } else {
            resp.sendRedirect(req.getContextPath() + "/login.jsp?error=Invalid Credentials");
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        boolean success = userService.register(
                req.getParameter("username"),
                req.getParameter("password"),
                req.getParameter("fullname"),
                req.getParameter("email"));
        if (success) {
            System.out.println("Registration Successful for " + req.getParameter("username"));
            resp.sendRedirect(req.getContextPath() + "/login.jsp?msg=Registration Successful");
        } else {
            System.out.println("Registration Failed for " + req.getParameter("username"));
            resp.sendRedirect(req.getContextPath()
                    + "/register.jsp?error=Registration Failed (Username might be taken or DB error)");
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }
}
