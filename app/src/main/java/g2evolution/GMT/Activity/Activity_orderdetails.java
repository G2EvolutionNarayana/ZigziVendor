package g2evolution.GMT.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import g2evolution.GMT.Adapter.Adapter_orderdetails;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_orderdetails;
import g2evolution.GMT.R;

/**
 * Created by G2e Android on 06-02-2018.
 */

public class Activity_orderdetails extends AppCompatActivity implements Adapter_orderdetails.OnItemClick {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_orderdetails> allItems1 = new ArrayList<FeederInfo_orderdetails>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_orderdetails> mListFeederInfo;
    Adapter_orderdetails mAdapterFeeds;
    RecyclerView rView;
    String strorderid;
    String orderitemid;
    private Adapter_orderdetails.OnItemClick mCallback;
    String userid,pid;
    String status,message,orderid,products;
    ImageView back;
    String strreason;
    Dialog dialog; // cancel dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_orderdetails);

        SharedPreferences prefuserdata = this.getSharedPreferences("OrderDetails", 0);
        orderid = prefuserdata.getString("orderid", "");


        SharedPreferences prefuserdata2 = getSharedPreferences("regcart", 0);
        userid = prefuserdata2.getString("userid","");
        pid = prefuserdata2.getString("productid","");

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_orderdetails.this));
        //setUpRecycler();
        // context = this;
      //  lLayout = new GridLayoutManager(Activity_orderdetails.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
       // rView.setLayoutManager(lLayout);
     //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        mCallback = this;

        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

       // setUpRecycler();

        new Loader().execute();



    }

    @Override
    public void onClickedItem(int pos, String qty, int status) {
        Log.e("DMen", "Pos:"+ pos + "Qty:"+qty);
        Log.e("testing","status  = "+status);
        Log.e("testing","title inm  = "+allItems1.get(pos).getId());

        orderitemid = allItems1.get(pos).getId();

        dialog.setContentView(R.layout.customdialogbox);
        final EditText editreason = (EditText) dialog.findViewById(R.id.editreason);
        Button butsubmit = (Button) dialog.findViewById(R.id.butsubmit);
        ImageView imgcancel = (ImageView) dialog.findViewById(R.id.imgcancel);

        butsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editreason.getText().toString() == null || editreason.getText().toString().length() == 0){
                    Toast.makeText(Activity_orderdetails.this, "Enter Reason", Toast.LENGTH_SHORT).show();
                }else{

                    strreason = editreason.getText().toString();

                    new DeleteItem().execute();
                }
            }
        });
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();




    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_orderdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_orderdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<FeederInfo_orderdetails>();
            return postJsonObject(EndUrl.OrderDetails_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    mAdapterFeeds = new Adapter_orderdetails(Activity_orderdetails.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                } else if (status.equals("error")) {

                    allItems1.clear();

                    mAdapterFeeds = new Adapter_orderdetails(Activity_orderdetails.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(Activity_orderdetails.this, "no data found", Toast.LENGTH_LONG).show();
                }else  {

                    allItems1.clear();

                    mAdapterFeeds = new Adapter_orderdetails(Activity_orderdetails.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(Activity_orderdetails.this, "no data found", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(Activity_orderdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.OrderDetails_order_id,orderid);


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



                FeederInfo_orderdetails item = new FeederInfo_orderdetails();

                 item.setId(post.optString("productId"));
                 item.setOrderimage(post.optString("image"));
                item.setOrderdate(post.optString("postedOn"));
                item.setOrdername(post.optString("title"));
                item.setOrderprodetails(post.optString("subTitle"));
                item.setOrderpriceamount(post.optString("price"));
                item.setQuantity_ordertext(post.optString("qty"));
                item.setOrdertotalamount(post.optString("totalAmount"));



                allItems1.add(item);

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


    class DeleteItem extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_orderdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_orderdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.OrderItemDelete_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {
                    Toast.makeText(Activity_orderdetails.this, message, Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    new Loader().execute();

                } else{
                    Toast.makeText(Activity_orderdetails.this, message, Toast.LENGTH_LONG).show();


                }

            } else {
                Toast.makeText(Activity_orderdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }



    public JSONObject makingJson2() {


        JSONObject object = new JSONObject();

        try {


            object.put(EndUrl.OrderItemDelete_Userid,userid);
            object.put(EndUrl.OrderItemDelete_Orderid,orderid);
            object.put(EndUrl.OrderItemDelete_Orderitem,orderitemid);
            object.put(EndUrl.OrderItemDelete_Reason,strreason);


            Log.e("json",object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

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

                result = convertInputStreamToString2(inputStream);
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

          /*  // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);



            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();



                FeederInfo_orderdetails item = new FeederInfo_orderdetails();

                item.setOrderimage(post.optString("image"));
                item.setOrderdate(post.optString("postedOn"));
                item.setOrdername(post.optString("title"));
                item.setOrderprodetails(post.optString("subTitle"));
                item.setOrderpriceamount(post.optString("TaxandPrice"));
                item.setQuantity_ordertext(post.optString("qty"));
                item.setOrdertotalamount(post.optString("NetAmount"));



                allItems1.add(item);

            }*/


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}







