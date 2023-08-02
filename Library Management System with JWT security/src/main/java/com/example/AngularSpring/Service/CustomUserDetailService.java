package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.StaffRepo;
import com.example.AngularSpring.Repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final StudentService studentService;

    private final StaffService staffService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        StudentDetails student = studentService.findByEmail(username);
        if (student != null) {
            return student;
        }

        // If the user is not found in StudentDetails, try to find the user in the Staff entity
        Staff staff = staffService.findByEmail(username);
        if (staff != null) {
            return staff;
        }

        // If the user is not found in either entity, throw an exception
        throw new UsernameNotFoundException("User not found with email: " + username);
    }
}
