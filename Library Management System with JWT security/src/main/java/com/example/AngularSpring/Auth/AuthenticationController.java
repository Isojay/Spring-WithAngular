package com.example.AngularSpring.Auth;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/staff/register")
    public ResponseEntity<?> registerStaff(@RequestBody RegisterRequest request){

        return ResponseEntity.ok(service.registerStaff(request));
    }
/*
    @PostMapping("/student/register")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDetails request){
        try {
            StudentDetails registeredStudent = service.registerStudent(request);
            String successMessage = "Student registration successful!";
            return ResponseEntity.ok(successMessage);
        } catch (DataIntegrityViolationException e) {
            // Duplicate email constraint violation
            String errorMessage = "Student registration failed. Email already exists.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } catch (Exception e) {
            // Other exceptions
            String errorMessage = "Student registration failed due to an error.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

*/
    @PostMapping("/login")
    public ResponseEntity<?> doLogin(@RequestBody AuthenticationRequest request) {
        try {
            AuthResponse response = service.authenticate(request);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }



}
