package com.example.AngularSpring.Auth;

import com.example.AngularSpring.Config.JwtService;
import com.example.AngularSpring.Entity.Role;
import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.StaffRepo;
import com.example.AngularSpring.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StaffRepo staffRepo;
    private final StudentService studentService;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public Staff registerStaff(RegisterRequest request) {
        Staff staffUser = Staff.builder()
                .sname(request.getUname())
                .semail(request.getUemail())
                .spassword(passwordEncoder.encode(request.getUpassword()))
                .role(Role.ADMIN)
                .build();
        return staffRepo.save(staffUser);
    }

    public StudentDetails registerStudent(StudentDetails request) {
        StudentDetails studentUser = StudentDetails.builder()
                .fName(request.getFName())
                .lName(request.getLName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .semester(request.getSemester())
                .role(Role.USER)
                .build();
        return studentService.save(studentUser);
    }

    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request.getUemail(), request.getUpassword());
        Staff staffUser = staffRepo.findBySemail(request.getUemail());
        StudentDetails studentUser = studentService.findByEmail(request.getUemail());

        String jwtToken;
        Role role;
        Integer id = null; // Initialize with null

        if (staffUser != null) {
            jwtToken = jwtService.generateToken(staffUser);
            role = staffUser.getRole();
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .build();
        } else {
            jwtToken = jwtService.generateToken(studentUser);
            role = studentUser.getRole();
            id = studentUser.getId();
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .id(id)
                    .build();

        }


    }


    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}
