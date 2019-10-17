package xplotica.littlekites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
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

import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Verifiy_Phone extends AppCompatActivity {

    View view_mobile, view_password;
    EditText edt_mobile, edt_password;
    TextInputLayout inputLayoutmobileno, inputLayoutpassword;
    Animation anim;
    Button butsignup;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    String Result, Message;
  //  CollapsingToolbarLayout collapsingToolbar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        context = this;

        setid();

       /* //  -----------------For Collapsing image Layout------------------------
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("OTP");
        //dynamicToolbarColor();

        toolbarTextAppernce();*/

        //  ------------------------------edittext animation -------------------

        anim = AnimationUtils.loadAnimation(Activity_Verifiy_Phone.this, R.anim.scale);

        butsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
    }


    // --------------------For validation all in fields after clicking submit button-----------------------
    private void submitForm() {

        if (!validationPhoneNo()) {
            return;
        }
        else if (!validatePassword()) {
            return;
        }


        else {

            ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {




                new  LoadSpinnerdata2().execute();


            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


            // SignupUser();
            /*Intent intent2 = new Intent(Activity_Verifiy_Phone.this, Activity_VehicleDetails.class);
            startActivity(intent2);
            finish();*/
        }

    }
    //-----------------Validation Phone no-----------------------
    private boolean validationPhoneNo() {
        String phone = edt_mobile.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {

            Toast.makeText(context, "Enter Mobile no", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            edt_mobile.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }

    //---------------------Validation Password-----------------------
    private boolean validatePassword() {
        if (edt_password.getText().toString().trim().isEmpty()) {

            Toast.makeText(context, "Enter OTP", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            edt_password.requestFocus();
            return false;
        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;

    }

    private void setid() {

        view_mobile = (View) findViewById(R.id.second_view_mobile);
        view_password = (View) findViewById(R.id.second_view_password);

        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_password = (EditText) findViewById(R.id.edt_password);

        inputLayoutmobileno = (TextInputLayout) findViewById(R.id.mobile_layout);
        inputLayoutpassword = (TextInputLayout) findViewById(R.id.password_layout);

        butsignup = (Button)findViewById(R.id.butsignup);

    }

 /*   private void toolbarTextAppernce() {
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


        final String strmobile = edt_mobile.getText().toString();
        final String strotp = edt_password.getText().toString();

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
            Log.e("testing", "strmobile value=" + strmobile);
            Log.e("testing", "strotp value=" + strotp);

            userpramas.add(new BasicNameValuePair(End_Urls.VERIFY_MOBILE, strmobile));
            userpramas.add(new BasicNameValuePair(End_Urls.VERIFY_OTP, strotp));




            JSONObject json = jsonParser.makeHttpRequest(End_Urls.VERIFY_URL, "POST", userpramas);


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


                Intent intent = new Intent(getApplicationContext(), Activity_Details.class);

                SharedPreferences prefuserdata = getSharedPreferences("loginresponse", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                prefeditor.putString("loginresponse", "" + "yes");
                prefeditor.putString("strmobile", "" + strmobile);

                prefeditor.commit();


                ((Activity_Verifiy_Phone) context).finish();
                startActivity(intent);

            }else{

                Toast.makeText(Activity_Verifiy_Phone.this, Message, Toast.LENGTH_LONG).show();




            }



        }

    }



}
