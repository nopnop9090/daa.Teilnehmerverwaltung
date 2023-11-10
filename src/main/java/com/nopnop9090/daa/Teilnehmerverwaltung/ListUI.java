package com.nopnop9090.daa.Teilnehmerverwaltung;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;


public class ListUI extends JFrame implements ActionListener {
	public ListUI() {
		initComponents();
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
		txtGroup = new JTextField();
		txtSurName = new JTextField();
		txtFirstName = new JTextField();
		buttonPanel = new JPanel();
		btnNew = new JButton();
		btnChange = new JButton();
		btnDelete = new JButton();
		btnSave = new JButton();
		listePanel = new JPanel();
		scrollPane1 = new JScrollPane();
		list1 = new JList();

		setTitle("Teilnehmerverwaltung");
		setMinimumSize(new Dimension(690, 460));
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		{
			mainPanel.setLayout(new GridLayout(1, 2, 20, 0));

			{
				tnPanel.setBorder(null);
				tnPanel.setLayout(new BorderLayout());

				{
					inputPanel.setBorder(new TitledBorder("Teilnehmer"));
					inputPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

					{
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
					}
					inputPanel.add(labelPanel);

					{
						textPanel.setLayout(new GridLayout(4, 1, 0, 10));

						txtTNNr.setMinimumSize(new Dimension(200, 20));
						txtTNNr.setPreferredSize(new Dimension(200, 20));
						textPanel.add(txtTNNr);
						textPanel.add(txtGroup);
						textPanel.add(txtSurName);
						textPanel.add(txtFirstName);
					}
					inputPanel.add(textPanel);
				}
				tnPanel.add(inputPanel, BorderLayout.NORTH);

				{
					buttonPanel.setLayout(new FlowLayout());

					btnNew.setText("Neu");
					btnNew.addActionListener(this);
					buttonPanel.add(btnNew);

					btnChange.setText("\u00c4ndern");
					btnChange.addActionListener(this);
					buttonPanel.add(btnChange);

					btnDelete.setText("L\u00f6schen");
					btnDelete.addActionListener(this);
					buttonPanel.add(btnDelete);

					btnSave.setText("Speichern");
					btnSave.addActionListener(this);
					buttonPanel.add(btnSave);
				}
				tnPanel.add(buttonPanel, BorderLayout.SOUTH);
			}
			mainPanel.add(tnPanel);

			{
				listePanel.setBorder(new TitledBorder("TN-Liste"));
				listePanel.setLayout(new BorderLayout());

				{
					scrollPane1.setViewportView(list1);
				}
				listePanel.add(scrollPane1, BorderLayout.CENTER);
			}
			mainPanel.add(listePanel);
		}

		JPanel outerPanel = new JPanel();
		outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		outerPanel.setLayout(new BorderLayout());
		outerPanel.add(mainPanel, BorderLayout.CENTER);
		
		contentPane.add(outerPanel, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}

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
	private JTextField txtSurName;
	private JTextField txtFirstName;
	private JPanel buttonPanel;
	private JButton btnNew;
	private JButton btnChange;
	private JButton btnDelete;
	private JButton btnSave;
	private JPanel listePanel;
	private JScrollPane scrollPane1;
	private JList list1;

	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " !");
	}
	
	public static void main(String[] args) {
		new ListUI();
		
	}
}
