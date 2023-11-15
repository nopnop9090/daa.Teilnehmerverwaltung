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
	private JPanel mainPanel;
	private JPanel tnPanel;
	private JPanel inputPanel;
	private JPanel labelPanel;
	private JLabel lblTNNr;
	private JLabel lblGroup;
	private JLabel lblSurName;
	private JLabel lblFirstName;
	private JPanel textPanel;
	public JTextField txtTNNr;
	public JTextField txtGroup;
	public JComboBox<String> cmbGroup;
	public JTextField txtSurName;
	public JTextField txtFirstName;
	private JPanel buttonPanel;
	public JButton btnNew;
	public JButton btnChange;
	public JButton btnDelete;
	public JButton btnSave;
	public JButton btnAbort;
	private JPanel listePanel;
	private JScrollPane scrollPane1;
	public JList<Teilnehmer> tnJList;
    
	private TeilnehmerController controller = null;
		
	public void setController(TeilnehmerController controller) {
		this.controller = controller;
		System.out.println("controller registered");
	}

	public TeilnehmerView() {
		initComponents();
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

		tnJList.addListSelectionListener(this);
		btnNew.addActionListener(this);
		btnChange.addActionListener(this);
		btnDelete.addActionListener(this);
		btnSave.addActionListener(this);
		btnAbort.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Schedule the additional work on the EDT
                SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
					    controller.performCleanupWork();
					}
				});
            }
        });
		
	}


	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand() + " !");

		switch(e.getActionCommand().toLowerCase()) {
			case "neu":
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_new();
					}
				});
				break;
			case "ändern":
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_change();
					}
				});
				break;
			case "löschen":
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_delete();
					}
				});
				break;
			case "speichern":
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_save();
					}
				});
				break;
			case "abbruch":
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_abort();
					}
				});
				break;
		}
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
	    SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
			    controller.valueChanged(e);
			}
		});
	}}
