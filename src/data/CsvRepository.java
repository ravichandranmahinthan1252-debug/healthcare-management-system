package data;

import model.*;
import util.CsvUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads/writes the provided CSV files into the DataStore.
 * Uses a tolerant approach because the provided CSV columns contain '...' truncation in headers/values.
 */
public class CsvRepository {

    public void loadAll(DataStore ds, File baseDir) throws IOException {
        ds.patientsFile = resolve(baseDir, "patients.csv");
        ds.cliniciansFile = resolve(baseDir, "clinicians.csv");
        ds.facilitiesFile = resolve(baseDir, "facilities.csv");
        ds.appointmentsFile = resolve(baseDir, "appointments.csv");
        ds.prescriptionsFile = resolve(baseDir, "prescriptions.csv");
        ds.referralsFile = resolve(baseDir, "referrals.csv");
        ds.staffFile = resolve(baseDir, "staff.csv");

        loadPatients(ds);
        loadClinicians(ds);
        loadFacilities(ds);
        loadAppointments(ds);
        loadPrescriptions(ds);
        loadReferrals(ds);
        loadStaff(ds);
    }

    private File resolve(File baseDir, String name) {
        if (baseDir != null) {
            File f = new File(baseDir, name);
            if (f.exists()) return f;
        }
        File local = new File(name);
        return local.exists() ? local : (baseDir != null ? new File(baseDir, name) : local);
    }

    private void loadPatients(DataStore ds) throws IOException {
        ds.patients.clear();
        List<String[]> rows = CsvUtil.readAll(ds.patientsFile);
        ds.patientsHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.patients.add(Patient.fromCsv(rows.get(i)));
    }

    private void loadClinicians(DataStore ds) throws IOException {
        ds.clinicians.clear();
        List<String[]> rows = CsvUtil.readAll(ds.cliniciansFile);
        ds.cliniciansHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.clinicians.add(Clinician.fromCsv(rows.get(i)));
    }

    private void loadFacilities(DataStore ds) throws IOException {
        ds.facilities.clear();
        List<String[]> rows = CsvUtil.readAll(ds.facilitiesFile);
        ds.facilitiesHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.facilities.add(Facility.fromCsv(rows.get(i)));
    }

    private void loadAppointments(DataStore ds) throws IOException {
        ds.appointments.clear();
        List<String[]> rows = CsvUtil.readAll(ds.appointmentsFile);
        ds.appointmentsHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.appointments.add(Appointment.fromCsv(rows.get(i)));
    }

    private void loadPrescriptions(DataStore ds) throws IOException {
        ds.prescriptions.clear();
        List<String[]> rows = CsvUtil.readAll(ds.prescriptionsFile);
        ds.prescriptionsHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.prescriptions.add(Prescription.fromCsv(rows.get(i)));
    }

    private void loadReferrals(DataStore ds) throws IOException {
        ds.referrals.clear();
        List<String[]> rows = CsvUtil.readAll(ds.referralsFile);
        ds.referralsHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.referrals.add(Referral.fromCsv(rows.get(i)));
    }

    private void loadStaff(DataStore ds) throws IOException {
        ds.staff.clear();
        List<String[]> rows = CsvUtil.readAll(ds.staffFile);
        ds.staffHeader = rows.get(0);
        for (int i = 1; i < rows.size(); i++) ds.staff.add(StaffMember.fromCsv(rows.get(i)));
    }

    public void savePatients(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.patientsHeader);
        for (Patient p : ds.patients) rows.add(p.toCsv(ds.patientsHeader));
        CsvUtil.writeAll(ds.patientsFile, rows);
    }

    public void saveClinicians(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.cliniciansHeader);
        for (Clinician c : ds.clinicians) rows.add(c.toCsv(ds.cliniciansHeader));
        CsvUtil.writeAll(ds.cliniciansFile, rows);
    }

    public void saveAppointments(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.appointmentsHeader);
        for (Appointment a : ds.appointments) rows.add(a.toCsv(ds.appointmentsHeader));
        CsvUtil.writeAll(ds.appointmentsFile, rows);
    }

    public void savePrescriptions(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.prescriptionsHeader);
        for (Prescription p : ds.prescriptions) rows.add(p.toCsv(ds.prescriptionsHeader));
        CsvUtil.writeAll(ds.prescriptionsFile, rows);
    }

    public void saveReferrals(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.referralsHeader);
        for (Referral r : ds.referrals) rows.add(r.toCsv(ds.referralsHeader));
        CsvUtil.writeAll(ds.referralsFile, rows);
    }

    public void saveStaff(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.staffHeader);
        for (StaffMember s : ds.staff) rows.add(s.toCsv(ds.staffHeader));
        CsvUtil.writeAll(ds.staffFile, rows);
    }

    public void saveFacilities(DataStore ds) throws IOException {
        List<String[]> rows = new ArrayList<>();
        rows.add(ds.facilitiesHeader);
        for (Facility f : ds.facilities) rows.add(f.toCsv(ds.facilitiesHeader));
        CsvUtil.writeAll(ds.facilitiesFile, rows);
    }
}
