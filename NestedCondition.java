import java.util.Scanner;

public class NestedCondition {

    public static void main(String[] args) {
        // Creating a Scanner object to take user input
        Scanner scanner = new Scanner(System.in);

        // Displaying ticket options
        System.out.println("Ticket Options:");
        System.out.println("1. Silver");
        System.out.println("2. Gold");
        System.out.println("3. Diamond");

        // Taking input for ticket type
        System.out.print("Enter the number corresponding to the ticket type: ");
        int ticketType = scanner.nextInt();

        // Taking input for the number of tickets to be booked
        System.out.print("Enter the number of tickets you want to book: ");
        int numberOfTickets = scanner.nextInt();

        // Closing the Scanner to avoid resource leak
        scanner.close();

        // Calculating total cost based on ticket type
        double baseTicketPrice = 0.0;

        switch (ticketType) {
            case 1:
                baseTicketPrice = 50.0; // Silver ticket price
                break;
            case 2:
                baseTicketPrice = 100.0; // Gold ticket price
                break;
            case 3:
                baseTicketPrice = 150.0; // Diamond ticket price
                break;
            default:
                System.out.println("Invalid ticket type.");
                return;
        }

        double totalCost = baseTicketPrice * numberOfTickets;

        // Displaying the total cost
        System.out.println("Total Cost of Tickets: Ruppess " + totalCost);
    }
}
