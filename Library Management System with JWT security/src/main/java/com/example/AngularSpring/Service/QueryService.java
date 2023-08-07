package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Queries;
import com.example.AngularSpring.Repo.QueryRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepo queryRepo;

    public Queries saveQuery(Queries queries){
        return queryRepo.save(queries);
    }

    public void deleteQuery(int id){
        queryRepo.deleteById(id);
    }

    public List<Queries> findAll(){
        return queryRepo.findAll();
    }
}
