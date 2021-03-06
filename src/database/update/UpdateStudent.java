package database.update;

import database.userInterfaces.Administrator;
import database.userInterfaces.UpdateModules;

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
public class UpdateStudent {
    private JTextField old_s_id;
    private JTextField s_name;
    private JTextField s_id;
    private JTextField s_sex;
    private JTextField s_birth;
    private JTextField s_prov;
    private JTextField s_into;
    private JTextField s_region;
    private JTextField dept_id;
    private JTextField col_id;
    private JButton 更改Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterUpdate;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public UpdateStudent() {
        frame = new JFrame("UpdateStudent");
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
                UpdateModules updateModules = new UpdateModules();
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
                    String old_S_id = old_s_id.getText();
                    String S_id = s_id.getText();
                    String S_name = s_name.getText();
                    String S_sex = s_sex.getText();
                    String S_birth = s_birth.getText();
                    String S_prov = s_prov.getText();
                    String S_region = s_region.getText();
                    String S_into = s_into.getText();
                    String Dept_id = dept_id.getText();
                    String Col_id = col_id.getText();
                    String query = "UPDATE student SET S_id = '" + S_id + "', S_name = '" + S_name + "', S_sex = '" + S_sex + "', S_birth = '" + S_birth + "', S_prov = '" + S_prov + "', S_region = '" + S_region + "', S_into = '" + S_into + "', Dept_id = '" + Dept_id + "', Col_id = '" + Col_id + "' WHERE S_id = '" + old_S_id + "'";
                    if (!old_S_id.isEmpty() && !S_id.isEmpty() && !S_name.isEmpty() && !S_sex.isEmpty() && !S_birth.isEmpty() && !S_prov.isEmpty() && !S_region.isEmpty() && !S_into.isEmpty() && !Dept_id.isEmpty() && !Col_id.isEmpty())
                        st.executeUpdate(query);

                    ResultSet rs = st.executeQuery("SELECT * FROM student");
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
                    afterUpdate.setPreferredSize(new Dimension((int) (panelWidth * 1.2), panelHeight));
                    afterUpdate.setVisible(true);
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
        UpdateStudent updateStudent = new UpdateStudent();
    }
}
