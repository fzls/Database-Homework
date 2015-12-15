package database.update;

import database.Administrator;

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
 * Created by Silence on 2015/12/13.
 */
public class UpdateTeacher {
    private JTextField old_t_id;
    private JTextField t_id;
    private JTextField t_sex;
    private JTextField t_name;
    private JTextField t_prov;
    private JTextField t_birth;
    private JTextField t_region;
    private JTextField col_id;
    private JTextField sal;
    private JTextField dept_id;
    private JTextField prof;
    private JButton 更改Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterUpdate;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public UpdateTeacher() {
        frame = new JFrame("UpdateTeacher");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        tableView.setVisible(false);//hiding the unnecessary part before it is used, which makes the outlook more pretty
        afterUpdate.setVisible(false);
        panelWidth = afterUpdate.getWidth() + 40;
        frame.pack();
        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });
        更改Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel = new DefaultTableModel();
                createTableModel(tableModel);
                tableView.setModel(tableModel);
                try {
                    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to load the driver function", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                try {
                    Connection con = DriverManager.getConnection(Administrator.URL, Administrator.USER, Administrator.PASSWORD);
                    Statement st = con.createStatement();
                    String old_T_id = old_t_id.getText();
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
                    String query = "UPDATE teacher SET T_id = '" + T_id + "', T_name = '" + T_name + "', T_sex = '" + T_sex + "', T_birth = '" + T_birth + "', T_prov = '" + T_prov + "', T_region = '" + T_region + "', Dept_id = '" + Dept_id + "', Col_id = '" + Col_id + "', Prof = '" + Prof + "', Sal = '" + Sal + "' WHERE T_id = '" + old_T_id + "'";
                    if (!T_id.isEmpty() && !T_name.isEmpty() && !T_sex.isEmpty() && !T_birth.isEmpty() && !T_prov.isEmpty() && !T_region.isEmpty() && !Dept_id.isEmpty() && !Col_id.isEmpty() && !Prof.isEmpty() && !Sal.isEmpty() && !old_T_id.isEmpty())
                        st.executeUpdate(query);

                    ResultSet rs = st.executeQuery("SELECT * FROM teacher");
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
                    afterUpdate.setPreferredSize(new Dimension((int) (panelWidth * 1.3), panelHeight));
                    afterUpdate.setVisible(true);
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
        UpdateTeacher updateTeacher = new UpdateTeacher();
    }
}
