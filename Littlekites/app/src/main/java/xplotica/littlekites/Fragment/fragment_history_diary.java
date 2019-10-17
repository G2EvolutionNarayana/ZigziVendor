package xplotica.littlekites.Fragment;

import android.app.DatePickerDialog;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import xplotica.littlekites.Adapter.History_diary_Adapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.History_diary_feederInfo;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Sujata on 30-03-2017.
 */
public class fragment_history_diary extends Fragment implements View.OnClickListener{

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<History_diary_feederInfo> allItems1 = new ArrayList<History_diary_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<History_diary_feederInfo> mListFeederInfo;
    private History_diary_feederInfo adapter;
    History_diary_Adapter mAdapterFeeds;
    RecyclerView rView;

    Context context;


    TextView select_date;
    String schoolid,staffid,type,classid,sectionid;

    private DatePickerDialog toDatePickerDialog;
    public Calendar newDate2 = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;

    String[]Topic = new String[]{"School Annual Function","School Annual Function","School Annual Function","School Annual Function","School Annual Function","School Annual Function"};
    String[]Topic_details = new String[]{"We are informing you that 30th March is School Annual function.Kindly present on time.","We are informing you that 30th March is School Annual function.Kindly present on time.","We are informing you that 30th March is School Annual function.Kindly present on time.","We are informing you that 30th March is School Annual function.Kindly present on time.","We are informing you that 30th March is School Annual function.Kindly present on time.","We are informing you that 30th March is School Annual function.Kindly present on time."};

    String strdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_dairy, container, false);

        SharedPreferences prefuserdata = getActivity().getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");


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

        //setUpRecycler();

        strdate = "0";

        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {


            new TopTrend().execute();


        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        select_date=(TextView)rootView.findViewById(R.id.select_date);

       /* select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });
*/
        select_date.setOnClickListener(this);
        select_date.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();

        select_date.addTextChangedListener(generalTextWatcher);

        return rootView;

    }

    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

            strdate = select_date.getText().toString();

          //  Toast.makeText(getActivity(), "strdate==="+strdate, Toast.LENGTH_SHORT).show();



            ConnectionDetector cd = new ConnectionDetector(getActivity());
            if (cd.isConnectingToInternet()) {


                new TopTrend().execute();


            } else {


                Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }

    };

    private void setDateTimeField() {


        select_date.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate2.set(year, monthOfYear, dayOfMonth);
                select_date.setText(dateFormatter.format(newDate2.getTime()));


            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }



    private void setUpRecycler(){

        mListFeederInfo =new ArrayList<History_diary_feederInfo>();
        for(int i=0;i<Topic.length;i++){
            History_diary_feederInfo feederInfo = new History_diary_feederInfo();
            feederInfo.set_date(Topic[i]);
            feederInfo.set_topic_details(Topic_details[i]);


            mListFeederInfo.add(feederInfo);
        }
        mAdapterFeeds= new History_diary_Adapter(getActivity(),mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);


    }

    @Override
    public void onClick(View v) {
        if(v == select_date) {


            toDatePickerDialog.show();
            //  showTruitonTimePickerDialog(v);
            // showTruitonDatePickerDialog(v);
        }
    }

    class TopTrend extends AsyncTask<String, String, String>

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

            Log.e("testing", "staffid = " + staffid);
            Log.e("testing", "type = " + type);
            Log.e("testing", "schoolid = " + schoolid);
            Log.e("testing", "classid = " + classid);
            Log.e("testing", "sectionid = " + sectionid);
            Log.e("testing", "strdate = " + strdate);

            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_LOGINID, staffid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_CLASSID, classid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_SECTIONID, sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_HISTORY_DATE, strdate));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.DAIRY_HISTORY, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            allItems1.clear();
            arraylist1.clear();

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("data");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);


               allItems1.clear();
               arraylist1.clear();

                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    History_diary_feederInfo item = new History_diary_feederInfo();

                    //item.setDairyid(post.optString("diary_id"));
                    item.set_topic_details(post.optString("dairyDescription"));
                    //item.set_date(post.optString("dairyDate"));
                    // item.set_id(post.optString("product_id"));

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


            if (status.equals("Success")){

                mAdapterFeeds = new History_diary_Adapter(getActivity(), allItems1);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);

                mFeedRecyler.setAdapter(mAdapterFeeds);


            }else if (status.equals("Error")){

                allItems1.clear();
                arraylist1.clear();

                mAdapterFeeds = new History_diary_Adapter(getActivity(), allItems1);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);

                mFeedRecyler.setAdapter(mAdapterFeeds);


                 Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }
        }
    }

}
