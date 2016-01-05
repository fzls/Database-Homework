package database.statisticalQuery;

import database.userInterfaces.StatisticalQueryModule;
import database.userInterfaces.Administrator;

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
                    String query = "SELECT * FROM course where 1 = 1";
                    query="SELECT count(distinct T_id) as numOfStudents,  FROM tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"'";
                    query="select count(distinct T_id) as _90_100 from tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"' and Score > 90 and Score <= 100";
                    query="select count(distinct T_id) as _80_90 from tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"' and Score > 80 and Score <= 90";
                    query="select count(distinct T_id) as _70_80 from tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"' and Score > 70 and Score <= 80";
                    query="select count(distinct T_id) as _60_70 from tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"' and Score > 60 and Score <= 100";
                    query="select count(distinct T_id) as _60 from tc where Ayear = '"+Ayear+"' and C_id = '"+C_id+"' and Score <= 60";
                    //TODO need fix up
                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        Vector<java.io.Serializable> vcRows = new Vector<>();
                        vcRows.addElement(rs.getString(1));
                        vcRows.addElement(rs.getString(2));
                        vcRows.addElement(rs.getInt(3));
                        tableModel.addRow(vcRows);
                    }
                    rs.close();
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
