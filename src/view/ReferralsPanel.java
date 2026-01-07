package view;

import model.Referral;

import javax.swing.*;
import java.awt.*;

public class ReferralsPanel extends JPanel {
    public final JButton btnNew = new JButton("New Referral (Queue)");
    public final JButton btnProcessNext = new JButton("Process Next (Singleton)");
    public final JButton btnPreview = new JButton("Preview Email");
    public final JButton btnRefresh = new JButton("Refresh");

    public final JTable table;
    public final ReferralsTableModel model = new ReferralsTableModel();

    public ReferralsPanel() {
        setLayout(new BorderLayout());
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(btnNew);
        top.add(btnProcessNext);
        top.add(btnPreview);
        top.add(btnRefresh);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public Referral getSelected() {
        int row = table.getSelectedRow();
        if (row < 0) return null;
        return model.getAt(row);
    }
}
