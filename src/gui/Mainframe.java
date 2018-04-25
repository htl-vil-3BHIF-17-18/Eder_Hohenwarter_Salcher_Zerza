package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import bll.Kategorie;
import bll.Task;

public class Mainframe extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181889914322477954L;

	private JLabel lblvon = null;
	private JLabel lblbis = null;
	private JTextField txtvon = null;
	private JTextField txtbis = null;
	
	private JMenuBar menubar = null;
	private JButton btnRefresh = null;
	private Taskdialog taskdialog = null;
	private ListPanel tasktable = null;
	
	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


	
	
	public Mainframe(String title) {
		super(title);
		this.setMinimumSize(new Dimension(800,400));
		this.setPreferredSize(new Dimension(800,400));
		initializeControls();
		
		//***************
		//ZERZA HIER EIN BEISPIEL FÜR DIE ÜBERGABE :) 
		try {
			Date date1=dateFormat.parse("16.5.2001");
			Date date2=dateFormat.parse("17.5.2001");
			this.tasktable.addTask(new Task(Kategorie.GLF,"D","schwer",date1,date2,false));
			}catch(ParseException e)
			{
				e.printStackTrace();
			}
		//***************
	
		this.pack();
		this.setVisible(true);
	}
	private void initializeControls() {
		this.setLayout(new BorderLayout());
		menubar = new JMenuBar();
		btnRefresh = new JButton("Refresh");
		btnRefresh.setActionCommand("refresh");
		tasktable = new ListPanel();
		
		lblvon = new JLabel("Tasks anzeigen vom:");
		lblbis = new JLabel("bis:");
		
		txtvon = new JTextField("");
		txtvon.setMaximumSize(new Dimension(100,50));
		txtbis = new JTextField("");
		txtbis.setMaximumSize(new Dimension(100,50));
		
		taskdialog = new Taskdialog(this,null);
		
		menubar.add(btnRefresh);
		menubar.add(lblvon);
		menubar.add(txtvon);
		menubar.add(lblbis);
		menubar.add(txtbis);
		
		this.add(menubar,BorderLayout.PAGE_START);
		this.add(tasktable,BorderLayout.CENTER);
		this.add(taskdialog,BorderLayout.EAST);
		
	}
}
