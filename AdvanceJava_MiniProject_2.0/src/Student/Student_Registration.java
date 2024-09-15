package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student_Registration extends JFrame {

    private final JTextField txtRollNo;
    private final JTextField txtFirstName;
    private final JTextField txtLastName;
    private final JTextField txtDepartment;
    private final JTextField txtClass;
    private final JTextField txtUserName;
    private final JPasswordField txtPassword;
    private final JButton btnRegister;
    private final JButton btnLogin;

    public Student_Registration() {
        setTitle("Student Registration");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245)); // Light gray background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblTitle = new JLabel("Student Registration");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lblRollNo = new JLabel("Roll No:");
        txtRollNo = new JTextField(20);
        JLabel lblFirstName = new JLabel("First Name:");
        txtFirstName = new JTextField(20);
        JLabel lblLastName = new JLabel("Last Name:");
        txtLastName = new JTextField(20);
        JLabel lblDepartment = new JLabel("Department:");
        txtDepartment = new JTextField(20);
        JLabel lblClass = new JLabel("Class:");
        txtClass = new JTextField(20);
        JLabel lblUserName = new JLabel("Username:");
        txtUserName = new JTextField(20);
        JLabel lblPassword = new JLabel("Password:");
        txtPassword = new JPasswordField(20);

        btnRegister = new JButton("Register");
        btnLogin = new JButton("Login");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(lblTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblRollNo, gbc);
        gbc.gridx = 1;
        panel.add(txtRollNo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblFirstName, gbc);
        gbc.gridx = 1;
        panel.add(txtFirstName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblLastName, gbc);
        gbc.gridx = 1;
        panel.add(txtLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblDepartment, gbc);
        gbc.gridx = 1;
        panel.add(txtDepartment, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(lblClass, gbc);
        gbc.gridx = 1;
        panel.add(txtClass, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(lblUserName, gbc);
        gbc.gridx = 1;
        panel.add(txtUserName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(lblPassword, gbc);
        gbc.gridx = 1;
        panel.add(txtPassword, gbc);

        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(btnRegister, gbc);
        gbc.gridx = 1;
        gbc.gridy = 9;
        panel.add(btnLogin, gbc);

        add(panel, BorderLayout.CENTER);

        btnRegister.addActionListener((ActionEvent e) -> {
            registerStudent();
        });

        btnLogin.addActionListener((ActionEvent e) -> {
            new StudentLogin().setVisible(true);
            dispose();
        });
    }

    private void registerStudent() {
        String rollNo = txtRollNo.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String department = txtDepartment.getText();
        String className = txtClass.getText();
        String userName = txtUserName.getText();
        String password = new String(txtPassword.getPassword());

        // Check for empty fields
        if (rollNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || department.isEmpty() || 
            className.isEmpty() || userName.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "INSERT INTO E_student (roll_no, s_first_name, s_last_name, s_department, class, s_user_name, s_password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(rollNo));
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, department);
            stmt.setString(5, className);
            stmt.setString(6, userName);
            stmt.setString(7, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
                new StudentLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Please try again.");
            }
            conn.close();
        } catch (HeadlessException | NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "An error occurred during registration. Please try again.", "Registration Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Student_Registration().setVisible(true));
    }
}
