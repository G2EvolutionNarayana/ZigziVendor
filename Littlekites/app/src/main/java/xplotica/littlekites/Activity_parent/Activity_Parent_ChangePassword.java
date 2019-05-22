package xplotica.littlekites.Activity_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Activity.Activity_home;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_ChangePassword extends AppCompatActivity {

    View view_password, view_newpassword;
    EditText edt_password, edt_newpassword;
    TextInputLayout inputLayoutpassword, inputLayoutnewpassword;
    Animation anim;
    Button butsignup;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    String Result, Message;
   // CollapsingToolbarLayout collapsingToolbar;
    String schoolid,staffid,type,classid,sectionid, mobileno;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_change_password);

        context = this;


        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "2");
        mobileno = prefuserdata.getString("mobile", "");




        setid();
/*
        //  -----------------For Collapsing image Layout------------------------
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        //dynamicToolbarColor();

        toolbarTextAppernce();*/

        //  ------------------------------edittext animation -------------------

        anim = AnimationUtils.loadAnimation(Activity_Parent_ChangePassword.this, R.anim.scale);

        butsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }



    // --------------------For validation all in fields after clicking submit button-----------------------
    private void submitForm() {

        if (!validatePassword()) {
            return;
        }else if (!validateNewPassword()) {
            return;
        }


        else {

            ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {




                new LoadSpinnerdata2().execute();


            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


            // SignupUser();
            /*Intent intent2 = new Intent(Login.this, Activity_VehicleDetails.class);
            startActivity(intent2);
            finish();*/
        }

    }


    //---------------------Validation Password-----------------------
    private boolean validatePassword() {
        if (edt_password.getText().toString().trim().isEmpty()) {

            Toast.makeText(context, "Enter Old Password", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            edt_password.requestFocus();
            return false;
        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;

    }

    //---------------------Validation NewPassword-----------------------
    private boolean validateNewPassword() {
        if (edt_newpassword.getText().toString().trim().isEmpty()) {

            Toast.makeText(context, "Enter New Password", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            edt_newpassword.requestFocus();
            return false;
        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;

    }

    private void setid() {


        view_password = (View) findViewById(R.id.second_view_password);
        view_newpassword = (View) findViewById(R.id.second_view_newpassword);


        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_newpassword = (EditText) findViewById(R.id.edt_newpassword);


        inputLayoutpassword = (TextInputLayout) findViewById(R.id.password_layout);
        inputLayoutnewpassword = (TextInputLayout) findViewById(R.id.newpassword_layout);

        butsignup = (Button)findViewById(R.id.butsignup);

    }

   /* private void toolbarTextAppernce() {
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }*/


    class LoadSpinnerdata2 extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String status;

        String strresult1, strresult2, strresult3;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;



        final String strpassword = edt_password.getText().toString();
        final String strnewpassword = edt_newpassword.getText().toString();

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
            Log.e("testing", "mobileno value=" + mobileno);
            Log.e("testing", "strpassword value=" + strpassword);
            Log.e("testing", "strnewpassword value=" + strnewpassword);

            userpramas.add(new BasicNameValuePair(End_Urls.CHANGEPASSWORD_MOBILE, mobileno));
            userpramas.add(new BasicNameValuePair(End_Urls.CHANGEPASSWORD_PASSWORD, strpassword));
            userpramas.add(new BasicNameValuePair(End_Urls.CHANGEPASSWORD_CPASSWORD, strnewpassword));




            JSONObject json = jsonParser.makeHttpRequest(End_Urls.CHANGEPASSWORD_URL, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");

                try {
                    Result = json.getString("status");
                    Message = json.getString("responseText");







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



            if (Result.equals("success")){

                Toast.makeText(Activity_Parent_ChangePassword.this, Message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), Activity_home.class);
                finish();
                startActivity(intent);

            }else{

                Toast.makeText(Activity_Parent_ChangePassword.this, Message, Toast.LENGTH_LONG).show();




            }



        }

    }


}

