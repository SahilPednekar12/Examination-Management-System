package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Teacher_Registration extends JFrame {

    private final JTextField txtID, txtFirstName, txtLastName, txtDepartment, txtSubject;
    private final JPasswordField txtPassword;
    private final JButton btnRegister, btnLogin;

    public Teacher_Registration() {
        setTitle("Teacher Registration");
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
                Color color1 = new Color(70, 130, 180); // Steel Blue
                Color color2 = new Color(255, 255, 255); // White
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel lblTitle = new JLabel("Teacher Registration");
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
        txtID = createStyledTextField();
        txtFirstName = createStyledTextField();
        txtLastName = createStyledTextField();
        txtDepartment = createStyledTextField();
        txtSubject = createStyledTextField();
        txtPassword = createStyledPasswordField();

        // Add components to form
        addFormRow(formPanel, gbc, "ID:", txtID, 0);
        addFormRow(formPanel, gbc, "First Name:", txtFirstName, 1);
        addFormRow(formPanel, gbc, "Last Name:", txtLastName, 2);
        addFormRow(formPanel, gbc, "Department:", txtDepartment, 3);
        addFormRow(formPanel, gbc, "Subject:", txtSubject, 4);
        addFormRow(formPanel, gbc, "Password:", txtPassword, 5);

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
        btnRegister.addActionListener((ActionEvent e) -> registerTeacher());
        btnLogin.addActionListener((ActionEvent e) -> {
            new TeacherLogin().setVisible(true);
            dispose();
        });
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBackground(new Color(240, 248, 255)); // Alice Blue
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
            BorderFactory.createEmptyBorder(5, 7, 5, 7)
        ));
        return textField;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(240, 248, 255)); // Alice Blue
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 1),
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
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new Teacher_Registration().setVisible(true));
    }
}