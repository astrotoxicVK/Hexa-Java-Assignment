import java.util.Scanner;

public class CondStatements {
    public static void main(String[] args) {
        // Take input for available tickets and number of tickets to book
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the available tickets: ");
        int availableTickets = scanner.nextInt();

        System.out.print("Enter the number of tickets to book: ");
        int noOfBookingTickets = scanner.nextInt();

        // Check if there are enough available tickets
        if (availableTickets >= noOfBookingTickets && noOfBookingTickets > 0) {
            // Book tickets and display remaining tickets
            int remainingTickets = availableTickets - noOfBookingTickets;
            System.out.println("Tickets booked successfully! Remaining tickets: " + remainingTickets);
        } else {
            // Display message indicating that tickets are unavailable
            System.out.println("Ticket unavailable. Please choose a valid number of tickets.");
        }

        // Close the scanner
        scanner.close();
    }
}
