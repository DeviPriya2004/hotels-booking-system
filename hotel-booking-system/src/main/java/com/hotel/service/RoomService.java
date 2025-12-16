package com.hotel.service;

import com.hotel.dao.RoomDAO;
import com.hotel.model.Room;
import java.sql.Date;
import java.util.List;

public class RoomService {
    private RoomDAO roomDAO = new RoomDAO();

    public List<Room> getAllRooms() {
        return roomDAO.getAllRooms();
    }

    public Room getRoomById(int id) {
        return roomDAO.getRoomById(id);
    }

    public boolean addRoom(Room room) {
        return roomDAO.addRoom(room);
    }

    public List<Room> searchAvailableRooms(String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);
            return roomDAO.searchAvailableRooms(checkIn, checkOut);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid date format");
            return List.of();
        }
    }
}
