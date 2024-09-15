package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teacher_Registration extends JFrame {

    private final JTextField txtID;
    private final JTextField txtFirstName;
    private final JTextField txtLastName;
    private final JTextField txtDepartment;
    private final JTextField txtSubject;
    private final JPasswordField txtPassword;
    private final JButton btnRegister;
    private final JButton btnLogin;

    public Teacher_Registration() {
        setTitle("Teacher Registration");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblID = new JLabel("ID:");
        txtID = new JTextField(20);
        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField(20);
        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField(20);
        JLabel lblDepartment = new JLabel("Department:");
        txtDepartment = new JTextField(20);
        JLabel lblSubject = new JLabel("Subject:");
        txtSubject = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        btnRegister = new JButton("Register");
        btnLogin = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(lblID, gbc);
        gbc.gridx = 1;
        add(txtID, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblFirstName, gbc);
        gbc.gridx = 1;
        add(txtFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(lblLastName, gbc);
        gbc.gridx = 1;
        add(txtLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(lblDepartment, gbc);
        gbc.gridx = 1;
        add(txtDepartment, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(lblSubject, gbc);
        gbc.gridx = 1;
        add(txtSubject, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(lblPassword, gbc);
        gbc.gridx = 1;
        add(txtPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        add(btnRegister, gbc);
        gbc.gridx = 1;
        gbc.gridy = 7;
        add(btnLogin, gbc);

        btnRegister.addActionListener((ActionEvent e) -> {
            registerTeacher();
        });

        btnLogin.addActionListener((ActionEvent e) -> {
            new TeacherLogin().setVisible(true);
            dispose();
        });
    }

    private void registerTeacher() {
        String id = txtID.getText().trim();
        String firstName = txtFirstName.getText().trim();
        String lastName = txtLastName.getText().trim();
        String department = txtDepartment.getText().trim();
        String subject = txtSubject.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Check if any field is empty
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || department.isEmpty() || subject.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "INSERT INTO E_teacher (id, first_name, last_name, department, subject, password) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, department);
            stmt.setString(5, subject);
            stmt.setString(6, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                new TeacherLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
            conn.close();
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Teacher_Registration().setVisible(true));
    }
}
