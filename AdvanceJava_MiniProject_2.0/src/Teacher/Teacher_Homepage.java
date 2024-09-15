package Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Teacher_Homepage extends JFrame {

    private final JButton viewProfileButton;
    private final JButton examScheduleButton;
    private final JButton examResultButton;

    public Teacher_Homepage() {
        setTitle("Teacher Homepage");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Custom panel with background image
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon("C:\\Users\\hp\\Documents\\NetBeansProjects\\AdvanceJava_MiniProject_2.0\\src\\Student\\bg.jpg").getImage());

        // Set the layout of the panel to GridBagLayout for better layout control
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create buttons
        viewProfileButton = createButton("View Profile");
        examScheduleButton = createButton("Exam Schedule");
        examResultButton = createButton("Exam Result");

        // Set constraints for the Profile button
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(viewProfileButton, gbc);

        // Set constraints for the Exam Schedule button
        gbc.gridy = 1;
        panel.add(examScheduleButton, gbc);

        // Set constraints for the Exam Result button
        gbc.gridy = 2;
        panel.add(examResultButton, gbc);

        // Action listeners
        viewProfileButton.addActionListener((ActionEvent e) -> {
            new Teacher_Profile().setVisible(true);
            dispose();
        });

        examScheduleButton.addActionListener((ActionEvent e) -> {
            new Teacher_ExamSchedule().setVisible(true);
            dispose();
        });

        examResultButton.addActionListener((ActionEvent e) -> {
            new Teacher_Results().setVisible(true);
            dispose();
        });

        // Set the custom panel as the content pane
        setContentPane(panel);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 40));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(0, 123, 255));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
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
        SwingUtilities.invokeLater(() -> {
            new Teacher_Homepage().setVisible(true);
        });
    }
}
