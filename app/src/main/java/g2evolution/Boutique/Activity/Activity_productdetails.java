package g2evolution.Boutique.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter2;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.SingleItemModel;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_productdetails extends AppCompatActivity  {


    ImageView butdecrement,butincrement,back;
    TextView quantity;

    int currentNos;
    String qty;

    TextView addcart,booknow;
    String headers;
   // ImageView pdimage;

    TextView pdtitle,pdsubtitle,pdprice,pdsubprice;

   // TextView pdabout;
   // TextView pdusedfor,pdProcessing;

    String strcategoryName, strsubCategoryName, strchildCategoryName;
    String  _pid,_pdimage,_pdtitle,_pdsubtitle,_pdprice,_pdsubprice,_pdabout,__shipppingAmount,_TaxandPrice,_NetAmount, _descvalue, _totalReviewCount, _avgRating;

  //  String pdusedfor,_pdProcessing;

   // LinearLayout linearlayout1;
    TextView textrating, textreviews;

    JSONParser jsonParser = new JSONParser();
    ArrayList<SectionDataModel> allSampleData;
    RecyclerView my_recycler;

    TextView textdiscount;

    String status,message,total_record;

    String categoryName, Productid,UserId,cart_id;


    RelativeLayout relativemain;

   // ArrayList<SectionDataModel> allSampleData;



    HashMap<String,String> url_maps = new HashMap<String, String>();
    WebView webviewdesc;

    private ProgressDialog pDialog;
    private String TAG = Activity_productdetails.class.getSimpleName();

    String strbuttonstatus;

    ImageView details_image;
    String multipleimages,multipleimagesid;

    JSONArray posts2;


    ArrayList<String> images = new ArrayList<String>();

    String  category,subcategoryname,catid,viewUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        strbuttonstatus = "";


        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
        viewUserId = prefuserdata23.getString("UserId", "");

        SharedPreferences prefuserdata = this.getSharedPreferences("category", 0);
        category = prefuserdata.getString("category", "");
        catid = prefuserdata.getString("catid", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");

        Log.e("testing","subcategoryname = details = "+subcategoryname);

    /*    SharedPreferences prefuserdata1 = this.getSharedPreferences("ProDetails", 0);
        category = prefuserdata1.getString("category", "");
        catid = prefuserdata1.getString("catid", "");
        subcategoryname = prefuserdata1.getString("subcategoryname", "");

*/

        // linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        textrating = (TextView) findViewById(R.id.textrating);
        textreviews = (TextView) findViewById(R.id.textreviews);

        butdecrement = (ImageView)findViewById(R.id.butdecrement);
        butincrement = (ImageView)findViewById(R.id.butincrement);
        details_image = (ImageView)findViewById(R.id.details_image);


        details_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_productdetails.this, Gallery_Images.class);
                intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME,images);

                Log.e("testing","images=====multiple image"+images);

                startActivity(intent);

            }
        });

        back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        quantity = (TextView) findViewById(R.id.quantity);
        addcart = (TextView) findViewById(R.id.addcart);
        booknow = (TextView) findViewById(R.id.booknow);

      //  pdimage = (ImageView)findViewById(R.id.pdimage);

        webviewdesc = (WebView) findViewById(R.id.webviewdesc);

        textdiscount = (TextView)findViewById(R.id.textdiscount);

        pdtitle = (TextView)findViewById(R.id.pdtitle);
        pdsubtitle = (TextView)findViewById(R.id.pdsubtitle);
        pdprice = (TextView)findViewById(R.id.pdprice);
        pdsubprice = (TextView)findViewById(R.id.pdsubprice);
       // pdabout = (TextView)findViewById(R.id.pdabout);
       // pdusedfor = (TextView)findViewById(R.id.pdusedfor);
      //  pdProcessing = (TextView)findViewById(R.id.pdProcessing);

        relativemain = (RelativeLayout) findViewById(R.id.relativemain);
        relativemain.setVisibility(View.GONE);



        SharedPreferences prefuserdata3 = this.getSharedPreferences("ProDetails", 0);
        Productid = prefuserdata3.getString("Proid", "");
        categoryName = prefuserdata3.getString("categoryName", "");


        Log.e("testing","productid in oncreate = "+Productid);

        SharedPreferences prefuserdata5 = getSharedPreferences("regId", 0);
        UserId = prefuserdata5.getString("UserId", "");


       // mDemoSlider = (SliderLayout)findViewById(R.id.slider);

      //  sliderLayout = (SliderLayout)findViewById(R.id.slider);

      /*  HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Honey",R.drawable.bg);
        file_maps.put("Furnishing",R.drawable.bg);
        file_maps.put("Beauty and Health",R.drawable.bg);
        file_maps.put("Dry Fruits", R.drawable.bg);

        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(Activity_productdetails.this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(Activity_productdetails.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
*/


/*
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);*/

       // new Images().execute();


        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "addtocart";

                qty = quantity.getText().toString();

                if(qty == null || qty.equals("0")  || qty.length() == 0){

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                }else  if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {



                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);

                    finish();
                }
                else{

                    new Addtocart().execute();

                }

               /* Intent i = new Intent(Activity_productdetails.this,Activity_cart.class);
                startActivity(i);
                */

            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "booknow";

                qty = quantity.getText().toString();
                if(qty == null || qty.equals("0") || qty.length() == 0){

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                }else  if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {


                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);

                    finish();
                }
                else{

                    new BOOK_Now().execute();

                }


               /* Intent i = new Intent(Activity_productdetails.this,Activity_cart.class);
                startActivity(i);
                */


            }
        });


        textreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent i = new Intent(Activity_productdetails.this,Activity_review.class);

                    SharedPreferences prefuserdata = getSharedPreferences("reviewid", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("reviewid", "" + _pid);

                    prefeditor.commit();


                    startActivity(i);

                }

            }
        });


        butincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {currentNos
                        = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException numberEx) {
                    System.out.print(numberEx);
                    Log.e("testing","numberEx==="+numberEx);
                }

                //currentNos = Integer.parseInt(recyclerViewHolders.quantity.getText().toString());
                quantity.setText(String.valueOf(++currentNos));
                qty = quantity.getText().toString();


            }
        });

        butdecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(quantity.getText().toString());
                if (currentNos <=1){


                }else{
                    quantity.setText(String.valueOf(--currentNos));
                }


               /* try {
                    currentNos = Integer.parseInt(Countval.getText().toString());
                } catch (NumberFormatException numberEx) {
                    System.out.print(numberEx);
                }


                Countval.setText(String.valueOf(--currentNos));*/
                qty = quantity.getText().toString();

            }
        });

        allSampleData = new ArrayList<SectionDataModel>();
        my_recycler = (RecyclerView)findViewById(R.id.recycler_view);
        my_recycler.setHasFixedSize(true);


        ConnectionDetector cd = new ConnectionDetector(Activity_productdetails.this);
        if (cd.isConnectingToInternet()) {

            new Loader().execute();
            new Loader2().execute();

        } else {


            Toast.makeText(Activity_productdetails.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

    }
    public static void hideSoftKeyboard(android.app.Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(android.app.Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
     //   mDemoSlider.stopAutoCycle();
        super.onStop();
    }



    private void ShowLogoutAlert1(String data) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Activity_productdetails.this);
        alertDialog.setTitle("Login to Grocery");
        alertDialog.setMessage(data);
        //  alertDialog.setBackgroundResource(R.color.dialog_color);
        // alertDialog.setIcon(R.drawable.exit);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Activity_productdetails.this, Login.class);
                startActivity(intent);
                finish();
                //loginandregistermethod1();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();

    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_productdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_productdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.Product_details_URL, makingJson());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){
                    relativemain.setVisibility(View.VISIBLE);
                    textdiscount.setVisibility(View.GONE);



                    if (_descvalue == null || _descvalue.length() == 0 || _descvalue.equals("NA")){

                        textdiscount.setVisibility(View.INVISIBLE);

                    }else{
                        textdiscount.setVisibility(View.VISIBLE);
                        textdiscount.setText((_descvalue+"%"));
                    }


                    pdtitle.setText(_pdtitle);
                    pdsubtitle.setText(_pdsubtitle);
                //    pdprice.setText(_pdprice);
                //    pdsubprice.setText(_pdsubprice);
                   // pdabout.setText(_pdabout);
                    final String mimeType = "text/html";
                    final String encoding = "UTF-8";
                    webviewdesc.loadDataWithBaseURL("", _pdabout, mimeType, encoding, "");
                  //  webviewdesc.loadUrl(_pdabout);
                 //   pdusedfor.setText(_pdusedfor);
                   // pdProcessing.setText(_pdProcessing);

                    textrating.setText(_avgRating);

                    if (_totalReviewCount==null||_totalReviewCount.length()==0||_totalReviewCount.equals("null")){

                        textreviews.setText("0"+ " Rating and Reviews");
                    }else {

                        textreviews.setText(_totalReviewCount+ " Rating and Reviews");

                    }


                    if (_pdimage == null || _pdimage.length() == 0 || _pdimage.equals("null")){

                    }else{
                        Glide.with(Activity_productdetails.this)
                                .load(Uri.parse(_pdimage))
                                // .diskCacheStrategy(DiskCacheStrategy.NONE)
                                //.skipMemoryCache(true)
                                .error(R.drawable.car)
                                .into(details_image);

                    }


                    if (_descvalue == null || _descvalue.length() == 0 || _descvalue.equals("NA")){
                        final String strrs = getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+_pdsubprice);
                    }else{
                        pdprice.setVisibility(View.VISIBLE);
                        final String strrs = getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+_pdsubprice);


                        pdprice.setText(strrs+" "+_pdprice);
                        pdprice.setPaintFlags(pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

                    }


                  //  Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();


                }else {

                    Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_productdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson() {


        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Product_details_Id,Productid);


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
            Log.e("testing","result in post message========="+json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");
            total_record = json.getString("total_record");


            String arrayresponce = json.getString("data");

            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");

                strcategoryName = post.getString("categoryName");
                strsubCategoryName = post.getString("subCategoryName");
                strchildCategoryName = post.getString("childCategoryName");
                _pdimage = post.getString("image");
                _pdtitle  = post.getString("title");
                _pdsubtitle  = post.getString("subTitle");
                _pdprice  = post.getString("price");
                _descvalue = post.getString("discountValue");
                _pdsubprice = post.getString("afterDiscount");
                _pdabout  = post.getString("description");
              //  _pdusedfor  = post.getString("usedFor");
              //  _pdProcessing  = post.getString("processingType");

              //  _shipppingAmount =post.getString("shipppingAmount");
             //   _TaxandPrice =post.getString("TaxandPrice");
               // _NetAmount =post.getString("NetAmount");
                _pid = post.getString("productId");
                _totalReviewCount = post.getString("totalReviewCount");
                _avgRating = post.getString("avgRating");

                String strgallery = post.getString("gallery");
                JSONArray arraygallery = new JSONArray(strgallery);
                Log.e("testing12", "responcearray==strgallery value=" + strgallery);


                for (int i1 = 0; i1 < arraygallery.length(); i1++) {

                    JSONObject post2 = arraygallery.getJSONObject(i1);

                    multipleimages=post2.getString("gId");
                    multipleimagesid=post2.getString("prodcutImage");
                    Log.e("testing12", "multipleimagesid value=" + multipleimagesid);

                //    url_maps.put(post2.getString("gId"), post2.getString("prodcutImage"));

                }

                JSONArray posts2 = post.optJSONArray("gallery");

                for (int i1 = 0; i1 < posts2.length(); i1++) {
                    JSONObject post2 = posts2.optJSONObject(i1);
                    multipleimages=post2.getString("gId");
                    images.add(post2.getString("prodcutImage"));
                    Log.e("testing12", "multipleimagesid value=" + multipleimagesid);

                    //find the group position inside the list
                    //groupPosition = deptList.indexOf(headerInfo);

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
//-----------------------------------------------------------------------------------------------------

    private class Recentlyproducts extends AsyncTask<Void, Void, Void> {

        String headers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Activity_productdetails.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(EndUrl.Recently_added_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {

                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node

                    Log.e("testing","jsonObj = "+jsonObj);


                    JSONObject response = new JSONObject(jsonObj.toString());

                    Log.e("testing", "jsonParser2222" + jsonObj);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    //  Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("data");
                    Log.e("testing", "jsonParser3333" + posts);

                    if (posts == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();

                    } else {

                        Log.e("testing", "jon122222211");
                        Log.e("testing", "jsonParser4444" + posts);

                        for (int i = 0; i < posts.length(); i++) {
                            Log.e("testing", "" + posts);

                            Log.e("testing", "" + i);
                            JSONObject post = posts.optJSONObject(i);
                            // JSONArray posts2 = response.optJSONArray("categories");
                            Log.e("testng", "" + post);
                            headers = post.getString("categoryName");

                            Log.e("testing", "name is 11= " + post.getString("categoryName"));


                            String Title = post.getString("categoryName");

                            SectionDataModel dm = new SectionDataModel();

                            dm.setHeaderTitle(post.getString("categoryName"));

                             posts2 = post.optJSONArray("products");

                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);

                               /* String Title2 = post2.getString("title");
                                String Productid = post2.getString("productId");
                                String Parentid = post2.getString("subcatName");
                                String Typename = post2.getString("location");

                               */

                                String finalimg = null;

                                finalimg = post2.getString("image");
                              /*  JSONArray posts3 = post2.optJSONArray("multipleImages");
                                for (int i2 = 0; i2 < posts3.length(); i2++) {
                                    JSONObject post3 = posts3.optJSONObject(i2);

                                    finalimg = post3.getString("image");


                                    //find the group position inside the list
                                    //groupPosition = deptList.indexOf(headerInfo);
                                }*/

                                singleItem.add(new SingleItemModel(post2.getString("productId"),post2.getString("categoryName"),post2.getString("title"),post2.getString("subTitle"),post2.getString("price"),post2.getString("discountValue"),post2.getString("afterDiscount"), finalimg,post2.getString("stockQuantity")));

                            }

                            dm.setAllItemsInSection(singleItem);

                            allSampleData.add(dm);

                        }

                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Activity_productdetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Activity_productdetails.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {

                Log.e(TAG, "Couldn't get json from server.");
                Activity_productdetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Activity_productdetails.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;

        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */

            if (posts2.equals("[]")){

             //   Toast.makeText(Activity_productdetails.this, "No ", Toast.LENGTH_SHORT).show();

            }else {

                RecyclerViewDataAdapter2 adapter = new RecyclerViewDataAdapter2(Activity_productdetails.this, allSampleData);

                my_recycler.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                my_recycler.setAdapter(adapter);

            }

        }

    }


    class Loader2 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_productdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_productdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.GetCategoryChildProductList_URL, makingJson2());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null||posts2.equals("[]")) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========Loader2====" + result);

                if (status.equals("success")) {

                    RecyclerViewDataAdapter2 adapter = new RecyclerViewDataAdapter2(Activity_productdetails.this, allSampleData);

                    my_recycler.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                    my_recycler.setAdapter(adapter);

                } else if (status.equals("error")) {


                }

            } else {
                Toast.makeText(Activity_productdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();
        try {

            jobj.put(EndUrl.Get_subcatId,strsubCategoryName);
            jobj.put(EndUrl.Get_childCatId,strchildCategoryName);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("testing","json object222 "+jobj);
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

        JSONObject jsonObj = null;

        try {

            jsonObj = new JSONObject(result);


            // Getting JSON Array node

            Log.e("testing","jsonObj = "+jsonObj);

            JSONObject response = new JSONObject(jsonObj.toString());

            Log.e("testing", "jsonParser2222" + jsonObj);

            //JSONObject jsonArray1 = new JSONObject(json.toString());
            status = response.getString("status");
            message = response.getString("message");
            total_record = response.getString("total_record");
            JSONArray posts = response.optJSONArray("data");
            Log.e("testing", "jsonParser3333" + posts);


            if (posts == null) {

                Log.e("testing", "jon11111111111111111");

                //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();

            } else {


                Log.e("testing", "jon122222211");
                Log.e("testing", "jsonParser4444" + posts);


                for (int i = 0; i < posts.length(); i++) {

                    Log.e("testing", "" + posts);

                    Log.e("testing", "" + i);
                    JSONObject post = posts.optJSONObject(i);
                    // JSONArray posts2 = response.optJSONArray("Products");
                    Log.e("testng", "" + post);
                 //   headers = post.getString("categoryName");

                   // Log.e("testing", "name is 11= " + post.getString("categoryName"));

                   // String Title = post.getString("categoryName");

                    SectionDataModel dm = new SectionDataModel();

                   // dm.setHeaderTitle(post.getString("categoryName"));
                    dm.setHeaderTitle("");

                     posts2 = post.optJSONArray("Products");
                    ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                    if (posts2 == null || posts2.length() == 0){

                    }else{
                        for (int i1 = 0; i1 < posts2.length(); i1++) {
                            JSONObject post2 = posts2.optJSONObject(i1);

                               /* String Title2 = post2.getString("title");
                                String Productid = post2.getString("productId");
                                String Parentid = post2.getString("subcatName");
                                String Typename = post2.getString("location");

                               */

                            String finalimg = null;

                            finalimg = post2.getString("image");

                              /*  JSONArray posts3 = post2.optJSONArray("multipleImages");
                                for (int i2 = 0; i2 < posts3.length(); i2++) {
                                    JSONObject post3 = posts3.optJSONObject(i2);


                                    finalimg = post3.getString("image");

                                    //find the group position inside the list
                                    //groupPosition = deptList.indexOf(headerInfo);
                                }*/

                            singleItem.add(new SingleItemModel(post2.getString("productId"),post2.getString("categoryName"),post2.getString("title"),post2.getString("subTitle"),post2.getString("price"),post2.getString("discountValue"),post2.getString("afterDiscount"), finalimg,post2.getString("stockQuantity")));

                        }

                    }

                    dm.setAllItemsInSection(singleItem);

                    allSampleData.add(dm);

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return jsonObj;

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


    private class Addtocart extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_productdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_productdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject1(EndUrl.Addtocart_URL, makingJson1());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){


                    if (strbuttonstatus.equals("addtocart")){
                        //Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();

                        new MainActivity.cartcount().execute();
                        new Product_List.productcartcount().execute();
                        onBackPressed();
/*
                       Intent intent = new Intent(Activity_productdetails.this, Activity_cart.class);

                        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("userid", "" + UserId);
                        prefeditor.putString("productid", "" + _pid);
                        prefeditor.putString("quantity", "" + qty);
                        prefeditor.putString("addtocart", "" + strbuttonstatus);

                        prefeditor.commit();
                        startActivity(intent);*/


                    }else if (strbuttonstatus.equals("booknow")){

                     //   Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(Activity_productdetails.this, Activity_address.class);

                        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("userid", "" + UserId);
                        prefeditor.putString("productid", "" + _pid);

                        prefeditor.putString("quantity", "" + qty);
                        prefeditor.putString("bookstatus", "" + strbuttonstatus);

                        prefeditor.commit();
                        startActivity(intent);

                    }

                }else if (status.equals("fail")){

                    Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_productdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson1() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.Addtocart_userId,UserId);
            object.put(EndUrl.Addtocart_productId,Productid);
            object.put(EndUrl.Addtocart_quantity,qty);

            //if you want to modify some value just do like this.

            details.put(EndUrl.Addtocart_outside_otp,object);
            Log.d("json",details.toString());
            Log.d("json",object.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }

    public JSONObject postJsonObject1(String url, JSONObject loginJobj){
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

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");
                cart_id  = post.getString("cart_id ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }


    private class BOOK_Now extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_productdetails.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_productdetails.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject3(EndUrl.BOOK_NOW_URL, makingJson3());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){


                    //    Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Activity_productdetails.this, Activity_address.class);

                        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("userid", "" + UserId);
                        prefeditor.putString("productid", "" + _pid);
                        prefeditor.putString("qty", "" + qty);
                        prefeditor.putString("bookstatus", "" + strbuttonstatus);

                        prefeditor.commit();
                        startActivity(intent);



                }else {
                    Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_productdetails.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson3() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

           // object.put(EndUrl.Addtocart_userId,UserId);
            details.put(EndUrl.BOOK_NOW_productId,Productid);
            details.put(EndUrl.BOOK_NOW_quantity,qty);


            //if you want to modify some value just do like this.

           // details.put(EndUrl.Addtocart_outside_otp,object);
            Log.d("json",details.toString());
         //   Log.d("json",object.toString());
            Log.e("testing","json====book_now"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }

    public JSONObject postJsonObject3(String url, JSONObject loginJobj){
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

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                // empId = post.getString("empId");

              String  price  = post.getString("price ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

}
