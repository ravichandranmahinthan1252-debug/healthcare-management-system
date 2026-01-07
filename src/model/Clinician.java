package model;

public class Clinician extends Staff {
    private String title; // GP, Nurse, Specialist etc
    private String speciality;
    private String registrationNumber; // GMC/NMC
    private String phone;
    private String email;
    private String workplaceId;
    private String workplaceType;
    private String employmentStatus;
    private String startDate;

    public Clinician(String clinicianId, String firstName, String lastName, String role) {
        super(clinicianId, firstName, lastName, role);
    }

    public static Clinician fromCsv(String[] r) {
        // clinicians.csv: clinician_id, first, last, title, speciality, regNo, phone, email, workplace_id, workplace_type, employment_status, start_date
        Clinician c = new Clinician(val(r,0), val(r,1), val(r,2), "Clinician");
        c.title = val(r,3);
        c.speciality = val(r,4);
        c.registrationNumber = val(r,5);
        c.phone = val(r,6);
        c.email = val(r,7);
        c.workplaceId = val(r,8);
        c.workplaceType = val(r,9);
        c.employmentStatus = val(r,10);
        c.startDate = val(r,11);
        return c;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,staffId);
        set(r,1,firstName);
        set(r,2,lastName);
        set(r,3,title);
        set(r,4,speciality);
        set(r,5,registrationNumber);
        set(r,6,phone);
        set(r,7,email);
        set(r,8,workplaceId);
        set(r,9,workplaceType);
        set(r,10,employmentStatus);
        set(r,11,startDate);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSpeciality() { return speciality; }
    public void setSpeciality(String speciality) { this.speciality = speciality; }
    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getWorkplaceId() { return workplaceId; }
    public void setWorkplaceId(String workplaceId) { this.workplaceId = workplaceId; }
    public String getWorkplaceType() { return workplaceType; }
    public void setWorkplaceType(String workplaceType) { this.workplaceType = workplaceType; }
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
}
