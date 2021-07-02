package com.example.testtask.services;

import com.example.testtask.util.JobNumberGenerator;
import com.example.testtask.entity.Job;
import com.example.testtask.entity.enums.EResult;
import com.example.testtask.parser.ExcelFileParserToDatabase;
import com.example.testtask.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;


@Service
public class FileImportService {

    private final JobRepository jobRepository;
    private final ExcelFileParserToDatabase excelFileParserToDatabase;

    @Autowired
    public FileImportService(ExcelFileParserToDatabase excelFileParserToDatabase,
                             JobRepository jobRepository) {
        this.excelFileParserToDatabase = excelFileParserToDatabase;
        this.jobRepository = jobRepository;
    }

    @Async
    public void process(InputStream inputStream, Long jobNumber) {

        Job job = new Job();
        job.setJobNumber(jobNumber);
        job.setStatus(EResult.IN_PROGRESS);
        jobRepository.saveAndFlush(job);


        boolean gotError = false;
        try {
            excelFileParserToDatabase.parse(inputStream);
        } catch (IOException e) {
            // e.printStackTrace();
            gotError = true;
        }

        if (gotError) {
            job.setStatus(EResult.ERROR);
        } else {
            job.setStatus(EResult.DONE);
        }

        jobRepository.saveAndFlush(job);

    }
}
