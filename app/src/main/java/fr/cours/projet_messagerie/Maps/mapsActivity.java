package fr.cours.projet_messagerie.Maps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.cours.projet_messagerie.R;
import fr.cours.projet_messagerie.databinding.ActivityMapsBinding;

public class mapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Intent intent = getIntent();
        Double longitude = intent.getDoubleExtra("longitude", 0.0);
        Double latitude = intent.getDoubleExtra("latitude", 0.0);
        // ajoute le marker
        LatLng msgPosition = new LatLng(longitude, latitude);
        mMap.addMarker(new MarkerOptions().position(msgPosition));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(msgPosition));
    }

    public void onClickRetour(View view){
        finish();
    }
}