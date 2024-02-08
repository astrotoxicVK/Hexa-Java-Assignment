
import java.util.Scanner;

public class Looping {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
           
            System.out.println("Ticket Options:");
            System.out.println("1. Silver");
            System.out.println("2. Gold");
            System.out.println("3. Diamond");

           
            System.out.print("Enter the ticket type (1 for Silver, 2 for Gold, 3 for Diamond) or type 'Exit' to end: ");
            String userInput = scanner.next();

            if (userInput.equalsIgnoreCase("Exit")) {
                System.out.println("Exiting the ticket booking system. Thank you!");
                break;
            }

            int ticketType;
            try {
                ticketType = Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid ticket type or 'Exit'.");
                continue;
            }

            
            System.out.print("Enter the number of tickets needed: ");
            int noOfTickets = scanner.nextInt();

         
            double ticketPrice = 0.0;
            String ticketCategory = "";

            switch (ticketType) {
                case 1:
                    ticketPrice = 50.0; 
                    ticketCategory = "Silver";
                    break;
                case 2:
                    ticketPrice = 100.0; 
                    ticketCategory = "Gold";
                    break;
                case 3:
                    ticketPrice = 150.0; 
                    ticketCategory = "Diamond";
                    break;
                default:
                    System.out.println("Invalid ticket type. Please enter a valid ticket type or 'Exit'.");
                    continue;
            }

            double totalCost = ticketPrice * noOfTickets;

            System.out.println("Booking Details:");
            System.out.println("Ticket Category: " + ticketCategory);
            System.out.println("Number of Tickets: " + noOfTickets);
            System.out.println("Total Cost: $" + totalCost);
        }

        scanner.close();
    }
}