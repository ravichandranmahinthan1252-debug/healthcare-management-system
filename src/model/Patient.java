package model;

public class Patient {
    private String patientId;
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String nhsNumber;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String postcode;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String registrationDate;
    private String gpSurgeryId;

    public Patient(String patientId, String firstName, String lastName) {
        this.patientId = patientId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Factory from CSV row with best-effort mapping (files include truncated columns with '...')
    public static Patient fromCsv(String[] r) {
        Patient p = new Patient(val(r,0), val(r,1), val(r,2));
        p.dateOfBirth = val(r,3);
        p.nhsNumber = val(r,4);
        p.gender = val(r,5);
        p.phone = val(r,6);
        p.email = val(r,7);
        p.address = val(r,8);
        p.postcode = val(r,9);
        p.emergencyContactName = val(r,10);
        p.emergencyContactPhone = val(r,11);
        p.registrationDate = val(r,12);
        p.gpSurgeryId = val(r,13);
        return p;
    }

    public String[] toCsv(String[] header) {
        // match the provided file's column count (header length)
        String[] r = new String[header.length];
        set(r,0,patientId);
        set(r,1,firstName);
        set(r,2,lastName);
        set(r,3,dateOfBirth);
        set(r,4,nhsNumber);
        set(r,5,gender);
        set(r,6,phone);
        set(r,7,email);
        set(r,8,address);
        set(r,9,postcode);
        set(r,10,emergencyContactName);
        set(r,11,emergencyContactPhone);
        set(r,12,registrationDate);
        set(r,13,gpSurgeryId);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public String getNhsNumber() { return nhsNumber; }
    public void setNhsNumber(String nhsNumber) { this.nhsNumber = nhsNumber; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String emergencyContactName) { this.emergencyContactName = emergencyContactName; }
    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String emergencyContactPhone) { this.emergencyContactPhone = emergencyContactPhone; }
    public String getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(String registrationDate) { this.registrationDate = registrationDate; }
    public String getGpSurgeryId() { return gpSurgeryId; }
    public void setGpSurgeryId(String gpSurgeryId) { this.gpSurgeryId = gpSurgeryId; }

    public String getFullName() { return (firstName + " " + lastName).trim(); }
}
