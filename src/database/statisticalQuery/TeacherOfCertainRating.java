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
 * Created by 风之凌殇 on 2015/12/13.
 */
public class TeacherOfCertainRating {
    private JTextField ayear;
    private JTextField rating;
    private JButton 查询Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterInsert;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public TeacherOfCertainRating() {
        frame = new JFrame("TeacherOfCertainRating");
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
                    String Rating = rating.getText();
                    //TODO SQL need to be test
                    String query = "select t.T_id, t.T_name, t.T_sex, t.T_birth,t.T_prov, t.T_region,t.Dept_id,t.Col_id,t.Prof,t.Sal,tc.Ayear, tc.Semester,tc.C_id " +
                            "from teacher as t,tc where  t.T_id = tc.T_id and tc.T_id = (SELECT T_id FROM tc WHERE Ayear = '" + Ayear + "' and Rating = '" + Rating + "' )";

                    ResultSet rs = st.executeQuery(query);
                    while (rs.next()) {
                        Vector<Object> vcRows = new Vector<>();
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
                        vcRows.addElement(rs.getString(11));
                        vcRows.addElement(rs.getInt(12));
                        vcRows.addElement(rs.getString(13));
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
                tableModel.addColumn("Ayear");
                tableModel.addColumn("Semester");
                tableModel.addColumn("C_id");
            }
        });
    }

    public static void main(String[] args) {
        new TeacherOfCertainRating();
    }
}
