package be.zenodotus.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.Context;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;

public class Totalen {
	
	private Context context;
	private VerlofsoortDao verlofsoortDao;
	private VerlofDao verlofDao;
	private ArrayList<Verlofsoort> verlofsoorten;
	private ArrayList<Verlof> verloven;
	private List<Rekenen> berekeningen;
	private int jaar;
	private Calendar cal;

	
	public Totalen(Context context, int jaar) {
		this.context = context;
		
		this.jaar = jaar;
		berekeningen = new ArrayList<Rekenen>();
		
	}
	
	public void getSoorten() {
		verlofsoortDao = new VerlofsoortDao(context);
		verlofsoortDao.open();
		verlofsoorten = verlofsoortDao.getAlleSoortenPerJaar(jaar);
		verlofsoortDao.close();
	}
	
public List<Rekenen> berekenUren() {
		
		getSoorten();
		verlofDao = new VerlofDao(context);
		verlofDao.open();
		Rekenen berekening;
		for(int i = 0; i < verlofsoorten.size(); i++) {
			verloven = verlofDao.getAlleVerlovenPerJaarPerSoort(jaar, verlofsoorten.get(i).getSoort());
			berekening = new Rekenen(verlofsoorten.get(i).getUren(), verlofsoorten.get(i).getSoort());
			for(int j = 0; j < verloven.size(); j++) {
				berekening.aftrekken(verloven.get(j).getUrental());
			}
			berekeningen.add(berekening);

		}
		verlofDao.close();
		return berekeningen;
}
	
	
	

}
