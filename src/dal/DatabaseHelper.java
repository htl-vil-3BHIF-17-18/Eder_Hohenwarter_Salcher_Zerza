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
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Insert = con.createStatement();		
			
			if(task != null) {
				int kat=0;
				if(task.getKategorie()==Kategorie.Hausübung){
					kat=1;
				}else if(task.getKategorie()==Kategorie.Schulübung){
					kat=2;
				}else if(task.getKategorie()==Kategorie.Schularbeit){
					kat=3;
				}else if(task.getKategorie()==Kategorie.GLF){
					kat=4;
				}else if(task.getKategorie()==Kategorie.PLF){
					kat=5;
				}else if(task.getKategorie()==Kategorie.Mitarbeitskontrolle){
					kat=6;
				}
				
				rs = stmt_Insert.executeQuery("INSERT INTO Tasks VALUES(seqTasks.NEXTVAL" + kat + ", '" + task.getFach() +"', '" + task.getBeschreibung() + "', '" + task.getVon() + "', '" + task.getBis() + "', '" + task.getIsDone() +"')");	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt_Insert.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void deleteData(Task task) {
		Connection con = null;
		Statement stmt_Insert = null;
		ResultSet rs = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Insert = con.createStatement();		
			
			if(task != null) {
				rs = stmt_Insert.executeQuery("DELETE FROM Tasks WHERE ....");	
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt_Insert.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
