<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Login - Hotel Booking</title>
            <link rel="stylesheet" href="assets/css/style.css">
        </head>

        <body>
            <header>
                <h1>Hotel Booking</h1>
                <nav>
                    <a href="index.jsp">Home</a>
                    <a href="register.jsp">Register</a>
                </nav>
            </header>

            <div class="container">
                <div class="form-container">
                    <h2>Login</h2>
                    <% if (request.getParameter("error") !=null) { %>
                        <div class="alert alert-danger">
                            <%= request.getParameter("error") %>
                        </div>
                        <% } %>
                            <% if (request.getParameter("msg") !=null) { %>
                                <div class="alert alert-success">
                                    <%= request.getParameter("msg") %>
                                </div>
                                <% } %>

                                    <form action="${pageContext.request.contextPath}/auth/login" method="post">
                                        <div class="form-group">
                                            <label>Username</label>
                                            <input type="text" name="username" required>
                                        </div>
                                        <div class="form-group">
                                            <label>Password</label>
                                            <input type="password" name="password" required>
                                        </div>
                                        <button type="submit" class="btn">Login</button>
                                    </form>
                                    <p style="margin-top: 1rem; text-align: center;">
                                        Don't have an account? <a href="register.jsp">Register here</a>
                                    </p>
                </div>
            </div>
        </body>

        </html>