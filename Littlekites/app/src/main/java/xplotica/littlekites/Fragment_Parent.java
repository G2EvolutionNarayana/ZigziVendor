package xplotica.littlekites;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xplotica.littlekites.Activity_parent.Activity_parent_Otp;
import xplotica.littlekites.app.Config;
import xplotica.littlekites.utils.NotificationUtils;

/**
 * Created by santa on 3/30/2017.
 */
public class Fragment_Parent extends Fragment {

    EditText Mobileno;

    Button Submit;

    String mobileno;

    String Result;

    String School_id,Student_id,School_name,ClassId,SectionId,mobile, Student_name, ClassName, SectionName, FatherName, MotherName, Rollno, ClassTeacheId, ClassTeacherName;

    String logo,filepath;

    String regId;
    private ProgressDialog pDialog;
    // server key for php----AAAADve3Ut8:APA91bHIyK3qu7TtY5H06f2xHWfOo6xhTmhSvfPmLNdK2fyAoqgs0sEqob-LD0GEgDaIFGTby51t-VChysGcq04sr0MhTDRD49HQo6HJL_TACpoAC7yedY1FAMq_PXTpyas3spKi5QJA---

    // ----FCM server key on janardhan.deucetech@gmail.com----
    //------------------------FCM Notification--------------------------
    //private static final String TAG = getAcgetSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);


        Mobileno =(EditText)rootView.findViewById(R.id.mobileno);

        Submit =(Button)rootView.findViewById(R.id.login);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               /* Intent intent = new Intent(getActivity(), Activity_parent_Otp.class);
                startActivity(intent);*/

              login();
            }
        });

        //------------------------FCM Notification--------------------------
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getActivity(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
//------------------------FCM Notification--------------------------



        return rootView;
    }



    //------------------------FCM Notification--------------------------------------------------------
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        // Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e("testing","Firebase Reg Id: " + regId);
            // txtRegId.setText("Firebase Reg Id: " + regId);
        else
            Log.e("testing","Firebase Reg Id is not received yet!");
        //  txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    public void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getActivity());
    }

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    //------------------------FCM Notification------------------------------------------------------



    private void login() {
        if (!validate()) {
            onUpdateFailed();
            return;
        }

        Submit.setEnabled(true);
        registerUser();

    }

    public boolean validate() {
        boolean valid = true;
        mobileno = Mobileno.getText().toString();

        if (mobileno.isEmpty() || !Patterns.PHONE.matcher(mobileno).matches() || mobileno.length() != 10) {
            Mobileno.setError("Enter a Valid  Mobile Number");
            valid = false;
        } else {
            Mobileno.setError(null);
        }


        return valid;

    }

    private void onUpdateFailed() {
        Submit.setEnabled(true);
        Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_LONG).show();
    }


    private void registerUser() {

        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading Services");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

        mobileno = Mobileno.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.PARENT_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {


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

                            //Student_id=jsonArray1.getString("student_id");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);
                        Log.e("testing", "School id ==== " + School_id);
                        Log.e("testing", "mobile no ===== " + mobile);
                        Log.e("testing", "Section id ===== " + SectionId);
                        Log.e("testing", "Class id ===== " + ClassId);
                        Log.e("testing", "SchoolName ===== " + School_name);

                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("success")) {
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), Result, Toast.LENGTH_LONG).show();

                            Log.e("testing", "Result111==" + Result);

                            SharedPreferences prefuserdata = getActivity().getSharedPreferences("registerUser", 0);
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

                            Intent intent = new Intent(getActivity(), Activity_parent_Otp.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);

                        } else {
                            pDialog.dismiss();
                            Log.e("testing", "json response == " + response);
                            Toast.makeText(getActivity(), "Mobile number mismatch", Toast.LENGTH_LONG).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.e("testing", "error response == " + error);
                        Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put(End_Urls.PARENT_MOBILENO, mobileno);
                map.put(End_Urls.PARENT_TYPE, "2");
                map.put(End_Urls.PARENT_FcmToken, regId);

                Log.e("testing","mobileno"+mobileno);

                return map;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
