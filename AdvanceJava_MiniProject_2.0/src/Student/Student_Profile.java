package Student;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.*;

public class Student_Profile extends JFrame {

    private final JTextField rollNoField;
    private final JTextField nameField;
    private final JTextField departmentField;
    private final JTextField classField;
    private final JPasswordField passwordField;
    private final JLabel nameLabel;
    private final JLabel departmentLabel;
    private final JLabel classLabel;
    private final JButton homeButton;
    private final JButton editButton;
    private final JButton saveButton;

    public Student_Profile() {
        setTitle("Student Profile");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg2.jpg").getImage());
        panel.setLayout(new BorderLayout(10, 10));

        // Panel for input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);  // Make the panel transparent
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Login",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel rollNoLabel = new JLabel("Roll No:");
        JLabel passwordLabel = new JLabel("Password:");
        rollNoField = new JTextField(15);
        passwordField = new JPasswordField(15);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(rollNoLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(rollNoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        // Panel for displaying student information
        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false);  // Make the panel transparent
        resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Student Information",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        resultPanel.setLayout(new GridLayout(4, 2, 10, 10));

        nameLabel = new JLabel("Name: ");
        departmentLabel = new JLabel("Department: ");
        classLabel = new JLabel("Class: ");

        nameField = new JTextField();
        departmentField = new JTextField();
        classField = new JTextField();

        nameField.setVisible(false);
        departmentField.setVisible(false);
        classField.setVisible(false);

        resultPanel.add(new JLabel("Name:"));
        resultPanel.add(nameLabel);
        resultPanel.add(nameField);

        resultPanel.add(new JLabel("Department:"));
        resultPanel.add(departmentLabel);
        resultPanel.add(departmentField);

        resultPanel.add(new JLabel("Class:"));
        resultPanel.add(classLabel);
        resultPanel.add(classField);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // Make the panel transparent
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        homeButton = new JButton("Home");
        editButton = new JButton("Edit");
        saveButton = new JButton("Save");

        homeButton.setFont(new Font("Arial", Font.BOLD, 12));
        editButton.setFont(new Font("Arial", Font.BOLD, 12));
        saveButton.setFont(new Font("Arial", Font.BOLD, 12));

        homeButton.setBackground(Color.BLUE);
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        editButton.setBackground(Color.GREEN);
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        saveButton.setBackground(Color.ORANGE);
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        saveButton.setVisible(false);

        buttonPanel.add(homeButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Add components to the main panel
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(resultPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the custom panel as the content pane
        setContentPane(panel);

        // Add action listeners
        passwordField.addActionListener(e -> fetchProfile());

        homeButton.addActionListener(e -> {
            new Student_Homepage().setVisible(true);
            dispose();
        });

        editButton.addActionListener(e -> toggleEditMode(true));

        saveButton.addActionListener(e -> {
            saveProfile();
            toggleEditMode(false);
        });
    }

    // Method to fetch student profile from database
    private void fetchProfile() {
        String rollNo = rollNoField.getText();
        String password = new String(passwordField.getPassword());

        if (rollNo.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both roll number and password.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system")) {
            String sql = "SELECT * FROM E_student WHERE roll_no = ? AND s_password = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, rollNo);
                pst.setString(2, password);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        nameLabel.setText(rs.getString("s_first_name") + " " + rs.getString("s_last_name"));
                        departmentLabel.setText(rs.getString("s_department"));
                        classLabel.setText(rs.getString("class"));

                        nameField.setText(rs.getString("s_first_name") + " " + rs.getString("s_last_name"));
                        departmentField.setText(rs.getString("s_department"));
                        classField.setText(rs.getString("class"));

                        passwordField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid roll number or password.", "Not Found", JOptionPane.ERROR_MESSAGE);
                        nameLabel.setText("");
                        departmentLabel.setText("");
                        classLabel.setText("");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to toggle edit mode for profile information
    private void toggleEditMode(boolean enable) {
        nameLabel.setVisible(!enable);
        departmentLabel.setVisible(!enable);
        classLabel.setVisible(!enable);

        nameField.setVisible(enable);
        departmentField.setVisible(enable);
        classField.setVisible(enable);

        saveButton.setVisible(enable);
        editButton.setVisible(!enable);
    }

    // Method to save edited profile information to database
    private void saveProfile() {
        String rollNo = rollNoField.getText();
        String[] name = nameField.getText().split(" ");
        String firstName = name[0];
        String lastName = name.length > 1 ? name[1] : "";
        String department = departmentField.getText();
        String className = classField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system")) {
            String sql = "UPDATE E_student SET s_first_name = ?, s_last_name = ?, s_department = ?, class = ? WHERE roll_no = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, department);
                pst.setString(4, className);
                pst.setString(5, rollNo);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(this, "Profile updated successfully!");

                fetchProfile();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Custom JPanel class to handle background image
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(Image image) {
            this.backgroundImage = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Student_Profile().setVisible(true));
    }
}
