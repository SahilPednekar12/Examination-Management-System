package Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Student_Registration extends JFrame {

    private final JTextField txtRollNo, txtFirstName, txtLastName, txtDepartment, txtClass, txtUserName;
    private final JPasswordField txtPassword;
    private final JButton btnRegister, btnLogin;

    public Student_Registration() {
        setTitle("Student Registration");
        setSize(650, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int w = getWidth();
                int h = getHeight();
                Color color1 = new Color(100, 149, 237); // Cornflower blue
                Color color2 = new Color(255, 255, 255); // White
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel lblTitle = new JLabel("Student Registration");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 32));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(255, 255, 255, 220)); // Semi-transparent white
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize components
        txtRollNo = createStyledTextField();
        txtFirstName = createStyledTextField();
        txtLastName = createStyledTextField();
        txtDepartment = createStyledTextField();
        txtClass = createStyledTextField();
        txtUserName = createStyledTextField();
        txtPassword = createStyledPasswordField();

        // Add components to form
        addFormRow(formPanel, gbc, "Roll No:", txtRollNo, 0);
        addFormRow(formPanel, gbc, "First Name:", txtFirstName, 1);
        addFormRow(formPanel, gbc, "Last Name:", txtLastName, 2);
        addFormRow(formPanel, gbc, "Department:", txtDepartment, 3);
        addFormRow(formPanel, gbc, "Class:", txtClass, 4);
        addFormRow(formPanel, gbc, "Username:", txtUserName, 5);
        addFormRow(formPanel, gbc, "Password:", txtPassword, 6);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        btnRegister = createStyledButton("Register");
        btnLogin = createStyledButton("Login");

        buttonPanel.add(btnRegister);
        buttonPanel.add(btnLogin);

        // Add components to main panel
        mainPanel.add(lblTitle, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add main panel to frame
        add(mainPanel);

        // Action listeners
        btnRegister.addActionListener((ActionEvent e) -> registerStudent());
        btnLogin.addActionListener((ActionEvent e) -> {
            new StudentLogin().setVisible(true);
            dispose();
        });
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(240, 248, 255)); // Alice Blue
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(240, 248, 255)); // Alice Blue
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 149, 237), 1),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return passwordField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, String labelText, JComponent component, int row) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(new Color(25, 25, 112)); // Midnight Blue

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0.1;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.9;
        panel.add(component, gbc);
    }

    private void registerStudent() {
        String rollNo = txtRollNo.getText();
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        String department = txtDepartment.getText();
        String studentClass = txtClass.getText();
        String userName = txtUserName.getText();
        String password = new String(txtPassword.getPassword());

        // Validation for empty fields
        if (rollNo.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
            department.isEmpty() || studentClass.isEmpty() ||
            userName.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Database connection and registration logic
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "INSERT INTO E_student (roll_no, s_first_name, s_last_name, s_department, class, s_user_name, s_password) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(rollNo));
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, department);
            stmt.setString(5, studentClass);
            stmt.setString(6, userName);
            stmt.setString(7, password);

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Student registered successfully.");
                new StudentLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error registering student. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Student_Registration().setVisible(true));
    }
}
