package gui;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bll.Task;

public class ListPanel extends JPanel  {

	private String[] titles = null;
	private DefaultTableModel model = null;
	private JScrollPane scroll = null;
	private JTable table =null;

	public ListPanel() {
		initializeControls();
		this.setVisible(true);
	}

	public void initializeControls() {
		this.titles = new String[] { "Kategorie", "Fach", "Beschreibung", "Von", "Bis", "Erledigt" };

		this.model = new DefaultTableModel(titles,0); 
		
		this.table =new JTable(model);
		this.scroll = new JScrollPane(this.table);
		
		this.setLayout(new BorderLayout());
		this.add(this.scroll, BorderLayout.CENTER);
	}
}
