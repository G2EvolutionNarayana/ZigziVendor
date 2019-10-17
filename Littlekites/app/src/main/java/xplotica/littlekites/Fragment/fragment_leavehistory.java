package xplotica.littlekites.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter.Adapter_Leave_History;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.FeederInfo_leave_history;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class fragment_leavehistory extends Fragment {



    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_leave_history> allItems1 = new ArrayList<FeederInfo_leave_history>();

    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_leave_history> mListFeederInfo;
    private FeederInfo_leave_history adapter;
    Adapter_Leave_History mAdapterFeeds;

    Context context;

    RecyclerView rView;

    // private GridLayoutManager lLayout;


    String []Date = new String[]{"2017-10-21 12:05:03","2017-10-15 08:30:32","2017-09-11 16:58:02","2017-01-15 21:07:54"};
    String []Fromdate = new String[]{"25-10-2017","01-07-2017","15-05-2017","20-02-2017"};
    String []todate = new String[]{"25-10-2017","05-07-2017","17-05-2017","23-02-2017"};
    String []Details = new String[]{"Need leave for some personal reason","Need leave for some personal reason","Need leave for some personal reason","Need leave for some personal reason"};


    String schoolid,staffid,type,classid,sectionid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leavehistory, container, false);

        SharedPreferences prefuserdata = getActivity().getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");

        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // context = this;
        // lLayout = new GridLayoutManager(this,2);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        //setUpRecycler();
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            new TopTrend().execute();

        } else {

            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }




        return rootView;
    }



    private void setUpRecycler() {
        mListFeederInfo =new ArrayList<FeederInfo_leave_history>();

        for(int i=0;i<Date.length;i++){
            FeederInfo_leave_history feedInfo = new FeederInfo_leave_history();
            feedInfo.setDate(Date[i]);

            feedInfo.setFromdate(Fromdate[i]);
            feedInfo.setTodate(todate[i]);

            feedInfo.setDescription(Details[i]);
            // feedInfo.setBackcolor(Color[i]);
            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new Adapter_Leave_History(getActivity(),mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }
    class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;
        String arrayresponce;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
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
            mListFeederInfo =new ArrayList<FeederInfo_leave_history>();

            userpramas.add(new BasicNameValuePair(End_Urls.LEAVEMANAGEMENTHistory_TEACHERID,staffid ));

            userpramas.add(new BasicNameValuePair(End_Urls.LEAVEMANAGEMENTHistory_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.LEAVEMANAGEMENTHistory_CLASSID, classid));
            userpramas.add(new BasicNameValuePair(End_Urls.LEAVEMANAGEMENTHistory_SCETIONID, sectionid));


            JSONObject json = jsonParser.makeHttpRequest(End_Urls.LEAVEMANAGEMENTHistory_URL, "POST", userpramas);

            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                arrayresponce = json.getString("leaveList");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    FeederInfo_leave_history item = new FeederInfo_leave_history();


                    item.setFromdate(post.optString("fromDate"));
                    item.setDate(post.optString("currentdate"));
                    item.setTodate(post.optString("toDate"));
                    item.setAccept(post.optString("lmStatus"));
                    item.setReason(post.optString("rejectReson"));
                    item.setDescription(post.optString("Description"));

                    mListFeederInfo.add(item);

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


            if (status.equals("Success")){

                mAdapterFeeds = new Adapter_Leave_History(getActivity(), mListFeederInfo);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            }else if (status.equals("no")){
                Toast.makeText(getActivity(), ""+arrayresponce, Toast.LENGTH_SHORT).show();


            }

        }
    }

}
