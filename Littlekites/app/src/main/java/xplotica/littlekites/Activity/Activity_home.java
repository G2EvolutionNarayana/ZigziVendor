package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.legacy.app.ActionBarDrawerToggle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.messaging.FirebaseMessaging;
import com.joooonho.SelectableRoundedImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Activity_Details;
import xplotica.littlekites.Adapter.RecyclerViewAdapter_home;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.ItemObject_home;
import xplotica.littlekites.FeederInfo_parent.parent_Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;
import xplotica.littlekites.app.Config;
import xplotica.littlekites.utils.NotificationUtils;


/**
 * Created by Jana on 30-03-2017.
 */
public class Activity_home extends AppCompatActivity {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<ItemObject_home> allItems1 = new ArrayList<ItemObject_home>();

    private RecyclerView mFeedRecyler;
    private ArrayList<ItemObject_home> mListFeederInfo;
    private ItemObject_home adapter;
    RecyclerViewAdapter_home mAdapterFeeds;

    Context context;
    String schoolid,staffid,type,classid,sectionid, mobileno;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    // RecyclerView rView;

    String Result;
    private GridLayoutManager lLayout;
    SelectableRoundedImageView imgthumbnail;
    String staff_id, school_id, firstName,schoolLogo, logoURL, className, sectionName, photourl, schoolId, schoolName, classId, sectionId, staffId, otp, middleName, lastName, empId, gender, dob, bloodGroup, presentaddress, state, city, pinCode, Mobile, email, maritalStatus, staffType, staffDesignation, joiningDate, subjectHandling, panNumber, expYear, previousEmployer, bankName, accountNumber, dateofLeaving, highSchoolPassYear, secondarySchoolPassYear, teacherTraining, teacherTrainingCertificate, otherCertificate, fatherName, fatherEmail, motherName, motherEmail, permanantPhone, permanantAddress, permanantState, permanantCity, permanantPincode, created_by, reg_date, status;
    private MenuItem menuItemcart;
    private ActionBarDrawerToggle mDrawerToggle;

    String finalphoto, finallogo;
    String photo;

    String regId;

   // ImageView imagelogo;
    TextView textprofilename, textempid, textclass, textsection;
   // String []Name = new String[]{"Attendance","Diary","Leave","Time Table","Message","Group Message","Gallery","History"};
    String []Name = new String[]{"Attendance","Diary","Leave","Time Table","Gallery","History"};

   // Integer[]Image={R.drawable.attendancelogo, R.drawable.diarylogo, R.drawable.leavelogo, R.drawable.homeworklogo, R.drawable.messagelogo, R.drawable.messagelogo, R.drawable.gallerylogo, R.drawable.historylogo};
    Integer[]Image={R.drawable.attendancelogo, R.drawable.diarylogo, R.drawable.leavelogo, R.drawable.homeworklogo, R.drawable.gallerylogo, R.drawable.historylogo};

   // Integer[]Color= {R.color.blue, R.color.orange, R.color.purple, R.color.pink, R.color.pink, R.color.pink, R.color.brown, R.color.dark_green};

    CollapsingToolbarLayout collapsingToolbar;


    //------------------------FCM Notification--------------------------
    private static final String TAG = Activity_home.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_collapse);


        context = this;

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        setTitleColor(R.color.white);

        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");
        mobileno = prefuserdata.getString("mobileno", "");

        Log.e("testing","schoolid = "+schoolid);
        Log.e("testing","staffid = "+staffid);
        Log.e("testing","classid = "+classid);
        Log.e("testing","sectionid = "+sectionid);
        Log.e("testing","type = "+type);
        Log.e("testing","mobileno = "+mobileno);
        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setTitle("");
        toolbarTextAppernce();
        //setAutoUpdate();

        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        //mFeedRecyler.setLayoutManager(new LinearLayoutManager(this));
        setUpRecycler();
        imgthumbnail = (SelectableRoundedImageView)findViewById(R.id.fedderthumbnail);
       // imagelogo = (ImageView) findViewById(R.id.imagelogo);
        textprofilename = (TextView) findViewById(R.id.teextteachername);
        textempid = (TextView) findViewById(R.id.textempid);
        textclass = (TextView) findViewById(R.id.textclass);
        textsection = (TextView) findViewById(R.id.textsection);
        // context = this;
        lLayout = new GridLayoutManager(this,3);
        // rView = (RecyclerView) findViewById(R.id.recycler_view);
        // rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

            new TopTrend().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


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


        textprofilename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Profile.class);


                startActivity(intent);
            }
        });

        //  -----------------For Collapsing image Layout------------------------
        // final Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        // this.setSupportActionBar(toolbar);
        //getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Home");*/
    }

    private void setUpRecycler() {
        mListFeederInfo =new ArrayList<ItemObject_home>();

        for(int i=0;i<Name.length;i++){
            ItemObject_home feedInfo = new ItemObject_home();
            feedInfo.setPhoto(Image[i]);
            feedInfo.setName(Name[i]);
           // feedInfo.setBackcolor(Color[i]);
            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new RecyclerViewAdapter_home(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }

    private void toolbarTextAppernce() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.Logout);

        return true;

    }

    private void setNotifCount(int count) {

        invalidateOptionsMenu();
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Password:
                // Toast.makeText(Activity_Main.this, "Clicking", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Activity_home.this, Activity_ChangePassword.class);
                startActivity(intent2);

                return true;


            case R.id.Logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_home.this);
                builder.setTitle("Logout ");
                builder.setMessage("Are you Sure Want to Logout");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                ConnectionDetector cd = new ConnectionDetector(context);
                                if (cd.isConnectingToInternet()) {

                                    new LoadSpinnerdata().execute();

                                } else {

                                    Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                                }

                            }
                        });
                builder.show();

                return true;

            default:
                mDrawerToggle.onOptionsItemSelected(item);
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String status;

        String strresult1, strresult2, strresult3;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "mobileno value=" + mobileno);
            Log.e("testing", "type value=" + type);
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_Logout_MOBILENO, mobileno));
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_Logout_TYPE, type));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.TEACHER_Logout, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");


                try {
                    Result = json.getString("status");






                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            pDialog.dismiss();
            Log.e("testing", "result is = " + responce);



            if (Result.equals("success")){

                SharedPreferences prefuserdata = context.getSharedPreferences("registerUser", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("schoolid", "");
                prefeditor.putString("type", "");
                prefeditor.clear();
                prefeditor.commit();
                Intent i7 = new Intent(context, Activity_Details.class);
                i7.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i7);
                //((Activity) context).finish();
                ((Activity_home) context).finish();

            }else{

                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }

    public  class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;

        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_PROFILE_MOBILE, mobileno));
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_PROFILE_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_PROFILE_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_PROFILE_LOGINID, staffid));


            Log.e("testing","mobile"+mobileno);
            Log.e("testing","Logintype"+type);
            Log.e("testing","Schoolid"+schoolid);
            Log.e("testing","staffid"+staffid);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.TEACHER_PROFILE, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                Result = json.getString("status");
                String arrayresponce = json.getString("StaffProfile");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();



                    parent_Student_details_feederInfo item = new parent_Student_details_feederInfo();






                    schoolId = post.getString("schoolId");
                    schoolName = post.getString("schoolName");
                    schoolLogo = post.getString("schoolLogo");
                    logoURL = post.getString("logoURL");
                    classId = post.getString("classId");
                    className = post.getString("className");
                    sectionId = post.getString("sectionId");
                    sectionName = post.getString("sectionName");
                    staffId = post.getString("staffId");
                    firstName = post.getString("firstName");
                    empId = post.getString("empId");
                    email = post.getString("email");
                    mobileno = post.getString("Mobile");
                    presentaddress = post.getString("presentaddress");
                    photo = post.getString("photo");
                    photourl = post.getString("PhotoURL");



                }


               /* String arrayresponce1 = json.getString("Specialist");
                Log.e("testing", "adapter value=" + arrayresponce1);

                JSONArray responcearray1 = new JSONArray(arrayresponce1);
                Log.e("testing", "responcearray value=" + responcearray1);


                for (int i = 0; i < responcearray1.length(); i++) {

                    JSONObject post = responcearray1.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    parent_Student_details_feederInfo item = new parent_Student_details_feederInfo();


                    item.set_student(post.optString("specialist"));
                    item.set_school(post.optString("regDate"));
                    item.set_rollno(post.optString("queryId"));


                    allItems1.add(item);

                    arraylist1.add(map);

                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return responce;

        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);




            Log.e("testing", "result is = " + responce);

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            Log.e("testing","firstName = "+firstName);
            Log.e("testing","empId = "+empId);
            Log.e("testing","className = "+className);
            Log.e("testing","sectionName = "+sectionName);

            if (Result.equals("Success")){
                finalphoto = photourl+""+photo;
                finallogo = logoURL+""+schoolLogo;
                SharedPreferences prefuserdata = getSharedPreferences("myprofile", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("name", "" + firstName);
                prefeditor.putString("empId", "" + empId);
                prefeditor.putString("photo", "" + photo);
                prefeditor.putString("finalphoto", "" + finalphoto);
                prefeditor.putString("gender", "" + gender);
                prefeditor.putString("presentaddress", "" + presentaddress);
                prefeditor.putString("email", "" + email);
                prefeditor.putString("bloodGroup", "" + bloodGroup);
                prefeditor.putString("Mobile", "" + Mobile);


                prefeditor.commit();


                Log.e("testing","Name"+firstName);
                textprofilename.setText(firstName);
                textempid.setText(empId);
                textclass.setText("Class: "+className);
                textsection.setText("Section: "+sectionName);

                if (photo == null || photo.equals("")||photo.equals(null)||photo.equals("null")){
                    imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgthumbnail.setOval(true);
                    imgthumbnail.setImageResource(R.drawable.profilewhite);
                }else{
                    imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgthumbnail.setOval(true);

                    Log.e("testing","Image in Dynamic="+finalphoto);
                    Glide.with(Activity_home.this)
                            .load(Uri.parse(finalphoto))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgthumbnail);
                }
                if (schoolLogo == null || schoolLogo.equals("")||schoolLogo.equals(null)||schoolLogo.equals("null")){


                }else{


                    Log.e("testing","Image in Dynamic="+finallogo);
                   /* Glide.with(Activity_home.this)
                            .load(Uri.parse(finallogo))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imagelogo);*/
                }


            }else if (Result.equals("Error")){

                Toast.makeText(Activity_home.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }else{
                Log.e("testing","Name 22 "+firstName);
            }

        }

    }



/*

    private void setAutoUpdate() {
        new UpdateHandler.Builder(this)
                .setContent("Please, update app to new version to continue reposting")// Alert Dialog Content
                .setTitle("New version available") //Alert Dialog Text
                .setUpdateText("Yes")
                .setCancelText("No")
                .setCancelable(false)
                .showDefaultAlert(true)
                .showWhatsNew(true) //Show whats new from play store
                .setCheckerCount(2)
                .setOnUpdateFoundLister(new UpdateHandler.Builder.UpdateListener() {
                    @Override
                    public void onUpdateFound(boolean newVersion, String whatsNew) {

                    }
                })
                .setOnUpdateClickLister(new UpdateHandler.Builder.UpdateClickListener() {
                    @Override
                    public void onUpdateClick(boolean newVersion, String whatsNew) {
                        //Takes user to play store
                        startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
                    }
                })
                .setOnCancelClickLister(new UpdateHandler.Builder.UpdateCancelListener() {
                    @Override
                    public void onCancelClick() {
                        // Closes the App
                        finishAffinity();
                    }
                })
                .build();
    }

*/


}
