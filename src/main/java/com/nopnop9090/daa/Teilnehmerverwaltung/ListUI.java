package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;


public class ListUI {

	JFrame frame = new JFrame("Teilnehmerverwaltung");
	JPanel mainPanel = new JPanel(new FlowLayout());
	JPanel tnPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JPanel labelPanel = new JPanel();
	JPanel textPanel = new JPanel();
	JPanel listePanel = new JPanel();
	JPanel buttonPanel = new JPanel();
	JButton btnNew = new JButton("Neu");
	JButton btnChange = new JButton("Ändern");
	JButton btnDelete = new JButton("Löschen");
	JButton btnSave = new JButton("Speichern");
	
	public ListUI() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		TitledBorder titledBorder = BorderFactory.createTitledBorder("Teilnehmer");
		titledBorder.setTitleJustification(TitledBorder.LEFT);
        titledBorder.setTitlePosition(TitledBorder.TOP);
        tnPanel.setBorder(titledBorder);
				
        JLabel label = new JLabel("TN-Nr");
        //label.setBounds(0,0,50,20);
        labelPanel.add(label);

        label = new JLabel("Gruppe");
        //label.setBounds(0,0,50,20);
        labelPanel.add(label);

        label = new JLabel("Name");
        //label.setBounds(0,0,50,20);
        labelPanel.add(label);

        label = new JLabel("Vorname");
        //label.setBounds(0,0,50,20);
        labelPanel.add(label);

        labelPanel.setLayout(new GridLayout(4, 1));
		inputPanel.add(labelPanel);

		
		// JTextField - einzeilige Eingabe
		JTextField text = new JTextField();
		text.setBounds(10, 10, 200, 50);
		textPanel.add(text);
		
		// JTextField - einzeilige Eingabe
		text = new JTextField();
		text.setBounds(10, 10, 200, 50);
		textPanel.add(text);

		textPanel.setLayout(new GridLayout(4, 1));
		inputPanel.add(textPanel);
		
		tnPanel.add(inputPanel, BorderLayout.NORTH);

		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		buttonPanel.add(btnNew);
		buttonPanel.add(btnChange);
		buttonPanel.add(btnDelete);
		buttonPanel.add(btnSave);
		tnPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		mainPanel.add(tnPanel);
		
		mainPanel.add(listePanel);
		frame.add(mainPanel);
		
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);		
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new ListUI();
		
	}

}
