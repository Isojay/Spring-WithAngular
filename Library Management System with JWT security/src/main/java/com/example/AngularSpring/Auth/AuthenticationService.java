package com.example.AngularSpring.Auth;

import com.example.AngularSpring.Config.GoogleAuth;
import com.example.AngularSpring.Config.JwtService;
import com.example.AngularSpring.Entity.Role;
import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.StaffRepo;
import com.example.AngularSpring.Service.StudentService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
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
    private final GoogleAuth googleAuth;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public void registerStaff(RegisterRequest request) {
        Staff staffUser = Staff.builder()
                .sname(request.getUname())
                .semail(request.getUemail())
                .spassword(passwordEncoder.encode(request.getUpassword()))
                .role(Role.ADMIN)
                .accountstatus(false)
                .build();
        staffRepo.save(staffUser);
    }

    public void registerStudent(StudentDetails request) {
        StudentDetails studentUser = StudentDetails.builder()
                .fName(request.getFName())
                .lName(request.getLName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .semester(request.getSemester())
                .contact(request.getContact())
                .address(request.getAddress())
                .idstatus(0)
                .role(Role.USER)
                .build();
        studentService.save(studentUser);
    }


    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request.getUemail(), request.getUpassword());
        Staff staffUser = staffRepo.findBySemail(request.getUemail());
        StudentDetails studentUser = studentService.findbyEmail(request.getUemail());

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
            String email = studentUser.getEmail();
            jwtToken = jwtService.generateToken(studentUser);
            role = studentUser.getRole();
            id = studentUser.getId();
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .id(id)
                    .email(email)
                    .build();
        }
    }

    public AuthResponse logInWithGoogle(String passToken){
        Payload payload = googleAuth.verifyAndExtractPayload(passToken);
        if (payload != null){
            System.out.println("2");
            if(studentService.findbyEmail(payload.getEmail()) == null){
                StudentDetails studentUser = StudentDetails.builder()
                        .fName((String) payload.get("given_name"))
                        .lName((String) payload.get("family_name"))
                        .email(payload.getEmail())
                        .contact((Long) payload.get("phone_number"))
                        .idstatus(0)
                        .role(Role.USER)
                        .build();
                studentService.save(studentUser);
            }
            StudentDetails studentUser = studentService.findbyEmail(payload.getEmail());
            String email = studentUser.getEmail();
            String jwtToken = jwtService.generateToken(studentUser);
            Role role = studentUser.getRole();
            int id = studentUser.getId();
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .id(id)
                    .email(email)
                    .build();
        }else{
            System.out.println("3");
            return null;
        }
    }


    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }
}
