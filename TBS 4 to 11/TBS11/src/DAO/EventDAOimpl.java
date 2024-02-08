package DAO;

import entity.Event;
import entity.Venue;
import util.DButil;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import exception.*;

public class EventDAOimpl implements EventDOA {

    private static final String INSERT_EVENT_SQL = "INSERT INTO Event (event_name, event_date, event_time, venue_id, total_seats, available_seats, ticket_price, event_type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_EVENTS_SQL = "SELECT * FROM Event e INNER JOIN Venue v ON e.venue_id = v.venue_id";
    private static final String SUM_AVAILABLE_TICKETS_SQL = "SELECT SUM(available_seats) AS total_available_tickets FROM Event";
    private static final String SELECT_EVENT_BY_ID_SQL = "SELECT * FROM Event e INNER JOIN Venue v ON e.venue_id = v.venue_id WHERE e.event_id = ?";
    private static final String SELECT_EVENT_BY_ID = "SELECT * FROM Event WHERE event_id = ?";
    @Override
    public void createEvent(Event event) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DButil.getDBConn();
            connection.setAutoCommit(false);

            // Assuming you have a VenueDAO to handle Venue DB operations
            VenueDAO venueDAO = new VenueDAOImpl();
            Venue venue = event.getVenue();

            // Check if the venue exists, and insert or update accordingly
            // This can be a method in VenueDAOImpl that handles the logic
            int venueId = venueDAO.insertOrUpdateVenue(venue, connection);

            // Set the venue ID to the Event object
            preparedStatement = connection.prepareStatement(INSERT_EVENT_SQL);
            preparedStatement.setString(1, event.getEventName());
            preparedStatement.setDate(2, event.getEventDate());
            preparedStatement.setTime(3, event.getEventTime());
            preparedStatement.setInt(4, venueId); // Set venueId here
            preparedStatement.setInt(5, event.getTotalSeats());
            preparedStatement.setInt(6, event.getAvailableSeats());
            preparedStatement.setDouble(7, event.getTicketPrice());
            preparedStatement.setString(8, event.getEventType());
            preparedStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            throw e;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    public Event getEventDetailsById(int eventId) throws SQLException {
        Event event = null;

        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_BY_ID_SQL)) {

            preparedStatement.setInt(1, eventId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    event = new Event();
                    event.setEventId(resultSet.getInt("e.event_id"));
                    event.setEventName(resultSet.getString("e.event_name"));
                    event.setEventType(resultSet.getString("e.event_type"));
                    // ... set other event properties ...

                    Venue venue = new Venue();
                    venue.setVenueId(resultSet.getInt("v.venue_id"));
                    venue.setVenueName(resultSet.getString("v.venue_name"));
                    venue.setAddress(resultSet.getString("v.address"));
                    // ... set other venue properties ...

                    event.setVenue(venue);
                }
            }
        }
        return event;
    }
    public int getAvailableNoOfTickets() throws SQLException {
        int totalAvailableTickets = 0;

        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SUM_AVAILABLE_TICKETS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                totalAvailableTickets = resultSet.getInt("total_available_tickets");
            }
        }
        return totalAvailableTickets;
    }

    @Override
    public Event getEventById(int eventId) throws SQLException, EventNotFoundException {
        Event event = null;
        try (Connection connection = DButil.getDBConn();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_EVENT_BY_ID);) {
            preparedStatement.setInt(1, eventId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                Time eventTime = rs.getTime("event_time");
                double ticketPrice = rs.getDouble("ticket_price");
                // ... extract other fields ...

                event = new Event();
                event.setEventId(eventId);
                event.setEventName(eventName);
                event.setEventDate(eventDate);
                event.setEventTime(eventTime);
                event.setTicketPrice(ticketPrice);
                // ... set other fields ...}
            }
            else {
                throw new EventNotFoundException("Event not found with ID: " + eventId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; 
        }
        return event;
    }

}
