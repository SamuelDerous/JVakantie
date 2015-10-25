package be.zenodotus.databank;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class WerkdagDao {

	private SQLiteDatabase db;
	private DatabaseHandler dbHelper;
	private final Context context;
	
	private static final String TABLE_WERKDAGEN = "tblWerkdagen";
	private static final String WERK_ID = "id";
	private static final String WERK_DAG= "dag";
	private static final String WERK_WERKDAG = "werkdag";
	
	public WerkdagDao(Context context) {
		this.context = context;
	}
	
	public WerkdagDao open() throws SQLException {
		dbHelper = new DatabaseHandler(context);
		db = dbHelper.getWritableDatabase();
		
		return this;
	}
	
	public void close() throws SQLException {
		db.close();
	}
	
	public long updateWerkdag(Werkdag werkdag) {
		ContentValues initialValues = new ContentValues();
		int iswerkdag = 0;
		if (werkdag.isWerkdag()) {
			iswerkdag = 1;
		}
		initialValues.put(WERK_DAG, werkdag.getDag());
		initialValues.put(WERK_WERKDAG, iswerkdag);
		String where = "dag = '" + werkdag.getDag() + "'";
		
		return db.update(TABLE_WERKDAGEN, initialValues, where, null);
	}
	
	
	public ArrayList<Werkdag> getWeekend() {
		ArrayList<Werkdag> weekendLijst = new ArrayList<Werkdag>();
		String selectQuery = "Select * from " + TABLE_WERKDAGEN + " where " + WERK_WERKDAG + " <> 1;";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Werkdag weekend = new Werkdag();
				weekend.setId(Integer.parseInt(cursor.getString(0)));
				weekend.setDag(cursor.getString(1));
				weekend.setWerkdag(false);
				
				weekendLijst.add(weekend);
			} while (cursor.moveToNext());
		}
		return weekendLijst;
	}
	
	public List<Werkdag> getAlleDagen() {
		List<Werkdag> werkdagen = new ArrayList<Werkdag>();
		String selectQuery = "select * from " + TABLE_WERKDAGEN + ";";
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				Werkdag werkdag = new Werkdag();
				werkdag.setId(Integer.parseInt(cursor.getString(0)));
				werkdag.setDag(cursor.getString(1));
				if (cursor.getInt(2) == 1) {
					werkdag.setWerkdag(true);
				} else {
					werkdag.setWerkdag(false);
				}
				werkdagen.add(werkdag);
			} while (cursor.moveToNext());
			
			
		}
		return werkdagen;
		
	}
	
}
