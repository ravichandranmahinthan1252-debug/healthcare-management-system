package view;

import model.Referral;

public class ReferralsTableModel extends AbstractListTableModel<Referral> {
    private final String[] cols = {"ID","Patient","From Clinician","To Clinician","Urgency","Status","Date","Appt"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Referral r = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> r.getReferralId();
            case 1 -> r.getPatientId();
            case 2 -> r.getReferringClinicianId();
            case 3 -> r.getReferredToClinicianId();
            case 4 -> r.getUrgency();
            case 5 -> r.getStatus();
            case 6 -> r.getReferralDate();
            case 7 -> r.getAppointmentId();
            default -> "";
        };
    }
}
