package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Repo.StaffRepo;
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

}
