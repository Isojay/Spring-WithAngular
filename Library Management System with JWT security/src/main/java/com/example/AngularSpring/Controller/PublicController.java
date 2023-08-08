package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Auth.MsgResponse;
import com.example.AngularSpring.Entity.StudentDetails;
import com.example.AngularSpring.Service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PublicController {

    private final StudentService studentService;
    public static String Uploaddir =  System.getProperty("user.dir")+"/src/main/resources/static/Pictures";
    MsgResponse msgResponse = new MsgResponse();

    @GetMapping("/profileById/{id}")
    public ResponseEntity<?> profileByid(@PathVariable int id){
        Optional<StudentDetails> response = studentService.findById(id);
        if(response.isPresent()){
            return ResponseEntity.ok(response);
        }else{
            msgResponse.setMessage("Student ID not Found");
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(msgResponse);
        }
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



}
