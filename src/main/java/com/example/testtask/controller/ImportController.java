package com.example.testtask.controller;

import com.example.testtask.util.JobNumberGenerator;
import com.example.testtask.entity.enums.EResult;
import com.example.testtask.exceptions.EmptyFileException;
import com.example.testtask.services.FileImportService;
import com.example.testtask.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
public class ImportController {

    private final FileImportService fileImportService;
    private final JobService jobService;
    private final JobNumberGenerator jobNumberGenerator;

    @Autowired
    public ImportController(FileImportService fileImportService,
                            JobService jobService,
                            JobNumberGenerator jobNumberGenerator) {
        this.fileImportService = fileImportService;
        this.jobService = jobService;
        this.jobNumberGenerator = jobNumberGenerator;
    }

    @PostMapping("/import")
    public Long uploadFile(@RequestParam("file") MultipartFile uploadedFile) {

        if (uploadedFile.isEmpty()) {
            throw new EmptyFileException("Uploaded file is empty.");
        }

        InputStream inputStream = null;
        try {
            inputStream = uploadedFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long jobNumber = jobNumberGenerator.generate();
        fileImportService.process(inputStream, jobNumber);
        return jobNumber;

    }

    @GetMapping("/import/{id}")
    public EResult getStatus(@PathVariable(name="id") Long jobNumber) {
        return jobService.getJobStatus(jobNumber);
    }

}
