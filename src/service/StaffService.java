package service;

import data.CsvRepository;
import data.DataStore;
import model.StaffMember;
import util.IdGenerator;

import java.io.IOException;

public class StaffService {
    private final DataStore ds;
    private final CsvRepository repo;

    public StaffService(DataStore ds, CsvRepository repo) {
        this.ds = ds;
        this.repo = repo;
    }

    public StaffMember createStaff(String firstName, String lastName, String role, String department, 
                                   String facilityId, String phone, String email, String employmentStatus, 
                                   String lineManager, String accessLevel) throws IOException {
        String id = IdGenerator.nextWithPrefix("ST", ds.staff.size(), 3);
        StaffMember s = new StaffMember(id, firstName, lastName, role);
        s.setDepartment(department);
        s.setFacilityId(facilityId);
        s.setPhone(phone);
        s.setEmail(email);
        s.setEmploymentStatus(employmentStatus);
        s.setStartDate(java.time.LocalDate.now().toString());
        s.setLineManager(lineManager);
        s.setAccessLevel(accessLevel);
        ds.staff.add(s);
        repo.saveStaff(ds);
        return s;
    }

    public void updateStaff() throws IOException {
        repo.saveStaff(ds);
    }

    public void deleteStaff(StaffMember s) throws IOException {
        ds.staff.remove(s);
        repo.saveStaff(ds);
    }
}