package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Sports extends Event {

    private String sportName;
    private String teamsName; // e.g., "India vs Pakistan"

    public Sports(int eventId, String eventName, LocalDate eventDate, LocalTime eventTime, 
                  Venue venue, int totalSeats, int availableSeats, double ticketPrice, 
                  String eventType, String sportName, String teamsName) {
        super(eventId, eventName, Date.valueOf(eventDate), Time.valueOf(eventTime),
              venue, totalSeats, availableSeats, ticketPrice, eventType);
        this.sportName = sportName;
        this.teamsName = teamsName;
    }

    // Getters and Setters
    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public String getTeamsName() {
        return teamsName;
    }

    public void setTeamsName(String teamsName) {
        this.teamsName = teamsName;
    }

    // Overridden Method
    @Override
}
