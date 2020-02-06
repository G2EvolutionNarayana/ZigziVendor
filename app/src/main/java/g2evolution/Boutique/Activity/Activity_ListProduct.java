package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

import g2evolution.Boutique.Adapter.Adapter_List_Product;
import g2evolution.Boutique.Adapter.Adapter_Subcategory_list;
import g2evolution.Boutique.Adapter.Adapter_child_subcategory_list;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_Subcategory_list;
import g2evolution.Boutique.FeederInfo.FeederInfo_child_subcategory_list;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.FeederInfo_List_Product;


public class Activity_ListProduct extends AppCompatActivity {

    //testing comments
    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_Subcategory_list> allItems1 = new ArrayList<FeederInfo_Subcategory_list>();
    private RecyclerView mFeedRecyler;
    Adapter_Subcategory_list mAdapterFeeds;

    GridLayoutManager lLayout;
    private Adapter_Subcategory_list.OnItemClick mCallback;

    private Adapter_child_subcategory_list.OnItemClick1 mCallback1;

    String status,message,products;

    private ArrayList<FeederInfo_child_subcategory_list> allItems2 = new ArrayList<FeederInfo_child_subcategory_list>();
    private RecyclerView mFeedRecyler1;
    Adapter_child_subcategory_list mAdapterFeeds1;

    GridLayoutManager lLayout1;

    private ArrayList<FeederInfo_List_Product> allItems3 = new ArrayList<FeederInfo_List_Product>();
    private RecyclerView mFeedRecyler2;
    Adapter_List_Product mAdapterFeeds2;

    GridLayoutManager lLayout2;
    String catid, categoryname_heading;
    ImageView back;
    LinearLayout filter,sort;
    Integer introwposition;
    TextView text;
    ImageView cartImage,wishlistImage,searchImage;


    JSONParser jsonParser = new JSONParser();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);

        SharedPreferences prefuserdata = getSharedPreferences("ProDetails", 0);
        catid = prefuserdata.getString("Proid", "");
        categoryname_heading = prefuserdata.getString("categoryName", "");



        back=(ImageView)findViewById(R.id.back);
        filter=(LinearLayout)findViewById(R.id.filter);
        sort=(LinearLayout)findViewById(R.id.sort);
        text = (TextView) findViewById(R.id.text);

        text.setText(categoryname_heading);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setHasFixedSize(true);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_ListProduct.this));
        mFeedRecyler.setLayoutManager(lLayout);



        cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Activity_ListProduct.this,Activity_cart.class);
                startActivity(intent);

            }
        });

        wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent intent =new Intent(Activity_ListProduct.this,Activity_WishList.class);
                startActivity(intent);*/

            }
        });
        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Activity_ListProduct.this,Activity_search.class);
                startActivity(intent);

            }
        });

        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty = getResources().getString(R.string.whatsappnumber);
                String toNumber = "+91"+qty; // Replace with mobile phone number without +Sign or leading zeros.
                String text = "You are requesting chat from Product List page please continue chatting...";// Replace with your message.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                startActivity(intent);



            }
        });



        new LoadProductList().execute();


        mFeedRecyler1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mFeedRecyler1.setHasFixedSize(true);
        //  mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_ListProduct.this));
        mFeedRecyler1.setLayoutManager(lLayout1);


        mFeedRecyler2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mFeedRecyler2.setHasFixedSize(true);
        mFeedRecyler2.setLayoutManager(new GridLayoutManager(Activity_ListProduct.this,2));

        // mFeedRecyler2.setLayoutManager(lLayout2);

        //   new Child_SubCategory_List().execute();
        //   new PRODUCT_LIST().execute();
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog logoutdialog = new Dialog(Activity_ListProduct.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutdialog.setCancelable(true);
                logoutdialog.setCanceledOnTouchOutside(true);
                LayoutInflater inflater = (LayoutInflater) Activity_ListProduct.this.getSystemService(Activity_ListProduct.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.sort_custom_layout, null);
                //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logoutdialog.setContentView(convertView);

                // LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logoutdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.BOTTOM;
                logoutdialog.getWindow().setAttributes(lp);
                logoutdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                ImageView imgcancel = (ImageView) convertView.findViewById(R.id.imgcancel);
                imgcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutdialog.dismiss();
                    }
                });

                logoutdialog.show();

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* final Dialog logoutdialog = new Dialog(Activity_ListProduct.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutdialog.setCancelable(true);
                logoutdialog.setCanceledOnTouchOutside(true);
                LayoutInflater inflater = (LayoutInflater) Activity_ListProduct.this.getSystemService(Activity_ListProduct.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.filter_layout, null);
                //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logoutdialog.setContentView(convertView);

                // LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);

                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logoutdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                lp.gravity = Gravity.BOTTOM;
                logoutdialog.getWindow().setAttributes(lp);
                logoutdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


                ImageView imgcancel = (ImageView) convertView.findViewById(R.id.back);
                imgcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutdialog.dismiss();
                    }
                });

                lvCategory = convertView.findViewById(R.id.lvCategory);
                new Filterdata().execute();

                logoutdialog.show();*/




            }
        });

    }







    class PRODUCT_LIST extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_ListProduct.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_ListProduct.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems3.clear();
            allItems3 = new ArrayList<FeederInfo_List_Product>();
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

                    mAdapterFeeds2 = new Adapter_List_Product(Activity_ListProduct.this, allItems3);
                    //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Activity_ListProduct.this, LinearLayoutManager.VERTICAL, true));
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                } else if (status.equals("fail")) {

                    allItems3.clear();

                    mAdapterFeeds2 = new Adapter_List_Product(Activity_ListProduct.this, allItems3);
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                    Toast.makeText(Activity_ListProduct.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(Activity_ListProduct.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject jobj = new JSONObject();
     /*   // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Get_subcatId,subcategoryname);
            jobj.put(EndUrl.Get_childCatId,childid);


        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.e("testing","json object "+jobj);*/
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


                    FeederInfo_List_Product item = new FeederInfo_List_Product();

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
            pDialog = new ProgressDialog(Activity_ListProduct.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems3 =new ArrayList<FeederInfo_List_Product>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetProducts_id, catid));
          //  userpramas.add(new BasicNameValuePair(EndUrl.GetProducts_id, "6"));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetProductst_URL, "GET", userpramas);

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


                            FeederInfo_List_Product item = new FeederInfo_List_Product();

                           // item.setId(post.optString("product_id"));
                            item.setCategoryname(post.optString("name"));
                            item.setElectronicname(post.optString("name"));
                          //  item.setElectronicdetail1(post.optString("sku"));

                            item.setElectronicimage(post.optString("image"));
                            //  item.setStockQuantity(post.optString("stockQuantity"));


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




                            if (post.has("actual_price")){
                                item.setElectronicprice(post.optString("actual_price"));
                            }else{

                            }
                            if (post.has("offer_value")){
                                item.setDiscountvalue(post.optString("offer_value"));
                            }else{

                            }
                            if (post.has("discount_price")){
                                item.setAfterdiscount(post.optString("discount_price"));
                            }else{

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
                Log.e("testing123", "allItems1===" + allItems1);


/*
                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                 prodcuts_recycler.setLayoutManager(mLayoutManager);
                product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/

                mAdapterFeeds2 = new Adapter_List_Product(Activity_ListProduct.this, allItems3);
                //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Activity_ListProduct.this, LinearLayoutManager.VERTICAL, true));
                mFeedRecyler2.setAdapter(mAdapterFeeds2);





            }
            else {
                mAdapterFeeds2 = new Adapter_List_Product(Activity_ListProduct.this, allItems3);
                //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Activity_ListProduct.this, LinearLayoutManager.VERTICAL, true));
                mFeedRecyler2.setAdapter(mAdapterFeeds2);

            /*    product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/




            }



        }

    }


}
