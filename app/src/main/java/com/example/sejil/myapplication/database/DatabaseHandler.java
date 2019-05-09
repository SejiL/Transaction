package com.example.sejil.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.sejil.myapplication.model.TransActions;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "transactions.db";
    private static final String TABLE_NAME = "transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_DETAILS = "details";
    private static final String COLUMN_TIME = "time";

    SQLiteDatabase db;

    public DatabaseHandler(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = getWritableDatabase();
    }

    public void addTransAction(TransActions transActionsObj) {
        ContentValues values = new ContentValues();

        values.put(COLUMN_PRICE, transActionsObj.getPrice());
        values.put(COLUMN_DETAILS, transActionsObj.getDetails());
        values.put(COLUMN_TIME, transActionsObj.getTransactiontime());

        db.insert(TABLE_NAME, null, values);
    }

    public ArrayList<TransActions> getAllTransActions() {
        ArrayList<TransActions> transActions = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TransActions transAction = new TransActions(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)
                );

                transActions.add(transAction);
            } while (cursor.moveToNext());
        }

        return transActions;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRICE + " TEXT, " + COLUMN_DETAILS + " TEXT, " + COLUMN_TIME + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
