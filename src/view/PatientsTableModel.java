package view;

import model.Patient;

public class PatientsTableModel extends AbstractListTableModel<Patient> {
    private final String[] cols = {"ID","First Name","Last Name","DOB","NHS","Gender","Phone","Email","GP Surgery"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Patient p = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getPatientId();
            case 1 -> p.getFirstName();
            case 2 -> p.getLastName();
            case 3 -> p.getDateOfBirth();
            case 4 -> p.getNhsNumber();
            case 5 -> p.getGender();
            case 6 -> p.getPhone();
            case 7 -> p.getEmail();
            case 8 -> p.getGpSurgeryId();
            default -> "";
        };
    }
}
