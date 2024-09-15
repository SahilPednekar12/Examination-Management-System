package Teacher;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class Teacher_Profile extends JFrame {

    private final JTextField idField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField departmentField;
    private final JTextField subjectField;
    private final JLabel firstNameLabel;
    private final JLabel lastNameLabel;
    private final JLabel departmentLabel;
    private final JLabel subjectLabel;
    private final JButton fetchButton;
    private final JButton homeButton;
    private final JButton editButton;
    private final JButton saveButton;

    public Teacher_Profile() {
        setTitle("Teacher Profile");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Teacher\\bg2.jpg").getImage());
        panel.setLayout(new BorderLayout(10, 10));

        // Panel for input and fetch button
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false); // Make the panel transparent
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fetch Teacher Info",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel idLabel = new JLabel("Enter Teacher ID:");
        idField = new JTextField(15);
        fetchButton = new JButton("Fetch Info");

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        inputPanel.add(idField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(fetchButton, gbc);

        // Panel for displaying teacher information
        JPanel resultPanel = new JPanel();
        resultPanel.setOpaque(false); // Make the panel transparent
        resultPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Teacher Information",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14), Color.BLUE));
        resultPanel.setLayout(new GridLayout(5, 2, 10, 10));

        firstNameLabel = new JLabel("First Name: ");
        lastNameLabel = new JLabel("Last Name: ");
        departmentLabel = new JLabel("Department: ");
        subjectLabel = new JLabel("Subject: ");

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        departmentField = new JTextField();
        subjectField = new JTextField();

        firstNameField.setVisible(false);
        lastNameField.setVisible(false);
        departmentField.setVisible(false);
        subjectField.setVisible(false);

        resultPanel.add(new JLabel("First Name:"));
        resultPanel.add(firstNameLabel);
        resultPanel.add(firstNameField);

        resultPanel.add(new JLabel("Last Name:"));
        resultPanel.add(lastNameLabel);
        resultPanel.add(lastNameField);

        resultPanel.add(new JLabel("Department:"));
        resultPanel.add(departmentLabel);
        resultPanel.add(departmentField);

        resultPanel.add(new JLabel("Subject:"));
        resultPanel.add(subjectLabel);
        resultPanel.add(subjectField);

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
        fetchButton.addActionListener(e -> fetchProfile());

        homeButton.addActionListener(e -> {
            new Teacher_Homepage().setVisible(true);
            dispose();
        });

        editButton.addActionListener(e -> toggleEditMode(true));

        saveButton.addActionListener(e -> {
            saveProfile();
            toggleEditMode(false);
        });
    }

    // Method to fetch teacher profile from the database
    private void fetchProfile() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter the teacher ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system")) {
            String sql = "SELECT * FROM E_teacher WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        firstNameLabel.setText(rs.getString("first_name"));
                        lastNameLabel.setText(rs.getString("last_name"));
                        departmentLabel.setText(rs.getString("department"));
                        subjectLabel.setText(rs.getString("subject"));

                        firstNameField.setText(rs.getString("first_name"));
                        lastNameField.setText(rs.getString("last_name"));
                        departmentField.setText(rs.getString("department"));
                        subjectField.setText(rs.getString("subject"));
                    } else {
                        JOptionPane.showMessageDialog(this, "Teacher not found.", "Not Found", JOptionPane.ERROR_MESSAGE);
                        firstNameLabel.setText("");
                        lastNameLabel.setText("");
                        departmentLabel.setText("");
                        subjectLabel.setText("");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to toggle edit mode for profile information
    private void toggleEditMode(boolean enable) {
        firstNameLabel.setVisible(!enable);
        lastNameLabel.setVisible(!enable);
        departmentLabel.setVisible(!enable);
        subjectLabel.setVisible(!enable);

        firstNameField.setVisible(enable);
        lastNameField.setVisible(enable);
        departmentField.setVisible(enable);
        subjectField.setVisible(enable);

        saveButton.setVisible(enable);
        editButton.setVisible(!enable);
    }

    // Method to save edited profile information to the database
    private void saveProfile() {
        String id = idField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String department = departmentField.getText();
        String subject = subjectField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system")) {
            String sql = "UPDATE E_teacher SET first_name = ?, last_name = ?, department = ?, subject = ? WHERE id = ?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, firstName);
                pst.setString(2, lastName);
                pst.setString(3, department);
                pst.setString(4, subject);
                pst.setString(5, id);
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
        SwingUtilities.invokeLater(() -> new Teacher_Profile().setVisible(true));
    }
}
