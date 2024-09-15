package Student;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_Results extends JFrame {

    private final JTable tableResults;
    private final JTextField txtRollNo;
    private final JPasswordField txtPassword;
    private final JButton btnFetchResults;
    private final JButton btnHome;

    public Student_Results() {
        setTitle("Student Results");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Background Image
        JLabel background = new JLabel(new ImageIcon("path_to_your_image.jpg"));
        background.setLayout(new BorderLayout());
        setContentPane(background);

        // Layout and components
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false); // Make the panel transparent

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setOpaque(false); // Make the input panel transparent
        JLabel lblRollNo = new JLabel("Roll No:");
        JLabel lblPassword = new JLabel("Password:");
        txtRollNo = new JTextField();
        txtPassword = new JPasswordField();
        btnFetchResults = new JButton("Fetch Results");

        inputPanel.add(lblRollNo);
        inputPanel.add(txtRollNo);
        inputPanel.add(lblPassword);
        inputPanel.add(txtPassword);
        inputPanel.add(new JLabel());
        inputPanel.add(btnFetchResults);

        panel.add(inputPanel, BorderLayout.NORTH);

        // Table model for results
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Subject", "Marks Obtained", "Total Marks"}, 0);
        tableResults = new JTable(model);
        tableResults.setFillsViewportHeight(true);
        tableResults.setFont(new Font("Arial", Font.PLAIN, 14));
        tableResults.setRowHeight(30);

        // Centering text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableResults.getColumnCount(); i++) {
            tableResults.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adding table to scroll pane
        JScrollPane scrollPane = new JScrollPane(tableResults);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), "Exam Results",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(50, 50, 50)));
        scrollPane.setPreferredSize(new Dimension(550, 250));

        // Adding color to the table header
        JTableHeader header = tableResults.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        panel.add(scrollPane, BorderLayout.CENTER);

        // Home button
        btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.BOLD, 14));
        btnHome.setBackground(new Color(70, 130, 180));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Make the button panel transparent
        buttonPanel.add(btnHome);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the transparent panel to the background
        background.add(panel, BorderLayout.CENTER);

        // Action listener for fetching results
        btnFetchResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchResults(model);
            }
        });

        btnHome.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Student_Homepage().setVisible(true);
                dispose();
            }
        });
    }

    private void fetchResults(DefaultTableModel model) {
        String rollNo = txtRollNo.getText();
        String password = new String(txtPassword.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system")) {
            String sql = "SELECT roll_no FROM E_student WHERE roll_no = ? AND s_password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rollNo);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int rollNoDb = rs.getInt("roll_no");

                String resultsQuery = "SELECT subject, Marks_obt, TotalMarks FROM result WHERE roll_no = ?";
                PreparedStatement resultsStmt = conn.prepareStatement(resultsQuery);
                resultsStmt.setInt(1, rollNoDb);
                ResultSet resultsRs = resultsStmt.executeQuery();

                model.setRowCount(0); // Clear existing rows
                int totalMarksObtained = 0;
                int totalMarks = 0;

                while (resultsRs.next()) {
                    String subject = resultsRs.getString("subject");
                    int marksObt = resultsRs.getInt("Marks_obt");
                    int total = resultsRs.getInt("TotalMarks");

                    totalMarksObtained += marksObt;
                    totalMarks += total;

                    model.addRow(new Object[]{subject, marksObt, total});
                }

                if (totalMarks > 0) {
                    double percentage = (double) totalMarksObtained / totalMarks * 100;
                    model.addRow(new Object[]{"Total Percentage", totalMarksObtained, totalMarks});
                    model.addRow(new Object[]{"", "", String.format("%.2f%%", percentage)});
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid roll number or password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Student_Results().setVisible(true);
        });
    }
}
