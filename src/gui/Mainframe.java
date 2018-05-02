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
import sun.invoke.empty.Empty;

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
	private String[] cbKategorienListe = { "Alles", "GLF", "Mitarbeitskontrolle", "Haus�bung", "PLF", "Schularbeit",
			"Schul�bung" };

	private JMenuBar menubar = null;
	private JButton btnRefresh = null;
	private Taskdialog taskdialog = null;
	private ListPanel tasktable = null;
	private DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");

	public Mainframe(String title) {
		super(title);
		this.setMinimumSize(new Dimension(800, 400));
		this.setPreferredSize(new Dimension(800, 400));
		initializeControls();

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
			try {
				if (this.taskdialog.getEingabeTask() != null) {
					DatabaseHelper.saveData(this.taskdialog.getEingabeTask(), this.tasktable);
					this.tasktable.addTask((this.taskdialog.getEingabeTask()));
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getActionCommand().equals("refresh")) {
			this.tasktable.deleteTable();

			if (!this.txtvon.getText().isEmpty()  && !this.txtbis.getText().isEmpty()) {
				try {
					this.tasktable.addListInTable(DatabaseHelper.loadData(this.kategorieauswahl.getSelectedItem().toString(),dateFormat.parse(this.txtvon.getText()), dateFormat.parse(this.txtbis.getText())));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				this.tasktable.addListInTable(DatabaseHelper.loadDataNoDate(this.kategorieauswahl.getSelectedItem().toString()));
			}

		} else if (e.getActionCommand().equals("ContexteMenuLoeschen")) {
			DatabaseHelper.deleteData(this.tasktable.getSelectedTaskIDandDeleteRow());
		}
	}
}
