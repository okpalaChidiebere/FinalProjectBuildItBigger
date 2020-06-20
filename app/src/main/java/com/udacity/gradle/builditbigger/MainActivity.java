package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.jokes.CollinsJoke;
import com.example.android.jokesactivity.JokesActivity;


public class MainActivity extends AppCompatActivity implements JokeListener {

    private String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        CollinsJoke jokes = new CollinsJoke();

        Context context = this;
        CharSequence text = jokes.tellAHandCraftedJoke();
        /*int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/

        new EndpointsAsyncTask(MainActivity.this).execute();
        /*Intent myIntent = new Intent(this, JokesActivity.class);
        myIntent.putExtra(JokesActivity.JOKES_EXTRA, text); //passing jokes from the Java Library to the Android Library
        startActivity(myIntent);*/

    }

    @Override
    public void onJokeLoaded(String joke) {
        Log.v("onJokeLoaded", "Joke is: " + joke);
        this.mJoke = joke;

        Intent myIntent = new Intent(this, JokesActivity.class);
        myIntent.putExtra(JokesActivity.JOKES_EXTRA, mJoke); //passing jokes from the Java Library to the Android Library
        startActivity(myIntent);
    }


}
