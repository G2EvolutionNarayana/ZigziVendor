package g2evolution.Boutique.Activity;

import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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

import g2evolution.Boutique.Adapter.Adapter_cart;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_cart;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;


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

    String[]Name =new String[]{"Maggi","Maggi","Maggi"};
    String[]details =new String[]{"(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle","(Kachi Ghani),1 ltr Bottle"};
    String[]price =new String[]{"50.00","50.00","50.00"};
    String[]amount =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.maggi, R.drawable.maggi, R.drawable.maggi};

    TextView continueshop,checkout;

    TextView textsubtotal,textshipping,texttax,textfinaltotal,coupon_text,textsubtotal_tax,wallet_text;
    Button apply_button;
    String strcoupon_edit_text;
    String strsubtotal,strshipping,strtax,strfinaltotal;

    JSONParser jsonParser = new JSONParser();

    ImageView back;

    String userid,pid,shname,shemailid,shmobileno;

    String status,message,products;

    String stractualprice, strdiscountprice, strfinalprice;

    private String TAG = Activity_cart.class.getSimpleName();

    LinearLayout linear2;
    LinearLayout linerend;

    String strquantity, strcartid,strprodcutid, strprodcutsize, strbuttonstatus;

    ImageView empty_crt;

    Integer intcartcount = null;

    TextView text;
    ImageView cartImage,wishlistImage,searchImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);





        //setCartLayout();
        mCallback=this;
        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);

        text = (TextView) findViewById(R.id.text);
        cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);
        text.setText("Shopping Cart");
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
     /*   SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
        userid = prefuserdata.getString("userid","");
        pid = prefuserdata.getString("productid","");
        strbuttonstatus = prefuserdata.getString("addtocart","");*/

        Log.e("testing","userid=after====="+userid);

        SharedPreferences prefuserdata1 =  getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Log.e("testing","userid======"+userid);


        checkout = (TextView)findViewById(R.id.checkout);
        textsubtotal = (TextView)findViewById(R.id.textsubtotal);
        textsubtotal_tax = (TextView)findViewById(R.id.textsubtotal_tax);
        textshipping = (TextView)findViewById(R.id.textshipping);
        //  texttax = (TextView)findViewById(R.id.texttax);
        textfinaltotal = (TextView)findViewById(R.id.textfinaltotal);
        back = (ImageView)findViewById(R.id.back);

        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linerend = (LinearLayout) findViewById(R.id.linerend);
        linear2.setVisibility(View.GONE);
        linerend.setVisibility(View.GONE);


        cartImage.setVisibility(View.GONE);
        wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Activity_cart.this,Activity_WishList.class);
                startActivity(intent);

            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Activity_cart.this,Activity_search.class);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Intent intent = new Intent(Activity_cart.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
                finish();
            }
        });

        continueshop = (TextView) findViewById(R.id.continueshop);
        coupon_text = (TextView) findViewById(R.id.coupon_text);
        wallet_text = (TextView) findViewById(R.id.wallet_text);
        continueshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        coupon_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog logoutdialog = new Dialog(Activity_cart.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutdialog.setCanceledOnTouchOutside(true);
                logoutdialog.setCancelable(true);
                LayoutInflater inflater = (LayoutInflater) Activity_cart.this.getSystemService(Activity_cart.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.coupon_custom_layout, null);
                //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logoutdialog.setContentView(convertView);
                // LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logoutdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logoutdialog.getWindow().setAttributes(lp);


                final EditText coupon_edit_text = (EditText) logoutdialog.findViewById(R.id.coupon_edit_text);
                apply_button = (Button) logoutdialog.findViewById(R.id.apply_button);



                apply_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strcoupon_edit_text = coupon_edit_text.getText().toString();

                        if (strcoupon_edit_text == null || strcoupon_edit_text.trim().length() == 0 || strcoupon_edit_text.trim().equals("null")){

                            Toast.makeText(Activity_cart.this, "Please Enter Coupon code", Toast.LENGTH_SHORT).show();

                        }else{

                            new CheckCoupon().execute();

                        }
                    }
                });


                logoutdialog.show();
            }
        });

        wallet_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog logoutdialog = new Dialog(Activity_cart.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutdialog.setCanceledOnTouchOutside(true);
                logoutdialog.setCancelable(true);
                LayoutInflater inflater = (LayoutInflater) Activity_cart.this.getSystemService(Activity_cart.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.credit_layout, null);
                //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logoutdialog.setContentView(convertView);
                // LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logoutdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logoutdialog.getWindow().setAttributes(lp);
                logoutdialog.show();
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

                if(intcartcount == null || intcartcount == 0){

                    Toast.makeText(Activity_cart.this, "Cart Amount is Empty", Toast.LENGTH_LONG).show();


                }else {

                    Intent intent = new Intent(Activity_cart.this,Activity_address.class);

                    SharedPreferences prefuserdata2 = getSharedPreferences("regcart", 0);
                    SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
                    prefeditor2.putString("bookstatus", "" + "addtocart");

                    prefeditor2.commit();


                    SharedPreferences prefuserdata = getSharedPreferences("pa+y", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("Apay", "" + strfinaltotal);

                    prefeditor.commit();

                    SharedPreferences prefuserdata3 = getSharedPreferences("cartpaymentdetails", 0);
                    SharedPreferences.Editor prefeditor3 = prefuserdata3.edit();
                    prefeditor3.putString("stractualprice", "" + stractualprice);
                    prefeditor3.putString("strdiscountprice", "" + strdiscountprice);
                    prefeditor3.putString("strfinalprice", "" + strfinalprice);

                    prefeditor3.commit();

                    startActivity(intent);


                    startActivity(intent);

                }

            }
        });


        // setUpRecycler();
        new LoadProductList().execute();


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
        strprodcutid = allItems1.get(pos).getProductid();
        strprodcutsize = allItems1.get(pos).getStrsize();



        if (status == 3){

            new CartDelete().execute();

        }else{
            new UpdateCart().execute();
        }
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
                    linear2.setVisibility(View.GONE);
                    linerend.setVisibility(View.VISIBLE);

                    textsubtotal.setText(strrs+" "+strsubtotal);
                    textsubtotal_tax.setText(strrs+" "+"120");

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

                }


            } else if (status.equals("fail")){


                //   Toast.makeText(Activity_cart.this, ""+message, Toast.LENGTH_SHORT).show();

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

    class LoadProductList extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_cart.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 =new ArrayList<FeederInfo_cart>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.CartList_user_id, userid));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.CartList_URL, "POST", userpramas);

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
                        intcartcount = responcearray.length();
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);

                            stractualprice = post.getString("actual_price");
                            strdiscountprice = post.getString("discount_price");
                            strfinalprice = post.getString("final_price");
                            String arrayresponseproducts = post.getString("products");
                            Log.e("testing", "adapter value=" + arrayresponseproducts);
                            JSONArray responcearrayproducts = new JSONArray(arrayresponseproducts);
                            Log.e("testing", "responcearrayproducts value=" + responcearrayproducts);

                            for (int i1 = 0; i1 < responcearrayproducts.length(); i1++) {

                                JSONObject postproducts = responcearrayproducts.getJSONObject(i1);

                                FeederInfo_cart item = new FeederInfo_cart();

                                item.setId(postproducts.optString("cart_id"));
                                item.setProductid(postproducts.optString("variant_id"));
                                //  item.setStrsize(postproducts.optString("size"));
                                item.setCartimage(postproducts.optString("image"));
                                item.setCartname(postproducts.optString("name"));
                              //  item.setCartprodetails(postproducts.optString("sku"));
                                item.setCartamount(postproducts.optString("actual_price"));



                                if (postproducts.has("quantity")){
                                    item.setCartquantity(postproducts.optString("quantity"));
                                }else{

                                }


                                if (postproducts.has("discount_price")){
                                    item.setDiscountvalue(postproducts.optString("discount_price"));
                                }else{

                                }
                                if (postproducts.has("final_price")){
                                    item.setAfterdiscount(postproducts.optString("final_price"));
                                }else{

                                }

                                if (postproducts.has("total_price")){
                                    item.setCarttotalamount(postproducts.optString("total_price"));
                                }else{

                                }


                                allItems1.add(item);

                            }









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
                Log.e("testing123", "allItems1===" + allItems1);

                final String strrs = getResources().getString(R.string.Rs);
                linear2.setVisibility(View.GONE);
                linerend.setVisibility(View.VISIBLE);

                textsubtotal.setText(strrs+" "+strsubtotal);
                textsubtotal_tax.setText(strrs+" "+"120");

                textshipping.setText(strrs+" "+strshipping);
                //    texttax.setText(strtax);
                textfinaltotal.setText(strrs+" "+strfinaltotal);

                mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);



            }
            else {

                empty_crt.setVisibility(View.VISIBLE);
                allItems1.clear();

                mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);




            }



        }

    }
    class CartDelete extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_cart.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.CartDelete_id, strcartid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.CartDelete_URL;
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
                  /*  if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");

                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        str_user_id = jsonobjectdata.getString("user_id");
                        Log.e("testing","userid - "+str_user_id);



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
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

/*
                Intent intent = new Intent(Activity_WishList.this, MainActivity.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);*/

                new LoadProductList().execute();

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }


    class UpdateCart extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_cart.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_product_id, strprodcutid));
         //   userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_product_name, strprodcutsize));
            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_quantity, strquantity));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.PostCartUpdate_URL;
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
                  /*  if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");

                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        str_user_id = jsonobjectdata.getString("user_id");
                        Log.e("testing","userid - "+str_user_id);



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
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

/*
                Intent intent = new Intent(Activity_WishList.this, MainActivity.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);*/

                new LoadProductList().execute();

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }
    class CheckCoupon extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        String coupon_id, coupon_price;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_cart.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.CheckCoupon_coupon_code, strcoupon_edit_text));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.CheckCoupon_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "GET", userpramas);


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
                    if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");

                        JSONObject  jsonobjectdata = new JSONObject(strdata);
                        coupon_id = jsonobjectdata.getString("id");
                        coupon_price = jsonobjectdata.getString("coupon_price");



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

/*
                Intent intent = new Intent(Activity_WishList.this, MainActivity.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);*/


                if(intcartcount == null || intcartcount == 0){

                    Toast.makeText(Activity_cart.this, "Cart Amount is Empty", Toast.LENGTH_LONG).show();


                }else {

                    Intent intent = new Intent(Activity_cart.this,Activity_address.class);

                    SharedPreferences prefuserdata2 = getSharedPreferences("regcart", 0);
                    SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
                    prefeditor2.putString("bookstatus", "" + "addtocart");
                    prefeditor2.putString("coupon_id", "" + coupon_id);
                    prefeditor2.putString("coupon_price", "" + coupon_price);

                    prefeditor2.commit();


                    SharedPreferences prefuserdata = getSharedPreferences("pa+y", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("Apay", "" + strfinaltotal);

                    prefeditor.commit();
                    startActivity(intent);


                    startActivity(intent);

                }


              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
            }


        }

    }



}
