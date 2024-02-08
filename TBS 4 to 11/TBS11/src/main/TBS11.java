package main;

import entity.Booking;
import exception.*;
import entity.Event;
import entity.Venue;
import DAO.EventDAOimpl;
import DAO.VenueDAOImpl;
import DAO.BookinDAOImpl;
import java.sql.Date;
import java.sql.Time;
import java.util.Scanner;
import java.sql.SQLException;

public class TBS11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EventDAOimpl eventDAO = new EventDAOimpl();
        VenueDAOImpl venueDAO = new VenueDAOImpl();
        BookinDAOImpl bookingDAO = new BookinDAOImpl();
        int choice;

        do {
            System.out.println("\n--- Event Management System ---");
            System.out.println("1. Create Event");
            System.out.println("2. Get Event Details by ID");
            System.out.println("3. Get Total Available Tickets");
            System.out.println("4. Calculate Booking Cost");
            System.out.println("5. Book Tickets");
            System.out.println("6. Cancel Booking");
            System.out.println("7. Get Booking Details");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            switch (choice) {
                case 1:
                    createEvent(scanner, eventDAO, venueDAO);
                    break;
                case 2:
                    getEventDetailsById(scanner, eventDAO);
                    break;
                case 3:
                    getAvailableNoOfTickets(eventDAO);
                    break;
                case 4:
                    calculateBookingCost(scanner, eventDAO);
                    break;
                case 5:
                    bookTickets(scanner, eventDAO, bookingDAO);
                    break;
                case 6:
                    cancelBooking(scanner, bookingDAO);
                    break;
                case 7:
                    getBookingDetails(scanner, bookingDAO);
                    break;
                case 8:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 8);

        scanner.close();
    }

    private static void createEvent(Scanner scanner, EventDAOimpl eventDAO, VenueDAOImpl venueDAO) {
        try {
            System.out.println("Enter Event Name:");
            String eventName = scanner.nextLine();

            System.out.println("Enter Event Date (YYYY-MM-DD):");
            Date eventDate = Date.valueOf(scanner.nextLine());

            System.out.println("Enter Event Time (HH:MM:SS):");
            Time eventTime = Time.valueOf(scanner.nextLine());

            System.out.println("Enter Total Seats:");
            int totalSeats = scanner.nextInt();

            System.out.println("Enter Ticket Price:");
            double ticketPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline left-over

            System.out.println("Enter Event Type (Movie/Sports/Concert):");
            String eventType = scanner.nextLine();

            System.out.println("Enter Venue ID (Enter 0 if Venue does not exist):");
            int venueId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            Venue venue;
            if (venueId > 0) {
                venue = venueDAO.getVenueById(venueId);
                if (venue == null) {
                    System.out.println("No venue found with ID: " + venueId + ". Please enter venue details.");
                    venue = getVenueDetails(scanner);
                    venueDAO.createVenue(venue); // Assuming createVenue method handles setting the ID after insertion
                }
            } else {
                venue = getVenueDetails(scanner);
                venueDAO.createVenue(venue); // Assuming createVenue method handles setting the ID after insertion
            }

            Event event = new Event();
            event.setEventName(eventName);
            event.setEventDate(eventDate);
            event.setEventTime(eventTime);
            event.setTotalSeats(totalSeats);
            event.setAvailableSeats(totalSeats); // Assuming all seats are initially available
            event.setTicketPrice(ticketPrice);
            event.setEventType(eventType);
            event.setVenue(venue);

            eventDAO.createEvent(event);
            System.out.println("Event created successfully.");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input format: " + e.getMessage());
        }
    }
    private static void getEventDetailsById(Scanner scanner, EventDAOimpl eventDAO) {
        try {
            System.out.println("Enter Event ID:");
            int eventId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            Event event = eventDAO.getEventDetailsById(eventId);
            if (event != null) {
                Venue venue = event.getVenue(); // Assuming getVenue() method exists in Event class
                System.out.println("Event ID: " + event.getEventId());
                System.out.println("Event Name: " + event.getEventName());
                System.out.println("Event Type: " + event.getEventType());
                System.out.println("Venue Name: " + (venue != null ? venue.getVenueName() : "N/A"));
                System.out.println("Venue Address: " + (venue != null ? venue.getAddress() : "N/A"));
            } else {
                System.out.println("No event found with ID: " + eventId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static Venue getVenueDetails(Scanner scanner) {
        System.out.println("Enter Venue Name:");
        String venueName = scanner.nextLine();

        System.out.println("Enter Venue Address:");
        String venueAddress = scanner.nextLine();

        Venue venue = new Venue();
        venue.setVenueName(venueName);
        venue.setAddress(venueAddress);
        return venue;
    }


    private static void getAvailableNoOfTickets(EventDAOimpl eventDAO) {
        try {
            int totalTickets = eventDAO.getAvailableNoOfTickets();
            System.out.println("Total Available Tickets: " + totalTickets);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    private static void calculateBookingCost(Scanner scanner, EventDAOimpl eventDAO) {
        try {
            System.out.println("Enter Event ID:");
            int eventId = scanner.nextInt();
            System.out.println("Enter Number of Tickets:");
            int numTickets = scanner.nextInt();

            Event event = eventDAO.getEventById(eventId);
            if (event != null) {
                double totalCost = event.getTicketPrice() * numTickets;
                System.out.println("Total Booking Cost for " + numTickets + " tickets: " + totalCost);
            } else {
                System.out.println("Event not found with ID: " + eventId);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void cancelBooking(Scanner scanner, BookinDAOImpl bookingDAO) {
        try {
            System.out.println("Enter Booking ID to cancel:");
            int bookingId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            bookingDAO.cancelBooking(bookingId);
            System.out.println("Booking cancelled successfully.");
        } catch (InvalidBookingIDException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }

    private static void getBookingDetails(Scanner scanner, BookinDAOImpl bookingDAO) {
        try {
            System.out.println("Enter Booking ID:");
            int bookingId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            Booking booking = bookingDAO.getBookingDetails(bookingId);
            if (booking != null) {
                // Display booking details
                // Ensure Booking class has a meaningful toString() method
                System.out.println(booking);
            } else {
                System.out.println("No booking found with ID: " + bookingId);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
    private static void bookTickets(Scanner scanner, EventDAOimpl eventDAO, BookinDAOImpl bookingDAO) {
        try {
            System.out.println("Enter Booking ID (Enter 0 to create a new booking):");
            int bookingId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            if (bookingId > 0) {
                Booking existingBooking = bookingDAO.getBookingDetails(bookingId);
                if (existingBooking != null) {
                    System.out.println("Booking already exists for ID: " + bookingId);
                    return; // Exit the method if booking already exists
                }
            }

            // Continue with new booking process
            System.out.println("Enter Event ID:");
            int eventId = scanner.nextInt();

            // Ensure Event Exists
            Event event = eventDAO.getEventById(eventId);
            if (event == null) {
                System.out.println("Event not found with ID: " + eventId);
                return;
            }

            System.out.println("Enter Customer ID:");
            int customerId = scanner.nextInt();

            System.out.println("Enter Number of Tickets:");
            int numTickets = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over

            double ticketPrice = event.getTicketPrice();
            double totalCost = ticketPrice * numTickets;

            // Create new booking
            Booking newBooking = new Booking();
            newBooking.setCustomerId(customerId);
            newBooking.setEventId(eventId);
            newBooking.setNumTickets(numTickets);
            newBooking.setTotalCost(totalCost);
            newBooking.setBookingDate(new java.sql.Date(System.currentTimeMillis())); // Set booking date as current date

            bookingDAO.createBooking(newBooking);
            System.out.println("New booking created successfully. Total cost: " + totalCost);
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Error: Null reference encountered.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
