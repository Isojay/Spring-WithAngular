package com.example.AngularSpring.Auth;


import com.example.AngularSpring.Entity.StudentDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    MsgResponse response = new MsgResponse();



    @PostMapping("/staff/register")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterRequest request){

        try {
            service.registerStaff(request);
            String successMessage = "Registration successful!, Wait for Admin to Approve!!";
            response.setMessage(successMessage);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Registration failed. Email already exists.";
            response.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            String errorMessage = "Registration failed due to an error. Try again or Contact ADMIN ";
            response.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/student/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDetails request){
        MsgResponse response = new MsgResponse();
        try {
            service.registerStudent(request);
            String successMessage = "Student registration successful!";
            response.setMessage(successMessage);
            return ResponseEntity.ok(response);
        } catch (DataIntegrityViolationException e) {
            String errorMessage = "Student registration failed. Email already exists.";
            response.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            String errorMessage = "Student registration failed due to an error.";
            response.setMessage(errorMessage);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody AuthenticationRequest request) {
        try {
            AuthResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            response.setMessage("Invalid email or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }



}
