package DAO;

import entity.Event;
import exception.EventNotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface EventDOA {
    void createEvent(Event event) throws SQLException;
    int getAvailableNoOfTickets() throws SQLException;
    Event getEventDetailsById(int eventId) throws SQLException;
    Event getEventById(int eventId)  throws SQLException, EventNotFoundException;

}
