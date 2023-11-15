package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.io.File;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class App 
{
    public static void main( String[] args )
    {
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	TeilnehmerModel model = new TeilnehmerModel();

            	if(new File("teilnehmerliste.csv").exists()) {
                	model.readFromCSV("teilnehmerliste.csv");
            	} else {
            		model.setSampleContents();
            	}

            	TeilnehmerView view = new TeilnehmerView(model);
                TeilnehmerController controller = new TeilnehmerController(model, view);

                view.setVisible(true);
            }
        });
    }
}
