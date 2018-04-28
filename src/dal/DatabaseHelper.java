package dal;

import java.sql.*;
import java.util.ArrayList;

import bll.Kategorie;
import bll.Task;

public class DatabaseHelper {
	public static ArrayList<Task> loadData() {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task s = null;
		ArrayList<Task> taskarray=new ArrayList<Task>();
		java.util.Date von=null;
		java.util.Date bis=null;

		try {

			/* Step 1 Registrieren des Treibers */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/* Step 2 */
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			/* Step 3 */
			stmt_Select = con.createStatement();
			/* Step 4 */
			rs = stmt_Select.executeQuery("SELECT * FROM Tasks");

			while (rs.next()) {
				/* Step 5 */
				Kategorie kategorie=null;
				
				if(rs.getString(1)=="Hausübung") {
					kategorie=Kategorie.Hausübung;
				}
				
				else if(rs.getString(1)=="Schularbeit") {
					kategorie=Kategorie.Schularbeit;	
				}
				
				else if(rs.getString(1)=="Schulübung") {
					kategorie=Kategorie.Schulübung;
				}
				
				else if(rs.getString(1)=="PLF") {
					kategorie=Kategorie.PLF;
				}
				
				else if(rs.getString(1)=="Mitarbeitskontrolle") {
					kategorie=Kategorie.Mitarbeitskontrolle;
				}
				
				else if(rs.getString(1)=="GLF") {
					kategorie=Kategorie.GLF;
				}
				
				von=rs.getDate(4);
				bis=rs.getDate(5);
				
				if(rs.getString(6)=="true") {
					s = new Task(kategorie, rs.getString(2), rs.getString(3), von, bis, true);
				}
				
				else if(rs.getString(6)=="false") {
					s = new Task(kategorie, rs.getString(2), rs.getString(3), von, bis, false);
				}
				
				taskarray.add(s);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				/* Step 6 */
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
