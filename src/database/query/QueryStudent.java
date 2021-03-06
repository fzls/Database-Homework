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
public class QueryStudent {
    private JTextField s_id;
    private JTextField s_name;
    private JTextField s_sex;
    private JTextField s_birth;
    private JTextField dept_id;
    private JTextField s_region;
    private JTextField s_prov;
    private JTextField s_into;
    private JTextField col_id;
    private JButton 查询Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterInsert;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public QueryStudent() {
        frame = new JFrame("QueryStudent");
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
                    String S_id = s_id.getText();
                    String S_name = s_name.getText();
                    String S_sex = s_sex.getText();
                    String S_birth = s_birth.getText();
                    String S_prov = s_prov.getText();
                    String S_region = s_region.getText();
                    String S_into = s_into.getText();
                    String Dept_id = dept_id.getText();
                    String Col_id = col_id.getText();
                    String query = "SELECT * FROM student where 1 = 1";
                    if (!S_id.isEmpty() && S_id != "")
                        query += "and S_id like '%" + S_id + "%'";
                    if (!S_name.isEmpty() && S_name != "")
                        query += "and S_name like '%" + S_name + "%'";
                    if (!S_sex.isEmpty() && S_sex != "")
                        query += "and S_sex like '%" + S_sex + "%'";
                    if (!S_birth.isEmpty() && S_birth != "")
                        query += "and S_birth like '%" + S_birth + "%'";
                    if (!S_prov.isEmpty() && S_prov != "")
                        query += "and S_prov like '%" + S_prov + "%'";
                    if (!S_region.isEmpty() && S_region != "")
                        query += "and S_region like '%" + S_region + "%'";
                    if (!S_into.isEmpty() && S_into != "")
                        query += "and S_into like '%" + S_into + "%'";
                    if (!Dept_id.isEmpty() && Dept_id != "")
                        query += "and Dept_id like '%" + Dept_id + "%'";
                    if (!Col_id.isEmpty() && Col_id != "")
                        query += "and Col_id like '%" + Col_id + "%'";
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
                    afterInsert.setPreferredSize(new Dimension((int) (panelWidth * 1.2), panelHeight));
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
        new QueryStudent();
    }
}
