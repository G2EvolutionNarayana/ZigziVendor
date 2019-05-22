package xplotica.littlekites.Activity_parent;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import xplotica.littlekites.Adapter_parent.parent_Homework_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_HomeWork_feederInfo;
import xplotica.littlekites.FeederInfo_parent.parent_Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_parent_Home_work extends AppCompatActivity implements View.OnClickListener {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<parent_HomeWork_feederInfo> allItems1 = new ArrayList<parent_HomeWork_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_HomeWork_feederInfo> mListFeederInfo;
    private parent_Student_details_feederInfo adapter;
    parent_Homework_Adapter mAdapterFeeds;
    RecyclerView rView;

    Context context;

    TextView SelectDate;

    String[]Message = new String[]{"Today home work has been assigned","Today home work has been assigned","Today home work has been assigned","Today home work has been assigned","Today home work has been assigned","Today home work has been assigned"};
    String[]Time = new String[]{"11-01-2017","11-01-2017","11-01-2017","11-01-2017","11-01-2017","11-01-2017"};
    String[]TopicName = new String[]{"Mathematics","Science","History","History","History","History"};


    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    private DatePickerDialog toDatePickerDialog;

    public Calendar newDate2 = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;

    String strdate;

    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_home_work);

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");



        Log.e("testing","mobile============== "+mobile);


        Log.e("testing","type====="   +  type);
        Log.e("testing","studentid====="   +  Student_id);
        Log.e("testing","schoolname====="   +  School_name);
        Log.e("testing","School_id====="   +  School_id);
        Log.e("testing","Classid====="   +  Classid);
        Log.e("testing","Sectionid====="   +  Sectionid);

        back=(ImageView)findViewById(R.id.back);

        context =this;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(), Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        SelectDate =(TextView)findViewById(R.id.selectDate);


        SelectDate.setOnClickListener(this);
        SelectDate.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();

        SelectDate.addTextChangedListener(generalTextWatcher);

        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((this)));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);

        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        strdate = "0";

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

            new TopTrend2().execute();


        } else {


            Toast.makeText(getApplicationContext(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        //mFeedRecyler.setHasFixedSize(true);

       // setUpRecycler();

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

            strdate = SelectDate.getText().toString();
            //  Toast.makeText(getActivity(), "strdate==="+strdate, Toast.LENGTH_SHORT).show();

            Log.e("testing","strtDate======" +strdate );


            ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {

               new TopTrend2().execute();


            } else {


                Toast.makeText(getApplicationContext(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }

    };


    private void setDateTimeField() {
        SelectDate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();



        toDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate2.set(year, monthOfYear, dayOfMonth);
                SelectDate.setText(dateFormatter.format(newDate2.getTime()));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<parent_HomeWork_feederInfo>();
        for(int i=0;i<TopicName.length;i++){
            parent_HomeWork_feederInfo notification_feederInfo = new parent_HomeWork_feederInfo();
            notification_feederInfo.set_topicName(TopicName[i]);
            notification_feederInfo.set_time(Time[i]);
            notification_feederInfo.set_message(Message[i]);


            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new parent_Homework_Adapter(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }

    @Override
    public void onClick(View v) {
        if(v == SelectDate) {


            toDatePickerDialog.show();
            //  showTruitonTimePickerDialog(v);
            // showTruitonDatePickerDialog(v);
        }
    }

 /*   class TopTrend extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(context);
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


            //userpramas.add(new BasicNameValuePair("customerId", RId));

            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_CLASSID, Classid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_SECTIONID, Sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_DATE, strdate));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_SCHOOL_ID, School_id));


            Log.e("testing","type====="   +  type);
            Log.e("testing","studentid====="   +  Student_id);
            Log.e("testing","schoolname====="   +  School_name);
            Log.e("testing","School_id====="   +  School_id);
            Log.e("testing","Classid====="   +  Classid);
            Log.e("testing","Sectionid====="   +  Sectionid);


            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOMEWORK_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("homeworkHistory");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    parent_HomeWork_feederInfo item = new parent_HomeWork_feederInfo();


                    item.set_topicName(post.optString("subjectName"));
                    item.set_time(post.optString("homeWorkDate"));
                    item.set_message(post.optString("homeWork"));


                    allItems1.add(item);

                    arraylist1.add(map);

                }

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


                mAdapterFeeds= new parent_Homework_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);



            }else if (status.equals("Error")){
                 Toast.makeText(context, "no data found", Toast.LENGTH_LONG).show();
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
            pDialog = new ProgressDialog(Activity_parent_Home_work.this);
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


            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_CLASSID,Classid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_SECTIONID, Sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_SCHOOL_ID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_DETAILS_DATE, strdate));



            Log.e("testing","Logintype = "+type);
            Log.e("testing","Classid = "+Classid);
            Log.e("testing","Sectionid = "+Sectionid);
            Log.e("testing","Schoolid = "+School_id);
            Log.e("testing","strdate = "+strdate);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOMEWORK_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("homeworkHistory");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);



                allItems1.clear();
                arraylist1.clear();

                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();



                    parent_HomeWork_feederInfo item = new parent_HomeWork_feederInfo();


                    //item.setDairyid(post.optString("diary_id"));
                    item.set_subject(post.optString("subjectName"));
                    item.set_message(post.optString("homeWork"));
                    item.set_topicName(post.optString("homeWorkDate"));
                    item.setDate(post.optString("homeWorkRegDate"));


                    Log.e("testing","studentName============="+"diary_id");
                    Log.e("testing","schoolName======"+"dairyDate");
                    Log.e("testing","rollNumber======"+"dairyDescription");


                    allItems1.add(item);

                    arraylist1.add(map);

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

            Log.e("testing","studentName============="+"diary_id");
            Log.e("testing","schoolName======"+"dairyDate");
            Log.e("testing","rollNumber======"+"dairyDescription");

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            if (status.equals("Success")){


                mAdapterFeeds= new parent_Homework_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


                Log.e("testing","recycleler Adapter-===");

            }else if (status.equals("Error")){

                allItems1.clear();
                arraylist1.clear();

                mAdapterFeeds= new parent_Homework_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(Activity_parent_Home_work.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }

}
