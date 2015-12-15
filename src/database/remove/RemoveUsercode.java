package database.remove;

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
 * Created by 风之凌殇 on 2015/12/14.
 */
public class RemoveUsercode {
    private JTextField u_id;
    private JButton 删除Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterDeletion;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public RemoveUsercode() {
        frame = new JFrame("RemoveUsercode");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
        tableView.setVisible(false);//hiding the unnecessary part before it is used, which makes the outlook more pretty
        afterDeletion.setVisible(false);
        panelWidth = afterDeletion.getWidth() + 40;
        frame.pack();

        退出Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        删除Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel;
                tableModel = new DefaultTableModel();
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
                    String U_id = u_id.getText();
                    String query = "DELETE FROM usercode WHERE U_id = '" + U_id + "'";
                    if (!U_id.isEmpty())
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
                    afterDeletion.setPreferredSize(new Dimension((int) (panelWidth * 1.2), panelHeight));
                    afterDeletion.setVisible(true);
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
        RemoveUsercode removeUsercode = new RemoveUsercode();
    }
}
