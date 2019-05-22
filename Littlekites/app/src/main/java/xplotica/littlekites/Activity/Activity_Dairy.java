package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_Dairy extends AppCompatActivity {


    JSONParser jsonParser = new JSONParser();
    ImageView back;

    EditText Topic,Description;
    Button Submit;

    String topic,description,schoolid,staffid,type,classid,sectionid;

    String Result,Message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dairy);

        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");

        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              /*  Intent intent = new Intent(getApplicationContext(), Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

       // Topic=(EditText)findViewById(R.id.topic);
        Description=(EditText)findViewById(R.id.description);
        Submit=(Button)findViewById(R.id.submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }


    public void Submit() {
        if (!validate()) {
            onUpdateFailed();
            return;
        }
        Submit.setEnabled(true);
        description = Description.getText().toString().trim();
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet()) {

            new LoadSpinnerabsent_entry().execute();


        } else {

            Toast.makeText(getApplicationContext(), "Internet Connection Not Available", Toast.LENGTH_LONG).show();
        }

       // savingData();

        Log.e("testij", "jjjkkll");
      //  Toast.makeText(getApplicationContext(), "Submit Button is clicking", Toast.LENGTH_SHORT).show();


    }


    public boolean validate(){
        boolean valid =true;


       // topic =Topic.getText().toString();
        description =Description.getText().toString();

       /* if (topic.isEmpty() ) {
            Topic.setError("Please enter Topic");
            valid = false;
        } else {
            Topic.setError(null);
        }



      *//*  if (email.isEmpty()  ||!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("enter a valid Email Address");
            valid = false;
        } else {
            Email.setError(null);
        }*//*
*/

        if (description.isEmpty()) {
            Description.setError("Please enter the description");
            valid = false;
        } else {
            Description.setError(null);
        }

        return valid;

    }

    private void onUpdateFailed(){
        Submit.setEnabled(true);
        Toast.makeText(getApplicationContext(), "Dairy Entry Failed", Toast.LENGTH_LONG).show();
    }


    private void savingData() {


       // topic = Topic.getText().toString().trim();
        description = Description.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.DAIRY_ENTRY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {


                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");
                           // Message = jsonArray1.getString("response");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);

                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("Success")) {

                            Toast.makeText(getApplicationContext(), Result, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Result);

                            Intent intent = new Intent(Activity_Dairy.this, Activity_home.class);
                            startActivity(intent);

                        } else {

                            Log.e("testing", "json response == " + response);
                            Toast.makeText(Activity_Dairy.this, Result, Toast.LENGTH_LONG).show();

                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing", "error response == " + error);
                        Toast.makeText(Activity_Dairy.this, "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();


                map.put(End_Urls.DAIRY_SCHOOLID, schoolid);
                map.put(End_Urls.DAIRY_LOGINID, staffid);
                map.put(End_Urls.DAIRY_CLASSID, classid);
                map.put(End_Urls.DAIRY_SECTIONID, sectionid);
                map.put(End_Urls.DAIRY_LOGINTYPE, type);
                map.put(End_Urls.DAIRY_DESCRIPTION, description);


                Log.e("t","map"+map);
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
            pDialog = new ProgressDialog(Activity_Dairy.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_LOGINID, staffid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_CLASSID, classid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_SECTIONID, sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.DAIRY_DESCRIPTION, description));

            Log.e("testing", "userpramas value=" + userpramas);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.DAIRY_ENTRY, "POST", userpramas);


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

                Intent intent = new Intent(Activity_Dairy.this, Activity_home.class);
                startActivity(intent);

            } else {

                Toast.makeText(Activity_Dairy.this, Result, Toast.LENGTH_LONG).show();

            }

        }

    }


}
