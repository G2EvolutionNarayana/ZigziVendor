package g2evolution.Boutique;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class SignUp_ACtivity extends AppCompatActivity implements View.OnClickListener {



    @BindView(R.id.user_name_edit_text)
    EditText user_name_edit_text;
    @BindView(R.id.phone_number_edit_text)
    EditText phone_number_edit_text;
    @BindView(R.id.password_edit_text)
    EditText password_edit_text;
    @BindView(R.id.edit_emailid)
    EditText edit_emailid;
    @BindView(R.id.sign_up_text)
    TextView sign_up_text;
    @BindView(R.id.member_text)
    TextView member_text;

    String strtemp_user_id;

    JSONParser jsonParser = new JSONParser();

    String strusername, strmobileno, stremailid, strpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);


        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView) findViewById(R.id.title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sub_title_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.sign_up_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.member_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.user_name_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.phone_number_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.password_edit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.edit_emailid)).setTypeface(typeface);

        listeners();
    }

    private void listeners() {

        sign_up_text.setOnClickListener(this);
        member_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sign_up_text:

                submit();

                break;


            case R.id.member_text:

                Intent intent2=new Intent(SignUp_ACtivity.this,Login_Activity.class);
                startActivity(intent2);
                break;




            default:


        }

    }
    private void submit() {


        strusername = user_name_edit_text.getText().toString();
        strmobileno = phone_number_edit_text.getText().toString();
        stremailid = edit_emailid.getText().toString();
        strpassword = password_edit_text.getText().toString();

        if (strusername.isEmpty()){

            Toast.makeText(SignUp_ACtivity.this, "Please Enter Username", Toast.LENGTH_SHORT).show();
        }else if (strmobileno.isEmpty()){

            Toast.makeText(SignUp_ACtivity.this, "Please Enter Mobileno", Toast.LENGTH_SHORT).show();
        }else if (stremailid.isEmpty()){

            Toast.makeText(SignUp_ACtivity.this, "Please Enter Email Id", Toast.LENGTH_SHORT).show();
        }

        else {
            if (strpassword.isEmpty()){

                Toast.makeText(SignUp_ACtivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            }else{

                //  strpassword = logpassword.getText().toString();

                ConnectionDetector cd = new ConnectionDetector(SignUp_ACtivity.this);
                if (cd.isConnectingToInternet()) {

                    // new Loader().execute();
                    new LoadSignup().execute();

                } else {


                    Toast.makeText(SignUp_ACtivity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }
            }



        }

    }


    class LoadSignup extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(SignUp_ACtivity.this);
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



            userpramas.add(new BasicNameValuePair(EndUrl.Signup_phone, strmobileno));
            userpramas.add(new BasicNameValuePair(EndUrl.Signup_name, strusername));
            userpramas.add(new BasicNameValuePair(EndUrl.Signup_email, strmobileno));
            userpramas.add(new BasicNameValuePair(EndUrl.Signup_password, strpassword));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.Signup_URL;
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
                        strtemp_user_id = jsonobjectdata.getString("temp_user_id");



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

                }else if (strtype.equals("creation_success")){
                    Toast.makeText(SignUp_ACtivity.this, strmessage, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUp_ACtivity.this, OTP_Activity.class);
                    SharedPreferences prefuserdata = getSharedPreferences("strsignup_user_id", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("strsignup_user_id", "" + strtemp_user_id);


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
                SignUp_ACtivity.this).create();

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

                        Intent intent = new Intent(SignUp_ACtivity.this, Login_Activity.class);
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
