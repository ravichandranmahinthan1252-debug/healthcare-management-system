package model;

/**
 * Non-clinical staff entries loaded from staff.csv.
 * Receptionist is represented by role == "Receptionist".
 */
public class StaffMember extends Staff {
    private String department;
    private String facilityId;
    private String phone;
    private String email;
    private String employmentStatus;
    private String startDate;
    private String lineManager;
    private String accessLevel;

    public StaffMember(String staffId, String firstName, String lastName, String role) {
        super(staffId, firstName, lastName, role);
    }

    public static StaffMember fromCsv(String[] r) {
        StaffMember s = new StaffMember(val(r,0), val(r,1), val(r,2), val(r,3));
        s.department = val(r,4);
        s.facilityId = val(r,5);
        s.phone = val(r,6);
        s.email = val(r,7);
        s.employmentStatus = val(r,8);
        s.startDate = val(r,9);
        s.lineManager = val(r,10);
        s.accessLevel = val(r,11);
        return s;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,staffId);
        set(r,1,firstName);
        set(r,2,lastName);
        set(r,3,role);
        set(r,4,department);
        set(r,5,facilityId);
        set(r,6,phone);
        set(r,7,email);
        set(r,8,employmentStatus);
        set(r,9,startDate);
        set(r,10,lineManager);
        set(r,11,accessLevel);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEmploymentStatus() { return employmentStatus; }
    public void setEmploymentStatus(String employmentStatus) { this.employmentStatus = employmentStatus; }
    public String getStartDate() { return startDate; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public String getLineManager() { return lineManager; }
    public void setLineManager(String lineManager) { this.lineManager = lineManager; }
    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}
