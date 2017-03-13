package com.example.android.mathematical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * This activity is for when the user resumes the app after certain circumstances. For example, when
 * the user leaves the {@link SumActivity} before the full sum is reveal, or
 * {@link UserAnswerActivity}.
 */
public class ContinueActivity extends AppCompatActivity {

    TextView continueMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue);

        // Create reference to TextViews in Activity
        continueMessageText = (TextView) findViewById(R.id.continue_message_text);

        // If the activity that started this intent has continue message extra set TextView
        boolean hasContinueMessage = getIntent().hasExtra(UserAnswerActivity.EXTRA_CONTINUE_MESSAGE);
        if (hasContinueMessage) {
            continueMessageText.setText(getIntent().getStringExtra(UserAnswerActivity.EXTRA_CONTINUE_MESSAGE));
        }

    }

    public void continueButtonClick(View v){
        // start SumActivity
        Intent intent = new Intent(getApplicationContext(), SumActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        finish();
    }
}
