package be.zenodotus.databank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class VerlofsoortDao {

	private SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	private final Context context;
	
	private static final String TABLE_SOORT_VERLOF = "tblSoortVerlof";
	
	private static final String SOORT_ID = "id";
	private static final String SOORT_SOORT = "soort";
	private static final String SOORT_UREN = "uren";
	private static final String SOORT_JAAR = "jaar";
	
	public VerlofsoortDao(Context context) {
		this.context = context;
	}
	
	public VerlofsoortDao open() throws SQLException {
		dbHelper = new DatabaseHandler(context);
		db = dbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() throws SQLException {
		db.close();
	}
	
	public long toevoegenSoort(Verlofsoort soort) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(SOORT_SOORT, soort.getSoort());
		initialValues.put(SOORT_UREN, soort.getUren());
		initialValues.put(SOORT_JAAR, soort.getJaar());
		
		return db.insert(TABLE_SOORT_VERLOF, null, initialValues);
			
	}
	
	public ArrayList<Verlofsoort> getAlleSoorten() {
		ArrayList<Verlofsoort> soortenLijst = new ArrayList<Verlofsoort>();
		String selectQuery = "Select * from " + TABLE_SOORT_VERLOF;
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Verlofsoort soort = new Verlofsoort();
				soort.setId(Integer.parseInt(cursor.getString(0)));
				soort.setSoort(cursor.getString(1));
				soort.setUren(cursor.getString(2));
				
				soortenLijst.add(soort);
			} while (cursor.moveToNext());
		}
		return soortenLijst;
	}
	
	public ArrayList<Verlofsoort> getAlleSoortenPerJaar(int jaar) {
		ArrayList<Verlofsoort> soortenLijst = new ArrayList<Verlofsoort>();
		String selectQuery = "Select * from " + TABLE_SOORT_VERLOF + " where jaar = " + jaar;
		
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Verlofsoort soort = new Verlofsoort();
				soort.setId(Integer.parseInt(cursor.getString(0)));
				soort.setSoort(cursor.getString(1));
				soort.setUren(cursor.getString(2));
				
				soortenLijst.add(soort);
			} while (cursor.moveToNext());
		}
		return soortenLijst;
	}
	
	public long updateSoort(Verlofsoort soort) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(SOORT_SOORT, soort.getSoort());
		initialValues.put(SOORT_UREN, soort.getUren());
		String where = "id = " + soort.getId();
		
		return db.update(TABLE_SOORT_VERLOF, initialValues, where, null);
	}
	
	public long verwijderSoort(Verlofsoort soort) {
		String where = "id = " + soort.getId();
		
		return db.delete(TABLE_SOORT_VERLOF, where, null);
	}
	
	
	
}
