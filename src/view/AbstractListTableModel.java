package view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractListTableModel<T> extends AbstractTableModel {
    protected final List<T> items = new ArrayList<>();

    public void setItems(List<T> newItems) {
        items.clear();
        if (newItems != null) items.addAll(newItems);
        fireTableDataChanged();
    }

    public T getAt(int row) {
        if (row < 0 || row >= items.size()) return null;
        return items.get(row);
    }

    public List<T> getItems() { return items; }

    @Override public int getRowCount() { return items.size(); }
}
