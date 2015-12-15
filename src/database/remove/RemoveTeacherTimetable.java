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
public class RemoveTeacherTimetable {
    private JTextField ayear;
    private JTextField semester;
    private JTextField t_id;
    private JTextField c_id;
    private JButton 删除Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterDeletion;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public RemoveTeacherTimetable() {
        frame = new JFrame("RemoveTeacherTimetable");
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
                    String Ayear = ayear.getText();
                    String Semester = semester.getText();
                    String T_id = t_id.getText();
                    String C_id = c_id.getText();
                    String query = "DELETE FROM tc WHERE Ayear = '" + Ayear + "' AND Semester = '" + Semester + "' AND T_id = '" + T_id + "' AND C_id = '" + C_id + "'";
                    if (!Ayear.isEmpty() && !Semester.isEmpty() && !T_id.isEmpty() && !C_id.isEmpty())
                        st.executeUpdate(query);

                    ResultSet rs = st.executeQuery("SELECT * FROM tc");
                    while (rs.next()) {
                        Vector<java.io.Serializable> vcRows = new Vector<>();
                        vcRows.addElement(rs.getString(1));
                        vcRows.addElement(rs.getInt(2));
                        vcRows.addElement(rs.getString(3));
                        vcRows.addElement(rs.getString(4));
                        vcRows.addElement(rs.getString(5));
                        tableModel.addRow(vcRows);
                    }
                    rs.close();
                    st.close();
                    con.close();
                    tableView.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);//adjust the panel according to the table's current height, and set them visible
                    panelHeight = tableView.getRowHeight() * tableView.getRowCount() + 50;
                    afterDeletion.setPreferredSize(new Dimension(panelWidth, panelHeight));
                    afterDeletion.setVisible(true);
                    tableView.setVisible(true);
                    frame.pack();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "failed to excute the SQL statement\n" + ex.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }

            private void createTableModel(DefaultTableModel tableModel) {
                tableModel.addColumn("Ayear");
                tableModel.addColumn("Semester");
                tableModel.addColumn("T_id");
                tableModel.addColumn("C_id");
                tableModel.addColumn("Rating");
            }
        });
    }

    public static void main(String[] args) {
        RemoveTeacherTimetable removeTeacherTimetable = new RemoveTeacherTimetable();
    }
}
