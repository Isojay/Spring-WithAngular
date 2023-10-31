package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Repo.StaffRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepo staffRepo;
    private final PasswordEncoder passwordEncoder;
    public Staff findByEmail(String email){
        return staffRepo.findBySemail(email);
    }

    public Staff save(Staff staff){
        return staffRepo.save(staff);
    }

    @PostConstruct
    public void insertDefaultAdmin() {
        String defaultEmail = "admin@gmail.com";
        if (staffRepo.findBySemail(defaultEmail) != null) {
            Staff adminUser = staffRepo.findBySemail(defaultEmail);
            adminUser.setAccountstatus(true);
            adminUser.setSpassword(passwordEncoder.encode("test1234"));
            staffRepo.save(adminUser);
        }
    }

}
