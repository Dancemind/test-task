package com.example.testtask.services;

import com.example.testtask.util.JobNumberGenerator;
import com.example.testtask.entity.Job;
import com.example.testtask.entity.enums.EResult;
import com.example.testtask.exceptions.FileNotFoundException;
import com.example.testtask.parser.DatabaseToExcelFile;
import com.example.testtask.repository.JobRepository;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileExportService {

    JobNumberGenerator jobNumberGenerator;
    JobRepository jobRepository;
    DatabaseToExcelFile databaseToExcelFile;

    @Autowired
    public FileExportService(JobNumberGenerator jobNumberGenerator,
                             JobRepository jobRepository,
                             DatabaseToExcelFile databaseToExcelFile) {
        this.jobNumberGenerator = jobNumberGenerator;
        this.jobRepository = jobRepository;
        this.databaseToExcelFile = databaseToExcelFile;
    }

    @Async
    public void process(Long jobNumber) {

        Job job = new Job();
        job.setJobNumber(jobNumber);
        job.setStatus(EResult.IN_PROGRESS);
        jobRepository.saveAndFlush(job);

        boolean gotError = false;
        try {
            databaseToExcelFile.create(jobNumber);
        } catch (IOException e) {
            //e.printStackTrace();
            gotError = true;
        }

        if (gotError) {
            job.setStatus(EResult.ERROR);
        } else {
            job.setStatus(EResult.DONE);
        }

        jobRepository.saveAndFlush(job);

    }

    public byte[] giveFile(Long jobNumber) {

        byte[] file;

        try {
            InputStream inputStream = new FileInputStream("files/Sections" + jobNumber + ".xlsx");
            file = IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            // e.printStackTrace();
            throw new FileNotFoundException("File not found.");
        }

        return file;
    }

}
