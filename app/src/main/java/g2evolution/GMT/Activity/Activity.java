package g2evolution.GMT.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import g2evolution.GMT.Adapter.Adapter_main;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_main;
import g2evolution.GMT.FeederInfo.Info_MyCart;
import g2evolution.GMT.FeederInfo.Spinner_model;
import g2evolution.GMT.Fragment.Fragment_aboutus;
import g2evolution.GMT.Fragment.Fragment_accounts;
import g2evolution.GMT.Fragment.Fragment_terms;
import g2evolution.GMT.Fragment.fragment_home;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.HttpHandler;
import g2evolution.GMT.Utility.JSONParser;

public class Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


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

    private String TAG = Activity.class.getSimpleName();

    private ProgressDialog pDialog;
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

    TextView texthome;


String status,amount,message,total_record;

    String UserId,cartmenucount;
    private MenuItem menuItemcart;


    TextView profilename,profileid;

    //-------------------------------navigationrecycler-------------------------------------------------

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_main> allItems1 = new ArrayList<FeederInfo_main>();
    Context context;
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_main> mFeederInfo;
    private Activity madapter;
    Adapter_main mAdapterFeeds;
    RecyclerView rView;

    String []Name =new String[]{"Electronic Products","Accessorices","Grocery Products","Photo Frame",};


   LinearLayout linearsearch;

   String Username,Usermail,Usermob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mViewContainer = (FrameLayout) findViewById(R.id.fragment_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        // getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'></font>"));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#000000")));


        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");






        texthome = (TextView) findViewById(R.id.texthome);

        profilename = (TextView) findViewById(R.id.profilename);
        profileid = (TextView) findViewById(R.id.profileid);


        profilename .setText(Username);
        profileid .setText(Usermail);


        //   listview = (ListView) findViewById(R.id.listView1);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new fragment_home()).commit();


       /* ConnectionDetector cd = new ConnectionDetector(MainActivity.this);
        if (cd.isConnectingToInternet()) {
            new searchfunction().execute();
        } else {
            Toast.makeText(MainActivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }
*/



        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity.this));
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


                Intent i = new Intent(Activity.this, Activity_search.class);
                startActivity(i);


            }
        });



        ConnectionDetector cd = new ConnectionDetector(Activity.this);
        if (cd.isConnectingToInternet()) {


            new cartcount().execute();
            //  new Fetchprofileimage().execute();


        } else {


            Toast.makeText(Activity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


      //  setUpRecycler();

      //  new GetCategories().execute();

        texthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFragmentManager = getSupportFragmentManager();
                mFragmentTransaction = mFragmentManager.beginTransaction();
                mFragmentTransaction.replace(R.id.fragment_container, new fragment_home()).commit();


                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.cartitem);

        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());

            spanString.setSpan(new ForegroundColorSpan(Color.BLACK), 0, spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);


        }


        return true;

    }


/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }
*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.cartitem:


                Intent i = new Intent(Activity.this, Activity_cart.class);
                startActivity(i);

                return true;
            case R.id.filter:



                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

                mToolbar.setTitle("Accounts");

                Fragment_accounts fragment1 = new Fragment_accounts();
                FragmentTransaction fragmentTransaction3 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.fragment_container, fragment1);
                fragmentTransaction3.commit();



                } else if (id == R.id.about) {

                    mToolbar.setTitle("About Us");

                    Fragment_aboutus fragment3 = new Fragment_aboutus();
                    FragmentTransaction fragmentTransaction3 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container, fragment3);
                    fragmentTransaction3.commit();
                }
                else if (id == R.id.terms) {

                    mToolbar.setTitle("Terms & Conditions");

                    Fragment_terms fragment3 = new Fragment_terms();
                    FragmentTransaction fragmentTransaction3 =
                            getSupportFragmentManager().beginTransaction();
                    fragmentTransaction3.replace(R.id.fragment_container, fragment3);
                    fragmentTransaction3.commit();
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
            pDialog = new ProgressDialog(Activity.this);
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
           /* if (status.equals("success")) {

                mAdapterFeeds = new Adapter_main(Activity.this, allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            } else if (status.equals("error")) {

                allItems1.clear();

                mAdapterFeeds = new Adapter_main(Activity.this, allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(Activity.this, "no data found", Toast.LENGTH_LONG).show();
            }*/
        }

    }


//-------------------------------------------------------------------------------------------------------------


    class cartcount extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.GetCartCount_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);
                Log.e("testing","status=================" + status);
                Log.e("testing","cartmenucount=================" + cartmenucount);


                if (status.equals("success")){


                    String value2= cartmenucount;
                    int countw = Integer.valueOf(value2);
                    Log.e("mahi class count", "count==" + countw);

                    menuItemcart.setIcon(buildCounterDrawable1(countw,R.drawable.cart4));



                    Toast.makeText(Activity.this, ""+message, Toast.LENGTH_LONG).show();


                }else {
                    Toast.makeText(Activity.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    private Drawable buildCounterDrawable1(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.trolleycount, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            Log.e("testing", "wishcount================11111=" + count);
            View counterTextPanel = view.findViewById(R.id.count1);
            View counterTextPanel2 = view.findViewById(R.id.counterBackground);
            counterTextPanel.setVisibility(View.GONE);
            counterTextPanel2.setVisibility(View.GONE);
        } else {
            Log.e("testing", "wishcount================22222=" + count);
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

            Log.e("testing", "adapter GetCartCount_Id=" + UserId);



            //if you want to modify some value just do like this.

         /*   details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());
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
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            status = json.getString("status");
           // message = json.getString("message");
          //  total_record = json.getString("total_record");
            Log.e("testing","status in json = "+status);

            String arrayresponce = json.getString("data");



            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                cartmenucount = post.getString("cartCount");


                Log.e("testing","cartcount in json = "+cartmenucount);



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



}
