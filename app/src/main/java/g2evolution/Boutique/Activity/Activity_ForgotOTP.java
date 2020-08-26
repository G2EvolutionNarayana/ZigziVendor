package g2evolution.Boutique.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Login_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_ForgotOTP extends AppCompatActivity implements View.OnClickListener{


    Button butverify;

    EditText otp_edit;


    String strotp;
    Context context;

    String strreguser_id;

    JSONParser jsonParser = new JSONParser();

    EditText new_password, confirm_password;
    Button butpasswordsubmit;
    String strnewpassword, strconfirmpassword;

    String strforgot_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp);
        context = this;

        butverify = (Button) findViewById(R.id.butverify);
        otp_edit = (EditText) findViewById(R.id.otp_edit);
        butverify.setOnClickListener(this);

        SharedPreferences prefuserdata2 = getSharedPreferences("strforgot_user_id", 0);
        strforgot_user_id = prefuserdata2.getString("strforgot_user_id", "");
        Log.e("testing","strforgot_user_id = "+strforgot_user_id);



    }
    private void setpassword() {

        final Dialog dialogpassword = new Dialog(Activity_ForgotOTP.this);
        dialogpassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) Activity_ForgotOTP.this.getSystemService(Activity_ForgotOTP.this.LAYOUT_INFLATER_SERVICE);
        View convertView = (View) inflater.inflate(R.layout.dialog_password, null);
        //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

        dialogpassword.setContentView(convertView);

        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        dialogpassword.setCanceledOnTouchOutside(false);
        dialogpassword.setCancelable(false);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogpassword.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialogpassword.getWindow().setAttributes(lp);
        dialogpassword.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        new_password = (EditText) convertView.findViewById(R.id.new_password);
        confirm_password = (EditText) convertView.findViewById(R.id.confirm_password);
        butpasswordsubmit = (Button) convertView.findViewById(R.id.butpasswordsubmit);
        ImageView imgcancel = (ImageView) convertView.findViewById(R.id.imgcancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpassword.dismiss();
            }
        });
        butpasswordsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitpassword();
            }
        });

        dialogpassword.show();
    }

    private void submitpassword() {

        strnewpassword = new_password.getText().toString();
        strconfirmpassword = confirm_password.getText().toString();


        if (strnewpassword == null || strnewpassword.trim().length() == 0 || strnewpassword.trim().length() < 6 || strnewpassword.trim().length() > 13){
            new_password.setFocusableInTouchMode(true);
            new_password.requestFocus();
            Toast.makeText(context, "Please enter Password length 6 to 13 characters", Toast.LENGTH_SHORT).show();

        }else if (strconfirmpassword == null || strconfirmpassword.trim().length() == 0 || strconfirmpassword.trim().length() < 6 || strconfirmpassword.trim().length() > 13){
            confirm_password.setFocusableInTouchMode(true);
            confirm_password.requestFocus();
            Toast.makeText(context, "Please enter Confirm Password length 6 to 13 characters", Toast.LENGTH_SHORT).show();

        }else {

            if (strnewpassword.equals(strconfirmpassword)){
                ConnectionDetector cd = new ConnectionDetector(context);
                if (cd.isConnectingToInternet()) {




                    new PasswordSet().execute();

                } else {
                    Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                }


            }else{
                Toast.makeText(context, "Password Not Match", Toast.LENGTH_SHORT).show();
            }

        }

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.butverify:


                strotp = otp_edit.getText().toString();


                if (strotp == null || strotp.trim().length() == 0){
                    otp_edit.setFocusableInTouchMode(true);
                    otp_edit.requestFocus();
                    Toast.makeText(context, "Please enter OTP", Toast.LENGTH_SHORT).show();

                }else {

                    ConnectionDetector cd = new ConnectionDetector(context);
                    if (cd.isConnectingToInternet()) {




                        new OTPLoad().execute();

                    } else {
                        Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                    }


                }





                break;

            default:
                break;
        }
    }


    class OTPLoad extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(context);
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
            userpramas.add(new BasicNameValuePair(EndUrl.ValidateForgotPasswordOTP_URL_user_id, strforgot_user_id));
            userpramas.add(new BasicNameValuePair(EndUrl.ValidateForgotPasswordOTP_URL_forgot_exp, strotp));
            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.ValidateForgotPasswordOTP_URL;
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
                        strreguser_id = jsonobjectdata.getString("user_id");



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

                if (strresponse == null || strresponse.length() == 0){

                }else if (strtype.equals("verify_success")){
                    Toast.makeText(context, strmessage, Toast.LENGTH_SHORT).show();

                    setpassword();


                }else{

                }



            } else if (status.equals("failure")) {
                Toast.makeText(context, strmessage, Toast.LENGTH_SHORT).show();
                // alertdialog(strtype, strmessage);
            }else{

            }


        }

    }

    class PasswordSet extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(context);
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
            userpramas.add(new BasicNameValuePair(EndUrl.SignupUpdatePassword_URL_user_id, strreguser_id));
            userpramas.add(new BasicNameValuePair(EndUrl.SignupUpdatePassword_URL_password, strnewpassword));
            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.SignupUpdatePassword_URL;
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

                }else if (strtype.equals("update_success")){
                    //  Toast.makeText(context, strmessage, Toast.LENGTH_SHORT).show();


                    alertdialog(strtype, strmessage);

                }else{

                }



            } else if (status.equals("failure")) {
                Toast.makeText(context, strmessage, Toast.LENGTH_SHORT).show();

            }else{

            }


        }

    }
    private void alertdialog(String strresponse, String strmessage) {

        final AlertDialog alertDialog = new AlertDialog.Builder(
                context).create();

        // Setting Dialog Title
        alertDialog.setTitle("Verification Success");
        alertDialog.setCancelable(false);
        // Setting Dialog Message
        alertDialog.setMessage(strmessage);

        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogTheme; //style id
        // Setting OK Button
        alertDialog.setButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog,
                                        int which) {

                        alertDialog.dismiss();

                        Intent intent = new Intent(context, Login_Activity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        // Write your code here to execute after dialog
                        // closed
                       /* Toast.makeText(getApplicationContext(),
                                "You clicked on OK", Toast.LENGTH_SHORT)
                                .show();*/
                    }
                });


        alertDialog.show();
    }
}
