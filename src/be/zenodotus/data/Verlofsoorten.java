package be.zenodotus.data;

import java.util.ArrayList;

import be.zenodotus.databank.Verlofsoort;

public class Verlofsoorten {

	private ArrayList<Verlofsoort> soorten;
	int jaar;
	
	public Verlofsoorten(int jaar) {
		this.jaar = jaar;
		maakLijsten();
	}
	
	private void maakLijsten() {
		Verlofsoort verlofsoort = new Verlofsoort();
		verlofsoort.setSoort("55");
		verlofsoort.setUren("230:24");
		verlofsoort.setJaar(jaar);
		soorten.add(verlofsoort);
		verlofsoort.setSoort("AN");
		verlofsoort.setUren("19:12");
		verlofsoort.setJaar(jaar);
		soorten.add(verlofsoort);
		verlofsoort.setSoort("C");
		verlofsoort.setUren("76:48");
		verlofsoort.setJaar(jaar);
		soorten.add(verlofsoort);
		verlofsoort.setSoort("CV");
		verlofsoort.setUren("12:48");
		verlofsoort.setJaar(jaar);
		soorten.add(verlofsoort);
		verlofsoort.setSoort("JV");
		verlofsoort.setUren("128:00");
		verlofsoort.setJaar(jaar);
		soorten.add(verlofsoort);
		
		
	}
	
	public ArrayList<Verlofsoort> getVerlofsoorten() {
		return soorten;
	}
}
