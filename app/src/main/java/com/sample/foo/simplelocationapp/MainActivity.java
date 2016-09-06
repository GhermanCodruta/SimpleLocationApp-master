package com.sample.foo.simplelocationapp;


import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String PROXIMITY_INTENT_ACTION =
            "com.sample.foo.simplelocationapp";
    LocationManager locationManager;
    double longitudeGPS, latitudeGPS;
    TextView longitudeValueGPS, latitudeValueGPS;


    private final LocationListener locationListenerGPS = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitudeGPS = location.getLongitude();
            latitudeGPS = location.getLatitude();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    longitudeValueGPS.setText(longitudeGPS + "");
                    latitudeValueGPS.setText(latitudeGPS + "");
                    Toast.makeText(MainActivity.this, "GPS Provider update", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        longitudeValueGPS = (TextView) findViewById(R.id.longitudeValueGPS);
        latitudeValueGPS = (TextView) findViewById(R.id.latitudeValueGPS);

    }

    private boolean checkLocation() {
        if (!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void toggleGPSUpdates(View view) {
        if (!checkLocation())
            return;
        Button button = (Button) view;
        if (button.getText().equals(getResources().getString(R.string.pause))) {
            locationManager.removeUpdates(locationListenerGPS);
            button.setText(R.string.resume);
        } else {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 2 * 60 * 1000, 10, locationListenerGPS);
            button.setText(R.string.pause);
        }
    }
    ArrayList mPositions = new ArrayList<CoordsPair>();
    private void createPositions() {
        mPositions.add(new CoordsPair(46.802463, 23.608192));
        mPositions.add(new CoordsPair(51.455814, -2.603125));
        mPositions.add(new CoordsPair(53.475638, -2.242057));
    }

    private void registerIntents() {
        for (int i = 0; i < mPositions.size(); i++) {
            CoordsPair latLon = (CoordsPair) mPositions.get(i);
            setProximityAlert(latLon.getLatitude(),
                    latLon.getLongitude(),
                    i + 1,
                    i);
        }
    }

    void setProximityAlert(double lat, double lon, final long eventID, int requestCode) {
        // Radius set in meters
        float radius = 100f;

        // Expiration is 10 Minutes (10mins * 60secs * 1000milliSecs)
        long expiration = 600000;

        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Intent intent = new Intent(PROXIMITY_INTENT_ACTION);
        intent.putExtra(ProximityAlert.EVENT_ID_INTENT_EXTRA, eventID);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        locManager.addProximityAlert(lat, lon, radius, expiration, pendingIntent);


    }
}


