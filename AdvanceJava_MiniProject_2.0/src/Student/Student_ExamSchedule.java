package Student;

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

public class Student_ExamSchedule extends JFrame {

    private final JTable tableSchedule;
    private final JButton btnHome;

    public Student_ExamSchedule() {
        setTitle("Exam Schedule");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and background image
        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg2.jpg"));
        setContentPane(background);
        background.setLayout(new BorderLayout());

        // Table model for exam schedule
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Exam ID", "Subject", "Date", "Credits", "Time"}, 0);
        tableSchedule = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (c instanceof JComponent) {
                    ((JComponent) c).setOpaque(false); // Make table cells transparent
                }
                return c;
            }
        };
        tableSchedule.setFillsViewportHeight(true);
        tableSchedule.setFont(new Font("Arial", Font.PLAIN, 14));
        tableSchedule.setRowHeight(30);
        tableSchedule.setOpaque(false); // Make table transparent

        // Centering text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableSchedule.getColumnCount(); i++) {
            tableSchedule.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adding table to scroll pane
        JScrollPane scrollPane = new JScrollPane(tableSchedule);
        scrollPane.setOpaque(false); // Make scroll pane transparent
        scrollPane.getViewport().setOpaque(false); // Make viewport transparent
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), "Exam Schedule",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(50, 50, 50)));
        scrollPane.setPreferredSize(new Dimension(550, 250)); // Adjust size to be smaller

        // Adding color to the table header
        JTableHeader header = tableSchedule.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setOpaque(false); // Make header transparent

        // Home button
        btnHome = new JButton("Home");
        btnHome.setFont(new Font("Arial", Font.BOLD, 14));
        btnHome.setBackground(new Color(70, 130, 180));
        btnHome.setForeground(Color.WHITE);
        btnHome.setFocusPainted(false);
        btnHome.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);  // To show the background image
        buttonPanel.add(btnHome);

        background.add(scrollPane, BorderLayout.CENTER);
        background.add(buttonPanel, BorderLayout.SOUTH);

        btnHome.addActionListener((ActionEvent e) -> {
            new Student_Homepage().setVisible(true);
            dispose();
        });

        fetchSchedule(model);
    }

    private void fetchSchedule(DefaultTableModel model) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "SELECT exam_id, Exam_name, E_date, E_credits, time FROM E_EXAMS";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("exam_id"),
                        rs.getString("Exam_name"),
                        rs.getDate("E_date"),
                        rs.getInt("E_credits"),
                        rs.getTime("time")
                });
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace(); // To help with debugging
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Student_ExamSchedule().setVisible(true);
        });
    }
}
