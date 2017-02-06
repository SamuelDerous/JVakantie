package be.zenodotus.databank;

public class Verlofsoort {

	private int id;
	private String soort, uren;
	private int jaar;
	
	public int getId() {
		return id;
	}

	public String getSoort() {
		return soort;
	}

	public void setSoort(String soort) {
		this.soort = soort;
	}

	public String getUren() {
		return uren;
	}

	public void setUren(String uren) {
		this.uren = uren;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public void setJaar(int jaar) {
		this.jaar = jaar;
	}
	
	public int getJaar() {
		return jaar;
	}
	
	
}
