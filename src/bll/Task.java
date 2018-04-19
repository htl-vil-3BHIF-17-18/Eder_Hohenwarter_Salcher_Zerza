package bll;

import java.util.Date;

public class Task {
	
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

	public boolean getIsDone() {
		return this.isDone;
	}
	
}
