package com.example.AngularSpring.Controller;

import com.example.AngularSpring.Entity.Queries;
import com.example.AngularSpring.Service.QueryService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/query")
public class QueryController {

    private  final QueryService queryService;

    @GetMapping("/getQueries")
    public List<Queries> getQueries(){

        System.out.println("hello from QUeries");
        return queryService.findAll();
    }

    @PostMapping("/add")
    public void addQuery(@RequestBody Queries queries){
        queries.setStatus(0);
        queries.setDate(LocalDate.now());
        queryService.saveQuery(queries);
    }

    @PutMapping("/add")
    public void editQuery(@RequestBody Queries queries){
        queries.setStatus(1);
        queryService.saveQuery(queries);
    }

    @DeleteMapping("/deletebyId/{id}")
    public void deleteQuery(@PathVariable int id){
        queryService.deleteQuery(id);
    }





}
