package database.statisticalQuery;

import database.userInterfaces.Administrator;
import database.userInterfaces.StatisticalQueryModule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

/**
 * Created by 风之凌殇 on 2015/12/14.
 */
public class StudentsWhoSelectThisCourse {
    private JTextField ayear;
    private JTextField c_id;
    private JButton 查询Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterInsert;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public StudentsWhoSelectThisCourse() {
        frame = new JFrame("StudentsWhoSelectThisCourse");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        tableView.setVisible(false);
        afterInsert.setVisible(false);
        panelWidth = afterInsert.getWidth();
        frame.pack();


        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new StatisticalQueryModule();
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        查询Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = new DefaultTableModel();
                createTableModel(tableModel);
                tableView.setModel(tableModel);
                try {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to load the driver function/n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    Connection con = DriverManager.getConnection(Administrator.URL, Administrator.USER, Administrator.PASSWORD);
                    Statement st = con.createStatement();
                    String Ayear = ayear.getText();
                    String C_id = c_id.getText();
                    Vector<java.io.Serializable> vcRows = new Vector<>();
                    // 查询各学年，不同课程的学生选修情况，包括选课人数，各分数档人数（如 100~90，89~80，79~70，69~60，不及格等），平均分，最高分，最低分；
                    String[] query = new String[9];
                    query[0] = "SELECT count(distinct S_id) as numOfStudents FROM sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "'";
                    query[1] = "SELECT AVG(score) as average FROM sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "'";
                    query[2] = "SELECT MIN(score) as minimum FROM sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "'";
                    query[3] = "SELECT MAX(score) as maximum FROM sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "'";
                    query[4] = "select count(distinct S_id) as _90_100 from sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "' and Score > 90 and Score <= 100";
                    query[5] = "select count(distinct S_id) as _80_90 from sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "' and Score > 80 and Score <= 90";
                    query[6] = "select count(distinct S_id) as _70_80 from sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "' and Score > 70 and Score <= 80";
                    query[7] = "select count(distinct S_id) as _60_70 from sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "' and Score > 60 and Score <= 70";
                    query[8] = "select count(distinct S_id) as _60 from sc where Ayear = '" + Ayear + "' and C_id = '" + C_id + "' and Score <= 60";
                    for (int i = 0; i < 9; ++i) {
                        ResultSet rs = st.executeQuery(query[i]);
                        while (rs.next()) {
                            vcRows.addElement(rs.getInt(1));
                        }
                        rs.close();
                    }
                    tableModel.addRow(vcRows);
                    st.close();
                    con.close();
                    tableView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//adjust the panel according to the table's current height, and set them visible
                    panelHeight = tableView.getRowHeight() * tableView.getRowCount() + 50;
                    afterInsert.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    afterInsert.setVisible(true);
                    tableView.setVisible(true);
                    frame.pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }

            private void createTableModel(DefaultTableModel tableModel) {
                tableModel.addColumn("Numbers");
                tableModel.addColumn("Average");
                tableModel.addColumn("Minimum");
                tableModel.addColumn("Maximum");
                tableModel.addColumn("90~100");
                tableModel.addColumn("80~90");
                tableModel.addColumn("70~80");
                tableModel.addColumn("60~70");
                tableModel.addColumn("~60");
            }
        });
    }

    public static void main(String[] args) {
        new StudentsWhoSelectThisCourse();
    }

}
