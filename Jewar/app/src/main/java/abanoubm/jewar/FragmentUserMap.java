package abanoubm.jewar;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class FragmentUserMap extends Fragment implements OnMapReadyCallback, LocationListener {
    private double lon, lat;
    private Marker mLocationMarker, GPSMarker;
    private GoogleMap mMap;
    private final int MAP_REQUEST_CODE = 600;
private TextView saveButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lon = getArguments().getDouble("lon");
            lat = getArguments().getDouble("lat");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_map, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        saveButton = (TextView)root.findViewById(R.id.save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateTask().execute();
            }
        });

        return root;

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        mMap = map;

        new DisplayTask().execute();

        if (Build.VERSION.SDK_INT < 23 ||
                ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getContext(),
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED) {

            setGPSMarkerlocation();
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    MAP_REQUEST_CODE);
        }

        map.setOnMarkerDragListener(new OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                saveButton.setVisibility(View.VISIBLE);
            }

            @Override
            public void onMarkerDrag(Marker marker) {
                lat = marker.getPosition().latitude;
                lon = marker.getPosition().longitude;
            }
        });
        map.setOnMapClickListener(new OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (mLocationMarker != null)
                    mLocationMarker.remove();
                mLocationMarker = map
                        .addMarker(new MarkerOptions()
                                .position(
                                        new LatLng(point.latitude,
                                                point.longitude))
                                .title("meet people here!")
                                .draggable(true));
                mLocationMarker.showInfoWindow();

                lat = point.latitude;
                lon = point.longitude;
                saveButton.setVisibility(View.VISIBLE);

            }
        });


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

    private class UpdateTask extends AsyncTask<Void, Void, Integer> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(getActivity());
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected void onPostExecute(Integer result) {
            switch (result.intValue()) {
                case -1:
                    Toast.makeText(getActivity(), "check internet connection",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(getActivity(), "your meeting location is updated",
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            pBar.dismiss();

        }

        @Override
        protected Integer doInBackground(Void... params) {
            return JewarApi.update_location(lat, lon);
        }
    }

    private class DisplayTask extends
            AsyncTask<Void, Void, APIResponse> {
        private ProgressDialog pBar;

        @Override
        protected void onPreExecute() {
            pBar = new ProgressDialog(getActivity());
            pBar.setCancelable(false);
            pBar.show();
        }

        @Override
        protected void onPostExecute(APIResponse response) {
            switch (response.getStatus()) {
                case -1:
                    Toast.makeText(getActivity(), "check internet connection",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    Toast.makeText(getActivity(), "can't update try again",
                            Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    ArrayList<Double> locs = (ArrayList<Double>) response.getData();

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(locs.get(0), locs.get(1)), 0));
                    mLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(new LatLng(locs.get(0), locs.get(1))).title("meet people here!")
                            .draggable(false));
                    mLocationMarker.showInfoWindow();

                    break;
                default:
                    break;
            }
            pBar.dismiss();

        }

        @Override
        protected APIResponse doInBackground(Void... params) {
            return JewarApi.get_location();
        }

    }

    private void setGPSMarkerlocation() {

        mMap.setMyLocationEnabled(true);

        LocationManager locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);
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