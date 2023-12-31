package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Model.MsgResponse;
import com.example.AngularSpring.Entity.Log_Table;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.LogService;
import com.example.AngularSpring.Service.StudentService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class PublicController {

    private final StudentService studentService;
    private final LogService logService;
    public static String Uploaddir =  "/home/blue/Pictures/LBMS";
//    public static String Uploaddir =  "opt/Librarymgmt";
    MsgResponse msgResponse = new MsgResponse();
    private final PasswordEncoder passwordEncoder;

//    to create the folder if not present

        public void createDirectoryIfNeeded() {
            String directoryPath = Uploaddir;
            Path path = Paths.get(directoryPath);

            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                    System.out.println("Directory created: " + directoryPath);
                } catch (IOException e) {
                    System.err.println("Error creating directory: " + e.getMessage());
                }
            }
        }
        @PostConstruct
        public void init(){
            createDirectoryIfNeeded();
        }

    @GetMapping("/profileById/{id}")
    public ResponseEntity<?> profileByid(@PathVariable int id){
        Optional<StudentDetails> response = studentService.findById(id);
        if(response.isPresent()){
            StudentDetails send = response.get();
            send.setPassword(null);
            return ResponseEntity.ok(send);
        }else{
            msgResponse.setMessage("Student ID not Found");
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(msgResponse);
        }
    }


    @PutMapping("/add")
    public StudentDetails updateStudent(@RequestBody StudentDetails studentDetail) {
        Optional<StudentDetails> response = studentService.findById(studentDetail.getId());
        if(response.isPresent()){
            StudentDetails send = response.get();
            studentDetail.setPassword(send.getPassword());
            studentDetail.setGoogleActivate(false);
            System.out.println(studentDetail.isGoogleActivate());
        }
        return studentService.save(studentDetail);
    }

    @PutMapping("/change")
    public ResponseEntity<?> changePass(@RequestBody StudentDetails studentDetail,
                                     @RequestParam("password") String npassword) {
        StudentDetails studentDetails1 = studentService.findbyEmail(studentDetail.getEmail());
        if(passwordEncoder.matches(studentDetail.getPassword(),studentDetails1.getPassword())){
            studentDetail.setPassword(passwordEncoder.encode(npassword));
            studentService.save(studentDetail);
            msgResponse.setMessage("Password Sucessfully Changed !!");
            return ResponseEntity.ok(msgResponse);
        }
        msgResponse.setMessage("Error Error!! Password Incorrect !!!");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(msgResponse);
    }

    @PutMapping("/setPass")
    public ResponseEntity<?> setPass(@RequestBody StudentDetails studentDetail,
                                        @RequestParam("password") String npassword) {
        StudentDetails studentDetails = studentService.findbyEmail(studentDetail.getEmail());
        if(passwordEncoder.matches("12345",studentDetails.getPassword())){
            studentDetail.setPassword(passwordEncoder.encode(npassword));
            studentService.save(studentDetail);
            msgResponse.setMessage("Password Sucessfully Set !!");
            return ResponseEntity.ok(msgResponse);
        }
        msgResponse.setMessage("Error Error!! Please Try Again or DO it in the  Profile section !!!");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(msgResponse);
    }

    @PostMapping("/upByImg")
    public ResponseEntity<?> upByimg(@RequestParam("file") MultipartFile file,
                                     @RequestParam("id") int id) throws IOException {

        StudentDetails studentDetails = studentService.findById(id).get();

        if (file.isEmpty()) {
            msgResponse.setMessage("File is Empty");
            return ResponseEntity.status(400).body(msgResponse);
        }

        String originalName = file.getOriginalFilename();
        assert originalName != null;
        String fileExtension = originalName.substring(originalName.lastIndexOf('.'));
        String newName = id + "_" + studentDetails.getFName() + fileExtension;
        System.out.println(newName);
        studentDetails.setImgName(newName);
        studentService.save(studentDetails);
        try {
            File destFile = new File(Uploaddir + File.separator + newName);
            file.transferTo(destFile);

            msgResponse.setMessage("Image uploaded successfully");
            return ResponseEntity.ok(msgResponse);
        } catch (IOException e) {
            msgResponse.setMessage("Error uploading image: " + e.getMessage());
            return ResponseEntity.status(500).body(msgResponse);
        }
    }


    @GetMapping("/activityById/{id}")
    private List<Log_Table> activityById(@PathVariable int id){
        return logService.findbyid(id);
    }

    @GetMapping("/deleteActivityById/{id}")
    private void deleteById(@PathVariable  int id){
        logService.deleteById(id);
    }


}
