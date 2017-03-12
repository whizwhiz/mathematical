package com.example.android.mathematical.database;

import android.provider.BaseColumns;

/**
 * Created by Matthew Johnson on 11/03/2017.
 *
 * <p>Companion class (know as a contract class) which explicitly specifies the layout of the schema
 * in a systematic and self-documenting way.</p>
 *
 * <p>Using example from https://developer.android.com/training/basics/data-storage/databases.html#DbHelper</p>
 */

public final class AllStatsContract {
    // To prevent someone instantiating the contract class, make the constructor private.
    private AllStatsContract() {

    }

    /**
     * Inner class that defines the table contents. By implementing {@link BaseColumns} interface,
     * this class can inherit a primary key field called _ID.*/
    public static class AllStatsEntry implements BaseColumns {
        public static final String TABLE_NAME = "allstat";
        public static final String COLUMN_NAME_USERS_NAME = "usersnames";
        public static final String COLUMN_NAME_1ST_OPERAND = "operand1";
        public static final String COLUMN_NAME_2ND_OPERAND = "operand2";
        public static final String COLUMN_NAME_OTHER_OPERAND = "operandothers";
        public static final String COLUMN_NAME_USERS_ANSWER = "usersanswer";
        public static final String COLUMN_NAME_ANSWER = "answer";
        public static final String COLUMN_NAME_TIME = "timer";

    }

}

