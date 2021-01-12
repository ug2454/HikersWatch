package com.example.userlocationapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    double latitude;
    double longitude;
    TextView latitudetextView;
    TextView longitudetextview;
    TextView altitudetextview;
    TextView accuracytextview;
    TextView addresstextview;
    int altitude;
    int accuracy;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudetextView = findViewById(R.id.lattitudeTextView);
        longitudetextview = findViewById(R.id.longitudeTextView);
        altitudetextview = findViewById(R.id.altitudeTextView);
        accuracytextview = findViewById(R.id.accuracyTextView);
        addresstextview = findViewById(R.id.addressTextView);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("Location", String.valueOf(location.getLatitude()));
                Log.i("Location", String.valueOf(location.getLongitude()));
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                altitude = (int) location.getAltitude();
                accuracy = (int) location.getAccuracy();

                latitudetextView.setText("Lattitude : " + String.valueOf(latitude));
                longitudetextview.setText("Longitude : " + String.valueOf(longitude));
                altitudetextview.setText("Altitude : " + altitude);
                accuracytextview.setText("Accuracy : " + accuracy);
                setAddress();
//                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
//
//                try {
//                    System.out.println("inside on change");
//                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
//                    if (addressList != null && addressList.size() > 0) {
//                        String address = "";
//                        Log.i("PlaceInfo", addressList.get(0).toString());
//                        if (addressList.get(0).getFeatureName() != null) {
//                            address += addressList.get(0).getFeatureName() + ", ";
//                        }
//                        if (addressList.get(0).getLocality() != null) {
//                            address += addressList.get(0).getLocality() + ", ";
//                        }
//                        if (addressList.get(0).getAdminArea() != null) {
//                            address += addressList.get(0).getAdminArea();
//                        }
//
//                        addresstextview.setText(address);
//                        Log.i("Address", address);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
        };
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            Location lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


            if (lastKnownLocation != null) {
                updateLocationInfo(lastKnownLocation);
            }


        }


    }

    public void setText() {
        latitudetextView.setText("Lattitude : " + String.valueOf(latitude));
        longitudetextview.setText("Longitude : " + String.valueOf(longitude));
        altitudetextview.setText("Altitude : " + altitude);
        accuracytextview.setText("Accuracy : " + accuracy);
    }

    public void setAddress() {
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            System.out.println("inside on change");
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                String address = "";
                Log.i("PlaceInfo", addressList.get(0).toString());
                if (addressList.get(0).getFeatureName() != null) {
                    address += addressList.get(0).getFeatureName() + ", ";
                }
                if (addressList.get(0).getLocality() != null) {
                    address += addressList.get(0).getLocality() + ", ";
                }
                if (addressList.get(0).getAdminArea() != null) {
                    address += addressList.get(0).getAdminArea();
                }

                addresstextview.setText(address);
                Log.i("Address", address);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLocationInfo(Location location) {
        Log.i("location", location.toString());
        TextView t1 = findViewById(R.id.lattitudeTextView);
        TextView t2 = findViewById(R.id.longitudeTextView);
        TextView t3 = findViewById(R.id.accuracyTextView);
        TextView t4 = findViewById(R.id.altitudeTextView);
        TextView t5 = findViewById(R.id.addressTextView);

        t1.setText("Lattitude : " + String.valueOf(location.getLatitude()));
        t2.setText("Longitude : " + String.valueOf(location.getLongitude()));
        t3.setText("Accuracy : " + (int) location.getAccuracy());
        t4.setText("Altitude : " + (int) location.getAltitude());


    }


}