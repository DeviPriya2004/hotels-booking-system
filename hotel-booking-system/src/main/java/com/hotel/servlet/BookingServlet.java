package com.hotel.servlet;

import com.hotel.model.Booking;
import com.hotel.model.User;
import com.hotel.service.BookingService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/bookings/*")
public class BookingServlet extends HttpServlet {
    private BookingService bookingService = new BookingService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if ("/create".equals(action)) {
            int roomId = Integer.parseInt(req.getParameter("roomId"));
            String checkIn = req.getParameter("checkIn");
            String checkOut = req.getParameter("checkOut");

            boolean success = bookingService.bookRoom(user.getId(), roomId, checkIn, checkOut);
            if (success) {
                resp.sendRedirect(req.getContextPath() + "/bookings/my?msg=Booking Confirmed");
            } else {
                resp.sendRedirect(req.getContextPath() + "/index.jsp?error=Booking Failed or Room Unavailable");
            }
        } else if ("/cancel".equals(action)) {
            int bookingId = Integer.parseInt(req.getParameter("id"));
            bookingService.cancelBooking(bookingId);
            resp.sendRedirect(req.getContextPath() + "/bookings/my?msg=Booking Cancelled");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        if ("/my".equals(action)) {
            List<Booking> bookings = bookingService.getUserBookings(user.getId());
            req.setAttribute("bookings", bookings);
            req.getRequestDispatcher("/my_bookings.jsp").forward(req, resp);
        } else if ("/all".equals(action) && "ADMIN".equals(user.getRole())) {
            List<Booking> bookings = bookingService.getAllBookings();
            req.setAttribute("bookings", bookings);
            req.getRequestDispatcher("/admin_bookings.jsp").forward(req, resp);
        }
    }
}
