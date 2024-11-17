import java.util.*;

// This class represents customer information and the services they requested
public class CustomerInfo {
    // Unique customer identifier
    private String customerId;
    // Name of the customer
    private String customerName;
    // Customer's vehicle plate number
    private String vehiclePlateNumber;
    // List of services requested by this customer
    private List<ServiceInfo> requestedServices;

    // Constructor to initialize customer information with ID, name, and vehicle plate number
    public CustomerInfo(String customerId, String customerName, String vehiclePlateNumber) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.vehiclePlateNumber = vehiclePlateNumber;
        this.requestedServices = new LinkedList<>(); // Initialize with LinkedList for fast insertions
    }

    // Adds a service to the list of services requested by this customer
    public void addService(ServiceInfo service) {
        requestedServices.add(service);
    }

    // Returns the customer ID
    public String getCustomerId() {
        return customerId;
    }

    // Returns the customer name
    public String getCustomerName() {
        return customerName;
    }

    // Returns the vehicle plate number
    public String getVehiclePlateNumber() {
        return vehiclePlateNumber;
    }

    // Returns the list of services requested by the customer
    public List<ServiceInfo> getRequestedServices() {
        return requestedServices;
    }

    // Calculates the total cost of all services for this customer
    public double getTotalServiceCost() {
        return requestedServices.stream().mapToDouble(ServiceInfo::getServiceCost).sum();
    }

    // Returns a string representation of the customer, displaying their ID, name, and vehicle plate number
    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + customerName + ", Vehicle Plate: " + vehiclePlateNumber;
    }
}
