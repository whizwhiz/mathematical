package com.example.android.mathematical;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.mathematical.controller.Operands;

/**
 * Activity for display the sum step by step
 */
public class SumActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName(); // for Log.x()

    private TextView sumRevealText;
    private TextView sumLineText;
    private Operands operandsController;
    private int[] sumOperands;
    private int operandCount = 0; // for counting the number of operands shown from sumOperands

    // Handler for the timed delay when starting the UserAnswerActivity shortly after the last
    // operand is shown.
    private Handler handler;
    private int LAST_OPERAND_TIME_DELAY = 750;

    // Key strings for storing and retrieving intent extras.
    public static final String EXTRA_SUM_OPERANDS = "com.example.android.mathematical.SUM_OPERANDS";
    // Operation constant
    public static final String OPERATION = "x";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum);

        // Initialise variables
        handler = new Handler();
        operandsController = new Operands();

        // Create reference to TextViews in Activity
        sumRevealText   = (TextView) findViewById(R.id.sum_reveal_text);
        sumLineText     = (TextView) findViewById(R.id.sum_line_text);

        // get the first set of operands and show the 1st operand
        sumOperands = operandsController.getOperands();
        showNextOperand(sumOperands[operandCount], false);
        operandCount++;

        // Listens for user to click the sumRevealText TextView
        sumRevealText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if operandCount is 0 then don't append next operand, otherwise append.
                // May never run.
                if (operandCount == 0) {
                    showNextOperand(sumOperands[operandCount], false);
                    operandCount++;
                } // else always runs
                else if ((operandCount > 0) && (operandCount < sumOperands.length)) {
                    // after the first operand is display in the sumLineText (TextView) the rest
                    // need to be append.
                    showNextOperand(sumOperands[operandCount], true);
                    operandCount++;

                    // if all sum operands have been shown start UserAnswerActivity after a set delay
                    if (operandCount == sumOperands.length) {
                        handler.postDelayed(runnable, LAST_OPERAND_TIME_DELAY);
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(TAG, "onStop()");

        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(TAG, "onRestart");

        // If user leaves this activity then returns and the full sum hasn't been revealed do not
        // resume start another activity.
        // TODO: 13/03/2017 Change this if condition to a boolean variable
        if (operandCount == sumOperands.length) {
            // Start another activity
            Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);
            startActivity(intent);

        }
    }

    /**
     * Start the {@link UserAnswerActivity}.
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Log.i(TAG, "started runnable");

            // start user answer activity
            Intent intent = new Intent(getApplicationContext(), UserAnswerActivity.class);
            intent.putExtra(EXTRA_SUM_OPERANDS, sumOperands);
            // Removes this intent from the back stack, so if the user comes out of app (onStop())
            // on the next screen the app reverts back to the previous stacked activity (MainActivity)
            // upon resuming. Now the user must start from the beginning (MainActivity) instead of
            // resuming this activity and giving them extra time to answer.
            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    };

    /**
     * This is for display the sum step by step in the sumRevealText and sumLineText TextViews
     * @param operand The operand to display
     * @param append whether to append the operand (true) to the sum line text
     */
    public void showNextOperand(int operand, boolean append) {
        // TODO: Possibly change this method to accept a View (TextView) so it can be moved to Operands.class

        if (!append) {
            sumRevealText.setText(Integer.toString(operand)); // convert int to String
            sumLineText.setText(Integer.toString(operand));
        }
        else {
            sumRevealText.setText(Integer.toString(operand));
            sumLineText.append(" " + OPERATION + " " + operand);
        }
    }

}
