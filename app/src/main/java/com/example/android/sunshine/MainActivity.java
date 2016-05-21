package com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "In onCreate Method");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "In onPause Method");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "In onStop Method");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "In onResume Method");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_TAG, "In onStart Method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "In onDestroy Method");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        } else if (id == R.id.action_location) {
            openGeoLocationOnMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openGeoLocationOnMap() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String zipCode = prefs.getString(getString(R.string.pref_location_key),
            getString(R.string.pref_location_default));
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon()
            .appendQueryParameter("q", zipCode)
            .build();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
