package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import g2evolution.Boutique.Adapter.Adapter_SearchProducts;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_SearchProducts;

public class Activity_SearchProducts extends AppCompatActivity implements Adapter_SearchProducts.OnItemClickcourses {

    JSONParser jsonParser = new JSONParser();

    Adapter_SearchProducts courses_Adapter;
    Adapter_SearchProducts.OnItemClickcourses mCallback2;

    RecyclerView recyclercoursesoffered;
    String searchtext = "";

    SearchView search;


    private ArrayList<Entity_SearchProducts> allItemscourses = new ArrayList<Entity_SearchProducts>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_products);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        search = (SearchView) findViewById(R.id.search);

        recyclercoursesoffered = (RecyclerView)findViewById(R.id.rv_pending);
        recyclercoursesoffered.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(Activity_SearchProducts.this, LinearLayoutManager.VERTICAL, false);
        recyclercoursesoffered.setLayoutManager(mLayoutManager2);
        mCallback2 = this;

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Do whatever you need. This will be fired only when submitting.
                Log.e("testing","onsubmit");
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Do whatever you need when text changes.
                // This will be fired every time you input any character.
                Log.e("testing","onchange = "+newText);;
                searchtext = newText;
                new LoadProducts().execute();
                return false;
            }
        });


        // setUpRecyler();

        new LoadProducts().execute();

    }


    @Override
    public void OnItemClickcourses(int pos) {


    }

    public class LoadProducts extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        String jsontotal_retailers, jsoncolleted_retailers, jsonpending_retailes, jsontotal_amount_collected, jsontotal_amount_pending, jsontoday_pending_amount, jsontotal_business;

        private ProgressDialog pDialog;
        Integer intcartcount = 0;
        String validuser_id, validuser_name, validuser_email, validuserphone;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_SearchProducts.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        public String doInBackground(String... args) {
            allItemscourses =new ArrayList<Entity_SearchProducts>();
            //  product_details_lists = new ArrayList<Product_list>();

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            //  String paramsheader = "Bearer "+strregisteredtoken;


            userpramas.add(new BasicNameValuePair(EndUrl.GetProducts_search_param, searchtext));

            //  Log.e("testing", "paramsheader = " + paramsheader);
            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.GetProductst_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "GET", userpramas);

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


                            Entity_SearchProducts item = new Entity_SearchProducts();

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


                            allItemscourses.add(item);







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


            pDialog.dismiss();

            if (status == null || status.trim().length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {



                courses_Adapter = new Adapter_SearchProducts(Activity_SearchProducts.this,allItemscourses, mCallback2);
                recyclercoursesoffered.setAdapter(courses_Adapter);


            }
            else {
                Toast.makeText(Activity_SearchProducts.this, strmessage, Toast.LENGTH_SHORT).show();

                courses_Adapter = new Adapter_SearchProducts(Activity_SearchProducts.this,allItemscourses, mCallback2);
                recyclercoursesoffered.setAdapter(courses_Adapter);
            }



        }



    }


}
