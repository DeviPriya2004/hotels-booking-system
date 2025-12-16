package com.hotel.service;

import com.hotel.dao.BookingDAO;
import com.hotel.dao.RoomDAO;
import com.hotel.model.Booking;
import com.hotel.model.Room;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO = new BookingDAO();
    private RoomDAO roomDAO = new RoomDAO();

    public boolean bookRoom(int userId, int roomId, String checkInStr, String checkOutStr) {
        try {
            Date checkIn = Date.valueOf(checkInStr);
            Date checkOut = Date.valueOf(checkOutStr);

            // Validation: Check dates
            if (checkIn.after(checkOut) || checkIn.equals(checkOut)) {
                return false;
            }

            // Validation: Check availability
            if (!bookingDAO.isRoomAvailable(roomId, checkIn, checkOut)) {
                return false;
            }

            // Calculate total price
            Room room = roomDAO.getRoomById(roomId);
            if (room == null)
                return false;

            long nights = ChronoUnit.DAYS.between(checkIn.toLocalDate(), checkOut.toLocalDate());
            BigDecimal totalPrice = room.getPrice().multiply(new BigDecimal(nights));

            Booking booking = new Booking();
            booking.setUserId(userId);
            booking.setRoomId(roomId);
            booking.setCheckIn(checkIn);
            booking.setCheckOut(checkOut);
            booking.setTotalPrice(totalPrice);
            booking.setStatus("CONFIRMED");

            return bookingDAO.createBooking(booking);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Booking> getUserBookings(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }

    public List<Booking> getAllBookings() {
        return bookingDAO.getAllBookings(); // For admin
    }

    public boolean cancelBooking(int bookingId) {
        return bookingDAO.updateStatus(bookingId, "CANCELLED");
    }
}
