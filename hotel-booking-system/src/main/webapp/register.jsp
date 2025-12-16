<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html>

    <head>
        <title>Register - Hotel Booking</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <header>
            <h1>Hotel Booking</h1>
            <nav>
                <a href="index.jsp">Home</a>
                <a href="login.jsp">Login</a>
            </nav>
        </header>

        <div class="container">
            <div class="form-container">
                <h2>Register</h2>
                <% if (request.getParameter("error") !=null) { %>
                    <div class="alert alert-danger">
                        <%= request.getParameter("error") %>
                    </div>
                    <% } %>

                        <form action="${pageContext.request.contextPath}/auth/register" method="post"
                            onsubmit="return validatePassword();">
                            <div class="form-group">
                                <label>Full Name</label>
                                <input type="text" name="fullname" required>
                            </div>
                            <div class="form-group">
                                <label>Email</label>
                                <input type="email" name="email" required>
                            </div>
                            <div class="form-group">
                                <label>Username</label>
                                <input type="text" name="username" required>
                            </div>
                            <div class="form-group">
                                <label>Password</label>
                                <input type="password" name="password" id="password" minlength="6" required
                                    title="Password must be at least 6 characters">
                            </div>
                            <div class="form-group">
                                <label>Confirm Password</label>
                                <input type="password" name="confirm_password" id="confirm_password" required>
                            </div>
                            <button type="submit" class="btn">Create Account</button>
                        </form>
            </div>
        </div>

        <script>
            function validatePassword() {
                var password = document.getElementById("password").value;
                var confirmPassword = document.getElementById("confirm_password").value;
                if (password != confirmPassword) {
                    alert("Passwords do not match!");
                    return false;
                }
                return true;
            }
        </script>
    </body>

    </html>