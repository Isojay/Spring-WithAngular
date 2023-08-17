package com.example.AngularSpring.Auth;

import com.example.AngularSpring.Config.GoogleAuth;
import com.example.AngularSpring.Config.JwtService;
import com.example.AngularSpring.Entity.Log_Table;
import com.example.AngularSpring.Entity.Role;
import com.example.AngularSpring.Entity.Staff;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Repo.LogRepo;
import com.example.AngularSpring.Repo.StaffRepo;
import com.example.AngularSpring.Service.StudentService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua_parser.Client;
import ua_parser.Parser;
import ua_parser.UserAgentParser;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final StaffRepo staffRepo;
    private final StudentService studentService;
    private final JwtService jwtService;
    private final GoogleAuth googleAuth;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final LogRepo logRepo;


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
                .googleActivate(false)
                .build();
        studentService.save(studentUser);
    }


    public AuthResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request.getUemail(), request.getUpassword());
        Staff staffUser = staffRepo.findBySemail(request.getUemail());
        StudentDetails studentUser = studentService.findbyEmail(request.getUemail());

        String jwtToken;
        Role role;
        int id ; // Initialize with null

        if (staffUser != null) {
            jwtToken = jwtService.generateToken(staffUser);
            role = staffUser.getRole();
            id = staffUser.getId();
            logRecords(id,"Staff");
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .build();
        } else {
            String email = studentUser.getEmail();
            jwtToken = jwtService.generateToken(studentUser);
            role = studentUser.getRole();
            id = studentUser.getId();
            boolean activate = studentUser.isGoogleActivate();
            logRecords(id,"Student");
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .id(id)
                    .email(email)
                    .activate(activate)
                    .build();
        }
    }

    public AuthResponse logInWithGoogle(String passToken){
        Payload payload = googleAuth.verifyAndExtractPayload(passToken);
        if (payload != null){
            System.out.println("20");
            StudentDetails studentUser1 = studentService.findbyEmail(payload.getEmail());
            if(studentUser1 == null){
                System.out.println("why?");
                StudentDetails saveStudentUser = StudentDetails.builder()
                        .fName((String) payload.get("given_name"))
                        .lName((String) payload.get("family_name"))
                        .email(payload.getEmail())
                        .idstatus(0)
                        .password(passwordEncoder.encode("12345"))
                        .role(Role.USER)
                        .googleActivate(true)
                        .build();
                studentService.save(saveStudentUser);
            }else {
                studentUser1.setGoogleActivate(false);
                studentService.save(studentUser1);
            }
            StudentDetails studentUser = studentService.findbyEmail(payload.getEmail());
            String email = studentUser.getEmail();
            String jwtToken = jwtService.generateToken(studentUser);
            Role role = studentUser.getRole();
            int id = studentUser.getId();
            logRecords(id,"Student");
            boolean activate = studentUser.isGoogleActivate();
            return AuthResponse.builder()
                    .token(jwtToken)
                    .role(role.name())
                    .id(id)
                    .email(email)
                    .activate(activate)
                    .build();
        }else{
            return null;
        }
    }

    private void logRecords(int id,String from){

        Log_Table record = new Log_Table();
        if (Objects.equals(from, "Student")){
            StudentDetails studentUser = studentService.findById(id).get();
            record.setStudentDetails(studentUser);
        }else {
            Staff staff = staffRepo.findById(id).get();
            record.setStaff(staff);
        }
        String Os = System.getProperty("os.name");
        record.setOS(Os);
        record.setLocalDateTime(LocalDateTime.now());

        String userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3";
        Parser uaParser = new Parser();
        Client client = uaParser.parse(userAgentString);
        System.out.println("Browser Family: " + client.userAgent.family);
        record.setBrowser(client.userAgent.family);
        logRepo.save(record);
    }

    private void authenticateUser(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
    }


}
