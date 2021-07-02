package com.example.testtask.parser;

import com.example.testtask.entity.GeologicalClass;
import com.example.testtask.entity.Section;
import com.example.testtask.services.SectionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
public class DatabaseToExcelFile {

    SectionService sectionService;

    public DatabaseToExcelFile(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    public void create(Long jobNumber) throws IOException {

        List<Section> sections = sectionService.getAllSections();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowCount = 0;

        int totalColumns;
        int columnCount = 0;
        Cell cell;
        Row row;

        if (sections.size() > 0) {
            totalColumns = sections.get(0).getGeologicalClasses().size() * 2 + 1;
            row = sheet.createRow(++rowCount);

            cell = row.createCell(++columnCount);
            cell.setCellValue("Section name");

            for (int i = 0; i < totalColumns - 1; i++) {
                cell = row.createCell(++columnCount);
                if (i % 2 == 0) {
                    cell.setCellValue(new StringBuilder("Class ")
                            .append(i / 2 + 1)
                            .append(" name")
                            .toString());
                } else {
                    cell.setCellValue(new StringBuilder("Class ")
                            .append(i / 2 + 1)
                            .append(" code")
                            .toString());
                }
            }
        }

        for (Section section : sections) {
            row = sheet.createRow(++rowCount);

            columnCount = 0;

            cell = row.createCell(++columnCount);
            cell.setCellValue(section.getName());

            for (GeologicalClass geologicalClass : section.getGeologicalClasses()) {

                cell = row.createCell(++columnCount);
                cell.setCellValue(geologicalClass.getName());

                cell = row.createCell(++columnCount);
                cell.setCellValue(geologicalClass.getCode());
            }

            try (FileOutputStream outputStream = new FileOutputStream(
                    "files/Sections" + jobNumber + ".xlsx")) {
                workbook.write(outputStream);
            }

        }
    }
}
