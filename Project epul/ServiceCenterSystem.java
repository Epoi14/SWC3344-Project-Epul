import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class ServiceCenterSystem extends JFrame implements ActionListener {
    private JButton loadDataButton, processServiceButton, displayCustomersButton, showReceiptButton;
    private JTextArea displayArea;
    private Queue<CustomerInfo> serviceLane1, serviceLane2, serviceLane3;
    private Stack<CustomerInfo> completeStack;
    private boolean alternateLane = true;
    private JTabbedPane tabbedPane;

    public ServiceCenterSystem() {
    setTitle("Celestial Vehicle Service Center");
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    tabbedPane = new JTabbedPane();

    JPanel homePanel = new JPanel();
    homePanel.setLayout(new BorderLayout());
    JLabel homeImageLabel = new JLabel(new ImageIcon("service_logo.png"));
    homePanel.add(homeImageLabel, BorderLayout.CENTER);
    tabbedPane.addTab("Home", homePanel);

    JPanel servicePanel = new JPanel(new BorderLayout());
    JPanel buttonPanel = new JPanel(new GridLayout(1, 4, 10, 10));

    loadDataButton = new JButton("Load Data");
    processServiceButton = new JButton("Process Service");
    displayCustomersButton = new JButton("Display Customers");
    showReceiptButton = new JButton("Show Receipt");

    loadDataButton.addActionListener(this);
    processServiceButton.addActionListener(this);
    displayCustomersButton.addActionListener(this);
    showReceiptButton.addActionListener(this);

    buttonPanel.add(loadDataButton);
    buttonPanel.add(processServiceButton);
    buttonPanel.add(displayCustomersButton);
    buttonPanel.add(showReceiptButton);

    displayArea = new JTextArea();
    displayArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(displayArea);

    JLabel serviceImageLabel = new JLabel(new ImageIcon("service_management_logo.png"));
    servicePanel.add(serviceImageLabel, BorderLayout.WEST);
    servicePanel.add(buttonPanel, BorderLayout.NORTH);
    servicePanel.add(scrollPane, BorderLayout.CENTER);

    tabbedPane.addTab("Service Management", servicePanel);

    add(tabbedPane);

    serviceLane1 = new LinkedList<>();
    serviceLane2 = new LinkedList<>();
    serviceLane3 = new LinkedList<>();
    completeStack = new Stack<>();
}


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadDataButton) {
            loadData();
        } else if (e.getSource() == processServiceButton) {
            processService();
        } else if (e.getSource() == displayCustomersButton) {
            displayCustomers();
        } else if (e.getSource() == showReceiptButton) {
            showReceipt();
            saveToFile(); // Save receipt to a file
        }
    }

    private void loadData() {
        String filePath = "customerList.txt";
        checkAndCreateFile(filePath);

        displayArea.setText("Loading data from " + filePath + "...\n");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) {
                    displayArea.append("Invalid data format in line: " + line + "\n");
                    continue;
                }

                String customerId = parts[0].trim();
                String customerName = parts[1].trim();
                String vehiclePlateNumber = parts[2].trim();
                int serviceCount = Integer.parseInt(parts[3].trim());

                CustomerInfo customer = new CustomerInfo(customerId, customerName, vehiclePlateNumber);

                for (int i = 0; i < serviceCount; i++) {
                    if (4 + i >= parts.length) {
                        displayArea.append("Incomplete service data for customer: " + customerId + "\n");
                        break;
                    }

                    String[] serviceDetails = parts[4 + i].split(";");
                    if (serviceDetails.length < 5) {
                        displayArea.append("Invalid service data format: " + Arrays.toString(serviceDetails) + "\n");
                        continue;
                    }

                    int serviceId = Integer.parseInt(serviceDetails[0].trim());
                    String serviceType = serviceDetails[1].trim();
                    double serviceCost = Double.parseDouble(serviceDetails[2].trim());
                    String serviceDate = serviceDetails[3].trim();
                    String estimatedCompletionTime = serviceDetails[4].trim();

                    ServiceInfo service = new ServiceInfo(serviceId, serviceType, serviceCost, serviceDate, estimatedCompletionTime);
                    customer.addService(service);
                }

                if (serviceCount <= 3) {
                    if (alternateLane) {
                        serviceLane1.offer(customer);
                    } else {
                        serviceLane2.offer(customer);
                    }
                    alternateLane = !alternateLane;
                } else {
                    serviceLane3.offer(customer);
                }
            }
            displayArea.append("Data loaded and customers distributed into lanes.\n");
        } catch (IOException ex) {
            displayArea.append("Error loading data: " + ex.getMessage() + "\n");
        }
    }

    private void checkAndCreateFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while creating the file.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File already exists: " + file.getAbsolutePath());
        }
    }

    private void processService() {
        displayArea.setText("Processing services...\n");

        processLane(serviceLane1, "Service Lane 1");
        processLane(serviceLane2, "Service Lane 2");
        processLane(serviceLane3, "Service Lane 3");

        displayArea.append("Service processing completed.\n");
    }

    private void processLane(Queue<CustomerInfo> lane, String laneName) {
        int count = 0;
        while (!lane.isEmpty() && count < 5) {
            CustomerInfo customer = lane.poll();
            completeStack.push(customer);
            displayArea.append("Processed " + customer.getCustomerName() + " in " + laneName + "\n");
            count++;
        }
    }

    private void displayCustomers() {
        displayArea.setText("Displaying customers in service lanes...\n");
        displayArea.append("Service Lane 1:\n" + queueToString(serviceLane1) + "\n");
        displayArea.append("Service Lane 2:\n" + queueToString(serviceLane2) + "\n");
        displayArea.append("Service Lane 3:\n" + queueToString(serviceLane3) + "\n");
    }

    private void showReceipt() {
        displayArea.setText("Generating receipt for completed transactions...\n");
        for (CustomerInfo customer : completeStack) {
            displayArea.append("Customer ID: " + customer.getCustomerId() + "\n");
            displayArea.append("Name: " + customer.getCustomerName() + "\n");
            displayArea.append("Vehicle Plate: " + customer.getVehiclePlateNumber() + "\n");
            double totalCost = 0;
            for (ServiceInfo service : customer.getRequestedServices()) {
                displayArea.append("Service: " + service.getServiceType() + ", Cost: $" + service.getServiceCost() + "\n");
                totalCost += service.getServiceCost();
            }
            displayArea.append("Total Cost: $" + totalCost + "\n\n");
        }
    }

    private void saveToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("processedCustomers.txt"))) {
            for (CustomerInfo customer : completeStack) {
                writer.println("Customer ID: " + customer.getCustomerId());
                writer.println("Name: " + customer.getCustomerName());
                writer.println("Vehicle Plate: " + customer.getVehiclePlateNumber());
                double totalCost = 0;
                for (ServiceInfo service : customer.getRequestedServices()) {
                    writer.println("Service: " + service.getServiceType() + ", Cost: $" + service.getServiceCost());
                    totalCost += service.getServiceCost();
                }
                writer.println("Total Cost: $" + totalCost);
                writer.println();
            }
            System.out.println("Receipt saved to processedCustomers.txt.");
        } catch (IOException e) {
            System.out.println("Error saving receipt to file: " + e.getMessage());
        }
    }

    private String queueToString(Queue<CustomerInfo> queue) {
        StringBuilder sb = new StringBuilder();
        for (CustomerInfo customer : queue) {
            sb.append(customer).append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ServiceCenterSystem().setVisible(true));
    }
}