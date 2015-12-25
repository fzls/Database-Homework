package database.userInterfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by 风之凌殇 on 2015/12/26.
 */
public class Login {
    public static final String aAdministrator = "Administrator";
    public static final String aStudent = "Student";
    public static final String aTeacher = "Teacher";
    private JPanel panel;
    private JRadioButton isAdministrator;
    private JRadioButton isStudent;
    private JRadioButton isTeacher;
    private JButton 登陆Button;
    private JButton 退出Button;
    private JPasswordField password;
    private JTextField username;
    private JTextField verificationCode;
    private JFrame frame;

    public Login() {
        frame = new JFrame("登陆教务系统");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        登陆Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (checkCodePassed()) {
                    if (isAdministrator.isSelected()) {
                        if (isValidUser(aAdministrator)) {
                            Administrator administrator = new Administrator();
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        } else
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);

                    } else if (isStudent.isSelected()) {
                        //TODO wait the student module to be added in
                        if (isValidUser(aStudent)) {
//                        Student student = new Student();
//                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null, "登陆学生模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else if (isTeacher.isSelected()) {
                        //TODO wait the teacher module to be added in
                        if (isValidUser(aTeacher)) {
//                        Teacher teacher = new Teacher();
//                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null, "登陆教师模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, "你还没有选择登陆角色，请点击按钮进行选择。", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else
                    JOptionPane.showMessageDialog(null, "未输入验证码或验证码错误", "WARNING", JOptionPane.WARNING_MESSAGE);


            }

            //TODO 验证码模板稍后完成
            private boolean checkCodePassed() {


                return true;
            }

            private boolean isValidUser(String role) {
                boolean isValid = false;
                try {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to load the driver function", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    Connection con = DriverManager.getConnection(Administrator.URL, Administrator.USER, Administrator.PASSWORD);
                    Statement st = con.createStatement();
                    String Username = username.getText();
                    String Password = String.valueOf(password.getPassword());

                    ResultSet rs = st.executeQuery("SELECT * FROM usercode WHERE U_name = '" + Username + "' AND Password = '" + Password + "' AND Privilege = '" + role + "'");
                    if (rs.getFetchSize() == 1)
                        isValid = true;
                    rs.close();
                    st.close();
                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                return isValid;
            }
        });
    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
