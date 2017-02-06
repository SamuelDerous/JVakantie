package be.zenodotus.jvakantie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import be.zenodotus.data.Rekenen;
import be.zenodotus.data.Totalen;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;
import android.app.Activity;
import android.os.Bundle;
import android.widget.GridLayout;
import android.widget.TextView;

public class BerekeningenActivity extends Activity {
	
	private List<TextView> txtSoorten;
	private List<TextView> txtUren;
	private GridLayout grid;
	private int jaar;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_berekeningen);
		grid = (GridLayout) findViewById(R.id.gridHoeveelheid);
		txtUren = new ArrayList<TextView>();
		txtSoorten = new ArrayList<TextView>();
		Bundle datum = getIntent().getExtras();
		jaar = datum.getInt("JAAR");
		berekenUren();
		vulGrid();
		
	}
	
	
	
	public void vulGrid() {
		for(int i = 0; i < txtSoorten.size(); i++) {
			GridLayout.LayoutParams gridLayoutParams1 = new GridLayout.LayoutParams();
			GridLayout.LayoutParams gridLayoutParams2 = new GridLayout.LayoutParams();
			gridLayoutParams1.rowSpec = GridLayout.spec(i);
			gridLayoutParams1.width = 200;
			gridLayoutParams1.columnSpec = GridLayout.spec(0);
			txtSoorten.get(i).setLayoutParams(gridLayoutParams1);
			grid.addView(txtSoorten.get(i));
			gridLayoutParams2.rowSpec = GridLayout.spec(i);
			gridLayoutParams2.columnSpec = GridLayout.spec(1);
			txtUren.get(i).setLayoutParams(gridLayoutParams2);
			grid.addView(txtUren.get(i));
			
		}
	}
	
		
	public void berekenUren() {
		
		List<Rekenen> berekening = new Totalen(this, jaar).berekenUren();
		for(int i = 0; i < berekening.size(); i++) {
			txtUren.add(new TextView(this));
			txtUren.get(i).setTextSize(24);
			txtUren.get(i).setText(berekening.get(i).totaal());
			txtSoorten.add(new TextView(this));
			txtSoorten.get(i).setTextSize(24);
			txtSoorten.get(i).setText(berekening.get(i).getSoort());
			
			
		}
	}
}
