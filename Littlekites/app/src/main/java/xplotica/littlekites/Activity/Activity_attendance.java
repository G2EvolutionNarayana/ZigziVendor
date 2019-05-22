package xplotica.littlekites.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

import xplotica.littlekites.Activity_Details;
import xplotica.littlekites.Adapter.NumbersAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.Number1;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_attendance extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;


    ArrayList<Number1> numbers;
    private RecyclerView list;
    private Button btnGetSelected;

    Context context;
    String strarray, strrollnos;
    ImageView back;
    TextView textattendance;
    TextView Class,Section;
    String _class,_section,Id;
    String Result,Message, Classname, Sectionname;
    Dialog dialog1;
    String schoolid,staffid,type,classid,sectionid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");


        back=(ImageView)findViewById(R.id.back);
        Class=(TextView)findViewById(R.id.classname);
        Section=(TextView)findViewById(R.id.sectionname);

        context=this;
        dialog1 = new Dialog(context);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(),Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        btnGetSelected = (Button) findViewById(R.id.submit);
        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        numbers = new ArrayList<>();
       /* String ONEs[] = {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE", "TEN",
                "ELEVEN", "TWELVE", "THIRTEEN", "FOURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN", "NINETEEN", "TWENTY"};
        for (int i = 0; i <= 20; i++) {
            Number1 number = new Number1();
            number.setONEs(i + "");
            number.setTextONEs(ONEs[i]);
            this.numbers.add(number);
        }
        NumbersAdapter adapter = new NumbersAdapter(this.numbers);
        list.setAdapter(adapter);
*/


        ConnectionDetector cd = new ConnectionDetector(this);
        if (cd.isConnectingToInternet()) {

            new LoadSpinnerdata().execute();

        } else {


            Toast.makeText(this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Number1 number : numbers) {
                    if (number.isSelected()) {
                        if (stringBuilder.length() > 0)
                            stringBuilder.append(", ");
                        stringBuilder.append(number.getONEs());
                    }
                }

                StringBuilder stringBuilder2 = new StringBuilder();
                for (Number1 number : numbers) {
                    if (number.isSelected()) {
                        if (stringBuilder2.length() > 0)
                            stringBuilder2.append(", ");
                        stringBuilder2.append(number.getTextrolnno());
                    }
                }

                strarray = stringBuilder.toString();
                strrollnos = stringBuilder2.toString();



                        dialog1.setContentView(R.layout.customdialogattendance);
                        dialog1.setTitle("Absent List");

                        textattendance = (TextView) dialog1.findViewById(R.id.textattendance);


                        textattendance.setText(strrollnos);
                        // set the custom dialog components - text, image and button
                        //TextView text = (TextView) dialog.findViewById(R.id.text);
                        //text.setText("Android custom dialog example!");
                        //ImageView image = (ImageView) dialog.findViewById(R.id.image);
                        //image.setImageResource(R.drawable.user);

                        Button butsubmit = (Button) dialog1.findViewById(R.id.butsubmit);

                _class = Class.getText().toString().trim();
                _section = Section.getText().toString().trim();

                // if button is clicked, close the custom dialog
                         butsubmit.setOnClickListener(new View.OnClickListener() {

                            public void onClick(View v) {

                                ConnectionDetector cd = new ConnectionDetector(Activity_attendance.this);
                                if (cd.isConnectingToInternet()) {

                                    new LoadSpinnerabsent_entry().execute();

                                } else {


                                    Toast.makeText(Activity_attendance.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                                }


                            }
                        });


                        dialog1.show();


                //Toast.makeText(getApplicationContext(), stringBuilder2.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String responce;


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

            arraylist = new ArrayList<HashMap<String, String>>();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing","type = "+type);
            Log.e("testing","schoolid = "+schoolid);
            Log.e("testing","classid = "+classid);
            Log.e("testing","sectionid = "+sectionid);

            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_DETAILS_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_DETAILS_SCHOOLID,schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_DETAILS_CLASSID,classid));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_DETAILS_SECTIONID, sectionid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.ATTENDANCE_DETAILS, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    // status = json.getString("status");
                    Result = json.getString("status");
                    Classname = json.getString("className");
                    Sectionname = json.getString("sectionName");
                    String arrayresponce = json.getString("data");

                    Log.e("testing","classname=========="+Classname);
                    Log.e("testing","Section========"+Sectionname);
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

                            Number1 number = new Number1();
                            number.setONEs(post.optString("student_id"));
                            number.setTextONEs(post.optString("firstName"));
                            number.setTextrolnno(post.optString("rollNumber"));
                            numbers.add(number);

                          /*  FeedItem item = new FeedItem();

                            item.setId(post.optString("id"));
                            item.setPurpose(post.optString("name"));
                            item.setCity(post.optString("price"));

                            feedItemList.add(item);

                            arraylist.add(map);*/
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


            if (Result.equals("Success")){
                Class.setText(Classname);
                Section.setText(Sectionname);
                Log.e("testing", "Class======" + Class);
                String sect = Section.getText().toString();
                Log.e("testing", "section======" + Section);
                Log.e("testing", "result is = " + responce);
                NumbersAdapter adapter = new NumbersAdapter(numbers);
                list.setAdapter(adapter);

            }else{
                Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void absent_entry() {


        _class = Class.getText().toString().trim();
        _section = Section.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.ATTENDANCE_ENTRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {


                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");
                            Message = jsonArray1.getString("response");

                            // USERROLE_ID = jsonArray1.getString("user_role");
                            // QUERY_ID = jsonArray1.getString("queryId");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);
                        Log.e("testing", "Message == " + Result);

                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("Success")) {

                            Log.e("testing","test success");

                            Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Result);

                            Intent intent = new Intent(Activity_attendance.this, Activity_home.class);
                            startActivity(intent);
                            // openProfile();
                        } else {

                            Log.e("testing", "json response == " + response);
                            Toast.makeText(Activity_attendance.this, Result, Toast.LENGTH_LONG).show();


                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing", "error response == " + error);
                        Toast.makeText(Activity_attendance.this, "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                map.put(End_Urls.ATTENDANCE_ENTRY_LOGINID, staffid);
                map.put(End_Urls.ATTENDANCE_ENTRY_SCHOOLID, schoolid);
                map.put(End_Urls.ATTENDANCE_ENTRY_LOGINTYPE, type);
                map.put(End_Urls.ATTENDANCE_ENTRY_SECTIONID, sectionid);
                map.put(End_Urls.ATTENDANCE_ENTRY_CLASSID, classid);
                map.put("studentAbsentId[]", strarray);

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
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();






            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_ENTRY_LOGINID, staffid));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_ENTRY_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_ENTRY_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_ENTRY_SECTIONID, sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.ATTENDANCE_ENTRY_CLASSID, classid));
            userpramas.add(new BasicNameValuePair("studentAbsentId[]", strarray));

            Log.e("testing", "userpramas value=" + userpramas);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.ATTENDANCE_ENTRY, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");


                try {

                    Result = json.getString("status");
                    Message = json.getString("response");






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

                Log.e("testing","test success");

                Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
                Log.e("testing", "Message111==" + Result);

                Intent intent = new Intent(Activity_attendance.this, Activity_home.class);
                startActivity(intent);
                // openProfile();
            } else {

                Toast.makeText(Activity_attendance.this, Result, Toast.LENGTH_LONG).show();


            }



        }

    }



}
