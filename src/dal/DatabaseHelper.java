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
				von=rs.getDate(5);
				bis=rs.getDate(6);
				
				if(rs.getString(7)=="true") {
					task = new Task(kategorie, rs.getString(3), rs.getString(4), von, bis, true);
				}
				
				else if(rs.getString(7)=="false") {
					task = new Task(kategorie, rs.getString(3), rs.getString(4), von, bis, false);
				}
				
				task.setId(rs.getInt(1));			
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

	public static void saveData(Task task) {
		Connection con = null;
		Statement stmt_Insert = null;

		try {
			System.out.println("f1");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("f2");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Insert = con.createStatement();
				
			stmt_Insert.executeQuery("INSERT INTO Tasks VALUES(seqTasks.NEXTVAL," + task.getKategorie().toString() + ", '" + task.getFach() +"', '" + task.getBeschreibung() + "', TO_DATE('" + task.getVon() + "', 'DD.MM.YYYY'), To_DATE('" + task.getBis() + "', 'DD.MM.YYYY'), '" + task.getIsDone() +"')");
			System.out.println("f3");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt_Insert.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteData(Task task) {
		Connection con = null;
		Statement stmt_Delete = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Delete = con.createStatement();		
			
			if(task != null) {
				stmt_Delete.executeQuery("DELETE FROM Tasks WHERE id=" + task.getId());	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt_Delete.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update() {
		
	}
}
