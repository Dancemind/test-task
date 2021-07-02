package com.example.testtask.services;

import com.example.testtask.entity.enums.EResult;
import com.example.testtask.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    private final JobRepository jobRepository;

    @Autowired
    public JobService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public EResult getJobStatus(Long jobNumber) {
        return jobRepository.findByJobNumber(jobNumber).getStatus();
    }

}
