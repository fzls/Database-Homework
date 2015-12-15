package database;

import database.update.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by Silence on 2015/12/13.
 */
public class UpdateModule {
    private JButton 学院信息Button;
    private JButton 系别信息Button;
    private JButton 课程信息Button;
    private JButton 学生信息Button;
    private JButton 学生选课信息Button;
    private JButton 教师信息Button;
    private JButton 教师授课信息Button;
    private JButton 用户信息Button;
    private JButton 退出Button;
    private JPanel panel;
    private JFrame frame;

    public UpdateModule() {
        frame = new JFrame("UpdateModule");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        学院信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateCollege updateCollege = new UpdateCollege();
            }
        });
        系别信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateDepartment updateDepartment = new UpdateDepartment();
            }
        });
        课程信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateCourse updateCourse = new UpdateCourse();
            }
        });
        学生信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateStudent updateStudent = new UpdateStudent();
            }
        });
        学生选课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateStudentTimetable updateStudentTimetable = new UpdateStudentTimetable();
            }
        });
        教师信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateTeacher updateTeacher = new UpdateTeacher();
            }
        });
        教师授课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateTeacherTimetable updateTeacherTimetable = new UpdateTeacherTimetable();
            }
        });
        用户信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateUsercode updateUsercode = new UpdateUsercode();
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
        UpdateModule updateModule = new UpdateModule();

    }
}
