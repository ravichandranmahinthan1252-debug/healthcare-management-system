package view;

import model.Appointment;

public class AppointmentsTableModel extends AbstractListTableModel<Appointment> {
    private final String[] cols = {"ID","Patient","Clinician","Facility","Date","Time","Duration","Type","Status","Reason"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Appointment a = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> a.getAppointmentId();
            case 1 -> a.getPatientId();
            case 2 -> a.getClinicianId();
            case 3 -> a.getFacilityId();
            case 4 -> a.getDate();
            case 5 -> a.getTime();
            case 6 -> a.getDurationMinutes();
            case 7 -> a.getAppointmentType();
            case 8 -> a.getStatus();
            case 9 -> a.getReasonForVisit();
            default -> "";
        };
    }
}
