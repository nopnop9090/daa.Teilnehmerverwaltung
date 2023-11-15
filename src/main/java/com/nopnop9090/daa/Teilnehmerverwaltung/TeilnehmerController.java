package com.nopnop9090.daa.Teilnehmerverwaltung;

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;

public class TeilnehmerController {

	private TeilnehmerModel model;
	private TeilnehmerView view;

	private static final int MODE_DISPLAY = 0;
    private static final int MODE_EDIT = 1;
    private static final int MODE_CHANGE = 2;
    
	private int editMode = MODE_DISPLAY;
	private Teilnehmer lastSelected = null;

	
	public TeilnehmerController(TeilnehmerModel model, TeilnehmerView view) {
		this.model = model;
		this.view = view;

		view.setController(this);
		view.tnJList.setModel(model);

		rebuild_tnJList();
		enableEdits();
	}

	public void btnClick_new() {
		this.editMode = MODE_EDIT;
		switchEditMode();

		int newTNNr=1;
		for (Teilnehmer teilnehmer : model.getTeilnehmerList()) {
			int tmp = teilnehmer.getId();
			if(tmp>=newTNNr)
				newTNNr=tmp+1;
		}
		
		setFields("" + newTNNr, "", "", "");
	}
	
	public void btnClick_change() {
		this.editMode = MODE_CHANGE;
		switchEditMode();
	}
	
	public void btnClick_delete() {
		// "are you sure?"
		if(view.tnJList.getSelectedIndex()>=0) {	// make sure something is actually selected (the button should not be visible otherwise but who knows..)
			if(JOptionPane.showConfirmDialog(null, "Soll der gewählte Eintrag wirklich gelöscht werden?", "Achtung", JOptionPane.YES_NO_OPTION)==0) {
				Teilnehmer selectedTeilnehmer = view.tnJList.getSelectedValue();
				model.remove(selectedTeilnehmer);
				rebuild_tnJList();
				// yes, delete
			}
		}
	}
	public void btnClick_save () {
		try {
			Boolean skipSaving = false;

			if(((String)view.cmbGroup.getSelectedItem()).length()<1 || view.txtFirstName.getText().length()<1 || view.txtSurName.getText().length()<1) {
				skipSaving = true;
				JOptionPane.showMessageDialog(null, "Alle Felder müssen ausgefüllt sein", "Fehler", JOptionPane.WARNING_MESSAGE);
				// missing fields? alert the user and abort saving  
			}
				
			if(this.editMode!=MODE_CHANGE && !skipSaving) {
				int newTNNr = Integer.parseInt(view.txtTNNr.getText());
				for (Teilnehmer teilnehmer : model.getTeilnehmerList()) {
					if(newTNNr == teilnehmer.getId()) {
						// already exisiting id? alert the user and abort saving 
						JOptionPane.showMessageDialog(null, "Teilnehmernummer bereits vorhanden", "Fehler", JOptionPane.WARNING_MESSAGE);
						skipSaving = true;
						break;
					}
				}
			}
			if(!skipSaving) {
				if(this.editMode==MODE_CHANGE) // edit = remove + readd 
					model.remove(view.tnJList.getSelectedValue());
				
				Teilnehmer newtn = new Teilnehmer(Integer.parseInt(view.txtTNNr.getText()), ((String)view.cmbGroup.getSelectedItem()), view.txtSurName.getText(), view.txtFirstName.getText());
				model.add(newtn);
				this.lastSelected = newtn;

				this.editMode = MODE_DISPLAY;
				switchEditMode();
			}
		} catch( NumberFormatException ex ) {
			JOptionPane.showMessageDialog(null, "Teilnehmernummer muss numerisch sein", "Fehler", JOptionPane.WARNING_MESSAGE);
			// didnt work, alert the user ..
		}
	}
	
	public void btnClick_abort() {
		this.editMode = MODE_DISPLAY;
		switchEditMode();
		setFields(this.lastSelected);
	}

	public void valueChanged(ListSelectionEvent e) {
	    if (!e.getValueIsAdjusting()) {
	        // Get the selected value
			this.lastSelected = view.tnJList.getSelectedValue();
			System.out.println("listselection: " + this.lastSelected);
			setFields(this.lastSelected);
	    }
	}
	
	void performCleanupWork() {
		System.out.println("closing ..");
    	model.writeToCSV("teilnehmerliste.csv");

        System.exit(0);
	}
	
	public void enableEdits()
	{
		Boolean enable = (this.editMode==MODE_EDIT || this.editMode==MODE_CHANGE);
		
		if(this.editMode!=MODE_CHANGE)
			view.txtTNNr.setEditable(enable);
		view.cmbGroup.setEnabled(enable);
		view.txtSurName.setEditable(enable);
		view.txtFirstName.setEditable(enable);
		view.tnJList.setEnabled(!enable);
	}

	public int isGroupInComboBox(String group) {
	    for (int i = 0; i < view.cmbGroup.getItemCount(); i++) {
	        if (group.equals(view.cmbGroup.getItemAt(i))) {
	            return i;
	        }
	    }
	    return -1;
	}

	public void setFields(String tnNr, String firstName, String surName, String group)
	{
		view.txtTNNr.setText(tnNr);
		view.txtFirstName.setText(firstName);
		view.txtSurName.setText(surName);
		
		int n=isGroupInComboBox(group);
		if(n!=-1)
			view.cmbGroup.setSelectedIndex(n);
		else
			view.cmbGroup.setSelectedItem(group);
	}
	
	public void clearFields() {
		setFields("", "", "", "");
	}

	
	void rebuild_tnJList() {
		Teilnehmer oldtn = view.tnJList.getSelectedValue();
		if(oldtn != null)
			System.out.println("info: " + oldtn);
			
		model.sortById();
		
		view.cmbGroup.removeAllItems();
		
		for (Teilnehmer teilnehmer : model.getTeilnehmerList()) {
			String group = teilnehmer.getGruppe();
			if (isGroupInComboBox(group)==-1) {
				view.cmbGroup.addItem(group);
			}
		}

		Boolean allowEditDelete = (model.getSize()>0);
		view.btnChange.setVisible(allowEditDelete);
		view.btnDelete.setVisible(allowEditDelete);
		if(oldtn == null)
			view.tnJList.setSelectedIndex(0);
		else
			view.tnJList.setSelectedValue(oldtn,  true);
	}

	public void switchEditMode() {
		if(view.btnSave.isVisible()) {
			view.btnNew.setVisible(true);
			view.btnSave.setVisible(false);
			view.btnAbort.setVisible(false);
			
			rebuild_tnJList(); // this will make bntChange and btnDelete visible if appropriate 
			view.tnJList.setSelectedValue(this.lastSelected, true);
			
		} else {
			this.lastSelected = view.tnJList.getSelectedValue();
			
			view.btnNew.setVisible(false);
			view.btnSave.setVisible(true);
			view.btnAbort.setVisible(true);
			
			// explicitly set visible to these to false, they are made visible in rebuild_tnJList as needed
			view.btnChange.setVisible(false);
			view.btnDelete.setVisible(false);
		}
		enableEdits();
	}

	public void setFields(Teilnehmer tn) {
        if (tn != null) {
        	setFields("" + tn.getId(), tn.getVorname(), tn.getName(), tn.getGruppe());
        }
	}

	public void miClick_quit() {
		view.dispose();
	}

	public void miClick_new() {
		// TODO Auto-generated method stub
		
	}

	public void miClick_open() {
		// TODO Auto-generated method stub
		
	}

	public void miClick_save() {
		// TODO Auto-generated method stub
		
	}

	public void miClick_saveas() {
		// TODO Auto-generated method stub
		
	}


}
