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
public class UpdateTeacherTimetable {
    private JTextField old_ayear;
    private JTextField old_semester;
    private JTextField old_t_id;
    private JTextField old_c_id;
    private JTextField semester;
    private JTextField ayear;
    private JTextField t_id;
    private JTextField c_id;
    private JTextField rating;
    private JButton 更改Button;
    private JButton 退出Button;
    private JTable tableView;
    private JPanel panel;
    private JScrollPane afterUpdate;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public UpdateTeacherTimetable() {
        frame = new JFrame("UpdateTeacherTimetable");
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
                    String old_Ayear = old_ayear.getText();
                    String old_Semester = old_semester.getText();
                    String old_T_id = old_t_id.getText();
                    String old_C_id = old_c_id.getText();
                    String Ayear = ayear.getText();
                    String Semester = semester.getText();
                    String T_id = t_id.getText();
                    String C_id = c_id.getText();
                    String Rating = rating.getText();
                    String query = "UPDATE tc SET Ayear = '" + Ayear + "', Semester = '" + Semester + "', T_id = '" + T_id + "', C_id = '" + C_id + "', Rating = '" + Rating + "' WHERE Ayear = '" + old_Ayear + "' AND Semester = '" + old_Semester + "' AND T_id = '" + old_T_id + "' AND C_id = '" + old_C_id + "'";
                    if (!old_Ayear.isEmpty() && !old_Semester.isEmpty() && !old_T_id.isEmpty() && !old_C_id.isEmpty() && !Ayear.isEmpty() && !Semester.isEmpty() && !T_id.isEmpty() && !C_id.isEmpty() && !Rating.isEmpty())
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
                tableModel.addColumn("Ayear");
                tableModel.addColumn("Semester");
                tableModel.addColumn("T_id");
                tableModel.addColumn("C_id");
                tableModel.addColumn("Rating");
            }
        });
    }

    public static void main(String[] args) {
        UpdateTeacherTimetable updateTeacherTimetable = new UpdateTeacherTimetable();
    }
}
