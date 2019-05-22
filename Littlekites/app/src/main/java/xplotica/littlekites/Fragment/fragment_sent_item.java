package xplotica.littlekites.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter.Fragment_sentitem_Adapter;
import xplotica.littlekites.FeederInfo.Fragment_sentitem_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Sujata on 22-03-2017.
 */
public class fragment_sent_item  extends Fragment {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<Fragment_sentitem_feederInfo> allItems1 = new ArrayList<Fragment_sentitem_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<Fragment_sentitem_feederInfo> mListFeederInfo;
    private Fragment_sentitem_feederInfo adapter;
    Fragment_sentitem_Adapter mAdapterFeeds;
    RecyclerView rView;
    Context context;

    String[]Topic = new String[]{"Regarding Absent","Regarding Absent","Regarding Absent","Regarding Absent","Regarding Absent"};
    String[]Date = new String[]{"23 March 2017","23 March 2017","23 March 2017","23 March 2017","23 March 2017"};
    String[]Rollno = new String[]{"12501","12501","12501","12501","12501"};
    String[]Name = new String[]{"Rajeev","Rajeev","Rajeev","Rajeev","Rajeev"};
    String[]Section = new String[]{"A","B","C","D","E"};
    String[]Message = new String[]{"Your son is absent from 23 march,can i know the reason.","Your son is absent from 23 march,can i know the reason.","Your son is absent from 23 march,can i know the reason.","Your son is absent from 23 march,can i know the reason.","Your son is absent from 23 march,can i know the reason."};




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sentitem, container, false);

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

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            // new TopTrend().execute();


        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }



        return rootView;

    }

    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<Fragment_sentitem_feederInfo>();
        for(int i=0;i<Topic.length;i++){
            Fragment_sentitem_feederInfo feederInfo = new Fragment_sentitem_feederInfo();
            feederInfo.set_topic(Topic[i]);
            feederInfo.set_date(Date[i]);
            feederInfo.set_rollno(Rollno[i]);
            feederInfo.set_name(Name[i]);
            feederInfo.set_section(Section[i]);
            feederInfo.set_message(Message[i]);

            mListFeederInfo.add(feederInfo);
        }
        mAdapterFeeds= new Fragment_sentitem_Adapter(getActivity(),mListFeederInfo);
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
            arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();




            JSONObject json = jsonParser.makeHttpRequest("", "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("products");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    Fragment_sentitem_feederInfo item = new Fragment_sentitem_feederInfo();


                    item.set_topic(post.optString("product_name"));
                    item.set_date(post.optString("product_company"));
                    item.set_rollno(post.optString("product_price"));
                    item.set_name(post.optString("product_rating"));
                    //item.set_id(post.optString("product_id"));
                    item.set_section(post.optString("product_id"));
                    item.set_message(post.optString("product_image"));

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



                mAdapterFeeds = new Fragment_sentitem_Adapter(getActivity(), allItems1);

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