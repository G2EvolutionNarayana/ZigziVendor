package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter.GroupAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.GroupEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_GroupMessage extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();

    RecyclerView rView2;
    private ArrayList<GroupEntity> mListFeederInfo2;
    GroupAdapter mAdapterFeeds2;
    ImageView back;

    Context context;

    String schoolid,type,loginid;

    String[] Name = new String[]{"Jana", "Mithun", "Santanu", "Rohit", "Jana", "Mithun", "Santanu", "Rohit"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_message);


        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        loginid = prefuserdata.getString("staffid", "");
        type = prefuserdata.getString("type", "");

        Log.e("testing","scoolid = "+schoolid);
        Log.e("testing","loginid = "+loginid);
        Log.e("testing","type = "+type);

        context = this;

        rView2 = (RecyclerView) findViewById(R.id.recycler_view2);
        rView2.setLayoutManager(new LinearLayoutManager(context));
        rView2.setHasFixedSize(true);
        //setUpRecycler2();
        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {


           // setUpRecycler2();
            new LoadSpinnerdata().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });


    }

    private void setUpRecycler2() {
        mListFeederInfo2 = new ArrayList<GroupEntity>();

        for (int i = 0; i < Name.length; i++) {
            GroupEntity feedInfo = new GroupEntity();
            feedInfo.setHomegroupname(Name[i]);

            mListFeederInfo2.add(feedInfo);
        }
        mAdapterFeeds2= new GroupAdapter(context,mListFeederInfo2);
        rView2.setAdapter(mAdapterFeeds2);
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

            mListFeederInfo2 = new ArrayList<GroupEntity>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
             Log.e("testing", "schoolid value=" + schoolid);
             Log.e("testing", "loginid value=" + loginid);
             userpramas.add(new BasicNameValuePair(End_Urls.ADGroupFetch_Schoolid, schoolid));
             userpramas.add(new BasicNameValuePair(End_Urls.ADGroupFetch_Teacherid, loginid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.ADGroupFetch_Url, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");

                    String arrayresponce = json.getString("GroupName");

                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();


                            GroupEntity item = new GroupEntity();
                            item.setHomegroupid(post.optString("groupId"));
                            item.setHomegroupteacherid(post.optString("teacherId"));
                            item.setHomegroupname(post.optString("groupName"));
                            item.setHomegroupteachername(post.optString("teacherName"));




                            // Log.e("testing", currentobj.getString("services"));

                            mListFeederInfo2.add(item);
                        }
                    }



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



            if (status.equals("Success")){


                mAdapterFeeds2= new GroupAdapter(context,mListFeederInfo2);
                rView2.setAdapter(mAdapterFeeds2);





            }else{

                // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }
}

