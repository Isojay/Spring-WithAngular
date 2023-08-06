package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Repo.StaffRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepo staffRepo;

    public Staff findByEmail(String email){
        return staffRepo.findBySemail(email);
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
