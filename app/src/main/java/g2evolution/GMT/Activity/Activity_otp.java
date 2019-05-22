package g2evolution.GMT.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import g2evolution.GMT.Autoverifyotp.SmsBroadcastReceiver;
import g2evolution.GMT.Autoverifyotp.SmsListener2;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;

public class Activity_otp extends AppCompatActivity {

    EditText Otp;
    Button _butsubmit;
    Context context;
    String Message;
    String OTPId;
    String status,message;

   // public static final String SIGNUP_OTP_URL="http://brprojects.co.in/fertilizers/signup_otp.php";
   // public static final String KEY_OTP="otp_num";

    String strmobileno;
    String _otp = null;
    String user_id,user_name,user_email,user_mobile;
    public static final String OTP_REGEX = "[0-9]{1,6}";

    public static final Integer REQUEST_CODE_READ_SMS=1;
    public static final Integer REQUEST_ID_MULTIPLE_PERMISSIONS=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_otp);
        ActivityCompat.requestPermissions(Activity_otp.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, REQUEST_CODE_READ_SMS);

        //checkAndRequestPermissions();

        SharedPreferences prefuserdata = getSharedPreferences("regotp", 0);
        OTPId = prefuserdata.getString("otpid","0");
        strmobileno = prefuserdata.getString("mobile","0");
        Otp =(EditText)findViewById(R.id.OTP);
        _butsubmit =(Button)findViewById(R.id.Submit);

        SmsBroadcastReceiver.bindListener(new SmsListener2() {
            @Override
            public void messageReceived(String messageText) {
                Log.e("Text",messageText);
                Pattern pattern = Pattern.compile(OTP_REGEX);
                Matcher matcher = pattern.matcher(messageText);

                while (matcher.find())
                {
                    _otp = matcher.group();
                }

                Otp.setText(_otp);
                //    Log.e("testing","otp : "+otp);
                //  Toast.makeText(Activity_ForgotPasswordOTP.this,"OTP = auto11 : "+ otp ,Toast.LENGTH_LONG).show();
                //  Toast.makeText(MainActivity.this,"Message: "+messageText,Toast.LENGTH_LONG).show();
            }
        });

        _butsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        Otp.addTextChangedListener(watch);

    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);

        int receiveSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);

        int readSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (receiveSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_MMS);
        }
        if (readSMS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }
    TextWatcher watch = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub

          //  submit();
          /*  output.setText(s);
            if(a == 9){
                Toast.makeText(getApplicationContext(), "Maximum Limit Reached", Toast.LENGTH_SHORT).show();
            }*/
        }};


    private void submit() {
        if (!Validateotp()) {
            return;
        }


        else {

            _otp = Otp.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Activity_otp.this);
            if (cd.isConnectingToInternet()) {


                //  new TopTrend().execute();

                new Loader().execute();

            } else {


                Toast.makeText(Activity_otp.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


        }

    }

    private boolean Validateotp() {

        String phone = Otp.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 6) {

            Toast.makeText(Activity_otp.this, "Enter Otp", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            Otp.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }



    class Loader extends AsyncTask<Void, Void, JSONObject> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_otp.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_otp.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.SIGNUP_OTP_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);


            Log.e("testing", "result in post execute====beforeeee=====" + result);


            if (result != null) {
                dialog.dismiss();


                Log.e("testing", "result in post execute===after======" + result);

                if (status.equals("success")) {

                    //Toast.makeText(Activity_Login.this, Message, Toast.LENGTH_LONG).show();
                    Log.e("testing", "Message111==" + Message);
                    Intent intent = new Intent(Activity_otp.this, MainActivity.class);
                    SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("UserId", "" + user_id);
                    prefeditor.putString("Username", "" + user_name);
                    prefeditor.putString("Usermail", "" + user_email);
                    prefeditor.putString("Usermob", "" + user_mobile);
                    prefeditor.commit();
                    startActivity(intent);
                    finish();


                    } else if (status.equals("error")){

                    Toast.makeText(Activity_otp.this, "Wrong OTP Entered", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(Activity_otp.this, Message, Toast.LENGTH_LONG).show();

                    dialog.dismiss();
                }
            }

        }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.SIGNUP_OTP_userMobile,strmobileno);
            object.put(EndUrl.SIGNUP_OTP_otp,_otp);


            //if you want to modify some value just do like this.

            details.put(EndUrl.SIGNUPOTPjsonobject_outside_otp,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }


        public JSONObject postJsonObject(String url, JSONObject loginJobj) {
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
                if (inputStream != null)

                    result = convertInputStreamToString(inputStream);
                else
                    result = "Did not work!";


            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            JSONObject json = null;
            try {

                json = new JSONObject(result);
                Log.e("testing", "testing in json result=======" + result);
                Log.e("testing", "testing in json result json=======" + json);
                Log.e("testing", "result in post status=========" + json.getString("status"));
                Log.e("testing", "result in post message=========" + json.getString("message"));

                status = json.getString("status");
                message = json.getString("message");

                String arrayresponce = json.getString("data");


                Log.e("testing", "adapter value=" + arrayresponce);

                JSONArray responcearray = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i = 0; i < responcearray.length(); i++) {

                    JSONObject post = responcearray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();


                    user_id = post.getString("user_id");
                    user_name = post.getString("user_name");
                    user_email = post.getString("user_email");
                    user_mobile = post.getString("user_mobile");


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // 11. return result

            return json;
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;
        }
    }










