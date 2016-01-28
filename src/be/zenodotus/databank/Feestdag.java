package be.zenodotus.databank;

import java.util.Calendar;

public class Feestdag {
	
	private int id;
	private String feestdag;
	private int jaar, dag, maand;
	
	
	public String getFeestdag() {
		return feestdag;
	}
	public void setFeestdag(String feestdag) {
		this.feestdag = feestdag;
	}
	
	
	public int getJaar() {
		return jaar;
	}
	public void setJaar(int jaar) {
		this.jaar = jaar;
	}
	public int getDag() {
		return dag;
	}
	public void setDag(int dag) {
		this.dag = dag;
	}
	public int getMaand() {
		return maand;
	}
	public void setMaand(int maand) {
		this.maand = maand;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	

}
