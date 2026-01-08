package model;

public class Appointment {
    private String appointmentId;
    private String patientId;
    private String clinicianId;
    private String facilityId;
    private String date;
    private String time;
    private String durationMinutes;
    private String appointmentType;
    private String status;
    private String reasonForVisit;
    private String notes;
    private String createdDate;
    private String lastModified;

    public Appointment(String appointmentId, String patientId, String clinicianId) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
    }

    public static Appointment fromCsv(String[] r) {
        // appointments.csv likely 13 cols (see header count)
        Appointment a = new Appointment(val(r,0), val(r,1), val(r,2));
        a.facilityId = val(r,3);
        a.date = val(r,4);
        a.time = val(r,5);
        a.durationMinutes = val(r,6);
        a.appointmentType = val(r,7);
        a.status = val(r,8);
        a.reasonForVisit = val(r,9);
        a.notes = val(r,10);
        a.createdDate = val(r,11);
        a.lastModified = val(r,12);
        return a;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,appointmentId);
        set(r,1,patientId);
        set(r,2,clinicianId);
        set(r,3,facilityId);
        set(r,4,date);
        set(r,5,time);
        set(r,6,durationMinutes);
        set(r,7,appointmentType);
        set(r,8,status);
        set(r,9,reasonForVisit);
        set(r,10,notes);
        set(r,11,createdDate);
        set(r,12,lastModified);
        return r;
    }

    public boolean checkAvailability() {
        // simplified: availability handled by controller/service; this is a placeholder for class diagram
        return true;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getAppointmentId() { return appointmentId; }
    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getClinicianId() { return clinicianId; }
    public void setClinicianId(String clinicianId) { this.clinicianId = clinicianId; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(String durationMinutes) { this.durationMinutes = durationMinutes; }
    public String getAppointmentType() { return appointmentType; }
    public void setAppointmentType(String appointmentType) { this.appointmentType = appointmentType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getReasonForVisit() { return reasonForVisit; }
    public void setReasonForVisit(String reasonForVisit) { this.reasonForVisit = reasonForVisit; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }
    public String getLastModified() { return lastModified; }
    public void setLastModified(String lastModified) { this.lastModified = lastModified; }
}
