package g2evolution.Boutique.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

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

import g2evolution.Boutique.Activity.Activity_BookDelivery;
import g2evolution.Boutique.Activity.Activity_BookingDelivery2;
import g2evolution.Boutique.GpsTracker;
import g2evolution.Boutique.R;

public class Fragment_BookDelivery extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;

    LatLng latLng;
    String strlatitude, strlongitude;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    LocationRequest mLocationRequest;

    String strcurrentlanti, strcurrentlong;
    double latitude;
    double longitude;

    String strcity2, strstate2, strcountry2, strpostal2, strsubadminarea2;

    String strupdatelat = "";
    String strupdaelong = "" ;
    String strpicklat = "";
    String strpicklong = "";
    String strlocationposition;
    private static final int REQUEST_SELECT_PLACE = 1000;

    TextView mLocationMarkerText;
    private LatLng mCenterLatLong;
    ImageView imageMarker;
    List<Place.Field> fields = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG);
    TextView picuplocation;
    TextView droplocation;
    TextView textcontinue;

    String strpicklatitude, strpicklongitude, strpickaddress, strdroplatitude, strdroplongitude, strdropaddress;

    private static final String LOG_TAG = "MainActivity";





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookdelivery, container, false);


        if (!Places.isInitialized()) {
            Places.initialize(getActivity(), getString(R.string.api_key), Locale.US);
        }

        mLocationMarkerText = (TextView)rootView.findViewById(R.id.locationMarkertext);
        imageMarker=(ImageView) rootView.findViewById(R.id.imageMarker);
        picuplocation = (TextView) rootView.findViewById(R.id.picuplocation);
        droplocation = (TextView) rootView.findViewById(R.id.droplocation);
        textcontinue = (TextView) rootView.findViewById(R.id.textcontinue);
        createLocationRequest();
        buildGoogleApiClient();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        picuplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strlocationposition="1";
                imageMarker.setImageResource(R.drawable.greenmarker);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields)
//                        .build(getActivity());
//                startActivityForResult(intent, REQUEST_SELECT_PLACE);

            }
        });
        droplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strlocationposition="2";
                imageMarker.setImageResource(R.drawable.redmarker);
//                Intent intent = new Autocomplete.IntentBuilder(
//                        AutocompleteActivityMode.OVERLAY, fields)
//                        .build(getActivity());
//                startActivityForResult(intent, REQUEST_SELECT_PLACE);

            }
        });
        textcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (strpicklatitude == null || strpicklatitude.trim().length() == 0 || strpicklatitude.trim().equals("null") || strpicklatitude.trim().equals("0.0")){
                    Toast.makeText(getActivity(), "Please select Pickup Location", Toast.LENGTH_SHORT).show();
                }else{
                    if (strdroplatitude == null || strdroplatitude.trim().length() == 0 || strdroplatitude.trim().equals("null") || strdroplatitude.trim().equals("0.0")){
                        Toast.makeText(getActivity(), "Please select Drop Location", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent = new Intent(getActivity(), Activity_BookingDelivery2.class);
                        SharedPreferences prefuserdata = getActivity().getSharedPreferences("booklocation", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("strpicklatitude", "" + strpicklatitude);
                        prefeditor.putString("strpicklongitude", "" + strpicklongitude);
                        prefeditor.putString("strpickaddress", "" + strpickaddress);
                        prefeditor.putString("strdroplatitude", "" + strdroplatitude);
                        prefeditor.putString("strdroplongitude", "" + strdroplongitude);
                        prefeditor.putString("strdropaddress", "" + strdropaddress);

                        prefeditor.commit();
                        startActivity(intent);
                    }
                }

            }
        });




        return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_SELECT_PLACE) {

            if (resultCode == android.app.Activity.RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                this.onPlaceSelected(place);
                Log.e("testing", "Place: " + place.getName() + ", " + place.getId());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                this.onError(status);
                Log.e("testing", status.getStatusMessage());
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }


          /*  if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                this.onPlaceSelected(place);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                this.onError(status);
            }*/
        }


        //   new UploadTaskImage().execute();


    /*    if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);*/


    }
    public void onError(Status status) {
        Log.e(LOG_TAG, "onError: Status = " + status.toString());
        Toast.makeText(getActivity(), "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

    //-----------------------------------google places--------------------------------------
    public void onPlaceSelected(Place place) {

        LatLng latLng = place.getLatLng();

        // strname = place.getName().toString();
        // straddress = place.getAddress().toString();
        Log.e("testing", "latitude" + latLng.latitude);
        Log.e("testing", "longitude" + latLng.longitude);


        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    latLng.latitude, latLng.longitude, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append("\n");
                }
                sb.append(address.getLocality()).append("\n");
                sb.append(address.getPostalCode()).append("\n");
                sb.append(address.getCountryName());
                result = sb.toString();
              /*  strcity = address.getLocality();
                strpostal = address.getPostalCode();
                strcountry = address.getCountryName();
                strstate = address.getAdminArea();
                strlocality = address.getLocality();
                stradminarea = address.getAdminArea();
                strsubadminarea = address.getAdminArea();
                Double doulatitude = address.getLatitude();
                Double doulongitude = address.getLongitude();

                Log.e("testing", "location strcity" + strcity);
                Log.e("testing", "location strpostal" + strpostal);
                Log.e("testing", "location strcountry" + strcountry);
                Log.e("testing", "location strstate" + strstate);
                Log.e("testing", "location strsublocality" + strlocality);
                Log.e("testing", "location stradminarea" + stradminarea);
                Log.e("testing", "location strsubadminarea" + strsubadminarea);
                Log.e("testing", "location doulongitude" + doulongitude);
                Log.e("testing", "location strpostal" + strpostal);*/

                Double doulatitude = address.getLatitude();
                Double doulongitude = address.getLongitude();

                String strlanti = doulatitude.toString();
                String strlong = doulongitude.toString();
                picuplocation.setText(place.getAddress());

                // strupdatelat = String.valueOf(strlanti);
                // strupdaelong = String.valueOf(strlong);

                latitude = Double.parseDouble(strlanti);
                longitude = Double.parseDouble(strlong);

                mMap.addMarker(new MarkerOptions()
                                //.title(strname)
                                //.snippet(strempid)
                                .position(new LatLng(latitude, longitude
                                ))
                        //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                        //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))


                );
                LatLng dest = new LatLng(latitude, longitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest, 17.0f));




            }
        } catch (IOException e) {
            // Log.e(TAG, "Unable connect to Geocoder", e);
        }


    }

    private void currentlocation() {
        //-----------code for current location---------------------------------------------------------//



/*
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(getActivity(), GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();*/
        GpsTracker gps = new GpsTracker(getActivity());
        if (gps.canGetLocation()) {
            gps.getLatitude();
            gps.getLongitude();

            //  Toast.makeText(getActivity(), "Latitude Value-" + gps.getLatitude() + "--" + "Longitude Value-" + gps.getLongitude(), Toast.LENGTH_LONG).show();
            // Here I am using Geocoder class to get address through Latitude and Longitude
            Geocoder geocoder;
            List<Address> addresses=null;
            geocoder = new Geocoder(getActivity(), Locale.getDefault());
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            if (latitude == 0.0) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
            }

            //save latitude and longitude in sharepreference

            Log.e("latitude", "latitude--" + latitude);
            Log.e("longitude", "longitude--" + longitude);

            System.out.println(latitude);
            System.out.println(longitude);

            try {

                Log.e("latitude", "inside latitude--" + latitude);


                addresses = geocoder.getFromLocation(latitude, longitude, 1);

                if (addresses != null && addresses.size() > 0) {
                  /*  address = addresses.get(0).getAddressLine(0);
                    city = addresses.get(0).getLocality();
                    state = addresses.get(0).getAdminArea();
                    country = addresses.get(0).getCountryName();
                    postalCode = addresses.get(0).getPostalCode();
                    knownName = addresses.get(0).getFeatureName();
                    area = addresses.get(0).getSubAdminArea();
                    area1 = addresses.get(0).getSubLocality();
                    String as1 = addresses.get(0).getPremises();
                    System.out.println(address + " , " + city + " , " + area + " , " + area1 + " , " + as1 + " , " + state + " , " + country + " , " + postalCode + " , " + knownName);

                    Log.e("testing","location in current location ========= "+area1);
*/
                    /* 687, 1st Cross Chowedeshwari Road , Bengaluru , null , Marathahalli , null , Karnataka , India , 560037 , 687*/
              /*   Log.e("testing","mAutocompleteTextViewpostalCode"+postalCode);
                SharedPreferences prefuserdata1 = MainActivity.this.getSharedPreferences("placeslocation", 0);
                SharedPreferences.Editor prefeditor =prefuserdata1.edit();
                prefeditor.putString("Latitudevalue", "" + latitude);
                prefeditor.putString("Longitudevalue", "" + longitude);
                //   prefeditor.putString("postalcode", "" + postalCode);
                prefeditor.putString("postalcode", "" + postalCode);
                prefeditor.commit();*/

                    strcurrentlanti = String.valueOf(latitude);
                    strcurrentlong = String.valueOf(longitude);

                 /*   mMap.addMarker(new MarkerOptions()
                                    //.title(strname)
                                    //.snippet(strempid)
                                    .position(new LatLng(latitude, longitude
                                    ))
                            //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            //  .icon(BitmapDescriptorFactory.fromResource(R.drawable.car))


                    );*/
                    LatLng dest = new LatLng(latitude, longitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest, 17.0f));


                    // new GetVehicleList().execute();

                    // textlocation.setText(area1);
                    //  strlanti = String.valueOf(latitude);
                    //  strlong = String.valueOf(longitude);


                }

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                //setcurrentlocation.setText(address + " " + city + " " + country);
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
       /* LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
        currentlocation();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buildGoogleApiClient();
            //  mMap.setMyLocationEnabled(true);

          /*  if (ActivityCompat.checkSelfPermission(getActivity(),
                    permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();

                mMap.setMyLocationEnabled(true);

            } else {
                //Request Location Permission
                checkLocationPermission();
            }*/

        }
        else {

            buildGoogleApiClient();
            //   mMap.setMyLocationEnabled(true);

        }

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {

                CameraPosition position = mMap.getCameraPosition();
/*
                Location location = mMap.getMyLocation();
                LatLng target = new LatLng(location.getLatitude(), location.getLongitude());*/


             /*   CameraPosition.Builder builder = new CameraPosition.Builder();
                builder.zoom(15);
                builder.target(target);*/

                mCenterLatLong = position.target;
                mMap.clear();

                double latitude;
                double longitude;
                try {

                    Location mLocation = new Location("");
                    mLocation.setLatitude(mCenterLatLong.latitude);
                    mLocation.setLongitude(mCenterLatLong.longitude);

                    Geocoder geocoder;
                    List<Address> addresses=null;
                    geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    latitude = mCenterLatLong.latitude;
                    longitude = mCenterLatLong.longitude;

                    if (latitude == 0.0) {

                        latitude = mCenterLatLong.latitude;
                        longitude = mCenterLatLong.longitude;
                    }
                    try {

                        addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null && addresses.size() > 0) {
                            String address = addresses.get(0).getAddressLine(0);

                            // mLocationMarkerText.setText("Lat : " + mCenterLatLong.latitude + "," + "Long : " + mCenterLatLong.longitude);
                            mLocationMarkerText.setText(address);

                            if (strlocationposition==null||strlocationposition.length()==0||strlocationposition.equals("0")||strlocationposition.equals("1")){

                                //  mMap.clear();
                                // startIntentService(mLocation);
                                Double dlat1 = mCenterLatLong.latitude;
                                Double dlong1 = mCenterLatLong.longitude;

                                strpicklat = String.valueOf(dlat1);
                                strpicklong = String.valueOf(dlong1);

                                picuplocation.setText(address);
                                strpickaddress = address;


                                //  setData();



                                if (strpicklat == null || strpicklat.trim().length() == 0 || strpicklat.equals("null")){



                                }else{

                                    mMap.addMarker(new MarkerOptions()
                                            //.title(strname)
                                            //.snippet(strempid)
                                            .position(new LatLng(Double.valueOf(strpicklat), Double.valueOf(strpicklong)
                                            ))
                                            //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmarker))

                                    );
                                    strpicklatitude = strpicklat;
                                    strpicklongitude = strpicklong;

                                    double latitudes1 = Double.parseDouble(strpicklat);
                                    double longitudes1 = Double.parseDouble(strpicklong);

                                    LatLng latLng1 = new LatLng(latitudes1,longitudes1);



                                    //  new GetVehicleDetails_CurrentLocation().execute();


                                }

                                if (strupdatelat == null || strupdatelat.trim().length() == 0 || strupdatelat.equals("null")){




                                }else{

                                    mMap.addMarker(new MarkerOptions()
                                            //.title(strname)
                                            //.snippet(strempid)
                                            .position(new LatLng(Double.valueOf(strupdatelat), Double.valueOf(strupdaelong)
                                            ))
                                            //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.redmarker))

                                    );
                                    strdroplatitude = strupdatelat;
                                    strdroplongitude = strupdaelong;

                                    double latitudes1 = Double.parseDouble(strupdatelat);
                                    double longitudes1 = Double.parseDouble(strupdaelong);

                                    LatLng latLng1 = new LatLng(latitudes1,longitudes1);




                                }


                            }else {


                                Double dlat = mCenterLatLong.latitude;
                                Double dlong = mCenterLatLong.longitude;
                                //  mMap.clear();
                                strupdatelat = String.valueOf(dlat);
                                strupdaelong = String.valueOf(dlong);

                                droplocation.setText(address);
                                strdropaddress = address;
                                if (strpicklat == null || strpicklat.trim().length() == 0 || strpicklat.equals("null")){



                                }else{

                                    mMap.addMarker(new MarkerOptions()
                                            //.title(strname)
                                            //.snippet(strempid)
                                            .position(new LatLng(Double.valueOf(strpicklat), Double.valueOf(strpicklong)
                                            ))
                                            //     .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.greenmarker))
                                    );
                                    strpicklatitude = strpicklat;
                                    strpicklongitude = strpicklong;

                                    double latitudes1 = Double.parseDouble(strpicklat);
                                    double longitudes1 = Double.parseDouble(strpicklong);

                                    LatLng latLng1 = new LatLng(latitudes1,longitudes1);



                                }

                                if (strupdatelat == null || strupdatelat.trim().length() == 0 || strupdatelat.equals("null")){



                                }else{

                                    if (mMap==null||mMap.equals("")){



                                    }else {

                                        mMap.addMarker(new MarkerOptions()
                                                //.title(strname)
                                                //.snippet(strempid)
                                                .position(new LatLng(Double.valueOf(strupdatelat), Double.valueOf(strupdaelong)
                                                ))
                                                //  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE))
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.redmarker))

                                        );
                                        strdroplatitude = strupdatelat;
                                        strdroplongitude = strupdaelong;

                                        double latitudes1 = Double.parseDouble(strupdatelat);
                                        double longitudes1 = Double.parseDouble(strupdaelong);

                                        LatLng latLng1 = new LatLng(latitudes1,longitudes1);

                                        //  new GetVehicleDetails_CurrentLocation().execute();

                                    }

                                }

                            }


                            strcity2 = addresses.get(0).getLocality();
                            strstate2 = addresses.get(0).getAdminArea();
                            strcountry2 = addresses.get(0).getCountryName();
                            strpostal2 = addresses.get(0).getPostalCode();
                            strsubadminarea2 = addresses.get(0).getSubAdminArea();
                            String  strlocality = addresses.get(0).getSubLocality();
                            String as1 = addresses.get(0).getPremises();

                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                        //setcurrentlocation.setText(address + " " + city + " " + country);

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }

            }

        });


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    @Override
    public void onLocationChanged(Location location) {

        //To clear map data
        //   mMap.clear();
        //To hold location
        latLng = new LatLng(location.getLatitude(), location.getLongitude());

        Double dlat = location.getLatitude();
        Double dlon = location.getLongitude();

        strlatitude = String.valueOf(dlat);
        strlongitude = String.valueOf(dlon);

   /*     LatLng sydney = new LatLng(dlat, dlon);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*/
        // lat1=location.getLatitude();
        // lon1=location.getLongitude();

        //  vlat = "12.987510";
        // vlon = "77.737672";
        //   openProfile();


    }


    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnected(Bundle bundle) {

        //mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

      /*  if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            }, 10);
        }*/


    }

    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }
    @Override
    public void onConnectionSuspended(int i) {
        //   mPlaceArrayAdapter.setGoogleApiClient(null);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new android.app.AlertDialog.Builder(getActivity())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has Activity_BookDelivery.t shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();

                }

                return;

            }

            // other 'case' lines to check for other
            // permissions this app might request

        }

    }

}
