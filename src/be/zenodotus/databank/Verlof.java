package be.zenodotus.databank;



public class Verlof {
	
	private int id;
	private int jaar;
	private int maand;
	private int dag;
	private String verlofsoort;
	private String urental;
	private boolean nieuw;
	
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getJaar() {
		return jaar;
	}
	public void setJaar(int jaar) {
		this.jaar = jaar;
	}
	public int getMaand() {
		return maand;
	}
	public void setMaand(int maand) {
		this.maand = maand;
	}
	public int getDag() {
		return dag;
	}
	public void setDag(int dag) {
		this.dag = dag;
	}
	public String getVerlofsoort() {
		return verlofsoort;
	}
	public void setVerlofsoort(String verlofsoort) {
		this.verlofsoort = verlofsoort;
	}
	public String getUrental() {
		return urental;
	}
	public void setUrental(String urental) {
		this.urental = urental;
	}
	public boolean isNieuw() {
		return nieuw;
	}
	public void setNieuw(boolean nieuw) {
		this.nieuw = nieuw;
	}
	

}
