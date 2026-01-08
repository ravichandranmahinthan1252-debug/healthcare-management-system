package service;

import data.CsvRepository;
import data.DataStore;
import model.Clinician;
import model.Patient;
import model.Prescription;
import util.IdGenerator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class PrescriptionService {
    private final DataStore ds;
    private final LookupService lookup;
    private final CsvRepository repo;

    public PrescriptionService(DataStore ds, LookupService lookup, CsvRepository repo) {
        this.ds = ds;
        this.lookup = lookup;
        this.repo = repo;
    }

    public Prescription createPrescription(String patientId, String clinicianId, String appointmentId,
                                           String medication, String dosage, String frequency,
                                           String quantity, String instructions, String pharmacy) throws IOException {
        String id = IdGenerator.nextWithPrefix("RX", ds.prescriptions.size(), 3);
        Prescription p = new Prescription(id, patientId, clinicianId);
        p.setAppointmentId(appointmentId);
        p.setPrescribedDate(LocalDate.now().toString());
        p.setMedication(medication);
        p.setDosage(dosage);
        p.setFrequency(frequency);
        p.setQuantity(quantity);
        p.setInstructions(instructions);
        p.setPharmacyName(pharmacy);
        p.setStatus("Issued");
        p.setIssueDate(LocalDate.now().toString());
        p.setCollectionDate("");

        ds.prescriptions.add(p);
        repo.savePrescriptions(ds);

        return p;
    }

    public String generatePrescriptionText(Prescription pr) {
        Patient pat = lookup.findPatient(pr.getPatientId());
        Clinician cli = lookup.findClinician(pr.getClinicianId());

        StringBuilder sb = new StringBuilder();
        sb.append("PRESCRIPTION (Simulated Printout)\n");
        sb.append("Prescription ID: ").append(pr.getPrescriptionId()).append("\n");
        sb.append("Issue Date: ").append(pr.getIssueDate()).append("\n\n");
        sb.append("Patient: ").append(pat != null ? pat.getFullName() : pr.getPatientId()).append("\n");
        if (pat != null) sb.append("NHS Number: ").append(pat.getNhsNumber()).append("\n");
        sb.append("Prescriber: ").append(cli != null ? cli.getFullName() : pr.getClinicianId()).append("\n\n");
        sb.append("Medication: ").append(pr.getMedication()).append("\n");
        sb.append("Dosage: ").append(pr.getDosage()).append("\n");
        sb.append("Frequency: ").append(pr.getFrequency()).append("\n");
        sb.append("Quantity: ").append(pr.getQuantity()).append("\n");
        sb.append("Instructions: ").append(pr.getInstructions()).append("\n\n");
        sb.append("Pharmacy: ").append(pr.getPharmacyName()).append("\n");
        sb.append("Status: ").append(pr.getStatus()).append("\n");
        return sb.toString();
    }

    public void persistPrescriptionText(File outputDir, Prescription pr) throws IOException {
        outputDir.mkdirs();
        String txt = generatePrescriptionText(pr);
        File out = new File(outputDir, "prescription_" + pr.getPrescriptionId() + ".txt");
        try (FileWriter fw = new FileWriter(out, false)) {
            fw.write(txt);
        }
        File combined = new File(outputDir, "prescriptions_outbox.txt");
        try (FileWriter fw = new FileWriter(combined, true)) {
            fw.write(txt);
            fw.write(System.lineSeparator() + "-----" + System.lineSeparator());
        }
    }
}
