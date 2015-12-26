package database.userInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

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
    private JLabel verificationCodeGraph;
    private JPanel test;
    private JFrame frame;
    private String verifyCode;
    private int width;
    private int height;
    private int codeCount;
    private int fontHeight;
    private int x = 0;
    private int codeY;
    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    public Login() {
        frame = new JFrame("登陆教务系统");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        initVerifyCode();
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
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }

                    } else if (isStudent.isSelected()) {
                        //TODO wait the student module to be added in
                        if (isValidUser(aStudent)) {
//                        Student student = new Student();
//                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null, "登陆学生模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (isTeacher.isSelected()) {
                        //TODO wait the teacher module to be added in
                        if (isValidUser(aTeacher)) {
//                        Teacher teacher = new Teacher();
//                        frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                            JOptionPane.showMessageDialog(null, "登陆教师模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "你还没有选择登陆角色，请点击按钮进行选择。", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    initVerifyCode();
                    JOptionPane.showMessageDialog(null, "未输入验证码或验证码错误,请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                }


            }

            //TODO 验证码模板稍后完成
            private boolean checkCodePassed() {
                return verifyCode.equals(verificationCode.getText());
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

    private void initVerifyCode() {
        width = verificationCodeGraph.getWidth();
        height = verificationCodeGraph.getHeight();
        fontHeight = height - 2;
        x = width / (codeCount + 1);
        codeY = height - 4;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        Random random = new Random();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

        Font font = new Font("Fixedays", Font.PLAIN, fontHeight);
        graphics2D.setFont(font);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(0, 0, width - 1, height - 1);

        graphics2D.setColor(Color.BLACK);
        for (int i = 0; i < 160; ++i) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics2D.drawLine(x, y, x + xl, y + yl);
        }

        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < codeCount; ++i) {
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);

            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(strRand, (i + 1) * x, codeY);

            randomCode.append(strRand);
        }

        verifyCode = randomCode.toString();
        test.paintComponents(graphics2D);

    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
