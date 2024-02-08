package DAO;

import entity.Booking;
import exception.InvalidBookingIDException;

import java.sql.SQLException;

public interface BookingDAO {
    Booking getBookingById(int bookingId) throws SQLException,InvalidBookingIDException ;
    void createBooking(Booking booking) throws SQLException;
    void cancelBooking(int bookingId) throws SQLException, InvalidBookingIDException;
    Booking getBookingDetails(int bookingId) throws SQLException,InvalidBookingIDException;
    // Other methods as needed
}
