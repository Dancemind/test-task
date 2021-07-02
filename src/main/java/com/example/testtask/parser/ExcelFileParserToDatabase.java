package com.example.testtask.parser;

import com.example.testtask.entity.GeologicalClass;
import com.example.testtask.entity.Section;
import com.example.testtask.services.GeologicalClassService;
import com.example.testtask.services.SectionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Component
public class ExcelFileParserToDatabase {


    SectionService sectionService;
    GeologicalClassService geologicalClassService;

    @Autowired
    public ExcelFileParserToDatabase(SectionService sectionService,
                           GeologicalClassService geologicalClassService) {
        this.sectionService = sectionService;
        this.geologicalClassService = geologicalClassService;
    }


    public void parse(InputStream inputStream) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        workbook.sheetIterator();
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();

        Section section = new Section();
        GeologicalClass geologicalClass = new GeologicalClass();


        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            Iterator<Row> it = sheet.iterator();

            int rowNumber = 0;

            while (it.hasNext()) {
                Row row = it.next();

                if (rowNumber == 0) {
                    rowNumber = 1;
                    continue;
                }

                Iterator<Cell> cells = row.iterator();

                int column = 0;
                while (cells.hasNext()) {
                    Cell cell = cells.next();

                    switch (cell.getCellType()) {
                        case STRING:
                            if (column == 0) {
                                section.setName(cell.getStringCellValue());
                                section = sectionService.addSection(section);
                                column++;
                                continue;
                            }
                            if (column % 2 == 1) {
                                geologicalClass.setName(cell.getStringCellValue());
                                geologicalClass.setSection(section);
                                column++;
                                continue;
                            }
                            if (column % 2 == 0) {
                                geologicalClass.setCode(cell.getStringCellValue());
                                geologicalClassService.addGeologicalClass(geologicalClass);
                                geologicalClass = new GeologicalClass();
                                column++;
                                continue;
                            }
                            break;
                        case NUMERIC:
                            if (column == 0) {
                                section.setName(Double.toString(cell.getNumericCellValue()));
                                section = sectionService.addSection(section);
                                column++;
                                continue;
                            }
                            if (column % 2 == 1) {
                                geologicalClass.setName(Double.toString(cell.getNumericCellValue()));
                                geologicalClass.setSection(section);
                                column++;
                                continue;
                            }
                            if (column % 2 == 0) {
                                geologicalClass.setCode(Double.toString(cell.getNumericCellValue()));
                                geologicalClassService.addGeologicalClass(geologicalClass);
                                geologicalClass = new GeologicalClass();
                                column++;
                                continue;
                            }
                            break;
                    }

                }
                section = new Section();
            }
        }

    }
}
