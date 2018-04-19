package gui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bll.Task;

public class Taskdialog extends JPanel{
	private JLabel lblKategorie = null;
	private JLabel lblVon = null;
	private JLabel lblBis = null;
	private JLabel lblFach = null;
	private JLabel lblBeschreibung = null;
	private JButton btnAdd = null;
	
	private JTextField txtKategorie= null;
	private JTextField txtVon = null;
	private JTextField txtBis = null;
	private JTextField txtFach = null;
	private JTextField txtBeschreibung = null;
	
	private JFrame parent = null;
	private Task task = null;
	
	public Taskdialog(JFrame parent,Task task) {
		this.parent = parent;
		this.task = task;
		initializeControls();
		
	}
	private void initializeControls() {
		this.setLayout(new GridLayout(7,2));
		this.setMinimumSize(new Dimension(200,300));
		this.setPreferredSize(new Dimension(200,300));
		lblKategorie = new JLabel("Kategorie :");
		lblVon = new JLabel("Von :");
		lblBis= new JLabel("Bis :");
		lblFach = new JLabel("Fach :");
		lblBeschreibung = new JLabel("Beschreibung :");
		btnAdd = new JButton("Add");
		
//		txtISBN = new JTextField();
//		txttitle = new JTextField();
//		txtfirstname= new JTextField();
//		txtlastname = new JTextField();
//		txtpublisher = new JTextField();
//		txtprice = new JTextField();
//		
//		
//		this.add(lblISBN);
//		this.add(txtISBN);
//		this.add(lbltitle);
//		this.add(txttitle);
//		this.add(lblfirstname);
//		this.add(txtfirstname);
//		this.add(lbllastname);
//		this.add(txtlastname);
//		this.add(lblpublisher);
//		this.add(txtpublisher);
//		this.add(lblprice);
//		this.add(txtprice);
//		this.add(lblamount);
//		this.add(txtamount);
		
		
	}


}
