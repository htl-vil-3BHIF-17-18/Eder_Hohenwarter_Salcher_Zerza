package bll;

import java.util.Date;

public class Task {
	private int id=0;
	private Kategorie kategorie = null;
	private String fach="";
	private String beschreibung="";
	private Date von =null;
	private Date bis= null;
	private boolean isDone=false; 
	
	//boolean muss für checkbox abgeprüft werden! 
	
	@Override
	public String toString() {
		return "Kategorie: " + kategorie + ", Fach: " + fach + ", Beschreibung: " + beschreibung + ", Von:" + von
				+ ", Bis: " + bis;
	}
	
	public Task(Kategorie kategorie, String fach, String beschreibung, Date von, Date bis, boolean isDone) {
		super();
		this.kategorie = kategorie;
		this.fach = fach;
		this.beschreibung = beschreibung;
		this.von = von;
		this.bis = bis;
		this.isDone = isDone;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id=id;
	}

	public boolean getIsDone() {
		return this.isDone;
	}

	public Kategorie getKategorie() {
		return kategorie;
	}

	public String getFach() {
		return fach;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public Date getVon() {
		return von;
	}

	public Date getBis() {
		return bis;
	}
	
	
}
