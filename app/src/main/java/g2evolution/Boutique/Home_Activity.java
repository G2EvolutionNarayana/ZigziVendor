package g2evolution.Boutique;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import g2evolution.Boutique.Activity.Activity_BookDelivery;
import g2evolution.Boutique.Activity.Activity_Profile;
import g2evolution.Boutique.Adap.Bottom_Navigation_Adapter;
import g2evolution.Boutique.entit.Bottom_Navigation_List;
import g2evolution.Boutique.frag.Contact_Us_Fragment;
import g2evolution.Boutique.frag.E_Commerce_Fragment;
import g2evolution.Boutique.frag.Home_Fragment;
import g2evolution.Boutique.frag.Profile_Fragment;
import g2evolution.Boutique.frag.Resource_Management_Fragment;
import g2evolution.Boutique.frag.View_Pager_Fragment;

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Home_Fragment.OnFragmentInteractionListener,
        Profile_Fragment.OnFragmentInteractionListener,
        Contact_Us_Fragment.OnFragmentInteractionListener,
        E_Commerce_Fragment.OnFragmentInteractionListener,
        Resource_Management_Fragment.OnFragmentInteractionListener,
        Bottom_Navigation_Adapter.OnItemlevelsinside{


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    LocationManager locationManager;
    String provider;

    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    NavigationView navigationView;

    RecyclerView navigation_recycler;


    Bottom_Navigation_Adapter.OnItemlevelsinside mCallback2;
    private ArrayList<Bottom_Navigation_List> bottom_navigation_lists = new ArrayList<Bottom_Navigation_List>();
    Bottom_Navigation_Adapter bottom_navigation_adapter;

    String[]Name= new String[]{"Materials",   "Machine Equipment",  "Employees Hiring","Cloth Materials"};
    String[]Id= new String[]{"1",   "2",  "3","4"};

    LinearLayout linearbottom;
    ActionBar actionBar;
    boolean doubleBackToExitPressedOnce = false;

    TextView textzigzi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCallback2=this;


        Hidesoftkeyboard();

        textzigzi = (TextView) findViewById(R.id.textzigzi);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar(                                                                      ).setTitle(Html.fromHtml("<font color='#FFFFFF'>Home</font>"));

    //   toolbar.setNavigationIcon(R.drawable.menu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setTitle("Boutique");

        }

        navigationView = (NavigationView) findViewById(R.id.navigation);
        linearbottom = (LinearLayout) findViewById(R.id.linearbottom);
        navigation_recycler = (RecyclerView) findViewById(R.id.navigation_recycler);
        navigation_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Home_Activity.this, LinearLayoutManager.HORIZONTAL,false);
        navigation_recycler.setLayoutManager(mLayoutManager);



        navigationView.setNavigationItemSelectedListener(this);

       /* locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        provider = locationManager.getBestProvider(new Criteria(), false);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);


        checkLocationPermission();*/

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new View_Pager_Fragment()).commit();

        setUpReccyler2();


        checkLocationPermission();

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {

            Intent intent=new Intent(Home_Activity.this,Home_Activity.class);
            startActivity(intent);
            finish();

        }

        else  if (id == R.id.profile) {


          //  linearbottom.setVisibility(View.GONE);
            Intent intent = new Intent(Home_Activity.this, Activity_Profile.class);
            startActivity(intent);


          /*  Profile_Fragment fragment2 = new Profile_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();*/

        }
        else  if (id == R.id.bookdelivery) {

            linearbottom.setVisibility(View.GONE);
          /*  E_Commerce_Fragment fragment2 = new E_Commerce_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();*/

          Intent intent = new Intent(Home_Activity.this, Activity_BookDelivery.class);
          startActivity(intent);
        }
        else  if (id == R.id.ecommerce) {

            linearbottom.setVisibility(View.GONE);
          /*  E_Commerce_Fragment fragment2 = new E_Commerce_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();*/

            Intent intent = new Intent(Home_Activity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.lisense)
        {
            textzigzi.setText("Resource Management");
            linearbottom.setVisibility(View.GONE);
            Resource_Management_Fragment fragment2 = new Resource_Management_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();
        }
        else if(id==R.id.contact_us)
        {
            linearbottom.setVisibility(View.GONE);
            Contact_Us_Fragment fragment2 = new Contact_Us_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();
        }
        else if(id==R.id.policy)
        {  linearbottom.setVisibility(View.GONE);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse("http://google.in/"));
            startActivity(browserIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Hidesoftkeyboard() {

        View focusedView = Home_Activity.this.getCurrentFocus();
        if(focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Home_Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }




    private void setUpReccyler2() {
        bottom_navigation_lists =new ArrayList<Bottom_Navigation_List>();

        for(int i=0;i<Name.length;i++){
            Bottom_Navigation_List feedInfo = new Bottom_Navigation_List();
            feedInfo.setName(Name[i]);
            feedInfo.setId(Id[i]);

            bottom_navigation_lists.add(feedInfo);
        }
        bottom_navigation_adapter = new Bottom_Navigation_Adapter(Home_Activity.this,bottom_navigation_lists, mCallback2);
        navigation_recycler.setAdapter(bottom_navigation_adapter);
    }

    @Override
    public void OnItemlevelsinside(int pos, String qty, int status) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission nedded")
                        .setMessage("Location Permission nedded")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Home_Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
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
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                      //  locationManager.requestLocationUpdates(provider, 400, 1, (android.location.LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }


        }
    }


}
