package service;

import data.CsvRepository;
import data.DataStore;
import model.Appointment;
import util.IdGenerator;

import java.io.IOException;
import java.time.LocalDate;

public class AppointmentService {
    private final DataStore ds;
    private final CsvRepository repo;

    public AppointmentService(DataStore ds, CsvRepository repo) {
        this.ds = ds;
        this.repo = repo;
    }

    public boolean isSlotAvailable(String clinicianId, String date, String time) {
        for (Appointment a : ds.appointments) {
            if (a.getClinicianId().equals(clinicianId) && a.getDate().equals(date) && a.getTime().equals(time)
                    && !"Cancelled".equalsIgnoreCase(a.getStatus())) {
                return false;
            }
        }
        return true;
    }

    public Appointment bookAppointment(String patientId, String clinicianId, String facilityId,
                                       String date, String time, String duration, String type, String reason, String notes) throws IOException {
        if (!isSlotAvailable(clinicianId, date, time)) {
            throw new IOException("Selected clinician is not available at that date/time.");
        }
        String id = IdGenerator.nextWithPrefix("A", ds.appointments.size(), 3);
        Appointment a = new Appointment(id, patientId, clinicianId);
        a.setFacilityId(facilityId);
        a.setDate(date);
        a.setTime(time);
        a.setDurationMinutes(duration);
        a.setAppointmentType(type);
        a.setStatus("Scheduled");
        a.setReasonForVisit(reason);
        a.setNotes(notes);
        a.setCreatedDate(LocalDate.now().toString());
        a.setLastModified(LocalDate.now().toString());

        ds.appointments.add(a);
        repo.saveAppointments(ds);
        return a;
    }

    public void updateAppointments() throws IOException {
        repo.saveAppointments(ds);
    }

    public void cancelAppointment(Appointment a) throws IOException {
        a.setStatus("Cancelled");
        a.setLastModified(LocalDate.now().toString());
        repo.saveAppointments(ds);
    }

    public void deleteAppointment(Appointment a) throws IOException {
        ds.appointments.remove(a);
        repo.saveAppointments(ds);
    }
}
