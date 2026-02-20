package railwayreservation;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class RailwayReservation {
    private static Connection connect() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/TrainReservation", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RailwayReservation::createFrontPage);
    }

    private static void createFrontPage() {
        JFrame frame = new JFrame("Railway Reservation System");
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(100, 149, 237));

        JLabel title = new JLabel("Welcome to Railway Reservation System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        JButton startButton = new JButton("Get Started");
        startButton.addActionListener(e -> {
            frame.dispose();
            createLoginPage();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(100, 149, 237));
        buttonPanel.add(startButton);

        frame.add(title, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private static void createLoginPage() {
        JFrame frame = new JFrame("User Login");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));
        frame.getContentPane().setBackground(new Color(176, 224, 230));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Create Account");

        loginBtn.addActionListener(e -> {
            if (authenticateUser(userField.getText(), new String(passField.getPassword()))) {
                frame.dispose();
                createMainMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials, try again.");
            }
        });

        registerBtn.addActionListener(e -> {
            frame.dispose();
            createRegisterPage();
        });

        frame.add(userLabel); frame.add(userField);
        frame.add(passLabel); frame.add(passField);
        frame.add(loginBtn); frame.add(registerBtn);
        frame.setVisible(true);
    }

    private static boolean authenticateUser(String username, String password) {
        try (Connection con = connect();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void createRegisterPage() {
        JFrame frame = new JFrame("Create Account");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));
        frame.getContentPane().setBackground(new Color(255, 228, 196));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        registerBtn.addActionListener(e -> {
            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
                ps.setString(1, userField.getText());
                ps.setString(2, new String(passField.getPassword()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Account Created Successfully!");
                frame.dispose();
                createLoginPage();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            createLoginPage();
        });

        frame.add(userLabel); frame.add(userField);
        frame.add(passLabel); frame.add(passField);
        frame.add(registerBtn); frame.add(backBtn);
        frame.setVisible(true);
    }

    private static void createMainMenu() {
        JFrame frame = new JFrame("Train Reservation System");
        frame.setSize(600, 400);
        frame.setLayout(new FlowLayout());
        frame.getContentPane().setBackground(new Color(173, 216, 230));

        JButton viewTrainsBtn = new JButton("View Trains");
        JButton bookTicketBtn = new JButton("Book Ticket");
        JButton viewBookingsBtn = new JButton("View Bookings");
        JButton cancelTicketBtn = new JButton("Cancel Ticket");
        JButton exitBtn = new JButton("Exit");

        viewTrainsBtn.addActionListener(e -> viewTrains(frame));
        bookTicketBtn.addActionListener(e -> bookTicket(frame));
        viewBookingsBtn.addActionListener(e -> viewBookings(frame));
        cancelTicketBtn.addActionListener(e -> cancelTicket(frame));
        exitBtn.addActionListener(e -> frame.dispose());

        frame.add(viewTrainsBtn);
        frame.add(bookTicketBtn);
        frame.add(viewBookingsBtn);
        frame.add(cancelTicketBtn);
        frame.add(exitBtn);
        frame.setVisible(true);
    }

    private static void viewTrains(JFrame parentFrame) {
        JFrame frame = new JFrame("Available Trains");
        frame.setSize(400, 300);
        JTextArea textArea = new JTextArea();
        frame.add(new JScrollPane(textArea));

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM trains")) {
            while (rs.next()) {
                textArea.append("Train ID: " + rs.getInt("train_id") + ", Name: " + rs.getString("train_name") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    private static void bookTicket(JFrame parentFrame) {
        JFrame frame = new JFrame("Book Ticket");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));
        frame.getContentPane().setBackground(new Color(240, 248, 255));

        JLabel nameLabel = new JLabel("Passenger Name:");
        JTextField nameField = new JTextField();
        JLabel trainLabel = new JLabel("Train ID:");
        JTextField trainField = new JTextField();
        JButton bookBtn = new JButton("Book");

        bookBtn.addActionListener(e -> {
            try (Connection con = connect();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO bookings (passenger_name, train_id) VALUES (?, ?)")) {
                ps.setString(1, nameField.getText());
                ps.setInt(2, Integer.parseInt(trainField.getText()));
                ps.executeUpdate();
                JOptionPane.showMessageDialog(frame, "Ticket Booked Successfully!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        frame.add(nameLabel); frame.add(nameField);
        frame.add(trainLabel); frame.add(trainField);
        frame.add(bookBtn);
        frame.setVisible(true);
    }

    private static void viewBookings(JFrame parentFrame) {
        JFrame frame = new JFrame("View Bookings");
        frame.setSize(400, 300);
        JTextArea textArea = new JTextArea();
        frame.add(new JScrollPane(textArea));

        try (Connection con = connect();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM bookings")) {
            while (rs.next()) {
                textArea.append("Passenger: " + rs.getString("passenger_name") + " - Train ID: " + rs.getInt("train_id") + "\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        frame.setVisible(true);
    }

    private static void cancelTicket(JFrame parentFrame) {
        JOptionPane.showMessageDialog(null, "Cancel Ticket feature coming soon.");
    }
}
