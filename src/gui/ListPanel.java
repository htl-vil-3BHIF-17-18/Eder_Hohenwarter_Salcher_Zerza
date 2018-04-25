package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bll.Task;

public class ListPanel extends JPanel {

	private String[] titles =null;
	private DefaultTableModel model = null;
	private JTable table = null;
	
	public ListPanel() {
		this.setLayout(new BorderLayout ());
		initializeControls();
		this.setVisible(true);	
	}
	
	public void initializeControls() {
		this.titles = new String[]{ "Kategorie", "Fach", "Beschreibung", "Von","Bis","Erledigt" };
		this.model = new DefaultTableModel( titles, 0 );
		this.table = new JTable( model );
		this.add(table, BorderLayout.CENTER);
		
	}
}
