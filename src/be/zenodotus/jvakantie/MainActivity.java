package be.zenodotus.jvakantie;






import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import be.zenodotus.data.BWBelgischeFeestdagen;
import be.zenodotus.databank.Feestdag;
import be.zenodotus.databank.FeestdagDao;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	GridView gridView;
	
	
	
	private TextView txtMaand;
	private ImageButton btnTerug, btnVerder;
	private int[] dagen = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
	private String[] weekdagen = {"ZO", "ZO", "MA", "DI", "WO", "DO", "VR", "ZA"};
	private GregorianCalendar kal;
	private List<Feestdag> feestdagen;
	private FeestdagDao dao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		kal = new GregorianCalendar();
		if (kal.isLeapYear(kal.get(GregorianCalendar.YEAR))) {
			dagen[1] = 29;
		}
		dao = new FeestdagDao(this);
		dao.open();
		feestdagen = dao.getFeestdagenperJaar(kal.get(GregorianCalendar.YEAR));
		if (feestdagen.size() == 0) {
			BWBelgischeFeestdagen maakFeestdagen = new BWBelgischeFeestdagen(kal.get(GregorianCalendar.YEAR));
			List<Feestdag> maken = maakFeestdagen.getFeestdagen();
			for(int i = 0; i < maken.size(); i++) {
				dao.toevoegenFeestdag(maken.get(i));
			}
		}
		dao.close();
		vulMaand();
		setKalender();
	}

	private void vulMaand() {
		SimpleDateFormat kalFormat = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
		txtMaand = (TextView) findViewById(R.id.txtMaand);
		txtMaand.setText(kalFormat.format(kal.getTime()));
		btnTerug = (ImageButton) findViewById(R.id.btnTerug);
		btnVerder = (ImageButton) findViewById(R.id.btnVerder);
		
		btnTerug.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setVorigeMaand();
				
				
			}
		});
		
		btnVerder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setVolgendeMaand();
				
				
			}
		});
		
	}
	
	
	
	@Override
	public void onRestart() {
		super.onRestart();
		finish();
		startActivity(getIntent());
		
	}
	
	public void setVolgendeMaand() {
		if(kal.get(Calendar.MONTH)== kal.getActualMaximum(Calendar.MONTH)) {
			kal.set((kal.get(Calendar.YEAR)+ 1), kal.getActualMinimum(Calendar.MONTH), 1);
		} else {
			kal.set(Calendar.MONTH, kal.get(Calendar.MONTH)+ 1);
			
		}
		setKalender();
		
	}
	
	public void setVorigeMaand() {
		if(kal.get(Calendar.MONTH)== kal.getActualMinimum(Calendar.MONTH)) {
			kal.set((kal.get(Calendar.YEAR)- 1) , kal.getActualMaximum(Calendar.MONTH), 1);
		} else {
			kal.set(Calendar.MONTH, kal.get(Calendar.MONTH) - 1);
		}
		setKalender();
		
	}
	
	public void setKalender() {
		
	int maand = kal.get(Calendar.MONTH);
	int jaar = kal.get(Calendar.YEAR);
	Calendar dag = Calendar.getInstance();
	dag.set(jaar, maand, 1);
	
	vulMaand();
	String[] items = new String[dagen[maand]];
	VerlofDao dao = new VerlofDao(this);
	dao.open();		
	ArrayList<Verlof> verloflijst = dao.getAlleVerlovenPerJaar(jaar, maand);
	dao.close();
	for(int i = 0; i < dagen[maand]; i++) {
		dag.set(Calendar.DAY_OF_MONTH, i + 1);
		
		items[i] = weekdagen[dag.get(Calendar.DAY_OF_WEEK)] + "\t " + (i + 1) + "\t";
		for(int j = 0; j < verloflijst.size(); j++) {
			if ((verloflijst.get(j).getDag() == i)) {
				items[i] += " " + verloflijst.get(j).getVerlofsoort() + " " + verloflijst.get(j).getUrental();
				if (verloflijst.size() > 1) {
						items[i] += "\n\t\t\t\t\t";
				}
			}
		}
		
		
	}
	
	gridView = (GridView) findViewById(R.id.gridView1);

	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_list_item_1, items);

	adapter.notifyDataSetChanged();
	gridView.setAdapter(adapter);

	gridView.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v,
			int position, long id) {
		   
			Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
			addIntent.putExtra("JAAR", kal.get(Calendar.YEAR));
			addIntent.putExtra("MAAND", kal.get(Calendar.MONTH));
			addIntent.putExtra("DAG", position);
			MainActivity.this.startActivity(addIntent);
			
		}
	});
	
	
	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()) {
		case R.id.mnuToevoegenSoort:
			Intent addIntent = new Intent(this, AddSoort.class);
			this.startActivity(addIntent);
			break;
		case R.id.mnuSoorten:
			Intent soortenIntent = new Intent(this, SoortenActivity.class);
			this.startActivity(soortenIntent);
			break;
		case R.id.mnuBerekeningen:
			Intent berekeningenIntent = new Intent(this, BerekeningenActivity.class);
			this.startActivity(berekeningenIntent);
			break;
		
		case R.id.action_settings:
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			this.startActivity(settingsIntent);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}
}
