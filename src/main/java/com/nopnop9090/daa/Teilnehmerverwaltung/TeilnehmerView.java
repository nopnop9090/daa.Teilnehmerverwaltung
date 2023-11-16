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
import java.awt.event.KeyEvent;

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
	public JMenu menu = new JMenu();
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem miNew;
	private JMenuItem miOpen;
	private JMenuItem miSave;
	private JMenuItem miSaveAs;
	private JMenuItem miQuit;

	
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

			menuBar = new JMenuBar();
			{
				menu = new JMenu("Datei");
				{
					miNew = new JMenuItem("Neu");
					miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK));
					miNew.setMnemonic('N');
					miNew.addActionListener(this);
					menu.add(miNew);

					miOpen = new JMenuItem("Öffnen ...");
					miOpen.addActionListener(this);
					menu.add(miOpen);

					miSave = new JMenuItem("Speichern");
					miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
					miSave.setMnemonic('S');
					miSave.addActionListener(this);
					menu.add(miSave);

					miSaveAs = new JMenuItem("Speichern unter ...");
					miSaveAs.addActionListener(this);
					menu.add(miSaveAs);
					
					// Trennlinie
					menu.addSeparator();
					
					// 3. Eintrag zu Datei
					miQuit = new JMenuItem("Beenden");
					miQuit.addActionListener(this);
					menu.add(miQuit);
				}

				menuBar.add(menu);
			}

			contentPane.add(menuBar, BorderLayout.NORTH);

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
								btnNew.addActionListener(this);
								buttonPanel.add(btnNew);

								btnChange = new JButton();
								btnChange.setText("\u00c4ndern");
								btnChange.addActionListener(this);
								buttonPanel.add(btnChange);

								btnDelete = new JButton();
								btnDelete.setText("L\u00f6schen");
								btnDelete.addActionListener(this);
								buttonPanel.add(btnDelete);

								btnSave = new JButton();
								btnSave.setText("Speichern");
								btnSave.addActionListener(this);
								btnSave.setVisible(false);
								buttonPanel.add(btnSave);
								
								btnAbort = new JButton();
								btnAbort.setText("Abbruch");
								btnAbort.addActionListener(this);
								btnAbort.setVisible(false);
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
							tnJList.addListSelectionListener(this);

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
		System.out.println("[" + e.getActionCommand() + "]");

		if(e.getSource() == btnNew) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_new();
					}
				});
		} else if(e.getSource() == btnChange) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_change();
					}
				});
		}else if(e.getSource() == btnDelete) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_delete();
					}
				});
		}else if(e.getSource() == btnSave) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						controller.btnClick_save();
					}
				});
		}else if(e.getSource() == btnAbort) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.btnClick_abort();
				}
			});
		}else if(e.getSource() == miNew) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.miClick_new();
				}
			});
		}else if(e.getSource() == miOpen) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.miClick_open();
				}
			});
		}else if(e.getSource() == miSave) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.miClick_save();
				}
			});
		}else if(e.getSource() == miSaveAs) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.miClick_saveas();
				}
			});
		}else if(e.getSource() == miQuit) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					controller.miClick_quit();
				}
			});
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
