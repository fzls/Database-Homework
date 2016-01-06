package database.userInterfaces;

import database.statisticalQuery.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

/**
 * Created by 风之凌殇 on 2015/12/13.
 */
public class StatisticalQueryModule {
    private JButton 课程选修学生情况Button;
    private JButton 课程尖子生Button;
    private JButton 学生排名Button;
    private JButton 吊车尾Button;
    private JButton 退出Button;
    private JButton 挂科大王Button;
    private JButton 高考强省何处寻Button;
    private JButton 挂科之源Button;
    private JButton 学霸五人众Button;
    private JPanel panel;
    private JButton 评教Button;
    private JButton _75PlusPlusButton;
    private JButton 寻找大学霸Button;
    private JFrame frame;

    public StatisticalQueryModule() {
        frame = new JFrame("StatisticalQueryModule");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        评教Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TeacherOfCertainRating();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        课程选修学生情况Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsWhoSelectThisCourse();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        课程尖子生Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsWhoRanksTopFiveInThisCourse();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        学生排名Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Ranking();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        _75PlusPlusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsWhoHasNoCourseLowerThan75();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        挂科大王Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentsWhoFailedMoreThanTwoCoursesInTheVeryYear();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        挂科之源Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CoursesWithMoreThanTwoStudentsFailedTheExams();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        吊车尾Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TheStudentWhoHasTheLowerstScoreInThisCourse();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        寻找大学霸Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StudentWhoLearnedAllTheCourseThatThisStudentHasLearnedAndPassedAllOfThem();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));

            }
        });
        高考强省何处寻Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HowManyStudentsCameFromEachProvince();
                new HowManyStudentsCameFromEachProvinceWithinThisDepartment();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        学霸五人众Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TopFiveStudentsWhoHasTheHighestCredits();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public static void main(String[] args) {
        new StatisticalQueryModule();
    }
}
