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
		this.setLayout(new BorderLayout());
		initializeControls();
		this.setVisible(true);	
	}
	
	public void initializeControls() {
		titles = new String[]{ "Kategorie", "Fach", "Beschreibung", "Von","Bis","Erledigt" };
		model = new DefaultTableModel( titles, 0 );
		table = new JTable( model );
		
	}
}
