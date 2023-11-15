package com.nopnop9090.daa.Teilnehmerverwaltung;

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
            	TeilnehmerModel model = new TeilnehmerModel("teilnehmerliste.csv");
            	TeilnehmerView view = new TeilnehmerView();
                TeilnehmerController controller = new TeilnehmerController(model, view);

                view.setVisible(true);
            }
        });
    }
}
