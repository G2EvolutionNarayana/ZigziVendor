package g2evolution.GMT;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.GMT.Activity.Activity_Aboutus;
import g2evolution.GMT.Activity.Activity_TermsandConditions;
import g2evolution.GMT.Activity.Activity_cart;
import g2evolution.GMT.Activity.Activity_search;
import g2evolution.GMT.Activity.Login;
import g2evolution.GMT.Activity.My_Orders;
import g2evolution.GMT.Adapter.Adapter_main;
import g2evolution.GMT.FeederInfo.FeederInfo_main;
import g2evolution.GMT.FeederInfo.Info_MyCart;
import g2evolution.GMT.FeederInfo.SectionDataModel;
import g2evolution.GMT.FeederInfo.Spinner_model;
import g2evolution.GMT.Fragment.Fragment_AccountInformation;
import g2evolution.GMT.Fragment.Fragment_Categories;
import g2evolution.GMT.Fragment.fragment_home;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.HttpHandler;
import g2evolution.GMT.Utility.JSONParser;
import g2evolution.GMT.Utility.NotificationUtils;
import g2evolution.GMT.app.Config;

public class MainActivity1 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Adapter_main.OnItemClick{


    JSONParser jsonParser = new JSONParser();
    private ArrayList<Info_MyCart> mListFeederInfo;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle toggle;
    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    FrameLayout mViewContainer;
    TextView login;
    LinearLayout loginlinear;
    private FloatingActionButton fab;
    ArrayList<String> listdata = new ArrayList<String>();
    private MenuItem mMenuBellItem;


    /*//------------------searchable view in actionbar---------------------
    private MenuItem searchMenuItem;
    MenuItemCompat menuitem = null;
    SearchView searchView;
    RelativeLayout relativemain;
    ListView listview;
    ArrayAdapter<String> adapter;

    private CustomAdapter customAdapter;
    ListView listView;
    Cursor cursor;
    StudentRepo studentRepo ;
    private static DBHelper dbHelper;*/

    //----------------------------------------filter----------------------------------------------------

    Spinner catagoryspin;
    TextView filtersubmit;
    String spinnerid;
    ArrayList<Spinner_model> spinner_json;
    ArrayList<String> spinner_array;

    String searchType;
    TextView texthome,textoffers,account,aboutus,termsconditon;

    String status,amount,message,total_record;

    String UserId,cartmenucount;
    private MenuItem menuItemcart;

    String  headers;
    TextView profilename,profileid;
    private Adapter_main.OnItemClick mCallback;

    //-------------------------------navigationrecycler-------------------------------------------------

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_main> allItems1 = new ArrayList<FeederInfo_main>();
    Context context;
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_main> mFeederInfo;
    private MainActivity1 madapter;
    Adapter_main mAdapterFeeds;
    RecyclerView rView;

    String []Name =new String[]{"Electronic Products","Accessorices","Grocery Products","Photo Frame",};


   LinearLayout linearsearch;
   String Username,Usermail,Usermob;
   TextView lowtohigh,hightolow,newest,offer;

   // private String TAG = fragment_home.class.getSimpleName();

    private ProgressDialog pDialog;
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;


    String strquantity, strcartid;

    boolean doubleBackToExitPressedOnce = false;


    // ----FCM server key on android@g2evolution.co.in----
    //------------------------FCM Notification--------------------------
    private static final String TAG = MainActivity1.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------
    String regId;

TextView My_Order,My_Cart;

    Dialog dialog; // apartment dialog
    Spinner spinapartment;

    String capartmentid_id,viewUserId;
    ImageView items_cart,items_filter,items_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        context = this;

        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
         viewUserId = prefuserdata23.getString("UserId", "");


        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewContainer = (FrameLayout) findViewById(R.id.fragment_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>MMEShop</font>"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));

        capartmentid_id ="0";

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");


        allSampleData = new ArrayList<SectionDataModel>();
        my_recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);

        mCallback = this;

        items_cart=(ImageView)findViewById(R.id.image);
        items_filter=(ImageView)findViewById(R.id.filter);
        items_location=(ImageView)findViewById(R.id.location);

        items_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity1.this, Activity_cart.class);
                    startActivity(intent);

                }

            }
        });

        items_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        texthome = (TextView) findViewById(R.id.texthome);
        textoffers = (TextView) findViewById(R.id.textoffers);
        account = (TextView) findViewById(R.id.accounts);
        My_Order = (TextView) findViewById(R.id.myorders);
        My_Cart = (TextView) findViewById(R.id.cart);
        aboutus = (TextView) findViewById(R.id.about);
        termsconditon = (TextView) findViewById(R.id.terms);

        profilename = (TextView) findViewById(R.id.profilename);
        profileid = (TextView) findViewById(R.id.profileid);

        Log.e("testing","Usermail===="+Usermail);
        Log.e("testing","Username===="+Username);

        if (Username==null||Username.length()== 0 ||Username.equals("null")||Username.equals("0")||Usermail==null||Usermail.length()==0||Usermail.equals("null")||Usermail.equals("")){

            profilename.setText("Please Login");
            profilename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(MainActivity1.this,Login.class);
                    startActivity(intent);
                }
            });

        }else {

            profilename .setText(Username);
            profileid .setText(Usermail);
        }


        //   listview = (ListView) findViewById(R.id.listView1);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new fragment_home()).commit();


       /*
        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {
            new searchfunction().execute();
        } else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }

*/

        //------------------------FCM Notification--------------------------

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }

            }
        };

        displayFirebaseRegId();

//------------------------FCM Notification--------------------------

        dialog = new Dialog(MainActivity1.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //setocationdialog();

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(MainActivity1.this));
        //setUpRecycler();
        // context = this;
        ///  lLayout = new GridLayoutManager(Activity_cart.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        linearsearch = (LinearLayout)findViewById(R.id.linearsearch);
        linearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(MainActivity1.this, Activity_search.class);
                startActivity(i);

            }
        });


        My_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity1.this, My_Orders.class);
                    startActivity(intent);
                }

            }
        });

        My_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");
                }else {

                    Intent intent = new Intent(MainActivity1.this, Activity_cart.class);
                    startActivity(intent);
                }


            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");
                }else {

                    Intent intent = new Intent(MainActivity1.this, Fragment_AccountInformation.class);
                    startActivity(intent);

                }


            }
        });


        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, Activity_Aboutus.class);
                startActivity(intent);

               /* mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new Fragment_aboutus()).commit();*/

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        termsconditon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity1.this, Activity_TermsandConditions.class);
                startActivity(intent);

              /*  mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new Fragment_terms()).commit();
*/


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        ConnectionDetector cd = new ConnectionDetector(MainActivity1.this);
        if (cd.isConnectingToInternet()) {


        // new cartcount().execute();
            //  new Fetchprofileimage().execute();

            new GetCategories().execute();

        } else {


            Toast.makeText(MainActivity1.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


      //  setUpRecycler();


        texthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity1.this,MainActivity1.class);
                startActivity(intent);

              /*  mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new fragment_home()).commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);*/

            }
        });

        textoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
               // mFragmentTransaction.replace(R.id.fragment_container, new Fragment_Offer_Products()).commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        
    }



    //------------------------FCM Notification--------------------------------------------------------
    // Fetches reg id from shared preferences
    // and displays on the screen

    private void displayFirebaseRegId() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e("testing","Firebase Reg Id: " + regId);
            // txtRegId.setText("Firebase Reg Id: " + regId);
        else
            Log.e("testing","Firebase Reg Id is not received yet!");
        //  txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    private void ShowLogoutAlert1(String data) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity1.this);
        alertDialog.setTitle("Login to Grocery");
        alertDialog.setMessage(data);
        //  alertDialog.setBackgroundResource(R.color.dialog_color);
        // alertDialog.setIcon(R.drawable.exit);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity1.this, Login.class);
                startActivity(intent);
                finish();
                //loginandregistermethod1();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    //------------------------FCM Notification------------------------------------------------------


    @Override
    public void onClickedItem(int pos, int qty, int status) {
        Log.e("DMen", "Pos:"+ pos + "Qty:"+qty);
        Log.e("testing","status  = "+status);
        Log.e("testing","title inm  = "+allItems1.get(pos).getId());


        String category = allItems1.get(pos).getTextnavi();
        strquantity = String.valueOf(qty);
        strcartid = allItems1.get(pos).getId();
        String  strcategory = allItems1.get(pos).getId();

        SharedPreferences prefuserdata = getSharedPreferences("category", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();
        prefeditor.putString("category", "" + category);

        Log.e("testing","category  = " + category);

        prefeditor.commit();

        Fragment_Categories fragment3 = new Fragment_Categories();
        FragmentTransaction fragmentTransaction3 =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fragment_container, fragment3);
        fragmentTransaction3.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }

/*

//----------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.cartitem);

        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());

            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);


            ConnectionDetector cd1 = new ConnectionDetector(context);
            if (cd1.isConnectingToInternet()) {

                new cartcount().execute();

            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }
        }

        return true;

    }

    @Override
public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {

        case R.id.cartitem:

        //    menuItemcart = menu.findItem(R.id.cartitem);

            if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                ShowLogoutAlert1("Please login");

            }else {

                Intent intent = new Intent(MainActivity1.this, Activity_cart.class);
                startActivity(intent);

            }

            return true;
*/
/*

        case R.id.tracking:

            //    menuItemcart = menu.findItem(R.id.cartitem);


            setocationdialog();
            return true;
*//*



        case R.id.filter:

            final List<String> points = new ArrayList<>();

            final Dialog logindialog = new Dialog(MainActivity1.this);
            logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater)MainActivity1.this.getSystemService(MainActivity1.this.LAYOUT_INFLATER_SERVICE);
            View convertView = (View) inflater.inflate(R.layout.filter, null);

               //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

            logindialog.setContentView(convertView);
            //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
            // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
            logindialog.setCanceledOnTouchOutside(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(logindialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            logindialog.getWindow().setAttributes(lp);


            lowtohigh = (TextView)convertView.findViewById(R.id.lowtohigh);
            hightolow = (TextView)convertView.findViewById(R.id.hightolow);
            newest = (TextView)convertView.findViewById(R.id.newest);
            offer = (TextView)convertView.findViewById(R.id.offer);


            lowtohigh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchType = "lowTOhigh";

                    SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("searchtype", "" + "lowTOhigh");

                    prefeditor.commit();

                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container, new fragment_filter()).commit();
                    logindialog.dismiss();


                      */
/*  ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {

                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }
*//*

                }
            });

            hightolow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchType = "highTOlow";
                    SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("searchtype", "" + "highTOlow");

                    prefeditor.commit();

                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container, new fragment_filter()).commit();
                    logindialog.dismiss();

                }
            });

            newest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchType = "newest";

                    SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("searchtype", "" + "newest");

                    prefeditor.commit();

                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container, new fragment_filter()).commit();
                    logindialog.dismiss();

*/
/*

                        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {


                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }

*//*

                }
            });

            offer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    searchType = "offer";


                    SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("searchtype", "" + "offer");

                    prefeditor.commit();

                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container, new fragment_filter()).commit();
                    logindialog.dismiss();


                   */
/*     ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {

                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }*//*



                }
            });

            logindialog.show();

            return true;
        default:

            return super.onOptionsItemSelected(item);

    }
}
*/


/*

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

       /* if (id == R.id.home) {

            mToolbar.setTitle("Home");
            mToolbar.setTitleTextColor(Color.WHITE);


          *//*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);*//*

            fragment_home fragment2 = new fragment_home();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();

        }
        else*/
            if (id == R.id.accounts) {

              //  mToolbar.setTitle("Accounts");
/*
                Intent intent = new Intent(MainActivity.this, Activity_Account.class);
                startActivity(intent);*/

              /*  Fragment_accounts fragment1 = new Fragment_accounts();
                FragmentTransaction fragmentTransaction3 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, fragment1);
                fragmentTransaction3.commit();*/



                } else if (id == R.id.about) {

                 //   mToolbar.setTitle("About Us");

                  /*  Fragment_aboutus fragment3 = new Fragment_aboutus();
                    FragmentTransaction fragmentTransaction3 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container, fragment3);
                    fragmentTransaction3.commit();*/
                }
                else if (id == R.id.terms) {

                  //  mToolbar.setTitle("Terms & Conditions");
/*
                    Fragment_terms fragment3 = new Fragment_terms();
                    FragmentTransaction fragmentTransaction3 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container, fragment3);
                    fragmentTransaction3.commit();*/
                }
/*
                else if (id == R.id.logout) {

                    mToolbar.setTitle("Logout");
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Logout ");
                    builder.setMessage("Are you Sure Want to Logout");
                    builder.setNegativeButton("NO",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    // Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                                }
                            });
                    builder.setPositiveButton("YES",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    Intent intent = new Intent(MainActivity.this, Login.class);
*/
/*
                                    SharedPreferences prefuserdata = getSharedPreferences("registerUser", 0);
                                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                                    prefeditor.putString("id", "");

                                    prefeditor.clear();
                                    prefeditor.commit();*//*

                                    //Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                                    startActivity(intent);
                                }
                            });
                    builder.show();
                }
*/





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity1.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(EndUrl.GetCategorywise_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node

                    Log.e("testing","jsonObj = "+jsonObj);

                    status = jsonObj.getString("status");
                    message = jsonObj.getString("message");
                    total_record = jsonObj.getString("total_record");

                    String arrayresponce = jsonObj.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


                        FeederInfo_main item = new FeederInfo_main();

                        //  item.setId(post.optString("id"));

                        item.setTextnavi(post.optString("categoryName"));
                        item.setId(post.optString("catId"));



                        allItems1.add(item);

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            if (status == null || status.length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {

                mAdapterFeeds = new Adapter_main(MainActivity1.this, allItems1,mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            } else if (status.equals("error")) {

                allItems1.clear();

                mAdapterFeeds = new Adapter_main(MainActivity1.this,allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(MainActivity1.this, "no data found", Toast.LENGTH_LONG).show();
            }
        }

    }


//-------------------------------------------------------------------------------------------------------------


    class cartcount extends AsyncTask<Void, Void, JSONObject> {

      //  ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(MainActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.GetCartCount_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
              //  dialog.dismiss();

                Log.e("testing2","result in post execute========="+result);

                if (status.equals("success")){

                    Log.e(" cart count", "cartmenucount==" + cartmenucount);

                    String value2= cartmenucount;

                    if (value2 == null || value2.length() == 0 || value2.equals("null")){


                    }else{

                        int countw = Integer.valueOf(value2);
                        Log.e("mahi class count", "count==" + countw);

                        menuItemcart.setIcon(buildCounterDrawable1(countw,R.drawable.menu_cart));

                    }


/*
                    if (_pdsubprice == null || _pdsubprice.length() == 0){
                        final String strrs = Activity_productdetails.this.getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+_pdsubprice);

                    }else{
                        pdprice.setVisibility(View.VISIBLE);
                        final String strrs = Activity_productdetails.this.getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+ _pdsubprice);


                        pdprice.setText(strrs+" "+ _pdsubprice);
                        pdprice.setPaintFlags(pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

                    }*/

                   // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_LONG).show();

                }else {

                   // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {

              //  Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
               // dialog.dismiss();

            }
        }

    }

    private Drawable buildCounterDrawable1(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.trolleycount, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {

            Log.e("testing2", "wishcount================11111=" + count);
            View counterTextPanel = view.findViewById(R.id.count1);
            View counterTextPanel2 = view.findViewById(R.id.counterBackground);
            counterTextPanel.setVisibility(View.GONE);
            counterTextPanel2.setVisibility(View.GONE);

        } else {

            Log.e("testing2", "wishcount================22222=" + count);
            TextView textView = (TextView) view.findViewById(R.id.count1);
            textView.setText("" + count);

        }


        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.GetCartCount_Id,UserId);

            Log.e("testing2", "adapter GetCartCount_Id=" + UserId);

            //if you want to modify some value just do like this.

         /*   details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing2","json"+details.toString());
*/


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;

    }



    public JSONObject postJsonObject(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing2","testing in json result======="+result);
            Log.e("testing2","testing in json result json======="+json);
            Log.e("testing2","result in post status========="+json.getString("status"));
            status = json.getString("status");
           // message = json.getString("message");
          //  total_record = json.getString("total_record");


            String arrayresponce = json.getString("data");



            Log.e("testing2", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing2", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                cartmenucount = post.getString("cartCount");
                Log.e("testing2", "cartcount = "+post.getString("cartCount"));





            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
//-----------------------------------------------------------------------------------------------------

/*


    class Filter extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(MainActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // allItems = new ArrayList<SingleItemModel>();
            return postJsonObject1(EndUrl.Filter_URL, makingJson1());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            */
/**
             * Updating parsed JSON data into ListView
             * *//*

            RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(MainActivity.this, allSampleData);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);
        }

    }

    public JSONObject makingJson1() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Filter_searchType, searchType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

    }



    public JSONObject postJsonObject1(String url, JSONObject loginJobj) {
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if (inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            JSONObject jsonObj = new JSONObject(String.valueOf(json));

            // Getting JSON Array node

            Log.e("testing", "jsonObj = " + jsonObj);


            JSONObject response = new JSONObject(jsonObj.toString());

            Log.e("testing", "jsonParser2222" + jsonObj);

            //JSONObject jsonArray1 = new JSONObject(json.toString());
            //  Result = response.getString("status");
            JSONArray posts = response.optJSONArray("data");
            Log.e("testing", "jsonParser3333" + posts);


            if (posts == null) {
                Log.e("testing", "jon11111111111111111");

                //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
            } else {

                Log.e("testing", "jon122222211");
                Log.e("testing", "jsonParser4444" + posts);

                for (int i = 0; i < posts.length(); i++) {
                    Log.e("testing", "" + posts);

                    Log.e("testing", "" + i);
                    JSONObject post = posts.optJSONObject(i);
                    // JSONArray posts2 = response.optJSONArray("categories");
                    Log.e("testng", "" + post);
                    headers = post.getString("categoryName");

                    Log.e("testing", "name is 11= " + post.getString("categoryName"));


                    String Title = post.getString("categoryName");

                    SectionDataModel dm = new SectionDataModel();

                    dm.setHeaderTitle(post.getString("categoryName"));

                    JSONArray posts2 = post.optJSONArray("products");
                    ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                    for (int i1 = 0; i1 < posts2.length(); i1++) {
                        JSONObject post2 = posts2.optJSONObject(i1);

                               */
/* String Title2 = post2.getString("title");
                                String Productid = post2.getString("productId");
                                String Parentid = post2.getString("subcatName");
                                String Typename = post2.getString("location");

                               *//*


                        String finalimg = null;

                        finalimg = post2.getString("image");
                              */
/*  JSONArray posts3 = post2.optJSONArray("multipleImages");
                                for (int i2 = 0; i2 < posts3.length(); i2++) {
                                    JSONObject post3 = posts3.optJSONObject(i2);

                                    finalimg = post3.getString("image");


                                    //find the group position inside the list
                                    //groupPosition = deptList.indexOf(headerInfo);
                                }*//*


                        singleItem.add(new SingleItemModel(post2.getString("productId"),post2.getString("categoryName"),post2.getString("title"),post2.getString("subTitle"),post2.getString("price"),post2.getString("discountValue"),post2.getString("afterDiscount"), finalimg));


                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;


    }

*/

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
  //      Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        fragment_home fragment3 = new fragment_home();
        FragmentTransaction fragmentTransaction3 =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fragment_container, fragment3);
        fragmentTransaction3.commit();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 5000);
    }


    private void setocationdialog() {

        dialog.setContentView(R.layout.customdialogbox);
        TextView textsearch = (TextView) dialog.findViewById(R.id.textsearch);
        TextView dialogcancel = (TextView) dialog.findViewById(R.id.dialogcancel);
        spinapartment = (Spinner) dialog.findViewById(R.id.spinapartment);


        textsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (capartmentid_id == null || capartmentid_id.length() == 0){
                    Toast.makeText(MainActivity1.this, "Please select Pincode", Toast.LENGTH_SHORT).show();
                }else{
                    dialog.dismiss();
                    ConnectionDetector cd = new ConnectionDetector(MainActivity1.this);
                    if (cd.isConnectingToInternet()) {

                        // new SetApartment().execute();

                    } else {


                        Toast.makeText(MainActivity1.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
       /* ConnectionDetector cd = new ConnectionDetector(Activity_Home.this);
        if (cd.isConnectingToInternet()) {

            // new LoadSpinnerdata().execute();
            new DownloadJSON().execute();


        } else {


            Toast.makeText(Activity_Home.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }*/
        dialogcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // dialog.setTitle("OTP");
        //  dialog.setCancelable(false);
/*

        ImageView imgcancel = (ImageView) dialog.findViewById(R.id.imgcancel);
        edt_mobile = (EditText) dialog.findViewById(R.id.edt_mobile);
        butlogin = (Button) dialog.findViewById(R.id.butlogin);


        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        butlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strpickotp = edt_mobile.getText().toString();

                if (strpickotp == null || strpickotp.length() == 0 || strpickotp.equals("Enter OTP")) {


                } else {
                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {

                        new LoadSpinnerdata4().execute();


                    } else {


                        Toast.makeText(Activiy_Dynamic_Route.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }
                }

            }
        });

*/

        dialog.show();

    }

}
