<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <!DOCTYPE html>
        <html>

        <head>
            <title>My Bookings - Hotel Booking</title>
            <link rel="stylesheet" href="../assets/css/style.css">
            <style>
                .booking-card {
                    border: 1px solid #ddd;
                    padding: 1rem;
                    margin-bottom: 1rem;
                    border-radius: 4px;
                    background: white;
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                }

                .status-CONFIRMED {
                    color: green;
                    font-weight: bold;
                }

                .status-CANCELLED {
                    color: red;
                    font-weight: bold;
                }
            </style>
        </head>

        <body>
            <header>
                <h1>Hotel Paradise</h1>
                <nav>
                    <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
                    <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                </nav>
            </header>

            <div class="container">
                <h2>My Bookings</h2>
                <% if (request.getParameter("msg") !=null) { %>
                    <div class="alert alert-success">
                        <%= request.getParameter("msg") %>
                    </div>
                    <% } %>

                        <c:choose>
                            <c:when test="${not empty bookings}">
                                <c:forEach var="b" items="${bookings}">
                                    <div class="booking-card">
                                        <div>
                                            <h4>Room ${b.roomNumber}</h4>
                                            <p>Check-in: ${b.checkIn} | Check-out: ${b.checkOut}</p>
                                            <p>Total: &#8377;${b.totalPrice}</p>
                                            <p>Status: <span class="status-${b.status}">${b.status}</span></p>
                                        </div>
                                        <div>
                                            <c:if test="${b.status == 'CONFIRMED'}">
                                                <form action="${pageContext.request.contextPath}/bookings/cancel"
                                                    method="post"
                                                    onsubmit="return confirm('Are you sure you want to cancel?');">
                                                    <input type="hidden" name="id" value="${b.id}">
                                                    <button type="submit" class="btn btn-danger">Cancel</button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <p>You have no bookings yet.</p>
                            </c:otherwise>
                        </c:choose>
            </div>
        </body>

        </html>