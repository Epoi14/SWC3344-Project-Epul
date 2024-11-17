// This class represents information about a service requested in the vehicle service center
public class ServiceInfo {
    // Unique identifier for the service
    private int serviceId;
    // Type of service (e.g., "Oil Change")
    private String serviceType;
    // Cost of the individual service
    private double serviceCost;
    // Date when the service is scheduled
    private String serviceDate;
    // Estimated time for service completion
    private String estimatedCompletionTime;

    // Constructor to initialize all fields of the service
    public ServiceInfo(int serviceId, String serviceType, double serviceCost, String serviceDate, String estimatedCompletionTime) {
        this.serviceId = serviceId;
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
        this.serviceDate = serviceDate;
        this.estimatedCompletionTime = estimatedCompletionTime;
    }

    // Returns the unique ID of the service
    public int getServiceId() {
        return serviceId;
    }

    // Returns the type of service
    public String getServiceType() {
        return serviceType;
    }

    // Returns the cost of the service
    public double getServiceCost() {
        return serviceCost;
    }

    // Returns the date when the service is scheduled
    public String getServiceDate() {
        return serviceDate;
    }

    // Returns the estimated completion time for the service
    public String getEstimatedCompletionTime() {
        return estimatedCompletionTime;
    }

    // Returns a string representation of the service
    @Override
    public String toString() {
        return "Service ID: " + serviceId + ", Type: " + serviceType + ", Cost: " + serviceCost +
               ", Date: " + serviceDate + ", Estimated Completion: " + estimatedCompletionTime;
    }
}
