package com.example.testtask.controller;

import com.example.testtask.util.JobNumberGenerator;
import com.example.testtask.entity.enums.EResult;
import com.example.testtask.services.FileExportService;
import com.example.testtask.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class ExportController {

    private final FileExportService fileExportService;
    private final JobService jobService;
    private final JobNumberGenerator jobNumberGenerator;

    @Autowired
    public ExportController(FileExportService fileExportService,
                            JobService jobService,
                            JobNumberGenerator jobNumberGenerator) {
        this.fileExportService = fileExportService;
        this.jobService = jobService;
        this.jobNumberGenerator = jobNumberGenerator;
    }

    @Autowired

    @GetMapping("/export")
    public Long generateExcelFile() {
        Long jobNumber = jobNumberGenerator.generate();
        fileExportService.process(jobNumber);
        return jobNumber;
    }

    @GetMapping("/export/{id}")
    public EResult getExportResult(@PathVariable(name = "id") Long jobNumber) {
        return jobService.getJobStatus(jobNumber);
    }

    @GetMapping(
            value = "/export/{id}/file",
            produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    )
    public @ResponseBody byte[] getExcelFile(@PathVariable(name = "id") Long jobNumber) {
        return fileExportService.giveFile(jobNumber);
    }

}

