package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Concert extends Event {
    private String artist;
    private String type; // Theatrical, Classical, Rock, Recital

    // Constructors
    public Concert(int eventId, String eventName, LocalDate eventDate, LocalTime eventTime, 
                   Venue venue, int totalSeats, int availableSeats, double ticketPrice, 
                   String eventType, String artist, String type) {
        super(eventId, eventName, Date.valueOf(eventDate), Time.valueOf(eventTime),
              venue, totalSeats, availableSeats, ticketPrice, eventType);
        this.artist = artist;
        this.type = type;
    }

    // Getters and Setters
    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

-

}
