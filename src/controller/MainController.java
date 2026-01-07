package controller;

import data.CsvRepository;
import data.DataStore;
import model.*;
import service.*;
import view.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MainController {
    private final DataStore ds = new DataStore();
    private final CsvRepository repo = new CsvRepository();

    private LookupService lookup;
    private PatientService patientService;
    private AppointmentService appointmentService;
    private PrescriptionService prescriptionService;
    private ReferralService referralService;
    private StaffService staffService;
    private FacilityService facilityService;

    private final MainFrame frame = new MainFrame();

    // Default to using the same folder as the CSV data files (as requested).
    private File dataDir = new File(".");
    private File outputDir = new File(".");

    public void start() {
        frame.attachMenuListener(this::onMenu);
        wirePanels();
        frame.setVisible(true);

        // attempt initial load
        tryLoadData();
    }

    private void initServices() {
        lookup = new LookupService(ds);
        patientService = new PatientService(ds, repo);
        appointmentService = new AppointmentService(ds, repo);
        prescriptionService = new PrescriptionService(ds, lookup, repo);
        referralService = new ReferralService(ds, lookup, repo);
        staffService = new StaffService(ds, repo);
        facilityService = new FacilityService(ds, repo);
    }

    private void tryLoadData() {
        try {
            repo.loadAll(ds, dataDir);
            initServices();
            refreshAllTables();
            // Keep outputs (referral/prescription text) in the same folder as the CSV files by default.
            outputDir = dataDir;
            frame.setStatus("Loaded data from: " + dataDir.getAbsolutePath());
        } catch (Exception ex) {
            frame.setStatus("Data not loaded.");
            frame.showError("Could not load data files. Please choose the folder that contains the CSV files.", ex);
        }
    }

    private void refreshAllTables() {
        frame.getPatientsPanel().model.setItems(ds.patients);
        frame.getCliniciansPanel().model.setItems(ds.clinicians);
        frame.getStaffPanel().model.setItems(ds.staff);
        frame.getFacilityPanel().model.setItems(ds.facilities);
        frame.getAppointmentsPanel().model.setItems(ds.appointments);
        frame.getPrescriptionsPanel().model.setItems(ds.prescriptions);
        frame.getReferralsPanel().model.setItems(ds.referrals);
    }

    private void onMenu(ActionEvent e) {
        String cmd = e.getActionCommand();
        if ("chooseDataDir".equals(cmd)) {
            File chosen = frame.chooseDirectory("Select folder containing CSV files", dataDir);
            if (chosen != null) {
                dataDir = chosen;
                tryLoadData();
            }
        } else if ("chooseOutDir".equals(cmd)) {
            File chosen = frame.chooseDirectory("Select output folder for referral/prescription text files", outputDir);
            if (chosen != null) {
                outputDir = chosen;
                frame.setStatus("Output folder: " + outputDir.getAbsolutePath());
            }
        } else if ("reload".equals(cmd)) {
            tryLoadData();
        }
    }

    private void wirePanels() {
        wirePatients();
        wireClinicians();
        wireStaff();
        wireFacilities();
        wireAppointments();
        wirePrescriptions();
        wireReferrals();
    }

    private void wirePatients() {
        PatientsPanel p = frame.getPatientsPanel();

        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("First Name", "")
                    .addField("Last Name", "")
                    .addField("DOB (YYYY-MM-DD)", "")
                    .addField("NHS Number", "")
                    .addField("Gender", "")
                    .addField("Phone", "")
                    .addField("Email", "")
                    .addField("GP Surgery ID", "");
            if (FormDialog.showConfirm(frame, "New Patient", fb.getPanel())) {
                try {
                    patientService.createPatient(
                            fb.get("First Name"),
                            fb.get("Last Name"),
                            fb.get("DOB (YYYY-MM-DD)"),
                            fb.get("NHS Number"),
                            fb.get("Gender"),
                            fb.get("Phone"),
                            fb.get("Email"),
                            fb.get("GP Surgery ID")
                    );
                    refreshAllTables();
                    frame.setStatus("Patient created.");
                } catch (IOException ex) {
                    frame.showError("Failed to create patient.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            Patient sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a patient first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("First Name", sel.getFirstName())
                    .addField("Last Name", sel.getLastName())
                    .addField("DOB (YYYY-MM-DD)", sel.getDateOfBirth())
                    .addField("NHS Number", sel.getNhsNumber())
                    .addField("Gender", sel.getGender())
                    .addField("Phone", sel.getPhone())
                    .addField("Email", sel.getEmail())
                    .addField("GP Surgery ID", sel.getGpSurgeryId());
            if (FormDialog.showConfirm(frame, "Edit Patient: " + sel.getPatientId(), fb.getPanel())) {
                sel.setFirstName(fb.get("First Name"));
                sel.setLastName(fb.get("Last Name"));
                sel.setDateOfBirth(fb.get("DOB (YYYY-MM-DD)"));
                sel.setNhsNumber(fb.get("NHS Number"));
                sel.setGender(fb.get("Gender"));
                sel.setPhone(fb.get("Phone"));
                sel.setEmail(fb.get("Email"));
                sel.setGpSurgeryId(fb.get("GP Surgery ID"));

                try {
                    patientService.updatePatients();
                    refreshAllTables();
                    frame.setStatus("Patient updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save patient changes.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            Patient sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a patient first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete patient " + sel.getPatientId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    patientService.deletePatient(sel);
                    refreshAllTables();
                    frame.setStatus("Patient deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete patient.", ex);
                }
            }
        });
    }

    private void wireClinicians() {
        CliniciansPanel p = frame.getCliniciansPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            // keep clinician IDs consistent: user enters
            FormBuilder fb = new FormBuilder()
                    .addField("Clinician ID (e.g., C999)", "")
                    .addField("First Name", "")
                    .addField("Last Name", "")
                    .addField("Title", "")
                    .addField("Speciality", "")
                    .addField("Registration No", "")
                    .addField("Phone", "")
                    .addField("Email", "")
                    .addField("Workplace ID", "");
            if (FormDialog.showConfirm(frame, "New Clinician", fb.getPanel())) {
                Clinician c = new Clinician(fb.get("Clinician ID (e.g., C999)"), fb.get("First Name"), fb.get("Last Name"), "Clinician");
                c.setTitle(fb.get("Title"));
                c.setSpeciality(fb.get("Speciality"));
                c.setRegistrationNumber(fb.get("Registration No"));
                c.setPhone(fb.get("Phone"));
                c.setEmail(fb.get("Email"));
                c.setWorkplaceId(fb.get("Workplace ID"));
                ds.clinicians.add(c);
                try {
                    repo.saveClinicians(ds);
                    refreshAllTables();
                    frame.setStatus("Clinician added.");
                } catch (IOException ex) {
                    frame.showError("Failed to save clinician.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            Clinician sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a clinician first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("First Name", sel.getFirstName())
                    .addField("Last Name", sel.getLastName())
                    .addField("Title", sel.getTitle())
                    .addField("Speciality", sel.getSpeciality())
                    .addField("Registration No", sel.getRegistrationNumber())
                    .addField("Phone", sel.getPhone())
                    .addField("Email", sel.getEmail())
                    .addField("Workplace ID", sel.getWorkplaceId());
            if (FormDialog.showConfirm(frame, "Edit Clinician: " + sel.getStaffId(), fb.getPanel())) {
                sel.setFirstName(fb.get("First Name"));
                sel.setLastName(fb.get("Last Name"));
                sel.setTitle(fb.get("Title"));
                sel.setSpeciality(fb.get("Speciality"));
                sel.setRegistrationNumber(fb.get("Registration No"));
                sel.setPhone(fb.get("Phone"));
                sel.setEmail(fb.get("Email"));
                sel.setWorkplaceId(fb.get("Workplace ID"));

                try {
                    repo.saveClinicians(ds);
                    refreshAllTables();
                    frame.setStatus("Clinician updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save clinician changes.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            Clinician sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a clinician first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete clinician " + sel.getStaffId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                ds.clinicians.remove(sel);
                try {
                    repo.saveClinicians(ds);
                    refreshAllTables();
                    frame.setStatus("Clinician deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete clinician.", ex);
                }
            }
        });
    }

    private void wireStaff() {
        StaffPanel p = frame.getStaffPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("First Name", "")
                    .addField("Last Name", "")
                    .addField("Role", "")
                    .addField("Department", "")
                    .addField("Facility ID", "")
                    .addField("Phone", "")
                    .addField("Email", "")
                    .addField("Employment Status", "Full-time")
                    .addField("Line Manager", "")
                    .addField("Access Level", "Standard");
            if (FormDialog.showConfirm(frame, "New Staff Member", fb.getPanel())) {
                try {
                    staffService.createStaff(
                            fb.get("First Name"),
                            fb.get("Last Name"),
                            fb.get("Role"),
                            fb.get("Department"),
                            fb.get("Facility ID"),
                            fb.get("Phone"),
                            fb.get("Email"),
                            fb.get("Employment Status"),
                            fb.get("Line Manager"),
                            fb.get("Access Level")
                    );
                    refreshAllTables();
                    frame.setStatus("Staff member created.");
                } catch (IOException ex) {
                    frame.showError("Failed to create staff member.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            StaffMember sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a staff member first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("First Name", sel.getFirstName())
                    .addField("Last Name", sel.getLastName())
                    .addField("Role", sel.getRole())
                    .addField("Department", sel.getDepartment())
                    .addField("Facility ID", sel.getFacilityId())
                    .addField("Phone", sel.getPhone())
                    .addField("Email", sel.getEmail())
                    .addField("Employment Status", sel.getEmploymentStatus())
                    .addField("Line Manager", sel.getLineManager())
                    .addField("Access Level", sel.getAccessLevel());
            if (FormDialog.showConfirm(frame, "Edit Staff Member: " + sel.getStaffId(), fb.getPanel())) {
                sel.setFirstName(fb.get("First Name"));
                sel.setLastName(fb.get("Last Name"));
                sel.setRole(fb.get("Role"));
                sel.setDepartment(fb.get("Department"));
                sel.setFacilityId(fb.get("Facility ID"));
                sel.setPhone(fb.get("Phone"));
                sel.setEmail(fb.get("Email"));
                sel.setEmploymentStatus(fb.get("Employment Status"));
                sel.setLineManager(fb.get("Line Manager"));
                sel.setAccessLevel(fb.get("Access Level"));

                try {
                    staffService.updateStaff();
                    refreshAllTables();
                    frame.setStatus("Staff member updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save staff member changes.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            StaffMember sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a staff member first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete staff member " + sel.getStaffId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    staffService.deleteStaff(sel);
                    refreshAllTables();
                    frame.setStatus("Staff member deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete staff member.", ex);
                }
            }
        });
    }

    private void wireFacilities() {
        FacilityPanel p = frame.getFacilityPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("Name", "")
                    .addField("Type (GP Surgery/Hospital)", "GP Surgery")
                    .addField("Address", "")
                    .addField("Postcode", "")
                    .addField("Phone", "")
                    .addField("Email", "")
                    .addField("Opening Hours", "")
                    .addField("Manager Name", "")
                    .addField("Capacity", "")
                    .addField("Specialities Offered", "");
            if (FormDialog.showConfirm(frame, "New Facility", fb.getPanel())) {
                try {
                    facilityService.createFacility(
                            fb.get("Name"),
                            fb.get("Type (GP Surgery/Hospital)"),
                            fb.get("Address"),
                            fb.get("Postcode"),
                            fb.get("Phone"),
                            fb.get("Email"),
                            fb.get("Opening Hours"),
                            fb.get("Manager Name"),
                            fb.get("Capacity"),
                            fb.get("Specialities Offered")
                    );
                    refreshAllTables();
                    frame.setStatus("Facility created.");
                } catch (IOException ex) {
                    frame.showError("Failed to create facility.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            Facility sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a facility first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("Name", sel.getName())
                    .addField("Type (GP Surgery/Hospital)", sel.getType())
                    .addField("Address", sel.getAddress())
                    .addField("Postcode", sel.getPostcode())
                    .addField("Phone", sel.getPhone())
                    .addField("Email", sel.getEmail())
                    .addField("Opening Hours", sel.getOpeningHours())
                    .addField("Manager Name", sel.getManagerName())
                    .addField("Capacity", sel.getCapacity())
                    .addField("Specialities Offered", sel.getSpecialitiesOffered());
            if (FormDialog.showConfirm(frame, "Edit Facility: " + sel.getFacilityId(), fb.getPanel())) {
                sel.setName(fb.get("Name"));
                sel.setType(fb.get("Type (GP Surgery/Hospital)"));
                sel.setAddress(fb.get("Address"));
                sel.setPostcode(fb.get("Postcode"));
                sel.setPhone(fb.get("Phone"));
                sel.setEmail(fb.get("Email"));
                sel.setOpeningHours(fb.get("Opening Hours"));
                sel.setManagerName(fb.get("Manager Name"));
                sel.setCapacity(fb.get("Capacity"));
                sel.setSpecialitiesOffered(fb.get("Specialities Offered"));

                try {
                    facilityService.updateFacilities();
                    refreshAllTables();
                    frame.setStatus("Facility updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save facility changes.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            Facility sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a facility first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete facility " + sel.getFacilityId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    facilityService.deleteFacility(sel);
                    refreshAllTables();
                    frame.setStatus("Facility deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete facility.", ex);
                }
            }
        });
    }

    private void wireAppointments() {
        AppointmentsPanel p = frame.getAppointmentsPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("Patient ID", "")
                    .addField("Clinician ID", "")
                    .addField("Facility ID", "")
                    .addField("Date (YYYY-MM-DD)", "")
                    .addField("Time (HH:MM)", "")
                    .addField("Duration Minutes", "15")
                    .addField("Type", "Routine Consultation")
                    .addField("Reason", "")
                    .addField("Notes", "");
            if (FormDialog.showConfirm(frame, "Book Appointment", fb.getPanel())) {
                try {
                    appointmentService.bookAppointment(
                            fb.get("Patient ID"),
                            fb.get("Clinician ID"),
                            fb.get("Facility ID"),
                            fb.get("Date (YYYY-MM-DD)"),
                            fb.get("Time (HH:MM)"),
                            fb.get("Duration Minutes"),
                            fb.get("Type"),
                            fb.get("Reason"),
                            fb.get("Notes")
                    );
                    refreshAllTables();
                    frame.setStatus("Appointment booked.");
                } catch (IOException ex) {
                    frame.showError("Failed to book appointment.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            Appointment sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select an appointment first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("Patient ID", sel.getPatientId())
                    .addField("Clinician ID", sel.getClinicianId())
                    .addField("Facility ID", sel.getFacilityId())
                    .addField("Date (YYYY-MM-DD)", sel.getDate())
                    .addField("Time (HH:MM)", sel.getTime())
                    .addField("Duration Minutes", sel.getDurationMinutes())
                    .addField("Type", sel.getAppointmentType())
                    .addField("Status", sel.getStatus())
                    .addField("Reason", sel.getReasonForVisit())
                    .addField("Notes", sel.getNotes());
            if (FormDialog.showConfirm(frame, "Edit Appointment: " + sel.getAppointmentId(), fb.getPanel())) {
                sel.setPatientId(fb.get("Patient ID"));
                sel.setClinicianId(fb.get("Clinician ID"));
                sel.setFacilityId(fb.get("Facility ID"));
                sel.setDate(fb.get("Date (YYYY-MM-DD)"));
                sel.setTime(fb.get("Time (HH:MM)"));
                sel.setDurationMinutes(fb.get("Duration Minutes"));
                sel.setAppointmentType(fb.get("Type"));
                sel.setStatus(fb.get("Status"));
                sel.setReasonForVisit(fb.get("Reason"));
                sel.setNotes(fb.get("Notes"));
                sel.setLastModified(java.time.LocalDate.now().toString());

                try {
                    appointmentService.updateAppointments();
                    refreshAllTables();
                    frame.setStatus("Appointment updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save appointment changes.", ex);
                }
            }
        });

        p.btnCancel.addActionListener(e -> {
            Appointment sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select an appointment first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Cancel appointment " + sel.getAppointmentId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    appointmentService.cancelAppointment(sel);
                    refreshAllTables();
                    frame.setStatus("Appointment cancelled.");
                } catch (IOException ex) {
                    frame.showError("Failed to cancel appointment.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            Appointment sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select an appointment first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete appointment " + sel.getAppointmentId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                try {
                    appointmentService.deleteAppointment(sel);
                    refreshAllTables();
                    frame.setStatus("Appointment deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete appointment.", ex);
                }
            }
        });
    }

    private void wirePrescriptions() {
        PrescriptionsPanel p = frame.getPrescriptionsPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnAdd.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("Patient ID", "")
                    .addField("Clinician ID", "")
                    .addField("Appointment ID", "")
                    .addField("Medication", "")
                    .addField("Dosage", "")
                    .addField("Frequency", "")
                    .addField("Quantity", "")
                    .addField("Instructions", "")
                    .addField("Pharmacy", "");
            if (FormDialog.showConfirm(frame, "New Prescription", fb.getPanel())) {
                try {
                    Prescription pr = prescriptionService.createPrescription(
                            fb.get("Patient ID"),
                            fb.get("Clinician ID"),
                            fb.get("Appointment ID"),
                            fb.get("Medication"),
                            fb.get("Dosage"),
                            fb.get("Frequency"),
                            fb.get("Quantity"),
                            fb.get("Instructions"),
                            fb.get("Pharmacy")
                    );
                    prescriptionService.persistPrescriptionText(outputDir, pr);
                    refreshAllTables();
                    frame.setStatus("Prescription created & exported to output folder.");
                } catch (IOException ex) {
                    frame.showError("Failed to create prescription.", ex);
                }
            }
        });

        p.btnEdit.addActionListener(e -> {
            Prescription sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a prescription first."); return; }

            FormBuilder fb = new FormBuilder()
                    .addField("Patient ID", sel.getPatientId())
                    .addField("Clinician ID", sel.getClinicianId())
                    .addField("Appointment ID", sel.getAppointmentId())
                    .addField("Medication", sel.getMedication())
                    .addField("Dosage", sel.getDosage())
                    .addField("Frequency", sel.getFrequency())
                    .addField("Quantity", sel.getQuantity())
                    .addField("Instructions", sel.getInstructions())
                    .addField("Pharmacy", sel.getPharmacyName())
                    .addField("Status", sel.getStatus());
            if (FormDialog.showConfirm(frame, "Edit Prescription: " + sel.getPrescriptionId(), fb.getPanel())) {
                sel.setPatientId(fb.get("Patient ID"));
                sel.setClinicianId(fb.get("Clinician ID"));
                sel.setAppointmentId(fb.get("Appointment ID"));
                sel.setMedication(fb.get("Medication"));
                sel.setDosage(fb.get("Dosage"));
                sel.setFrequency(fb.get("Frequency"));
                sel.setQuantity(fb.get("Quantity"));
                sel.setInstructions(fb.get("Instructions"));
                sel.setPharmacyName(fb.get("Pharmacy"));
                sel.setStatus(fb.get("Status"));

                try {
                    repo.savePrescriptions(ds);
                    refreshAllTables();
                    frame.setStatus("Prescription updated.");
                } catch (IOException ex) {
                    frame.showError("Failed to save prescription changes.", ex);
                }
            }
        });

        p.btnDelete.addActionListener(e -> {
            Prescription sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a prescription first."); return; }
            int res = JOptionPane.showConfirmDialog(frame, "Delete prescription " + sel.getPrescriptionId() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                ds.prescriptions.remove(sel);
                try {
                    repo.savePrescriptions(ds);
                    refreshAllTables();
                    frame.setStatus("Prescription deleted.");
                } catch (IOException ex) {
                    frame.showError("Failed to delete prescription.", ex);
                }
            }
        });

        p.btnExport.addActionListener(e -> {
            Prescription sel = p.getSelected();
            if (sel == null) { frame.showInfo("Select a prescription first."); return; }
            try {
                prescriptionService.persistPrescriptionText(outputDir, sel);
                frame.showInfo("Exported prescription text files to: " + outputDir.getAbsolutePath());
            } catch (IOException ex) {
                frame.showError("Failed to export prescription text.", ex);
            }
        });
    }

    private void wireReferrals() {
        ReferralsPanel p = frame.getReferralsPanel();
        p.btnRefresh.addActionListener(e -> refreshAllTables());

        p.btnNew.addActionListener(e -> {
            FormBuilder fb = new FormBuilder()
                    .addField("Patient ID", "")
                    .addField("Referring Clinician ID (GP)", "")
                    .addField("Referred To Clinician ID (Specialist)", "")
                    .addField("From Facility ID", "")
                    .addField("To Facility ID", "")
                    .addField("Urgency (Routine/Urgent)", "Routine")
                    .addField("Clinical Summary", "")
                    .addField("Diagnosis/Concern", "")
                    .addField("Current Medications", "")
                    .addField("Investigations", "")
                    .addField("Appointment ID", "")
                    .addField("Notes", "");
            if (FormDialog.showConfirm(frame, "New Referral (queued in singleton manager)", fb.getPanel())) {
                Referral r = referralService.createReferral(
                        fb.get("Patient ID"),
                        fb.get("Referring Clinician ID (GP)"),
                        fb.get("Referred To Clinician ID (Specialist)"),
                        fb.get("From Facility ID"),
                        fb.get("To Facility ID"),
                        fb.get("Urgency (Routine/Urgent)"),
                        fb.get("Clinical Summary"),
                        fb.get("Diagnosis/Concern"),
                        fb.get("Current Medications"),
                        fb.get("Investigations"),
                        fb.get("Appointment ID"),
                        fb.get("Notes")
                );
                frame.showInfo("Referral queued in singleton manager: " + r.getReferralId() + "\nClick 'Process Next' to persist and generate email text.");
                frame.setStatus("Referral queued: " + r.getReferralId());
            }
        });

        p.btnProcessNext.addActionListener(e -> {
            try {
                Referral processed = referralService.processNext(outputDir);
                if (processed == null) {
                    frame.showInfo("Referral queue is empty.");
                } else {
                    refreshAllTables();
                    frame.showInfo("Processed referral: " + processed.getReferralId() + "\nSaved text output to: " + outputDir.getAbsolutePath());
                    frame.setStatus("Referral processed via singleton: " + processed.getReferralId());
                }
            } catch (IOException ex) {
                frame.showError("Failed to process referral.", ex);
            }
        });

        p.btnPreview.addActionListener(e -> {
            Referral next = referralService.peekNext();
            if (next == null) {
                frame.showInfo("Referral queue is empty. Create a referral first.");
                return;
            }
            String txt = referralService.previewEmail(next);
            JTextArea area = new JTextArea(txt, 20, 60);
            area.setWrapStyleWord(true);
            area.setLineWrap(true);
            area.setCaretPosition(0);
            area.setEditable(false);
            JOptionPane.showMessageDialog(frame, new JScrollPane(area), "Preview Referral Email (next in queue)", JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
