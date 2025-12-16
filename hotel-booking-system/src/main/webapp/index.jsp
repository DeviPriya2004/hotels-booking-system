<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="jakarta.tags.core" %>
        <%@ page import="com.hotel.model.User" %>
            <% User user=(User) session.getAttribute("user"); %>
                <!DOCTYPE html>
                <html>

                <head>
                    <title>Hotel Booking Management System</title>
                    <link rel="stylesheet" href="assets/css/style.css">
                </head>

                <body>
                    <header>
                        <h1>Hotel Paradise</h1>
                        <nav>
                            <% if (user==null) { %>
                                <a href="login.jsp">Login</a>
                                <a href="register.jsp">Register</a>
                                <% } else { %>
                                    <span>Welcome, <%= user.getFullName() %></span>
                                    <a href="bookings/my">My Bookings</a>
                                    <% if ("ADMIN".equals(user.getRole())) { %>
                                        <a href="bookings/all">Manage Bookings</a>
                                        <!-- <a href="admin_rooms.jsp">Manage Rooms</a> -->
                                        <% } %>
                                            <a href="${pageContext.request.contextPath}/auth/logout">Logout</a>
                                            <% } %>
                        </nav>
                    </header>

                    <div class="container">
                        <div class="hero">
                            <h2>Find Your Perfect Stay</h2>
                            <p>Luxury rooms at affordable prices</p>

                            <form action="rooms" method="get" class="search-bar">
                                <input type="hidden" name="action" value="search">
                                <div>
                                    <label
                                        style="display:block; text-align:left; font-size:0.8rem; margin-bottom:5px;">Check-in</label>
                                    <input type="date" name="checkIn" required value="${param.checkIn}">
                                </div>
                                <div>
                                    <label
                                        style="display:block; text-align:left; font-size:0.8rem; margin-bottom:5px;">Check-out</label>
                                    <input type="date" name="checkOut" required value="${param.checkOut}">
                                </div>
                                <div style="display:flex; align-items:flex-end;">
                                    <button type="submit">Check Availability</button>
                                </div>
                            </form>
                        </div>

                        <% if (request.getParameter("error") !=null) { %>
                            <div class="alert alert-danger">
                                <%= request.getParameter("error") %>
                            </div>
                            <% } %>

                                <!-- Room Listing -->
                                <h2 style="margin-bottom: 1.5rem;">Available Rooms</h2>

                                <div class="grid">
                                    <c:choose>
                                        <c:when test="${not empty rooms}">
                                            <c:forEach var="room" items="${rooms}">
                                                <div class="card">
                                                    <!-- Image Selection Logic -->
                                                    <c:set var="imgSrc"
                                                        value="https://images.unsplash.com/photo-1631049307264-da0ec9d70304?auto=format&fit=crop&w=500&q=80" />
                                                    <c:if test="${room.type == 'Double'}">
                                                        <c:set var="imgSrc"
                                                            value="https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=500&q=80" />
                                                    </c:if>
                                                    <c:if test="${room.type == 'Suite'}">
                                                        <c:set var="imgSrc"
                                                            value="https://images.unsplash.com/photo-1578683010236-d716f9a3f461?auto=format&fit=crop&w=500&q=80" />
                                                    </c:if>

                                                    <img src="${imgSrc}" alt="${room.type}"
                                                        style="height: 200px; width: 100%; object-fit: cover;">

                                                    <div class="card-content">
                                                        <h3 class="card-title">${room.type} - Room ${room.roomNumber}
                                                        </h3>
                                                        <p class="card-price">&#8377;${room.price} / night</p>
                                                        <p>${room.description}</p>

                                                        <c:choose>
                                                            <c:when test="${sessionScope.user != null}">
                                                                <c:if
                                                                    test="${not empty param.checkIn and not empty param.checkOut}">
                                                                    <form
                                                                        action="${pageContext.request.contextPath}/bookings/create"
                                                                        method="post">
                                                                        <input type="hidden" name="roomId"
                                                                            value="${room.id}">
                                                                        <input type="hidden" name="checkIn"
                                                                            value="${param.checkIn}">
                                                                        <input type="hidden" name="checkOut"
                                                                            value="${param.checkOut}">
                                                                        <button type="submit" class="btn">Book
                                                                            Now</button>
                                                                    </form>
                                                                </c:if>
                                                                <c:if test="${empty param.checkIn}">
                                                                    <p style="color: #666; font-size: 0.9rem;">Select
                                                                        dates to book</p>
                                                                </c:if>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <a href="login.jsp" class="btn">Login to Book</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <p>No rooms available. Please search for different dates or view all rooms
                                                (if implemented).</p>
                                            <!-- Fallback to show something if no search -->
                                            <c:if test="${empty rooms and empty param.action}">
                                                <p style="margin-top:10px; color: #555;">Use the search bar above to
                                                    check availability.</p>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                    </div>
                </body>

                </html>