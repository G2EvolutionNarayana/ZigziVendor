package xplotica.littlekites;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.FeederInfo.Details_feederInfo;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;
import xplotica.littlekites.app.Config;
import xplotica.littlekites.utils.NotificationUtils;

public class Activity_Details extends AppCompatActivity {


    String regId;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<Details_feederInfo> allItems1 = new ArrayList<Details_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<Details_feederInfo> mListFeederInfo;
    private Details_feederInfo adapter;
    Details_Adapter mAdapterFeeds;

    RecyclerView rView;

    Context context;


    String strmobile;

    // server key for php----AAAADve3Ut8:APA91bHIyK3qu7TtY5H06f2xHWfOo6xhTmhSvfPmLNdK2fyAoqgs0sEqob-LD0GEgDaIFGTby51t-VChysGcq04sr0MhTDRD49HQo6HJL_TACpoAC7yedY1FAMq_PXTpyas3spKi5QJA---

    // ----FCM server key on janardhan.deucetech@gmail.com----
    //------------------------FCM Notification--------------------------
    //private static final String TAG = getAcgetSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        SharedPreferences prefuserdata= getSharedPreferences("loginresponse",0);
        strmobile=prefuserdata.getString("strmobile","");
        context=this;
        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((this)));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);
        rView = (RecyclerView)findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

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

                    Toast.makeText(context, "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
//------------------------FCM Notification--------------------------


        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {


            new TopTrend().execute();

        } else {

            Toast.makeText(this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

    }

    //------------------------FCM Notification--------------------------------------------------------
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        // Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            Log.e("testing", "Firebase Reg Id: " + regId);


            SharedPreferences prefuserdata = context.getSharedPreferences("tokenid", 0);
            SharedPreferences.Editor prefeditor = prefuserdata.edit();

            prefeditor.putString("regId", "" + regId);


            prefeditor.commit();

        }
            // txtRegId.setText("Firebase Reg Id: " + regId);
        else{
            Log.e("testing", "Firebase Reg Id is not received yet!");
            //  txtRegId.setText("Firebase Reg Id is not received yet!");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(context).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(context);
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    //------------------------FCM Notification------------------------------------------------------




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
            pDialog = new ProgressDialog(Activity_Details.this);
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


            userpramas.add(new BasicNameValuePair(End_Urls.DETAILS_MOBILE, strmobile));


            Log.e("testing","strmobile = "+strmobile);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.DETAILS_URL, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("Profile");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();



                    Details_feederInfo item = new Details_feederInfo();


                    item.setSchoolid(post.optString("school_Id"));
                    item.setSchoolname(post.optString("school_Name"));
                    item.setClassid(post.optString("class_Id"));
                    item.setClassname(post.optString("class_name"));
                    item.setSectionid(post.optString("section_Id"));
                    item.setSectionname(post.optString("section_Name"));
                    item.setLoginid(post.optString("login_Id"));
                    item.setLoginname(post.optString("login_name"));
                    item.setLogintype(post.optString("login_Type"));
                    item.setMobileno(post.optString("mobile"));
                    item.setIdentifyno(post.optString("identifiedNumber"));
                    item.setClassteacherid(post.optString("class_teacher_id"));
                    item.setClassteachername(post.optString("class_teacher_name"));


                    Log.e("testing","studentName============="+"studentName");
                    Log.e("testing","schoolName======"+"schoolName");
                    Log.e("testing","rollNumber======"+"rollNumber");


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

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/


            if (status.equals("Success")){


                mAdapterFeeds= new Details_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


                Log.e("testing","recycleler Adapter-===");

            }else if (status.equals("Error")){

                Toast.makeText(Activity_Details.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }

}
