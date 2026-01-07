package view;

import model.StaffMember;

public class StaffTableModel extends AbstractListTableModel<StaffMember> {
    private final String[] cols = {"ID","First Name","Last Name","Role","Department","Facility","Phone","Email","Employment","Access Level"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        StaffMember s = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> s.getStaffId();
            case 1 -> s.getFirstName();
            case 2 -> s.getLastName();
            case 3 -> s.getRole();
            case 4 -> s.getDepartment();
            case 5 -> s.getFacilityId();
            case 6 -> s.getPhone();
            case 7 -> s.getEmail();
            case 8 -> s.getEmploymentStatus();
            case 9 -> s.getAccessLevel();
            default -> "";
        };
    }
}