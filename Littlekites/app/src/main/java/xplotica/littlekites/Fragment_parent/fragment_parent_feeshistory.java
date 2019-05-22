package xplotica.littlekites.Fragment_parent;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
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

import xplotica.littlekites.Adapter_parent.parent_fee_history_adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_fee_history;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class fragment_parent_feeshistory  extends Fragment {

    JSONParser jsonParser = new JSONParser();
    private ArrayList<parent_fee_history> allItems1 = new ArrayList<parent_fee_history>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_fee_history> mListFeederInfo;
    private parent_fee_history adapter;
    parent_fee_history_adapter mAdapterFeeds;

    RecyclerView rView;


    String []Amount = new String[]{"10000","5000","3500","25000"};
    String []Time = new String[]{"21-10-2017","15-09-2017","01-05-2017","15-03-2017"};

    String []Details = new String[]{"Tution Fees","Hostel Fees","Books Fees","Tution Fees"};

    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_feeshistory, container, false);

        SharedPreferences prefuserdata= getActivity().getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");

        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));

        // context = this;
        // lLayout = new GridLayoutManager(this,2);
        rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);


        // setUpRecycler();

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            new TopTrend().execute();

        } else {

            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


        return rootView;
    }


    private void setUpRecycler() {
        mListFeederInfo =new ArrayList<parent_fee_history>();

        for(int i=0;i<Amount.length;i++){
            parent_fee_history feedInfo = new parent_fee_history();


            feedInfo.setAmount(Amount[i]);
            feedInfo.setTime(Time[i]);
            feedInfo.setDescription(Details[i]);

            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new parent_fee_history_adapter(getActivity(),mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }

    class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;


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

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            userpramas.add(new BasicNameValuePair(End_Urls.PARENTFEESHIS_StudentID,Student_id ));


            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENTFEESHIS_URL, "POST", userpramas);

            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");

            try {
                status = json.getString("status");
                String arrayresponce = json.getString("paymentHistory");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();

                    parent_fee_history item = new parent_fee_history();

                    item.setDescription(post.optString("installmentName"));
                    item.setAmount(post.optString("amount"));
                    item.setTransid(post.optString("installmentId"));
                    item.setTransstatus(post.optString("transactionStatus"));
                    item.setTime(post.optString("transtactionDate"));
                    item.setPaymenttype(post.optString("paymentType"));

                    //  item.setResoan(post.optString("product_image"));

                    allItems1.add(item);

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


            if (status.equals("Success")){

                mAdapterFeeds = new parent_fee_history_adapter(getActivity(), allItems1);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            }else if (status.equals("no")){
                //  Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }


}
