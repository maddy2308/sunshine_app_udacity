package com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecast_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            updateWeather();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateWeather();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent detailActivityIntent = new Intent(getContext(), DetailActivity.class);
                startService(detailActivityIntent, mForecastAdapter.getItem(position));
            }

        });

        mForecastAdapter = new ArrayAdapter<>(getContext(), R.layout.list_item_forecast,
                R.id.list_item_forecast_textview, new ArrayList<String>());

        listView.setAdapter(mForecastAdapter);

        return rootView;
    }

    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String zipCode = prefs.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        String userUnitPref = prefs.getString(getString(R.string.pref_units_key),
                getString(R.string.pref_units_metric));
        new FetchWeatherTask(mForecastAdapter).execute(zipCode, userUnitPref);
    }

    private void startService(Intent intentToStart, String item) {
        intentToStart.putExtra(Intent.EXTRA_TEXT, item);
        startActivity(intentToStart);
    }

}
