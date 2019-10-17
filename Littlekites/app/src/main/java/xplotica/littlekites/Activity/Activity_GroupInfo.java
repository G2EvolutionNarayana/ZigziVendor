package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter.GroupinfoAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.GroupinfoEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_GroupInfo extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;
    CollapsingToolbarLayout collapsingToolbar;
    RecyclerView rView1;
    GroupinfoAdapter mAdapterFeeds;
    private ArrayList<GroupinfoEntity> mListFeederInfo;
    Context context;
    String strtgroupid;
    String groupid,groupname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_info);

        context = this;


        SharedPreferences prefuserdata2 = this.getSharedPreferences("group", 0);
        groupid = prefuserdata2.getString("groupid", "");
        groupname = prefuserdata2.getString("homename", "");

        Log.e("testing","groupid = "+groupid);
        Log.e("testing","groupname = "+groupname);

        //  -----------------For Collapsing image Layout------------------------
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Group Info");
        toolbarTextAppernce();

        rView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        rView1.setLayoutManager(new LinearLayoutManager(this));
        rView1.setHasFixedSize(true);

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

            new LoadSpinnerdata().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


    }
    private void toolbarTextAppernce() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
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
            mListFeederInfo = new ArrayList<GroupinfoEntity>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "groupid value=" + groupid);
            userpramas.add(new BasicNameValuePair(End_Urls.GroupInfo_Id, groupid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.GroupInfo_Url, "POST", userpramas);


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

                            GroupinfoEntity item = new GroupinfoEntity();
                            item.setGroupid(post.optString("groupId"));
                            item.setGroupname(post.optString("groupName"));
                            item.setUsername(post.optString("teacherName"));




                            // Log.e("testing", currentobj.getString("services"));

                            mListFeederInfo.add(item);
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


                mAdapterFeeds= new GroupinfoAdapter(context, mListFeederInfo);
                rView1.setAdapter(mAdapterFeeds);





            }else{

                // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }


}
