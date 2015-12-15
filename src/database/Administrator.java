package database;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


/**
 * Created by 风之凌殇 on 2015/12/13.
 */
public class Administrator {
    public static final String USER = "sa";
    public static final String PASSWORD = "test";
    public static final String URL = "jdbc:odbc:fzls";
    private JButton 更新Button;
    private JButton 添加Button;
    private JButton 删除Button;
    private JButton 退出Button;
    private JPanel panel;
    private JFrame frame;

    public Administrator() {
        frame = new JFrame("Administrator");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        更新Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UpdateModule updateModule = new UpdateModule();
            }
        });

        添加Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddModule addModule = new AddModule();
            }
        });


        删除Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RemoveModule removeModule = new RemoveModule();
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
        Administrator administrator = new Administrator();
    }
}
