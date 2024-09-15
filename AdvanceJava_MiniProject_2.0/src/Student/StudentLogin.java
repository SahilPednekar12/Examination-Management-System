package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentLogin extends JFrame {
    private final JTextField rollNoField;
    private final JPasswordField passwordField;
    private final JButton submitButton;
    private final JButton registerButton;
    private final JLabel backgroundLabel;

    public StudentLogin() {
        // Set up the frame
        setTitle("Student Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create components
        JLabel rollNoLabel = new JLabel("Roll No:");
        rollNoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rollNoLabel.setForeground(Color.BLACK);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(Color.BLACK);

        rollNoField = new JTextField(15);
        passwordField = new JPasswordField(15);
        submitButton = new JButton("Login");
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
        formPanel.add(rollNoLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(rollNoField, gbc);

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
        backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setLayout(new BorderLayout());
        backgroundLabel.add(formPanel, BorderLayout.CENTER);

        add(backgroundLabel, BorderLayout.CENTER);

        // Add action listeners
        submitButton.addActionListener((ActionEvent e) -> {
            authenticateUser();
        });

        registerButton.addActionListener((ActionEvent e) -> {
            new Student_Registration().setVisible(true);
            this.dispose();
        });
    }

    private void authenticateUser() {
        String rollNo = rollNoField.getText();
        String password = new String(passwordField.getPassword());

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "SELECT * FROM E_student WHERE roll_no = ? AND s_password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(rollNo));
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Successful login
                new Student_Homepage().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid roll number or password", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
        } catch (HeadlessException | NumberFormatException | SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentLogin().setVisible(true));
    }
}
