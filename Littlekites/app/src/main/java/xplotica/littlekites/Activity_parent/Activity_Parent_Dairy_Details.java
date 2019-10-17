package xplotica.littlekites.Activity_parent;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import xplotica.littlekites.Adapter_parent.parent_Dairy_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_Dairy_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Santanu on 16-04-2017.
 */

public class Activity_Parent_Dairy_Details extends AppCompatActivity implements View.OnClickListener {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<parent_Dairy_feederInfo> allItems1 = new ArrayList<parent_Dairy_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_Dairy_feederInfo> mListFeederInfo;
    private parent_Dairy_feederInfo adapter;
    parent_Dairy_Adapter mAdapterFeeds;
    RecyclerView rView;

    Context context;

    TextView select_date;
    String schoolid, staffid, classid, sectionid;


    private DatePickerDialog toDatePickerDialog;
    public Calendar newDate2 = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;

    String[] Topic = new String[]{"School Annual Function", "School Annual Function", "School Annual Function", "School Annual Function", "School Annual Function", "School Annual Function"};
    String[] Topic_details = new String[]{"We are informing you that 30th March is School Annual function.Kindly present on time.", "We are informing you that 30th March is School Annual function.Kindly present on time.", "We are informing you that 30th March is School Annual function.Kindly present on time.", "We are informing you that 30th March is School Annual function.Kindly present on time.", "We are informing you that 30th March is School Annual function.Kindly present on time.", "We are informing you that 30th March is School Annual function.Kindly present on time."};

    String strdate;
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dairy);

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");



        Log.e("testing","type====="   +  type);
        Log.e("testing","studentid====="   +  Student_id);
        Log.e("testing","schoolname====="   +  School_name);
        Log.e("testing","School_id====="   +  School_id);
        Log.e("testing","Classid====="   +  Classid);
        Log.e("testing","Sectionid====="   +  Sectionid);


        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((Activity_Parent_Dairy_Details.this)));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        //mFeedRecyler.setHasFixedSize(true);

        //setUpRecycler();

        strdate = "0";

        ConnectionDetector cd = new ConnectionDetector(Activity_Parent_Dairy_Details.this);
        if (cd.isConnectingToInternet()) {

            new TopTrend2().execute();


        } else {


            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(),Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        select_date=(TextView)findViewById(R.id.selectDate);

       /* select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });
*/

        select_date.setOnClickListener(this);
        select_date.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();

        select_date.addTextChangedListener(generalTextWatcher);




    }


    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            strdate = select_date.getText().toString();
            //  Toast.makeText(getActivity(), "strdate==="+strdate, Toast.LENGTH_SHORT).show();
            //setUpRecycler();

            ConnectionDetector cd = new ConnectionDetector(Activity_Parent_Dairy_Details.this);
            if (cd.isConnectingToInternet()) {

                new TopTrend2().execute();


            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }

    };

    private void setDateTimeField() {
        select_date.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();



        toDatePickerDialog = new DatePickerDialog(Activity_Parent_Dairy_Details.this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate2.set(year, monthOfYear, dayOfMonth);
                select_date.setText(dateFormatter.format(newDate2.getTime()));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    @Override
    public void onClick(View v) {
        if(v == select_date) {


            toDatePickerDialog.show();
            //  showTruitonTimePickerDialog(v);
            // showTruitonDatePickerDialog(v);
        }
    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<parent_Dairy_feederInfo>();
        for(int i=0;i<Topic.length;i++){
            parent_Dairy_feederInfo feederInfo = new parent_Dairy_feederInfo();
            feederInfo.set_topicName(Topic[i]);
            feederInfo.set_message(Topic_details[i]);


            mListFeederInfo.add(feederInfo);
        }
        mAdapterFeeds= new parent_Dairy_Adapter(Activity_Parent_Dairy_Details.this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }

   /* public  class TopTrend extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_Parent_Dairy_Details.this);
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


            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_SCHOOL_ID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_SECTIONID, sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_CLASSID, classid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_DATE, strdate));


            Log.e("testing","mobile"+mobile);
            Log.e("testing","Logintype"+type);
            Log.e("testing","Schoolid"+School_id);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.DAIRY_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");


            try {
                status = json.getString("status");
                String arrayresponce = json.getString("DiaryHistory");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);
                allItems1.clear();

                arraylist1.clear();
                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);

                    HashMap<String, String> map = new HashMap<String, String>();



                    parent_Dairy_feederInfo item = new parent_Dairy_feederInfo();


                    item.setDairyid(post.optString("diary_id"));
                    item.set_topicName(post.optString("dairyDate"));
                    item.set_message(post.optString("dairyDescription"));

                    Log.d("item",String.valueOf(responcearray.length()));

                    Log.e("testing","studentName============="+"studentName");
                    Log.e("testing","schoolName======"+"schoolName");
                    Log.e("testing","rollNumber======"+"rollNumber");


                    allItems1.add(item);

                    arraylist1.add(map);

                }

               *//* String arrayresponce1 = json.getString("Specialist");
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

                }*//*

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

            *//*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*//*


            if (status.equals("Success")){


                mAdapterFeeds= new parent_Dairy_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


                Log.e("testing","recycleler Adapter-===");

            }else if (status.equals("Error")){

                Toast.makeText(Activity_Parent_Dairy_Details.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }*/


    public  class TopTrend2 extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_Parent_Dairy_Details.this);
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

/*

            userpramas.add(new BasicNameValuePair("loginType", "2"));
            userpramas.add(new BasicNameValuePair("schoolId", "5"));
            userpramas.add(new BasicNameValuePair("diaryDate", "2017-04-16"));
            userpramas.add(new BasicNameValuePair("classId", "2"));
            userpramas.add(new BasicNameValuePair("sectionId", "4"));
*/


            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_CLASSID,Classid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_SECTIONID, Sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_SCHOOL_ID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DETAILS_DATE, strdate));



            Log.e("testing","Logintype = "+type);
            Log.e("testing","Classid = "+Classid);
            Log.e("testing","Sectionid = "+Sectionid);
            Log.e("testing","Schoolid = "+School_id);
            Log.e("testing","strdate = "+strdate);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.DAIRY_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("DiaryHistory");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);

                allItems1.clear();
                arraylist1.clear();


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();



                    parent_Dairy_feederInfo item = new parent_Dairy_feederInfo();


                    item.setDairyid(post.optString("diary_id"));
                    item.set_message(post.optString("dairyDescription"));
                    item.set_topicName(post.optString("dairyDate"));
                    item.setDate(post.optString("dairyRegDate"));


                    Log.e("testing","studentName============="+"diary_id");
                    Log.e("testing","schoolName======"+"dairyDate");
                    Log.e("testing","rollNumber======"+"dairyDescription");


                    allItems1.add(item);

                    arraylist1.add(map);

                }
                Log.d("Length", String.valueOf(responcearray.length()));

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

            Log.e("testing","studentName============="+"diary_id");
            Log.e("testing","schoolName======"+"dairyDate");
            Log.e("testing","rollNumber======"+"dairyDescription");

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            if (status.equals("Success")){


                mAdapterFeeds= new parent_Dairy_Adapter(Activity_Parent_Dairy_Details.this,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


                Log.e("testing","recycleler Adapter-===");

            }else if (status.equals("Error")){

                allItems1.clear();
                arraylist1.clear();


                mAdapterFeeds= new parent_Dairy_Adapter(Activity_Parent_Dairy_Details.this,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(Activity_Parent_Dairy_Details.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }

}