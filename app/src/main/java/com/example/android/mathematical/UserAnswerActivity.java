package com.example.android.mathematical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.mathematical.controller.Operands;
import com.example.android.mathematical.util.Stopwatch;

/**
 * Activity for getting the user's answer
 */
public class UserAnswerActivity extends AppCompatActivity {
    // Declaring variables
    private EditText usersAnswerEditText;
    private TextView sumLineText;
    private int usersAnswer = -1;
    private Stopwatch stopwatch;
    private boolean hasAnswer = false; // has the user answered

    // Key strings for storing and retrieving intent extras.
    public static final String EXTRA_USERS_ANSWER = "com.example.android.mathematical.USERS_ANSWER";
    public static final String EXTRA_USERS_TIME = "com.example.android.mathematical.USERS_TIME";
    public static final String EXTRA_WAS_ACTIVITY_STOP = "com.example.android.mathematical.WAS_ACTIVITY_STOP";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_answer);

        // initialise stopwatch
        stopwatch = new Stopwatch();
        // start stopwatch
        stopwatch.start();

        // Create reference to TextViews in Activity
        usersAnswerEditText     = (EditText) findViewById(R.id.users_answer_edittext);
        sumLineText             = (TextView) findViewById(R.id.sum_line_text);

        // Empty text this TextView
        usersAnswerEditText.setText("");

        // Retrieve the sumOperands from intent (SumActivity)
        final int[] sumOperands = getIntent().getIntArrayExtra(SumActivity.EXTRA_SUM_OPERANDS);

        // Display sum line in this TextView
        sumLineText.setText(Operands.sumToString(sumOperands, SumActivity.OPERATION));

        // Key listener to detect ENTER key being pressed
        usersAnswerEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    // parse users answer to an integer
                    usersAnswer = Integer.parseInt(usersAnswerEditText.getText().toString());

                    hasAnswer = true; //

                    // stop timer
                    stopwatch.stop();

                    // start the AnswerActivity
                    Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
                    // put the neccesary variables in Extras
                    intent.putExtra(EXTRA_USERS_ANSWER, usersAnswer);
                    intent.putExtra(EXTRA_USERS_TIME, stopwatch.getElapsedTime());
                    intent.putExtra(SumActivity.EXTRA_SUM_OPERANDS, sumOperands);
                    //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // may not need
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        // TODO: Send user to an new Activity if they leave this one (onStop())
        // If uses leaves this activity (onStop()) then to avoid resuming back to it, start another
        // Activity.
        Log.i("UserAnswerActivity", "triggered onStop. hasAnswer = " + hasAnswer);

        // if user hasn't answered the sum that means user left app (onStop())
        if (!hasAnswer) {
            Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(EXTRA_WAS_ACTIVITY_STOP, true);
            startActivity(intent);
        }
    }
}
