package view;

import model.Prescription;

public class PrescriptionsTableModel extends AbstractListTableModel<Prescription> {
    private final String[] cols = {"ID","Patient","Clinician","Appt","Medication","Dosage","Freq","Qty","Pharmacy","Status","Issue"};

    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int column) { return cols[column]; }

    @Override public Object getValueAt(int rowIndex, int columnIndex) {
        Prescription p = items.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> p.getPrescriptionId();
            case 1 -> p.getPatientId();
            case 2 -> p.getClinicianId();
            case 3 -> p.getAppointmentId();
            case 4 -> p.getMedication();
            case 5 -> p.getDosage();
            case 6 -> p.getFrequency();
            case 7 -> p.getQuantity();
            case 8 -> p.getPharmacyName();
            case 9 -> p.getStatus();
            case 10 -> p.getIssueDate();
            default -> "";
        };
    }
}
