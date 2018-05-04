package dal;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import bll.Kategorie;
import bll.Task;
import gui.Infobox;
import gui.ListPanel;

public class DatabaseHelper {
	@SuppressWarnings("deprecation")
	public static ArrayList<Task> loadData(String sortKategorie, java.util.Date von, java.util.Date bis) {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task task = null;
		ArrayList<Task> taskarray = new ArrayList<Task>();
		String kategorie = null;
		Kategorie k = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			stmt_Select = con.createStatement();

			if (sortKategorie == "Alles") {
				rs = stmt_Select.executeQuery("SELECT * FROM Tasks WHERE von >= TO_DATE('"
						+ von.toLocaleString().split(" ")[0] + "', 'dd.mm.yyyy') AND bis <= TO_DATE('"
						+ bis.toLocaleString().split(" ")[0] + "', 'dd.mm.yyyy')");
			} else {
				rs = stmt_Select.executeQuery(
						"SELECT * FROM Tasks WHERE kategorie LIKE '" + sortKategorie + "' AND von > TO_DATE('"
								+ von.toLocaleString().split(" ")[0] + "', 'dd.mm.yyyy') AND bis < TO_DATE('"
								+ bis.toLocaleString().split(" ")[0] + "', 'dd.mm.yyyy')");
			}

			while (rs.next()) {
				von = rs.getDate(5);
				bis = rs.getDate(6);
				kategorie = rs.getString(2);

				if (kategorie.contains("Hausübung")) {
					k = Kategorie.Hausübung;
				} else if (kategorie.contains("Schularbeit")) {
					k = Kategorie.Schularbeit;
				} else if (kategorie.contains("Schulübung")) {
					k = Kategorie.Schulübung;
				} else if (kategorie.contains("PLF")) {
					k = Kategorie.PLF;
				} else if (kategorie.contains("Mitarbeitskontrolle")) {
					k = Kategorie.Mitarbeitskontrolle;
				} else if (kategorie.contains("GLF")) {
					k = Kategorie.GLF;
				}

				if (rs.getString(7).contains("true")) {
					task = new Task(k, rs.getString(3), rs.getString(4), von, bis, true);
				}

				else if (rs.getString(7).contains("false")) {
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
		PreparedStatement stmt_Insert = null;
		String isDone="";
		String s = "";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			stmt_Insert = con.prepareStatement("INSERT INTO Tasks VALUES(seqTasks.NEXTVAL,?,?,?,?,?,?)");
			stmt_Insert.setString(1, task.getKategorie().toString());
			stmt_Insert.setString(2, task.getFach());
			stmt_Insert.setString(3, task.getBeschreibung());
			stmt_Insert.setDate(4, new java.sql.Date(task.getVon().getTime()));
			stmt_Insert.setDate(5, new java.sql.Date(task.getBis().getTime()));
			
			if(task.getIsDone()==true) {
				isDone="true";
			}
			
			else if(task.getIsDone()==false) {
				isDone="false";
			}
			
			stmt_Insert.setString(6, isDone);
			stmt_Insert.executeQuery();

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

	public static void deleteData(int id) {
		Connection con = null;
		Statement stmt_Delete = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			stmt_Delete = con.createStatement();
			stmt_Delete.executeQuery("DELETE FROM Tasks WHERE id=" + id);
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

	public static void updateData(int id, boolean isDone) {
		Connection con = null;
		PreparedStatement stmt_Update = null;
		String doneOrNot="";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			stmt_Update = con.prepareStatement("UPDATE Tasks SET IsDone=? WHERE id=?");

			if (isDone == true) {
				doneOrNot="true";
			}

			else if (isDone == false) {
				doneOrNot="false";
			}
			
			stmt_Update.setString(1, doneOrNot);
			stmt_Update.setInt(2, id);
			stmt_Update.executeQuery();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt_Update.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void updateChangedData(Task task, int id) {
		Connection con = null;
		PreparedStatement stmt_Update = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			 
			stmt_Update=con.prepareStatement("UPDATE Tasks SET kategorie=?, fach=?, beschreibung=?, von=?, bis=? WHERE id=?");
			stmt_Update.setString(1, task.getKategorie().toString());
			stmt_Update.setString(2, task.getFach());
			stmt_Update.setString(3, task.getBeschreibung());
			stmt_Update.setDate(4, new java.sql.Date(task.getVon().getTime()));
			stmt_Update.setDate(5, new java.sql.Date(task.getBis().getTime()));
			stmt_Update.setInt(6, id);
			stmt_Update.execute();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt_Update.close();
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<Task> loadDataNoDate(String sortKategorie) {
		Connection con = null;
		Statement stmt_Select = null;
		ResultSet rs = null;
		Task task = null;
		ArrayList<Task> taskarray = new ArrayList<Task>();
		String kategorie = null;
		Kategorie k = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:d3b04/d3b@212.152.179.117:1521:ora11g");
			stmt_Select = con.createStatement();

			if (sortKategorie == "Alles") {
				rs = stmt_Select.executeQuery("SELECT * FROM Tasks");
			} else {
				rs = stmt_Select.executeQuery("SELECT * FROM Tasks WHERE kategorie LIKE '" + sortKategorie + "'");
			}

			while (rs.next()) {
				kategorie = rs.getString(2);

				if (kategorie.contains("Hausübung")) {
					k = Kategorie.Hausübung;
				} else if (kategorie.contains("Schularbeit")) {
					k = Kategorie.Schularbeit;
				} else if (kategorie.contains("Schulübung")) {
					k = Kategorie.Schulübung;
				} else if (kategorie.contains("PLF")) {
					k = Kategorie.PLF;
				} else if (kategorie.contains("Mitarbeitskontrolle")) {
					k = Kategorie.Mitarbeitskontrolle;
				} else if (kategorie.contains("GLF")) {
					k = Kategorie.GLF;
				}

				if (rs.getString(7).contains("true")) {
					task = new Task(k, rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), true);
				}

				else if (rs.getString(7).contains("false")) {
					task = new Task(k, rs.getString(3), rs.getString(4), rs.getDate(5), rs.getDate(6), false);
				}

				task.setId(rs.getInt(1));
				taskarray.add(task);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
			Infobox.infoBox("Verbindung zur Datenbank nicht möglich!", "Datenbankverbindung");
		} finally {
			
			try {
				rs.close();
				stmt_Select.close();
				con.close();
			} catch (SQLException e) {
				Infobox.infoBox("Verbindung zur Datenbank nicht möglich!", "Datenbankverbindung");
				e.printStackTrace();
			}
		}

		return taskarray;
	}
}
