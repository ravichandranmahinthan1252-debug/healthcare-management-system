package view;

import model.Facility;

public class FacilityTableModel extends AbstractListTableModel<Facility> {
    private final String[] cols = {"ID","Name","Type","Address","Postcode","Phone","Email","Manager","Capacity","Specialities"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Facility f = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> f.getFacilityId();
            case 1 -> f.getName();
            case 2 -> f.getType();
            case 3 -> f.getAddress();
            case 4 -> f.getPostcode();
            case 5 -> f.getPhone();
            case 6 -> f.getEmail();
            case 7 -> f.getManagerName();
            case 8 -> f.getCapacity();
            case 9 -> f.getSpecialitiesOffered();
            default -> "";
        };
    }
}