package entity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class Movie extends Event {
    private String genre;
    private String actorName;
    private String actressName;

    // Constructors
    public Movie(int eventId, String eventName, LocalDate eventDate, LocalTime eventTime, 
                 Venue venue, int totalSeats, int availableSeats, double ticketPrice, 
                 String eventType, String genre, String actorName, String actressName) {
        // Convert LocalDate and LocalTime to sql.Date and sql.Time
        super(eventId, eventName, Date.valueOf(eventDate), Time.valueOf(eventTime),
              venue, totalSeats, availableSeats, ticketPrice, eventType);
        this.genre = genre;
        this.actorName = actorName;
        this.actressName = actressName;
    }

    // Getters and Setters
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    public String getActressName() {
        return actressName;
    }

    public void setActressName(String actressName) {
        this.actressName = actressName;
    }

    // Overridden Method
    public void displayEventDetails() {
    	// Assuming this method exists in Event class
        System.out.println("Genre: " + genre);
        System.out.println("Hero: " + actorName);
        System.out.println("Heroine: " + actressName);
    }
}
