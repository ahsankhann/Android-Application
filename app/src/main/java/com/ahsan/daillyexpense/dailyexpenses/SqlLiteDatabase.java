package com.ahsan.daillyexpense.dailyexpenses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.lang.UCharacter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class SqlLiteDatabase extends SQLiteOpenHelper {


    SQLiteDatabase db;

    private static final String DATABASE_NAME = "Daily_Expense";

    private static final int DATABASE_VERSION=1;

    private static final String TABLE_EXPENSE="tbl_expenses";

    //Student Table Fields
    public static final String KEY_DateID= "_id";
    public static final String KEY_Food="food";
    public static final String KEY_Petrol="petrol";
    public static final String KEY_Ciggrette="ciggrette";
    public static final String KEY_Others="others";

    public SqlLiteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENT_TABLE="CREATE TABLE " + TABLE_EXPENSE + " (" + KEY_DateID+" TEXT PRIMARY KEY, " +
                KEY_Food + " TEXT, " + KEY_Petrol + " TEXT, " + KEY_Ciggrette +  " TEXT, " +KEY_Others + " TEXT )";

        db.execSQL(CREATE_STUDENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSE);
        onCreate(db);
    }

    public long insertExpense(String _id, String food, String petrol, String ciggrette, String others) {
        db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        ContentValues CV = new ContentValues();
        Date _date = null;
        try {
            _date = dateFormat.parse(_id);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String Datess = dateFormat.format(_date);
        CV.put(KEY_DateID, Datess);
        CV.put(KEY_Food, food);
        CV.put(KEY_Petrol, petrol);
        CV.put(KEY_Ciggrette, ciggrette);
        CV.put(KEY_Others, others);
        return db.insert(TABLE_EXPENSE, null, CV);
    }

    public int CheckExpense(String _Date){
        db = this.getReadableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date _date = null;
        try {
            _date = dateFormat.parse(_Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String Datess = dateFormat.format(_date);
        String[] Coulmns = new String[]{KEY_DateID, KEY_Food, KEY_Petrol, KEY_Ciggrette, KEY_Others};
        Cursor cr = db.query(TABLE_EXPENSE, Coulmns, KEY_DateID + " = ? ", new String[] {Datess},null,null,null);
        //Cursor cr = db.rawQuery("Select * From tbl_expenses Where _id = ?", new String[] {Datess});
        int iRow = cr.getColumnIndex(KEY_DateID);
        int Res;

        if(cr.moveToFirst())
            Res = 1;
        else
            Res = 0;

        db.close();
        return Res;

    }

    public JSONArray ArrayReturn(){
        db = this.getReadableDatabase();
        String[] Coulmns = new String[]{KEY_DateID, KEY_Food, KEY_Petrol, KEY_Ciggrette, KEY_Others};
        //String querys = "Select * From " + TABLE_EXPENSE + " Where " + KEY_DateID + " Like '/1/2019 ";
        Cursor cr = db.query(TABLE_EXPENSE, Coulmns,null,null, null, null,null);
        JSONArray JSON = cur2Json(cr);
        db.close();
        return JSON;
    }

    public JSONArray cur2Json(Cursor cursor) {

        JSONArray resultSet = new JSONArray();
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            int totalColumn = cursor.getColumnCount();
            JSONObject rowObject = new JSONObject();
            for (int i = 0; i < totalColumn; i++) {
                if (cursor.getColumnName(i) != null) {
                    try {
                        rowObject.put(cursor.getColumnName(i),
                                cursor.getString(i));
                    } catch (Exception e) {

                    }
                }
            }
            resultSet.put(rowObject);
            cursor.moveToNext();
        }

        cursor.close();
        return resultSet;

    }
}
