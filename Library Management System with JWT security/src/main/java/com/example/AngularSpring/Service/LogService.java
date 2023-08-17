package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Log_Table;
import com.example.AngularSpring.Repo.LogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LogService {

    private  final LogRepo logRepo;

    public List<Log_Table> findAll(){
        return logRepo.findAll();
    }

    public List<Log_Table> findbyid(int id){
        return logRepo.findAllByStudentDetails_id(id);
    }

    public void deleteById(int id){
        logRepo.deleteById(id);
    }

}
