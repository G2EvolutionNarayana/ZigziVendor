package g2evolution.Boutique.Activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
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

import g2evolution.Boutique.Adapter.Adapter_electronic;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_electronic;
import g2evolution.Boutique.R;

/**
 * Created by G2e Android on 06-02-2018.
 */



public class Activity_categories extends AppCompatActivity {


    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_electronic> allItems1 = new ArrayList<FeederInfo_electronic>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_electronic> mListFeederInfo;
    Adapter_electronic mAdapterFeeds;
    RecyclerView rView;

    GridLayoutManager lLayout;

    String []Name =new String[]{"Moto","Moto","Moto"};
    String []details =new String[]{"(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)","(Black,32 GB),(3GB RAM)"};
    String []details1 =new String[]{"(3GB RAM)","(3GB RAM)","(3GB RAM)"};
    String []price =new String[]{"50.00","50.00","50.00"};
    Integer[]Image={R.drawable.mob, R.drawable.mob, R.drawable.mob};

    String category,catid,subcategoryname;
    String status,message,headers,products;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_eletronic);


        SharedPreferences prefuserdata = this.getSharedPreferences("category", 0);
        category = prefuserdata.getString("category", "");
        catid = prefuserdata.getString("catid", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");



        SharedPreferences prefuserdata1 = this.getSharedPreferences("ProDetails", 0);
       // category = prefuserdata1.getString("category", "");
        catid = prefuserdata1.getString("catid", "");
        subcategoryname = prefuserdata1.getString("subcategoryname", "");


        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_categories.this));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(Activity_categories.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);
        mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);


        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });


        new Loader().execute();


    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_categories.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_categories.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1 = new ArrayList<FeederInfo_electronic>();
            return postJsonObject(EndUrl.GetCategorySubcategoryProduct_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    mAdapterFeeds = new Adapter_electronic(Activity_categories.this, allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                } else if (status.equals("error")) {

                    allItems1.clear();

                    mAdapterFeeds = new Adapter_electronic(Activity_categories.this, allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(Activity_categories.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(Activity_categories.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.GetSubcatCategoryProduct_name,category);
       //     jobj.put(EndUrl.GetCategorySubcategoryProduct_name,subcategoryname);


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

                headers = post.getString("categoryName");
                products = post.getString("products");



                JSONArray responcearray2 = new JSONArray(products);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);


                    FeederInfo_electronic item = new FeederInfo_electronic();

                     item.setId(post2.optString("productId"));
                     item.setCategoryname(post2.optString("categoryName"));
                    item.setElectronicname(post2.optString("brandName"));
                    item.setElectronicdetail1(post2.optString("title"));
                    item.setElectronicprice(post2.optString("price"));
                    item.setElectronicimage(post2.optString("image"));
                    item.setDiscountvalue(post2.optString("discountValue"));
                    item.setAfterdiscount(post2.optString("afterDiscount"));

                    Log.e("testing","productId in json = "+post2.optString("productId"));
                    Log.e("testing","title in json = "+post2.optString("title"));
                    Log.e("testing","price in json = "+post2.optString("price"));
                    Log.e("testing","image in json = "+post2.optString("image"));

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
}







