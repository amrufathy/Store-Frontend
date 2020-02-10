package com.example.store.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.store.models.CartItem;

import java.util.ArrayList;

public class SQLiteDriver extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "orders.db";
    private static final String TABLE_NAME = "order_items";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_NAME = "product_name";
    private static final String QUANTITY = "quantity";
    private static final String COST = "cost";

    @Nullable
    private static SQLiteDriver mInstance = null;


    private SQLiteDriver(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Nullable
    public static SQLiteDriver getInstance(@NonNull Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new SQLiteDriver(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        /*
        CREATE TABLE IF NOT EXISTS DATABASE_NAME.TABLE_NAME (
            CUSTOMER_ID INTEGER,
            PRODUCT_ID INTEGER,
            PRODUCT_NAME TEXT,
            QUANTITY INTEGER,
            COST REAL
        );
        */

        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s (" +
                        "%s INTEGER," +
                        "%s INTEGER," +
                        "%s TEXT," +
                        "%s INTEGER," +
                        "%s REAL);", TABLE_NAME, CUSTOMER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, COST);

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        onCreate(db);
    }

    public boolean save(int customer_id, int product_id, String product_name, int quantity, double cost) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_ID, customer_id);
        values.put(PRODUCT_ID, product_id);
        values.put(PRODUCT_NAME, product_name);
        values.put(QUANTITY, quantity);
        values.put(COST, cost);

        long newRowId = db.insert(TABLE_NAME, null, values);

        return newRowId != -1;
    }

    @NonNull
    public ArrayList<CartItem> readAllProducts() {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {CUSTOMER_ID, PRODUCT_ID, PRODUCT_NAME, QUANTITY, COST};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null,
                null, null, null);

        ArrayList<CartItem> itemsList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Integer customer_id = cursor.getInt(cursor.getColumnIndex(CUSTOMER_ID));
            Integer product_id = cursor.getInt(cursor.getColumnIndex(PRODUCT_ID));
            String product_name = cursor.getString(cursor.getColumnIndex(PRODUCT_NAME));
            Integer quantity = cursor.getInt(cursor.getColumnIndex(QUANTITY));
            Double cost = cursor.getDouble(cursor.getColumnIndex(COST));

            itemsList.add(new CartItem(customer_id, product_id, product_name, quantity, cost));
        }

        cursor.close();

        return itemsList;
    }

    public void clearRecords() {
        getWritableDatabase().execSQL("DELETE FROM " + TABLE_NAME);
    }

    public void deleteRecord(int customer_id, int product_id, int quantity) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = %d AND %s = %d AND %s = %d;",
                TABLE_NAME, CUSTOMER_ID, customer_id, PRODUCT_ID, product_id, QUANTITY, quantity);
        getWritableDatabase().execSQL(query);
    }
}
