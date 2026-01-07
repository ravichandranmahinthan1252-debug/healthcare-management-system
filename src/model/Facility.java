package model;

public class Facility {
    private String facilityId;
    private String name;
    private String type;
    private String address;
    private String postcode;
    private String phone;
    private String email;
    private String openingHours;
    private String managerName;
    private String capacity;
    private String specialitiesOffered;

    public Facility(String facilityId, String name) {
        this.facilityId = facilityId;
        this.name = name;
    }

    public static Facility fromCsv(String[] r) {
        Facility f = new Facility(val(r,0), val(r,1));
        f.type = val(r,2);
        f.address = val(r,3);
        f.postcode = val(r,4);
        f.phone = val(r,5);
        f.email = val(r,6);
        f.openingHours = val(r,7);
        f.managerName = val(r,8);
        f.capacity = val(r,9);
        f.specialitiesOffered = val(r,10);
        return f;
    }

    public String[] toCsv(String[] header) {
        String[] r = new String[header.length];
        set(r,0,facilityId);
        set(r,1,name);
        set(r,2,type);
        set(r,3,address);
        set(r,4,postcode);
        set(r,5,phone);
        set(r,6,email);
        set(r,7,openingHours);
        set(r,8,managerName);
        set(r,9,capacity);
        set(r,10,specialitiesOffered);
        return r;
    }

    private static String val(String[] r, int i){ return (r!=null && i<r.length) ? r[i] : ""; }
    private static void set(String[] r,int i,String v){ if(i<r.length) r[i]=v==null?"":v; }

    public String getFacilityId() { return facilityId; }
    public void setFacilityId(String facilityId) { this.facilityId = facilityId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getOpeningHours() { return openingHours; }
    public void setOpeningHours(String openingHours) { this.openingHours = openingHours; }
    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }
    public String getCapacity() { return capacity; }
    public void setCapacity(String capacity) { this.capacity = capacity; }
    public String getSpecialitiesOffered() { return specialitiesOffered; }
    public void setSpecialitiesOffered(String specialitiesOffered) { this.specialitiesOffered = specialitiesOffered; }
}
