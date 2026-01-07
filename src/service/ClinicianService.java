package service;

import data.CsvRepository;
import data.DataStore;
import model.Clinician;
import util.IdGenerator;

import java.io.IOException;

public class ClinicianService {
    private final DataStore ds;
    private final CsvRepository repo;

    public ClinicianService(DataStore ds, CsvRepository repo) {
        this.ds = ds;
        this.repo = repo;
    }

    public Clinician createClinician(String firstName, String lastName, String title, String speciality, 
                                   String registrationNumber, String phone, String email, String workplaceId, 
                                   String workplaceType, String employmentStatus, String startDate) throws IOException {
        String id = IdGenerator.nextWithPrefix("C", ds.clinicians.size(), 3);
        Clinician c = new Clinician(id, firstName, lastName, "Clinician");
        c.setTitle(title);
        c.setSpeciality(speciality);
        c.setRegistrationNumber(registrationNumber);
        c.setPhone(phone);
        c.setEmail(email);
        c.setWorkplaceId(workplaceId);
        c.setWorkplaceType(workplaceType);
        c.setEmploymentStatus(employmentStatus);
        c.setStartDate(startDate);
        ds.clinicians.add(c);
        repo.saveClinicians(ds);
        return c;
    }

    public void updateClinicians() throws IOException {
        repo.saveClinicians(ds);
    }

    public void deleteClinician(Clinician c) throws IOException {
        ds.clinicians.remove(c);
        repo.saveClinicians(ds);
    }
}