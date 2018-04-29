package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import bll.Task;

public class ListPanel extends JPanel {

	private String[] titles = null;
	private DefaultTableModel model = null;
	private JScrollPane scroll = null;
	private JTable table = null;
	private Object[] taskArray = null;

	public ListPanel() {
		initializeControls();
		this.setVisible(true);
	}

	public void initializeControls() {

		this.titles = new String[] { "Kategorie", "Fach", "Beschreibung", "Von", "Bis", "Erledigt" };

		this.model = new DefaultTableModel(titles, 0) {
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Date.class;
				case 4:
					return Date.class;
				default:
					return Boolean.class;
				}
			}
		};
	
		this.table = new JTable(model);
		
		this.scroll = new JScrollPane(this.table);

		this.setLayout(new BorderLayout());
		this.add(this.scroll, BorderLayout.CENTER);


	}

	public void addTask(Task t) {
		this.taskArray = new Object[] { t.getKategorie().toString(), t.getFach(), t.getBeschreibung(), t.getVon(), t.getBis(),
				t.getIsDone() };
		this.model.addRow(this.taskArray);
		}
}
