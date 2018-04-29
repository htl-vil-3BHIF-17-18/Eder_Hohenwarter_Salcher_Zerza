package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import bll.Kategorie;
import bll.Task;
import dal.DatabaseHelper;

public class Mainframe extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181889914322477954L;

	private JLabel lblvon = null;
	private JLabel lblbis = null;
	private JLabel lblFilter = null;
	private JFormattedTextField txtvon = null;
	private JFormattedTextField txtbis = null;
	private JComboBox kategorieauswahl = null;
	private String[] cbKategorienListe = {"Alles","GLF","Mitarbeitskontrolle","Hausübung","PLF","Schularbeit","Schulübung"};


	
	private JMenuBar menubar = null;
	private JButton btnRefresh = null;
	private Taskdialog taskdialog = null;
	private ListPanel tasktable = null;

	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

	public Mainframe(String title) {
		super(title);
		this.setMinimumSize(new Dimension(800, 400));
		this.setPreferredSize(new Dimension(800, 400));
		initializeControls();

		// ***************
		// ZERZA HIER EIN BEISPIEL FÜR DIE ÜBERGABE :)
		try {
			Date date1 = dateFormat.parse("16.5.2001");
			Date date2 = dateFormat.parse("17.5.2001");
			this.tasktable.addTask(new Task(Kategorie.GLF, "D", "schwer", date1, date2, false));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// ***************

		this.pack();
		this.setVisible(true);
	}

	private void initializeControls() {
		this.setLayout(new BorderLayout());
		menubar = new JMenuBar();
		btnRefresh = new JButton("Refresh");
		btnRefresh.setActionCommand("refresh");
		tasktable = new ListPanel();

		lblvon = new JLabel("    Tasks anzeigen vom:  ");
		lblbis = new JLabel("    bis:  ");
		lblFilter = new JLabel("    Filter:  ");
		

		txtvon = new JFormattedTextField(dateFormat);
		txtvon.setMaximumSize(new Dimension(100, 50));
		txtbis = new JFormattedTextField(dateFormat);
		txtbis.setMaximumSize(new Dimension(100, 50));
		
		kategorieauswahl = new JComboBox(cbKategorienListe);
		kategorieauswahl.setMaximumSize(new Dimension(150, 50));

		taskdialog = new Taskdialog(this, null, this);

		menubar.add(btnRefresh);
		menubar.add(lblvon);
		menubar.add(txtvon);
		menubar.add(lblbis);
		menubar.add(txtbis);
		menubar.add(lblFilter);
		menubar.add(kategorieauswahl);

		this.add(menubar, BorderLayout.PAGE_START);
		this.add(tasktable, BorderLayout.CENTER);
		this.add(taskdialog, BorderLayout.EAST);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("btnAdd")) {
			try {
				if (this.taskdialog.getEingabeTask() != null) {
					this.tasktable.addTask((this.taskdialog.getEingabeTask()));
					DatabaseHelper.saveData(this.taskdialog.getEingabeTask());
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("refresh")) {
			
		}
	}
}
