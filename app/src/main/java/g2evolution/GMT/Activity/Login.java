package g2evolution.GMT.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.messaging.FirebaseMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.GMT.EndUrl;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONParser;
import g2evolution.GMT.Utility.NotificationUtils;
import g2evolution.GMT.app.Config;

public class Login extends AppCompatActivity {

EditText logmob;
    TextView logsubmit,forgetpasswd;

    JSONParser jsonParser = new JSONParser();

    String loginid, loginname, loginmobileno, loginemail,  loginlocation, location_id;
    EditText logemail,logpassword;
    String strmobileno, strpassword,strloginid;

    TextView skip;

    String strmailid;

    ArrayList<HashMap<String, String>> arraylist;

    String status,message;

    String _userId,_userName,_userEmail,_usermob;

    EditText forget_mobile;

    TextView forgot_submit;

    String Strforgot;

    EditText fotp_num,fotp_otp;

    TextView fotp_submit;
    String Strfotp_otp;
    String userid;


    EditText new_password;

    TextView newpass_submit;

    String strnewpassword;

    TextView signup;

    // ----FCM server key on android@g2evolution.co.in----
    //------------------------FCM Notification--------------------------
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__login_);


        logmob = (EditText) findViewById(R.id.logmob);
        logpassword = (EditText) findViewById(R.id.logpassword);

        logsubmit = (TextView) findViewById(R.id.logsubmit);

        skip = (TextView) findViewById(R.id.skip);

        forgetpasswd = (TextView) findViewById(R.id.forgetpasswd);

        signup = (TextView) findViewById(R.id.signup);


        logsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Signup.class);
                startActivity(i);

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        forgetpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent i = new Intent(Login.this, Activity_ForgotPassword.class);
                startActivity(i);*/

                final Dialog logindialog = new Dialog(Login.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater)Login.this.getSystemService(Login.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.dialog_forgotpassword, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                logindialog.setContentView(convertView);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp);

                // fotp_num = (EditText)convertView.findViewById(R.id.fotp_num);
                forget_mobile = (EditText)convertView.findViewById(R.id.editmobileno);

                TextView forgot_submit = (TextView)convertView.findViewById(R.id.forgot_submit);

                forgot_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitForm();
                    }
                });

                logindialog.show();


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

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    // txtMessage.setText(message);
                }
            }
        };


        displayFirebaseRegId();
    }
//------------------------FCM Notification--------------------------




    //------------------------FCM Notification--------------------------------------------------------
    // Fetches reg id from shared preferences
    // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId))
            Log.e("testing","Firebase Reg Id: " + regId);
            // txtRegId.setText("Firebase Reg Id: " + regId);
        else
            Log.e("testing","Firebase Reg Id is not received yet!");
        //  txtRegId.setText("Firebase Reg Id is not received yet!");
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }


    //------------------------FCM Notification------------------------------------------------------





    // --------------------For validation all in fields after clicking submit button-----------------------
    private void submitForm() {

        if (!validationPhoneNo()) {
            return;
        }


        else {


            Strforgot = forget_mobile.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Login.this);
            if (cd.isConnectingToInternet()) {

                new  Loadforgot().execute();

            } else {


                Toast.makeText(Login.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }

    }
    //-----------------Validation Phone no-----------------------
    private boolean validationPhoneNo() {
        String phone = forget_mobile.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {

            Toast.makeText(Login.this, "Please Enter MobileNo", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            forget_mobile.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }

    private void submit() {

        strmobileno = logmob.getText().toString();

       if (strmobileno.isEmpty()){

           Toast.makeText(Login.this, "Please Enter Mobileno", Toast.LENGTH_SHORT).show();
       }

        else {

            strpassword = logpassword.getText().toString();
           if (strpassword.isEmpty()){

               Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
           }else{
               ConnectionDetector cd = new ConnectionDetector(Login.this);
               if (cd.isConnectingToInternet()) {

                    new Loader().execute();
                 //  new Loader123().execute();

               } else {


                   Toast.makeText(Login.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

               }
           }



        }

    }

    //------------------Validating mobileno-----------------
    private boolean ValidateUsername() {

        String phone = logemail.getText().toString();

        if (phone== null||phone.isEmpty()||phone.length()==0 ) {

            Toast.makeText(Login.this, "Please Enter MobileNo", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            logemail.requestFocus();
            return false;

        } else {

            // inputLayoutmobile.setErrorEnabled(false);
        }


        return true;

    }

    //-------------------------Validating Password------------------------
    private boolean validatePassword() {

        if (logpassword.getText().toString().trim().isEmpty()) {

            Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_LONG).show();
            //edt_landmark.setError(getString(R.string.reg_validation_landmark));
            logpassword.requestFocus();
            return false;
        } else {
            //inputLayoutlandmark.setErrorEnabled(false);
        }

        return true;
    }


       class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Login.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Login.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.LOGIN_URL, makingJson());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, MainActivity.class);

                    SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("UserId", "" + _userId);
                    prefeditor.putString("Username", "" + _userName);
                    prefeditor.putString("Usermail", "" + _userEmail);
                    prefeditor.putString("Usermob", "" + _usermob);

                    prefeditor.commit();
                    startActivity(intent);
                    finish();

                }else {

                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.LOGIN_MOBILENO,strmobileno);
            object.put(EndUrl.lOGIN_PASSWORD,strpassword);
            object.put(EndUrl.lOGIN_FCM_Token,regId);

            //if you want to modify some value just do like this.

         /*   details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());
*/
         Log.e("testing","object = "+object.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }



    public JSONObject postJsonObject(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;

        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");



            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                _userId  = post.getString("userId");
                _userName  = post.getString("userName");
                _userEmail  = post.getString("userEmail");
                _usermob  = post.getString("userMobile");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
//-----------------------------------------------------------------------------------------------------



     class Loadforgot extends AsyncTask<Void, Void, JSONObject> {

    ProgressDialog dialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            dialog = new ProgressDialog(Login.this, ProgressDialog.THEME_HOLO_LIGHT);
        }else{
            dialog = new ProgressDialog(Login.this);
        }
        dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected JSONObject doInBackground(Void... params) {

        return postJsonObject1(EndUrl.FORGTEPASSWORD_URL, makingJson1());
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        if (result!=null) {
            dialog.dismiss();

            Log.e("testing","result in post execute========="+result);

            if (status.equals("success")){
                Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();


                final Dialog logindialog = new Dialog(Login.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater)Login.this.getSystemService(Login.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.forgotpassword_otp, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                logindialog.setContentView(convertView);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp);

               // fotp_num = (EditText)convertView.findViewById(R.id.fotp_num);
                fotp_otp = (EditText)convertView.findViewById(R.id.fotp_otp);

                fotp_submit = (TextView)convertView.findViewById(R.id.fotp_submit);

                fotp_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        submitForgototp();
                    }
                });

                logindialog.show();

            }else {
                Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
            dialog.dismiss();
        }
    }

}

    private void submitForgototp() {

        if (!validatefotp()) {
            return;
        }

        else {

          //  Strfotp_num = fotp_num.getText().toString();
            Strfotp_otp = fotp_otp.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Login.this);
            if (cd.isConnectingToInternet()) {

                new Loadforgototp().execute();

            } else {

                Toast.makeText(Login.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }
        }


        }

    private boolean validatefotp() {

        String phone = fotp_otp.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {

            Toast.makeText(Login.this, "Enter OTP", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            fotp_otp.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }
        return true;
    }

    public JSONObject makingJson1() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.FORGET_MOBILENO,Strforgot);

            //if you want to modify some value just do like this.


        /*    details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());
*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public JSONObject postJsonObject1(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                _userId  = post.getString("userid");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }
//---------------------------------------------------------------------------------------------------------

    class Loadforgototp extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Login.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Login.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.FORGTEPASSWORDOTP_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();

                    final Dialog logindialog = new Dialog(Login.this);
                    logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    LayoutInflater inflater = (LayoutInflater)Login.this.getSystemService(Login.this.LAYOUT_INFLATER_SERVICE);
                    View convertView = (View) inflater.inflate(R.layout.changepassword, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                    logindialog.setContentView(convertView);
                    //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                    // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                    logindialog.setCanceledOnTouchOutside(true);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(logindialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    lp.gravity = Gravity.CENTER;
                    logindialog.getWindow().setAttributes(lp);


                    new_password = (EditText)convertView.findViewById(R.id.new_password);
                    newpass_submit = (TextView) convertView.findViewById(R.id.newpass_submit);

                    newpass_submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           submitnewpassword();

                        }
                    });
                    logindialog.show();

                }else {
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    private void submitnewpassword() {

        if (!validateNewPassword()) {
            return;
        }

        else {
            strnewpassword = new_password.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Login.this);
            if (cd.isConnectingToInternet()) {


                //  new TopTrend().execute();

                new newpasswordLoader().execute();

            } else {


                Toast.makeText(Login.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean validateNewPassword() {

        if (new_password.getText().toString().trim().isEmpty()) {

            Toast.makeText(Login.this, "Enter Password", Toast.LENGTH_LONG).show();
            //edt_landmark.setError(getString(R.string.reg_validation_landmark));
            new_password.requestFocus();
            return false;
        } else {
            //inputLayoutlandmark.setErrorEnabled(false);
        }

        return true;
    }


    private JSONObject makingJson2() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.FORGTEPASSWORDOTP_MOBILENO,Strforgot);
            object.put(EndUrl.FORGTEPASSWORDOTP_OTP,Strfotp_otp);

            Log.e("FORGTEPASSWORDOTP_OTP",""+object );

            Log.d("json",details.toString());

            //if you want to modify some value just do like this.

            details.put(EndUrl.FORGTEPASSWORDOTPjsonobject_outside_otp,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }
    public JSONObject postJsonObject2(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                userid  = post.getString("userid ");


                Log.e("testing","userid====="   + userid );



            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }



    class newpasswordLoader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Login.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Login.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject3(EndUrl.NewPASSWORD_URL, makingJson3());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, Login.class);


                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson3() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.NewPASSWORD_MOBILENO,Strforgot);
            object.put(EndUrl.NewPASSWORD_PASSWORD,strnewpassword);


            //if you want to modify some value just do like this.

         /*   details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());

*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }



    public JSONObject postJsonObject3(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                userid  = post.getString("userid ");
                /*username  = post.getString("userName ");
                usermail  = post.getString("userEmail ");
                usermobile  = post.getString("userMobile ");
*/
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }


    class Loader123 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Login.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Login.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject123(EndUrl.SIGNUP_URL, makingJson123());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Login.this, Activity_otp.class);

                    SharedPreferences prefuserdata = getSharedPreferences("regotp", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("otpid", "" + userid);
                    prefeditor.putString("mobile", "" + strmobileno);
                    //   prefeditor.putString("designationId", "" + designationId);

                    prefeditor.commit();
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Login.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson123() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.SIGNUP__json_insideMOBILENO,strmobileno);
            object.put(EndUrl.SINGUP__json_insideregisterFrom,"Application");
            object.put(EndUrl.SINGUP__json_FCM_Token,regId);

            //if you want to modify some value just do like this.

            details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }



    public JSONObject postJsonObject123(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL

            //http://localhost:9000/api/products/GetAllProducts
            HttpPost httpPost = new HttpPost(url);

            System.out.println(url);
            String json = "";

            // 4. convert JSONObject to JSON to String

            json = loginJobj.toString();

            System.out.println(json);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)

                result = convertInputStreamToString123(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing","testing in json result======="+result);
            Log.e("testing","testing in json result json======="+json);
            Log.e("testing","result in post status========="+json.getString("status"));
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                userid  = post.getString("userid ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString123(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


}

