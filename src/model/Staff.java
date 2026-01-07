package model;

public abstract class Staff {
    protected String staffId;
    protected String firstName;
    protected String lastName;
    protected String role;

    protected Staff(String staffId, String firstName, String lastName, String role) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return (firstName + " " + lastName).trim(); }

    // class diagram method placeholder
    public boolean login(String username, String password) {
        return username != null && !username.isBlank();
    }
}
