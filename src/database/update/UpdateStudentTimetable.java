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
public class UpdateStudentTimetable {
    private JTextField old_semester;
    private JTextField old_ayear;
    private JTextField old_s_id;
    private JTextField old_c_id;
    private JTextField ayear;
    private JTextField semester;
    private JButton 更改Button;
    private JButton 退出Button;
    private JTextField c_id;
    private JTextField s_id;
    private JTextField score;
    private JPanel panel;
    private JTable tableView;
    private JScrollPane afterUpdate;
    private JFrame frame;
    private int panelWidth;
    private int panelHeight;

    public UpdateStudentTimetable() {
        frame = new JFrame("UpdateStudentTimetable");
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

                    String Ayear = ayear.getText();
                    String Semester = semester.getText();
                    String S_id = s_id.getText();
                    String C_id = c_id.getText();
                    String Score = score.getText();
                    String old_Ayear = old_ayear.getText();
                    String old_Semester = old_semester.getText();
                    String old_S_id = old_s_id.getText();
                    String old_C_id = old_c_id.getText();
                    String query = "UPDATE sc SET Ayear = '" + Ayear + "', Semester = '" + Semester + "', S_id = '" + S_id + "', C_id = '" + C_id + "', Score = '" + Score + "' WHERE Ayear = '" + old_Ayear + "' AND Semester = '" + old_Semester + "' AND S_id = '" + old_S_id + "' AND C_id = '" + old_C_id + "'";
                    if (!Ayear.isEmpty() && !Semester.isEmpty() && !S_id.isEmpty() && !C_id.isEmpty() && !Score.isEmpty() && !old_Ayear.isEmpty() && !old_Semester.isEmpty() && !old_S_id.isEmpty() && !old_C_id.isEmpty())
                        st.executeUpdate(query);

                    ResultSet rs = st.executeQuery("SELECT * FROM sc");
                    while (rs.next()) {
                        Vector<java.io.Serializable> vcRows = new Vector<>();
                        vcRows.addElement(rs.getString(1));
                        vcRows.addElement(rs.getInt(2));
                        vcRows.addElement(rs.getString(3));
                        vcRows.addElement(rs.getString(4));
                        vcRows.addElement(rs.getInt(5));
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
                tableModel.addColumn("S_id");
                tableModel.addColumn("C_id");
                tableModel.addColumn("Score");
            }
        });
    }

    public static void main(String[] args) {
        UpdateStudentTimetable updateStudentTimetable = new UpdateStudentTimetable();

    }
}
