package com.example.a01_travel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "TravelPlans.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "travel_plans";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESTINATION = "destination";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
    public static final String COLUMN_BUDGET = "budget";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DESTINATION + " TEXT," +
                    COLUMN_START_DATE + " TEXT," +
                    COLUMN_END_DATE + " TEXT," +
                    COLUMN_BUDGET + " REAL)";

    /** @Function:  DatabaseHelper()
     * @Param: Context context
     *  @Detail: Initializes the database with the specified name and version within the given context.*/
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** @Function: onCreate()
     * @Param: SQLiteDatabase db
     *  @Detail: Creates the database tables as per the SQL_CREATE_ENTRIES definition..*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    /** @Function: onUpgrade()
     *  @Param: SQLiteDatabase db, int oldVersion, int newVersion
     *  @Detail: Drops the old database table if it exists and creates a new one.*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /** @Function: getAllTravelPlans()
     *  @Detail: Fetches all travel plan records from the database.*/
    public Cursor getAllTravelPlans() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }


}
