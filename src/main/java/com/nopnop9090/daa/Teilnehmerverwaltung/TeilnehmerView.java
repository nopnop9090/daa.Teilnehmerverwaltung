package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.*;

@SuppressWarnings("serial")
public class TeilnehmerView extends JFrame implements ActionListener, ListSelectionListener {
	private static final int MODE_DISPLAY = 0;
    private static final int MODE_EDIT = 1;
    private static final int MODE_CHANGE = 2;
    
	private int editMode = MODE_DISPLAY;
	private Teilnehmer lastSelected = null;

	private JPanel mainPanel;
	private JPanel tnPanel;
	private JPanel inputPanel;
	private JPanel labelPanel;
	private JLabel lblTNNr;
	private JLabel lblGroup;
	private JLabel lblSurName;
	private JLabel lblFirstName;
	private JPanel textPanel;
	private JTextField txtTNNr;
	private JTextField txtGroup;
	private JComboBox<String> cmbGroup;
	private JTextField txtSurName;
	private JTextField txtFirstName;
	private JPanel buttonPanel;
	private JButton btnNew;
	private JButton btnChange;
	private JButton btnDelete;
	private JButton btnSave;
	private JButton btnAbort;
	private JPanel listePanel;
	private JScrollPane scrollPane1;
	private JList<Teilnehmer> tnJList;
    
	private TeilnehmerController teilnehmerController = null;
	private TeilnehmerModel teilnehmerModel = null;
		
	public void setController(TeilnehmerController controller) {
		this.teilnehmerController = controller;
		System.out.println("controller registered");
	}

	public TeilnehmerView(TeilnehmerModel model) {
		this.teilnehmerModel=model;

		initComponents();
		rebuild_tnJList();
	}
	
	private void initComponents() {
		setTitle("Teilnehmerverwaltung");
		setMinimumSize(new Dimension(690, 460));
		setResizable(false);
		
		Container contentPane = getContentPane();	// the frames contentpane
		{
			contentPane.setLayout(new BorderLayout());

			JPanel outerPanel = new JPanel();	// surrounding panel with 20 pixel padding
			{
				outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
				outerPanel.setLayout(new BorderLayout());
				{
					mainPanel = new JPanel();	
					{
						mainPanel.setLayout(new GridLayout(1, 2, 20, 0));
						
						tnPanel = new JPanel();
						{
							tnPanel.setBorder(null);
							tnPanel.setLayout(new BorderLayout());

							inputPanel = new JPanel();
							{
								inputPanel.setBorder(new TitledBorder("Teilnehmer"));
								inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

								labelPanel = new JPanel();
								{
									labelPanel.setLayout(new GridLayout(4, 1, 0, 16));

									lblTNNr = new JLabel();
									lblTNNr.setText("TN-Nr");
									lblTNNr.setHorizontalAlignment(SwingConstants.RIGHT);
									labelPanel.add(lblTNNr);

									lblGroup = new JLabel();
									lblGroup.setText("Gruppe");
									lblGroup.setHorizontalAlignment(SwingConstants.RIGHT);
									labelPanel.add(lblGroup);

									lblSurName = new JLabel();
									lblSurName.setText("Name");
									lblSurName.setHorizontalAlignment(SwingConstants.RIGHT);
									labelPanel.add(lblSurName);

									lblFirstName = new JLabel();
									lblFirstName.setText("Vorname");
									lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
									labelPanel.add(lblFirstName);
								}

								inputPanel.add(labelPanel);
								
								
								textPanel = new JPanel();
								{
									textPanel.setLayout(new GridLayout(4, 1, 0, 10));
									
									txtTNNr = new JTextField();
									txtTNNr.setMinimumSize(new Dimension(300, 20));
									txtTNNr.setPreferredSize(new Dimension(300, 20));
									((AbstractDocument) txtTNNr.getDocument()).setDocumentFilter(new NumericFilter());
									textPanel.add(txtTNNr);

									cmbGroup = new JComboBox<>();
									cmbGroup.setEditable(true);
									textPanel.add(cmbGroup);

									txtSurName = new JTextField();
									textPanel.add(txtSurName);

									txtFirstName = new JTextField();
									textPanel.add(txtFirstName);
								}

								inputPanel.add(textPanel);
							}

							tnPanel.add(inputPanel, BorderLayout.NORTH);

							buttonPanel = new JPanel();
							{
								buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

								btnNew = new JButton();
								btnNew.setText("Neu");
								buttonPanel.add(btnNew);

								btnChange = new JButton();
								btnChange.setText("\u00c4ndern");
								buttonPanel.add(btnChange);

								btnDelete = new JButton();
								btnDelete.setText("L\u00f6schen");
								buttonPanel.add(btnDelete);

								btnSave = new JButton();
								btnSave.setText("Speichern");
								buttonPanel.add(btnSave);
								
								btnAbort = new JButton();
								btnAbort.setText("Abbruch");
								buttonPanel.add(btnAbort);
							}
							
							tnPanel.add(buttonPanel, BorderLayout.SOUTH);
						}

						mainPanel.add(tnPanel);
					
						listePanel = new JPanel();
						{
							listePanel.setBorder(new TitledBorder("TN-Liste"));
							listePanel.setLayout(new BorderLayout());
							
							tnJList = new JList<Teilnehmer>();

							scrollPane1 = new JScrollPane();
							scrollPane1.setViewportView(tnJList);
							scrollPane1.setBorder(null);

							listePanel.add(scrollPane1, BorderLayout.CENTER);
						}

						mainPanel.add(listePanel);
					}
				}
				outerPanel.add(mainPanel, BorderLayout.CENTER);
			}

			contentPane.add(outerPanel, BorderLayout.CENTER);
		}
		
		this.pack();
		
		setLocationRelativeTo(getOwner());
		
		btnSave.setVisible(false);
		btnAbort.setVisible(false);

		tnJList.setModel(teilnehmerModel);
		tnJList.addListSelectionListener(this);
		btnNew.addActionListener(this);
		btnChange.addActionListener(this);
		btnDelete.addActionListener(this);
		btnSave.addActionListener(this);
		btnAbort.addActionListener(this);
		
		enableEdits();
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Schedule the additional work on the EDT
                SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
					    performCleanupWork();
					}
				});
            }
        });
		
	}

	public void enableEdits()
	{
		Boolean enable = (this.editMode==MODE_EDIT || this.editMode==MODE_CHANGE);
		
		if(this.editMode!=MODE_CHANGE)
			txtTNNr.setEditable(enable);
		cmbGroup.setEnabled(enable);
		txtSurName.setEditable(enable);
		txtFirstName.setEditable(enable);
		tnJList.setEnabled(!enable);
	}

	void performCleanupWork() {
		System.out.println("closing ..");
    	teilnehmerModel.writeToCSV("teilnehmerliste.csv");

        System.exit(0);
	}
	
	void rebuild_tnJList() {
		Teilnehmer oldtn = tnJList.getSelectedValue();
		if(oldtn != null)
			System.out.println("info: " + oldtn);
			
		teilnehmerModel.sortById();
		
		cmbGroup.removeAllItems();
		
		for (Teilnehmer teilnehmer : teilnehmerModel.getTeilnehmerList()) {
			String group = teilnehmer.getGruppe();
			if (isGroupInComboBox(group)==-1) {
				cmbGroup.addItem(group);
			}
		}

		Boolean allowEditDelete = (teilnehmerModel.getSize()>0);
		btnChange.setVisible(allowEditDelete);
		btnDelete.setVisible(allowEditDelete);
		if(oldtn == null)
			tnJList.setSelectedIndex(0);
		else
			tnJList.setSelectedValue(oldtn,  true);
	}

	private int isGroupInComboBox(String group) {
	    for (int i = 0; i < cmbGroup.getItemCount(); i++) {
	        if (group.equals(cmbGroup.getItemAt(i))) {
	            return i;
	        }
	    }
	    return -1;
	}
	
	public void switchEditMode() {
		if(btnSave.isVisible()) {
			btnNew.setVisible(true);
			btnSave.setVisible(false);
			btnAbort.setVisible(false);
			
			rebuild_tnJList(); // this will make bntChange and btnDelete visible if appropriate 
			tnJList.setSelectedValue(this.lastSelected, true);
			
		} else {
			this.lastSelected = tnJList.getSelectedValue();
			
			btnNew.setVisible(false);
			btnSave.setVisible(true);
			btnAbort.setVisible(true);
			
			// explicitly set visible to these to false, they are made visible in rebuild_tnJList as needed
			btnChange.setVisible(false);
			btnDelete.setVisible(false);
		}
		enableEdits();
	}

	public void clearFields() {
		setFields("", "", "", "");
	}

	public void btnClick_new() {
		this.editMode = MODE_EDIT;
		switchEditMode();

		int newTNNr=1;
		for (Teilnehmer teilnehmer : teilnehmerModel.getTeilnehmerList()) {
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
		if(tnJList.getSelectedIndex()>=0) {	// make sure something is actually selected (the button should not be visible otherwise but who knows..)
			if(JOptionPane.showConfirmDialog(null, "Soll der gewählte Eintrag wirklich gelöscht werden?", "Achtung", JOptionPane.YES_NO_OPTION)==0) {
				Teilnehmer selectedTeilnehmer = tnJList.getSelectedValue();
				teilnehmerModel.remove(selectedTeilnehmer);
				rebuild_tnJList();
				// yes, delete
			}
		}
	}
	public void btnClick_save () {
		try {
			Boolean skipSaving = false;

			if(((String)cmbGroup.getSelectedItem()).length()<1 || txtFirstName.getText().length()<1 || txtSurName.getText().length()<1) {
				skipSaving = true;
				JOptionPane.showMessageDialog(null, "Alle Felder müssen ausgefüllt sein", "Fehler", JOptionPane.WARNING_MESSAGE);
				// missing fields? alert the user and abort saving  
			}
				
			if(this.editMode!=MODE_CHANGE && !skipSaving) {
				int newTNNr = Integer.parseInt(txtTNNr.getText());
				for (Teilnehmer teilnehmer : teilnehmerModel.getTeilnehmerList()) {
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
					teilnehmerModel.remove(tnJList.getSelectedValue());
				
				Teilnehmer newtn = new Teilnehmer(Integer.parseInt(txtTNNr.getText()), ((String)cmbGroup.getSelectedItem()), txtSurName.getText(), txtFirstName.getText());
				teilnehmerModel.add(newtn);
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

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " !");

		switch(e.getActionCommand().toLowerCase()) {
			case "neu":
				btnClick_new();
				break;
			case "ändern":
				btnClick_change();
				break;
			case "löschen":
				btnClick_delete();
				break;
			case "speichern":
				btnClick_save();
				break;
			case "abbruch":
				btnClick_abort();
				break;
		}
	}

	public void setFields(String tnNr, String firstName, String surName, String group)
	{
		txtTNNr.setText(tnNr);
		txtFirstName.setText(firstName);
		txtSurName.setText(surName);
		
		int n=isGroupInComboBox(group);
		if(n!=-1)
			cmbGroup.setSelectedIndex(n);
		else
			cmbGroup.setSelectedItem(group);
	}
	
	public void setFields(Teilnehmer tn) {
        if (tn != null) {
        	setFields("" + tn.getId(), tn.getVorname(), tn.getName(), tn.getGruppe());
        }
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
        if (!e.getValueIsAdjusting()) {
            // Get the selected value
    		this.lastSelected = tnJList.getSelectedValue();
    		System.out.println("listselection: " + this.lastSelected);
    		setFields(this.lastSelected);
        }
	}
}
