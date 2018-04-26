package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bll.Task;

public class ListPanel extends JTable {

	private String[] titles =null;
	private DefaultTableModel model = null;
	
	public ListPanel() {
		initializeControls();
		this.setVisible(true);	
	}
	
	public void initializeControls() {
		this.titles = new String[]{ "Kategorie", "Fach", "Beschreibung", "Von","Bis","Erledigt" };
		this.model.setColumnIdentifiers(this.titles);
	}
}
