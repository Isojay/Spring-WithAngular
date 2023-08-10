package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Queries;
import com.example.AngularSpring.Repo.QueryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Queries> findbyid(int id){ return queryRepo.findById(id);}

    public List<Queries> findByStatus(int id){ return queryRepo.findAllByStatus(id);}

}
