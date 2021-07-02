package com.example.testtask.repository;

import com.example.testtask.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    Job findByJobNumber(Long jobNumber);
}
