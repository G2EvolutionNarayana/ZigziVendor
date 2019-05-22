package xplotica.littlekites.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import xplotica.littlekites.Adapter.Wed_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.Wed_Info;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 7/4/2017.
 */

public class fragment_wed extends Fragment {

    JSONParser jsonParser = new JSONParser();

    private RecyclerView mFeedRecyler;
    private ArrayList<Wed_Info> mListFeederInfo;
    private Wed_Adapter mAdapterFeeds;
    String[] Period = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
    String[] Time = new String[]{"9:00 AM - 09:45 AM", "09:45 AM - 10:30 AM", "10:45 AM - 11:30 AM", "11:30 AM - 12:15 PM", "1:15 PM - 2:00 PM", "2:00 PM - 2:45 PM", "3:00 PM - 3:45 PM", "3:45 PM - 4:30 PM"};

    String[] Class = new String[]{"1", "2", "4", "7", "3", "5", "8", "6"};

    String[] Section = new String[]{"A", "C", "B", "A", "B", "C", "B", "A"};

    String[] Subject = new String[]{"Maths", "Maths", "Maths", "Social", "Maths", "Maths", "Maths", "Social"};

    String schoolid,staffid,classid,sectionid,type;


    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_wed, container, false);

            SharedPreferences prefuserdata = getActivity().getSharedPreferences("registerUser", 0);
            schoolid = prefuserdata.getString("schoolid", "");
            staffid = prefuserdata.getString("staffid", "");
            classid = prefuserdata.getString("classid", "");
            sectionid = prefuserdata.getString("sectionid", "");
            type = prefuserdata.getString("type", "");




            mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
            mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));


            mFeedRecyler.setHasFixedSize(true);

           // setUpRecycler();

            ConnectionDetector cd = new ConnectionDetector(getActivity());
            if (cd.isConnectingToInternet()) {




                 new LoadSpinnerdata().execute();


            } else {


                Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }



        } else {

        }
        return rootView;
    }

    private void setUpRecycler() {

        mListFeederInfo = new ArrayList<Wed_Info>();
        for (int i = 0; i < Period.length; i++) {
            Wed_Info feedInfo = new Wed_Info();
            feedInfo.setPeriod(Period[i]);
            feedInfo.setStarttime(Time[i]);
            feedInfo.setClassname(Class[i]);
            feedInfo.setSection(Section[i]);
            feedInfo.setSubject(Subject[i]);

            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds = new Wed_Adapter(getActivity(), mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }


    class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String status;
        String responce;

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


            mListFeederInfo = new ArrayList<Wed_Info>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            Log.e("testing","schoolid = "+schoolid);
            Log.e("testing","staffid = "+staffid);

            userpramas.add(new BasicNameValuePair(End_Urls.TimeTable_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.TimeTable_TEACHERID, staffid));
            userpramas.add(new BasicNameValuePair(End_Urls.TimeTable_DAYID, "3"));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.TimeTable_URL, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");
                    String arrayresponce = json.getString("teacherTimeTable");
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

                            Wed_Info item = new Wed_Info();

                            item.setId(post.optString("timeTableId"));
                            item.setPeriod(post.optString("periodNo"));
                            item.setStarttime(post.optString("periodStartTime"));
                            item.setEndtime(post.optString("periodEndTime"));
                            item.setClassname(post.optString("className"));
                            item.setSection(post.optString("sectionName"));
                            item.setSubject(post.optString("subjectName"));


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

            mAdapterFeeds = new Wed_Adapter(getActivity(), mListFeederInfo);
            mFeedRecyler.setAdapter(mAdapterFeeds);

            mAdapterFeeds.notifyDataSetChanged();

        }

    }


}
