package com.example.mytreeapplication.frags;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mytreeapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsFragment extends Fragment implements OnMapReadyCallback
//        GoogleApiClient.ConnectionCallbacks,
//        GoogleApiClient.OnConnectionFailedListener,
//        GoogleMap.OnMarkerDragListener,
//        GoogleMap.OnMapLongClickListener,
//        GoogleMap.OnMarkerClickListener
            {

    private double longitude;
    private double latitude;
    private GoogleApiClient googleApiClient;
    private static final String TAG = "MapsFrag";
    GoogleMap map;
                private GoogleMap.OnMarkerDragListener context;

                public MapsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_maps, container, false);
        SupportMapFragment supportMapFragment=(SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map1);
        supportMapFragment.getMapAsync(this);
       return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map=googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng india = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(india).title("Marker in India"));
        map.moveCamera(CameraUpdateFactory.newLatLng(india));
       // map.setOnMarkerDragListener(context);
       // map.setOnMapLongClickListener(context);
    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onMapLongClick(LatLng latLng) {
//        map.addMarker(new MarkerOptions().position(latLng).draggable(true));
//    }
//
//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        return false;
//    }
//
//    @Override
//    public void onMarkerDragStart(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDrag(Marker marker) {
//
//    }
//
//    @Override
//    public void onMarkerDragEnd(Marker marker) {
//
//    }
//    @Override
//    protected void onStart() {
//        googleApiClient.connect();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        googleApiClient.disconnect();
//        super.onStop();
//    }

    //Getting current location
//    private void getCurrentLocation() {
//        map.clear();
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location != null) {
//            //Getting longitude and latitude
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//
//            //moving the map to location
//            moveMap();
//        }
//    }
//    private void moveMap() {
//        /**
//         * Creating the latlng object to store lat, long coordinates
//         * adding marker to map
//         * move the camera with animation
//         */
//        LatLng latLng = new LatLng(latitude, longitude);
//        map.addMarker(new MarkerOptions()
//                .position(latLng)
//                .draggable(true)
//                .title("Marker in India"));
//
//        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//        map.animateCamera(CameraUpdateFactory.zoomTo(15));
//        map.getUiSettings().setZoomControlsEnabled(true);
//
//
//    }

}
