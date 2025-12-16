package com.hotel.servlet;

import com.hotel.config.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/seed-rooms")
public class SeedDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String[] sqlStatements = {
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('103', 'Single', 5200.00, 'Cozy single room with city view', 'single_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('104', 'Double', 8500.00, 'Spacious double room with garden view', 'double_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('105', 'Single', 4800.00, 'Standard single room', 'single_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('202', 'Suite', 16000.00, 'Luxury suite with panoramic view', 'suite.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('203', 'Double', 9000.00, 'Premium double room', 'double_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('204', 'Single', 5100.00, 'Modern single room near elevator', 'single_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('301', 'Suite', 18000.00, 'Presidential suite', 'suite.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('302', 'Double', 8200.00, 'Double room with extra bed', 'double_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('303', 'Single', 5500.00, 'Deluxe single room', 'single_room.jpg')",
                "INSERT INTO rooms (room_number, type, price, description, image_url) VALUES ('304', 'Double', 8800.00, 'Double room with balcony', 'double_room.jpg')"
        };

        try (Connection conn = DBConnection.getInstance().getConnection()) {
            int count = 0;
            for (String sql : sqlStatements) {
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.executeUpdate();
                    count++;
                } catch (SQLException e) {
                    out.println("<p style='color:orange'>Skipped (maybe duplicate): " + e.getMessage() + "</p>");
                }
            }
            out.println("<h2 style='color:green'>Successfully added " + count + " new rooms!</h2>");
            out.println("<a href='index.jsp'>Go to Home Page</a>");

        } catch (Exception e) {
            out.println("<h2 style='color:red'>Database Error</h2>");
            e.printStackTrace(out);
        }
    }
}
