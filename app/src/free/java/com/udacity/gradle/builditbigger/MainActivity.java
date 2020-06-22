package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.android.jokesactivity.JokesActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity implements JokeListener {

    private String mJoke;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setInterstitialAdToDisplay();

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
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.v("MainActivity", "The interstitial not loaded yet.");
            if (!TextUtils.isEmpty(mJoke)) { //at this point we have a joke returned from the jokeListener
                launchJokeActivity();
            }
        }


    }

    @Override
    public void onJokeLoaded(String joke) {
        Log.v("onJokeLoaded", "Joke is: " + joke);
        this.mJoke = joke;
    }

    private void launchJokeActivity() {
        Intent myIntent = new Intent(this, JokesActivity.class);
        myIntent.putExtra(JokesActivity.JOKES_EXTRA, mJoke); //passing jokes from the Java Library to the Android Library
        startActivity(myIntent);
    }

    private void setInterstitialAdToDisplay() {

        MobileAds.initialize(this, getString(R.string.admob_app_id)); // Initialize the mobile ads SDK.

        mInterstitialAd = new InterstitialAd(this); // Create Interstitial Ad
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id)); //set the adUnitId.
        requestNewInterstitialAd();


        // Event listener for the ad interstitial
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() { //when the ad is closed
                requestNewInterstitialAd(); //create the next ad to display
                if (TextUtils.isEmpty(mJoke)) {
                    mJoke = "No Joke Found";
                    launchJokeActivity();
                } else {
                    launchJokeActivity();
                }
            }
        });
    }

    private void requestNewInterstitialAd() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR) //emulator for app freeDeug. You will have to remove the line when publishing your app to playstore
                    .build();

            mInterstitialAd.loadAd(adRequest);
        }
    }


}
