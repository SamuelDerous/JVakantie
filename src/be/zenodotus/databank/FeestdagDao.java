package be.zenodotus.databank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FeestdagDao {

	private SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	private final Context context;
	
	private static final String TABLE_FEESTDAGEN= "tblFeestdagen";
	
	private static final String FEEST_ID = "volgnummer";
	private static final String FEEST_FEESTDAG = "feestdag";
	private static final String FEEST_JAAR = "jaar";
	private static final String FEEST_MAAND = "maand";
	private static final String FEEST_DAG = "dag";
	private static final String FEEST_OPGENOMEN = "opgenomen";
	
	public FeestdagDao(Context context) {
		this.context = context;
	}
	
	public FeestdagDao open() throws SQLException {
		dbHelper = new DatabaseHandler(context);
		db = dbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() throws SQLException {
		db.close();
	}
	
	public long toevoegenFeestdag(Feestdag feestdag) {
			ContentValues initialValues = new ContentValues();
			initialValues.put(FEEST_JAAR, feestdag.getJaar());
			initialValues.put(FEEST_MAAND, feestdag.getMaand());
			initialValues.put(FEEST_DAG, feestdag.getDag());
			initialValues.put(FEEST_FEESTDAG, feestdag.getFeestdag());
			initialValues.put(FEEST_OPGENOMEN, 0);
			
			
			return db.insert(TABLE_FEESTDAGEN, null, initialValues);
				
		
		
	}
	
	public Feestdag getFeestdag(int jaar, int maand, int dag) {
		String selectQuery = "Select * from " + TABLE_FEESTDAGEN + " where jaar = " + jaar + " and maand = " + maand + " and dag = " + dag;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if(cursor.moveToFirst()) {
			Feestdag feestdag = new Feestdag();
			feestdag.setId(Integer.parseInt(cursor.getString(0)));
			feestdag.setJaar(Integer.parseInt(cursor.getString(2)));
			feestdag.setMaand(Integer.parseInt(cursor.getString(3)));
			feestdag.setDag(Integer.parseInt(cursor.getString(4)));
			feestdag.setFeestdag(cursor.getString(1));
			return feestdag;
		} else {
			return null;
		}
	}
	
	public List<Feestdag> getFeestdagenperJaar(int jaar) {
		
			ArrayList<Feestdag> feestdagLijst = new ArrayList<Feestdag>();
			String selectQuery = "Select * from " + TABLE_FEESTDAGEN + " where jaar = " + jaar;
			Cursor cursor = db.rawQuery(selectQuery, null);
			
			if(cursor.moveToFirst()) {
				do {
					Feestdag feestdag = new Feestdag();
					feestdag.setId(Integer.parseInt(cursor.getString(0)));
					feestdag.setJaar(Integer.parseInt(cursor.getString(2)));
					feestdag.setMaand(Integer.parseInt(cursor.getString(3)));
					feestdag.setDag(Integer.parseInt(cursor.getString(4)));
					feestdag.setFeestdag(cursor.getString(1));
									
					feestdagLijst.add(feestdag);
				} while (cursor.moveToNext());
			}
		return feestdagLijst;
	}
	
}
