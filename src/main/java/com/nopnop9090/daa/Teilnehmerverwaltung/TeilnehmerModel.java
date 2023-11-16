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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@SuppressWarnings("serial")
public class TeilnehmerModel extends AbstractListModel<Teilnehmer> {
	private List<Teilnehmer> teilnehmerList;
	
	public TeilnehmerModel() {
        teilnehmerList = new ArrayList<>();
    }

	public TeilnehmerModel(String csv_filename) {
        this(); // call default constructor
		
    	if(new File(csv_filename).exists()) {
        	readFromCSV(csv_filename);
    	} else {
    		setSampleContents();
    	}
	}
	
	public void setSampleContents() {
		teilnehmerList.clear();
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

	public List<Teilnehmer> getTeilnehmerList() {
        return teilnehmerList;
    }

    public void add(Teilnehmer teilnehmer) {
        teilnehmerList.add(teilnehmer);
    }
    
    @Override
    public int getSize() {
        return teilnehmerList.size();
    }

    @Override
    public Teilnehmer getElementAt(int index) {
        return teilnehmerList.get(index);
    }
    
    public void updateList() {
        fireContentsChanged(this, 0, getSize() - 1);
    }
    
    public void remove(Teilnehmer tn) {
    	teilnehmerList.remove(tn);
    }
    
	public void sortById() {
    	Collections.sort(teilnehmerList, new Comparator<Teilnehmer>() {
			@Override
			public int compare(Teilnehmer t1, Teilnehmer t2) {
				return Integer.compare(t1.getId(), t2.getId());
			}
		});
		updateList();
	}
	
	public void clear() {
		teilnehmerList.clear();
		updateList();
	}
    public void readFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            teilnehmerList.clear(); // Vorhandene Liste löschen

            // Skip the header line (assuming it's the first line)
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
            	System.out.println("readFromCSV[" + line + "]");
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
		updateList();
    }

    public void writeToCSV(String filename) {
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
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String escapeCSV(Object value) {
        String stringValue = value.toString();
        if (stringValue.contains(";") || stringValue.contains("\"")) {
            // Escape by enclosing in double quotes and double any existing double quotes
            return "\"" + stringValue.replace("\"", "\"\"") + "\"";
        } else {
            return stringValue;
        }
    }

    private String[] unescapeCSV(String[] parts) {
        for (int i = 0; i < parts.length; i++) {
            // Remove enclosing double quotes and unescape any existing double quotes
            parts[i] = parts[i].replaceAll("^\"|\"$", "").replace("\"\"", "\"");
        }
        return parts;
    }
    
    public void  readFromExcel(String Filename) {
        teilnehmerList.clear();

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
		updateList();
    }

    public void writeToExcel(String Filename) {
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

}
