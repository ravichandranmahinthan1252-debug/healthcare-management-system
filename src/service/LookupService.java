package service;

import data.DataStore;
import model.*;

public class LookupService {
    private final DataStore ds;

    public LookupService(DataStore ds) {
        this.ds = ds;
    }

    public Patient findPatient(String patientId) {
        for (Patient p : ds.patients) if (p.getPatientId().equals(patientId)) return p;
        return null;
    }

    public Clinician findClinician(String clinicianId) {
        for (Clinician c : ds.clinicians) if (c.getStaffId().equals(clinicianId)) return c;
        return null;
    }

    public Facility findFacility(String facilityId) {
        for (Facility f : ds.facilities) if (f.getFacilityId().equals(facilityId)) return f;
        return null;
    }
}
