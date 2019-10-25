package g2evolution.Boutique;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

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

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCallback2=this;


        Hidesoftkeyboard();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar(                                                                      ).setTitle(Html.fromHtml("<font color='#FFFFFF'>Home</font>"));

    //   toolbar.setNavigationIcon(R.drawable.menu);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        ActionBar actionBar = getSupportActionBar();
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

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new View_Pager_Fragment()).commit();

        setUpReccyler2();
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


            linearbottom.setVisibility(View.GONE);
      Profile_Fragment fragment2 = new Profile_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();

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

}
