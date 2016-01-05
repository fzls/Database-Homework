package database.userInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
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
    public static final String aQuery = "Query";
    public static final String aStatisticalQuery = "StatisticalQuery";
    public static final boolean disableRoles = true;
    public static final boolean disableVC = true;
    private JPanel panel;
    private JRadioButton isAdministrator;
    private JRadioButton isQuery;
    private JRadioButton isStatisticalQuery;
    private JButton 登陆Button;
    private JButton 退出Button;
    private JPasswordField password;
    private JTextField username;
    private JTextField verificationCode;
    private JLabel verificationCodeGraph;
    private JPanel test;
    private JFrame frame;
    private String verifyCode;
    char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
            'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    public Login() {
        frame = new JFrame("登陆教务系统");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        initVerifyCode();
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
                            new Administrator();
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在或权限不正确，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }

                    } else if (isQuery.isSelected()) {
                        if (isValidUser(aQuery)) {
                            new QueryModule();
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                            JOptionPane.showMessageDialog(null, "登陆学生模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在或权限不正确，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    } else if (isStatisticalQuery.isSelected()) {
                        if (isValidUser(aStatisticalQuery)) {
                            new StatisticalQueryModule();
                            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
//                            JOptionPane.showMessageDialog(null, "登陆教师模块成功，这是debug信息，请注意注释掉", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            initVerifyCode();
                            JOptionPane.showMessageDialog(null, "密码错误或用户名不存在或权限不正确，请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "你还没有选择登陆角色，请点击按钮进行选择。", "WARNING", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    initVerifyCode();
                    JOptionPane.showMessageDialog(null, "未输入验证码或验证码错误,请重新输入", "WARNING", JOptionPane.WARNING_MESSAGE);
                }


            }

            private boolean checkCodePassed() {
                if (disableVC)
                    return true;
                return verificationCode.getText() != null && !verificationCode.getText().equals("") && verifyCode.equals(verificationCode.getText());
            }

            private boolean isValidUser(String role) {
                if (disableRoles)
                    return true;
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

                    if (!Username.isEmpty() && !Password.isEmpty()) {
                        ResultSet rs = st.executeQuery("SELECT * FROM usercode WHERE U_name = '" + Username + "' AND Password = '" + Password + "' collate Chinese_PRC_CS_AI");
                        String actualRole = new String();
                        while (rs.next()) {
                            actualRole = rs.getString(4);
                        }
                        System.out.println(actualRole);
                        if (actualRole.equals(role))
                            isValid = true;
                        rs.close();
                    }
                    st.close();
                    con.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                return isValid;
            }
        });
        verificationCodeGraph.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                initVerifyCode();
            }
        });
        isAdministrator.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                initVerifyCode();
            }
        });
    }

    private void initVerifyCode() {
        Random random = new Random();

        int codeCount = 4 + random.nextInt(3);
        int width = 200;
        int height = 40;
        int fontHeight = height - 2;
        int wordWidth = width / (codeCount + 1);
        int codeY = height - 4;

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);

        Font font = new Font("Fixedays", Font.PLAIN, fontHeight);
        graphics2D.setFont(font);

        graphics2D.setColor(Color.BLACK);
        graphics2D.drawRect(0, 0, width - 1, height - 1);

        graphics2D.setColor(Color.BLACK);
        for (int i = 0; i < 30; ++i) {
            int X = random.nextInt(width);
            int Y = random.nextInt(height);
            int deltaX = random.nextInt(20);
            int deltaY = random.nextInt(20);
            graphics2D.drawLine(X, Y, X + deltaX, Y + deltaY);
        }

        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        for (int i = 0; i < codeCount; ++i) {
            String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);

            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            AffineTransform trans = new AffineTransform();
            trans.rotate(1.0 * StrictMath.PI * (-30 + random.nextInt(60)) / 180, (i + 1) * wordWidth, codeY);

            float scaleSize = random.nextFloat() + 0.8f;
            if (scaleSize > 1f) scaleSize = 1f;
            trans.scale(scaleSize, scaleSize);
            graphics2D.setTransform(trans);

            graphics2D.setColor(new Color(red, green, blue));
            graphics2D.drawString(strRand, (i + 1) * wordWidth, codeY);

            randomCode.append(strRand);
        }

        verifyCode = randomCode.toString();
        ImageIcon icon = new ImageIcon(bufferedImage);
        verificationCodeGraph.setIcon(icon);

    }

    public static void main(String[] args) {
        Login login = new Login();
    }
}
