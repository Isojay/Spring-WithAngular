package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Auth.MsgResponse;
import com.example.AngularSpring.Entity.Queries;
import com.example.AngularSpring.Service.QueryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class QueryController {

    private  final QueryService queryService;
    MsgResponse msgResponse =new MsgResponse();

    @GetMapping("/getQueries")
    public List<Queries> getQueries(){
        return queryService.findAll();
    }

    @GetMapping("/getQuerybyId/{id}")
    public ResponseEntity<?> getQueryById(@PathVariable int id){
        try {
            Queries queries = queryService.findbyid(id).get();
            queries.setStatus(1);
            queryService.saveQuery(queries);
            return ResponseEntity.ok(queries);
        }catch (Exception e){
            msgResponse.setMessage("Internal Server Error !!!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgResponse);
        }
    }

    @GetMapping("/byStatus/{id}")
    public List<Queries> findByStatus(@PathVariable int id){
        if(id == 1) {
            List<Queries> status1 = queryService.findByStatus(id);
            List<Queries> status0 = queryService.findByStatus(0);
            status1.addAll(status0);
            return status1;
        }
        return queryService.findByStatus(id);
    }

    @PostMapping("/add")
    public void addQuery(@RequestBody Queries queries){
        queries.setStatus(0);
        queries.setDate(LocalDate.now());
        queryService.saveQuery(queries);
    }

    @PutMapping("/add")
    public void editQuery(@RequestBody Queries queries){
        queries.setStatus(2);
        queryService.saveQuery(queries);
    }

    @DeleteMapping("/deletebyId/{id}")
    public void deleteQuery(@PathVariable int id){
        queryService.deleteQuery(id);
    }





}
