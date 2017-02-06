package be.zenodotus.databank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

// Referenced classes of package be.zenodotus.databank:
//            Personal, DatabaseHandler

public class PersonalDao
{

    private static final String PERSONAL_ADRES = "adres";
    private static final String PERSONAL_EMAIL = "email";
    private static final String PERSONAL_ID = "id";
    private static final String PERSONAL_NAAM = "naam";
    private static final String PERSONAL_TELEFOON = "telefoon";
    private static final String PERSONAL_WERKGEVER = "werkgever";
    private static final String PERSONAL_WGADRES = "werkgeversadres";
    private static final String PERSONAL_WGEMAIL = "werkgeversemail";
    private static final String PERSONAL_WGTELEFOON = "werkgeverstelefoon";
    private static final String TABLE_PERSONAL = "tblPersonal";
    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHandler dbHelper;

    public PersonalDao(Context context1)
    {
        context = context1;
    }

    public void close()
        throws SQLException
    {
        db.close();
    }

    public Personal getGegevens()
    {
        Personal personal = null;
        Cursor cursor = db.rawQuery("Select * from tblPersonal", null);
        if (cursor.moveToFirst())
        {
            personal = new Personal();
            personal.setId(cursor.getInt(0));
            personal.setNaam(cursor.getString(1));
            personal.setAdres(cursor.getString(2));
            personal.setTelefoon(cursor.getString(3));
            personal.setEmail(cursor.getString(4));
            personal.setWerkgever(cursor.getString(5));
            personal.setWgAdres(cursor.getString(6));
            personal.setWgTelefoon(cursor.getString(7));
            personal.setWgEmail(cursor.getString(8));
        }
        return personal;
    }

    public PersonalDao open()
        throws SQLException
    {
        dbHelper = new DatabaseHandler(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public int setNAW(Personal personal)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("naam", personal.getNaam());
        contentvalues.put("adres", personal.getAdres());
        contentvalues.put("telefoon", personal.getTelefoon());
        contentvalues.put("email", personal.getEmail());
        return db.update("tblPersonal", contentvalues, null, null);
    }

    public int setWerkgever(Personal personal)
    {
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("werkgever", personal.getWerkgever());
        contentvalues.put("werkgeversadres", personal.getWgAdres());
        contentvalues.put("werkgeverstelefoon", personal.getWgTelefoon());
        contentvalues.put("werkgeversemail", personal.getWgEmail());
        return db.update("tblPersonal", contentvalues, null, null);
    }
}
