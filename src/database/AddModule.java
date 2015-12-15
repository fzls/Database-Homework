package database;

import database.add.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by 风之凌殇 on 2015/12/13.
 */
public class AddModule {
    private JButton 学院信息Button;
    private JButton 系别信息Button;
    private JButton 课程信息Button;
    private JButton 教师信息Button;
    private JButton 退出Button;
    private JButton 学生信息Button;
    private JButton 教师授课信息Button;
    private JButton 学生选课信息Button;
    private JButton 用户信息Button;
    private JPanel panel;
    private JFrame frame;

    public AddModule() {
        frame = new JFrame("AddModule");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        学院信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCollege addCollege = new AddCollege();
            }
        });
        系别信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddDepartment addDepartment = new AddDepartment();
            }
        });
        课程信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddCourse addCourse = new AddCourse();
            }
        });
        学生信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudent addStudent = new AddStudent();
            }
        });
        学生选课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddStudentTimetable addStudentTimetable = new AddStudentTimetable();
            }
        });
        教师信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTeacher addTeacher = new AddTeacher();
            }
        });
        教师授课信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddTeacherTimetable addTeacherTimetable = new AddTeacherTimetable();
            }
        });

        用户信息Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddUsercode addUsercode = new AddUsercode();
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
        AddModule addModule = new AddModule();
    }
}
