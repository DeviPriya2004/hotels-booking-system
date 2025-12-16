package com.hotel.dao;

import com.hotel.config.DBConnection;
import com.hotel.model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms";
        try (Connection conn = DBConnection.getInstance().getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public Room getRoomById(int id) {
        String sql = "SELECT * FROM rooms WHERE id = ?";
        try (Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToRoom(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addRoom(Room room) {
        String sql = "INSERT INTO rooms (room_number, type, price, status, description, image_url) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, room.getRoomNumber());
            pstmt.setString(2, room.getType());
            pstmt.setBigDecimal(3, room.getPrice());
            pstmt.setString(4, room.getStatus());
            pstmt.setString(5, room.getDescription());
            pstmt.setString(6, room.getImageUrl());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Search available rooms for a given date range
    // A room is available if it has no confirmed/checked-in bookings that overlap
    // with the requested range
    public List<Room> searchAvailableRooms(Date checkIn, Date checkOut) {
        List<Room> rooms = new ArrayList<>();
        // Query logic: Select rooms where NOT EXISTS a booking that overlaps
        // Overlap condition: (BookStart < ReqEnd) AND (BookEnd > ReqStart)

        String sql = "SELECT * FROM rooms r WHERE r.status = 'AVAILABLE' AND NOT EXISTS (" +
                "SELECT 1 FROM bookings b WHERE b.room_id = r.id " +
                "AND b.status IN ('CONFIRMED', 'CHECKED_IN') " +
                "AND b.check_in < ? AND b.check_out > ?)";

        try (Connection conn = DBConnection.getInstance().getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, checkOut); // BookStart < ReqEnd => BookStart < checkOut
            pstmt.setDate(2, checkIn); // BookEnd > ReqStart => BookEnd > checkIn

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        return new Room(
                rs.getInt("id"),
                rs.getString("room_number"),
                rs.getString("type"),
                rs.getBigDecimal("price"),
                rs.getString("status"),
                rs.getString("description"),
                rs.getString("image_url"));
    }
}
