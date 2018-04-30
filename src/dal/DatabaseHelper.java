package dal;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bll.Kategorie;
import bll.Task;
import gui.ListPanel;

public class DatabaseHelper {
	public static ArrayList<Task> loadData(String sortKategorie, java.util.Date von, java.util.Date bis) {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task task = null;
		ArrayList<Task> taskarray=new ArrayList<Task>();
		String kategorie=null;
		Kategorie k=null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Select = con.createStatement();
			
			if(sortKategorie=="Alles")
			{
				rs = stmt_Select.executeQuery("SELECT * FROM Tasks WHERE TO_CHAR(von,'DD.MM.YYYY') > TO_CHAR(" + von + ",'DD.MM.YYYY') AND TO_CHAR(bis,'DD.MM.YYYY') < TO_CHAR(" + bis +",'DD.MM.YYYY')");

			}else {
				rs = stmt_Select.executeQuery("SELECT * FROM Tasks WHERE kategorie LIKE " + sortKategorie + " AND TO_CHAR(von,'DD.MM.YYYY') > TO_CHAR(" + von + ",'DD.MM.YYYY') AND TO_CHAR(bis,'DD.MM.YYYY') < TO_CHAR(" + bis +",'DD.MM.YYYY')" );
			}

			while (rs.next()) {
				von=rs.getDate(5);
				bis=rs.getDate(6);
				kategorie=rs.getString(2);
				
				
				if(kategorie.contains("Hausübung"))
				{
					k=Kategorie.Hausübung;
				}else if(kategorie.contains("Schularbeit"))
				{
					k=Kategorie.Schularbeit;
				}else if(kategorie.contains("Schulübung"))
				{
					k=Kategorie.Schulübung;
				}else if(kategorie.contains("PLF"))
				{
					k=Kategorie.PLF;
				}else if(kategorie.contains("Mitarbeitskontrolle"))
				{
					k=Kategorie.Mitarbeitskontrolle;
				}else if(kategorie.contains("GLF"))
				{
					System.out.println("stimmt");
					k=Kategorie.GLF;
				}
				
				if(rs.getString(7).contains("true")) {
					task = new Task(k, rs.getString(3), rs.getString(4), von, bis, true);
				}
				
				else if(rs.getString(7).contains("false")) {
					task = new Task(k, rs.getString(3), rs.getString(4), von, bis, false);
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

	public static void saveData(Task task, ListPanel listpanel) throws ParseException {
		Connection con = null;
		Statement stmt_Insert = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd.mm.yyyy");
		String s="";

		try {
			System.out.println("f1");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("f2");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b06/d3b@212.152.179.117:1521:ora11g");
			stmt_Insert = con.createStatement();
			System.out.println(sdf.format(task.getVon()).toString());
			System.out.println(sdf.format(task.getBis()).toString());
			
			//isDone nur zum Testen vordefiniert
			task.setIsDone(false);
			
			s="true";
			
			stmt_Insert.executeQuery("INSERT INTO Tasks Values(seqTasks.NEXTVAL, '" + task.getKategorie().toString() + "', '" + task.getFach() + "', '" + task.getBeschreibung() + "', TO_DATE('" + sdf.format(task.getVon()).toString() + "'), TO_DATE('" + sdf.format(task.getBis()).toString() + "'), '" + s + "')");
			System.out.println("f3");	
		} catch (ClassNotFoundException e) {
			listpanel.setInsert(false);
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
			listpanel.setInsert(false);
		} finally {
			try {
				stmt_Insert.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
				listpanel.setInsert(false);
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
