package g2evolution.Boutique;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.LinkedHashMap;
import java.util.List;

import g2evolution.Boutique.Activity.Activity_Aboutus;
import g2evolution.Boutique.Activity.Activity_Address_Navigation;
import g2evolution.Boutique.Activity.Activity_ContactUs;
import g2evolution.Boutique.Activity.Activity_TermsandConditions;
import g2evolution.Boutique.Activity.Activity_WishList;
import g2evolution.Boutique.Activity.Activity_cart;
import g2evolution.Boutique.Activity.Activity_search;
import g2evolution.Boutique.Activity.Activity_subcategory_details;
import g2evolution.Boutique.Activity.Login;
import g2evolution.Boutique.Activity.My_Bookings_Activity;
import g2evolution.Boutique.Activity.My_Orders;
import g2evolution.Boutique.Activity.Notifications_Activity;
import g2evolution.Boutique.Adapter.Adapter_main;
import g2evolution.Boutique.Adapter.MyListAdapter_MainActivity;
import g2evolution.Boutique.Adapter.spinnerAdapter;
import g2evolution.Boutique.FeederInfo.DetailInfo;
import g2evolution.Boutique.FeederInfo.FeedDetail;
import g2evolution.Boutique.FeederInfo.FeedHeader;
import g2evolution.Boutique.FeederInfo.FeederInfo_main;
import g2evolution.Boutique.FeederInfo.HeaderInfo;
import g2evolution.Boutique.FeederInfo.Info_MyCart;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.Spinner_model;
import g2evolution.Boutique.FeederInfo.WorldPopulation;
import g2evolution.Boutique.Fragment.Fragment_Home_New;
import g2evolution.Boutique.Fragment.Fragment_filter_new_Pagination;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.JSONfunctions1;
import g2evolution.Boutique.Utility.NotificationUtils;
import g2evolution.Boutique.app.Config;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Adapter_main.OnItemClick{


    //----------------------------Ask A question- Dailauge----------------------//


    List<FeedHeader> listDataHeader;
    List<FeedDetail> feedDetail1 = new ArrayList<FeedDetail>();
    HashMap<String, List<FeedDetail>> listDataChild;


    private LinkedHashMap<String, HeaderInfo> myDepartments = new LinkedHashMap<String, HeaderInfo>();
    private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();

    private MyListAdapter_MainActivity listAdapter;
    private ExpandableListView myList;

    //our child listener
    private ExpandableListView.OnChildClickListener myListItemClicked = new ExpandableListView.OnChildClickListener() {

        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = deptList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo = headerInfo.getProductList().get(childPosition);
            //display it or do something with it
            /*Toast.makeText(getActivity(), "Clicked on Detail " + headerInfo.getName()
                    + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();*/
            return false;

        }

    };


    //our group listener
    private ExpandableListView.OnGroupClickListener myListGroupClicked = new ExpandableListView.OnGroupClickListener() {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = deptList.get(groupPosition);
            //display it or do something with it
            /*Toast.makeText(getActivity(), "Child on Header " + headerInfo.getName(),
                    Toast.LENGTH_LONG).show();*/

            return false;

        }

    };

    public static int notificationCountCart = 0;

    JSONParser jsonParser = new JSONParser();
    private ArrayList<Info_MyCart> mListFeederInfo;

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle toggle;
    Toolbar mToolbar;

    FrameLayout mViewContainer;
    TextView login;
    LinearLayout loginlinear;
    private FloatingActionButton fab;
    ArrayList<String> listdata = new ArrayList<String>();
    private MenuItem mMenuBellItem;


    String loginstatus;
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
    TextView texthome,textoffers;
   // TextView account,aboutus,termsconditon,contactus;

    static String status;
    String amount;
    String message;
    String total_record;

    static String UserId;
    public  static  String cartmenucount;
    private static MenuItem menuItemcart;

    String  headers;
    TextView profilename,profileid;
   // TextView logout;
    private Adapter_main.OnItemClick mCallback;

    //-------------------------------navigationrecycler-------------------------------------------------

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_main> allItems1 = new ArrayList<FeederInfo_main>();
    static Context context;
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_main> mFeederInfo;
    private MainActivity madapter;
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
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------
    String regId;

  //  TextView My_Order,My_Cart,notifcations,my_bookings;

    Dialog dialog; // apartment dialog

    String viewUserId;

    //------------------searchable spinner----------------
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<WorldPopulation> world;//spinapartment
    ArrayList<String> worldlist;//spinapartment
    Spinner spinapartment;
    String capartmentid_id;
    String pincode;

    DrawerLayout mDrawerLayout;

    TextView textmyaddress, textmycart,textmywishlist, textmyorders, textnotifications, textmybookings, textaboutus, textcontactus, textterms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
         viewUserId = prefuserdata23.getString("UserId", "");

        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

      //  logout = (TextView) findViewById(R.id.logout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mViewContainer = (FrameLayout) findViewById(R.id.fragment_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>GMT</font>"));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        textmyaddress = (TextView) findViewById(R.id.textmyaddress);
        textmycart = (TextView) findViewById(R.id.textmycart);
        textmywishlist = (TextView) findViewById(R.id.textmywishlist);
        textmyorders = (TextView) findViewById(R.id.textmyorders);
        textnotifications = (TextView) findViewById(R.id.textnotifications);
        textmybookings = (TextView) findViewById(R.id.textmybookings);
        textaboutus = (TextView) findViewById(R.id.textaboutus);
        textcontactus = (TextView) findViewById(R.id.textcontactus);
        textterms = (TextView) findViewById(R.id.textterms);

     /*   NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));*/

        capartmentid_id ="0";

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        SharedPreferences prefuserdata2 = getSharedPreferences("pincode", 0);
        pincode = prefuserdata2.getString("pincode", "");

        Log.e("testing","pincode====Main sharedpreference"+pincode);

        allSampleData = new ArrayList<SectionDataModel>();
      //  my_recycler_view = (RecyclerView)findViewById(R.id.recycler_view);
     //   my_recycler_view.setHasFixedSize(true);

        mCallback = this;

        texthome = (TextView) findViewById(R.id.texthome);
      //  textoffers = (TextView) findViewById(R.id.textoffers);

      /*  account = (TextView) findViewById(R.id.accounts);
        My_Order = (TextView) findViewById(R.id.myorders);
        My_Cart = (TextView) findViewById(R.id.cart);
        notifcations = (TextView) findViewById(R.id.notifcations);
        my_bookings = (TextView) findViewById(R.id.my_bookings);
        aboutus = (TextView) findViewById(R.id.about);
        termsconditon = (TextView) findViewById(R.id.terms);
        contactus = (TextView) findViewById(R.id.contactus);*/


        profilename = (TextView) findViewById(R.id.profilename);
        profileid = (TextView) findViewById(R.id.profileid);

        Log.e("testing","Usermail===="+Usermail);
        Log.e("testing","Username===="+Username);

      /*  if (Usermob==null||Usermob.length()==0|Usermob.equals("")||Usermob.equals("null")){

           logout.setText("Login");
           loginstatus="Login";

        }else {
            loginstatus="Logout";
            logout.setText("Logout");
           // profilename .setText(Username);

        }*/

       /* My_Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId==null ||viewUserId.equals("") || viewUserId.equals("null") ||  viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {


                    Intent intent = new Intent(MainActivity.this, My_Orders.class);
                    startActivity(intent);


                }

            }
        });



        my_bookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, My_Bookings_Activity.class);
                    startActivity(intent);
                }

            }
        });


        notifcations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Notifications_Activity.class);
                    startActivity(intent);

                }

            }
        });


        My_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Activity_cart.class);
                    startActivity(intent);

                }

            }
        });
*/
      /*  logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (loginstatus.equals("Login")){

                    ShowLogoutAlert1("Please login");


                }else {

                    final Dialog logoutdialog = new Dialog(MainActivity.this);
                    logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
                    View convertView = (View) inflater.inflate(R.layout.logout_dialog, null);
                    //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                    logoutdialog.setContentView(convertView);
                    LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                    // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                    logoutdialog.setCanceledOnTouchOutside(false);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(logoutdialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    logoutdialog.getWindow().setAttributes(lp);


                    TextView yesselect = (TextView) convertView.findViewById(R.id.yes);

                    yesselect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SharedPreferences sharedPreferences = getSharedPreferences("regId", 0);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear();
                            edit.commit();

                            SharedPreferences prefuserdata1=getSharedPreferences("Logintype",0);
                            SharedPreferences.Editor edit1 = prefuserdata1.edit();
                            edit1.clear();
                            edit1.commit();

                            Intent i = new Intent(MainActivity.this, Login.class);
                            startActivity(i);
                            finish();
                        }
                    });


                    TextView noselect = (TextView) convertView.findViewById(R.id.no);
                    noselect.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            logoutdialog.dismiss();

                        }
                    });

                    logoutdialog.show();
                }

            }
        });*/
      /*  account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Activity_Address_Navigation.class);
                    startActivity(intent);

                }

            }
        });


        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Activity_Aboutus.class);
                startActivity(intent);

               *//* mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new Fragment_aboutus()).commit();*//*

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        termsconditon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Activity_TermsandConditions.class);
                startActivity(intent);

              *//*  mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new Fragment_terms()).commit();
*//*

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(MainActivity.this, Activity_ContactUs.class);
                startActivity(intent);


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });
*/



        profileid.setText(Usermob);

           /* if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){

                setocationdialog();

            }else{


            }*/
        //   listview = (ListView) findViewById(R.id.listView1);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new Fragment_Home_New()).commit();

       /*
        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {
            new searchfunction().execute();
        } else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }

*/

        //get reference to the ExpandableListView
          myList = (ExpandableListView) findViewById(R.id.myList);
        //create the adapter by passing your ArrayList data
          listAdapter = new MyListAdapter_MainActivity(MainActivity.this, deptList);
        //attach the adapter to the list
          myList.setAdapter(listAdapter);

        //expand all Groups
          expandAll();

        //listener for child row click
        //myList.setOnChildClickListener(myListItemClicked);
        //listener for group heading click
        //  myList.setOnGroupClickListener(myListGroupClicked);


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

        //setocationdialog();
/*

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //setUpRecycler();
        // context = this;
        ///  lLayout = new GridLayoutManager(Activity_cart.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);
*/

        linearsearch = (LinearLayout)findViewById(R.id.linearsearch);
        linearsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("testing","pincode,,,,,search-----"+pincode);

                if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){
                    Toast.makeText(MainActivity.this, "Select Pincode", Toast.LENGTH_SHORT).show();
                    setocationdialog();
                }else{
                    Intent i = new Intent(MainActivity.this, Activity_search.class);

                    SharedPreferences prefuserdata = getSharedPreferences("searchparam", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("category_id", "" + "");
                    prefeditor.putString("sub_category_id", "" + "");
                    prefeditor.putString("child_category_id", "" + "");


                    prefeditor.commit();


                    startActivity(i);
                }
            }
        });



        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {

         //  new GetCategories().execute();
            new userdata().execute();
            
        } else {

            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

      //  setUpRecycler();

        texthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,Home_Activity.class);
                startActivity(intent);
                finish();

              /*  mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new fragment_home()).commit();

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);*/

            }
        });

/*

        textoffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){
                    Toast.makeText(MainActivity.this, "Select Pincode", Toast.LENGTH_SHORT).show();
                    setocationdialog();

                }else{

                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    mFragmentTransaction.replace(R.id.fragment_container, new Fragment_Offer_Products()).commit();

                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                }

            }

        });
*/



        textmyaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Activity_Address_Navigation.class);
                    startActivity(intent);

                }
            }
        });

        textmycart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Activity_cart.class);
                    startActivity(intent);

                }
            }
        });
        textmywishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity_WishList.class);
                startActivity(intent);
            }
        });
        textmyorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewUserId==null ||viewUserId.equals("") || viewUserId.equals("null") ||  viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {


                    Intent intent = new Intent(MainActivity.this, My_Orders.class);
                    startActivity(intent);


                }
            }
        });

        textnotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, Notifications_Activity.class);
                    startActivity(intent);

                }
            }
        });

        textmybookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewUserId==null || viewUserId.equals("") || viewUserId.equals("null") || viewUserId.equals(null) || viewUserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent intent = new Intent(MainActivity.this, My_Bookings_Activity.class);
                    startActivity(intent);
                }
            }
        });

        textaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Activity_Aboutus.class);
                startActivity(intent);
            }
        });

        textcontactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentc = new Intent(MainActivity.this, Activity_ContactUs.class);
                startActivity(intentc);
            }
        });

        textterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentt = new Intent(MainActivity.this, Activity_TermsandConditions.class);
                startActivity(intentt);
            }
        });


    }


    //-----------------------expandable------------------------//



    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }


    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.collapseGroup(i);
        }
    }




    //here we maintain our products in various departments
    private int addProduct(String department, String product) {

        int groupPosition = 0;

        //check the hash map if the group already exists
        HeaderInfo headerInfo = myDepartments.get(department);
        //add the group if doesn't exists
        if (headerInfo == null) {

            headerInfo = new HeaderInfo();
            headerInfo.setName(department);
            myDepartments.put(department, headerInfo);
            deptList.add(headerInfo);

        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;

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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Login to Zigzi");
        alertDialog.setMessage(data);
        //  alertDialog.setBackgroundResource(R.color.dialog_color);
        // alertDialog.setIcon(R.drawable.exit);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MainActivity.this, Login_Activity.class);
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
        prefeditor.putString("category", "" + strcategory);
        prefeditor.putString("categoryname", "" + category);


        prefeditor.commit();


        Log.e("testing","category  = ======on click" + strcategory);
        Log.e("testing","categoryname  = ======on click" + category);

        if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){

            Toast.makeText(MainActivity.this, "Select Pincode", Toast.LENGTH_SHORT).show();
            setocationdialog();

        }else{


            Intent intent = new Intent(MainActivity.this, Activity_subcategory_details.class);
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


    }


//----------------------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.cartitem);

        MenuItem menuItemlogin = menu.findItem(R.id.login);
        MenuItem menuItemlogout = menu.findItem(R.id.logout);
        /*if (UserId==null||UserId.trim().length()==0||UserId.trim().equals("0")||UserId.trim().equals("null")){




            menuItemlogin.setVisible(true);
            menuItemlogout.setVisible(false);

        }else {


            menuItemlogin.setVisible(false);
            menuItemlogout.setVisible(true);

            // profilename .setText(Username);

        }*/

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

                Intent intent = new Intent(MainActivity.this, Activity_cart.class);
                startActivity(intent);

            }

            return true;

      /*  case R.id.tracking:

            //    menuItemcart = menu.findItem(R.id.cartitem);
            setocationdialog();

            return true;*/

      /*  case R.id.login:
            ShowLogoutAlert1("Please login");



            return true;
        case R.id.logout:

            logoutmethod();


            return true;*/
        case R.id.filter:

            if (pincode==null||pincode.length()== 0 ||pincode.equals("null")||pincode.equals("0")){

                setocationdialog();

            }else {

                final List<String> points = new ArrayList<>();

                final Dialog logindialog = new Dialog(MainActivity.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater1 = (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
                View convertView1 = (View) inflater1.inflate(R.layout.filter, null);

                //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logindialog.setContentView(convertView1);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(logindialog.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp1.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp1);


                lowtohigh = (TextView) convertView1.findViewById(R.id.lowtohigh);
                hightolow = (TextView) convertView1.findViewById(R.id.hightolow);
                newest = (TextView) convertView1.findViewById(R.id.newest);
                // offer = (TextView)convertView1.findViewById(R.id.offer);


                lowtohigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchType = "lowTOhigh";

                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" + "lowTOhigh");

                        prefeditor.commit();

                        Intent intent =new Intent(MainActivity.this,Fragment_filter_new_Pagination.class);
                        startActivity(intent);
                       /* mFragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment_container, new Fragment_filter_new_Pagination()).commit();*/
                        logindialog.dismiss();


                      /*  ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {

                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }
*/

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

                        Intent intent =new Intent(MainActivity.this,Fragment_filter_new_Pagination.class);
                        startActivity(intent);
                       /* mFragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment_container, new Fragment_filter_new_Pagination()).commit();*/
                        logindialog.dismiss();

                    }
                });

                newest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchType = "recent";

                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" + "recent");

                        prefeditor.commit();

                        Intent intent =new Intent(MainActivity.this,Fragment_filter_new_Pagination.class);
                        startActivity(intent);

                      /*  mFragmentManager = getSupportFragmentManager();
                        mFragmentTransaction = mFragmentManager.beginTransaction();
                        mFragmentTransaction.replace(R.id.fragment_container, new Fragment_filter_new_Pagination()).commit();*/
                        logindialog.dismiss();


/*

                        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {

                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }

*/

                    }
                });



             /*   offer.setOnClickListener(new View.OnClickListener() {
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


                   *//*     ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                        if (cd.isConnectingToInternet()) {

                            new Filter().execute();

                        } else {

                            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                        }*//*

                    }
                });
*/
                logindialog.show();
            }


        default:

            return super.onOptionsItemSelected(item);

    }
}

    private void logoutmethod() {



            final Dialog logoutdialog = new Dialog(MainActivity.this);
            logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(MainActivity.this.LAYOUT_INFLATER_SERVICE);
            View convertView = (View) inflater.inflate(R.layout.logout_dialog, null);
            //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

            logoutdialog.setContentView(convertView);
            LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
            // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
            logoutdialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(logoutdialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            logoutdialog.getWindow().setAttributes(lp);


            TextView yesselect = (TextView) convertView.findViewById(R.id.yes);

            yesselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedPreferences = getSharedPreferences("regId", 0);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.clear();
                    edit.commit();

                    SharedPreferences prefuserdata1=getSharedPreferences("Logintype",0);
                    SharedPreferences.Editor edit1 = prefuserdata1.edit();
                    edit1.clear();
                    edit1.commit();

                    Intent i = new Intent(MainActivity.this, Login_Activity.class);
                    startActivity(i);
                    finish();
                }
            });


            TextView noselect = (TextView) convertView.findViewById(R.id.no);
            noselect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logoutdialog.dismiss();

                }
            });

            logoutdialog.show();



    }

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
            pDialog = new ProgressDialog(MainActivity.this);
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

                mAdapterFeeds = new Adapter_main(MainActivity.this, allItems1,mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            } else if (status.equals("error")) {

                allItems1.clear();

                mAdapterFeeds = new Adapter_main(MainActivity.this,allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(MainActivity.this, "no data found", Toast.LENGTH_LONG).show();

            }
        }

    }


//-------------------------------------------------------------------------------------------------------------


    public static class cartcount extends AsyncTask<Void, Void, JSONObject> {

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

            return postJsonObject(EndUrl.GetuserCartDetails_URL, makingJson());
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

                        menuItemcart.setIcon(buildCounterDrawable1(countw,R.drawable.cart));

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


    private static Drawable buildCounterDrawable1(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(context);
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

        return new BitmapDrawable(context.getResources(), bitmap);


    }

    public static JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.GetuserCartDetails_Id,UserId);

            Log.e("testing2", "adapter GetCartCount_Id=" + UserId);


            //if you want to modify some value just do like this.

         /*
            details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing2","json"+details.toString());

*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;

    }



    public static JSONObject postJsonObject(String url, JSONObject loginJobj){
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
                String products = post.getString("products");
                JSONArray responcearray2 = new JSONArray(products);
                cartmenucount = String.valueOf(responcearray2.length());
                Log.e("testing2", "cartcount = "+cartmenucount);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
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

   /* @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
  //      Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        Fragment_Home_New fragment3 = new Fragment_Home_New();
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
*/



    private void setocationdialog() {

        dialog.setContentView(R.layout.customdialogboxlocaion);
        TextView textsearch = (TextView) dialog.findViewById(R.id.textsearch);
        TextView dialogcancel = (TextView) dialog.findViewById(R.id.dialogcancel);
        spinapartment = (Spinner) dialog.findViewById(R.id.spinapartment);


        textsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (capartmentid_id == null || capartmentid_id.length() == 0 || spinapartment.getSelectedItem().toString().equals("Select Pincode")){
                    Toast.makeText(MainActivity.this, "Please select Pincode", Toast.LENGTH_SHORT).show();
                }else{

                    ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
                    if (cd.isConnectingToInternet()) {
                        SharedPreferences prefuserdata2 = getSharedPreferences("pincode", 0);
                        SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
                        prefeditor2.putString("pincode", "" + capartmentid_id);

                        Log.e("testing","pincode  = " + capartmentid_id);

                        prefeditor2.commit();
                        pincode = capartmentid_id;

                        Fragment_Home_New fragment1 = new Fragment_Home_New();
                        FragmentTransaction fragmentTransaction3 =
                                getSupportFragmentManager().beginTransaction();
                        fragmentTransaction3.replace(R.id.fragment_container, fragment1);
                        fragmentTransaction3.commit();

                        // new SetApartment().execute();
                        dialog.dismiss();

                    } else {
                        dialog.dismiss();

                        Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }

                }

            }
        });
        ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {

            // new LoadSpinnerdata().execute();
            new DownloadJSON().execute();


        } else {


            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
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


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            world = new ArrayList<WorldPopulation>();
            // Create an array to populate the spinner
            worldlist = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address
            jsonobject = JSONfunctions1
                    .getJSONfromURL(EndUrl.Pincode_URL, "GET",userpramas);


            Log.e("testing","apartments = "+jsonobject);

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("data");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    WorldPopulation worldpop = new WorldPopulation();

                    worldpop.setAname(jsonobject.optString("pincode"));
                    worldpop.setAid(jsonobject.optString("pincodeId"));
				/*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*/
                    world.add(worldpop);

                    // Populate spinner with country names
                    worldlist.add(jsonobject.optString("pincode"));

                }
            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void args) {

            // Locate the spinner in activity_main.xml
            // Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

/*
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.checkbox1);
            multiSelectionSpinner.setItems(students1);
            // multiSelectionSpinner.setSelection(new int[]{2, 6});
            multiSelectionSpinner.setListener(this);*/


            // -----------------------------spinner for age-----------------------------------


            spinnerAdapter adapter = new spinnerAdapter(MainActivity.this, R.layout.spinner_item);
            adapter.addAll(worldlist);
            adapter.add("Select Pincode");
            spinapartment.setAdapter(adapter);
            spinapartment.setSelection(adapter.getCount());

            /*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*/

            // Spinner on item click listener
            spinapartment
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            if(spinapartment.getSelectedItem() == "Select Pincode")
                            {
                                //Do nothing.
                            }
                            else{
                                capartmentid_id = world.get(position).getAname();

                                Log.e("ugender", "ugender = " + capartmentid_id);
                                // new DownloadJSON4().execute();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


        }
    }

    public class userdata extends AsyncTask<String, String, String> {

        String responce;
        String message;
        String headers;
        String childs;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing", "jsonParser startedkljhk");
            //userpramas.add(new BasicNameValuePair("feader_reg_id", id));
            //  Log.e("testing", "feader_reg_id" + id);

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.fragment_home_expandable, "GET", userpramas);


            Log.e("testing1", "jsonParser" + json);


            if (json == null) {

                Log.e("testing1", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing1", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                try {

                    JSONObject response = new JSONObject(json.toString());

                    Log.e("testing1", "jsonParser2222" + json);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    // Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("data");
                    Log.e("testing1", "jsonParser3333" + posts);



              /* if (posts.equals(null)){
                   listDataHeader = new ArrayList<FeedHeader>();
                   listDataChild= new HashMap<String, FeedDetail>();
               }else{
                   listDataHeader.clear();
                   listDataChild.clear();
               }*/
            /*Initialize array if null*/
                  /*  if (null == listDataHeader || null == listDataChild) {
                        listDataHeader = new ArrayList<FeedHeader>();
                        // listDataChild = new ArrayList<FeedDetail>();
                        listDataChild = new HashMap<String, List<FeedDetail>>();
                    } else {
                        listDataHeader.clear();
                        listDataChild.clear();
                    }*/

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


                            FeedHeader item = new FeedHeader();
                            //     String Rowid = post.getString("category_id");
                            String Title = post.getString("categoryName");
                            String date = post.getString("catId");

                            String image = post.getString("catImage");
                            String Subcategorylist = post.getString("Subcategorylist");
                            //     String desc = post.getString("category_id");
                            //     String time = post.getString("category_id");
                            //   String uploads = post.getString("cat_image");
                            // item.setHeaderName(Title);
                            // item.setRowid(Rowid);
                            //   item.setDescription(desc);
                            //  item.setUpload(uploads);
                            //  item.setTime(time);

                            Log.e("testing","test cat id========"+date);
                            // listDataHeader.add(item);


                            HeaderInfo headerInfo;

                            headerInfo = new HeaderInfo();
                            headerInfo.setName(Title);
                            headerInfo.setImage(image);
                            headerInfo.setTextchild(Subcategorylist);
                            myDepartments.put(Title, headerInfo);
                            deptList.add(headerInfo);

                            //get the children for the group
                            ArrayList<DetailInfo> productList = headerInfo.getProductList();
                            //size of the children list
                            int listSize = productList.size();
                            //add to the counter
                            listSize++;

                            // FeedDetail feedDetail = new FeedDetail(Title);
                            // listDataHeader.add(item);
                            // listDataChild.put(item.getHeaderName(), feedDetail);

                            JSONArray posts2 = post.optJSONArray("details");

                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);
                                FeedDetail item2 = new FeedDetail();
                                //String[] planets = new String[]
                                String subcatid = post2.getString("subcatId");
                                String Title2 = post2.getString("subcatname");
                                String Title4 = post2.getString("catId");
                                String Title3 = post2.getString("categoryName");
                                Log.e("testing","Title3====="+Title3);
                                Log.e("testing","subcatid====="+subcatid);
                                Log.e("testing", "name222 = " + post2.getString("subcatname"));
                                childs = post2.getString("subcatname");

                                Log.e("testing","Title2======"+Title2);

                                /*FeedDetail feedDetail = new FeedDetail(Title2);
                                item2.setTitle(Title2);
                                listDataChild.put(item.getHeaderName(), feedDetail);*/

                                item2.setTitle(Title2);
                                //listDataChild.add(item2);
                                // Log.e("mahi--------------", "mahi--------------------- " + item2.setTitle(Title2));

                                feedDetail1.add(item2);
                                Log.e("mahi--------------", "mahi--------------------- " + feedDetail1.add(item2));

                                //   listDataChild.put(item.getHeaderName(), feedDetail1);
                                ///listDataChild.put(listDataHeader.get(0), wk);*/

                                // listDataChild.add(item2);


                                //create a new child and add that to the group
                                DetailInfo detailInfo = new DetailInfo();
                                detailInfo.setSequence(String.valueOf(listSize));
                                Log.e("testing","listSize==="+listSize);
                                detailInfo.setName(Title2);
                                detailInfo.setCategoryname(Title3);
                                detailInfo.setSubcatid(subcatid);
                                detailInfo.setCategoryid(Title4);
                                productList.add(detailInfo);
                                headerInfo.setProductList(productList);

                                //find the group position inside the list
                                //groupPosition = deptList.indexOf(headerInfo);
                            }

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return responce;
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            pDialog.dismiss();
            Log.e("testing", "result is === " + result);

            // addProduct(listDataHeader, listDataChild);
            // mEListAdapter.setData(listDataHeader, listDataChild);

            //expListView.setAdapter(mEListAdapter);
            // listviewmyorder.setAdapter(adapter);
            Log.e("testing", "adapter");
            Log.e("testing", "adapter===="+deptList);

            listAdapter = new MyListAdapter_MainActivity(MainActivity.this, deptList);

//attach the adapter to the list
            myList.setAdapter(listAdapter);
            setListViewHeight(myList, 0);

        }

    }

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight = listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

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
