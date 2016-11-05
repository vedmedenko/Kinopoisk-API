package kode.kinopoisk.vedmedenko.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import kode.kinopoisk.vedmedenko.R;
import kode.kinopoisk.vedmedenko.utils.ConstantsManager;
import timber.log.Timber;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private String cinemaName;
    private double lat, lon;

    public static Intent getStartIntent(@NonNull Context context, @NonNull String cinemaName, double lat, double lon) {
        Intent intent = new Intent(context, MapsActivity.class);
        intent.putExtra(ConstantsManager.EXTRA_CINEMA_NAME, cinemaName);
        intent.putExtra(ConstantsManager.EXTRA_LAT, lat);
        intent.putExtra(ConstantsManager.EXTRA_LON, lon);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        cinemaName = getIntent().getStringExtra(ConstantsManager.EXTRA_CINEMA_NAME);
        lat = getIntent().getDoubleExtra(ConstantsManager.EXTRA_LAT, 0.0d);
        lon = getIntent().getDoubleExtra(ConstantsManager.EXTRA_LON, 0.0d);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng cinema = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(cinema).title(getString(R.string.marker_text, cinemaName)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 14.0f));
    }
}
