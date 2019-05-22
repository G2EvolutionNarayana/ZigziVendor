package xplotica.littlekites.Activity_parent;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class Activity_parent_Otp extends AppCompatActivity {



    TextView cnfcode;

    EditText EnterOTP;
    String otpno;
    private ProgressDialog pDialog;
    String Result,Message;
    String loginId,Schoolid,TYPE;
    String School_id,Student_id,School_name,ClassId,SectionId,mobile, Student_name, ClassName, SectionName, FatherName, MotherName, Rollno, ClassTeacheId, ClassTeacherName;
    String logo,filepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_otp);

        SharedPreferences prefuserdata = getSharedPreferences("registerUser", 0);
        loginId = prefuserdata.getString("mobile","0");
        Schoolid = prefuserdata.getString("schoolid","0");
        TYPE = prefuserdata.getString("type","0");


        EnterOTP=(EditText)findViewById(R.id.enterotp);

        cnfcode =(TextView)findViewById(R.id.code);

        cnfcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Activity_parent_Student_details.class);
                startActivity(intent);
               // update();
            }
        });

    }

    public void update() {
        if (!validate()) {
            onUpdateFailed();
            return;
        }
        cnfcode.setEnabled(true);

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

        pDialog = new ProgressDialog(Activity_parent_Otp.this);
        pDialog.setMessage("Loading Services");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        otpno= EnterOTP.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.PARENT_OTP,
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
                            School_id=jsonArray1.getString("school_id");
                            mobile=jsonArray1.getString("mobile");
                            School_name=jsonArray1.getString("schoolName");
                            Student_name=jsonArray1.getString("StudentName");
                            Student_id=jsonArray1.getString("student_id");
                            ClassId=jsonArray1.getString("class_id");
                            ClassName=jsonArray1.getString("class_name");
                            SectionId=jsonArray1.getString("section_id");
                            SectionName=jsonArray1.getString("section_name");
                            FatherName=jsonArray1.getString("fatherName");
                            MotherName=jsonArray1.getString("motherName");
                            Rollno=jsonArray1.getString("rollNumber");
                            filepath=jsonArray1.getString("filepath");
                            ClassTeacheId=jsonArray1.getString("classTeacherId");
                            ClassTeacherName=jsonArray1.getString("classTeacherName");
                            logo=jsonArray1.getString("logo");


                            Log.e("testing","Result == "+Result);
                            Log.e("testing","Message == "+Message);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if (Result.equals("success")) {
                            pDialog.dismiss();
                            Toast.makeText(Activity_parent_Otp.this, Result, Toast.LENGTH_LONG).show();

                            Log.e("testing", "Result111==" + Result);

                            SharedPreferences prefuserdata = Activity_parent_Otp.this.getSharedPreferences("registerUser", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();

                            prefeditor.putString("schoolid", "" + School_id);
                            prefeditor.putString("studentid", "" + Student_id);
                            prefeditor.putString("schoolname", "" + School_name);
                            prefeditor.putString("classid", "" + ClassId);
                            prefeditor.putString("sectionid", "" + SectionId);
                            prefeditor.putString("mobile", "" + mobile);
                            prefeditor.putString("logo", "" + logo);
                            prefeditor.putString("filepath", "" + filepath);
                            prefeditor.putString("ClassTeacheId", "" + ClassTeacheId);
                            prefeditor.putString("ClassTeacherName", "" + ClassTeacherName);
                            prefeditor.putString("type", "" + "2");


                            prefeditor.commit();

                            Log.e("testing","mobileno========"+mobile);
                            Log.e("testing","ClassTeacheId========"+ClassTeacheId);
                            Log.e("testing","ClassTeacherName========"+ClassTeacherName);

                            Intent intent = new Intent(Activity_parent_Otp.this, Activity_parent_Student_details.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                        else
                        {
                            pDialog.dismiss();
                            Toast.makeText(Activity_parent_Otp.this,Message, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message222==" + Message);

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(Activity_parent_Otp.this,Message, Toast.LENGTH_LONG ).show();
                        Log.e("testing","error response == " +
                                ""+error);
                        Intent intent = new Intent(Activity_parent_Otp.this,Activity_parent_Otp.class);

                        startActivity(intent);

                    }
                })

        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

                params.put(End_Urls.PARENT_OTPNUMBER,otpno);
                params.put(End_Urls.PARENT_LoginID,loginId);
                params.put(End_Urls.PARENT_LoginType,TYPE);

                Log.e("testing", "params11==" + params);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_parent_Otp.this);
        requestQueue.add(stringRequest);
    }
}
