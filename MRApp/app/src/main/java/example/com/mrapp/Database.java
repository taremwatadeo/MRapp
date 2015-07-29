package example.com.mrapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;


public class Database {

    public class RegisterTable {
        public static final String TABLE_NAME = "Register";
        public static final String ID = "Reg_id";
        public static final String NAME = "username";
        public static final String CATEGORY = "department";
        public static final String PASSWORD = "password";
        public static final String CONFIRMPASS = "confirm_pass";
        public static final String SECURITY = "security_hint";
    }

    public class PatientTable {
        public static final String TABLE_NAME = "Patient";
        public static final String ID = "pat_id";
        public static final String NAME = "patient_name";
        public static final String AGE = "patient_age";
        public static final String DISEASE = "disease";
        public static final String PRESCRIPTION = "prescription";
        public static final String DRUG = "drug_type";
        public static final String RETURNDATE = "returndate";
        public static final String REMARKS = "remarks";


    }

    public static final String DATABASE_NAME = "Musawo_db";
    private static final int DATABASE_VERSION = 1;
    private DbHelper ourHelper;
    private Context ourContext = null;
    public SQLiteDatabase db;

    public class DbHelper extends SQLiteOpenHelper {


        public DbHelper(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL("CREATE TABLE " + RegisterTable.TABLE_NAME + " (" + RegisterTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                            + RegisterTable.NAME + " TEXT," + RegisterTable.CATEGORY + " TEXT,"
                            + RegisterTable.PASSWORD + " TEXT," + RegisterTable.CONFIRMPASS + " TEXT,"
                            + RegisterTable.SECURITY + " TEXT);"
            );

            db.execSQL("CREATE TABLE " + PatientTable.TABLE_NAME + "(" + PatientTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + PatientTable.NAME + " TEXT," + PatientTable.AGE + " INTEGER," + PatientTable.DISEASE + " TEXT," + PatientTable.PRESCRIPTION + " VARCHAR,"
                            + PatientTable.DRUG + " TEXT," + PatientTable.RETURNDATE + " TEXT," + PatientTable.REMARKS + " TEXT);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("LOG_TAG", "updating database from version" + oldVersion + "to" + newVersion + ", which will destroy all data");

            db.execSQL("DROP TABLE IF EXISTS" + RegisterTable.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS" + PatientTable.TABLE_NAME);

            onCreate(db);//create new instance of database schema(tables)

        }
    }


    public Long registration(String user_name, String categoryy, String passwordd, String confirm_pass, String security_hint) throws SQLException {

        ContentValues contentValues = new ContentValues();

        contentValues.put(RegisterTable.NAME, user_name);
        contentValues.put(RegisterTable.CATEGORY, categoryy);
        contentValues.put(RegisterTable.PASSWORD, passwordd);
        contentValues.put(RegisterTable.CONFIRMPASS, confirm_pass);
        contentValues.put(RegisterTable.SECURITY, security_hint);

        return db.insert(RegisterTable.TABLE_NAME, null, contentValues);


    }

    public Database(Context c) {

        ourContext = c;
    }

    public Database open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        db = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {

        ourHelper.close();
    }

        /*public String getPatientName(long row) {
            String[] cols = new String[]{PatientTable.ID, PatientTable.NAME, PatientTable.AGE, PatientTable.DISEASE,
                    PatientTable.PRESCRIPTION, PatientTable.DRUG, PatientTable.RETURNDATE, PatientTable.REMARKS};
            Cursor x = db.query(PatientTable.TABLE_NAME, cols,PatientTable.ID + "=" + row, null, null, null, null);

            if(x != null){
                x.moveToFirst();
                String patientname = x.getString(1);
                return patientname;
            }

            return null;
            }*/


    public void deleteEntry(int id) {

        db.delete(PatientTable.TABLE_NAME, PatientTable.ID + "=" + id, null);

    }

    public Integer deleteP(Integer id) {
        SQLiteDatabase db = ourHelper.getReadableDatabase();
        return db.delete(PatientTable.TABLE_NAME, PatientTable.NAME + "=" + id, null);
    }


    public boolean validateUser(String un, String upass) {
        SQLiteDatabase B = ourHelper.getReadableDatabase();
        Cursor z = B.rawQuery("SELECT * FROM " + RegisterTable.TABLE_NAME + " WHERE " + RegisterTable.NAME + "='" + un + "'AND "
                + RegisterTable.PASSWORD + "='" + upass + "'", null);

        if (z.getCount() > 0)
            return true;

        return false;

    }


    public Long addingPatient(String pname, String page, String pdisease, String pprescription, String pdrug, String preturndate,
                              String premarks) throws SQLException {

        ContentValues contentValues = new ContentValues();

        contentValues.put(PatientTable.NAME, pname);
        contentValues.put(PatientTable.AGE, page);
        contentValues.put(PatientTable.DISEASE, pdisease);
        contentValues.put(PatientTable.PRESCRIPTION, pprescription);
        contentValues.put(PatientTable.DRUG, pdrug);
        contentValues.put(PatientTable.RETURNDATE, preturndate);
        contentValues.put(PatientTable.REMARKS, premarks);


        return db.insert(PatientTable.TABLE_NAME, null, contentValues);
    }

    public String getPatient() {

        String[] col = new String[]{PatientTable.ID, PatientTable.NAME, PatientTable.AGE, PatientTable.DISEASE,
                PatientTable.PRESCRIPTION, PatientTable.DRUG, PatientTable.RETURNDATE, PatientTable.REMARKS};
        Cursor x = db.query(PatientTable.TABLE_NAME, col, null, null, null, null, null);
        String result = "";

        for (x.moveToFirst(); !x.isAfterLast(); x.moveToNext()) {
            int tid = x.getColumnIndex(PatientTable.ID);
            int tname = x.getColumnIndex(PatientTable.NAME);
            int tage = x.getColumnIndex(PatientTable.AGE);
            int tdisease = x.getColumnIndex(PatientTable.DISEASE);
            int tprescription = x.getColumnIndex(PatientTable.PRESCRIPTION);
            int tdrug = x.getColumnIndex(PatientTable.DRUG);
            int treturndate = x.getColumnIndex(PatientTable.RETURNDATE);
            int tremarks = x.getColumnIndex(PatientTable.REMARKS);

            result = result + x.getString(tid) + "\t: " + x.getString(tname) + " \n ";
                    /*+ "\n "
                    + x.getString(tage) + "\n " + x.getString(tdisease) + "\n " + x.getString(tprescription) +
                    "\n " + x.getString(tdrug) + "\n " + x.getString(treturndate) + "\n " + x.getString(tremarks) + "\n";
                    */
        }

        return result;
    }

    public String getDetail(String x) {
        SQLiteDatabase A = ourHelper.getReadableDatabase();
        Cursor z = A.rawQuery("SELECT * FROM " + PatientTable.TABLE_NAME + " WHERE " + PatientTable.NAME + "='" + x + "'", null);
        String res = null;
        if (z.moveToFirst()) {
            do {
                res = z.getString(z.getColumnIndex(PatientTable.DRUG));
            } while (z.moveToNext());
        }
        return res;
    }


    public boolean updatep(Integer id, String pname, String page, String pdisease, String pprescription, String pdrug,
                           String preturndate, String premarks) {
        SQLiteDatabase db = ourHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(PatientTable.NAME, pname);
        contentValues.put(PatientTable.AGE, page);
        contentValues.put(PatientTable.DISEASE, pdisease);
        contentValues.put(PatientTable.PRESCRIPTION, pprescription);
        contentValues.put(PatientTable.DRUG, pdrug);
        contentValues.put(PatientTable.RETURNDATE, preturndate);
        contentValues.put(PatientTable.REMARKS, premarks);

        db.update(PatientTable.TABLE_NAME, contentValues, PatientTable.ID + "=" + id, new String[]{
                Integer.toString(id)});
        return true;
    }

    public String getHint(String hint, String hints) {
        SQLiteDatabase A = ourHelper.getReadableDatabase();

        Cursor z = A.rawQuery("SELECT * FROM " + RegisterTable.TABLE_NAME + " WHERE " + RegisterTable.NAME + "='" + hint + "'AND "
                + RegisterTable.SECURITY + "='" + hints + "'", null);
        String res = null;
        if (z.moveToFirst()) {
            do {
                res = z.getString(z.getColumnIndex(RegisterTable.PASSWORD));
            } while (z.moveToNext());
        }
        return res;
    }
        public ArrayList<People> getList() {

        ArrayList<People> array_list = new ArrayList();
        SQLiteDatabase db = ourHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PatientTable.TABLE_NAME ,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            People currentPerson = new People();
            currentPerson.setId(cursor.getInt(cursor.getColumnIndex(PatientTable.ID)));
            currentPerson.setNames(cursor.getString(cursor.getColumnIndex(PatientTable.NAME)));
            array_list.add(currentPerson);
            cursor.moveToNext();
        }

        return array_list;
    }


}


