package com.example.android.mathematical;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Main activity to show the initial screen of the app.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * Triggered by onClick event in corresponding XML.
     * Starts the {@link SumActivity} intent (screen).
     * @param view The view that triggered the call
     */
    public void startButtonClick(View view) {
        Intent intent = new Intent(this, SumActivity.class);
        // Removes this intent from the back stack, so if the user comes out of app (onStop()) on
        // the next screen the app reverts back to this MainActivity upon resuming. Now a new sum
        // reveal can be displayed from MainActivity start button instead of resuming this intent
        // and giving the user extra time to answer.
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

}
