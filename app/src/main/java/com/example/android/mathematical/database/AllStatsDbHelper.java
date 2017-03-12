package com.example.android.mathematical.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mathematical.database.AllStatsContract.AllStatsEntry;

/**
 * Created by Matthew Johnson on 12/03/2017.
 *
 * <p>This class is  for creating and maintaining the database and tables</p>
 *
 * <p>Using example from https://developer.android.com/training/basics/data-storage/databases.html#DbHelper</p>
 */
public class AllStatsDbHelper extends SQLiteOpenHelper {

    /**
     * Statement for creating the table that stores all the stats.
     */
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + AllStatsContract.AllStatsEntry.TABLE_NAME + " (" +
                    AllStatsEntry._ID                       + " INTEGER PRIMARY KEY," +
                    AllStatsEntry.COLUMN_NAME_1ST_OPERAND   + " INTEGER, " +
                    AllStatsEntry.COLUMN_NAME_2ND_OPERAND   + " INTEGER," +
                    AllStatsEntry.COLUMN_NAME_USERS_ANSWER  + " INTEGER," +
                    AllStatsEntry.COLUMN_NAME_ANSWER        + " INTEGER," +
                    AllStatsEntry.COLUMN_NAME_TIME          + " REAL)";

    /**
     * Statement for deleting the table that stores all the stats.
     */
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + AllStatsContract.AllStatsEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AllStats.db";

    /**
     * This helper opens/creates the database.
     * @param context to use for opening/creating the database
     */
    public AllStatsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //  Creating tables here.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade is simply to
        // discard the data and start over.
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
