package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_review;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_review;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;


public class Activity_review extends AppCompatActivity {


    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_review> allItems1 = new ArrayList<FeederInfo_review>();
    Context context;
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_review> mListFeederInfo;
    private Activity_review adapter;
    Adapter_review mAdapterFeeds;
    RecyclerView rView;

    String status,message,totalrecord;

    JSONParser jsonParser = new JSONParser();

    RatingBar ratingBar;
    String strreviewtitle, strreviewdesc, strreviewrating;
    EditText editreviewtitle, editreviewdesc;

    Dialog dialogmain; // Write Review Page

    String []Rating =new String[]{"2","3","4"};
    String []details =new String[]{"Kachi Ghani1 Bottle","Kachi Ghani1 Bottle","Kachi Ghani1 Bottle"};
    String []price =new String[]{"A literature review is an evaluative report of information","A literature review is an evaluative report of information","A literature review is an evaluative report of information"};

    TextView writereview,checkout;
    ImageView back;
    String UserId, productId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");


        SharedPreferences prefuserdata2 = getSharedPreferences("reviewid", 0);
        productId = prefuserdata2.getString("reviewid", "");


        Log.e("teting","productId = "+productId);


        dialogmain = new Dialog(Activity_review.this);
        dialogmain.requestWindowFeature(Window.FEATURE_NO_TITLE);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_review.this));

        //setUpRecycler();
        // context = this;
        ///  lLayout = new GridLayoutManager(Activity_cart.this,2);

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        writereview = (Button)findViewById(R.id.writereview);

        writereview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setreviewdialog();
            }
        });
        // setUpRecycler();

        new LoadReviews().execute();

    }

    private void setreviewdialog() {

        dialogmain.setContentView(R.layout.review);
        ImageView dialodcancel = (ImageView) dialogmain.findViewById(R.id.imgcancel) ;
        editreviewtitle = (EditText) dialogmain.findViewById(R.id.edittitle);
        editreviewdesc = (EditText) dialogmain.findViewById(R.id.editdesc);
        ratingBar = (RatingBar) dialogmain.findViewById(R.id.ratingBar1);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                // Toast.makeText(Activity_NewBrands.this, "Your Selected Ratings  : " + String.valueOf(rating), Toast.LENGTH_LONG).show();

            }
        });

        TextView textsubmit = (TextView) dialogmain.findViewById(R.id.textsubmit);
        dialodcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogmain.dismiss();
            }
        });

        textsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strreviewtitle = editreviewtitle.getText().toString();
                strreviewdesc = editreviewdesc.getText().toString();

                String numberAsString = String.valueOf(ratingBar.getRating());
                char first = numberAsString.charAt(0);


                strreviewrating = Character.toString(first);

                if (strreviewtitle == null || strreviewtitle.length() == 0){
                    Toast.makeText(Activity_review.this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                }else if (strreviewdesc == null || strreviewdesc.length() == 0){

                    Toast.makeText(Activity_review.this, "Please Enter Prescription", Toast.LENGTH_SHORT).show();
                }else if (strreviewrating == null || strreviewrating.length() == 0 || strreviewrating.equals("0")){

                    Toast.makeText(Activity_review.this, "Please Select Rating", Toast.LENGTH_SHORT).show();
                }else{
                    ConnectionDetector cd = new ConnectionDetector(Activity_review.this);
                    if (cd.isConnectingToInternet()) {

                        new PostReview().execute();


                    } else {


                        Toast.makeText(Activity_review.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }
                }


            }
        });

        dialogmain.show();

    }

    private void setUpRecycler() {


        mListFeederInfo = new ArrayList<FeederInfo_review>();

        for (int i = 0; i < Rating.length; i++)
        {
            FeederInfo_review feedInfo = new FeederInfo_review();

            feedInfo.setTextrating(Rating[i]);
            feedInfo.setTexttitle(details[i]);
            feedInfo.setTextdesc(price[i]);

            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new Adapter_review(this , mListFeederInfo);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }



    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_review.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_review.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.Rating_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();


                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    Toast.makeText(Activity_review.this, ""+message, Toast.LENGTH_LONG).show();
                    dialogmain.dismiss();

                }else {
                    Toast.makeText(Activity_review.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_review.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.Rating_Userid,UserId);
            object.put(EndUrl.Rating_Eventid,productId);
            object.put(EndUrl.Rating_Ratingcount,strreviewrating);
            object.put(EndUrl.Rating_Ratingtitle,strreviewtitle);
            object.put(EndUrl.Rating_Ratingcomment,strreviewdesc);
            object.put(EndUrl.Rating_Ratingfrom,"Application");

            details.put("rating",object);

            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

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
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");



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






    class Loader2 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_review.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_review.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.RatingList_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                    mAdapterFeeds= new Adapter_review(Activity_review.this , allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);



                }else {

                    mAdapterFeeds= new Adapter_review(Activity_review.this , allItems1);
                    mFeedRecyler.setAdapter(mAdapterFeeds);
                }

            }else {

                Toast.makeText(Activity_review.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();



        try {

            object.put(EndUrl.RatingList_Eventid,productId);




            Log.d("json",object.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }



    public JSONObject postJsonObject2(String url, JSONObject loginJobj){
        InputStream inputStream = null;
        String result = "";
        String headers;
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
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");
            totalrecord = json.getString("total_record");




            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();





                FeederInfo_review item = new FeederInfo_review();

                //  item.setId(post.optString("id"));


                item.setTextrating(post.optString("ratingCount"));
                item.setTexttitle(post.optString("ratingTitle"));
                item.setTextdesc(post.optString("ratingComment"));
                item.setTexton(post.optString("ratingOn"));
                item.setName(post.optString("userName"));





                allItems1.add(item);

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


    class LoadReviews extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_review.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 =new ArrayList<FeederInfo_review>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetReviews_product_id, productId));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetReviews_URL, "GET", userpramas);

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



                            FeederInfo_review item = new FeederInfo_review();

                            item.setId(post.optString("id"));


                            item.setTextrating(post.optString("rating"));
                            item.setTexttitle(post.optString("title"));
                            item.setTextdesc(post.optString("description"));
                            item.setTexton(post.optString("date"));
                            item.setName(post.optString("name"));





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
                //  Log.e("testing123", "allItems1===" + allItems1);


/*
                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                 prodcuts_recycler.setLayoutManager(mLayoutManager);
                product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/

                mAdapterFeeds= new Adapter_review(Activity_review.this , allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);





            }
            else {
                mAdapterFeeds= new Adapter_review(Activity_review.this , allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            /*    product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/




            }



        }

    }

    class PostReview extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_review.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 =new ArrayList<FeederInfo_review>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.PostReviews_product_id, productId));
            userpramas.add(new BasicNameValuePair(EndUrl.PostReviews_user_id, UserId));
            userpramas.add(new BasicNameValuePair(EndUrl.PostReviews_title, strreviewtitle));
            userpramas.add(new BasicNameValuePair(EndUrl.PostReviews_description, strreviewdesc));
            userpramas.add(new BasicNameValuePair(EndUrl.PostReviews_rating, strreviewrating));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.PostReviews_URL, "POST", userpramas);

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
                   /* if (status.equals("success")) {

                        status = json.getString("status");
                        strresponse = json.getString("response");
                        String arrayresponse = json.getString("data");
                        Log.e("testing", "adapter value=" + arrayresponse);


                        JSONArray responcearray = new JSONArray(arrayresponse);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();



                            FeederInfo_review item = new FeederInfo_review();

                            item.setId(post.optString("id"));


                            item.setTextrating(post.optString("rating"));
                            item.setTexttitle(post.optString("title"));
                            item.setTextdesc(post.optString("description"));
                           *//* item.setTexton(post.optString("ratingOn"));
                            item.setName(post.optString("userName"));*//*





                            allItems1.add(item);






                        }
                    }else{

                    }*/
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
                Toast.makeText(Activity_review.this, strmessage, Toast.LENGTH_SHORT).show();
                dialogmain.dismiss();
                new LoadReviews().execute();




            }
            else {


                Toast.makeText(Activity_review.this, strmessage, Toast.LENGTH_SHORT).show();


            }



        }

    }


}
