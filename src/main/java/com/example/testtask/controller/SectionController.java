package com.example.testtask.controller;


import com.example.testtask.dto.SectionDto;
import com.example.testtask.services.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SectionController {

    SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping("/sections/by-code")
    public List<SectionDto> sectionsThatHaveGeologicalClassesWithCode(@RequestParam(name="code") String code) {
        return sectionService.getAllSectionsByGeologicalClassWithCode(code);
    }
}
