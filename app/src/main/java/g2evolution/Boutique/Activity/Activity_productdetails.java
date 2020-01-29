package g2evolution.Boutique.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import g2evolution.Boutique.Adapter.Adapter_Generalinfo;
import g2evolution.Boutique.Adapter.Adapter_ProductVariations;
import g2evolution.Boutique.Adapter.Adapter_Productdescription;
import g2evolution.Boutique.Adapter.Adapter_Sizes;
import g2evolution.Boutique.Adapter.Image_Slider_Adapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter2;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_orderdetails;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.SingleItemModel;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_Generalinfo;
import g2evolution.Boutique.entit.Entity_Sizes;
import g2evolution.Boutique.entit.Entity_descriptionchild;
import g2evolution.Boutique.entit.Entity_descriptionheader;
import g2evolution.Boutique.entit.Entity_weightchild;
import g2evolution.Boutique.entit.Entity_weightheader;
import me.relex.circleindicator.CircleIndicator;


public class Activity_productdetails extends AppCompatActivity implements RecyclerViewDataAdapter.OnItemClick, Adapter_Generalinfo.OnItemClickcourses{

    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;
    ArrayList<Entity_weightheader> allSampleDatavariables;
    ArrayList<Entity_Generalinfo> allSampleDataGeneralInfo;
    ArrayList<Entity_descriptionheader> allSampleDatadescription;
    private RecyclerViewDataAdapter.OnItemClick mCallback;
    RecyclerViewDataAdapter adapter;


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

    String _pid,_pdimage,_pdtitle,_pdsubtitle,_pdprice,_pdsubprice,_pdabout,__shipppingAmount,_TaxandPrice,_NetAmount, _descvalue, _totalReviewCount, _avgRating;

    //  String pdusedfor,_pdProcessing;

    // LinearLayout linearlayout1;
    TextView textrating, textreviews, textratingreviews;

    JSONParser jsonParser = new JSONParser();
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
    Integer intsizes = null;
    ArrayList<String> images = new ArrayList<String>();
    String strpincode;
    TextView check_text;
    EditText editpincode;
    String category,subcategoryname,catid,viewUserId;
    Spinner spinapartment;
    ArrayList<String> worldlist;//spinapartment
    String[]Name =new String[]{"506244","576244","596244","506244",};
    String capartmentid_id;
    String pincode;
    Dialog dialog; // apartment dialog
   // ImageView cartImage,wishlistImage,searchImage;

    TextView discount_price;

    RelativeLayout relativelayoutimages;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final String[] XMEN= {""};
    private ArrayList<String> XMENArray = new ArrayList<String>();

    TextView wishlist;

    RecyclerView recyclergeneralinfo;
    RecyclerView recyclercoursesoffered;
    RecyclerView recyclerviewdescription;
    String [] strtitle = new String[]{"Bangalore", "Lucknow","Pune","Trivandrum","Kochi"};
    Adapter_Generalinfo courses_Adapter;
    Adapter_Generalinfo.OnItemClickcourses mCallback2;

    String strvariantid;

    private ArrayList<Entity_Sizes> allItemscourses = new ArrayList<Entity_Sizes>();

    String strsizeid, strsizename;

    List<String> arrayListkey = new ArrayList<String>();
    List<String> arrayListvalue = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);


        Bundle bundle = this.getIntent().getExtras();
        HashMap hashMap = (HashMap) bundle.getSerializable("HashMap");
        arrayListkey = new ArrayList<String>();
        arrayListvalue = new ArrayList<String>();
        Iterator myVeryOwnIterator = hashMap.keySet().iterator();
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)hashMap.get(key);
            Log.e("testing","Key: "+key+" Value: "+value);
            arrayListkey.add(key);
            arrayListvalue.add(value);
            //Toast.makeText(Activity_productdetails.this, "Key: "+key+" Value: "+value, Toast.LENGTH_LONG).show();
        }

        wishlist=(TextView)findViewById(R.id.wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("testing","strvariantid = "+strvariantid);
                new PostWhislist().execute();
                //Toast.makeText(Activity_productdetails.this, "Added to WishList", Toast.LENGTH_SHORT).show();
            }
        });
       /* cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);*/

        strbuttonstatus = "";

        mCallback=this;
        allSampleData = new ArrayList<SectionDataModel>();
        allSampleDatavariables = new ArrayList<Entity_weightheader>();
        allSampleDatadescription = new ArrayList<Entity_descriptionheader>();
        my_recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);


        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
        viewUserId = prefuserdata23.getString("UserId", "");
        Log.e("testing","viewUserId = "+viewUserId);

        SharedPreferences prefuserdata = this.getSharedPreferences("category", 0);
        category = prefuserdata.getString("category", "");
        catid = prefuserdata.getString("catid", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");

        Log.e("testing","catid = "+catid);


        check_text = (TextView) findViewById(R.id.check_text);
        editpincode = (EditText) findViewById(R.id.editpincode);
        relativelayoutimages = (RelativeLayout) findViewById(R.id.relativelayoutimages);
        mPager = (ViewPager) findViewById(R.id.pager);
        check_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpincode = editpincode.getText().toString();

                if (strpincode == null || strpincode.trim().length() != 6 || strpincode.trim().equals("null")){
                    Toast.makeText(Activity_productdetails.this, "Please Enter Correct Pincode", Toast.LENGTH_SHORT).show();
                }else{

                    new CheckPincode().execute();

                }
            }
        });

        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images == null || images.size() == 0){

                }else{
                    Intent intent = new Intent(Activity_productdetails.this, Gallery_Images.class);
                    intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME,images);
                    Log.e("testing12345","images=====multiple image"+images);
                    startActivity(intent);
                }

            }
        });

        recyclergeneralinfo = (RecyclerView) findViewById(R.id.recyclergeneralinfo);
        recyclergeneralinfo.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager11 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager mLayoutManager12 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        recyclergeneralinfo.setLayoutManager(mLayoutManager11);
        mCallback2 = this;


        recyclercoursesoffered = (RecyclerView) findViewById(R.id.recyclerviewsizes);
        recyclercoursesoffered.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL,false);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        recyclercoursesoffered.setLayoutManager(mLayoutManager);

        recyclerviewdescription = (RecyclerView) findViewById(R.id.recyclerviewdescription);
        recyclerviewdescription.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL,false);

        recyclerviewdescription.setLayoutManager(mLayoutManager3);

    /*    SharedPreferences prefuserdata1 = this.getSharedPreferences("ProDetails", 0);
        category = prefuserdata1.getString("category", "");
        catid = prefuserdata1.getString("catid", "");
        subcategoryname = prefuserdata1.getString("subcategoryname", "");

*/

        // linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);

       /* cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Activity_productdetails.this,Activity_cart.class);
                startActivity(intent);

            }
        });*/

       /* wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* Intent intent =new Intent(Activity_productdetails.this,Activity_WishList.class);
                startActivity(intent);*//*

            }
        });*/

      /*  searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Activity_productdetails.this,Activity_search.class);
                startActivity(intent);

            }
        });
*/
        textrating = (TextView) findViewById(R.id.textrating);
        textreviews = (TextView) findViewById(R.id.textreviews);
        textratingreviews = (TextView) findViewById(R.id.textratingreviews);

        butdecrement = (ImageView)findViewById(R.id.butdecrement);
        butincrement = (ImageView)findViewById(R.id.butincrement);
        details_image = (ImageView)findViewById(R.id.details_image);


        TextView actual_price = (TextView) findViewById(R.id.actual_price);
        actual_price.setText(getResources().getString(R.string.Rs) + "2,3900");
        actual_price.setPaintFlags(actual_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        discount_price = (TextView) findViewById(R.id.discount_price);
        //  discount_price.setText("(" +  "-30%" + ")");

        // spinapartment = (Spinner) findViewById(R.id.spinapartment);

        details_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_productdetails.this, Gallery_Images.class);
                intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME,images);
                Log.e("testing12345","images=====multiple image"+images);
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
        TextView textview_title1 = (TextView) findViewById(R.id.textview_title1);
        textview_title1.setText("Product Details");


        quantity = (TextView) findViewById(R.id.quantity);
        addcart = (TextView) findViewById(R.id.addcart);
        booknow = (TextView) findViewById(R.id.booknow);
        booknow.setVisibility(View.GONE);
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


        Log.e("testing","Productid = "+Productid);

        SharedPreferences prefuserdata5 = getSharedPreferences("regId", 0);
        UserId = prefuserdata5.getString("UserId", "");
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "addtocart";
                qty = quantity.getText().toString();

                Log.e("testing","strvariantid = "+strvariantid);

                if(qty == null || qty.equals("0")  || qty.length() == 0){

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                }else if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);
                    finish();

                }else{
                    new PostAddtocart().execute();
                   /* if (intsizes == null || intsizes == 0 ){
                        strsizeid = _pid;
                        new PostAddtocart().execute();
                    }else{
                        if (strsizeid == null || strsizeid.trim().length() == 0 || strsizeid.trim().equals("null")){
                            Toast.makeText(Activity_productdetails.this, "Please select size", Toast.LENGTH_SHORT).show();
                        }else{
                            new PostAddtocart().execute();
                        }
                    }
*/


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

                }else if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);
                    finish();

                }
                else{

                   // new BOOK_Now().execute();

                }


               /* Intent i = new Intent(Activity_productdetails.this,Activity_cart.class);
                startActivity(i);
                */


            }
        });


        textratingreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                    ShowLogoutAlert1("Please login");

                }else {

                    Intent i = new Intent(Activity_productdetails.this,Activity_review.class);

                    SharedPreferences prefuserdata = getSharedPreferences("reviewid", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("reviewid", "" + strvariantid);

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
        allSampleDatavariables = new ArrayList<Entity_weightheader>();
        allSampleDatadescription = new ArrayList<Entity_descriptionheader>();
        my_recycler = (RecyclerView)findViewById(R.id.recycler_view);
        my_recycler.setHasFixedSize(true);


        ConnectionDetector cd = new ConnectionDetector(Activity_productdetails.this);

        if (cd.isConnectingToInternet()) {

            //  new Loader().execute();
            new LoadProductList().execute();

            //---------offer products-----------
              new Getproducts().execute();
        } else {


            Toast.makeText(Activity_productdetails.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        // init();

    }

    //image sliding
    private void init() {


        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);


        mPager.setAdapter(new Image_Slider_Adapter(Activity_productdetails.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);




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
        alertDialog.setTitle("Login to Pranitha IT Solutions");
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

    @Override
    public void onClickedItem(int pos, String category, int status) {

    }

    @Override
    public void OnItemClickcourses(int pos, String qty, String sub_category_id, int status) {

        strsizeid = qty;
        strsizename = sub_category_id;

    }


       class LoadProductList extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        Integer imagelength;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_productdetails.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {
            Map<String,String> map2 = null;
            //  product_details_lists = new ArrayList<Product_list>();
              allSampleDataGeneralInfo = new ArrayList<Entity_Generalinfo>();


            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


           // userpramas.add(new BasicNameValuePair(EndUrl.GetProductsDetails_id, Productid));
            if (arrayListkey.size()!=0) {
                for (int i = 0; i < arrayListkey.size(); i++) {
                    userpramas.add(new BasicNameValuePair(arrayListkey.get(i), arrayListvalue.get(i)));
                }
            }

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetProductstDetails_URL, "GET", userpramas);

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
                        JSONObject  post = new JSONObject(arrayresponse);
                        _pdtitle  = post.getString("name");
                       // _pdsubtitle  = post.getString("sku");
                      //  _pdimage = post.getString("image");
                     //   _totalReviewCount = post.getString("rating_count");
                        JSONArray postsgeneralinfo = post.optJSONArray("general_info");
                        if (postsgeneralinfo == null){

                        }else{
                            for (int i = 0; i < postsgeneralinfo.length(); i++) {
                                JSONObject postgeneralinfo = postsgeneralinfo.optJSONObject(i);

                                Map<String,String> map3 = new HashMap<String,String>();
                                Iterator iter = postgeneralinfo.keys();
                                while(iter.hasNext()){
                                    String key = (String)iter.next();
                                    String value = postgeneralinfo.getString(key);

                                    Log.e("testing","Key :" + key + "  Value :" + value);
                                    Entity_Generalinfo item = new Entity_Generalinfo();

                                    item.setProductName(key);
                                    item.setProductValue(value);



                                    allSampleDataGeneralInfo.add(item);
                                    map3.put(key,value);
                                }






                            }
                        }

                        String strresponseparameters = post.getString("parameters");
                        JSONArray responcearrayparameters = new JSONArray(strresponseparameters);
                        Log.e("testing", "responcearray value=" + responcearrayparameters);

                        for (int i2 = 0; i2 < responcearrayparameters.length(); i2++) {
                            JSONObject postparameters = responcearrayparameters.getJSONObject(i2);


                            map2 = new HashMap<String,String>();
                            Iterator iter = postparameters.keys();
                            while(iter.hasNext()){
                                String key = (String)iter.next();
                                String value = postparameters.getString(key);

                                Log.e("testing","Key :" + key + "  Value :" + value);
                                map2.put(key,value);
                            }



                        }



                        JSONArray posts2 = post.optJSONArray("images");
                        if (posts2 == null){

                        }else{
                            imagelength = posts2.length();
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);
                                _pdimage = post2.getString("url");
                                multipleimages=post2.getString("id");
                                images.add(post2.getString("url"));
                                Log.e("testing12", "multipleimagesid value=" + multipleimagesid);
                                XMENArray.add(post2.getString("url"));
                                //find the group position inside the list
                                //groupPosition = deptList.indexOf(headerInfo);

                            }
                        }

                        if (post.has("actual_price")){
                            _pdprice  = post.getString("actual_price");
                        }else{

                        }
                        if (post.has("offer_value")){
                            _descvalue = post.getString("offer_value");
                        }else{

                        }

                        if (post.has("discount_price")){
                            _pdsubprice = post.getString("discount_price");
                        }else{

                        }

                        if (post.has("variant_id")){

                            strvariantid = post.getString("variant_id").trim();
                            if (strvariantid == null || strvariantid.trim().length() == 0 || strvariantid.trim().equals("")){
                                _pid = post.getString("id");
                            }else{
                                _pid = strvariantid;
                            }

                        }else{
                            _pid = post.getString("id");
                        }



                        JSONArray postsvariants = post.optJSONArray("variations");
                        if (postsvariants == null){

                        }else {
                            for (int i2 = 0; i2 < postsvariants.length(); i2++) {

                                Entity_weightheader dm = new Entity_weightheader();
                                JSONObject postvariants = postsvariants.optJSONObject(i2);

                                dm.setHeaderTitle(postvariants.getString("name"));
                                dm.setHeadervalue(postvariants.getString("code"));
                                JSONArray postsproducts = postvariants.optJSONArray("options");
                                ArrayList<Entity_weightchild> singleItem = new ArrayList<Entity_weightchild>();

                                if (postsproducts == null) {

                                } else{
                                    for (int i1 = 0; i1 < postsproducts.length(); i1++) {
                                        JSONObject post2 = postsproducts.optJSONObject(i1);

                                        if (i1 == 0){

                                        }else{

                                        }
                                        singleItem.add(new Entity_weightchild(postvariants.getString("code"), post2.getString("name"), post2.getString("value"), post2.getString("is_selected"), map2));

                                    }
                                }
                                dm.setAllItemsInSection(singleItem);
                                allSampleDatavariables.add(dm);
                            }

                        }

                        JSONArray postsdescription = post.optJSONArray("description");
                        if (postsdescription == null){

                        }else {
                            for (int i2 = 0; i2 < postsdescription.length(); i2++) {

                                Entity_descriptionheader dm = new Entity_descriptionheader();
                                JSONObject postvariants = postsdescription.optJSONObject(i2);

                                dm.setHeaderTitle(postvariants.getString("name"));
                                JSONArray postsproducts = postvariants.optJSONArray("attributes");
                                ArrayList<Entity_descriptionchild> singleItem = new ArrayList<Entity_descriptionchild>();

                                if (postsproducts == null) {

                                } else{
                                    for (int i1 = 0; i1 < postsproducts.length(); i1++) {
                                        JSONObject post2 = postsproducts.optJSONObject(i1);

                                        if (i1 == 0){

                                        }else{

                                        }
                                        singleItem.add(new Entity_descriptionchild(post2.getString("key"), post2.getString("value")));

                                    }
                                }
                                dm.setAllItemsInSection(singleItem);
                                allSampleDatadescription.add(dm);
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
                pdprice.setText(_pdprice);
                pdsubprice.setText(_pdsubprice);
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


                Log.e("testing","_pdprice = "+_pdprice);
                if (_descvalue == null || _descvalue.trim().length() == 0 || _descvalue.trim().equals("NA")|| _descvalue.trim().equals("0")){
                    final String strrs = getResources().getString(R.string.Rs);
                    pdsubprice.setText(strrs+" "+_pdprice);
                    pdprice.setVisibility(View.GONE);
                }else{
                    discount_price.setText("(" +"-"+  _descvalue +"%"+ ")");
                    pdprice.setVisibility(View.VISIBLE);
                    final String strrs = getResources().getString(R.string.Rs);
                    pdsubprice.setText(strrs+" "+_pdsubprice);


                    pdprice.setText(strrs+" "+_pdprice);
                    pdprice.setPaintFlags(pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

                }

                mPager.setAdapter(new Image_Slider_Adapter(Activity_productdetails.this,XMENArray));
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(mPager);


                courses_Adapter = new Adapter_Generalinfo(Activity_productdetails.this,allSampleDataGeneralInfo, mCallback2);
                recyclergeneralinfo.setAdapter(courses_Adapter);
               /* courses_Adapter = new Adapter_Sizes(Activity_productdetails.this,allItemscourses, mCallback2);
                recyclercoursesoffered.setAdapter(courses_Adapter);
*/
                Log.e("testing","allSampleDatavariables = "+allSampleDatavariables);
                Adapter_ProductVariations adapter = new Adapter_ProductVariations(Activity_productdetails.this, allSampleDatavariables);

                recyclercoursesoffered.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                recyclercoursesoffered.setAdapter(adapter);

                Adapter_Productdescription adapter2 = new Adapter_Productdescription(Activity_productdetails.this, allSampleDatadescription);

                recyclerviewdescription.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                recyclerviewdescription.setAdapter(adapter2);



               /* // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == imagelength) {
                            currentPage = 0;
                        }
                        mPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 2500, 2500);*/

            }
            else {


            }



        }

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

    private class Getproducts extends AsyncTask<Void, Void, Void> {

        String headers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
          /*  pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        @Override
        protected Void doInBackground(Void... arg0) {
        //    HttpHandler sh = new HttpHandler();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


           // userpramas.add(new BasicNameValuePair(EndUrl.GetProducts_id, catid));

            // Making a request to url and getting response
         //   String jsonStr = sh.makeServiceCall(EndUrl.Offer_Products_URL);
            JSONObject jsonObj = jsonParser.makeHttpRequest(EndUrl.Offer_Products_URL, "GET", userpramas);

            if (jsonObj != null) {

                try {


                    Log.e("testing12", "jsonObj = " + jsonObj);

                 //   JSONObject response = new JSONObject(jsonObj.toString());


                    JSONArray posts = jsonObj.optJSONArray("data");
                    Log.e("testing", "jsonParser3333" + posts);

                    if (posts == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {

                        Log.e("testing", "jon122222211");
                        Log.e("testing", "jsonParser4444" + posts);


                            Log.e("testing", "" + posts);


                            headers = "Offer Products";


                            SectionDataModel dm = new SectionDataModel();
                            dm.setHeaderTitle("Offer Products");
                            // dm.setHeaderid(post.getString("categoryId"));
                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                            for (int i1 = 0; i1 < posts.length(); i1++) {
                                JSONObject post2 = posts.optJSONObject(i1);


                               /*
                                String Title2 = post2.getString("title");
                                String Productid = post2.getString("productId");
                                String Parentid = post2.getString("subcatName");
                                String Typename = post2.getString("location");

                               */

                                String finalimg = null;

                                finalimg = post2.getString("image");


                                Log.e("testing","product_id"+post2.getString("product_id"));
                                Log.e("testing","name"+post2.getString("name"));
                                Log.e("testing","brand"+post2.getString("name"));
                                Log.e("testing","price"+post2.getString("price"));
                                Log.e("testing","offer"+post2.getString("offer"));
                                Log.e("testing","discount_price"+post2.getString("discount_price"));

                              /*  JSONArray posts3 = post2.optJSONArray("multipleImages");
                                for (int i2 = 0; i2 < posts3.length(); i2++) {
                                    JSONObject post3 = posts3.optJSONObject(i2);

                                    finalimg = post3.getString("image");


                                    //find the group position inside the list
                                    //groupPosition = deptList.indexOf(headerInfo);
                                }*/

                                singleItem.add(new SingleItemModel(post2.getString("product_id"), post2.getString("name"), post2.getString("name"), post2.getString("sku"), post2.getString("price"), post2.getString("offer"), post2.getString("discount_price"), finalimg, "2"));


                            }

                            dm.setAllItemsInSection(singleItem);

                            allSampleData.add(dm);



                    }


                } catch (final JSONException e) {
                    Activity_productdetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          /*  Toast.makeText( Activity_productdetails.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();*/
                        }
                    });

                }
            } else {
                Activity_productdetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                      /*  Toast.makeText( Activity_productdetails.this,
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();*/
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
          /*  if (pDialog.isShowing())
                pDialog.dismiss();
            *//**
             * Updating parsed JSON data into ListView
             * *//*
            pDialog.dismiss();*/
            Log.e("testing","allSampleData = "+allSampleData);


            adapter = new RecyclerViewDataAdapter( Activity_productdetails.this, allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager( Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);
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
            mProgress = new ProgressDialog(Activity_productdetails.this);
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



            userpramas.add(new BasicNameValuePair(EndUrl.PostWhishlist_User_id, viewUserId));
            userpramas.add(new BasicNameValuePair(EndUrl.PostWhishlist_product_id, strvariantid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.PostWhishlist_URL;
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
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
/*
                Intent intent = new Intent(Activity_productdetails.this, MainActivity.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);*/

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }

    class CheckPincode extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_productdetails.this);
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



            userpramas.add(new BasicNameValuePair(EndUrl.CheckPincode_pincode, strpincode));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.CheckPincode_URL;
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
                    strmessage = json.getString("message");

                  /*  status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");*/
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

            if (strmessage == null || strmessage.trim().length() == 0){

            }/*else if (status.equals("success")) {


                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();

              *//*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*//*



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }*/else{
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
            }


        }

    }

    class PostAddtocart extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_productdetails.this);
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



            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_product_id, strvariantid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_user_id, viewUserId));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_quantity, qty));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.AddToCart_URL;
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


                Intent intent = new Intent(Activity_productdetails.this, MainActivity.class);


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);

              /*  Log.e("testing","status 2= "+status);
                if (strtype == null || strtype.length() == 0){

                    Log.e("testing","status 3= "+status);
                }else if (strtype.equals("‘verify_success")){

                    Log.e("testing","status 4= "+strtype);

                }else{

                }*/



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }

}
