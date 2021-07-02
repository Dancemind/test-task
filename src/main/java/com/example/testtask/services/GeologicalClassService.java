package com.example.testtask.services;

import com.example.testtask.entity.GeologicalClass;
import com.example.testtask.repository.GeologicalClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeologicalClassService {

    private final GeologicalClassRepository geologicalClassRepository;

    @Autowired
    public GeologicalClassService(GeologicalClassRepository geologicalClassRepository) {
        this.geologicalClassRepository = geologicalClassRepository;
    }

    public GeologicalClass addGeologicalClass(GeologicalClass geologicalClass) {
        return geologicalClassRepository.save(geologicalClass);
    }

}
