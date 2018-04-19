package dal;

import java.sql.*;
import java.util.ArrayList;

import bll.Task;
import bll.Tasks;

public class DatabaseHelper {
	public static ArrayList<Task> loadData() {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task s = null;
		ArrayList<Task> studentarray=new ArrayList<Task>();
		java.util.Date datum=new java.util.Date();

		
		try {

			/* Step 1 Registrieren des Treibers */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/* Step 2 */
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@192.168.128.152:1521:ora11g");
			/* Step 3 */
			stmt_Select = con.createStatement();
			/* Step 4 */
			rs = stmt_Select.executeQuery("SELECT * FROM Tasks");

			while (rs.next()) {
				/* Step 5 */
				s = new Task(rs.getString(1), rs.getString(2), rs.getString(3), datum.parse(rs.getDate(4).toString()), rs.getDate(5), rs.getString(6));
				studentarray.add(s);
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
		
		return studentarray;
	}

	public static void saveData(ArrayList<Task> studentarray) {
		Connection con = null;
		Statement stmt_Insert = null;
		ResultSet rs = null;

		try {
			/* Step 1 Registrieren des Treibers */
			Class.forName("oracle.jdbc.driver.OracleDriver");
			/* Step 2 */
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@192.168.128.152:1521:ora11g");

			/* Step 3 */
			stmt_Insert = con.createStatement();
			/* Step 4 */
			
			for(Task s : studentarray) {
				rs = stmt_Insert.executeQuery("INSERT INTO schueler VALUES(" + s.getKatalognummer() + ", '" + s.getVorname() +"', '" + s.getNachname() +"')");
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
