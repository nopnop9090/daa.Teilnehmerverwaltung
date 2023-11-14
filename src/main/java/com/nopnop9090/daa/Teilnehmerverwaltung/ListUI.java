package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.*;

public class ListUI extends JFrame implements ActionListener, ListSelectionListener {
	public ListUI(TeilnehmerModel model) {
		this.editMode = 0;
		this.lastSelected = 0;
		
		initComponents();
		this.teilnehmerList=model.getTeilnehmerList();
		rebuildTeilnehmerJList();
		teilnehmerJList.setSelectedIndex(0);
	}
	
	public void enableEdits(Boolean enable)
	{

//		// enable/disable text fields (different looks)
//		txtTNNr.setFocusable(enable);
//		txtGroup.setFocusable(enable);
//		txtSurName.setFocusable(enable);
//		txtFirstName.setFocusable(enable);
	
		if(this.editMode!=2)
			txtTNNr.setEditable(enable);
		//txtGroup.setEditable(enable);
		cmbGroup.setEnabled(enable);
		txtSurName.setEditable(enable);
		txtFirstName.setEditable(enable);
		
	}

	private void initComponents() {
		mainPanel = new JPanel();
		tnPanel = new JPanel();
		inputPanel = new JPanel();
		labelPanel = new JPanel();
		lblTNNr = new JLabel();
		lblGroup = new JLabel();
		
		lblSurName = new JLabel();
		lblFirstName = new JLabel();
		textPanel = new JPanel();
		txtTNNr = new JTextField();
		//txtGroup = new JTextField();
		cmbGroup = new JComboBox<>();
		cmbGroup.setEditable(true);

		txtSurName = new JTextField();
		txtFirstName = new JTextField();
		buttonPanel = new JPanel();
		btnNew = new JButton();
		btnChange = new JButton();
		btnDelete = new JButton();
		btnSave = new JButton();
		btnAbort = new JButton();
		listePanel = new JPanel();
		scrollPane1 = new JScrollPane();
		listModel = new DefaultListModel<Teilnehmer>();
		teilnehmerJList = new JList<Teilnehmer>(listModel);

		teilnehmerJList.addListSelectionListener(this);
		
		setTitle("Teilnehmerverwaltung");
		setMinimumSize(new Dimension(690, 460));
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		mainPanel.setLayout(new GridLayout(1, 2, 20, 0));

		tnPanel.setBorder(null);
		tnPanel.setLayout(new BorderLayout());

		inputPanel.setBorder(new TitledBorder("Teilnehmer"));
		inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

		labelPanel.setLayout(new GridLayout(4, 1, 0, 16));

		lblTNNr.setText("TN-Nr");
		lblTNNr.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(lblTNNr);

		lblGroup.setText("Gruppe");
		lblGroup.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(lblGroup);

		lblSurName.setText("Name");
		lblSurName.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(lblSurName);

		lblFirstName.setText("Vorname");
		lblFirstName.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(lblFirstName);
		
		inputPanel.add(labelPanel);

		textPanel.setLayout(new GridLayout(4, 1, 0, 10));

		txtTNNr.setMinimumSize(new Dimension(300, 20));
		txtTNNr.setPreferredSize(new Dimension(300, 20));
		((AbstractDocument) txtTNNr.getDocument()).setDocumentFilter(new NumericFilter());
		textPanel.add(txtTNNr);

		
		//textPanel.add(txtGroup);
		textPanel.add(cmbGroup);
		textPanel.add(txtSurName);
		textPanel.add(txtFirstName);
		
		enableEdits(false);
		
		inputPanel.add(textPanel);
		
		tnPanel.add(inputPanel, BorderLayout.NORTH);

		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		btnNew.setText("Neu");
		btnNew.addActionListener(this);
		buttonPanel.add(btnNew);

		btnChange.setText("\u00c4ndern");
		btnChange.addActionListener(this);
		btnAbort.setVisible(false);
		buttonPanel.add(btnChange);

		btnDelete.setText("L\u00f6schen");
		btnDelete.addActionListener(this);
		btnAbort.setVisible(false);
		buttonPanel.add(btnDelete);

		btnSave.setText("Speichern");
		btnSave.addActionListener(this);
		buttonPanel.add(btnSave);
		btnSave.setVisible(false);
		
		btnAbort.setText("Abbruch");
		btnAbort.addActionListener(this);
		btnAbort.setVisible(false);
		buttonPanel.add(btnAbort);

		
		tnPanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.add(tnPanel);

		listePanel.setBorder(new TitledBorder("TN-Liste"));
		listePanel.setLayout(new BorderLayout());

		scrollPane1.setViewportView(teilnehmerJList);
		scrollPane1.setBorder(null);
		
		listePanel.add(scrollPane1, BorderLayout.CENTER);
		mainPanel.add(listePanel);

		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(mainPanel, BorderLayout.CENTER);
		
		contentPane.add(outerPanel, BorderLayout.CENTER);
		this.pack();
		
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

	private int editMode;
	private int lastSelected;
	private List<Teilnehmer> teilnehmerList;
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
	private JList<Teilnehmer> teilnehmerJList;
	private DefaultListModel<Teilnehmer> listModel;
	
	void rebuildTeilnehmerJList() {
		resortTNList();
		
		listModel.clear();
		cmbGroup.removeAllItems();
		
		for (Teilnehmer teilnehmer : teilnehmerList) {
			listModel.addElement(teilnehmer);
			String group = teilnehmer.getGruppe();
			if (isGroupInComboBox(group)==-1) {
				cmbGroup.addItem(group);
			}
		}
		Boolean allowEditDelete = (listModel.size()>0);
		btnChange.setVisible(allowEditDelete);
		btnDelete.setVisible(allowEditDelete);
		
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
			
			teilnehmerJList.setEnabled(true);
			rebuildTeilnehmerJList();

			enableEdits(false);
			
			teilnehmerJList.setSelectedIndex(this.lastSelected);
			
		} else {
			this.lastSelected=0;
			
			if(this.editMode>1)
				this.lastSelected = teilnehmerJList.getSelectedIndex();
			
			
			btnNew.setVisible(false);
			
			btnChange.setVisible(false);
			btnDelete.setVisible(false);
			
			btnSave.setVisible(true);
			btnAbort.setVisible(true);
			
			teilnehmerJList.setEnabled(false);

			enableEdits(true);
		}
	}

	public void resortTNList() {
    	Collections.sort(teilnehmerList, new Comparator<Teilnehmer>() {
			@Override
			public int compare(Teilnehmer t1, Teilnehmer t2) {
				return Integer.compare(t1.getId(), t2.getId());
			}
		});
	}
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " !");
		if(e.getActionCommand().equalsIgnoreCase("neu")) {
			this.editMode = 1;
			switchEditMode();

			int newTNNr=1;
			resortTNList();

			for (Teilnehmer teilnehmer : teilnehmerList) {
				int tmp = teilnehmer.getId();
				if(tmp>=newTNNr)
					newTNNr=tmp+1;
			}
			
			
			txtTNNr.setText("" + newTNNr);
			//txtGroup.setText("");
			cmbGroup.setSelectedItem("");
			txtFirstName.setText("");
			txtSurName.setText("");
		}else if(e.getActionCommand().equalsIgnoreCase("ändern")) {
			this.editMode = 2;
			switchEditMode();
		}else if(e.getActionCommand().equalsIgnoreCase("löschen")) {
			// "are you sure?"
			if(teilnehmerJList.getSelectedIndex()>=0) {	// make sure something is actually selected (the button should not be visible otherwise but who knows..)
				if(JOptionPane.showConfirmDialog(null, "Soll der gewählte Eintrag wirklich gelöscht werden?", "Achtung", JOptionPane.YES_NO_OPTION)==0) {
					Teilnehmer selectedTeilnehmer = teilnehmerJList.getSelectedValue();
					teilnehmerList.remove(selectedTeilnehmer);
					txtTNNr.setText("");
					cmbGroup.setSelectedIndex(-1);
					txtFirstName.setText("");
					txtSurName.setText("");
					rebuildTeilnehmerJList();
					teilnehmerJList.setSelectedIndex(0);
					// yes, delete
				}
			}
		}else if(e.getActionCommand().equalsIgnoreCase("speichern")) {
			try {
				Boolean skipSaving = false;

				if(((String)cmbGroup.getSelectedItem()).length()<1 || txtFirstName.getText().length()<1 || txtSurName.getText().length()<1) {
					skipSaving = true;
					JOptionPane.showMessageDialog(null, "Alle Felder müssen ausgefüllt sein", "Fehler", JOptionPane.WARNING_MESSAGE);
					// missing fields? alert the user and abort saving  
				}
					
				if(this.editMode!=2 && !skipSaving) {
					int newTNNr = Integer.parseInt(txtTNNr.getText());
					for (Teilnehmer teilnehmer : teilnehmerList) {
						if(newTNNr == teilnehmer.getId()) {
							// already exisiting id? alert the user and abort saving 
							JOptionPane.showMessageDialog(null, "Teilnehmernummer bereits vorhanden", "Fehler", JOptionPane.WARNING_MESSAGE);
							skipSaving = true;
							break;
						}
					}
				}
				if(!skipSaving) {
					if(this.editMode==2)
						teilnehmerList.remove(teilnehmerJList.getSelectedValue());
					
					int newTNNr = Integer.parseInt(txtTNNr.getText());
					teilnehmerList.add(new Teilnehmer(Integer.parseInt(txtTNNr.getText()), ((String)cmbGroup.getSelectedItem()), txtSurName.getText(), txtFirstName.getText()));
					rebuildTeilnehmerJList();
					this.editMode = 0;
					switchEditMode();
					//teilnehmerJList.setSelectedIndex(0);
				}
			} catch( NumberFormatException ex ) {
				JOptionPane.showMessageDialog(null, "Teilnehmernummer muss numerisch sein", "Fehler", JOptionPane.WARNING_MESSAGE);
				// didnt work, alert the user ..
			}
		}else if(e.getActionCommand().equalsIgnoreCase("abbruch")) {
			this.editMode = 0;
			txtTNNr.setText("");
			cmbGroup.setSelectedIndex(-1);
			txtFirstName.setText("");
			txtSurName.setText("");
			switchEditMode();
		}
	}

/*	public static void main(String[] args) {
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
    	List<Teilnehmer> teilnehmerList = new ArrayList<Teilnehmer>();
    	teilnehmerList.add(new Teilnehmer(2, "Comic", "Duck", "Donald"));
    	teilnehmerList.add(new Teilnehmer(3, "Zauberer", "Gans", "Gustav"));
    	teilnehmerList.add(new Teilnehmer(7, "Personen", "Niko", "Klaus"));

		new ListUI(teilnehmerList);
		
	}
*/
	@Override
	public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            // Get the selected value
            Teilnehmer selectedTeilnehmer = teilnehmerJList.getSelectedValue();
            if (selectedTeilnehmer != null) {
                // Access the internal ID
                int selectedId = selectedTeilnehmer.getId();
                System.out.println("Selected ID: " + selectedId);
                txtTNNr.setText("" + selectedTeilnehmer.getId());
                txtFirstName.setText("" + selectedTeilnehmer.getVorname());
                txtSurName.setText("" + selectedTeilnehmer.getName());
                cmbGroup.setSelectedIndex(isGroupInComboBox(selectedTeilnehmer.getGruppe()));
                //txtGroup.setText("" + selectedTeilnehmer.getGruppe());
                
            }
        }
	}
}
