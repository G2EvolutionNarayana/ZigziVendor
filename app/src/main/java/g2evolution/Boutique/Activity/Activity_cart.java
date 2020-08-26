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
import g2evolution.Boutique.Utility.Utils;


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

    String[] Name = new String[]{"Maggi", "Maggi", "Maggi"};
    String[] details = new String[]{"(Kachi Ghani),1 ltr Bottle", "(Kachi Ghani),1 ltr Bottle", "(Kachi Ghani),1 ltr Bottle"};
    String[] price = new String[]{"50.00", "50.00", "50.00"};
    String[] amount = new String[]{"50.00", "50.00", "50.00"};
    Integer[] Image = {R.drawable.maggi, R.drawable.maggi, R.drawable.maggi};


    TextView textsubtotal, textshipping, texttax, textfinaltotal, coupon_text, textsubtotal_tax, wallet_text,continueshop, checkout,text;
    Button apply_button;
    LinearLayout xLinlayCartdeatls,linerend;
    String strcoupon_edit_text,strsubtotal, strshipping, strtax, strfinaltotal,stractualprice, strdiscountprice, strfinalprice,userid, pid, shname, shemailid, shmobileno,status, message, products,strquantity, strcartid, strprodcutid, strprodcutsize, strbuttonstatus, strPrice;

    JSONParser jsonParser = new JSONParser();

    ImageView back,empty_crt, cartImage, wishlistImage, searchImage;

    private String TAG = Activity_cart.class.getSimpleName();

    Integer intcartcount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        //setCartLayout();
        mCallback = this;
        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);

        text = (TextView) findViewById(R.id.text);
        cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);
        text.setText("My Cart");
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_cart.this));

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);

        mFeedRecyler.setHasFixedSize(true);


        empty_crt = (ImageView) findViewById(R.id.empty_crt);

        Log.e("testing", "userid=after=====" + userid);

        SharedPreferences prefuserdata1 = getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Log.e("testing", "userid======" + userid);


        checkout = (TextView) findViewById(R.id.checkout);
        textsubtotal = (TextView) findViewById(R.id.textsubtotal);
        textshipping = (TextView) findViewById(R.id.textshipping);
        //  texttax = (TextView)findViewById(R.id.texttax);
        textfinaltotal = (TextView) findViewById(R.id.textfinaltotal);
        back = (ImageView) findViewById(R.id.back);

        linerend = (LinearLayout) findViewById(R.id.linerend);

        xLinlayCartdeatls= (LinearLayout) findViewById(R.id.xLinlayCartdeatls);
        linerend.setVisibility(View.GONE);

        cartImage.setVisibility(View.GONE);
        wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_cart.this, Activity_WishList.class);
                startActivity(intent);

            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_cart.this, Activity_search.class);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.putExtra("CartCount", String.valueOf(Utils.Cart_Count));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (intcartcount == null || intcartcount == 0) {

                    Toast.makeText(Activity_cart.this, "Cart list is Empty", Toast.LENGTH_LONG).show();


                } else {

                    Intent intent = new Intent(Activity_cart.this, Activity_address.class);

                    SharedPreferences prefuserdata2 = getSharedPreferences("regcart", 0);
                    SharedPreferences.Editor prefeditor2 = prefuserdata2.edit();
                    prefeditor2.putString("bookstatus", "" + "addtocart");

                    prefeditor2.commit();

                    SharedPreferences sharedPreferencesData = getSharedPreferences("PriceDeatils", 0);
                    SharedPreferences.Editor sharedEditor = sharedPreferencesData.edit();
                    sharedEditor.putString("price", "" + stractualprice);
                    sharedEditor.putString("discount_price", "" + strdiscountprice);
                    sharedEditor.putString("total_price", "" + strfinalprice);
                    sharedEditor.commit();


                    startActivity(intent);
                }

            }
        });


        // setUpRecycler();
        new LoadProductList().execute();


    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
        intent.putExtra("CartCount", String.valueOf(Utils.Cart_Count));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onClickedItem(int pos, int qty, int status) {
        Log.e("DMen", "Pos:" + pos + "Qty:" + qty);
        Log.e("testing", "status  = " + status);
        Log.e("testing", "title inm  = " + allItems1.get(pos).getId());

        strquantity = String.valueOf(qty);
        strPrice = allItems1.get(pos).getCartamount().toString();
        strcartid = allItems1.get(pos).getId();
        strprodcutid = allItems1.get(pos).getProductid();
        strprodcutsize = allItems1.get(pos).getStrsize();
        strfinalprice=allItems1.get(pos).getAfterdiscount();

        if (status == 3) {

            new CartDelete().execute();

        } else {
            new UpdateCart().execute();
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

        Log.e("testing", "json object " + jobj);
        return jobj;

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
        }

        public String doInBackground(String... args) {


            allItems1 = new ArrayList<FeederInfo_cart>();
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
                    JSONObject jsonobject = new JSONObject(strresponse);
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

                        Log.e("testing",  json.getString("data"));


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


                                if (postproducts.has("quantity")) {
                                    item.setCartquantity(postproducts.optString("quantity"));
                                } else {

                                }


                                if (postproducts.has("discount_price")) {
                                    item.setDiscountvalue(postproducts.optString("discount_price"));
                                } else {

                                }
                                if (postproducts.has("final_price")) {
                                    item.setAfterdiscount(postproducts.optString("final_price"));
                                } else {

                                }

                                if (postproducts.has("total_price")) {
                                    item.setCarttotalamount(postproducts.optString("total_price"));
                                } else {

                                }


                                allItems1.add(item);

                            }


                        }
                    } else {

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
            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")) {

            } else if (status.equals("success")) {
                Log.e("testing123", "allItems1===" + allItems1);

                final String strrs = getResources().getString(R.string.Rs);

                textsubtotal.setText(strrs + " " + stractualprice);

                textshipping.setText(strrs + " " + strdiscountprice);
                //    texttax.setText(strtax);
                textfinaltotal.setText(strrs + " " + strfinalprice);
                xLinlayCartdeatls.setVisibility(View.VISIBLE);
                empty_crt.setVisibility(View.GONE);
                mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            } else {
                xLinlayCartdeatls.setVisibility(View.GONE);
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
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);


        }

        public String doInBackground(String... args) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            userpramas.add(new BasicNameValuePair(EndUrl.CartDelete_id, strcartid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.CartDelete_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {

                    for (int i = 0; i < allItems1.size(); i++) {
                        final FeederInfo_cart feederInfo = allItems1.get(i);

                        if (feederInfo.getId().equals(strcartid)) {
                            allItems1.remove(i);
                        }
                    }


                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");

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
            Log.e("testing", "status = " + status);
            Log.e("testing", "strresponse = " + strresponse);
            Log.e("testing", "strmessage = " + strmessage);

            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {
                mAdapterFeeds.notifyDataSetChanged();
                Utils.Cart_Count = Utils.Cart_Count - 1;
                new LoadProductList().execute();

                if ( Utils.Cart_Count==0){
                    xLinlayCartdeatls.setVisibility(View.GONE);
                    empty_crt.setVisibility(View.VISIBLE);
                    allItems1.clear();
                    textfinaltotal.setText("");
//                    mAdapterFeeds = new Adapter_cart(Activity_cart.this, allItems1, mCallback);
//                    mFeedRecyler.setAdapter(mAdapterFeeds);
                }

            } else if (status.equals("failure")) {
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
            } else {

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
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

        }

        public String doInBackground(String... args) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_cart_id, strcartid));
            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_quantity, strquantity));
            userpramas.add(new BasicNameValuePair(EndUrl.PostCartUpdate_price, strPrice));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_type, "2"));
            userpramas.add(new BasicNameValuePair(EndUrl.Final_Price, strfinalprice));
//            userpramas.add(new BasicNameValuePair(EndUrl.AddToCartProduct_id, strprodcutid));

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
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");

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
            Log.e("testing", "status = " + status);
            Log.e("testing", "strresponse = " + strresponse);
            Log.e("testing", "strmessage = " + strmessage);

            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {

                new LoadProductList().execute();

            } else if (status.equals("failure")) {
                Toast.makeText(Activity_cart.this, strmessage, Toast.LENGTH_SHORT).show();
            } else {

            }


        }

    }

}
