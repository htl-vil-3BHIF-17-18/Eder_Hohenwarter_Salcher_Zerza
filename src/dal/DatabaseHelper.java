package dal;

import java.sql.*;
import java.util.ArrayList;

import bll.Kategorie;
import bll.Task;

public class DatabaseHelper {
	public static ArrayList<Task> loadData(Kategorie kategorie, java.util.Date von, java.util.Date bis) {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task task = null;
		ArrayList<Task> taskarray=new ArrayList<Task>();

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Select = con.createStatement();
			rs = stmt_Select.executeQuery("SELECT * FROM Tasks WHERE kategorie LIKE " + kategorie.toString() + " AND TO_CHAR(von,'DD.MM.YYYY') > TO_CHAR(" + von + ",'DD.MM.YYYY') AND TO_CHAR(bis,'DD.MM.YYYY') < TO_CHAR(" + bis +",'DD.MM.YYYY')" );

			while (rs.next()) {
				von=rs.getDate(4);
				bis=rs.getDate(5);
				
				if(rs.getString(6)=="true") {
					task = new Task(kategorie, rs.getString(2), rs.getString(3), von, bis, true);
				}
				
				else if(rs.getString(6)=="false") {
					task = new Task(kategorie, rs.getString(2), rs.getString(3), von, bis, false);
				}
				
				taskarray.add(task);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt_Select.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return taskarray;
	}

	public static void saveData(ArrayList<Task> taskarray) {
		Connection con = null;
		Statement stmt_Insert = null;
		ResultSet rs = null;
		//nfl
		try {
			/* Step 1 Registrieren des Treibers */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/* Step 2 */
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");

			/* Step 3 */
			stmt_Insert = con.createStatement();
			/* Step 4 */
			
			for(Task t : taskarray) {
				rs = stmt_Insert.executeQuery("INSERT INTO schueler VALUES(" + t.getKategorie() + ", '" + t.getFach() +"', '" + t.getBeschreibung() + "', '" + t.getVon() + "', '" + t.getBis() + "', '" + t.getIsDone() +"')");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				/* Step 6 */
				rs.close();
				stmt_Insert.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
