package be.zenodotus.jvakantie;






import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import be.zenodotus.adapters.DagenAdapter;
import be.zenodotus.creatie.GeneratePDF;
import be.zenodotus.data.BWBelgischeFeestdagen;
import be.zenodotus.data.KalenderItems;
import be.zenodotus.data.Rekenen;
import be.zenodotus.data.Totalen;
import be.zenodotus.data.Verlofsoorten;
import be.zenodotus.databank.Feestdag;
import be.zenodotus.databank.FeestdagDao;
import be.zenodotus.databank.Verlof;
import be.zenodotus.databank.VerlofDao;
import be.zenodotus.databank.Verlofsoort;
import be.zenodotus.databank.VerlofsoortDao;
import be.zenodotus.databank.Werkdag;
import be.zenodotus.databank.WerkdagDao;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
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
	private String[] strDagen;
	private List<Feestdag> feestdagen;
	private FeestdagDao dao;
	private WerkdagDao werkdagDao;
	private GregorianCalendar nu;
	private int positie;
	private float x1, x2;
	static final int MIN_DISTANCE = 150;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		kal = new GregorianCalendar();
		nu = new GregorianCalendar();
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
		VerlofsoortDao verlofsoortDao = new VerlofsoortDao(this);
		verlofsoortDao.open();
		List verlofsoorten = verlofsoortDao.getAlleSoortenPerJaar(kal.get(GregorianCalendar.YEAR));
		if(verlofsoorten.isEmpty()) {
			Verlofsoorten maakSoorten = new Verlofsoorten(kal.get(GregorianCalendar.YEAR));
			ArrayList<Verlofsoort> makenSoorten = maakSoorten.getVerlofsoorten();
			for(int i = 0; i < makenSoorten.size(); i++) {
				verlofsoortDao.toevoegenSoort(makenSoorten.get(i));
			}
		}
		verlofsoortDao.close();
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
	
		
	public void onResume() {
		super.onResume();
		setKalender();
		
		
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
	if (kal.isLeapYear(kal.get(GregorianCalendar.YEAR))) {
		dagen[1] = 29;
	}
	final KalenderItems[] items = new KalenderItems[dagen[maand]];
	strDagen = new String[dagen[maand]];
	VerlofDao verlofDao = new VerlofDao(this);
	verlofDao.open();		
	ArrayList<Verlof> verloflijst = verlofDao.getAlleVerlovenPerJaar(jaar, maand);
	verlofDao.close();
	for(int i = 0; i < dagen[maand]; i++) {
		
		dag.set(Calendar.DAY_OF_MONTH, i + 1);
		items[i] = new KalenderItems();
		items[i].setWeekdag(weekdagen[dag.get(Calendar.DAY_OF_WEEK)]);
		items[i].setDag(i + 1);
		List<Verlof> verloven = new ArrayList<Verlof>();
		for(int j = 0; j < verloflijst.size(); j++) {
			if ((verloflijst.get(j).getDag() == i)) {
				verloven.add(verloflijst.get(j));
				
			}
		}
		items[i].setVerlof(verloven);
		dao.open();
		Feestdag feestdag = dao.getFeestdag(jaar, maand, i + 1);
		if (feestdag != null) {
			items[i].setFeestdag(true);
		} else {
			items[i].setFeestdag(false);
		}
		
		
	}
	
	
	
	
	ListView lst = (ListView) findViewById(R.id.lvkalenderdagen);
	ArrayList<KalenderItems> lijst = new ArrayList<KalenderItems>(Arrays.asList(items));
	
	DagenAdapter<String> adapter = new DagenAdapter<String>(this,
			android.R.layout.simple_list_item_1, lijst, maand, jaar);
	
	lst.setAdapter(adapter);
	
	adapter.notifyDataSetChanged();
	/*lst.setOnTouchListener(new OnSwipeListener(this) {
		public void onSwipeRight() {
			setVolgendeMaand();
		}
		
		public void onSwipeLeft() {
			setVorigeMaand();
		}
	});*/

	lst.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v,
			int position, long id) {
			positie = position;
			boolean weekenddag = false;
			werkdagDao = new WerkdagDao(MainActivity.this);
			werkdagDao.open();
			List<Werkdag> weekend = werkdagDao.getWeekend();
			for(int i = 0; i < weekend.size(); i++) {
				if (weekend.get(i).getDag().equalsIgnoreCase(items[position].getWeekdag())) {
					weekenddag = true;
				}
			}
			if (!weekenddag) {
				LayoutInflater li = LayoutInflater.from(MainActivity.this);
				View promptsView = li.inflate(R.layout.activity_add, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						MainActivity.this);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final Spinner spVerlofsoort = (Spinner) promptsView
						.findViewById(R.id.spVerlofsoort);
				final EditText txtUren = (EditText) promptsView.findViewById(R.id.txtUren);
				

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("Toevoegen",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
					    	boolean isVerlofOk = true;
					    	int maand = kal.get(Calendar.MONTH);
							int jaar = kal.get(Calendar.YEAR);
							int dag = positie;
							
					    	Verlof verlof = new Verlof();
							verlof.setDag(dag);
							verlof.setMaand(maand);
							verlof.setJaar(jaar);
							verlof.setUrental(txtUren.getText().toString());
							verlof.setVerlofsoort(spVerlofsoort.getSelectedItem().toString());
							List<Rekenen> berekeningen = new Totalen(MainActivity.this, jaar).berekenUren();
							for(int i = 0; i < berekeningen.size(); i++) {
								if (berekeningen.get(i).getSoort().equals(verlof.getVerlofsoort())) {
										if(!berekeningen.get(i).aftrekken(verlof.getUrental())) {
											Toast.makeText(MainActivity.this, "Het verlofquota staat niet in de juiste vorm (0:00)", Toast.LENGTH_LONG).show();
											isVerlofOk = false;
											break;
										} else {
										if(berekeningen.get(i).getUren() < 0) {
											Toast.makeText(MainActivity.this, "Het verlofquota is overschreden", Toast.LENGTH_LONG).show();
											isVerlofOk = false;
											break;
										}
										}
								}
								
							}
							if(isVerlofOk) {
								VerlofDao verlofDao = new VerlofDao(MainActivity.this);
								verlofDao.open();
								verlofDao.toevoegenVerlof(verlof);
								verlofDao.close();
								Toast.makeText(MainActivity.this, "Verlof toegevoegd", Toast.LENGTH_LONG).show();
								txtUren.setText("");
								Intent returnIntent = new Intent();
								returnIntent.putExtra("result", maand);
								setResult(2, returnIntent);
								finish();
								
							}
							
						}
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
			
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
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			x1 = event.getX();
			break;
		case MotionEvent.ACTION_UP:
			x2 = event.getX();
			float deltaX = x2 - x1;
			if (Math.abs(deltaX) > MIN_DISTANCE) {
				if (x2 < x1) {
					setVolgendeMaand();
				}
				else {
					setVorigeMaand();
				}
			}
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch(item.getItemId()) {
		case R.id.mnuToevoegenSoort:
			Intent soortenToevoegenIntent = new Intent(this, AddSoort.class);
			
			soortenToevoegenIntent.putExtra("JAAR", kal.get(Calendar.YEAR));
			this.startActivity(soortenToevoegenIntent);
			break;
		
		case R.id.mnuSoorten:
			Intent soortenIntent = new Intent(this, SoortenActivity.class);
			soortenIntent.putExtra("JAAR", kal.get(Calendar.YEAR));
			this.startActivity(soortenIntent);
			break;
		case R.id.mnuBerekeningen:
			Intent berekeningenIntent = new Intent(this, BerekeningenActivity.class);
			berekeningenIntent.putExtra("JAAR", kal.get(Calendar.YEAR));
			this.startActivity(berekeningenIntent);
			break;
			
		case R.id.mnuPDF:
			LayoutInflater li2 = LayoutInflater.from(this);
			View promptsView2 = li2.inflate(R.layout.prompt_jaar, null);

			AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(
					this);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder2.setView(promptsView2);

			final EditText userInput = (EditText) promptsView2
					.findViewById(R.id.txtJaarInput);

			// set dialog message
			alertDialogBuilder2
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
				    	GeneratePDF nieuw = new GeneratePDF();
				    	String bestand = nieuw.vakantieAfdruk(MainActivity.this, "JacquelineVandenbroecke" + Integer.parseInt(userInput.getText().toString()) + ".pdf", Integer.parseInt(userInput.getText().toString()));
				    	Toast.makeText(MainActivity.this, "pdf " + bestand + " aangemaakt", Toast.LENGTH_LONG).show();
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog2 = alertDialogBuilder2.create();

			// show it
			alertDialog2.show();
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
