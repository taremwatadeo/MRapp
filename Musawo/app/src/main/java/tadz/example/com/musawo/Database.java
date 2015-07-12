package tadz.example.com.musawo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;

public class Database {


    public class RegisterTable {
        public static final String TABLE_NAME = "Register";
        public static final String ID = "Reg_id";
        public static final String FNAME = "first_name";
        public static final String LNAME = "last_name";
        public static final String EMAIL = "email";
        public static final String CATEGORY = "category";
        public static final String PASSWORD = "password";
        public static final String CONFIRMPASS = "confirm_pass";
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
                            + RegisterTable.FNAME + " TEXT," + RegisterTable.LNAME + " TEXT," + RegisterTable.EMAIL + " TEXT," + RegisterTable.CATEGORY + " TEXT,"
                            + RegisterTable.PASSWORD + " TEXT," + RegisterTable.CONFIRMPASS + " TEXT);"
            );

            db.execSQL("CREATE TABLE " + PatientTable.TABLE_NAME + "(" + PatientTable.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                            + PatientTable.NAME + " TEXT," + PatientTable.AGE + " INTEGER," + PatientTable.DISEASE + " TEXT," + PatientTable.PRESCRIPTION + " VARCHAR,"
                            + PatientTable.DRUG + " TEXT," + PatientTable.RETURNDATE + " VARCHAR," + PatientTable.REMARKS + " TEXT);"
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


        public Long registration(String fname, String lname, String emaill, String categoryy, String passwordd, String confirm_pass) throws SQLException {

            ContentValues contentValues = new ContentValues();

            contentValues.put(RegisterTable.FNAME, fname);
            contentValues.put(RegisterTable.LNAME, lname);
            contentValues.put(RegisterTable.EMAIL, emaill);
            contentValues.put(RegisterTable.CATEGORY, categoryy);
            contentValues.put(RegisterTable.PASSWORD, passwordd);
            contentValues.put(RegisterTable.CONFIRMPASS, confirm_pass);

            return db.insert(RegisterTable.TABLE_NAME, null, contentValues);


        }

       public Database (Context c) {

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

        public String getPatientName(long row) {
            String[] cols = new String[]{PatientTable.ID, PatientTable.NAME, PatientTable.AGE, PatientTable.DISEASE,
                    PatientTable.PRESCRIPTION, PatientTable.DRUG, PatientTable.RETURNDATE, PatientTable.REMARKS};
            Cursor x = db.query(PatientTable.TABLE_NAME, cols,PatientTable.ID + "=" + row, null, null, null, null);

            if(x != null){
                x.moveToFirst();
                String patientname = x.getString(1);
                return patientname;
            }

            return null;
        }

    public  void deleteEntry(long rrow){

        db.delete(PatientTable.TABLE_NAME,PatientTable.ID + "=" + rrow,null);

    }



        public boolean validateUser(String un, String upass) {
            SQLiteDatabase B = ourHelper.getReadableDatabase();
            Cursor z = B.rawQuery("SELECT * FROM " + RegisterTable.TABLE_NAME + " WHERE " + RegisterTable.LNAME + "='" + un + "'AND "
            + RegisterTable.PASSWORD + "='" + upass + "'" , null);

            if(z.getCount()>0)
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

                result = result + x.getString(tid) + "\n" + x.getString(tname) + "\n" + x.getString(tage) + "\n" + x.getString(tdisease) + "\n" + x.getString(tprescription) +
                        "\n" + x.getString(tdrug) + "\n" + x.getString(treturndate) + "\n" + x.getString(tremarks) + "\n";
            }


            return result;
        }

    }




