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
public class TheStudentWhoHasTheLowerstScoreInThisCourse {
    private JTextField c_id;
    private JButton 查询Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterInsert;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public TheStudentWhoHasTheLowerstScoreInThisCourse() {
        frame = new JFrame("TheStudentWhoHasTheLowerstScoreInThisCourse");
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
                    String C_id = c_id.getText();
                    // 查询某门成绩最低的学生情况
                    String query = "select * from student where s_id = (select top 1 S_id from sc where C_id ='" + C_id + "' order by score)";

                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        Vector<java.io.Serializable> vcRows = new Vector<>();
                        vcRows.addElement(rs.getString(1));
                        vcRows.addElement(rs.getString(2));
                        vcRows.addElement(rs.getString(3));
                        vcRows.addElement(rs.getDate(4));
                        vcRows.addElement(rs.getString(5));
                        vcRows.addElement(rs.getString(6));
                        vcRows.addElement(rs.getString(7));
                        vcRows.addElement(rs.getString(8));
                        vcRows.addElement(rs.getString(9));
                        tableModel.addRow(vcRows);
                    }
                    rs.close();
                    st.close();
                    con.close();
                    tableView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//adjust the panel according to the table's current height, and set them visible
                    panelHeight = tableView.getRowHeight() * tableView.getRowCount() + 50;
                    afterInsert.setPreferredSize(new Dimension((int) (panelWidth * 1.4), panelHeight));
                    afterInsert.setVisible(true);
                    tableView.setVisible(true);
                    frame.pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }

            }

            private void createTableModel(DefaultTableModel tableModel) {
                tableModel.addColumn("S_id");
                tableModel.addColumn("S_name");
                tableModel.addColumn("S_sex");
                tableModel.addColumn("S_birth");
                tableModel.addColumn("S_prov");
                tableModel.addColumn("S_region");
                tableModel.addColumn("S_into");
                tableModel.addColumn("Dept_id");
                tableModel.addColumn("Col_id");
            }
        });
    }

    public static void main(String[] args) {
        new TheStudentWhoHasTheLowerstScoreInThisCourse();
    }
}
