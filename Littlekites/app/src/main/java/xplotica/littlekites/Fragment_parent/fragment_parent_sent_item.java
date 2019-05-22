package xplotica.littlekites.Fragment_parent;

import android.app.ProgressDialog;
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

import org.apache.http.NameValuePair;
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

import xplotica.littlekites.Adapter_parent.parent_Sentitem_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.parent_sentitem_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.JSONParser;


/**
 * Created by Sujata on 22-03-2017.
 */
public class fragment_parent_sent_item extends Fragment {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<parent_sentitem_feederInfo> allItems1 = new ArrayList<parent_sentitem_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<parent_sentitem_feederInfo> mListFeederInfo;
    private parent_sentitem_feederInfo adapter;
    parent_Sentitem_Adapter mAdapterFeeds;
    RecyclerView rView;


    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat dateFormat;
    java.util.Date dt = null;


    String[]Message = new String[]{"Today My Son is Not Felling Well","Today My Son is Not Felling Well","Today My Son is Not Felling Well","Today My Son is Not Felling Well"};

    String[]Topics = new String[]{"Mathematics","Mathematics","Mathematics","Mathematics"};
    java.util.Date[]Date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_sentitem, container, false);


        try {
            dt = ft.parse("2017-01-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date = new Date[]{dt, dt, dt, dt};

       dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        mFeedRecyler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager((getActivity())));

        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(context,2);
        rView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        //mFeedRecyler.setHasFixedSize(true);

        setUpRecycler();


/*
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {


            //  new TopTrend().execute();


        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }*/

        return rootView;

    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<parent_sentitem_feederInfo>();
        for(int i=0;i<Message.length;i++){
            parent_sentitem_feederInfo notification_feederInfo = new parent_sentitem_feederInfo();
            notification_feederInfo.setMessage(Message[i]);
            notification_feederInfo.setTime(Topics[i]);
            notification_feederInfo.setDate(Date[i]);


            mListFeederInfo.add(notification_feederInfo);
        }
        mAdapterFeeds= new parent_Sentitem_Adapter(getActivity(),mListFeederInfo);
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


            //userpramas.add(new BasicNameValuePair("customerId", RId));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.STUDENT_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("Specialist");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    parent_sentitem_feederInfo item = new parent_sentitem_feederInfo();


                    item.setMessage(post.optString("specialist"));
                    item.setTime(post.optString("regDate"));

                    String date = post.getString("regDate");


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


            if (status.equals("yes")){


                mAdapterFeeds= new parent_Sentitem_Adapter(getActivity(),mListFeederInfo);
                mFeedRecyler.setAdapter(mAdapterFeeds);



            }else if (status.equals("no")){
                //  Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }

    }

}