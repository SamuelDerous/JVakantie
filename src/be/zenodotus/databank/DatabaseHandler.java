package be.zenodotus.databank;

import java.util.GregorianCalendar;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private SQLiteDatabase db;
	
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "Verlof";
	
	//tabellen
	private static final String TABLE_FEESTDAGEN= "tblFeestdagen";
	private static final String TABLE_SOORT_VERLOF = "tblSoortVerlof";
	private static final String TABLE_VERLOF = "tblVerlof";
	private static final String TABLE_WERKDAGEN = "tblWerkdagen";
	private static final String TABLE_PERSONAL = "tblPersonal";
	
	//records tblpersonal
	private static final String PERSONAL_ID = "id";
	private static final String PERSONAL_NAAM = "naam";
	private static final String PERSONAL_ADRES = "adres";
	private static final String PERSONAL_TELEFOON = "telefoon";
	private static final String PERSONAL_EMAIL = "email";
	private static final String PERSONAL_WERKGEVER = "werkgever";
	private static final String PERSONAL_WGADRES = "werkgeversadres";
	private static final String PERSONAL_WGTELEFOON = "werkgeverstelefoon";
	private static final String PERSONAL_WGEMAIL = "werkgeversemail";
	
	// records tblFeestdagen
	private static final String FEEST_ID = "volgnummer";
	private static final String FEEST_FEESTDAG = "feestdag";
	private static final String FEEST_JAAR = "jaar";
	private static final String FEEST_MAAND = "maand";
	private static final String FEEST_DAG = "dag";
	private static final String FEEST_OPGENOMEN = "opgenomen";
	
	// records tblSoortVerlof
	private static final String SOORT_ID = "id";
	private static final String SOORT_SOORT = "soort";
	private static final String SOORT_UREN = "uren";
	private static final String SOORT_JAAR = "jaar";
	
	// records tblVerlof
	private static final String VERLOF_ID = "id";
	private static final String VERLOF_JAAR = "jaar";
	private static final String VERLOF_MAAND = "maand";
	private static final String VERLOF_DAG = "dag";
	private static final String VERLOF_SOORT = "verlofsoort";
	private static final String VERLOF_UREN = "urental";
	private static final String VERLOF_NIEUW = "nieuw";

	// records tblVerkdagen
	private static final String WERK_ID = "id";
	private static final String WERK_DAG= "dag";
	private static final String WERK_WERKDAG = "werkdag";
	
	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		String createFeestdagenTable = "create table " + TABLE_FEESTDAGEN + "("
				+ FEEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + FEEST_FEESTDAG + " TEXT NOT NULL, " 
				+ FEEST_JAAR + " INTEGER NOT NULL, " + FEEST_MAAND + " INTEGER NOT NULL, " + FEEST_DAG + " INTEGER NOT NULL); ";
		String createSoortVerlofTable = "create table " + TABLE_SOORT_VERLOF + "("
				+ SOORT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SOORT_SOORT + " TEXT, "
				+ SOORT_UREN + " TEXT, " + SOORT_JAAR + " INTEGER);";
		String createVerlofTable = "create table " + TABLE_VERLOF + "(" 
				+ VERLOF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + VERLOF_JAAR + " INTEGER NOT NULL, " + VERLOF_MAAND + " INTEGER NOT NULL, " + VERLOF_DAG + " INTEGER NOT NULL, " 
				+ VERLOF_UREN + " TEXT NOT NULL, " + VERLOF_SOORT + " TEXT NOT NULL, " + VERLOF_NIEUW + " INTEGER);";
		String createWerkdagenTable = "create table " + TABLE_WERKDAGEN + "(" + WERK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ WERK_DAG + " TEXT NOT NULL, " + WERK_WERKDAG + " INTEGER);";
		String createPersonalTable = "create table " + TABLE_PERSONAL + "(" + PERSONAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
				+ PERSONAL_NAAM + " TEXT, " + PERSONAL_ADRES + " TEXT, " + PERSONAL_TELEFOON + " TEXT, " 
				+ PERSONAL_EMAIL + " TEXT, " + PERSONAL_WERKGEVER + " TEXT, " + PERSONAL_WGADRES + " TEXT, "
				+ PERSONAL_WGTELEFOON + " TEXT, " + PERSONAL_WGEMAIL + " TEXT);";
		arg0.execSQL(createFeestdagenTable);
		arg0.execSQL(createSoortVerlofTable);
		arg0.execSQL(createVerlofTable);
		arg0.execSQL(createWerkdagenTable);
		arg0.execSQL(createPersonalTable);
		String vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('MA', 1);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('DI', 0);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('WO', 1);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('DO', 1);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('VR', 1);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('ZA', 0);";
		arg0.execSQL(vulWerkdagenTable);
		vulWerkdagenTable = "insert into " + TABLE_WERKDAGEN + "(" + WERK_DAG + ", " + WERK_WERKDAG + ") VALUES ('ZO', 0);";
		String vulPersonal = "insert into " + TABLE_PERSONAL + "(" + PERSONAL_NAAM + ") VALUES ('John Doe');";
		arg0.execSQL(vulWerkdagenTable);
		arg0.execSQL(vulPersonal);
		GregorianCalendar nu = new GregorianCalendar();
		int jaar = nu.get(GregorianCalendar.YEAR);
		String vulSoorten = "insert into " + TABLE_SOORT_VERLOF + "(" + SOORT_SOORT + ", " + SOORT_UREN + ", " + SOORT_JAAR + ") values ('55', '230:24', " + jaar + ");";
		arg0.execSQL(vulSoorten);
		vulSoorten = "insert into " + TABLE_SOORT_VERLOF + "(" + SOORT_SOORT + ", " + SOORT_UREN + ", " + SOORT_JAAR + ") values ('AN', '19:12', " + jaar + ");";
		arg0.execSQL(vulSoorten);
		vulSoorten = "insert into " + TABLE_SOORT_VERLOF + "(" + SOORT_SOORT + ", " + SOORT_UREN + ", " + SOORT_JAAR + ") values ('C', '76:48', " + jaar + ");";
		arg0.execSQL(vulSoorten);
		vulSoorten = "insert into " + TABLE_SOORT_VERLOF + "(" + SOORT_SOORT + ", " + SOORT_UREN + ", " + SOORT_JAAR + ") values ('CV', '12:48', " + jaar + ");";
		arg0.execSQL(vulSoorten);
		vulSoorten = "insert into " + TABLE_SOORT_VERLOF + "(" + SOORT_SOORT + ", " + SOORT_UREN + ", " + SOORT_JAAR + ") values ('JV', '128:00', " + jaar + ");";
		arg0.execSQL(vulSoorten);
		
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		arg0.execSQL("drop table if exists " + TABLE_SOORT_VERLOF);
		arg0.execSQL("drop table if exists " + TABLE_FEESTDAGEN);
		arg0.execSQL("drop table if exists " + TABLE_VERLOF);
		arg0.execSQL("drop table if exists " + TABLE_WERKDAGEN);
		arg0.execSQL("drop table if exists " + TABLE_PERSONAL);
		onCreate(arg0);

	}
	
	public DatabaseHandler open() throws SQLException {
		db = this.getWritableDatabase();
		return this;
	}
	
	public void close() {
		this.close();
	}

}
