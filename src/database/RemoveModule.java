package database;

import database.remove.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Silence on 2015/12/13.
 */
public class RemoveModule {
    private JButton 学院信息Button;
    private JButton 系别信息Button;
    private JButton 课程信息Button;
    private JButton 学生信息Button;
    private JButton 教师授课信息Button;
    private JButton 学生选课信息Button;
    private JButton 教师信息Button;
    private JButton 用户信息Button;
    private JButton 退出Button;
    private JPanel panel;
    private JFrame frame;

    public RemoveModule() {
        frame = new JFrame("RemoveModule");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        学院信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveCollege removeCollege = new RemoveCollege();
            }
        });
        系别信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveDepartment removeDepartment = new RemoveDepartment();
            }
        });
        课程信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveCourse removeCourse = new RemoveCourse();
            }
        });
        学生信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveStudent removeStudent = new RemoveStudent();
            }
        });
        学生选课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveStudentTimetable removeStudentTimetable = new RemoveStudentTimetable();
            }
        });
        教师信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveTeacher removeTeacher = new RemoveTeacher();
            }
        });
        教师授课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveTeacherTimetable removeTeacherTimetable = new RemoveTeacherTimetable();
            }
        });
        用户信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveUsercode removeUsercode = new RemoveUsercode();
            }
        });
        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        RemoveModule removeModule = new RemoveModule();

    }
}
