package model;

public class Referral {
    private String referralId;
    private String patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private String referredToFacilityId;
    private String referralDate;
    private String urgency;
    private String clinicalSummary;
    private String diagnosis;
    private String medications;
    private String investigations;
    private String status;
    private String appointmentId;
    private String notes;
    private String createdDate;
    private String lastUpdated;

    public Referral(String referralId, String patientId) {
        this.referralId = referralId;
        this.patientId = patientId;
    }

    public static Referral fromCsv(String[] r) {
        Referral ref = new Referral(val(r,0), val(r,1));
        ref.referringClinicianId = val(r,2);
        ref.referredToClinicianId = val(r,3);
        ref.referringFacilityId = val(r,4);
        ref.referredToFacilityId = val(r,5);
        ref.referralDate = val(r,6);
        ref.urgency = val(r,7);
        ref.clinicalSummary = val(r,8);
        ref.diagnosis = val(r,9);
        ref.medications = val(r,10);
        ref.investigations = val(r,11);
        ref.status = val(r,12);
        ref.appointmentId = val(r,13);
        ref.notes = val(r,14);
        ref.createdDate = val(r,15);
        ref.lastUpdated = (r.length > 16) ? r[16] : val(r,15);
        return ref;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,referralId);
        set(r,1,patientId);
        set(r,2,referringClinicianId);
        set(r,3,referredToClinicianId);
        set(r,4,referringFacilityId);
        set(r,5,referredToFacilityId);
        set(r,6,referralDate);
        set(r,7,urgency);
        set(r,8,clinicalSummary);
        set(r,9,diagnosis);
        set(r,10,medications);
        set(r,11,investigations);
        set(r,12,status);
        set(r,13,appointmentId);
        set(r,14,notes);
        set(r,15,createdDate);
        if (header.length > 16) set(r,16,lastUpdated);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getReferralId() { return referralId; }
    public void setReferralId(String referralId) { this.referralId = referralId; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getReferringClinicianId() { return referringClinicianId; }
    public void setReferringClinicianId(String referringClinicianId) { this.referringClinicianId = referringClinicianId; }
    public String getReferredToClinicianId() { return referredToClinicianId; }
    public void setReferredToClinicianId(String referredToClinicianId) { this.referredToClinicianId = referredToClinicianId; }
    public String getReferringFacilityId() { return referringFacilityId; }
    public void setReferringFacilityId(String referringFacilityId) { this.referringFacilityId = referringFacilityId; }
    public String getReferredToFacilityId() { return referredToFacilityId; }
    public void setReferredToFacilityId(String referredToFacilityId) { this.referredToFacilityId = referredToFacilityId; }
    public String getReferralDate() { return referralDate; }
    public void setReferralDate(String referralDate) { this.referralDate = referralDate; }
    public String getUrgency() { return urgency; }
    public void setUrgency(String urgency) { this.urgency = urgency; }
    public String getClinicalSummary() { return clinicalSummary; }
    public void setClinicalSummary(String clinicalSummary) { this.clinicalSummary = clinicalSummary; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }
    public String getInvestigations() { return investigations; }
    public void setInvestigations(String investigations) { this.investigations = investigations; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public String getLastUpdated() { return lastUpdated; }
    public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
}
