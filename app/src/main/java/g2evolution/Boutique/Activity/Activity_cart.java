package g2evolution.Boutique.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.ArrayList;
import java.util.HashMap;

import g2evolution.Boutique.Adapter.Adapter_cart;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_cart;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;


public class Activity_cart extends AppCompatActivity implements Adapter_cart.OnItemClick {


    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_cart> allItems1 = new ArrayList<FeederInfo_cart>();
    Context context;
    private RecyclerView mFeedRecyler;
    private Activity_cart adapter;
    Adapter_cart mAdapterFeeds;
    RecyclerView rView;
    JSONArray responcearray2;
    private Adapter_cart.OnItemClick mCallback;

    String []Name =new String[]{"Maggi","Maggi","Maggi"};
    String []details =new String[]{"(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle"};
    String []price =new String[]{"50.00","50.00","50.00"};
    String []amount =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.maggi, R.drawable.maggi, R.drawable.maggi};

    TextView continueshop,checkout;

    TextView textsubtotal,textshipping,texttax,textfinaltotal;

    String strsubtotal,strshipping,strtax,strfinaltotal;

    ImageView back;

    String userid,pid,shname,shemailid,shmobileno;

    String status,message,products,message1;

    private String TAG = Activity_cart.class.getSimpleName();

    LinearLayout linear2;
    LinearLayout linerend;

    String strquantity, strcartid,strbuttonstatus,strquantity1;

    ImageView empty_crt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //setCartLayout();
        mCallback=this;
        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_cart.this));
        //setUpRecycler();
        // context = this;
      ///  lLayout = new GridLayoutManager(Activity_cart.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
       //  rView.setLayoutManager(lLayout);
      //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);


        empty_crt=(ImageView)findViewById(R.id.empty_crt);

        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
        userid = prefuserdata.getString("userid","");
        pid = prefuserdata.getString("productid","");
        strbuttonstatus = prefuserdata.getString("addtocart","");
        strquantity1 = prefuserdata.getString("quantity","");

        Log.e("testing","userid=after====="+userid);
        Log.e("testing","strquantity1===strquantity1====="+strquantity1);

        SharedPreferences prefuserdata1 =  getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Log.e("testing","userid======"+userid);


        checkout = (TextView)findViewById(R.id.checkout);
        textsubtotal = (TextView)findViewById(R.id.textsubtotal);
        textshipping = (TextView)findViewById(R.id.textshipping);
      //  texttax = (TextView)findViewById(R.id.texttax);
        textfinaltotal = (TextView)findViewById(R.id.textfinaltotal);
        back = (ImageView)findViewById(R.id.back);

        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linerend = (LinearLayout) findViewById(R.id.linerend);
        linear2.setVisibility(View.GONE);
        linerend.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Activity_cart.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        continueshop = (TextView) findViewById(R.id.continueshop);
        continueshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           onBackPressed();
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



          /*      JSONObject jobj3 = new JSONObject();
                // user_id = edt_mobileno.getText().toString();

                try {
                    JSONObject jobj = new JSONObject();
                    JSONObject jobj2 = new JSONObject();
                    jobj.put("status", "Pending");
                    jobj.put("Payment_mode", "COD");
                    jobj.put("total_amount", "2000");

                    jobj2.put("customer", "1");

                    jobj3.put("order", jobj);
                    jobj3.put("customer", jobj2);
                    jobj3.put("items", responcearray2);



                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.e("testing","jobj3 = "+jobj3);
*/

if(strfinaltotal == null || strfinaltotal.equals("")){


    Toast.makeText(Activity_cart.this, "Cart Amount is Empty", Toast.LENGTH_LONG).show();


}else {

    Intent intent = new Intent(Activity_cart.this,Activity_address.class);

    SharedPreferences prefuserdata2 = getSharedPreferences("regcart", 0);
    SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
    prefeditor2.putString("bookstatus", "" + "addtocart");

    prefeditor2.commit();


    SharedPreferences prefuserdata = getSharedPreferences("pay", 0);
    SharedPreferences.Editor prefeditor = prefuserdata.edit();
    prefeditor.putString("Apay", "" + strfinaltotal);

    prefeditor.commit();

    startActivity(intent);


}
            }
        });

        // setUpRecycler();
         new Loader().execute();

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Activity_cart.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClickedItem(int pos, int qty, int status) {
        Log.e("DMen", "Pos:"+ pos + "Qty:"+qty);
        Log.e("testing","status  = "+status);
        Log.e("testing","title inm  = "+allItems1.get(pos).getId());

        strquantity = String.valueOf(qty);
        strcartid = allItems1.get(pos).getId();
        new CartUpdate().execute();


    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_cart.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_cart.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<FeederInfo_cart>();
            return postJsonObject(EndUrl.GetuserCartDetails_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    final String strrs = getResources().getString(R.string.Rs);
                    linear2.setVisibility(View.VISIBLE);
                    linerend.setVisibility(View.VISIBLE);

                    textsubtotal.setText(strrs+" "+strsubtotal);

                    textshipping.setText(strrs+" "+strshipping);
                //    texttax.setText(strtax);
                    textfinaltotal.setText(strrs+" "+strfinaltotal);

                    mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                } else if (status.equals("fail")) {


                    empty_crt.setVisibility(View.VISIBLE);
                    allItems1.clear();

                    mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                 //   Toast.makeText(Activity_cart.this, "no data found", Toast.LENGTH_LONG).show();

                }else{


                }

            } else {

                Toast.makeText(Activity_cart.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetuserCartDetails_Id, userid);


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

                strsubtotal = post.getString("total");
                strshipping = post.getString("shipppingAmount");
              //  strtax = post.getString("tax");
                strfinaltotal = post.getString("subTotal");
                products = post.getString("products");


                responcearray2 = new JSONArray(products);
                Log.e("testing", "responcearray value=" + responcearray2);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);

                    FeederInfo_cart item = new FeederInfo_cart();

                    item.setId(post2.optString("productId"));
                    item.setCartimage(post2.optString("image"));
                    item.setCartname(post2.optString("brandName"));
                    item.setCartprodetails(post2.optString("title"));
                    item.setCartamount(post2.optString("price"));
                    item.setCarttotalamount(post2.optString("price"));
                    item.setCartquantity(post2.optString("quantity"));
                    item.setDiscountvalue(post2.optString("discountValue"));
                    item.setAfterdiscount(post2.optString("afterDiscount"));
                    item.setStockquntity(post2.optString("stockQuantity"));
                    item.setProducttotal(post2.optString("totalAmount"));


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


    class CartUpdate extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_cart.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_cart.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            // allItems = new ArrayList<SingleItemModel>();
            return postJsonObject1(EndUrl.CartUpdate_URL, makingJson1());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (result != null) {
                dialog.dismiss();
                /**
                 * Updating parsed JSON data into ListView
                 * */

                if (status.equals("success")) {
                    textsubtotal.setText(strsubtotal);
                    textshipping.setText(strshipping);
                    //   texttax.setText(strtax);
                    textfinaltotal.setText(strfinaltotal);
                    new Loader().execute();

                } else if (status.equals("fail")) {


                    Toast.makeText(Activity_cart.this, "" + message1, Toast.LENGTH_SHORT).show();

                }


            } else {



            }
        }

    }

    public JSONObject makingJson1() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            JSONObject jobj2 = new JSONObject();


            jobj2.put(EndUrl.CartUpdate_Userid, userid);
            jobj2.put(EndUrl.CartUpdate_Cartid, strcartid);
            jobj2.put(EndUrl.CartUpdate_Quantity, strquantity);
            jobj.put("qty", jobj2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object "+jobj);
        return jobj;

    }



    public JSONObject postJsonObject1(String url, JSONObject loginJobj) {
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
            message1 = json.getString("message");
            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);


            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                strsubtotal = post.getString("total");
                strshipping = post.getString("shipppingAmount");
                strtax = post.getString("tax");
                strfinaltotal = post.getString("subTotal");
                products = post.getString("products");


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString1(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


}
