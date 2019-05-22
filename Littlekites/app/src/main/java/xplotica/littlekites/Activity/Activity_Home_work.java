package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xplotica.littlekites.Adapter.spinnerAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.Json_Data1;
import xplotica.littlekites.FeederInfo.Json_Data2;
import xplotica.littlekites.FeederInfo.Json_Data3;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_Home_work extends AppCompatActivity {

    ArrayList<String> spinner_array1;
    ArrayList<String> spinner_array2;
    ArrayList<String> spinner_array3;

    ArrayList<Json_Data1> spinner_json1;
    ArrayList<Json_Data2> spinner_json2;
    ArrayList<Json_Data3> spinner_json3;

    JSONObject jsonobject;
    JSONArray jsonarray;

    String spinnerid_1,spinnerid_2,spinnerid_3,spinner_class,spinner_section,spinner_subject;


    JSONParser jsonParser = new JSONParser();

    private Spinner spinner1,spinner2,spinner3;

    EditText Homework;
    Button Submit;

    ImageView back;

    String homework;

    String Result;

    String schoolid,type,loginid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);


        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        loginid = prefuserdata.getString("staffid", "");
        type = prefuserdata.getString("type", "");

        Log.e("testing","scoolid = "+schoolid);
        Log.e("testing","loginid = "+loginid);
        Log.e("testing","type = "+type);

        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(),Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);
        spinner3=(Spinner)findViewById(R.id.spinner3);

      /*  addItemsOnSpinner1();
        addItemsOnSpinner2();
        addItemsOnSpinner3();
*/
        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {

            new userdata().execute();
        //    new Select_section().execute();
            //new Select_subject().execute();

        } else {
            Toast.makeText(this, "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }


        Homework=(EditText)findViewById(R.id.homework);
        Submit=(Button)findViewById(R.id.submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String str_class = spinner1.getSelectedItem().toString();
                String str_section = spinner2.getSelectedItem().toString();
                String str_subject = spinner3.getSelectedItem().toString();

                String str_homework = Homework.getText().toString();


                Log.e("testing", "strclass" + str_class);
                Log.e("testing", "str_class" + str_section);
                Log.e("testing", "str_subject" + str_subject);
                Log.e("testing", "strquestion====================" + str_homework);

                if (str_class.equals("Select Class")) {

                    Toast.makeText(getApplicationContext(), "Please select Class", Toast.LENGTH_SHORT).show();

                } else if (str_section.equals("Select Section")) {

                    Toast.makeText(getApplicationContext(), "Please select Section", Toast.LENGTH_SHORT).show();

                }else if (str_subject.equals("Select Subject")) {

                    Toast.makeText(getApplicationContext(), "Please select Subject", Toast.LENGTH_SHORT).show();

                }

                else if (str_homework.equals("") || str_homework.equals("null") || str_homework.equals(null)) {

                    Toast.makeText(getApplicationContext(), "Please Enter Home work", Toast.LENGTH_SHORT).show();

                } else {


                    homework = Homework.getText().toString();

                    ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
                    if (cd.isConnectingToInternet()) {

                        // savingData();

                        new LoadSpinnerabsent_entry().execute();

                       // absent_entry();

                    } else {

                        Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
                    }

                }
            }


        });


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
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_CLASS_LOGINID,loginid ));
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



            spinnerAdapter adapter = new spinnerAdapter(getApplicationContext(), R.layout.spinner_item);
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
                       // new Select_subject().execute();
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
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SECTION_LOGINID, loginid));


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



            spinnerAdapter adapter = new spinnerAdapter(getApplicationContext(), R.layout.spinner_item);
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



                    } else{
                        spinnerid_2 = spinner_json2.get(position).getId();
                        spinner_section = spinner_json2.get(position).getName();


                        Log.e("testing","spinnerid2========"+spinnerid_2);
                       new Select_subject().execute();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }

    }


    public class Select_subject extends AsyncTask<String, String, String> {


        String responce;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        public String doInBackground(String... args) {

            spinner_json3= new ArrayList<Json_Data3>();
            // Create an array to populate the spinner
            spinner_array3 = new ArrayList<String>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing","userparams");
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SUBJECT_LOGINTYPE,type));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SUBJECT_SCHOOLID,schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.SPINNER_SUBJECT_LOGINID,loginid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.SPINNER_SUBJECT, "POST", userpramas);

            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    // status = json.getString("status");
                    String arrayresponce = json.getString("subject");
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




                            Json_Data3 jsondata = new Json_Data3();

                            jsondata.setId(post.optString("subjectId"));
                            jsondata.setName(post.optString("subjectName"));

                            spinner_json3.add(jsondata);

                            // Populate spinner with country names
                            spinner_array3.add(post.optString("subjectName"));
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

            spinnerAdapter adapter = new spinnerAdapter(getApplicationContext(), R.layout.spinner_item);
            adapter.addAll(spinner_array3);
            adapter.add("Select Subject");
            spinner3.setAdapter(adapter);
            spinner3.setSelection(adapter.getCount());


            // Spinner on item click listener
            spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> arg0,
                                           View arg1, int position, long arg3) {
                    // TODO Auto-generated method stub
                    // Locate the textviews in activity_main.xml

                    if(spinner3.getSelectedItem() == "Select Subject")
                    {

                    }
                    else{


                        spinnerid_3 = spinner_json3.get(position).getId();
                        spinner_subject = spinner_json3.get(position).getName();

                        Log.e("testing","spinnerid3========"+spinnerid_3);

                        //new Select_subject().execute();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub
                }
            });

        }

    }

    // add items into spinner1 dynamically
    public void addItemsOnSpinner1() {

        spinner1 = (Spinner) findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Class I");
        list.add("Class II");
        list.add("Class III");
        list.add("Class IV");
        list.add("Class V");
        list.add("Class VI");
        list.add("Class VII");
        list.add("Class VIII");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    // add items into spinner2 dynamically
    public void addItemsOnSpinner2() {

        spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> list = new ArrayList<String>();

        list.add("Section A");
        list.add("Section B");
        list.add("Section C");
        list.add("Section D");
        list.add("Section E");
        list.add("Section F");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);
    }

    // add items into spinner2 dynamically
    public void addItemsOnSpinner3() {

        spinner3 = (Spinner) findViewById(R.id.spinner3);
        List<String> list = new ArrayList<String>();

        list.add("Math");
        list.add("English");
        list.add("Science");
        list.add("General Knowledge");
        list.add("History");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner3.setAdapter(dataAdapter);
    }


   public class TopTrend extends AsyncTask<String, String, String>

    {
        String responce;
        String message;
        String status;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Home_work.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
          //  arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();




            Log.e("testing", End_Urls.HOMEWORK_ENTRY_LOGINTYPE+" = HE Logintype = "+type);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_SECTIONID+" = HE section = "+spinnerid_2);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_CLASSID+" = HE class = "+spinnerid_1);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_SCHOOLID+" = HE schoolid = "+schoolid);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_LOGINID+" = HE loginid = "+loginid);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_SUBJECTID+" = HE subject = "+spinnerid_3);
            Log.e("testing", End_Urls.HOMEWORK_ENTRY_HOMEWORK+" = HE homework = "+homework);

            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_SECTIONID, spinnerid_2));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_CLASSID, spinnerid_1));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_LOGINID, loginid));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_SUBJECTID, spinnerid_3));
            userpramas.add(new BasicNameValuePair(End_Urls.HOMEWORK_ENTRY_HOMEWORK, homework));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOMEWORK_ENTRY, "POST", userpramas);


            Log.e("testing", "json result in HE = " + json);

            Log.e("testing", "jon2222222222222");


            try {
                status = json.getString("status");


               /* String arrayresponce = json.getString("Sucess");
                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();

                    History_diary_feederInfo item = new History_diary_feederInfo();

                    //item.setDairyid(post.optString("diary_id"));
                    item.set_topic_details(post.optString("dairyDescription"));
                    //item.set_date(post.optString("dairyDate"));
                    // item.set_id(post.optString("product_id"));
                }
*/

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

                Toast.makeText(Activity_Home_work.this, "Success", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), Activity_home.class);

                startActivity(intent);

                Log.e("testing","homeworkentry====="+homework);


            }else if (status.equals("Error")){

                 Toast.makeText(Activity_Home_work.this, "no data found", Toast.LENGTH_LONG).show();
                // rView.setVisibility(View.GONE);
                //textsearch.setText("No Data Found");
            }

        }

    }


    private void absent_entry() {


        homework = Homework.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.HOMEWORK_ENTRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {


                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");
                          //  Message = jsonArray1.getString("response");

                            // USERROLE_ID = jsonArray1.getString("user_role");
                            // QUERY_ID = jsonArray1.getString("queryId");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);
                        Log.e("testing", "Message == " + Result);



                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("Success")) {

                            Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Result);

                            Intent intent = new Intent(Activity_Home_work.this, Activity_home.class);
                            startActivity(intent);
                            // openProfile();
                        } else {

                            Log.e("testing", "json response == " + response);
                            Toast.makeText(Activity_Home_work.this, Result, Toast.LENGTH_LONG).show();


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing", "error c == " + error);
                        Toast.makeText(Activity_Home_work.this, "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put("loginType", type);
                map.put("schoolId", schoolid);
                map.put("loginId", loginid);
                map.put("classId", spinnerid_1);
                map.put("sectionId",spinnerid_2);
                map.put("subjectId",spinnerid_3);
                map.put("homework", homework);

                Log.e("testing","map = "+map);
              /*  map.put(End_Urls.HOMEWORK_ENTRY_CLASSID, "1");
                map.put(End_Urls.HOMEWORK_ENTRY_LOGINID, "2");
                map.put(End_Urls.HOMEWORK_ENTRY_LOGINTYPE, "1");
                map.put(End_Urls.HOMEWORK_ENTRY_SCHOOLID, "1");
                map.put(End_Urls.HOMEWORK_ENTRY_SECTIONID, "1");
                map.put(End_Urls.HOMEWORK_ENTRY_SUBJECTID, "1");
                map.put(End_Urls.HOMEWORK_ENTRY_HOMEWORK, homework);*/


                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    class LoadSpinnerabsent_entry extends AsyncTask<String, String, String> {
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
            pDialog = new ProgressDialog(Activity_Home_work.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();




            userpramas.add(new BasicNameValuePair("loginType", type));
            userpramas.add(new BasicNameValuePair("schoolId", schoolid));
            userpramas.add(new BasicNameValuePair("loginId", loginid));
            userpramas.add(new BasicNameValuePair("classId", spinnerid_1));
            userpramas.add(new BasicNameValuePair("sectionId", spinnerid_2));
            userpramas.add(new BasicNameValuePair("subjectId", spinnerid_3));
            userpramas.add(new BasicNameValuePair("homework", homework));

            Log.e("testing", "userpramas value=" + userpramas);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOMEWORK_ENTRY, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");


                try {

                    Result = json.getString("status");





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

            if (Result.equals("Success")) {

                Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
                Log.e("testing", "Message111==" + Result);

                Intent intent = new Intent(Activity_Home_work.this, Activity_home.class);
                startActivity(intent);
                // openProfile();
            } else {

                Toast.makeText(Activity_Home_work.this, Result, Toast.LENGTH_LONG).show();


            }



        }

    }


}
