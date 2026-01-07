package view;

import model.Clinician;

public class CliniciansTableModel extends AbstractListTableModel<Clinician> {
    private final String[] cols = {"ID","First Name","Last Name","Title","Speciality","Reg No","Phone","Email","Workplace"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Clinician c = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> c.getStaffId();
            case 1 -> c.getFirstName();
            case 2 -> c.getLastName();
            case 3 -> c.getTitle();
            case 4 -> c.getSpeciality();
            case 5 -> c.getRegistrationNumber();
            case 6 -> c.getPhone();
            case 7 -> c.getEmail();
            case 8 -> c.getWorkplaceId();
            default -> "";
        };
    }
}
