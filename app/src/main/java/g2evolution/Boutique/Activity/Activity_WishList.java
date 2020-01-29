package g2evolution.Boutique.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import g2evolution.Boutique.Adapter.Adapter_Whishlist_List;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.FeederInfo_Whishlist_List;


public class Activity_WishList extends AppCompatActivity implements Adapter_Whishlist_List.OnItemClick{

    GridLayoutManager lLayout1;
    String status,message,products;

    private ArrayList<FeederInfo_Whishlist_List> allItems3 = new ArrayList<FeederInfo_Whishlist_List>();
    private RecyclerView mFeedRecyler2;
    Adapter_Whishlist_List mAdapterFeeds2;

    Adapter_Whishlist_List.OnItemClick mCallback;
    TextView text_wishlist;
    ImageView back;

    String strwhishlistid;

    JSONParser jsonParser = new JSONParser();

    String viewUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
        viewUserId = prefuserdata23.getString("UserId", "");

        text_wishlist=(TextView)findViewById(R.id.textview_title1);
        text_wishlist.setText("WishList");
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mCallback = this;
        mFeedRecyler2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mFeedRecyler2.setHasFixedSize(true);
        mFeedRecyler2.setLayoutManager(new GridLayoutManager(Activity_WishList.this,2));

        new LoadWhishList().execute();



    }

    @Override
    public void onClickedItem(int pos, String qty, int status) {


        strwhishlistid = qty;

        new PostWhislist().execute();


    }


    class PRODUCT_LIST extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_WishList.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_WishList.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems3.clear();
            allItems3 = new ArrayList<FeederInfo_Whishlist_List>();
            return postJsonObject2(EndUrl.GetCategoryChildProductList_URL, makingJson2());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    Log.e("testing", "result in post execute=========" + allItems3);

                    mAdapterFeeds2 = new Adapter_Whishlist_List(Activity_WishList.this, allItems3, mCallback);
                    //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.VERTICAL, true));
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                } else if (status.equals("fail")) {

                    allItems3.clear();

                    mAdapterFeeds2 = new Adapter_Whishlist_List(Activity_WishList.this, allItems3, mCallback);
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                    Toast.makeText(Activity_WishList.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(Activity_WishList.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Get_subcatId,"30");
            jobj.put(EndUrl.Get_childCatId,"All");


        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.e("testing","json object "+jobj);
        return jobj;


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
            String Totalrecord = json.getString("total_record");

            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);


            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                //   headers = post.getString("categoryName");
                products = post.getString("Products");


                JSONArray responcearray2 = new JSONArray(products);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);


                    FeederInfo_Whishlist_List item = new FeederInfo_Whishlist_List();

                    item.setId(post2.optString("productId"));
                    item.setCategoryname(post2.optString("categoryName"));
                    item.setElectronicname(post2.optString("title"));
                    item.setElectronicdetail1(post2.optString("subTitle"));
                    item.setElectronicprice(post2.optString("price"));
                    item.setElectronicimage(post2.optString("image"));
                    item.setStockQuantity(post2.optString("stockQuantity"));
                    item.setDiscountvalue(post2.optString("discountValue"));
                    item.setAfterdiscount(post2.optString("afterDiscount"));

                    Log.e("testing","productId in json = "+post2.optString("productId"));
                    Log.e("testing","title in json = "+post2.optString("title"));
                    Log.e("testing","price in json = "+post2.optString("price"));
                    Log.e("testing","image in json = "+post2.optString("image"));

                    allItems3.add(item);
                }

            }


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

    class LoadWhishList extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_WishList.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems3 =new ArrayList<FeederInfo_Whishlist_List>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetWhishlist_User_id, viewUserId));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetWhishlist_URL, "GET", userpramas);

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


                            FeederInfo_Whishlist_List item = new FeederInfo_Whishlist_List();

                            item.setId(post.optString("wish_list_id"));
                            item.setProductid(post.optString("wish_list_id"));
                            item.setCategoryname(post.optString("name"));
                            item.setElectronicname(post.optString("name"));
                          //  item.setElectronicdetail1(post.optString("sku"));
                            item.setElectronicimage(post.optString("image"));
                            item.setElectronicprice(post.optString("actual_price"));
                            //  item.setStockQuantity(post.optString("stockQuantity"));
                            item.setDiscountvalue(post.optString("discount_price"));
                            item.setAfterdiscount(post.optString("final_price"));


                            String strresponseparameters = post.getString("parameters");
                            JSONArray responcearrayparameters = new JSONArray(strresponseparameters);
                            Log.e("testing", "responcearray value=" + responcearrayparameters);

                            for (int i2 = 0; i2 < responcearrayparameters.length(); i2++) {
                                JSONObject postparameters = responcearrayparameters.getJSONObject(i2);


                                Map<String,String> map2 = new HashMap<String,String>();
                                Iterator iter = postparameters.keys();
                                while(iter.hasNext()){
                                    String key = (String)iter.next();
                                    String value = postparameters.getString(key);

                                    Log.e("testing","Key :" + key + "  Value :" + value);
                                    map2.put(key,value);
                                }

                                item.setMapparameters(map2);


                            }


                            allItems3.add(item);







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
                //  Log.e("testing123", "allItems1===" + allItems1);


/*
                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                 prodcuts_recycler.setLayoutManager(mLayoutManager);
                product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/

                mAdapterFeeds2 = new Adapter_Whishlist_List(Activity_WishList.this, allItems3, mCallback);
                //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.VERTICAL, true));
                mFeedRecyler2.setAdapter(mAdapterFeeds2);





            }
            else {
                mAdapterFeeds2 = new Adapter_Whishlist_List(Activity_WishList.this, allItems3, mCallback);
                //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.VERTICAL, true));
                mFeedRecyler2.setAdapter(mAdapterFeeds2);

            /*    product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/




            }



        }

    }
    class PostWhislist extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_WishList.this);
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



            userpramas.add(new BasicNameValuePair(EndUrl.DeleteWhishlist_User_id, viewUserId));
            userpramas.add(new BasicNameValuePair(EndUrl.DeleteWhishlist_id, strwhishlistid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.DeleteWhishlist_URL;
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

                new LoadWhishList().execute();

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_WishList.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }


}
