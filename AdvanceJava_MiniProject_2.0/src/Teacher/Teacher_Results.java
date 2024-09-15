package Teacher;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher_Results extends JFrame {
    private final JTable table;
    private final JButton addButton, deleteButton, homeButton;

    public Teacher_Results() {
        setTitle("Teacher Results");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and background image
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg2.jpg"));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        // Table model for results
        String[] columnNames = {"Roll No", "First Name", "Last Name", "Department", "Subject", "Credits", "Total Marks", "Marks Obtained"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // Make table cells transparent
                }
                return c;
            }
        };
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setOpaque(false); // Make table transparent

        // Centering text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adding table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false); // Make scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make viewport transparent
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), "Exam Results",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(50, 50, 50)));
        scrollPane.setPreferredSize(new Dimension(750, 350)); // Adjust size

        // Adding color to the table header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setOpaque(false); // Make header transparent

        // Button setup
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        homeButton = new JButton("Home");

        // Button styling
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(70, 130, 180));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        homeButton.setFont(new Font("Arial", Font.BOLD, 14));
        homeButton.setBackground(new Color(70, 130, 180));
        homeButton.setForeground(Color.WHITE);
        homeButton.setFocusPainted(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // To show the background image
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(homeButton);

        background.add(scrollPane, BorderLayout.CENTER);
        background.add(buttonPanel, BorderLayout.SOUTH);

        // Load results
        loadResults(model);

        // Add action listeners
        addButton.addActionListener((ActionEvent e) -> addResult(model));
        deleteButton.addActionListener((ActionEvent e) -> deleteResult(model));
        homeButton.addActionListener((ActionEvent e) -> {
            new Teacher_Homepage().setVisible(true);
            dispose();
        });
    }

    private void loadResults(DefaultTableModel model) {
        model.setRowCount(0); // Clear the existing rows
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM result");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("roll_no"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("Department"),
                        rs.getString("subject"),
                        rs.getInt("credits"),
                        rs.getInt("TotalMarks"),
                        rs.getInt("Marks_obt")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addResult(DefaultTableModel model) {
        try {
            String rollNoStr = JOptionPane.showInputDialog(this, "Enter Roll No:");
            String firstName = JOptionPane.showInputDialog(this, "Enter First Name:");
            String lastName = JOptionPane.showInputDialog(this, "Enter Last Name:");
            String department = JOptionPane.showInputDialog(this, "Enter Department:");
            String subject = JOptionPane.showInputDialog(this, "Enter Subject:");
            String creditsStr = JOptionPane.showInputDialog(this, "Enter Credits:");
            String totalMarksStr = JOptionPane.showInputDialog(this, "Enter Total Marks:");
            String marksObtainedStr = JOptionPane.showInputDialog(this, "Enter Marks Obtained:");

            if (rollNoStr != null && !rollNoStr.isEmpty() &&
                firstName != null && !firstName.isEmpty() &&
                lastName != null && !lastName.isEmpty() &&
                department != null && !department.isEmpty() &&
                subject != null && !subject.isEmpty() &&
                creditsStr != null && !creditsStr.isEmpty() &&
                totalMarksStr != null && !totalMarksStr.isEmpty() &&
                marksObtainedStr != null && !marksObtainedStr.isEmpty()) {

                int rollNo = Integer.parseInt(rollNoStr);
                int credits = Integer.parseInt(creditsStr);
                int totalMarks = Integer.parseInt(totalMarksStr);
                int marksObtained = Integer.parseInt(marksObtainedStr);

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
                String query = "INSERT INTO result (roll_no, first_name, last_name, Department, subject, credits, TotalMarks, Marks_obt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, rollNo);
                pst.setString(2, firstName);
                pst.setString(3, lastName);
                pst.setString(4, department);
                pst.setString(5, subject);
                pst.setInt(6, credits);
                pst.setInt(7, totalMarks);
                pst.setInt(8, marksObtained);
                pst.executeUpdate();
                model.addRow(new Object[]{rollNo, firstName, lastName, department, subject, credits, totalMarks, marksObtained});
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error.");
        }
    }

    private void deleteResult(DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
                String query = "DELETE FROM result WHERE roll_no = ? AND subject = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, (int) model.getValueAt(selectedRow, 0));
                pst.setString(2, (String) model.getValueAt(selectedRow, 4));
                pst.executeUpdate();
                model.removeRow(selectedRow);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Database error.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Teacher_Results().setVisible(true));
    }
}
