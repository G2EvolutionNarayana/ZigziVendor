package xplotica.littlekites.Activity_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import xplotica.littlekites.Adapter_parent.RecyclerViewAdapter_Parent_home;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.ItemObject_home;
import xplotica.littlekites.FeederInfo_parent.parent_Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_Home extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;
    String Result;

    ArrayList<HashMap<String, String>> arraylist1;
    SelectableRoundedImageView imgthumbnail;
    private ArrayList<ItemObject_home> allItems1 = new ArrayList<ItemObject_home>();

    private RecyclerView mFeedRecyler;

    Toolbar mToolbar;

    private MenuItem menuItemcart;
    private ActionBarDrawerToggle mDrawerToggle;

    private ArrayList<ItemObject_home> mListFeederInfo;
    private ItemObject_home adapter;
    RecyclerViewAdapter_Parent_home mAdapterFeeds;
    RecyclerView rView;
    String student_id, school_id, firstName, ProfilePhotoURL, schoolLogo, logoURL, className, sectionName, rollNumber, gender, dob, bloodGroup, permanantAddress,  schoolName, schoolId, classId, sectionId, studentId, classTeacherId, classTeacherName, photo, studentPhoto, localAddress, classJoined, section, firstLanguage, panNumber, expYear, previousEmployer, bankName, accountNumber, dateofLeaving, highSchoolPassYear, secondarySchoolPassYear, teacherTraining, teacherTrainingCertificate, otherCertificate, fatherName, fatherEmail, motherName, motherEmail, permanantPhone, permanantState, permanantCity, permanantPincode, created_by, reg_date, status;
    Context context;
    String finalphoto, finallogo;

    private GridLayoutManager lLayout;

    //ImageView imagelogo;
    TextView teextteachername, textprofilename, textrollno;
    //TextView textclass;
   // String[]Home = new String[]{"Attendance","Diary","Fees","Message","Gallery","Calender","School Details","Events","Notice"};
    String[]Home = new String[]{"Attendance","Diary","Fees","Gallery","Calender","School Details","Events","Notice"};
   // Integer[]Image = {R.drawable.attendancelogo,R.drawable.diarylogo,R.drawable.feelogo,R.drawable.messagelogo,R.drawable.gallerylogo,R.drawable.calendarlogo,R.drawable.schooldetailslogo,R.drawable.eventslogo,R.drawable.noticelogo};
    Integer[]Image = {R.drawable.attendancelogo,R.drawable.diarylogo,R.drawable.feelogo,R.drawable.gallerylogo,R.drawable.calendarlogo,R.drawable.schooldetailslogo,R.drawable.eventslogo,R.drawable.noticelogo};


   // Integer[]BackgroundColor={R.color.orange,R.color.brown,R.color.green_50,R.color.yellow_50,R.color.accent,R.color.light_blue,R.color.blue,R.color.blue,R.color.accent};

    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    CollapsingToolbarLayout collapsingToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__parent__home);
        context = this;
        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setTitle("");
        toolbarTextAppernce();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
      //  setAutoUpdate();
        lLayout = new GridLayoutManager(context,3);

        rView = (RecyclerView)findViewById(R.id.recycler_view);

        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);

        setUpRecycler();
        imgthumbnail = (SelectableRoundedImageView)findViewById(R.id.fedderthumbnail);
       // imagelogo = (ImageView) findViewById(R.id.imagelogo);
        teextteachername = (TextView) findViewById(R.id.teextteachername);
        textprofilename = (TextView) findViewById(R.id.textstudentname);
        textrollno = (TextView) findViewById(R.id.textrollno);
      //  textclass = (TextView) findViewById(R.id.textclass);



    ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

             new TopTrend().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
        textprofilename.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Activity_Parent_Profile.class);


                startActivity(intent);
            }
        });
        //  -----------------For Collapsing image Layout------------------------
        //final Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        //getActivity().setSupportActionBar(toolbar);
        //getActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*  CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        //collapsingToolbar.setTitle("Home");
*/
    }
    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<ItemObject_home>();
        for(int i=0;i<Home.length;i++){
            ItemObject_home notification_feederInfo = new ItemObject_home();
            notification_feederInfo.setName(Home[i]);
            notification_feederInfo.setPhoto(Image[i]);
           // notification_feederInfo.setBackcolor(BackgroundColor[i]);

            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new RecyclerViewAdapter_Parent_home(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        menuItemcart = menu.findItem(R.id.Logout);


        return true;

    }
    private void toolbarTextAppernce() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }
    private void setNotifCount(int count) {

        invalidateOptionsMenu();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.Password:
                // Toast.makeText(Activity_Main.this, "Clicking", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(Activity_Parent_Home.this, Activity_Parent_ChangePassword.class);
                startActivity(intent2);

                return true;


            case R.id.Logout:

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Parent_Home.this);
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
            Log.e("testing", "mobileno value=" + mobile);
            Log.e("testing", "type value=" + type);
            userpramas.add(new BasicNameValuePair(End_Urls.TEACHER_Logout_MOBILENO, mobile));
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
                ((Activity_Parent_Home) context).finish();

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
            pDialog = new ProgressDialog(Activity_Parent_Home.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_PROFILE_MOBILE, mobile));
            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_PROFILE_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_PROFILE_SCHOOLID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_PROFILE_LOGINID, Student_id));


            Log.e("testing","mobile"+mobile);
            Log.e("testing","Logintype"+type);
            Log.e("testing","Schoolid"+School_id);
            Log.e("testing","Student_id"+Student_id);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.STUDENT_PROFILE, "POST", userpramas);


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
                    studentId = post.getString("studentId");
                    firstName = post.getString("firstName");
                    rollNumber = post.getString("rollNumber");
                    dob = post.getString("dob");
                    gender = post.getString("gender");
                    permanantAddress = post.getString("permanantAddress");
                    photo = post.getString("studentPhoto");
                    ProfilePhotoURL = post.getString("ProfilePhotoURL");
                    classTeacherId = post.getString("classTeacherId");
                    classTeacherName = post.getString("classTeacherName");




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
            new CountDownTimer(700, 100) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    try {
                        pDialog.dismiss();
                        pDialog = null;
                    } catch (Exception e) {
                        //TODO: Fill in exception
                    }
                }
            }.start();


            Log.e("testing", "result is = " + responce);

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            if (Result.equals("Success")){
                finalphoto = ProfilePhotoURL+""+photo;
                finallogo = logoURL+""+schoolLogo;
                Log.e("testing","image in adapter = "+finallogo);
                SharedPreferences prefuserdata = getSharedPreferences("myprofile", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("name", "" + firstName);
                prefeditor.putString("empId", "" + rollNumber);
                prefeditor.putString("photo", "" + photo);
                prefeditor.putString("finalphoto", "" + finalphoto);
                prefeditor.putString("gender", "" + gender);
                prefeditor.putString("presentaddress", "" + permanantAddress);
                prefeditor.putString("email", "" + gender);
                prefeditor.putString("bloodGroup", "" + bloodGroup);
                prefeditor.putString("Mobile", "" + dob);


                prefeditor.commit();

                teextteachername.setText(classTeacherName);
                textprofilename.setText(firstName);
                textrollno.setText(rollNumber+", "+className + ", "+sectionName + " Section");
                //textclass.setText(className + ", "+sectionName + " Section");
                if (photo == null||photo.equals("")||photo.equals(null)||photo.equals("null")){
                    imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgthumbnail.setOval(true);
                    imgthumbnail.setImageResource(R.drawable.profilewhite);
                }else{



                    imgthumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imgthumbnail.setOval(true);

                    Log.e("testing","Image in Dynamic="+finalphoto);
                    Glide.with(Activity_Parent_Home.this)
                            .load(Uri.parse(finalphoto))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imgthumbnail);
                }

                if (schoolLogo == null || schoolLogo.equals("")||schoolLogo.equals(null)||schoolLogo.equals("null")){


                }else{


                 /*   Log.e("testing","Image in Dynamic="+finallogo);
                    Glide.with(Activity_Parent_Home.this)
                            .load(Uri.parse(finallogo))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .skipMemoryCache(true)
                            .into(imagelogo);*/
                }

            }else if (Result.equals("Error")){

                Toast.makeText(Activity_Parent_Home.this, "no data found", Toast.LENGTH_LONG).show();
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
    }*/


}
