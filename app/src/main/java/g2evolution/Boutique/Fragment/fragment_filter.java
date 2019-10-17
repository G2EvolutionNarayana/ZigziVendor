package g2evolution.Boutique.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

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

import g2evolution.Boutique.Adapter.Adapter_Product_List;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_Product_List;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

/**
 * Created by Ithishree on 16-02-2018.
 */

public class fragment_filter extends AppCompatActivity implements RecyclerViewDataAdapter.OnItemClick{


    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;

    String status;
    String headers;
    private RecyclerViewDataAdapter.OnItemClick mCallback;
    RecyclerViewDataAdapter adapter;
    String searchtype;


    private ArrayList<FeederInfo_Product_List> allItems3 = new ArrayList<FeederInfo_Product_List>();
    private RecyclerView mFeedRecyler2;
    Adapter_Product_List mAdapterFeeds2;

    String categoryname, subcategoryname, categoryname_heading;

    String message,products;
    //The request counter to send ?page=1, ?page=2  requests
    private int requestCount = 1;

    ProgressBar progressBar1;

    //Volley Request Queue
    private RequestQueue requestQueue;

    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        SharedPreferences prefuserdata12 = getSharedPreferences("searchtype", 0);
        searchtype = prefuserdata12.getString("searchtype", "");






        SharedPreferences prefuserdata = getSharedPreferences("ProDetails", 0);
        categoryname = prefuserdata.getString("category", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");
        categoryname_heading = prefuserdata.getString("categoryname", "");

      /*  allSampleData = new ArrayList<SectionDataModel>();
        my_recycler_view = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);

*/
      back =(ImageView)findViewById(R.id.back);
      back.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              onBackPressed();
          }
      });
        mFeedRecyler2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mFeedRecyler2.setHasFixedSize(true);
        mFeedRecyler2.setLayoutManager(new LinearLayoutManager(fragment_filter.this));

//  -----pagination----------------//
        progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);


        requestQueue = Volley.newRequestQueue(fragment_filter.this);

        //Calling method to get data to fetch data
        new PRODUCT_LIST().execute();


        mAdapterFeeds2 = new Adapter_Product_List(fragment_filter.this, allItems3);
        mFeedRecyler2.setAdapter(mAdapterFeeds2);

        //Adding an scroll change listener to recyclerview
        //  recyclerView.setOnScrollChangeListener(this);
        mFeedRecyler2.addOnScrollListener(recyclerViewOnScrollListener);
        //===============================================================




    }


    @Override
    public void onClickedItem(int pos, String category, int status) {

    }


    class PRODUCT_LIST extends AsyncTask<Void, Void, JSONObject> {

       // ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(fragment_filter.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(fragment_filter.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();*/
            progressBar1.setVisibility(View.VISIBLE);

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
          //  allItems3.clear();
      //      allItems3 = new ArrayList<FeederInfo_Product_List>();
            return postJsonObject2(EndUrl.Filter_URL, makingJson2());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            progressBar1.setVisibility(View.GONE);
            if (result != null) {
              //  dialog.dismiss();

            //    Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {
                    requestCount++;
                    progressBar1.setVisibility(View.GONE);

                    Log.e("testing", "result in post execute=========" + result);
                    Log.e("testing", "result in post execute====allItems3=====" + allItems3);

/*
                    mAdapterFeeds2 = new Adapter_Product_List(fragment_filter.this, allItems3);
                    //mFeedRecyler2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true));
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);*/

                    mAdapterFeeds2.notifyDataSetChanged();


                } else if (status.equals("fail")) {


              //      Toast.makeText(fragment_filter.this, ""+message, Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(fragment_filter.this, "Something went wrong", Toast.LENGTH_LONG).show();
                //dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Filter_searchType,searchtype);
        //    jobj.put(EndUrl.Filter_subcatId,subcategoryname);
            jobj.put(EndUrl.Filter_Pagenumber,""+requestCount);
            jobj.put(EndUrl.Filter_request_count,"10");

            //jobj.put(EndUrl.Get_childCatId,childid);


            Log.e("testing","filter==="+jobj);

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
             status = json.getString("status");
            message = json.getString("message");
            String  Totalrecord = json.getString("total_record");

            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);



      /*      for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                //   headers = post.getString("categoryName");
                products = post.getString("products");

*/

                JSONArray responcearray2 = new JSONArray(arrayresponce);
                Log.e("testing", "responcearray value=" + arrayresponce);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);


                    FeederInfo_Product_List item = new FeederInfo_Product_List();

                    item.setId(post2.optString("productId"));
                    item.setCategoryname(post2.optString("categoryName"));
                    item.setElectronicname(post2.optString("title"));
                    item.setElectronicdetail1(post2.optString("subTitle"));
                    item.setElectronicprice(post2.optString("price"));
                    item.setElectronicimage(post2.optString("image"));
                    item.setStockQuantity(post2.optString("stockQuantity"));
                    item.setDiscountvalue(post2.optString("discountValue"));
                    item.setAfterdiscount(post2.optString("afterDiscount"));

                /*    Log.e("testing","productId in json = "+post2.optString("productId"));
                    Log.e("testing","title in json = "+post2.optString("title"));
                    Log.e("testing","price in json = "+post2.optString("price"));
                    Log.e("testing","image in json = "+post2.optString("image"));
*/


                    allItems3.add(item);
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

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (isLastItemDisplaying(recyclerView)) {
                //Calling the method getdata again
                new PRODUCT_LIST().execute();
            }
        }
    };
    //This method would check that the recyclerview scroll has reached the bottom or not
    private boolean isLastItemDisplaying(RecyclerView recyclerView) {
        if (recyclerView.getAdapter().getItemCount() != 0) {
            int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.getAdapter().getItemCount() - 1)
                return true;
        }
        return false;
    }


}