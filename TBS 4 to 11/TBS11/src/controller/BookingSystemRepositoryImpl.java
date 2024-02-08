
//MIGHT BE OVERRIDE DUE TO REPEADTED NATURE

/*package controller;

import util.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DAO.EventDOA;
import entity.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;


public class BookingSystemRepositoryImpl implements IBookingSystemRepository {
    private EventDOA eventDao = new EventDOA();
    private Scanner scanner = new Scanner(System.in);

    // Implementing the method as declared in IBookingSystemRepository
    @Override
    
        public Event createEvent() {
            try {
                System.out.println("Enter Event Name:");
                String eventName = scanner.nextLine();

                System.out.println("Enter Event Date (YYYY-MM-DD):");
                LocalDate date = LocalDate.parse(scanner.nextLine());

                System.out.println("Enter Event Time (HH:MM):");
                LocalTime time = LocalTime.parse(scanner.nextLine());

                System.out.println("Enter Total Seats:");
                int totalSeats = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter Ticket Price:");
                float ticketPrice = Float.parseFloat(scanner.nextLine());

                System.out.println("Enter Event Type:");
                String eventType = scanner.nextLine();

                Event newEvent = new Event(eventName, date, time, totalSeats, ticketPrice, eventType);
                return eventDao.createEvent(newEvent);

            } catch (DateTimeParseException e) {
                System.err.println("Invalid date or time format. Please try again.");
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format. Please try again.");
            }

            return null;
        }
    // Other methods...


    // Other methods...

       
    @Override
    public List<Event> getEventDetails() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM Events";
        try (Connection conn = DButil.getDBConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Event event = new Event(
                        0, rs.getString("event_name"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getTime("event_time").toLocalTime(),
                        rs.getInt("total_seats"),
                        rs.getInt("available_seats"),
                        0, rs.getDouble("ticket_price"),
                        rs.getString("event_type")
                );
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public int getAvailableNoOfTickets(int eventId) {
        String sql = "SELECT available_seats FROM Events WHERE event_id = ?";
        try (Connection conn = DButil.getDBConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("available_seats");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static final String SELECT_BOOKING_BY_ID_SQL = "SELECT * FROM Booking WHERE booking_id = ?";
    private static final String INSERT_BOOKING_SQL = "INSERT INTO Booking (customer_id, event_id, num_tickets, total_cost, booking_date) VALUES (?, ?, ?, ?, ?)";
    private static final String DELETE_BOOKING_SQL = "DELETE FROM Booking WHERE booking_id = ?";
    private static final String UPDATE_AVAILABLE_SEATS_SQL = "UPDATE Event SET available_seats = available_seats + ? WHERE event_id = ?";
    private static final String SELECT_BOOKING_SQL = "SELECT * FROM Booking WHERE booking_id = ?";
    private static final String SELECT_BOOKING_DETAILS_SQL = "SELECT * FROM Booking WHERE booking_id = ?";

    @Override
    public Booking getBookingById(int bookingId) throws SQLException {
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
    public void cancelBooking(int bookingId) throws SQLException {
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
                    throw new SQLException("Booking not found with ID: " + bookingId);
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
*/