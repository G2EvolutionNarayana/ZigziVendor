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
import java.util.HashMap;

import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;

public class Activity_ChangePassword extends AppCompatActivity {

    EditText editnewpassword, editconfirmpassword;
    TextView textsubmit;

    String userid,pid,shname,shemailid,shmobileno;

    String strnewpassword, strconfirmpassword;

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
        editconfirmpassword = (EditText) findViewById(R.id.editconfirmpassword);

        textsubmit = (TextView) findViewById(R.id.textsubmit);

        textsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {


        strnewpassword = editnewpassword.getText().toString();
        strconfirmpassword = editconfirmpassword.getText().toString();

        if (strnewpassword == null || strnewpassword.trim().length() == 0){
            Toast.makeText(Activity_ChangePassword.this, "Enter New Password", Toast.LENGTH_SHORT).show();
        }else if (strconfirmpassword == null || strconfirmpassword.trim().length() == 0){

            Toast.makeText(Activity_ChangePassword.this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();

        }else{
            if (strnewpassword.equals(strconfirmpassword)){

                ConnectionDetector cd = new ConnectionDetector(Activity_ChangePassword.this);
                if (cd.isConnectingToInternet()) {


                    //  new TopTrend().execute();

                    new newpasswordLoader().execute();

                } else {


                    Toast.makeText(Activity_ChangePassword.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(Activity_ChangePassword.this, "Enter correct password", Toast.LENGTH_SHORT).show();
            }
        }


    }



    class newpasswordLoader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_ChangePassword.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_ChangePassword.this);
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
                    Toast.makeText(Activity_ChangePassword.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Activity_ChangePassword.this, Login.class);


                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(Activity_ChangePassword.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_ChangePassword.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson3() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.NewPASSWORD_MOBILENO,shmobileno);
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


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

}
