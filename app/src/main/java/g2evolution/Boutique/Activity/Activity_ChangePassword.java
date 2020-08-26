package g2evolution.Boutique.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_ChangePassword extends AppCompatActivity {

    EditText editnewpassword, editoldpassword;
    TextView textsubmit;

    String userid,pid,shname,shemailid,shmobileno;

    String strnewpassword, stroldpassword;


    JSONParser jsonParser = new JSONParser();

    String status, message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        SharedPreferences prefuserdata1 =  getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Log.e("testing","userid======"+userid);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView textview_title1 = (TextView) findViewById(R.id.textview_title1);
        textview_title1.setText("Change Password");

        editnewpassword = (EditText) findViewById(R.id.editnewpassword);
        editoldpassword = (EditText) findViewById(R.id.editoldpassword);

        textsubmit = (TextView) findViewById(R.id.textsubmit);

        textsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {


        stroldpassword = editoldpassword.getText().toString();
        strnewpassword = editnewpassword.getText().toString();

        if (stroldpassword == null || stroldpassword.trim().length() == 0){

            Toast.makeText(Activity_ChangePassword.this, "Enter Old Password", Toast.LENGTH_SHORT).show();

        }else if (strnewpassword == null || strnewpassword.trim().length() == 0){
            Toast.makeText(Activity_ChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
        }else{


                ConnectionDetector cd = new ConnectionDetector(Activity_ChangePassword.this);
                if (cd.isConnectingToInternet()) {


                    //  new TopTrend().execute();

                    new ChangePassword().execute();

                } else {


                    Toast.makeText(Activity_ChangePassword.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                }

        }


    }


    class ChangePassword extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  mProgress = new ProgressDialog(Activity_Profile.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);
*/
            pDialog = new ProgressDialog(Activity_ChangePassword.this);
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.ChangePASSWORD_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.ChangePASSWORD_PASSWORD, strnewpassword));
            userpramas.add(new BasicNameValuePair(EndUrl.ChangePASSWORD_old_password, stroldpassword));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.ChangePASSWORD_URL;
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
                 /*   if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");


                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        strjsonusername = jsonobjectdata.getString("name");
                        strjsonemailid = jsonobjectdata.getString("email");
                        strjsonmobileno = jsonobjectdata.getString("phone");



                    } else {
                    }*/


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
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                Intent intent = new Intent(Activity_ChangePassword.this, Activity_Profile.class);
                startActivity(intent);


            } else if (status.equals("failure")) {
                Toast.makeText(Activity_ChangePassword.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }

}
