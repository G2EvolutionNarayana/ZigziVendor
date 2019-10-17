package xplotica.littlekites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class Activity_Forgot_Password extends AppCompatActivity {

    View view_mobile;
    EditText edt_mobile;
    TextInputLayout inputLayoutmobileno;
    Animation anim;
    Button butsignup;
    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    String Result, Message;
    //CollapsingToolbarLayout collapsingToolbar;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        context = this;

        setid();

      /*  //  -----------------For Collapsing image Layout------------------------
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        //dynamicToolbarColor();

        toolbarTextAppernce();*/

        //  ------------------------------edittext animation -------------------

        anim = AnimationUtils.loadAnimation(Activity_Forgot_Password.this, R.anim.scale);

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


        else {

            ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {




                new  LoadSpinnerdata2().execute();


            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


            // SignupUser();
            /*Intent intent2 = new Intent(Activity_Forgot_Password.this, Activity_VehicleDetails.class);
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



    private void setid() {

        view_mobile = (View) findViewById(R.id.second_view_mobile);

        edt_mobile = (EditText) findViewById(R.id.edt_mobile);

        inputLayoutmobileno = (TextInputLayout) findViewById(R.id.mobile_layout);

        butsignup = (Button)findViewById(R.id.butsignup);

    }

  /*  private void toolbarTextAppernce() {
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

            userpramas.add(new BasicNameValuePair(End_Urls.FORGOT_MOBILE, strmobile));




            JSONObject json = jsonParser.makeHttpRequest(End_Urls.FORGOT_URL, "POST", userpramas);


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


                Intent intent = new Intent(getApplicationContext(), Login.class);
                Toast.makeText(Activity_Forgot_Password.this, Message, Toast.LENGTH_LONG).show();
                ((Activity_Forgot_Password) context).finish();
                startActivity(intent);

            }else{

                Toast.makeText(Activity_Forgot_Password.this, Message, Toast.LENGTH_LONG).show();




            }



        }

    }



}
