package g2evolution.Boutique;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class OTP_Activity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.firstPinView)
    PinView firstPinView;
    @BindView(R.id.submit_text)
    TextView submit_text;
    @BindView(R.id.resend_text)
    TextView resend_text;

    String strsignup_user_id;
    String _otp;


    String str_user_id;

    JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boutotp);

        ButterKnife.bind(this);

        SharedPreferences prefuserdata = getSharedPreferences("strsignup_user_id", 0);
        strsignup_user_id = prefuserdata.getString("strsignup_user_id","0");

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");


        ((TextView) findViewById(R.id.submit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.resend_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.firstPinView)).setTypeface(typeface);



        listeners();


    }
    private void listeners() {

        submit_text.setOnClickListener(this);
        resend_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_text:

                submit();

                break;


            case R.id.resend_text:
                new ResendOTPLoad().execute();
                break;




            default:


        }

    }
    private void submit() {

        Log.e("testing","clicking");
        if (!Validateotp()) {
            return;
        }


        else {

            _otp = firstPinView.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(OTP_Activity.this);
            if (cd.isConnectingToInternet()) {


                //  new TopTrend().execute();

                new LoadSignupOTP().execute();

            } else {


                Toast.makeText(OTP_Activity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


        }

    }

    private boolean Validateotp() {

        String phone = firstPinView.getText().toString();
        if (phone == null || phone.trim().length() == 0) {

            Toast.makeText(OTP_Activity.this, "Enter Otp", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            firstPinView.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }

    class LoadSignupOTP extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearray;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(OTP_Activity.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);



        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.SignupOTP_register_exp, _otp));
            userpramas.add(new BasicNameValuePair(EndUrl.SignupOTP_temp_user_id, strsignup_user_id));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.SignupOTP_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {

                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");

                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        str_user_id = jsonobjectdata.getString("user_id");
                        Log.e("testing","userid - "+str_user_id);



                    } else {
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
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                Toast.makeText(OTP_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(OTP_Activity.this, Home_Activity.class);
                SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("UserId", "" + str_user_id);


                prefeditor.commit();



                startActivity(intent);

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(OTP_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }

    class ResendOTPLoad extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearray;
        String status;
        String strresponse;
        String strmode;
        String Message;
        String struserid;
        String strdata;
        String strvalid;
        JSONObject responceobject;
        String strresult1, strresult2, strresult3;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        ProgressDialog mProgress;

        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(OTP_Activity.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            //  userpramas.add(new BasicNameValuePair(EndUrls.Signup_registering_by, "user"));
            userpramas.add(new BasicNameValuePair(EndUrl.ResendSignupOTP_URL_temp_user_id, strsignup_user_id));
            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.ResendSignupOTP_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {

                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                if (strresponse == null || strresponse.length() == 0){

                }else if (strtype.equals("resend_success")){
                    Toast.makeText(OTP_Activity.this, strmessage, Toast.LENGTH_SHORT).show();



                }else{

                }



            } else if (status.equals("failure")) {
                Toast.makeText(OTP_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
            }else{

            }


        }

    }


}
