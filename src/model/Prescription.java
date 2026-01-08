package model;

public class Prescription {
    private String prescriptionId;
    private String patientId;
    private String clinicianId;
    private String appointmentId;
    private String prescribedDate;
    private String medication;
    private String dosage;
    private String frequency;
    private String quantity;
    private String refills;
    private String instructions;
    private String pharmacyName;
    private String status;
    private String issueDate;
    private String collectionDate;

    public Prescription(String prescriptionId, String patientId, String clinicianId) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
    }

    public static Prescription fromCsv(String[] r) {
        Prescription p = new Prescription(val(r,0), val(r,1), val(r,2));
        p.appointmentId = val(r,3);
        p.prescribedDate = val(r,4);
        p.medication = val(r,5);
        p.dosage = val(r,6);
        p.frequency = val(r,7);
        p.quantity = val(r,8);
        p.refills = val(r,9);
        p.instructions = val(r,10);
        p.pharmacyName = val(r,11);
        p.status = val(r,12);
        p.issueDate = val(r,13);
        p.collectionDate = val(r,14);
        return p;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,prescriptionId);
        set(r,1,patientId);
        set(r,2,clinicianId);
        set(r,3,appointmentId);
        set(r,4,prescribedDate);
        set(r,5,medication);
        set(r,6,dosage);
        set(r,7,frequency);
        set(r,8,quantity);
        set(r,9,refills);
        set(r,10,instructions);
        set(r,11,pharmacyName);
        set(r,12,status);
        set(r,13,issueDate);
        set(r,14,collectionDate);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getPrescriptionId() { return prescriptionId; }
    public void setPrescriptionId(String prescriptionId) { this.prescriptionId = prescriptionId; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getClinicianId() { return clinicianId; }
    public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getPrescribedDate() { return prescribedDate; }
    public void setPrescribedDate(String prescribedDate) { this.prescribedDate = prescribedDate; }
    public String getMedication() { return medication; }
    public void setMedication(String medication) { this.medication = medication; }
    public String getDosage() { return dosage; }
    public void setDosage(String dosage) { this.dosage = dosage; }
    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }
    public String getQuantity() { return quantity; }
    public void setQuantity(String quantity) { this.quantity = quantity; }
    public String getRefills() { return refills; }
    public void setRefills(String refills) { this.refills = refills; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public String getPharmacyName() { return pharmacyName; }
    public void setPharmacyName(String pharmacyName) { this.pharmacyName = pharmacyName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getIssueDate() { return issueDate; }
    public void setIssueDate(String issueDate) { this.issueDate = issueDate; }
    public String getCollectionDate() { return collectionDate; }
    public void setCollectionDate(String collectionDate) { this.collectionDate = collectionDate; }
}
