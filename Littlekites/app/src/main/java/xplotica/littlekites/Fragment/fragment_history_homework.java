package xplotica.littlekites.Fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
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

import xplotica.littlekites.Adapter.History_homework_Adapter;
import xplotica.littlekites.Adapter.spinnerAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.History_homework_feederInfo;
import xplotica.littlekites.FeederInfo.Json_Data1;
import xplotica.littlekites.FeederInfo.Json_Data2;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by Sujata on 30-03-2017.
 */
public class fragment_history_homework extends Fragment implements View.OnClickListener {

    ArrayList<String> spinner_array1;
    ArrayList<String> spinner_array2;

    ArrayList<Json_Data1> spinner_json1;
    ArrayList<Json_Data2> spinner_json2;

    JSONObject jsonobject;
    JSONArray jsonarray;

    String spinnerid_1,spinnerid_2,spinner_class,spinner_section;

    private Spinner spinner1,spinner2;

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<History_homework_feederInfo> allItems1 = new ArrayList<History_homework_feederInfo>();

    private RecyclerView mFeedRecyler;
    private ArrayList<History_homework_feederInfo> mListFeederInfo;
    private History_homework_feederInfo adapter;
    History_homework_Adapter mAdapterFeeds;
    RecyclerView rView;
    Context context;

    TextView select_date;

    private DatePickerDialog toDatePickerDialog;
    public Calendar newDate2 = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;

    String strdate;


    String[]Topic = new String[]{"Mathematics","Mathematics","Mathematics","Mathematics","Mathematics","Mathematics"};
    String[]Topic_details = new String[]{"Need to complete chapter 1 to 3","Need to complete chapter 1 to 3","Need to complete chapter 1 to 3","Need to complete chapter 1 to 3","Need to complete chapter 1 to 3","Need to complete chapter 1 to 3"};

    String schoolid,staffid,classid,sectionid,type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_homework, container, false);



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

     //   setUpRecycler();

        spinner1=(Spinner)rootView.findViewById(R.id.spinner1);
        spinner2=(Spinner)rootView.findViewById(R.id.spinner2);

     /*   addItemsOnSpinner1();
        addItemsOnSpinner2();*/

        select_date=(TextView)rootView.findViewById(R.id.select_date);

        select_date.setOnClickListener(this);
        select_date.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();

        select_date.addTextChangedListener(generalTextWatcher);


        ConnectionDetector cd = new ConnectionDetector(getActivity());
        if (cd.isConnectingToInternet()) {

            // new TopTrend().execute();
            new userdata().execute();
           // new Select_section().execute();


        } else {


            Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

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

        mListFeederInfo =new ArrayList<History_homework_feederInfo>();
        for(int i=0;i<Topic.length;i++){
            History_homework_feederInfo feederInfo = new History_homework_feederInfo();
            feederInfo.set_topic(Topic[i]);
            feederInfo.set_topic_details(Topic_details[i]);


            mListFeederInfo.add(feederInfo);
        }
        mAdapterFeeds= new History_homework_Adapter(getActivity(),mListFeederInfo);
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


    public class userdata extends AsyncTask<String, String, String> {


        String responce;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }


        public String doInBackground(String... args) {

            spinner_json1= new ArrayList<Json_Data1>();
            // Create an array to populate the spinner
            spinner_array1 = new ArrayList<String>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing","userparams");

            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_CLASS_LOGINID,staffid ));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_CLASS_SCHOOLID,schoolid ));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_CLASS_LOGINTYPE,type ));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.SPINNER_CLASS, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    // status = json.getString("status");
                    String arrayresponce = json.getString("class");
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


                            Json_Data1 jsondata = new Json_Data1();

                            jsondata.setId(post.optString("classId"));
                            jsondata.setName(post.optString("className"));

                            spinner_json1.add(jsondata);

                            // Populate spinner with country names
                            spinner_array1.add(post.optString("className"));


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
            //   pDialog.dismiss();
            Log.e("testing", "result is = " + responce);


            spinnerAdapter adapter = new spinnerAdapter(getActivity(), R.layout.spinner_item);
            adapter.addAll(spinner_array1);
            adapter.add("Select Class");
            spinner1.setAdapter(adapter);
            spinner1.setSelection(adapter.getCount());


            // Spinner on item click listener
            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml

                    if(spinner1.getSelectedItem() == "Select Class") {

                    } else{
                        spinnerid_1 = spinner_json1.get(position).getId();
                        spinner_class = spinner_json1.get(position).getName();


                        Log.e("testing","spinnerid1========"+spinnerid_1);


                        new Select_section().execute();

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });


        }

    }

    public class Select_section extends AsyncTask<String, String, String> {


        String responce;




        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        public String doInBackground(String... args) {

            spinner_json2= new ArrayList<Json_Data2>();
            // Create an array to populate the spinner
            spinner_array2 = new ArrayList<String>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing","userparams");


            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SECTION_LOGINTYPE,type));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SECTION_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SECTION_CLASSID,spinnerid_1 ));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SECTION_LOGINID, staffid));


            JSONObject json = jsonParser.makeHttpRequest(End_Urls.SPINNER_SECTION, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    // status = json.getString("status");
                    String arrayresponce = json.getString("section");
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

                            Json_Data2 jsondata = new Json_Data2();

                            jsondata.setId(post.optString("sectionId"));
                            jsondata.setName(post.optString("sectionName"));

                            spinner_json2.add(jsondata);

                            // Populate spinner with country names
                            spinner_array2.add(post.optString("sectionName"));


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
            //   pDialog.dismiss();
            Log.e("testing", "result is = " + responce);

            spinnerAdapter adapter = new spinnerAdapter(getActivity(), R.layout.spinner_item);
            adapter.addAll(spinner_array2);
            adapter.add("Select Section");
            spinner2.setAdapter(adapter);
            spinner2.setSelection(adapter.getCount());


            // Spinner on item click listener
            spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml

                    if(spinner2.getSelectedItem() == "Select Section")
                    {

                    }
                    else{
                        spinnerid_2 = spinner_json2.get(position).getId();
                        spinner_section = spinner_json2.get(position).getName();

                        Log.e("testing","spinnerid2========"+spinnerid_2);

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }

    }

  public  class TopTrend extends AsyncTask<String, String, String>

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

            Log.e("testing", "staffid = " + staffid + ", type = " + type + ", schoolid = " + schoolid + ", CLASSID = "+spinnerid_1+ ", SECTIONID, = "+spinnerid_2+ ", HOMEWORKDATE = "+strdate);

            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_LOGINID, staffid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_CLASSID, spinnerid_1));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_SECTIONID, spinnerid_2));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_HISTROY_HOMEWORKDATE, strdate));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOMEWORK_HISTORY, "POST", userpramas);


            Log.e("testing", "json result json  = " + json);

            Log.e("testing", "jon2222222222222");

            try {
                status = json.getString("status");
                String arrayresponce = json.getString("homework");


                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);


                Log.e("testing", "responcearray value=" + responcearray);


                allItems1.clear();
                arraylist1.clear();


                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);

                    HashMap<String, String> map = new HashMap<String, String>();

                    History_homework_feederInfo item = new History_homework_feederInfo();


                    item.set_topic(post.optString("subjectName"));
                    item.set_topic_details(post.optString("homework"));

                    Log.e("testing","subjectname============="  +"subjectName");
                    Log.e("testing","topicdetails============="  +"homework");


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


            Log.e("testing", "result is  post ===== " + responce);

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/
            if (status.equals("Success")){



                mAdapterFeeds = new History_homework_Adapter(getActivity(), allItems1);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);

                mFeedRecyler.setAdapter(mAdapterFeeds);




            }else if (status.equals("Error")){

                allItems1.clear();
                arraylist1.clear();

                mAdapterFeeds = new History_homework_Adapter(getActivity(), allItems1);

                Log.e("testing", "testing data for recycler ==" + mAdapterFeeds);

                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");

            }

        }
    }

    public void addItemsOnSpinner1() {

        List<String> list = new ArrayList<String>();
        list.add("Class I");
        list.add("Class II");
        list.add("Class III");
        list.add("Class IV");
        list.add("Class V");
        list.add("Class VI");
        list.add("Class VII");
        list.add("Class VIII");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    // add items into spinner2 dynamically
    public void addItemsOnSpinner2() {

        List<String> list = new ArrayList<String>();

        list.add("Section A");
        list.add("Section B");
        list.add("Section C");
        list.add("Section D");
        list.add("Section E");
        list.add("Section F");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

}
