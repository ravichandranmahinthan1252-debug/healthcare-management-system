package service;

import data.CsvRepository;
import data.DataStore;
import model.Facility;
import util.IdGenerator;

import java.io.IOException;

public class FacilityService {
    private final DataStore ds;
    private final CsvRepository repo;

    public FacilityService(DataStore ds, CsvRepository repo) {
        this.ds = ds;
        this.repo = repo;
    }

    public Facility createFacility(String name, String type, String address, String postcode, 
                                   String phone, String email, String openingHours, 
                                   String managerName, String capacity, String specialitiesOffered) throws IOException {
        String prefix = type.contains("Hospital") ? "H" : "S";
        String id = IdGenerator.nextWithPrefix(prefix, ds.facilities.size(), 3);
        Facility f = new Facility(id, name);
        f.setType(type);
        f.setAddress(address);
        f.setPostcode(postcode);
        f.setPhone(phone);
        f.setEmail(email);
        f.setOpeningHours(openingHours);
        f.setManagerName(managerName);
        f.setCapacity(capacity);
        f.setSpecialitiesOffered(specialitiesOffered);
        ds.facilities.add(f);
        repo.saveFacilities(ds);
        return f;
    }

    public void updateFacilities() throws IOException {
        repo.saveFacilities(ds);
    }

    public void deleteFacility(Facility f) throws IOException {
        ds.facilities.remove(f);
        repo.saveFacilities(ds);
    }
}