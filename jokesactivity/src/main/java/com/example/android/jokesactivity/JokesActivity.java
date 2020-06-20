package com.example.android.jokesactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class JokesActivity extends AppCompatActivity {

    public static final String JOKES_EXTRA = "JOKES_TO_DISPLAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jokes);

        String jokesToDisplay = getString(R.string.no_jokes);
        if(getIntent().hasExtra(JOKES_EXTRA)) {
            jokesToDisplay = getIntent().getStringExtra(JOKES_EXTRA);
        }

        TextView textView = (TextView) findViewById(R.id.tv_jokes);
        textView.setText(jokesToDisplay);

    }
}
