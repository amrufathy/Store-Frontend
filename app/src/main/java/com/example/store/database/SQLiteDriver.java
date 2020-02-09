package com.example.store.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDriver extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "orders.db";
    private static final String TABLE_NAME = "order_items";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String QUANTITY = "quantity";
    private static final String COST = "cost";


    public SQLiteDriver(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /*
        CREATE TABLE IF NOT EXISTS DATABASE_NAME.TABLE_NAME (
            CUSTOMER_ID INTEGER,
            PRODUCT_ID INTEGER,
            QUANTITY INTEGER,
            COST REAL
        );
        */

        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER," +
                        "%s INTEGER," +
                        "%s INTEGER," +
                        "%s REAL);", TABLE_NAME, CUSTOMER_ID, PRODUCT_ID, QUANTITY, COST);

        Log.d("BIBO DB", query);

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean save(int customer_id, int product_id, int quantity, double cost) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_ID, customer_id);
        values.put(PRODUCT_ID, product_id);
        values.put(QUANTITY, quantity);
        values.put(COST, cost);

        long newRowId = database.insert(TABLE_NAME, null, values);

        return newRowId != -1;
    }

    public void readAll() {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] columns = {CUSTOMER_ID, PRODUCT_ID, QUANTITY, COST};
        Cursor cursor = database.query(TABLE_NAME, columns, null, null,
                null, null, null);

        Log.d("BIBO DB READ", "The total cursor count is " + cursor.getCount());
    }
}
