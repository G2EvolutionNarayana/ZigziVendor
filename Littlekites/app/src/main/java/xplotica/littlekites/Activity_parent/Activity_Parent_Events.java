package xplotica.littlekites.Activity_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter_parent.Parent_Events_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_Events_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_Events extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<parent_Events_feederInfo> allItems1 = new ArrayList<parent_Events_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_Events_feederInfo> mListFeederInfo;
    private parent_Events_feederInfo adapter;
    Parent_Events_Adapter mAdapterFeeds;
    RecyclerView rView;

    Context context;


    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dateFormat;
    java.util.Date dt = null;


    ImageView back;

    String[]EventNAme = new String[]{"Holi","Diwalli","Durga Puja","Ganesh Puja","Annual Function"};

    String[]Start_Date = new String[]{"11-03-2017","11-03-2017","11-03-2017","11-03-2017","11-03-2017"};
    String[]End_Date = new String[]{"15-03-2017","15-03-2017","15-03-2017","18-03-2017","19-03-2017"};
    String[]Message = new String[]{"","","","",""};

    java.util.Date[]Date;

    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__parent__events);

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");


        Log.e("testing","mobile"+mobile);
        Log.e("testing","mobile"+School_id);
        Log.e("testing","mobile"+School_name);
        Log.e("testing","mobile"+Classid);
        Log.e("testing","mobile"+Student_id);

        try {
            dt = ft.parse("2017-01-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date = new Date[]{dt, dt, dt, dt,dt};

        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        context =this;

        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              /*  Intent intent = new Intent(getApplicationContext(), Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });
        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((this)));

      //  setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);

        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);

        //mFeedRecyler.setHasFixedSize(true);


        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {


             new TopTrend().execute();


        } else {


            Toast.makeText(this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


    }


    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<parent_Events_feederInfo>();
        for(int i=0;i<EventNAme.length;i++){
            parent_Events_feederInfo notification_feederInfo = new parent_Events_feederInfo();
            notification_feederInfo.set_eventnaem(EventNAme[i]);
            notification_feederInfo.setDate(Date[i]);
            notification_feederInfo.set_message(Message[i]);
            notification_feederInfo.set_strdate(Start_Date[i]);
            notification_feederInfo.set_enddate(End_Date[i]);


            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new Parent_Events_Adapter(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }


    class TopTrend extends AsyncTask<String, String, String>
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


            userpramas.add(new BasicNameValuePair(End_Urls.EVENT_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.EVENT_DETAILS_CLASSID,Classid ));
            userpramas.add(new BasicNameValuePair(End_Urls.EVENT_DETAILS_SCHOOLID, School_id));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.EVENT_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("data");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    parent_Events_feederInfo item = new parent_Events_feederInfo();


                    item.set_eventnaem(post.optString("title"));
                    item.setId(post.optString("event_id"));
                    item.set_message(post.optString("eventDescription"));
                    item.set_enddate(post.optString("eventEnd"));
                    item.set_strdate(post.optString("eventStart"));
                    String date = post.getString("reg_on");


                    try {
                        item.setDate(dateFormat.parse(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

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

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            if (status.equals("Success")){


                mAdapterFeeds= new Parent_Events_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);



            }else if (status.equals("Error")){
                 Toast.makeText(Activity_Parent_Events.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }


}
