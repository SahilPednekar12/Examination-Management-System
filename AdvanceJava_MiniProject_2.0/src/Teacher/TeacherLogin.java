package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherLogin extends JFrame {
    private final JTextField first_nameField;
    private final JPasswordField passwordField;
    private final JButton submitButton;
    private final JButton registerButton;

    public TeacherLogin() {
        setTitle("Teacher Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create components
        JLabel first_nameLabel = new JLabel("Username:");
        first_nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        first_nameLabel.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.BLACK);

        first_nameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        submitButton = new JButton("Submit");
        submitButton.setBackground(new Color(30, 144, 255));
        submitButton.setForeground(Color.WHITE);

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(30, 144, 255));
        registerButton.setForeground(Color.WHITE);

        // Create panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false); // Make background transparent
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(first_nameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(first_nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(submitButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(registerButton, gbc);

        // Create background image
        ImageIcon backgroundIcon = new ImageIcon("path/to/your/background/image.jpg"); // Update the path
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(formPanel, BorderLayout.CENTER);

        add(backgroundLabel, BorderLayout.CENTER);

        // Add action listeners
        submitButton.addActionListener((ActionEvent e) -> {
            String first_name = first_nameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(first_name, password)) {
                new Teacher_Homepage().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid first name or password");
            }
        });

        registerButton.addActionListener((ActionEvent e) -> {
            new Teacher_Registration().setVisible(true);
            dispose();
        });
    }

    private boolean validateLogin(String first_name, String password) {
        boolean isValid = false;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
            // Prepare the SQL statement
            String sql = "SELECT * FROM E_teacher WHERE first_name = ? AND password = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, first_name);
            pstmt.setString(2, password);
            // Execute the query
            rs = pstmt.executeQuery();
            // Check if a result was returned
            if(rs.next()) {
                isValid = true;
            }
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "JDBC Driver not found.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error.");
        } finally {
            // Close the resources
            try { if(rs != null) rs.close(); } catch (SQLException e) {}
            try { if(pstmt != null) pstmt.close(); } catch (SQLException e) {}
            try { if(conn != null) conn.close(); } catch (SQLException e) {}
        }

        return isValid;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TeacherLogin().setVisible(true));
    }
}
