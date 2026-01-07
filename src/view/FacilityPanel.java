package view;

import model.Facility;

import javax.swing.*;
import java.awt.*;

public class FacilityPanel extends JPanel {
    public final JButton btnAdd = new JButton("Add");
    public final JButton btnEdit = new JButton("Edit");
    public final JButton btnDelete = new JButton("Delete");
    public final JButton btnRefresh = new JButton("Refresh");

    public final JTable table;
    public final FacilityTableModel model = new FacilityTableModel();

    public FacilityPanel() {
        setLayout(new BorderLayout());

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnAdd);
        top.add(btnEdit);
        top.add(btnDelete);
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public Facility getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return model.getAt(row);
    }
}