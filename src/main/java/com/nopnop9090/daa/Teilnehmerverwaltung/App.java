package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class App 
{
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
    	TeilnehmerModel teilnehmerModel = new TeilnehmerModel();

    	if(new File("teilnehmerliste.csv").exists()) {
        	teilnehmerModel.readFromCSV("teilnehmerliste.csv");
    	} else {
    		List<Teilnehmer> teilnehmerList = teilnehmerModel.getTeilnehmerList();
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
    	
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		waitForClose(new ListUI(teilnehmerModel));
		
    	teilnehmerModel.writeToCSV("teilnehmerliste.csv");
        
        System.out.println("Goodbye");
        System.exit(0);
        
    }
}
