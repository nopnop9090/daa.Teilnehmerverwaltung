package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App 
{
    private static final String EXCEL_FILE_PATH = "c:/users/pc/downloads/test.xlsx";

    public static List<Teilnehmer> readTeilnehmerFromExcel() {
        List<Teilnehmer> teilnehmerList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(EXCEL_FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            Iterator<Row> iterator = sheet.iterator();

            // Skip the header row (assuming it's in row 0)
            if (iterator.hasNext()) {
                iterator.next();
            }

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                int id = (int) currentRow.getCell(0).getNumericCellValue();
                String gruppe = currentRow.getCell(1).getStringCellValue();
                String name = currentRow.getCell(2).getStringCellValue();
                String vorname = currentRow.getCell(3).getStringCellValue();

                Teilnehmer teilnehmer = new Teilnehmer(id, gruppe, name, vorname);
                teilnehmerList.add(teilnehmer);
            }

            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teilnehmerList;
    }

    public static void writeTeilnehmerToExcel(List<Teilnehmer> teilnehmerList) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(EXCEL_FILE_PATH)) {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Teilnehmer"); 

            // Write header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Gruppe");
            headerRow.createCell(2).setCellValue("Name");
            headerRow.createCell(3).setCellValue("Vorname");

            // Write Teilnehmer objects
            int rowNum = 1;
            for (Teilnehmer teilnehmer : teilnehmerList) {
                Row currentRow = sheet.createRow(rowNum++);
                currentRow.createCell(0).setCellValue(teilnehmer.getId());
                currentRow.createCell(1).setCellValue(teilnehmer.getGruppe());
                currentRow.createCell(2).setCellValue(teilnehmer.getName());
                currentRow.createCell(3).setCellValue(teilnehmer.getVorname());
            }

            workbook.write(fileOutputStream);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main( String[] args )
    {
    	List<Teilnehmer> teilnehmerList = readTeilnehmerFromExcel();
        for (Teilnehmer teilnehmer : teilnehmerList) {
            System.out.println(teilnehmer.getId() + ", " + teilnehmer.getGruppe() +
                    ", " + teilnehmer.getName() + ", " + teilnehmer.getVorname());
        }

        // Modify the teilnehmerList as needed...

        writeTeilnehmerToExcel(teilnehmerList);    }
}
