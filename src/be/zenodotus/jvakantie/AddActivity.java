package be.zenodotus.jvakantie;

import java.util.ArrayList;
import java.util.List;

import be.zenodotus.data.Rekenen;
import be.zenodotus.data.Totalen;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.VerlofsoortDao;
import be.zenodotus.databank.Verlofsoort;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddActivity extends Activity {
	
	Spinner spVerlofsoort;
	EditText txtUren;
	Button btnToevoegen;
	int jaar, maand, dag;
	VerlofDao dao;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		Bundle datum = getIntent().getExtras();
		spVerlofsoort = (Spinner) findViewById(R.id.spVerlofsoort);
		txtUren = (EditText) findViewById(R.id.txtUren);
		jaar = maand = dag = 0;
		if(datum != null) {
			jaar = datum.getInt("JAAR");
			maand = datum.getInt("MAAND");
			dag = datum.getInt("DAG");
		} 
		dao = new VerlofDao(this);
		
		btnToevoegen = (Button) findViewById(R.id.btnToevoegen);
		btnToevoegen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean isVerlofOk = true;
				Verlof verlof = new Verlof();
				verlof.setDag(dag);
				verlof.setMaand(maand);
				verlof.setJaar(jaar);
				verlof.setUrental(txtUren.getText().toString());
				verlof.setVerlofsoort(spVerlofsoort.getSelectedItem().toString());
				List<Rekenen> berekeningen = new Totalen(AddActivity.this, jaar).berekenUren();
				for(int i = 0; i < berekeningen.size(); i++) {
					if (berekeningen.get(i).getSoort().equals(verlof.getVerlofsoort())) {
							if(!berekeningen.get(i).aftrekken(verlof.getUrental())) {
								Toast.makeText(AddActivity.this, "Het verlofquota staat niet in de juiste vorm (0:00)", Toast.LENGTH_LONG).show();
								isVerlofOk = false;
								break;
							} else {
							if(berekeningen.get(i).getUren() < 0) {
								Toast.makeText(AddActivity.this, "Het verlofquota is overschreden", Toast.LENGTH_LONG).show();
								isVerlofOk = false;
								break;
							}
							}
					}
					
				}
				if(isVerlofOk) {
					dao.open();
					dao.toevoegenVerlof(verlof);
					dao.close();
					Toast.makeText(AddActivity.this, "Verlof toegevoegd", Toast.LENGTH_LONG).show();
					txtUren.setText("");
					Intent returnIntent = new Intent();
					returnIntent.putExtra("result", maand);
					setResult(2, returnIntent);
					finish();
				}
				
			}
			
		});
		VerlofsoortDao verlofsoortDao = new VerlofsoortDao(this);
		verlofsoortDao.open();
		ArrayList<Verlofsoort> verlofsoorten = verlofsoortDao.getAlleSoortenPerJaar(jaar);
		ArrayList<String> verlofsoort = new ArrayList<String>();
		
		for(int i = 0; i < verlofsoorten.size(); i++) {
			verlofsoort.add(verlofsoorten.get(i).getSoort());
		}
		verlofsoortDao.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, verlofsoort);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spVerlofsoort.setAdapter(adapter);
		
	}
}