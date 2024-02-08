package controller;
import entity.*;

import java.util.List;

public interface IEventServiceProvider {
	
	    Event createEvent(String eventName, String date, String time, int totalSeats, float ticketPrice, String eventType, Venue venue);
	    List<Event> getEventDetails();
	    int getAvailableNoOfTickets(Event event);
	}



