package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Repo.StaffRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepo staffRepo;

    public Staff findByEmail(String email){
        return staffRepo.findBySemail(email);
    }

    public Staff save(Staff staff){
        return staffRepo.save(staff);
    }

//    @PostConstruct
//    public void insertDefaultAdmin() {
//        String defaultEmail = "admin@gmail.com";
//        if (staffRepo.findBySemail(defaultEmail) != null) {
//            Staff adminUser = staffRepo.findBySemail(defaultEmail);
//            adminUser.setAccountstatus(true);
//            staffRepo.save(adminUser);
//        }
//    }

}
