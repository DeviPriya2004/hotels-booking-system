package com.hotel.servlet;

import com.hotel.model.Room;
import com.hotel.service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {
    private RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("search".equals(action)) {
            String checkIn = req.getParameter("checkIn");
            String checkOut = req.getParameter("checkOut");
            List<Room> rooms = roomService.searchAvailableRooms(checkIn, checkOut);
            req.setAttribute("rooms", rooms);
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            // List all (admin mostly, or default home)
            List<Room> rooms = roomService.getAllRooms();
            req.setAttribute("rooms", rooms);
            // If admin, send to admin page, else index
            // Simplified for now
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }

    // Admin add room
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Implement admin check here or in filter
        String roomNumber = req.getParameter("roomNumber");
        String type = req.getParameter("type");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        String description = req.getParameter("description");

        Room room = new Room();
        room.setRoomNumber(roomNumber);
        room.setType(type);
        room.setPrice(price);
        room.setStatus("AVAILABLE");
        room.setDescription(description);
        room.setImageUrl("default.jpg"); // Placeholder

        if (roomService.addRoom(room)) {
            resp.sendRedirect(req.getContextPath() + "/rooms?msg=Room Added");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin/add-room.jsp?error=Failed");
        }
    }
}
