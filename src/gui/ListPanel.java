package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Console;
import java.sql.RowId;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import bll.Kategorie;
import bll.Task;
import dal.DatabaseHelper;
import javafx.scene.input.DataFormat;

public class ListPanel extends JPanel implements TableModelListener {

	private String[] titles = null;
	private DefaultTableModel model = null;
	private JScrollPane scroll = null;
	private JTable table = null;
	private Object[] taskArray = null;
	private boolean insert = true;
	private MyPopupMenu popup = null;
	private ActionListener al = null;
	private DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
	Date today = new Date();

	public ListPanel(ActionListener al) {
		this.al = al;
		initializeControls();
		this.setVisible(true);
	}

	public void initializeControls() {

		this.titles = new String[] { "ID","Kategorie", "Fach", "Beschreibung", "Von", "Bis", "Erledigt" };

		this.model = new DefaultTableModel(titles, 0) {
			  @Override
		        public boolean isCellEditable(int row, int column)
		        {
		            return column == 6;
		        }
			@Override
			public Class getColumnClass(int column) {
				switch (column) {
				case 0:
					return Integer.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return Date.class;
				case 5:
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
						String status = getModel().getValueAt(row, 6).toString();
						Date bis=(Date) getModel().getValueAt(row, 5);
						
						if (status.equalsIgnoreCase("true")) {
							c.setBackground(Color.GREEN);
						}
						if (status.equalsIgnoreCase("false")&& bis.after(today)) {
							c.setBackground(Color.ORANGE);
						}
						if (status.equalsIgnoreCase("false")&& bis.before(today)) {
							c.setBackground(Color.RED);
						}

					}
				} else {
					c.setBackground(table.getBackground());
				}
				return c;
			}
		};
		table.getTableHeader().setReorderingAllowed(false);
		table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
		table.getModel().addTableModelListener(this);
				
		popup = new MyPopupMenu(this.al);
		this.table.addMouseListener(new PopupListener(this.popup));

		this.scroll = new JScrollPane(this.table);

		this.setLayout(new BorderLayout());
		this.add(this.scroll, BorderLayout.CENTER);

	}

	public void setInsert(boolean insert) {
		this.insert = insert;
	}

	public void addTask(Task t) throws ParseException {
		if (this.insert == true) {
			this.taskArray = new Object[] { t.getId(), t.getKategorie().toString(), t.getFach(), t.getBeschreibung(),
					t.getVon(), t.getBis(), t.getIsDone() };
			this.model.addRow(this.taskArray);
		}

		this.insert = true;
	}

	public int getSelectedTaskIDandDeleteRow() {
		int id = table.getSelectedRow();
		int rgw = Integer.parseInt(this.model.getValueAt(id, 0).toString());

		this.model.removeRow(id);
		return rgw;
	}

	public Task getSelectedTask() {
		int row = table.getSelectedRow();

		int id = Integer.parseInt(this.model.getValueAt(row, 0).toString());

		Kategorie kategorie = Kategorie.valueOf(this.model.getValueAt(row, 1).toString());

		String fach = (String) this.model.getValueAt(row, 2);
		String beschreibung = (String) this.model.getValueAt(row, 3);
		Date von = (Date) this.model.getValueAt(row, 4);
		Date bis = (Date) this.model.getValueAt(row, 5);

		Boolean isDone = (Boolean) this.model.getValueAt(row, 6);

		Task t = new Task(kategorie, fach, beschreibung, von, bis, isDone);
		t.setId(id);

		return t;

	}

	public void setChangedValue(Task task) {
		int row = this.table.getSelectedRow();
		this.model.setValueAt(task.getKategorie(), row, 1);
		this.model.setValueAt(task.getFach(), row, 2);
		this.model.setValueAt(task.getBeschreibung(), row, 3);
		this.model.setValueAt(task.getVon(), row, 4);
		this.model.setValueAt(task.getBis(), row, 5);
	}

	public void addListInTable(ArrayList<Task> liste) {
		for (Task t : liste) {
			this.taskArray = new Object[] { t.getId(), t.getKategorie().toString(), t.getFach(), t.getBeschreibung(),
					t.getVon(), t.getBis(), t.getIsDone() };
			this.model.addRow(this.taskArray);
		}
	}

	public void deleteTable() {
		int rowCount = this.model.getRowCount();
		for (int i = rowCount - 1; i >= 0; i--) {
			this.model.removeRow(i);
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {

		if (e.getColumn() == 6) {
			int row = e.getFirstRow();
			DatabaseHelper.updateData((Integer) this.model.getValueAt(row, 0), (Boolean) this.model.getValueAt(row, 6));
		}

	}
}
