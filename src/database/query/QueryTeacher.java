package database.query;

import database.userInterfaces.QueryModule;
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
public class QueryTeacher {
    private JTextField t_id;
    private JTextField t_name;
    private JTextField t_sex;
    private JTextField t_birth;
    private JTextField t_prov;
    private JTextField t_region;
    private JTextField dept_id;
    private JTextField col_id;
    private JTextField prof;
    private JTextField sal;
    private JButton 查询Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterInsert;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public QueryTeacher() {
        frame = new JFrame("QueryTeacher");
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
                new QueryModule();
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
                    String T_id = t_id.getText();
                    String T_name = t_name.getText();
                    String T_sex = t_sex.getText();
                    String T_birth = t_birth.getText();
                    String T_prov = t_prov.getText();
                    String T_region = t_region.getText();
                    String Dept_id = dept_id.getText();
                    String Col_id = col_id.getText();
                    String Prof = prof.getText();
                    String Sal = sal.getText();

                    String query = "SELECT * FROM teacher where 1 = 1";
                    if (!T_id.isEmpty() && T_id != "")
                        query += "and T_id like '%" + T_id + "%'";
                    if (!T_name.isEmpty() && T_name != "")
                        query += "and T_name like '%" + T_name + "%'";
                    if (!T_sex.isEmpty() && T_sex != "")
                        query += "and T_sex like '%" + T_sex + "%'";
                    if (!T_birth.isEmpty() && T_birth != "")
                        query += "and T_birth like '%" + T_birth + "%'";
                    if (!T_prov.isEmpty() && T_prov != "")
                        query += "and T_prov like '%" + T_prov + "%'";
                    if (!T_region.isEmpty() && T_region != "")
                        query += "and T_region like '%" + T_region + "%'";
                    if (!Dept_id.isEmpty() && Dept_id != "")
                        query += "and Dept_id like '%" + Dept_id + "%'";
                    if (!Col_id.isEmpty() && Col_id != "")
                        query += "and Col_id like '%" + Col_id + "%'";
                    if (!Prof.isEmpty() && Prof != "")
                        query += "and Prof like '%" + Prof + "%'";
                    if (!Sal.isEmpty() && Sal != "")
                        query += "and Sal like '%" + Sal + "%'";

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
                        vcRows.addElement(rs.getInt(10));
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
                tableModel.addColumn("T_id");
                tableModel.addColumn("T_name");
                tableModel.addColumn("T_sex");
                tableModel.addColumn("T_birth");
                tableModel.addColumn("T_prov");
                tableModel.addColumn("T_region");
                tableModel.addColumn("Dept_id");
                tableModel.addColumn("Col_id");
                tableModel.addColumn("Prof");
                tableModel.addColumn("Sal");
            }
        });
    }

    public static void main(String[] args) {
        new QueryTeacher();
    }
}
