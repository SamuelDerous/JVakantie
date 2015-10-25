package be.zenodotus.jvakantie;

import java.util.ArrayList;
import java.util.List;

import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

public class SoortenActivity extends Activity {
	
	List<EditText> txtSoorten, txtUren;
	List<Button> btnVerwerken;
	List<Verlofsoort> verlofsoorten;
	GridLayout grid;
	VerlofsoortDao dao;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_soorten);
		grid = (GridLayout) findViewById(R.id.gridSoorten);
		txtSoorten = new ArrayList<EditText>();
		txtUren = new ArrayList<EditText>();
		btnVerwerken = new ArrayList<Button>();
		maakViews();
		vulGrid();
		
		
	}
	
	private void maakViews() {
		dao = new VerlofsoortDao(this);
		dao.open();
		verlofsoorten = dao.getAlleSoorten();
		dao.close();
		for(int i = 0; i < verlofsoorten.size(); i++) {
			final Verlofsoort verlofsoort = verlofsoorten.get(i);
			txtSoorten.add(new EditText(SoortenActivity.this));
			txtUren.add(new EditText(this));
			btnVerwerken.add(new Button(this));
			txtSoorten.get(i).setText(verlofsoort.getSoort());
			txtUren.get(i).setText(verlofsoort.getUren());
			btnVerwerken.get(i).setText("Bewerken");
			btnVerwerken.get(i).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					dao.open();
					dao.updateSoort(verlofsoort);
					dao.close();
					Toast.makeText(SoortenActivity.this, "Verlofsoort aangepast", Toast.LENGTH_LONG).show();
					
				}
				
			});
			
		}
	}
	
	private void vulGrid() {
		for(int i = 0; i < txtSoorten.size(); i++) {
			GridLayout.LayoutParams gridLayoutParams1 = new GridLayout.LayoutParams();
			GridLayout.LayoutParams gridLayoutParams2 = new GridLayout.LayoutParams();
			GridLayout.LayoutParams gridLayoutParams3 = new GridLayout.LayoutParams();
			gridLayoutParams1.rowSpec = GridLayout.spec(i);
			gridLayoutParams1.columnSpec = GridLayout.spec(0);
			txtSoorten.get(i).setLayoutParams(gridLayoutParams1);
			grid.addView(txtSoorten.get(i));
			gridLayoutParams2.rowSpec = GridLayout.spec(i);
			gridLayoutParams2.columnSpec = GridLayout.spec(1);
			txtUren.get(i).setLayoutParams(gridLayoutParams2);
			grid.addView(txtUren.get(i));
			gridLayoutParams3.columnSpec = GridLayout.spec(2);
			gridLayoutParams3.rowSpec = GridLayout.spec(i);
			btnVerwerken.get(i).setLayoutParams(gridLayoutParams3);
			grid.addView(btnVerwerken.get(i));
		}
	}
	
	
}