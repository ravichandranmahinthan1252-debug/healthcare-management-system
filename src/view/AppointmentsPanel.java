package view;

import model.Appointment;

import javax.swing.*;
import java.awt.*;

public class AppointmentsPanel extends JPanel {
    public final JButton btnAdd = new JButton("Book");
    public final JButton btnEdit = new JButton("Edit");
    public final JButton btnCancel = new JButton("Cancel");
    public final JButton btnDelete = new JButton("Delete");
    public final JButton btnRefresh = new JButton("Refresh");

    public final JTable table;
    public final AppointmentsTableModel model = new AppointmentsTableModel();

    public AppointmentsPanel() {
        setLayout(new BorderLayout());
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnAdd);
        top.add(btnEdit);
        top.add(btnCancel);
        top.add(btnDelete);
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public Appointment getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return model.getAt(row);
    }
}
