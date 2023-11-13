package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class App 
{
    public static List<Teilnehmer> readTeilnehmerFromExcel(String Filename) {
        List<Teilnehmer> teilnehmerList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(Filename)) {
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

    public static void writeTeilnehmerToExcel(String Filename, List<Teilnehmer> teilnehmerList) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(Filename)) {

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
    
    public static List<Teilnehmer> readTeilnehmerFromCSV(String filename) {
        List<Teilnehmer> teilnehmerList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;

            // Skip the header line (assuming it's the first line)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = unescapeCSV(line.split(";"));
                int id = Integer.parseInt(parts[0].trim());
                String gruppe = parts[1].trim();
                String name = parts[2].trim();
                String vorname = parts[3].trim();

                Teilnehmer teilnehmer = new Teilnehmer(id, gruppe, name, vorname);
                teilnehmerList.add(teilnehmer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teilnehmerList;
    }

    public static void writeTeilnehmerToCSV(String filename, List<Teilnehmer> teilnehmerList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            // Write header row
            writer.write("ID;Gruppe;Name;Vorname");
            writer.newLine();

            // Write Teilnehmer objects
            for (Teilnehmer teilnehmer : teilnehmerList) {
                writer.write(escapeCSV(teilnehmer.getId()) + ";" +
                             escapeCSV(teilnehmer.getGruppe()) + ";" +
                             escapeCSV(teilnehmer.getName()) + ";" +
                             escapeCSV(teilnehmer.getVorname()));
                //System.out.println(teilnehmer.getId() + " written");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeCSV(Object value) {
        String stringValue = value.toString();
        if (stringValue.contains(";") || stringValue.contains("\"")) {
            // Escape by enclosing in double quotes and double any existing double quotes
            return "\"" + stringValue.replace("\"", "\"\"") + "\"";
        } else {
            return stringValue;
        }
    }

    private static String[] unescapeCSV(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            // Remove enclosing double quotes and unescape any existing double quotes
            parts[i] = parts[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
        }
        return parts;
    }
    
    private static void waitForClose(JFrame frame) {
        while (frame.isVisible()) {
            try {
                Thread.sleep(100); // Sleep for a short interval
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main( String[] args )
    {
    	List<Teilnehmer> teilnehmerList = null;
    	
    	
//    	if(new File("teilnehmerliste.xlsx").exists()) {
//        	teilnehmerList = readTeilnehmerFromExcel("teilnehmerliste.xlsx");
//    	} 
    	
    	if(new File("teilnehmerliste.csv").exists()) {
    		teilnehmerList = readTeilnehmerFromCSV("teilnehmerliste.csv");
    	}
    	
    	if(teilnehmerList == null) {
    		System.out.println("keine teilnehmerliste geladen - erstelle beispiele..");
        	teilnehmerList = new ArrayList<Teilnehmer>();
        	teilnehmerList.add(new Teilnehmer(1,"Comic","Maus","Minni"));
        	teilnehmerList.add(new Teilnehmer(2,"Comic","Duck","Donald"));
        	teilnehmerList.add(new Teilnehmer(3,"Zauberer","Gans","Gustav"));
        	teilnehmerList.add(new Teilnehmer(4,"Zauberer","Dumbledore","Albus"));
        	teilnehmerList.add(new Teilnehmer(5,"Zauberer","Potter","Harry"));
        	teilnehmerList.add(new Teilnehmer(6,"Comic","Maus","Micky"));
        	teilnehmerList.add(new Teilnehmer(7,"Personen","Niko","Klaus"));
        	teilnehmerList.add(new Teilnehmer(8,"Personen","Christ","Kind"));
        	teilnehmerList.add(new Teilnehmer(9,"Moderator","Meiser","Hans"));
    	}
    	
    	for (Teilnehmer teilnehmer : teilnehmerList) {
            System.out.println(teilnehmer.getId() + ", " + teilnehmer.getGruppe() +
                    ", " + teilnehmer.getName() + ", " + teilnehmer.getVorname());
        }

		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		waitForClose(new ListUI(teilnehmerList));
		
    	
        //writeTeilnehmerToExcel("teilnehmerliste.xlsx", teilnehmerList);   
        writeTeilnehmerToCSV("teilnehmerliste.csv", teilnehmerList);
        
        System.out.println("Goodbye");
        System.exit(0);
        
    }
}
