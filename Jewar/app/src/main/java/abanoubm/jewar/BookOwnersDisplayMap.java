package abanoubm.jewar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BookOwnersDisplayMap extends FragmentActivity implements
        OnMapReadyCallback, LocationListener {

    private String bookID;
    private Marker GPSMarker;
    private GoogleMap mMap;
    private Map<String, BookOwner> mMarkerInfoList = new HashMap<>();
    private TextView name, email, mobile;
    private final int MAP_REQUEST_CODE = 600;

    private class DisplayOwnersTask extends
            AsyncTask<Void, Void, APIResponse> {

        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(BookOwnersDisplayMap.this);
            pBar.setCancelable(false);
            pBar.setTitle("loading");
            pBar.setMessage("searching for owners ....");
            pBar.show();
        }

        @Override
        protected void onPostExecute(APIResponse response) {
            if (getApplicationContext() == null)
                return;
            switch (response.getStatus()) {
                case -1:
                    Toast.makeText(getApplicationContext(), "check your internet connection!",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 0:
                    Toast.makeText(getApplicationContext(), "connection timeout, login first!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(BookOwnersDisplayMap.this, SignIn.class));

                    break;
                case 3:
                    Toast.makeText(getApplicationContext(), "no book owners found!!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    break;

                case 7:
                    ArrayList<BookOwner> owners = (ArrayList<BookOwner>) response.getData();
                    if (owners != null) {
                        //   mAdapter.clearThenAddAll(owners);
                        if (owners.size() == 0) {
                            Toast.makeText(getApplicationContext(), "no book owners found!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        for (BookOwner owner : owners) {
                            mMarkerInfoList.put(mMap.addMarker(new MarkerOptions()
                                    .position(
                                            new LatLng(owner.getLat(), owner.getLng()))
                                    .title("\u200e" + owner.getName())
                                    .draggable(false)).getId(), owner);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "error while trying to search! try again!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            pBar.dismiss();
        }

        @Override
        protected APIResponse doInBackground(Void... params) {
            return JewarApi.get_owners_of_book(bookID);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        mobile = (TextView) findViewById(R.id.mobile);

        bookID = getIntent().getStringExtra("book_id");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMap == null)
            return;

        if (GPSMarker != null)
            GPSMarker.remove();
        GPSMarker = mMap.addMarker(new MarkerOptions()
                .position(
                        new LatLng(location.getLatitude(), location
                                .getLongitude()))
                .draggable(false)
                .title("your GPS current location is here!")
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        new DisplayOwnersTask().execute();

        if (Build.VERSION.SDK_INT < 23 ||
                ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

            setGPSMarkerlocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MAP_REQUEST_CODE);
        }


        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                BookOwner owner = mMarkerInfoList.get(marker.getId());
                if (owner != null) {
                    name.setText(owner.getName());
                    email.setText(owner.getEmail());
                    mobile.setText(owner.getMobile());

                }
                return false;

            }
        });

    }

    private void setGPSMarkerlocation() {

        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager)
                getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(new Criteria(), false));
        if (myLocation != null) {
            GPSMarker = mMap.addMarker(new MarkerOptions()
                    .position(
                            new LatLng(myLocation.getLatitude(), myLocation
                                    .getLongitude()))
                    .draggable(false)
                    .title("your GPS current location is here!")
                    .icon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MAP_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                setGPSMarkerlocation();
        }
    }
}