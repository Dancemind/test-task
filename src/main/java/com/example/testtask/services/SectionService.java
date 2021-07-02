package com.example.testtask.services;

import com.example.testtask.dto.SectionDto;
import com.example.testtask.entity.Section;

import com.example.testtask.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
   }

    public List<SectionDto> getAllSectionsByGeologicalClassWithCode(String code) {
        List<Section> sections = sectionRepository.findAllByGeologicalClassesThatContainCode(code);
        List<SectionDto> sectionDtoList = new ArrayList<>();
        for (Section section : sections) {
            SectionDto sectionDto = new SectionDto();
            sectionDto.setId(section.getId());
            sectionDto.setName(section.getName());
            sectionDtoList.add(sectionDto);
        }
        return sectionDtoList;
    }

    public Section addSection(Section section) {
        return sectionRepository.save(section);
    }

    public List<Section> getAllSections() {
         return sectionRepository.findAll();
    }

}
