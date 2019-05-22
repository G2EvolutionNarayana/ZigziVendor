package xplotica.littlekites.Fragment_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Activity_parent.Activity_Parent_FeesIntal;
import xplotica.littlekites.Activity_parent.Activity_Parent_FeesOthers;
import xplotica.littlekites.Adapter_parent.Parent_Adapter_Fees;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.Parent_feesinfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class fragment_parent_feesentry extends Fragment {



    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<Parent_feesinfo> allItems1 = new ArrayList<Parent_feesinfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<Parent_feesinfo> mListFeederInfo;
    private Parent_feesinfo adapter;
    Parent_Adapter_Fees mAdapterFeeds;

    Context context;

    RecyclerView rView;

    // private GridLayoutManager lLayout;

    String []Cab_service = new String[]{"Cab Service","Cab Service","Cab Service","Cab Service"};
    String []Time = new String[]{"8.10 A.M","8.10 A.M","8.10 A.M","8.10 A.M"};
    String []Cab_driver = new String[]{"Cab Driver","Cab Driver","Cab Driver","Cab Driver"};
    String []Name = new String[]{"Rajeev Ranjan","Rajeev Ranjan","Rajeev Ranjan","Rajeev Ranjan"};
    String []Details = new String[]{"Now I am starting from my location after 20 min,i will reach in your pick up address,please be there on time.","I am starting from your pick up address.","I reached at your pick up address.","I am starting from your pick up address."};

    String id,loginid;

    TextView texttotalfees, textpaid, textbal;
    Button butother;
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent_feesentry, container, false);


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

        texttotalfees = (TextView) rootView.findViewById(R.id.texttotalfees);
        textpaid = (TextView) rootView.findViewById(R.id.textpaid);
        textbal = (TextView) rootView.findViewById(R.id.textbal);

        butother = (Button) rootView.findViewById(R.id.butother);

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            new TopTrend().execute();

        } else {

            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }

        butother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Parent_FeesOthers.class);
                startActivity(intent);
            }
        });
        return rootView;
    }


    class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;
        String strtotalamount, strpaid, strbal;
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
            mListFeederInfo =new ArrayList<Parent_feesinfo>();

            Log.e("testing","School_id = "+School_id);
            Log.e("testing","Student_id = "+Student_id);
            Log.e("testing","Classid = "+Classid);

            userpramas.add(new BasicNameValuePair(End_Urls.PARENTFEESINFO_SchoolID,School_id ));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTFEESINFO_StudentID,Student_id ));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENTFEESINFO_ClassID,Classid ));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENTFEESINFO_URL, "POST", userpramas);

            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                strtotalamount = json.getString("total amount");
                strpaid = json.getString("paid amount");
                strbal = json.getString("balance amount");
                arrayresponce = json.getString("installment");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    Parent_feesinfo item = new Parent_feesinfo();


                    item.setId(post.optString("installmentId"));
                    item.setName(post.optString("InstallmentName"));
                    item.setAmount(post.optString("installmentAmount"));
                    item.setDate(post.optString("dueDate"));
                    item.setStatus(post.optString("paymentstatus"));


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


            if (status.equals("success")){
                texttotalfees.setText(strtotalamount);
                textpaid.setText(strpaid);
                textbal.setText(strbal);
                mAdapterFeeds = new Parent_Adapter_Fees(getActivity(), mListFeederInfo);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            }else if (status.equals("no")){
                Toast.makeText(getActivity(), ""+arrayresponce, Toast.LENGTH_SHORT).show();


            }

        }
    }

}
