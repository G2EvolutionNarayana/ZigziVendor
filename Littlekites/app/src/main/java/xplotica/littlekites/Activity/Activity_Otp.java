package xplotica.littlekites.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;


public class    Activity_Otp extends AppCompatActivity {

    TextView cnfcode;

    EditText EnterOTP;
    String otpno;

    String Result,Message;
    String Staff_id,School_id,School_name,Class_id,Section_id,Class_name,Section_name, Teacher_id, Teacher_name;
    String loginId,Schoolid,Type, mobileno;
    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        SharedPreferences prefuserdata = getSharedPreferences("registerUser", 0);
        loginId = prefuserdata.getString("loginid","0");
        Schoolid = prefuserdata.getString("schoolid","0");
        Type = prefuserdata.getString("type","0");
        mobileno = prefuserdata.getString("mobileno","0");


        EnterOTP=(EditText)findViewById(R.id.enterotp);

        cnfcode =(TextView)findViewById(R.id.code);

        cnfcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 update();
            }
        });


    }


    public void update() {
        if (!validate()) {
            onUpdateFailed();
            return;
        }
        //cnfcode.setEnabled(true);

        registerUser();

        //Toast.makeText(Activity_parent_Otp.this, "Submit Button is clicking", Toast.LENGTH_SHORT).show();

    }
    public boolean validate(){
        boolean valid =true;


        otpno=EnterOTP.getText().toString();

        if (otpno.isEmpty()) {
            EnterOTP.setError("Enter A Valid OTP Number");
            valid = false;
        } else {
            EnterOTP.setError(null);
        }

        return valid;

    }

    private void onUpdateFailed(){
        cnfcode.setEnabled(true);

        Toast.makeText(this, "Verification Failed", Toast.LENGTH_LONG).show();
    }

    private void registerUser() {

        pDialog = new ProgressDialog(Activity_Otp.this);
        pDialog.setMessage("Loading Services");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        otpno= EnterOTP.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.TEACHER_OTP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response = " + response);

                        try {
                            //JSONArray jsonArray = new JSONArray(response);
                            //for(int i=0;i<jsonArray.length();i++){
                            //JSONObject jresponse = 	jsonArray.getJSONObject(i);
                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");

                            School_id=jsonArray1.getString("school_Id");
                            School_name=jsonArray1.getString("school_Name");
                            Class_id=jsonArray1.getString("class_Id");
                            Class_name=jsonArray1.getString("class_name");
                            Section_id=jsonArray1.getString("section_Id");
                            Section_name=jsonArray1.getString("section_name");
                            Staff_id=jsonArray1.getString("teacher_Id");
                            Teacher_name=jsonArray1.getString("teacher_name");




                            Log.e("testing","Result == "+Result);
                            Log.e("testing","Message == "+Message);
                            //Log.e("testing","RegisterName == "+JsonName);
                            //Log.e("testing","RegisterId == "+JsonId);



                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (Result.equals("Success")) {
                            pDialog.dismiss();
                            Toast.makeText(Activity_Otp.this, Result, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Result);

                            Log.e("testing","schoolid"+School_id);
                            Log.e("testing","Staff_id"+Staff_id);
                            Log.e("testing","Class_id"+Class_id);
                            SharedPreferences prefuserdata = Activity_Otp.this.getSharedPreferences("registerUser", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();

                            prefeditor.putString("schoolid", "" + School_id);
                            prefeditor.putString("schoolname", "" + School_name);
                            prefeditor.putString("staffid", "" + Staff_id);
                            prefeditor.putString("classid", "" + Class_id);
                            prefeditor.putString("sectionid", "" + Section_id);
                            prefeditor.putString("type", "" + "1");
                            prefeditor.putString("classname", "" + ""+ Class_name);
                            prefeditor.putString("sectionname", "" + ""+ Section_name);
                            prefeditor.putString("teachername", "" + ""+ Teacher_name);
                            prefeditor.putString("mobileno", "" + ""+ mobileno);


                            prefeditor.commit();

                            Intent intent = new Intent(Activity_Otp.this, Activity_home.class);

                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            pDialog.dismiss();
                            Toast.makeText(Activity_Otp.this,Message, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message222==" + Message);


                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pDialog.dismiss();

                        Toast.makeText(Activity_Otp.this,Message, Toast.LENGTH_LONG ).show();
                        Log.e("testing","error response == " +
                                ""+error);
                        Intent intent = new Intent(Activity_Otp.this,Activity_Otp.class);


                        startActivity(intent);



                    }
                })

        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(End_Urls.TEACHER_OTPNUMBER,otpno);
                params.put(End_Urls.TEACHER_loginID,mobileno);
                params.put(End_Urls.TEACHER_Type,Type);

                Log.e("testing", "params11==" + params);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Otp.this);
        requestQueue.add(stringRequest);
    }

}
