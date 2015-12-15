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
public class UpdateUsercode {
    private JTextField old_u_id;
    private JTextField u_id;
    private JTextField u_name;
    private JTextField password;
    private JTextField privilege;
    private JButton 更改Button;
    private JButton 退出Button;
    private JTable tableView;
    private JScrollPane afterUpdate;
    private JPanel panel;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public UpdateUsercode() {
        frame = new JFrame("UpdateUsercode");
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
                    String old_U_id = old_u_id.getText();
                    String U_id = u_id.getText();
                    String U_name = u_name.getText();
                    String Password = password.getText();
                    String Privilege = privilege.getText();
                    String query = "UPDATE usercode SET U_id = '" + U_id + "', U_name = '" + U_name + "', Password = '" + Password + "', Privilege = '" + Privilege + "' WHERE U_id = '" + old_U_id + "'";
                    if (!old_U_id.isEmpty() && !U_id.isEmpty() && !U_name.isEmpty() && !Password.isEmpty() && !Privilege.isEmpty())
                        st.executeUpdate(query);

                    ResultSet rs = st.executeQuery("SELECT * FROM usercode");
                    while (rs.next()) {
                        Vector<String> vcRows = new Vector<>();
                        vcRows.addElement(rs.getString(1));
                        vcRows.addElement(rs.getString(2));
                        vcRows.addElement(rs.getString(3));
                        vcRows.addElement(rs.getString(4));
                        tableModel.addRow(vcRows);
                    }
                    rs.close();
                    st.close();
                    con.close();
                    tableView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//adjust the panel according to the table's current height, and set them visible
                    panelHeight = tableView.getRowHeight() * tableView.getRowCount() + 50;
                    afterUpdate.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    afterUpdate.setVisible(true);
                    tableView.setVisible(true);
                    frame.pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void createTableModel(DefaultTableModel tableModel) {
                tableModel.addColumn("U_id");
                tableModel.addColumn("U_name");
                tableModel.addColumn("Password");
                tableModel.addColumn("Privilege");
            }
        });
    }

    public static void main(String[] args) {
        UpdateUsercode updateUsercode = new UpdateUsercode();
    }
}
