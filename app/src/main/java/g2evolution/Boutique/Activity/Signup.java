package g2evolution.Boutique.Activity;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.WorldPopulation;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.NotificationUtils;
import g2evolution.Boutique.app.Config;

public class Signup extends AppCompatActivity {

    TextView signup,login;

    EditText editname, editemailid, editmobileno, editpassword;

    Button submit;

    JSONParser jsonParser = new JSONParser();
    String strname, strmobileno, stremail, strpassword;

    String status,message,userid ;


    // ----FCM server key on android@g2evolution.co.in----
    //------------------------FCM Notification--------------------------
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    //private TextView txtRegId, txtMessage;
    //------------------------FCM Notification--------------------------
    String regId;
 //   Spinner spinregapartment;

    String strregapartmentid;


    //------------------searchable spinner----------------
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<WorldPopulation> world;//spinapartment
    ArrayList<String> worldlist;//spinapartment
    Spinner spinapartment;
    String capartmentid_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


      //  signup = (TextView)findViewById(R.id.signup);
      //  login = (TextView)findViewById(R.id.login);

        editname = (EditText)findViewById(R.id.username);
        editemailid = (EditText)findViewById(R.id.mailid);
        editmobileno = (EditText)findViewById(R.id.mobileno);
        editpassword = (EditText)findViewById(R.id.password);

      //  spinregapartment = (Spinner) findViewById(R.id.spinregapartment);

        ConnectionDetector cd = new ConnectionDetector(Signup.this);
        if (cd.isConnectingToInternet()) {
            // new LoadSpinnerdata().execute();
          //  new DownloadJSON2().execute();

        } else {
            Toast.makeText(Signup.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
        }

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
//------------------------FCM Notification--------------------------


        submit = (Button)findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                submit();

            }
        });

    }

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






    private void submit() {
        if (!ValidateUsername()) {
            return;
        }

        if (!validateEmail()) {
            return;
        }
        if (!validateMobileno()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        else {

            strname = editname.getText().toString();
            stremail = editemailid.getText().toString();
            strmobileno = editmobileno.getText().toString();
            strpassword = editpassword.getText().toString();

            ConnectionDetector cd = new ConnectionDetector(Signup.this);
            if (cd.isConnectingToInternet()) {


              //  new TopTrend().execute();

                new Loader().execute();

            } else {


                Toast.makeText(Signup.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


        }

    }

    //------------------------Validating Username------------------------

    private boolean ValidateUsername() {



        if (editname.getText().toString().trim().isEmpty()) {

            Toast.makeText(Signup.this, "Enter Username", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            editname.requestFocus();
            return false;
        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }



    //-----------------------Validating email--------------------
    private boolean validateEmail() {

        String email = editemailid.getText().toString().trim();
        if (email.isEmpty() || !isValidateEmail(email)) {
            Toast.makeText(Signup.this, "Enter Email id", Toast.LENGTH_LONG).show();
            //edt_email.setError(getString(R.string.reg_validation_email));
            editemailid.requestFocus();
            return false;

        } else {
            // inputLayoutemail.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidateEmail(String email) {

        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //------------------Validating mobileno-----------------
    private boolean validateMobileno() {

        String phone = editmobileno.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {

            Toast.makeText(Signup.this, "Enter Mobileno", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            editmobileno.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }

    //-------------------------Validating Password------------------------
    private boolean validatePassword() {


        if (editpassword.getText().toString().trim().isEmpty()) {

            Toast.makeText(Signup.this, "Enter Password", Toast.LENGTH_LONG).show();
            //edt_landmark.setError(getString(R.string.reg_validation_landmark));
            editpassword.requestFocus();
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
                dialog = new ProgressDialog(Signup.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Signup.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.SIGNIN_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(Signup.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Signup.this, Activity_otp.class);

                    SharedPreferences prefuserdata = getSharedPreferences("regotp", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("otpid", "" + userid);
                    prefeditor.putString("mobile", "" + strmobileno);
                    //   prefeditor.putString("designationId", "" + designationId);

                    prefeditor.commit();
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Signup.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Signup.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.SIGNIN_USENAME,strname);
            object.put(EndUrl.SIGNIN_EMAILID,stremail);
            object.put(EndUrl.SIGNIN_MOBILENO,strmobileno);
            object.put(EndUrl.SIGNIN_PASSWORD,strpassword);
            object.put(EndUrl.SIGNIN_registerFrom,"Application");
            object.put(EndUrl.SIGNIN_FCM_Token,regId);

            //if you want to modify some value just do like this.

            details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

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
                userid  = post.getString("userid ");

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

  /*  private class DownloadJSON2 extends AsyncTask<Void, Void, Void> {

        public static final   String dsgsg=EndUrl.Pincode_URL;


        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            world = new ArrayList<WorldPopulation>();
            // Create an array to populate the spinner
            worldlist = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address
            jsonobject = JSONfunctions1
                    .getJSONfromURL(dsgsg,"GET", userpramas);

            Log.e("testing","apartment = "+jsonobject);
            try {

                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("data");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    WorldPopulation worldpop = new WorldPopulation();

                    worldpop.setAname(jsonobject.optString("pincode"));
                    worldpop.setAid(jsonobject.optString("pincodeId"));
				*//*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*//*
                    world.add(worldpop);

                    // Populate spinner with country names
                    worldlist.add(jsonobject.optString("pincode"));

                }

            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            // Locate the spinner in activity_main.xml
            // Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

*//*
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.checkbox1);
            multiSelectionSpinner.setItems(students1);
            // multiSelectionSpinner.setSelection(new int[]{2, 6});
            multiSelectionSpinner.setListener(this);*//*


            // -----------------------------spinner for age-----------------------------------


            spinnerAdapter adapter = new spinnerAdapter(Signup.this, R.layout.spinner_item);
            adapter.addAll(worldlist);
            adapter.add("Select Pincode");
            spinregapartment.setAdapter(adapter);
            spinregapartment.setSelection(adapter.getCount());

            *//*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*//*

            // Spinner on item click listener
            spinregapartment
                    .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> arg0,
                                                   View arg1, int position, long arg3) {
                            // TODO Auto-generated method stub
                            // Locate the textviews in activity_main.xml
                            if(spinregapartment.getSelectedItem() == "Select Pincode")
                            {
                                //Do nothing.
                            }
                            else{

                                strregapartmentid = world.get(position).getAid();


                                Log.e("ugender", "ugender = " + strregapartmentid);
                                // new DownloadJSON4().execute();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


        }
    }*/

}
