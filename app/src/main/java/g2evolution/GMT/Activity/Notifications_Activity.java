package g2evolution.GMT.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

import g2evolution.GMT.Adapter.Adapter_orderdetails;
import g2evolution.GMT.Adapter.Notification_Adapter;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_orderdetails;
import g2evolution.GMT.FeederInfo.Notification_list;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONParser;

public class Notifications_Activity extends AppCompatActivity implements  Notification_Adapter.OnItemlevelsinside {


    ImageView back;

    String UserId,Username,Usermail,Usermob;


    RecyclerView notification_recycler;

    ImageView no_data;

    Notification_Adapter.OnItemlevelsinside mCallbackcropnames;
    private ArrayList<Notification_list> notification_lists=new ArrayList<Notification_list>();
    Notification_Adapter notification_adapter;

    String[] Message=new String[]{"Order is on the way","Order is on the way","Order is on the way","Order is on the way","Order is on the way"};
    String[] date=new String[]{"15-03-2019","15-03-2019","15-03-2019","15-03-2019","15-03-2019"};
    String[] Description=new String[]{"GMT0025",
            "GMT0025",
            "GMT0025",
            "GMT0025s",
            "GMT0025"};


    JSONParser jsonParser = new JSONParser();

    String status,message,orderid,products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);


        back= (ImageView) findViewById(R.id.back);
        notification_recycler = (RecyclerView) findViewById(R.id.notification_recycler);
        no_data = (ImageView) findViewById(R.id.no_data);
        notification_recycler.setLayoutManager(new LinearLayoutManager(Notifications_Activity.this));
        notification_recycler.setHasFixedSize(true);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        Log.e("testing","Usermob===="+Usermob);





        ConnectionDetector cd = new ConnectionDetector(Notifications_Activity.this);
        if (cd.isConnectingToInternet()) {


            Log.e("testing","xsbxqjhb");

          //  setUpReccyler();

            new LoadData().execute();

            Log.e("testing","after");

        } else {

            Toast.makeText(Notifications_Activity.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
    }



    private void setUpReccyler() {
        notification_lists = new ArrayList<Notification_list>();

        for (int i = 0; i < date.length; i++) {
            Notification_list feedInfo = new Notification_list();
            feedInfo.setDate(date[i]);
            feedInfo.setMessage(Message[i]);
            feedInfo.setOrder_number(Description[i]);

            notification_lists.add(feedInfo);
        }
        notification_adapter = new Notification_Adapter(getApplicationContext(), notification_lists, mCallbackcropnames);
        notification_recycler.setAdapter(notification_adapter);
    }

    @Override
    public void OnItemlevelsinside(int pos, String qty, int status) {

    }

    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Notifications_Activity.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Notifications_Activity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            notification_lists = new ArrayList<Notification_list>();
            return postJsonObject(EndUrl.Notifications_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    notification_adapter = new Notification_Adapter(Notifications_Activity.this, notification_lists, mCallbackcropnames);
                    notification_recycler.setAdapter(notification_adapter);

                } else if (status.equals("error")) {

                    notification_lists.clear();

                    notification_adapter = new Notification_Adapter(Notifications_Activity.this, notification_lists, mCallbackcropnames);
                    notification_recycler.setAdapter(notification_adapter);

                    Toast.makeText(Notifications_Activity.this, "no data found", Toast.LENGTH_LONG).show();
                }else  {

                    notification_lists.clear();

                    notification_adapter = new Notification_Adapter(Notifications_Activity.this, notification_lists, mCallbackcropnames);
                    notification_recycler.setAdapter(notification_adapter);

                    Toast.makeText(Notifications_Activity.this, "no data found", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(Notifications_Activity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Notifications_URL_Userid,UserId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

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
            status = json.getString("status");
            message = json.getString("message");

            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);



            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();



                Notification_list item = new Notification_list();

                item.setId(post.optString("productId"));
                item.setDate(post.optString("image"));
                item.setOrder_number(post.optString("postedOn"));
                item.setMessage(post.optString("title"));



                notification_lists.add(item);

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
    class LoadData extends AsyncTask<String, String, String> {
        String responce;
        String status;
        String strresponse;
        String strdata;
        String img;
        ProgressDialog mProgress;

        String strcode, strtype, strmessage;
        String strtotal_record;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Notifications_Activity.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            userpramas.add(new BasicNameValuePair(EndUrl.Notifications_URL_Userid, UserId));
            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.Notifications_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "GET", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(context,"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {

                    status = json.getString("status");
                    strmessage = json.getString("message");
                    strtotal_record = json.getString("total_record");

                    if (status.equals("success")) {
                        strmessage = json.getString("message");
                        strtotal_record = json.getString("total_record");
                        strdata = json.getString("data");


                        JSONArray responcearray = new JSONArray(strdata);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();



                            Notification_list item = new Notification_list();

                            item.setId(post.optString("id"));
                         //   item.setDate(post.optString("image"));
                            item.setDate(post.optString("date_created"));
                            item.setTitle(post.optString("title"));
                            item.setMessage(post.optString("message"));



                            notification_lists.add(item);

                        }

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
                notification_adapter = new Notification_Adapter(Notifications_Activity.this, notification_lists, mCallbackcropnames);
                notification_recycler.setAdapter(notification_adapter);



            } else if (status.equals("failure")) {
                Toast.makeText(Notifications_Activity.this, strmessage, Toast.LENGTH_SHORT).show();

            }else{

            }

        }

    }

}
