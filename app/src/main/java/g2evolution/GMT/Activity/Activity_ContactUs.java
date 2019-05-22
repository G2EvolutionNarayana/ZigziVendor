package g2evolution.GMT.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import g2evolution.GMT.EndUrl;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONParser;

public class Activity_ContactUs extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();

    EditText editusername, editemailid,  editmessage;

    ImageView imgsend;

    String shuesrid, shname, shemailid, shmobileno;

    ImageView imgcancel;

    String strusername, stremailid, strcontactus, strsendmessage;
    TextView textemail, textmobileno,editcontactno,editaddress;

    String message,status;

    LinearLayout linearcontact;
    CardView cardcontactno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        shname = "";
        shemailid = "";
        shmobileno = "";

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        shuesrid = prefuserdata.getString("UserId", "");
        shname = prefuserdata.getString("Username", "");
        shemailid = prefuserdata.getString("Usermail", "");
        shmobileno = prefuserdata.getString("Usermob", "");

        Log.e("testing","shuesrid = "+shuesrid);
        Log.e("testing","shname = "+shname);
        Log.e("testing","shemailid = "+shemailid);
        Log.e("testing","shmobileno = "+shmobileno);


        linearcontact=(LinearLayout)findViewById(R.id.linearcontact);
        cardcontactno=(CardView) findViewById(R.id.cardcontactno);

        if (shuesrid==null||shuesrid.length()==0||shuesrid.equals("")||shuesrid.equals("null")){

            linearcontact.setVisibility(View.GONE);
            cardcontactno.setVisibility(View.GONE);

        }else {


        }
        //editusername = (EditText) findViewById(R.id.editusername);
        editemailid = (EditText) findViewById(R.id.editemailid);
        editcontactno = (TextView) findViewById(R.id.editcontactno);
        editmessage = (EditText) findViewById(R.id.editmessage);

      //  editusername.setText(shname);
       // editemailid.setText(shemailid);
        editcontactno.setText(shmobileno);

        textemail = (TextView) findViewById(R.id.textemail);
        textmobileno = (TextView) findViewById(R.id.textmobileno);
        editaddress = (TextView) findViewById(R.id.editaddress);


        imgcancel = (ImageView) findViewById(R.id.imgcancel);

       // imgsend = (ImageView) findViewById(R.id.imgsend);

        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (shuesrid == null || shuesrid.length() == 0 || shuesrid.equals("")) {

                    shemailid = editemailid.getText().toString();
                    strsendmessage = editmessage.getText().toString();


                    if (!validateEmail()) {
                        return;
                    }
                    if (strsendmessage == null || strsendmessage.length() == 0) {

                        Toast.makeText(Activity_ContactUs.this, "Enter Message", Toast.LENGTH_SHORT).show();
                        editmessage.setError("Enter Message");

                    } else {

                        ConnectionDetector cd = new ConnectionDetector(Activity_ContactUs.this);
                        if (cd.isConnectingToInternet()) {

                            shemailid = editemailid.getText().toString();
                            strsendmessage = editmessage.getText().toString();
                            new Loader123().execute();

                        } else {

                            Toast.makeText(Activity_ContactUs.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                        }

                    }

            }else

                {

                    shemailid = editemailid.getText().toString();
                    strsendmessage = editmessage.getText().toString();

                    if (!validateEmail()) {
                        return;
                    }
                    if (strsendmessage == null || strsendmessage.length() == 0) {

                        Toast.makeText(Activity_ContactUs.this, "Enter Message", Toast.LENGTH_SHORT).show();
                        editmessage.setError("Enter Message");

                    } else {

                        ConnectionDetector cd = new ConnectionDetector(Activity_ContactUs.this);
                        if (cd.isConnectingToInternet()) {

                            Log.e("testing", "shemailid====" + shemailid);
                            shemailid = editemailid.getText().toString();
                            strsendmessage = editmessage.getText().toString();

                            new Loader123().execute();

                        } else {

                            Toast.makeText(Activity_ContactUs.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                        }

                    }

                }
            }
        });


        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        textemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "info@grcshop.in", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
            }
        });


/*

        textmobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri number = Uri.parse("7022165904");

                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                startActivity(callIntent);
            }
        });
*/

    }


    //-----------------------Validating email--------------------
    private boolean validateEmail() {

        String email = editemailid.getText().toString().trim();
        if (email.isEmpty() || !isValidateEmail(email)) {
            Toast.makeText(Activity_ContactUs.this, "Enter Email id", Toast.LENGTH_LONG).show();
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

    class Loader123 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_ContactUs.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_ContactUs.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject123(EndUrl.fragment_feedback, makingJson123());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                //    Toast.makeText(Activity_ContactUs.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Activity_ContactUs.this, MainActivity.class);
                    startActivity(intent);
                  //  finish();

                }else {
                    Toast.makeText(Activity_ContactUs.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_ContactUs.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson123() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.fragment_feedback_emailid,shemailid);
            object.put(EndUrl.fragment_feedback_userid,shuesrid);
            object.put(EndUrl.fragment_feedback_message,strsendmessage);

            //    object.put(EndUrl.fragment_feedback_userid,stremailid);
           // object.put(EndUrl.SIGNUP__json_insideMOBILENO,strusername);
          //  object.put(EndUrl.SIGNUP__json_insideMOBILENO,strsendmessage);
            //if you want to modify some value just do like this.

        //    details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

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
              String  userid  = post.getString("userid ");

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
