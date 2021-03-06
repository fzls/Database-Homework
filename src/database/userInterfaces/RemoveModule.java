package database.userInterfaces;

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
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        系别信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveDepartment removeDepartment = new RemoveDepartment();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        课程信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveCourse removeCourse = new RemoveCourse();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        学生信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveStudent removeStudent = new RemoveStudent();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        学生选课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveStudentTimetable removeStudentTimetable = new RemoveStudentTimetable();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        教师信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveTeacher removeTeacher = new RemoveTeacher();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        教师授课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveTeacherTimetable removeTeacherTimetable = new RemoveTeacherTimetable();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        用户信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveUsercode removeUsercode = new RemoveUsercode();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Administrator administrator = new Administrator();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        RemoveModule removeModule = new RemoveModule();

    }
}
