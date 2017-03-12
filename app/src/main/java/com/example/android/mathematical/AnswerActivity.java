package com.example.android.mathematical;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mathematical.database.AllStatsContract;
import com.example.android.mathematical.database.AllStatsDbHelper;
import com.example.android.mathematical.controller.Operands;


public class AnswerActivity extends AppCompatActivity {
    private TextView timeTakenText;     // displays time taken to answer
    private TextView resultText;        // displays whether user is right or wrong
    private TextView sumLineText;       // displays the sum
    private TextView sumAnswerText;     // displays the answer to the sum
    private TextView usersAnswerText;   // displays user's answer if user was wrong

    private String CORRECT_ANSWER   = "CORRECT!";
    private String WRONG_ANSWER     = "WRONG!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        // check if AnswerActivity start from a previous activity stopping (i.e. UserAnswerActivity).
        // if this Extra exists it's going too be true.
        boolean startFromStopActivity = getIntent().hasExtra(UserAnswerActivity.EXTRA_WAS_ACTIVITY_STOP);
        if (startFromStopActivity) { moveTaskToBack(true); }


        // create reference to TextViews in Activity
        timeTakenText   = (TextView) findViewById(R.id.time_taken_text);
        resultText      = (TextView) findViewById(R.id.result_text);
        sumLineText     = (TextView) findViewById(R.id.sum_line_text);
        sumAnswerText   = (TextView) findViewById(R.id.sum_answer_text);
        usersAnswerText = (TextView) findViewById(R.id.users_answer_text);

        // Hide this text view initially (used for displaying user's wrong answer).
        usersAnswerText.setVisibility(View.GONE);

        // retrieve extras from intent
        int usersAnswer     = (getIntent().hasExtra(UserAnswerActivity.EXTRA_USERS_ANSWER)) ?
                getIntent().getIntExtra(UserAnswerActivity.EXTRA_USERS_ANSWER, -1) : -1;
        double usersTime    = (getIntent().hasExtra(UserAnswerActivity.EXTRA_USERS_TIME)) ?
                getIntent().getDoubleExtra(UserAnswerActivity.EXTRA_USERS_TIME, -1.0) : -1.0;
        int[] sumOperands   = (getIntent().hasExtra(SumActivity.EXTRA_SUM_OPERANDS)) ?
                getIntent().getIntArrayExtra(SumActivity.EXTRA_SUM_OPERANDS) : new int[] {0, -1};

        // get answer to sum
        int sumAnswer = 1;
        for (int operand : sumOperands) {
            sumAnswer = sumAnswer * operand;
        }


        // check user's answer
        boolean isUserCorrect = (usersAnswer == sumAnswer);

        // populate TextViews with there values
        timeTakenText.setText(usersTime + " Seconds");  // covert double to String
        if (isUserCorrect) {                    // set correct/wrong text
            resultText.setText(CORRECT_ANSWER);
        }
        else {
            resultText.setText(WRONG_ANSWER);
        }
        sumLineText.setText(Operands.sumToString(sumOperands, SumActivity.OPERATION));
        sumAnswerText.setText(sumAnswer + "");

        if (!isUserCorrect) {
            usersAnswerText.setText("Not " + usersAnswer);
            usersAnswerText.setVisibility(View.VISIBLE);
        }

        // TODO: 12/03/2017  Save stats
        // Access database
        AllStatsDbHelper dbHelper = new AllStatsDbHelper(getApplicationContext());

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(AllStatsContract.AllStatsEntry.COLUMN_NAME_1ST_OPERAND, sumOperands[0]);
        values.put(AllStatsContract.AllStatsEntry.COLUMN_NAME_2ND_OPERAND, sumOperands[1]);
        values.put(AllStatsContract.AllStatsEntry.COLUMN_NAME_USERS_ANSWER, usersAnswer);
        values.put(AllStatsContract.AllStatsEntry.COLUMN_NAME_ANSWER, sumAnswer);
        values.put(AllStatsContract.AllStatsEntry.COLUMN_NAME_TIME, usersTime);

        // insert the new row, returning the primary key value of new row
        long newRowID = db.insert(AllStatsContract.AllStatsEntry.TABLE_NAME, null, values);

        Toast.makeText(getApplicationContext(), "rowID = " + newRowID, Toast.LENGTH_SHORT).show();

        // Count sum number
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("AnswerActivity", "triggered start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("AnswerActivity", "triggered resume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("AnswerActivity", "triggered pause");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("AnswerActivity", "triggered stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("AnswerActivity", "triggered destroy");

    }


    public void nextButtonClick(View v) {
        // start SumActivity
        Intent intent = new Intent(getApplicationContext(), SumActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    public void endButtonClick(View v) {

    }
}
