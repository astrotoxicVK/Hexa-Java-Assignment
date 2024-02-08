package entity;

public class Customer {
	private int customerID;
    private String customerName;
    private String email;
    private String phoneNumber;

    // Constructors
    public Customer() {
        // default constructor
    }

    public Customer(int customerID,String customerName, String email, String phoneNumber) {
        this.customerName = customerName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.customerID = customerID;
    }

    // Getters and Setters
    // ...

    // Method
    public void displayCustomerDetails() {
        System.out.println("Customer Name: " + customerName);
        System.out.println("Customer Email: " + email);
        System.out.println("Customer Phone: " + phoneNumber);
      
    }
    public int getCustomerID()
    {
    	return customerID;
    }
    
}
