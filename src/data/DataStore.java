package data;

import model.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory "database" (no DB tech as per assignment).
 * Holds loaded datasets and remembers original headers for round-trip CSV writing.
 */
public class DataStore {
    public File patientsFile;
    public File cliniciansFile;
    public File facilitiesFile;
    public File appointmentsFile;
    public File prescriptionsFile;
    public File referralsFile;
    public File staffFile;

    public String[] patientsHeader;
    public String[] cliniciansHeader;
    public String[] facilitiesHeader;
    public String[] appointmentsHeader;
    public String[] prescriptionsHeader;
    public String[] referralsHeader;
    public String[] staffHeader;

    public final List<Patient> patients = new ArrayList<>();
    public final List<Clinician> clinicians = new ArrayList<>();
    public final List<Facility> facilities = new ArrayList<>();
    public final List<Appointment> appointments = new ArrayList<>();
    public final List<Prescription> prescriptions = new ArrayList<>();
    public final List<Referral> referrals = new ArrayList<>();
    public final List<StaffMember> staff = new ArrayList<>();
}
