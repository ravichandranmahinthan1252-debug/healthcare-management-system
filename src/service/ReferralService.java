package service;

import data.CsvRepository;
import data.DataStore;
import model.Referral;
import util.IdGenerator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ReferralService {
    private final DataStore ds;
    private final ReferralManager manager;

    public ReferralService(DataStore ds, LookupService lookup, CsvRepository repo) {
        this.ds = ds;
        this.manager = ReferralManager.getInstance(ds, lookup, repo);
    }

    public Referral createReferral(String patientId, String referringClinicianId,
                                   String referredToClinicianId, String fromFacilityId, String toFacilityId,
                                   String urgency, String clinicalSummary, String diagnosis,
                                   String medications, String investigations, String appointmentId, String notes) {
        String id = IdGenerator.nextWithPrefix("R", ds.referrals.size(), 3);
        Referral r = new Referral(id, patientId);
        r.setReferringClinicianId(referringClinicianId);
        r.setReferredToClinicianId(referredToClinicianId);
        r.setReferringFacilityId(fromFacilityId);
        r.setReferredToFacilityId(toFacilityId);
        r.setReferralDate(LocalDate.now().toString());
        r.setUrgency(urgency);
        r.setClinicalSummary(clinicalSummary);
        r.setDiagnosis(diagnosis);
        r.setMedications(medications);
        r.setInvestigations(investigations);
        r.setStatus("Pending");
        r.setAppointmentId(appointmentId);
        r.setNotes(notes);
        r.setCreatedDate(LocalDate.now().toString());
        r.setLastUpdated(LocalDate.now().toString());

        manager.enqueue(r);
        return r;
    }

    public Referral processNext(File outputDir) throws IOException {
        outputDir.mkdirs();
        return manager.processNext(outputDir);
    }

    public Referral peekNext() { return manager.peekNext(); }

    public String previewEmail(Referral r) {
        return manager.generateReferralText(r);
    }
}
