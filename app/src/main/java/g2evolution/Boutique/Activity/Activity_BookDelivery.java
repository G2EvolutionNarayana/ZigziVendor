package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import g2evolution.Boutique.Fragment.Fragment_BookDelivery;
import g2evolution.Boutique.Fragment.Fragment_BookingDeliveryHistory;
import g2evolution.Boutique.GpsTracker;
import g2evolution.Boutique.R;
import g2evolution.Boutique.frag.Resource_Management_Fragment;

public class Activity_BookDelivery extends AppCompatActivity {

    TextView textbookdelivery, textbookhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_delivery);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textbookdelivery = (TextView) findViewById(R.id.textbookdelivery);
        textbookhistory = (TextView) findViewById(R.id.textbookhistory);


        Fragment_BookDelivery fragment2 = new Fragment_BookDelivery();
        FragmentTransaction fragmentTransaction2 =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container, fragment2);
        fragmentTransaction2.commit();
        textbookdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textbookdelivery.setTextColor(Color.WHITE);
                textbookdelivery.setBackgroundColor(Color.parseColor("#d96666"));
                textbookhistory.setBackgroundColor(Color.parseColor("#F2F2F2"));
                textbookhistory.setTextColor(Color.parseColor("#AAAAAA"));

                Fragment_BookDelivery fragment2 = new Fragment_BookDelivery();
                FragmentTransaction fragmentTransaction2 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container, fragment2);
                fragmentTransaction2.commit();

            }
        });
        textbookhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textbookhistory.setTextColor(Color.WHITE);
                textbookdelivery.setTextColor(Color.parseColor("#AAAAAA"));
                textbookdelivery.setBackgroundColor(Color.parseColor("#F2F2F2"));
                textbookhistory.setBackgroundColor(Color.parseColor("#d96666"));
                Fragment_BookingDeliveryHistory fragment2 = new Fragment_BookingDeliveryHistory();
                FragmentTransaction fragmentTransaction2 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.fragment_container, fragment2);
                fragmentTransaction2.commit();
            }
        });



    }


}
