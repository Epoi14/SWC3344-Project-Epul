import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WelcomePage extends JFrame implements ActionListener {
    private JButton startButton;
    private JTabbedPane tabbedPane;

    public WelcomePage() {
        // Set title for the Vehicle Service Center
        setTitle("Welcome to Celestial Vehicle Service Center");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the icon image for the frame
        ImageIcon icon = new ImageIcon("service_icon.jpg");
        setIconImage(icon.getImage());

        // Initialize tabbed pane
        tabbedPane = new JTabbedPane();

        // Add Home Tab with an image
        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        ImageIcon logo = new ImageIcon("service_logo.png");
        JLabel logoLabel = new JLabel(logo, JLabel.CENTER);
        homePanel.add(logoLabel, BorderLayout.CENTER);

        tabbedPane.addTab("Home", homePanel);

        // Add Start Tab with Start Button
        JPanel startPanel = new JPanel();
        startPanel.setLayout(new FlowLayout());
        startButton = new JButton("Start");
        startButton.addActionListener(this);
        startButton.setBackground(new Color(176, 224, 230));
        startPanel.add(startButton);
        tabbedPane.addTab("Start", startPanel);

        // Add tabbed pane to the frame
        add(tabbedPane);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            dispose(); // Close the WelcomePage window
            new ServiceCenterSystem().setVisible(true); // Create and show the ServiceCenterSystem window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WelcomePage());
    }
}
