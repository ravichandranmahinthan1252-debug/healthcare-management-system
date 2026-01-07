package service;

import data.CsvRepository;
import data.DataStore;
import model.Patient;
import util.IdGenerator;

import java.io.IOException;

public class PatientService {
    private final DataStore ds;
    private final CsvRepository repo;

    public PatientService(DataStore ds, CsvRepository repo) {
        this.ds = ds;
        this.repo = repo;
    }

    public Patient createPatient(String firstName, String lastName, String dob, String nhs, String gender, String phone, String email, String gpSurgeryId) throws IOException {
        String id = IdGenerator.nextWithPrefix("P", ds.patients.size(), 3);
        Patient p = new Patient(id, firstName, lastName);
        p.setDateOfBirth(dob);
        p.setNhsNumber(nhs);
        p.setGender(gender);
        p.setPhone(phone);
        p.setEmail(email);
        p.setGpSurgeryId(gpSurgeryId);
        p.setRegistrationDate(java.time.LocalDate.now().toString());
        ds.patients.add(p);
        repo.savePatients(ds);
        return p;
    }

    public void updatePatients() throws IOException {
        repo.savePatients(ds);
    }

    public void deletePatient(Patient p) throws IOException {
        ds.patients.remove(p);
        repo.savePatients(ds);
    }
}
