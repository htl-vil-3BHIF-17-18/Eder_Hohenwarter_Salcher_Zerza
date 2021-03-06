package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import bll.Task;
import dal.DatabaseHelper;

public class Mainframe extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1181889914322477954L;
	// Test for push please delete
	private JLabel lblvon = null;
	private JLabel lblbis = null;
	private JLabel lblFilter = null;
	
	private JFormattedTextField txtvon = null;
	private JFormattedTextField txtbis = null;
	
	private JComboBox kategorieauswahl = null;
	private JMenuBar menubar = null;
	private JButton btnRefresh = null;
	private Taskdialog taskdialog = null;
	private ListPanel tasktable = null;
	private int selectedRow=0;
	private int selectedRowId=0;
	
	private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private String[] cbKategorienListe = { "Alles", "GLF", "Mitarbeitskontrolle", "Hausübung", "PLF", "Schularbeit",
	"Schulübung" };

	public Mainframe(String title) {
		super(title);
		this.setMinimumSize(new Dimension(800, 400));
		this.setPreferredSize(new Dimension(800, 400));

		initializeControls();
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	private void initializeControls() {
		this.setLayout(new BorderLayout());
		menubar = new JMenuBar();
		btnRefresh = new JButton("Refresh");
		btnRefresh.setActionCommand("refresh");
		btnRefresh.addActionListener(this);
		tasktable = new ListPanel(this);

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
			if (Infobox.netIsAvailable()) {
				try {
					if (this.taskdialog.getEingabeTask() != null) {
						DatabaseHelper.saveData(this.taskdialog.getEingabeTask(), this.tasktable);
						this.tasktable.addTask((this.taskdialog.getEingabeTask()));
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			} else {
				Infobox.infoBox("Keine Verbindung zum INTERRRNET!", "Internetverbindung");
			}

		} else if (e.getActionCommand().equals("refresh")) {
			if (Infobox.netIsAvailable()) {
				this.tasktable.deleteTable();

				if (!this.txtvon.getText().isEmpty() && !this.txtbis.getText().isEmpty()) {
					try {
						this.tasktable.addListInTable(DatabaseHelper.loadData(
								this.kategorieauswahl.getSelectedItem().toString(),
								dateFormat.parse(this.txtvon.getText()), dateFormat.parse(this.txtbis.getText())));
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				} else {
					this.tasktable.addListInTable(
							DatabaseHelper.loadDataNoDate(this.kategorieauswahl.getSelectedItem().toString()));
				}

				this.txtvon.setText("");
				this.txtbis.setText("");
			} else {
				Infobox.infoBox("Keine Verbindung zum INTERRRNET!", "Internetverbindung");
			}
		} else if (e.getActionCommand().equals("ContexteMenuLoeschen")) {
			if (Infobox.netIsAvailable()) {
				DatabaseHelper.deleteData(this.tasktable.getSelectedTaskIDandDeleteRow());
			} else {
				Infobox.infoBox("Keine Verbindung zur Datenbank!", "Internetverbindung");
			}
		} else if (e.getActionCommand().equals("ContexteMenuAendern")) {
			if (Infobox.netIsAvailable()) {
				Task task=this.tasktable.getSelectedTask();
				this.taskdialog.setTask(task);
				this.selectedRowId=task.getId();
				this.selectedRow=this.tasktable.getSelectedRow();
				this.taskdialog.enableAendernButton(true);
			} else {
				Infobox.infoBox("Keine Verbindung zum INTERRRNET!", "Internetverbindung");
			}

		} else if (e.getActionCommand().equals("btnAendern")) {
			if (Infobox.netIsAvailable()) {
				try {
					if (this.taskdialog.getEingabeTask() != null) {
						DatabaseHelper.updateChangedData(this.taskdialog.getEingabeTask(), this.selectedRowId);
						this.tasktable.setChangedValue(this.taskdialog.getEingabeTask(), this.selectedRow);
						this.tasktable.repaint();
						this.taskdialog.enableAendernButton(false);
						this.taskdialog.makeClear();
					}
				} catch (ParseException e1) {

					e1.printStackTrace();
				}
			} else {
				Infobox.infoBox("Keine Verbindung zum INTERRRNET!", "Internetverbindung");
			}

		}
	}

}
