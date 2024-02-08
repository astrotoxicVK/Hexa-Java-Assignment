package DAO;

import entity.Booking;
import exception.InvalidBookingIDException;
import util.DButil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookinDAOImpl implements BookingDAO {

    private static final String SELECT_BOOKING_BY_ID_SQL = "SELECT * FROM Booking WHERE booking_id = ?";
    private static final String INSERT_BOOKING_SQL = "INSERT INTO Booking (customer_id, event_id, num_tickets, total_cost, booking_date) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BOOKING_SQL = "DELETE FROM Booking WHERE booking_id = ?";
    private static final String UPDATE_AVAILABLE_SEATS_SQL = "UPDATE Event SET available_seats = available_seats + ? WHERE event_id = ?";
    private static final String SELECT_BOOKING_SQL = "SELECT * FROM Booking WHERE booking_id = ?";
    private static final String SELECT_BOOKING_DETAILS_SQL = "SELECT * FROM Booking WHERE booking_id = ?";

    @Override
    public Booking getBookingById(int bookingId) throws SQLException, InvalidBookingIDException {
        Booking booking = null;
        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKING_BY_ID_SQL)) {
            preparedStatement.setInt(1, bookingId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking = new Booking();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setCustomerId(resultSet.getInt("customer_id"));
                booking.setEventId(resultSet.getInt("event_id"));
                booking.setNumTickets(resultSet.getInt("num_tickets"));
                booking.setTotalCost(resultSet.getDouble("total_cost"));
                booking.setBookingDate(resultSet.getDate("booking_date"));
            }
            else {
            throw new InvalidBookingIDException("Booking not found with ID: " + bookingId);
            }
        }
        
            catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
		return booking;
   }

    @Override
    public void createBooking(Booking booking) throws SQLException {
        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKING_SQL)) {
            preparedStatement.setInt(1, booking.getCustomerId());
            preparedStatement.setInt(2, booking.getEventId());
            preparedStatement.setInt(3, booking.getNumTickets());
            preparedStatement.setDouble(4, booking.getTotalCost());
            preparedStatement.setDate(5, booking.getBookingDate());
            preparedStatement.executeUpdate();
        }
    }
    public void cancelBooking(int bookingId) throws SQLException, InvalidBookingIDException {
        try (Connection connection = DButil.getDBConn()) {
            // Start transaction
            connection.setAutoCommit(false);

            // Retrieve the booking to find the number of tickets and event ID
            int numTickets = 0;
            int eventId = 0;
            try (PreparedStatement selectStmt = connection.prepareStatement(SELECT_BOOKING_SQL)) {
                selectStmt.setInt(1, bookingId);
                ResultSet resultSet = selectStmt.executeQuery();
                if (resultSet.next()) {
                    numTickets = resultSet.getInt("num_tickets");
                    eventId = resultSet.getInt("event_id");
                } else {
                    throw new InvalidBookingIDException("Booking not found with ID: " + bookingId);
                }
            }

            // Delete the booking
            try (PreparedStatement deleteStmt = connection.prepareStatement(DELETE_BOOKING_SQL)) {
                deleteStmt.setInt(1, bookingId);
                deleteStmt.executeUpdate();
            }
            // Update available seats in the event
            try (PreparedStatement updateStmt = connection.prepareStatement(UPDATE_AVAILABLE_SEATS_SQL)) {
                updateStmt.setInt(1, numTickets);
                updateStmt.setInt(2, eventId);
                updateStmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
   

    @Override
    public Booking getBookingDetails(int bookingId) throws SQLException {
        Booking booking = null;

        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOKING_DETAILS_SQL)) {
            preparedStatement.setInt(1, bookingId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    booking = new Booking();
                    booking.setBookingId(resultSet.getInt("booking_id"));
                    booking.setCustomerId(resultSet.getInt("customer_id"));
                    booking.setEventId(resultSet.getInt("event_id"));
                    booking.setNumTickets(resultSet.getInt("num_tickets"));
                    booking.setTotalCost(resultSet.getDouble("total_cost"));
                    booking.setBookingDate(resultSet.getDate("booking_date"));
                }
            }
        }
        return booking;
    }
    
}
