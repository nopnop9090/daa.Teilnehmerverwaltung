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
    		teilnehmerModel.setSampleContents();
    	}
    	
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		waitForClose(new TeilnehmerView(teilnehmerModel));
		
    	teilnehmerModel.writeToCSV("teilnehmerliste.csv");
        
        System.out.println("Goodbye");
        System.exit(0);
        
    }
}
