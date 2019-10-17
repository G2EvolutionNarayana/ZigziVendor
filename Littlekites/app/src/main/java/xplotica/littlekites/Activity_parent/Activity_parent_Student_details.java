package xplotica.littlekites.Activity_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter_parent.parent_Student_details_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_Student_details_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_parent_Student_details extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<parent_Student_details_feederInfo> allItems1 = new ArrayList<parent_Student_details_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_Student_details_feederInfo> mListFeederInfo;
    private parent_Student_details_feederInfo adapter;
    parent_Student_details_Adapter mAdapterFeeds;

    RecyclerView rView;

    Context context;


    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    String[]School = new String[]{"DAV School","DAV School","DAV School","DAV School","DAV School","DAV School"};
    String[]Student = new String[]{"Santanu","Santanu","Santanu","Santanu","Santanu","Santanu"};
    String[]Rollno = new String[]{"1213456","2231456","3214563","4213456","5123456","1234566"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_student_details);

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");

        Log.e("testing","mobile============== student"+mobile);
        context=this;


        mFeedRecyler = (RecyclerView)findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((this)));

        //setUpRecycler();
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

       // setUpRecycler();

    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<parent_Student_details_feederInfo>();
        for(int i=0;i<School.length;i++){
            parent_Student_details_feederInfo notification_feederInfo = new parent_Student_details_feederInfo();
            notification_feederInfo.set_school(School[i]);
            notification_feederInfo.set_student(Student[i]);
            notification_feederInfo.set_rollno(Rollno[i]);

            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new parent_Student_details_Adapter(this,mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


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
            pDialog = new ProgressDialog(Activity_parent_Student_details.this);
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


            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_DETAILS_MOBILE, mobile));
            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.STUDENT_DETAILS_SCHOOLID, School_id));


            Log.e("testing","mobile"+mobile);
            Log.e("testing","Logintype"+type);
            Log.e("testing","Schoolid"+School_id);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.STUDENT_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("studentDetails");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();



                    parent_Student_details_feederInfo item = new parent_Student_details_feederInfo();


                    item.set_student(post.optString("studentName"));
                    item.set_school(post.optString("schoolName"));
                    item.set_rollno(post.optString("rollNumber"));


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


                mAdapterFeeds= new parent_Student_details_Adapter(context,allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


                Log.e("testing","recycleler Adapter-===");

            }else if (status.equals("Error")){

                 Toast.makeText(Activity_parent_Student_details.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

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

}