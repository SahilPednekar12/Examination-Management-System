

import Student.StudentLogin;
import Teacher.TeacherLogin;
import javax.swing.*;
import java.awt.event.ActionEvent;

public class LoginChoice extends JFrame {
    JRadioButton studentRadioButton, teacherRadioButton;
    JButton loginButton;

    public LoginChoice() {
        setTitle("Login Choice");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        studentRadioButton = new JRadioButton("Student");
        teacherRadioButton = new JRadioButton("Teacher");
        ButtonGroup group = new ButtonGroup();
        group.add(studentRadioButton);
        group.add(teacherRadioButton);

        studentRadioButton.setBounds(50, 50, 100, 30);
        teacherRadioButton.setBounds(150, 50, 100, 30);

        loginButton = new JButton("Login");
        loginButton.setBounds(100, 100, 100, 30);

        add(studentRadioButton);
        add(teacherRadioButton);
        add(loginButton);

        loginButton.addActionListener((ActionEvent e) -> {
            if (teacherRadioButton.isSelected()) {
                new TeacherLogin().setVisible(true);
            } else {
                new StudentLogin().setVisible(true);
            }
            dispose();
        });
    }

    public static void main(String[] args) {
        new LoginChoice().setVisible(true);
    }
}
