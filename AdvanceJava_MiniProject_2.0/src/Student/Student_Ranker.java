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

public class Student_Ranker extends JFrame {

    private final JTable tableRankers;
    private final JButton btnHome;

    public Student_Ranker() {
        setTitle("Top 3 Rankers");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Background panel with image
        BackgroundPanel bgPanel = new BackgroundPanel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg2.jpg").getImage());
        bgPanel.setLayout(new BorderLayout());
        setContentPane(bgPanel);

        // Table model for rankers
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Rank", "Roll No", "Name", "Department", "Percentage"}, 0);
        tableRankers = new JTable(model);
        tableRankers.setFillsViewportHeight(true);
        tableRankers.setFont(new Font("Arial", Font.PLAIN, 14));
        tableRankers.setRowHeight(30);

        // Centering text in table cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableRankers.getColumnCount(); i++) {
            tableRankers.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Adding table to scroll pane
        JScrollPane scrollPane = new JScrollPane(tableRankers);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(150, 150, 150)), "Top 3 Performers",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(50, 50, 50)));

        // Adding color to the table header
        JTableHeader header = tableRankers.getTableHeader();
        header.setBackground(new Color(70, 130, 180));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

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

        bgPanel.add(scrollPane, BorderLayout.CENTER);
        bgPanel.add(buttonPanel, BorderLayout.SOUTH);

        btnHome.addActionListener((ActionEvent e) -> {
            new Student_Homepage().setVisible(true);
            dispose();
        });

        fetchRankers(model);
    }

    private void fetchRankers(DefaultTableModel model) {
        try {
            Connection conn;
            conn = DriverManager.getConnection("jdbc:mysql://localhost/JavaMini", "root", "system");
            String sql = "SELECT roll_no, first_name, last_name, Department, " +
                    "(SUM(Marks_obt) / SUM(TotalMarks) * 100) as percentage " +
                    "FROM result " +
                    "GROUP BY roll_no, first_name, last_name, Department " +
                    "ORDER BY percentage DESC " +
                    "LIMIT 3";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            int rank = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                        rank,
                        rs.getInt("roll_no"),
                        rs.getString("first_name") + " " + rs.getString("last_name"),
                        rs.getString("Department"),
                        String.format("%.2f", rs.getDouble("percentage")) + "%"
                });
                rank++;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Student_Ranker().setVisible(true);
        });
    }

    // Inner class for background panel
    class BackgroundPanel extends JPanel {
        private final Image backgroundImage;

        public BackgroundPanel(Image backgroundImage) {
            this.backgroundImage = backgroundImage;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
