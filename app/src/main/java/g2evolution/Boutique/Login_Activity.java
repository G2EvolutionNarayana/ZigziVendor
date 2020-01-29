package g2evolution.Boutique;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import g2evolution.Boutique.Activity.Activity_ForgotOTP;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {



    JSONParser jsonParser = new JSONParser();

    @BindView(R.id.user_name_edit_text)
    EditText user_name_edit_text;
    @BindView(R.id.password_edit_text)
    EditText password_edit_text;
    @BindView(R.id.login_text)
    TextView login_text;
    @BindView(R.id.sign_in_text)
    TextView sign_in_text;
    @BindView(R.id.forgot_password_text)
    TextView forgot_password_text;


    EditText editforgotmobileno;
    String strforgotmobileno;
    String str_user_id;
    Button butforgot;
    String strforgot_user_id;

    String strmobileno, strpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boutlogin);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView) findViewById(R.id.title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sub_title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sign_in_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.login_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.forgot_password_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.user_name_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.password_edit_text)).setTypeface(typeface);

        listeners();
    }

    private void listeners() {

        sign_in_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
        forgot_password_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_in_text:

                Intent intent1=new Intent(Login_Activity.this,SignUp_ACtivity.class);
                startActivity(intent1);
                break;


            case R.id.login_text:

                submit();

                break;


            case R.id.forgot_password_text:

               /* Intent intent3=new Intent(Login_Activity.this,Forgot_Password_Activity.class);
                startActivity(intent3);*/

                forgetotp();

                break;

                default:


        }

    }
    private void submit() {

        strmobileno = user_name_edit_text.getText().toString();
        strpassword = password_edit_text.getText().toString();

        if (strmobileno.isEmpty()){

            Toast.makeText(Login_Activity.this, "Please Enter Mobileno", Toast.LENGTH_SHORT).show();
        }

        else {
            if (strpassword.isEmpty()){

                Toast.makeText(Login_Activity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }else{

            }

            //  strpassword = logpassword.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Login_Activity.this);
            if (cd.isConnectingToInternet()) {

                // new Loader().execute();
                new LoadPLogin().execute();

            } else {


                Toast.makeText(Login_Activity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


        }

    }

    private void forgetotp() {
        final Dialog dialogpassword = new Dialog(Login_Activity.this);
        dialogpassword.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) Login_Activity.this.getSystemService(Login_Activity.this.LAYOUT_INFLATER_SERVICE);
        View convertView = (View) inflater.inflate(R.layout.dialog_forgot, null);
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



        editforgotmobileno = (EditText) convertView.findViewById(R.id.editforgotmobileno);
        butforgot = (Button) convertView.findViewById(R.id.butforgot);
        ImageView imgcancel = (ImageView) convertView.findViewById(R.id.imgcancel);
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogpassword.dismiss();
            }
        });
        butforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitforgot();
            }
        });

        dialogpassword.show();


    }

    private void submitforgot() {

        strforgotmobileno = editforgotmobileno.getText().toString();


        if (strforgotmobileno == null || strforgotmobileno.trim().length() == 0){
            editforgotmobileno.setFocusableInTouchMode(true);
            editforgotmobileno.requestFocus();
            Toast.makeText(Login_Activity.this, "Please enter proper  Mobile number", Toast.LENGTH_SHORT).show();

        }else {

            ConnectionDetector cd = new ConnectionDetector(Login_Activity.this);
            if (cd.isConnectingToInternet()) {




                new ForgotPasswordLoad().execute();

            } else {
                Toast.makeText(Login_Activity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
            }

        }

    }


    class LoadPLogin extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Login_Activity.this);
            mProgress.setMessage("Fetching data...");
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



            userpramas.add(new BasicNameValuePair(EndUrl.Login_emailphone, strmobileno));
            userpramas.add(new BasicNameValuePair(EndUrl.Login_password, strpassword));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.Login_URL;
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

                Toast.makeText(Login_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login_Activity.this, Home_Activity.class);
                SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("UserId", "" + str_user_id);


                prefeditor.commit();



                startActivity(intent);
                finish();

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Login_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }
    class ForgotPasswordLoad extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Login_Activity.this);
            mProgress.setMessage("Fetching data...");
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



            userpramas.add(new BasicNameValuePair(EndUrl.ForgotPassword_emailphone, strforgotmobileno));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.ForgotPassword_URL;
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
                        strforgot_user_id = jsonobjectdata.getString("user_id");



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

                }else if (strtype.equals("send_success")){
                    Toast.makeText(Login_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login_Activity.this, Activity_ForgotOTP.class);
                    SharedPreferences prefuserdata = getSharedPreferences("strforgot_user_id", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("strforgot_user_id", "" + strforgot_user_id);


                    prefeditor.commit();



                    startActivity(intent);
                }else{

                }



            } else if (status.equals("failure")) {
                alertdialog(strtype, strmessage);
            }else{

            }


        }

    }


    private void alertdialog(String strresponse, String strmessage) {

        final AlertDialog alertDialog = new AlertDialog.Builder(
                Login_Activity.this).create();

        // Setting Dialog Title
        alertDialog.setTitle(strresponse);
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

                        Intent intent = new Intent(Login_Activity.this, Login_Activity.class);
                        startActivity(intent);
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
