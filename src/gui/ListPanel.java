package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.RowId;
import java.util.Date;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import bll.Task;

public class ListPanel extends JPanel {

	private String[] titles = null;
	private DefaultTableModel model = null;
	private JScrollPane scroll = null;
	private JTable table = null;
	private Object[] taskArray = null;
	private boolean insert=true;

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


		this.table = new JTable(model) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
			
				if (!isRowSelected(row)) {
					if (table.getColumnCount() >= 0) {
						String status = getModel().getValueAt(row, 5).toString();

						if (status.equalsIgnoreCase("true")) {
							c.setBackground(Color.GREEN);
						}
						if (status.equalsIgnoreCase("false")) {
							c.setBackground(Color.RED);
						}

					}
				} else {
					c.setBackground(table.getBackground());
				}
				return c;
			}
		};


		this.scroll = new JScrollPane(this.table);

		this.setLayout(new BorderLayout());
		this.add(this.scroll, BorderLayout.CENTER);

	}

	public void setInsert(boolean insert) {
		this.insert=insert;
	}
	
	public void addTask(Task t) {
		if(this.insert==true) {
			this.taskArray = new Object[] { t.getKategorie().toString(), t.getFach(), t.getBeschreibung(), t.getVon(),
					t.getBis(), t.getIsDone() };
			this.model.addRow(this.taskArray);
		}
		
		this.insert=true;
	}

	public void deleteTable() {
		int rowCount = this.model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
		    this.model.removeRow(i);
		}
	}

}
