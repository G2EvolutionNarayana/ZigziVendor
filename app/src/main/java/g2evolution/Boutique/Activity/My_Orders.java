package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import g2evolution.Boutique.Adapter.Adapter_orderhistory;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_orderhistory;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

public class My_Orders extends AppCompatActivity  implements Adapter_orderhistory.OnItemClick {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_orderhistory> allItems1 = new ArrayList<FeederInfo_orderhistory>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_orderhistory> mListFeederInfo;
    Adapter_orderhistory mAdapterFeeds;
    RecyclerView rView;
    String strreason;
    String orderitemid;

    String UserId;

    JSONParser jsonParser = new JSONParser();

    String status,message,order_date,ordersInfo;
    Dialog dialog1; // cancel dialog

    private Adapter_orderhistory.OnItemClick mCallback;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView back;

    String Username,Usermail,Usermob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentorder_history);

        back= (ImageView) findViewById(R.id.back);
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

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(My_Orders.this));
        //setUpRecycler();
        // context = this;
        //lLayout = new GridLayoutManager(getActivity(),2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        // rView.setLayoutManager(lLayout);
        // mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        mCallback = this;
        //   setUpRecycler();


        SharedPreferences prefuserdata1 = getSharedPreferences("regId", 0);
        UserId = prefuserdata1.getString("UserId", "");

        new LoadOrderList().execute();

        dialog1 = new Dialog(My_Orders.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);

     /*   viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
*/

    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(My_Orders.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(My_Orders.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<FeederInfo_orderhistory>();
            return postJsonObject(EndUrl.OrderHistory_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    mAdapterFeeds = new Adapter_orderhistory(My_Orders.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                } else if (status.equals("error")) {

                    allItems1.clear();

                    mAdapterFeeds = new Adapter_orderhistory(My_Orders.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(My_Orders.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(My_Orders.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();

        try {

            jobj.put(EndUrl.OrderHistory_customer_id,UserId);


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


                order_date = post.getString("order_date");
                ordersInfo = post.getString("ordersInfo");


                JSONArray responcearray2 = new JSONArray(ordersInfo);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);


                    FeederInfo_orderhistory item = new FeederInfo_orderhistory();

                    // item.s(post.optString("id"));

                    item.setDeliverystatus(post2.optString("status"));
                    item.setOrderdate(post2.optString("order_date"));
                    item.setOrderid(post2.optString("order_id"));
                    item.setOrderID(post2.optString("orderid"));
                    item.setTotalprice(post2.optString("total_amount"));
                    item.setShippingadress(post2.optString("shipping_address"));
                    item.setPaymentmode(post2.optString("payment_mode"));

                    allItems1.add(item);

                }
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

    @Override
    public void onClickedItem(int pos, String qty, int status) {
        Log.e("testing", "Pos:"+ pos + "Qty:"+qty);
        Log.e("testing","status  = "+status);
        Log.e("testing","title inm  = "+allItems1.get(pos).getOrderID());

        orderitemid = allItems1.get(pos).getOrderID();

        dialog1.setContentView(R.layout.customdialogbox);
        final EditText editreason = (EditText) dialog1.findViewById(R.id.editreason);
        Button butsubmit = (Button) dialog1.findViewById(R.id.butsubmit);
        ImageView imgcancel = (ImageView) dialog1.findViewById(R.id.imgcancel);

        butsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editreason.getText().toString() == null || editreason.getText().toString().length() == 0){
                    Toast.makeText(My_Orders.this, "Enter Reason", Toast.LENGTH_SHORT).show();
                }else{

                    strreason = editreason.getText().toString();

                    new OrderCancel().execute();
                }
            }
        });
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        dialog1.show();

        //  new CartUpdate().execute();

    }


    class DeleteItem extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(My_Orders.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(My_Orders.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.OrderDelete_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();
                dialog1.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    dialog.dismiss();
                    Toast.makeText(My_Orders.this,"Your Order has been Cancelled Successfully" , Toast.LENGTH_LONG).show();
                    new Loader().execute();

                } else{

                    Toast.makeText(My_Orders.this, message, Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(My_Orders.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }



    public JSONObject makingJson2() {


        JSONObject object = new JSONObject();

        try {


            object.put(EndUrl.OrderDelete_Userid,UserId);
            object.put(EndUrl.OrderDelete_Orderid,orderitemid);
            object.put(EndUrl.OrderDelete_Reason,strreason);
            object.put(EndUrl.OrderDelete_Mobileno,Usermob);


            Log.e("testing",object.toString());
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



    class LoadOrderList extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(My_Orders.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 = new ArrayList<FeederInfo_orderhistory>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetOrderList_user_id, UserId));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetOrderList_URL, "GET", userpramas);

            Log.e("testing", "userpramas result = " + userpramas);
            Log.e("testing", "json result = " + json);

            if (json == null) {

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
                        String arrayresponse = json.getString("data");
                        Log.e("testing", "adapter value=" + arrayresponse);




                        JSONArray responcearray = new JSONArray(arrayresponse);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();


                            FeederInfo_orderhistory item = new FeederInfo_orderhistory();

                            // item.s(post.optString("id"));

                            item.setDeliverystatus(post.optString("order_status"));
                            item.setOrderdate(post.optString("order_date"));
                            item.setOrderid(post.optString("order_number"));
                            item.setOrderID(post.optString("id"));
                            item.setTotalprice(post.optString("grand_total"));
                            // item.setShippingadress(post.optString("shipping_address"));
                            item.setPaymentmode(post.optString("payment_mode"));

                            allItems1.add(item);





                        }
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;


        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            //  progressbarloading.setVisibility(View.GONE);
            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {
                mAdapterFeeds = new Adapter_orderhistory(My_Orders.this, allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);






            }
            else {


                mAdapterFeeds = new Adapter_orderhistory(My_Orders.this, allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);





            }



        }

    }



    class OrderCancel extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(My_Orders.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 = new ArrayList<FeederInfo_orderhistory>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetOrderCancel_order_id, orderitemid));
           // userpramas.add(new BasicNameValuePair(EndUrl.GetOrderCancel_user_id, UserId));
         //   userpramas.add(new BasicNameValuePair(EndUrl.GetOrderCancel_order_status, "canceled"));
          //  userpramas.add(new BasicNameValuePair(EndUrl.GetOrderCancel_order_status_id, "7"));
            userpramas.add(new BasicNameValuePair(EndUrl.GetOrderCancel_reason, strreason));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetOrderCancel_URL, "POST", userpramas);

            Log.e("testing", "userpramas result = " + userpramas);
            Log.e("testing", "json result = " + json);

            if (json == null) {

            } else {
                Log.e("testing", "jon2222222222222");
                try {


                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;


        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            //  progressbarloading.setVisibility(View.GONE);
            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {


                dialog1.dismiss();
                new LoadOrderList().execute();


            }
            else {




            }



        }

    }


}
