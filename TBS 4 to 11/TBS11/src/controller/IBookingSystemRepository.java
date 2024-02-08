package controller;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import entity.Booking;
import entity.Customer;
import entity.Event;


public interface IBookingSystemRepository {
    List<Event> getEventDetails();
    int getAvailableNoOfTickets(int eventId);
    double calculateBookingCost(int numTickets, double ticketPrice);
    void bookTickets(String eventName, int numTickets, List<Customer> listOfCustomers);
    void cancelBooking(int bookingId);
    Booking getBookingDetails(int bookingId);
	Event createEvent();
}
