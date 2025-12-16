<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>Manage Bookings - Admin</title>
            <link rel="stylesheet" href="../assets/css/style.css">
        </head>

        <body>
            <header>
                <h1>Admin Dashboard</h1>
                <nav>
                    <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
                    <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </nav>
            </header>

            <div class="container">
                <h2>All Bookings</h2>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>User</th>
                            <th>Room</th>
                            <th>Dates</th>
                            <th>Total</th>
                            <th>Status</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookings}">
                            <tr>
                                <td>${b.id}</td>
                                <td>${b.userName}</td>
                                <td>${b.roomNumber}</td>
                                <td>${b.checkIn} to ${b.checkOut}</td>
                                <td style="font-weight:bold;">&#8377;${b.totalPrice}</td>
                                <td>${b.status}</td>
                                <td>
                                    <c:if test="${b.status == 'CONFIRMED'}">
                                        <form action="${pageContext.request.contextPath}/bookings/cancel" method="post"
                                            style="display:inline;">
                                            <input type="hidden" name="id" value="${b.id}">
                                            <button type="submit" class="btn btn-danger"
                                                style="padding:0.3rem 0.6rem; font-size:0.8rem;">Cancel</button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </body>

        </html>