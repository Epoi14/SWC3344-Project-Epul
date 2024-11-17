import java.util.LinkedList;

public class Product {
    // Create 2 queues named qAvailable and qNotAvailable
    public static MyQueue qAvailable = new MyQueue();  // Queue for available products
    public static MyQueue qNotAvailable = new MyQueue();  // Queue for not available products

    // Create 100 Product objects with specific availability
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++) {
            // Create a new product object for each iteration
            Product product = new Product(i, (i % 2 == 0)); // Even index -> Available, Odd index -> Not Available
            if (product.isAvailable) {
                qAvailable.enqueue(product);  // Add to qAvailable if available
            } else {
                qNotAvailable.enqueue(product);  // Add to qNotAvailable if not available
            }
        }

        // Display the sizes of qAvailable and qNotAvailable queues
        System.out.println("Size of qAvailable: " + qAvailable.size());
        System.out.println("Size of qNotAvailable: " + qNotAvailable.size());
    }

    private int id;
    private boolean isAvailable;

    // Constructor for Product class
    public Product(int id, boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }

    // Getter for availability status
    public boolean isAvailable() {
        return isAvailable;
    }
}

class MyQueue {
    private LinkedList<Product> queue = new LinkedList<>();

    // Enqueue method to add an element
    public void enqueue(Product product) {
        queue.add(product);
    }

    // Dequeue method to remove and return an element
    public Product dequeue() {
        if (isEmpty()) {
            return null;
        }
        return queue.removeFirst();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    // Get the size of the queue
    public int size() {
        return queue.size();
    }
}
