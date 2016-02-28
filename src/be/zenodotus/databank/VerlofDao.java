package be.zenodotus.databank;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class VerlofDao {

	private SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	private final Context context;
	
	private static final String TABLE_VERLOF = "tblVerlof";
	
	private static final String VERLOF_ID = "id";
	private static final String VERLOF_JAAR = "jaar";
	private static final String VERLOF_MAAND = "maand";
	private static final String VERLOF_DAG = "dag";
	private static final String VERLOF_SOORT = "verlofsoort";
	private static final String VERLOF_UREN = "urental";
	private static final String VERLOF_NIEUW = "nieuw";
	
	public VerlofDao(Context context) {
		this.context = context;
	}
	
	public VerlofDao open() throws SQLException {
		dbHelper = new DatabaseHandler(context);
		db = dbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() throws SQLException {
		db.close();
	}
	
	public long toevoegenVerlof(Verlof verlof) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(VERLOF_JAAR, verlof.getJaar());
		initialValues.put(VERLOF_MAAND, verlof.getMaand());
		initialValues.put(VERLOF_DAG, verlof.getDag());
		initialValues.put(VERLOF_SOORT, verlof.getVerlofsoort());
		initialValues.put(VERLOF_UREN, verlof.getUrental());
		initialValues.put(VERLOF_NIEUW, "1");
		
		return db.insert(TABLE_VERLOF, null, initialValues);
			
	}
	
	public long verwijderenVerlof(Verlof verlof) {
		String where = VERLOF_JAAR + " = " + verlof.getJaar() + " and " + VERLOF_MAAND + " = " + verlof.getMaand() +
				" and " + VERLOF_DAG + " = " + verlof.getDag();
		return db.delete(TABLE_VERLOF, where, null);
	}
	
	public ArrayList<Verlof> getAlleVerlovenPerJaar(int jaar, int maand) {
		ArrayList<Verlof> verlofLijst = new ArrayList<Verlof>();
		String selectQuery = "Select * from " + TABLE_VERLOF + " where jaar = " + jaar + " and maand = " + maand;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				int nieuw = Integer.parseInt(cursor.getString(6));
				Verlof verlof = new Verlof();
				verlof.setId(Integer.parseInt(cursor.getString(0)));
				verlof.setJaar(Integer.parseInt(cursor.getString(1)));
				verlof.setMaand(Integer.parseInt(cursor.getString(2)));
				verlof.setDag(Integer.parseInt(cursor.getString(3)));
				verlof.setUrental(cursor.getString(4));
				verlof.setVerlofsoort(cursor.getString(5));
				if (nieuw == 1) {
					verlof.setNieuw(true);
				} else {
					verlof.setNieuw(false);
				}
				
				verlofLijst.add(verlof);
			} while (cursor.moveToNext());
		}
		return verlofLijst;
	}
	
	public ArrayList<Verlof> getAlleVerlovenPerJaar(int jaar) {
		ArrayList<Verlof> verlofLijst = new ArrayList<Verlof>();
		String selectQuery = "Select * from " + TABLE_VERLOF + " where jaar = " + jaar + ";";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				int nieuw = Integer.parseInt(cursor.getString(6));
				Verlof verlof = new Verlof();
				verlof.setId(Integer.parseInt(cursor.getString(0)));
				verlof.setJaar(Integer.parseInt(cursor.getString(1)));
				verlof.setMaand(Integer.parseInt(cursor.getString(2)));
				verlof.setDag(Integer.parseInt(cursor.getString(3)));
				verlof.setUrental(cursor.getString(4));
				verlof.setVerlofsoort(cursor.getString(5));
				if (nieuw == 1) {
					verlof.setNieuw(true);
				} else {
					verlof.setNieuw(false);
				}
				
				verlofLijst.add(verlof);
			} while (cursor.moveToNext());
		}
		return verlofLijst;
	}
	
	public ArrayList<Verlof> getAlleVerlovenPerJaarPerSoort(int jaar, String soort) {
		ArrayList<Verlof> verlofLijst = new ArrayList<Verlof>();
		String selectQuery = "Select * from " + TABLE_VERLOF + " where jaar = " + jaar + " and verlofsoort = '" + soort + "';";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				int nieuw = Integer.parseInt(cursor.getString(6));
				Verlof verlof = new Verlof();
				verlof.setId(Integer.parseInt(cursor.getString(0)));
				verlof.setJaar(Integer.parseInt(cursor.getString(1)));
				verlof.setMaand(Integer.parseInt(cursor.getString(2)));
				verlof.setDag(Integer.parseInt(cursor.getString(3)));
				verlof.setUrental(cursor.getString(4));
				verlof.setVerlofsoort(cursor.getString(5));
				if (nieuw == 1) {
					verlof.setNieuw(true);
				} else {
					verlof.setNieuw(false);
				}
				
				verlofLijst.add(verlof);
			} while (cursor.moveToNext());
		}
		return verlofLijst;
	}
	
	public ArrayList<Verlof> getVerlofPerDag(int jaar, int maand, int dag) {
		ArrayList<Verlof> verlofLijst = new ArrayList<Verlof>();
		String selectQuery = "Select * from " + TABLE_VERLOF + " where jaar = " + jaar + " and maand = " + maand + " and dag = " + dag + ";";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				int nieuw = Integer.parseInt(cursor.getString(6));
				Verlof verlof = new Verlof();
				verlof.setId(Integer.parseInt(cursor.getString(0)));
				verlof.setJaar(Integer.parseInt(cursor.getString(1)));
				verlof.setMaand(Integer.parseInt(cursor.getString(2)));
				verlof.setDag(Integer.parseInt(cursor.getString(3)));
				verlof.setUrental(cursor.getString(4));
				verlof.setVerlofsoort(cursor.getString(5));
				if (nieuw == 1) {
					verlof.setNieuw(true);
				} else {
					verlof.setNieuw(false);
				}
				
				verlofLijst.add(verlof);
			} while (cursor.moveToNext());
		}
		return verlofLijst;
	}
	

}
