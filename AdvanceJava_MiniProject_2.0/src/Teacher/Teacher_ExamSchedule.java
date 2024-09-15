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

public class Teacher_ExamSchedule extends JFrame {
    JTable table;
    JButton addButton, deleteButton, homeButton;

    public Teacher_ExamSchedule() {
        setTitle("Exam Schedule");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and background image
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg2.jpg"));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        // Table setup
        String[] columnNames = {"Exam ID", "Exam Name", "Department", "Credits", "Marks", "Date", "Time", "Status"};
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
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
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
                BorderFactory.createLineBorder(new Color(150, 150, 150)), "Exam Schedule",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(50, 50, 50)));
        scrollPane.setPreferredSize(new Dimension(850, 350)); // Adjust size to be smaller

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

        // Load exam schedule
        loadExamSchedule(model);

        // Add action listeners
        addButton.addActionListener((ActionEvent e) -> addExam(model));
        deleteButton.addActionListener((ActionEvent e) -> deleteExam(model));
        homeButton.addActionListener((ActionEvent e) -> {
            new Teacher_Homepage().setVisible(true);
            dispose();
        });
    }

    private void addExam(DefaultTableModel model) {
        try {
            String examIdStr = JOptionPane.showInputDialog(this, "Enter Exam ID:");
            String examName = JOptionPane.showInputDialog(this, "Enter Exam Name:");
            String department = JOptionPane.showInputDialog(this, "Enter Department:");
            String creditsStr = JOptionPane.showInputDialog(this, "Enter Credits:");
            String marksStr = JOptionPane.showInputDialog(this, "Enter Marks:");
            String dateStr = JOptionPane.showInputDialog(this, "Enter Date (YYYY-MM-DD):");
            String timeStr = JOptionPane.showInputDialog(this, "Enter Time (HH:MM:SS):");
            String status = JOptionPane.showInputDialog(this, "Enter Status:");

            if (examIdStr != null && !examIdStr.isEmpty() &&
                    examName != null && !examName.isEmpty() &&
                    department != null && !department.isEmpty() &&
                    creditsStr != null && !creditsStr.isEmpty() &&
                    marksStr != null && !marksStr.isEmpty() &&
                    dateStr != null && !dateStr.isEmpty() &&
                    timeStr != null && !timeStr.isEmpty() &&
                    status != null && !status.isEmpty()) {

                int examId = Integer.parseInt(examIdStr);
                int credits = Integer.parseInt(creditsStr);
                int marks = Integer.parseInt(marksStr);

                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
                String query = "INSERT INTO E_EXAMS (exam_id, Exam_name, Dept, E_credits, E_marks, E_date, time, E_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, examId);
                pst.setString(2, examName);
                pst.setString(3, department);
                pst.setInt(4, credits);
                pst.setInt(5, marks);
                pst.setString(6, dateStr);
                pst.setString(7, timeStr);
                pst.setString(8, status);
                pst.executeUpdate();

                // Refresh table data
                loadExamSchedule(model);

                JOptionPane.showMessageDialog(this, "Exam added successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "All fields are required.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input format: " + ex.getMessage());
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void deleteExam(DefaultTableModel model) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                int examId = (int) model.getValueAt(selectedRow, 0);
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
                String query = "DELETE FROM E_EXAMS WHERE exam_id = ?";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setInt(1, examId);
                pst.executeUpdate();
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Exam deleted successfully.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            }
        }
    }

    private void loadExamSchedule(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/JavaMini", "root", "system");
            PreparedStatement pst = con.prepareStatement("SELECT * FROM E_EXAMS");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("exam_id"),
                        rs.getString("Exam_name"),
                        rs.getString("Dept"),
                        rs.getInt("E_credits"),
                        rs.getInt("E_marks"),
                        rs.getDate("E_date"),
                        rs.getTime("time"),
                        rs.getString("E_status")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Teacher_ExamSchedule().setVisible(true));
    }
}
