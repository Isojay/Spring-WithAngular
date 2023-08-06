package com.example.AngularSpring.Service;

import com.example.AngularSpring.Entity.Queries;
import com.example.AngularSpring.Repo.QueryRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueryService {

    private final QueryRepo queryRepo;

    public Queries saveQuery(Queries queries){
        return null;
    }

}
