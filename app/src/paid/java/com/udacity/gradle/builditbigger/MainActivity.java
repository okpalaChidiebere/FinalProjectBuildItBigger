package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.jokesactivity.JokesActivity;

public class MainActivity extends AppCompatActivity implements JokeListener {

    private String mJoke;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        new EndpointsAsyncTask(MainActivity.this).execute();
        showWorkInProgress();
    }

    @Override
    public void onJokeLoaded(String joke) {
        Log.v("onJokeLoaded", "Joke is: " + joke);
        this.mJoke = joke;

        showWorkFinished();
        Intent myIntent = new Intent(this, JokesActivity.class);
        myIntent.putExtra(JokesActivity.JOKES_EXTRA, mJoke); //passing jokes from the Java Library to the Android Library
        startActivity(myIntent);
    }

    private void showWorkInProgress() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    private void showWorkFinished() {
        mLoadingIndicator.setVisibility(View.GONE);
    }
}
