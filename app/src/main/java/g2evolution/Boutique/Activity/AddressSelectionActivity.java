package g2evolution.Boutique.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

import g2evolution.Boutique.R;

public class AddressSelectionActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnCameraIdleListener {
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    GoogleMap mMap;
    FusedLocationProviderClient mFusedLocationClient;
    LocationRequest mLocationRequest;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    String address, city, state, country, postalCode, knownName,subAdminArea,area;
    TextView xTvAdrress;
    ImageView xIvSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_selection);
        mInitWidgets();
        mInitObjects();

    }

    private void mInitWidgets() {
        xTvAdrress = findViewById(R.id.xTvAdrress);
        xIvSelect = findViewById(R.id.xIvSelect);
        xIvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (area == null) {
                    area = "";
                }
                if (city == null) {
                    city = "";
                }
                if (knownName == null) {
                    knownName = "";
                }
                if (state == null) {
                    state = "";
                }
                if (postalCode == null) {
                    postalCode = "";
                }

                Log.e("testing",area);
                Log.e("testing",city);
                Log.e("testing",state);
                Log.e("testing",postalCode);
                Log.e("testing",knownName);

                Intent intent = new Intent();
                intent.putExtra("Address", area);
                intent.putExtra("city", city);
                intent.putExtra("knownName", knownName);
                intent.putExtra("state", state);
                intent.putExtra("postalCode", postalCode);
                setResult(RESULT_OK, intent);
                finish();


            }
        });
    }

    private void mInitObjects() {

        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (checkPermmission()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        AddressSelectionActivity.this,
                        new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_CODE
                );
                return;
            }
            mFusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                if (location != null) {

                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mCurrLocationMarker = mMap.addMarker(markerOptions);
                    //move map camera
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                }
            });
        }

    }

    private Boolean checkPermmission() {
        boolean isPermissionGranted = false;

        if (ContextCompat.checkSelfPermission(
                AddressSelectionActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                + ContextCompat.checkSelfPermission(
                AddressSelectionActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                    AddressSelectionActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_CODE
            );
            isPermissionGranted = false;

        } else {
            isPermissionGranted = true;
            // Do something, when permissions are already granted
        }
        return isPermissionGranted;
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if ((grantResults.length > 0) && (grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    // Permissions are granted
                    Toast.makeText(AddressSelectionActivity.this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    finish();
                }
                return;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    AddressSelectionActivity.this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_CODE
            );
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnCameraIdleListener(this);


    }

    @Override
    public void onCameraIdle() {
        if (mMap != null) {
            mMap.clear();
        }

        LatLng mPosition = mMap.getCameraPosition().target;
        xTvAdrress.setText(getCompleteAddressString(mPosition.latitude, mPosition.longitude));
        Log.e("testing", "lat +" + mPosition.latitude);
        Log.e("testing", "lang +" + mPosition.longitude);

        // address, city, state, country, postalCode, knownName;

    }

    @Override
    public void onMapLongClick(LatLng latLng) {


    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
//                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");

                    address =addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                    area=addresses.get(0).getThoroughfare()+addresses.get(0).getSubLocality();
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    subAdminArea = addresses.get(0).getSubAdminArea();

                    Log.e("testingaddress",address);
                    Log.e("testingcity",city);
                    Log.e("testingstate",state);
                    Log.e("testingpostalCode",postalCode);
                    Log.e("testingknownName",knownName);
                    Log.e("testingsubAdminArea",subAdminArea);


                }
                strAdd = strReturnedAddress.toString();
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}