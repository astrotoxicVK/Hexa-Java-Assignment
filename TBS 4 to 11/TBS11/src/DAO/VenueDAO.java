package DAO;

import entity.Venue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface VenueDAO {
	
    void createVenue(Venue venue) throws SQLException;
    Venue getVenueById(int venueId) throws SQLException;
    List<Venue> getAllVenues() throws SQLException;
    void updateVenue(Venue venue) throws SQLException;
    void deleteVenue(int venueId) throws SQLException;
    int insertOrUpdateVenue(Venue venue, Connection connection) throws SQLException;	
}
