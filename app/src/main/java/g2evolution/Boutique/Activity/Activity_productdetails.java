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
import java.util.Timer;
import java.util.TimerTask;

import g2evolution.Boutique.Adapter.Adapter_Sizes;
import g2evolution.Boutique.Adapter.Image_Slider_Adapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter2;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.SingleItemModel;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_Sizes;
import me.relex.circleindicator.CircleIndicator;


public class Activity_productdetails extends AppCompatActivity implements RecyclerViewDataAdapter.OnItemClick, Adapter_Sizes.OnItemClickcourses{

    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;
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
    TextView textrating, textreviews;

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
    TextView text;
    ImageView cartImage,wishlistImage,searchImage;

    TextView discount_price;

    RelativeLayout relativelayoutimages;

    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final String[] XMEN= {""};
    private ArrayList<String> XMENArray = new ArrayList<String>();

    TextView wishlist;

    RecyclerView recyclercoursesoffered;
    String [] strtitle = new String[]{"Bangalore", "Lucknow","Pune","Trivandrum","Kochi"};
    Adapter_Sizes courses_Adapter;
    Adapter_Sizes.OnItemClickcourses mCallback2;

    private ArrayList<Entity_Sizes> allItemscourses = new ArrayList<Entity_Sizes>();

    String strsizeid, strsizename;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        text = (TextView) findViewById(R.id.text);



        wishlist=(TextView)findViewById(R.id.wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PostWhislist().execute();
                //Toast.makeText(Activity_productdetails.this, "Added to WishList", Toast.LENGTH_SHORT).show();
            }
        });
        cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);
        text.setText("Product Details");

        strbuttonstatus = "";

        mCallback=this;
        allSampleData = new ArrayList<SectionDataModel>();
        my_recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);


        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
        viewUserId = prefuserdata23.getString("UserId", "");

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

        recyclercoursesoffered = (RecyclerView) findViewById(R.id.recyclerviewsizes);
        recyclercoursesoffered.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        recyclercoursesoffered.setLayoutManager(mLayoutManager);
        mCallback2 = this;



    /*    SharedPreferences prefuserdata1 = this.getSharedPreferences("ProDetails", 0);
        category = prefuserdata1.getString("category", "");
        catid = prefuserdata1.getString("catid", "");
        subcategoryname = prefuserdata1.getString("subcategoryname", "");

*/

        // linearlayout1 = (LinearLayout) findViewById(R.id.linearlayout1);

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(Activity_productdetails.this,Activity_cart.class);
                startActivity(intent);

            }
        });

        wishlistImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent =new Intent(Activity_productdetails.this,Activity_WishList.class);
                startActivity(intent);*/

            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Activity_productdetails.this,Activity_search.class);
                startActivity(intent);

            }
        });

        textrating = (TextView) findViewById(R.id.textrating);
        textreviews = (TextView) findViewById(R.id.textreviews);

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


       /* spinnerAdapter adapter = new spinnerAdapter(Activity_productdetails.this, R.layout.spinner_item);
        adapter.addAll(Name);
        adapter.add("Select Pincode");
        spinapartment.setAdapter(adapter);
        spinapartment.setSelection(adapter.getCount());

            *//*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*//*

        // Spinner on item click listener
        spinapartment
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int position, long arg3) {
                        // TODO Auto-generated method stub
                        // Locate the textviews in activity_main.xml
                        if(spinapartment.getSelectedItem() == "Select Pincode")
                        {
                            //Do nothing.
                        }
                        else{

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                        // TODO Auto-generated method stub
                    }
                });
*/


        TextView size_guide_text = (TextView) findViewById(R.id.size_guide_text);
        size_guide_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog logoutdialog = new Dialog(Activity_productdetails.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                logoutdialog.setCancelable(true);
                logoutdialog.setCanceledOnTouchOutside(true);

                LayoutInflater inflater = (LayoutInflater) Activity_productdetails.this.getSystemService(Activity_productdetails.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.size_guide_custom_layout, null);
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



        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "addtocart";
                qty = quantity.getText().toString();


                if(qty == null || qty.equals("0")  || qty.length() == 0){

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                }else if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);
                    finish();

                }else{

                    if (intsizes == null || intsizes == 0 ){
                        strsizeid = _pid;
                        new PostAddtocart().execute();
                    }else{
                        if (strsizeid == null || strsizeid.trim().length() == 0 || strsizeid.trim().equals("null")){
                            Toast.makeText(Activity_productdetails.this, "Please select size", Toast.LENGTH_SHORT).show();
                        }else{
                            new PostAddtocart().execute();
                        }
                    }



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
                    prefeditor.putString("reviewid", "" + Productid);

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
        my_recycler = (RecyclerView)findViewById(R.id.recycler_view);
        my_recycler.setHasFixedSize(true);


        ConnectionDetector cd = new ConnectionDetector(Activity_productdetails.this);

        if (cd.isConnectingToInternet()) {

            //  new Loader().execute();
            new LoadProductList().execute();

            //---------offer products-----------
            //  new Getproducts().execute();
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

                        // pdprice.setVisibility(View.VISIBLE);
                        final String strrs = getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+_pdsubprice);


                        //  pdprice.setText(strrs+" "+_pdprice);
                        //  pdprice.setPaintFlags(pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

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

            //  product_details_lists = new ArrayList<Product_list>();


            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetProductsDetails_id, Productid));


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
                        //  JSONArray responcearray = new JSONArray(arrayresponse);
                        //  Log.e("testing", "responcearray value=" + responcearray);

                     /*   for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();*/




                        _pdimage = post.getString("image");
                        _pdtitle  = post.getString("name");
                        _pdsubtitle  = post.getString("sku");
                        Log.e("testing","_pdtitle = "+_pdtitle);



                        _totalReviewCount = post.getString("rating_count");

                        if (post.has("actual_price")){
                            _pdprice  = post.getString("actual_price");
                        }else{

                        }
                        if (post.has("offer_price")){
                            _descvalue = post.getString("offer_price");
                        }else{

                        }

                        if (post.has("discount_price")){
                            _pdsubprice = post.getString("discount_price");
                        }else{

                        }


                        //  _pdabout  = post.getString("description");

                        _pid = post.getString("id");
                          /*  _totalReviewCount = post.getString("totalReviewCount");
                            _avgRating = post.getString("avgRating");*/


                   /*     String straddition = json.getString("additional_information");
                        Log.e("testing", "adapter value=" + arrayresponse);

                        JSONObject  postaddition = new JSONObject(straddition);*/


                        JSONArray postssizes = post.optJSONArray("sizes");

                        if (postssizes == null || postssizes.length() == 0){

                        }else{
                            intsizes = postssizes.length();
                            for (int i1 = 0; i1 < postssizes.length(); i1++) {
                                JSONObject postsizes = postssizes.optJSONObject(i1);

                                Entity_Sizes feedInfo = new Entity_Sizes();
                                feedInfo.setId(postsizes.getString("id"));
                                feedInfo.setProductName(postsizes.getString("size"));
                                allItemscourses.add(feedInfo);
                            }
                        }

                        JSONArray posts2 = post.optJSONArray("images");
                        imagelength = posts2.length();
                        for (int i1 = 0; i1 < posts2.length(); i1++) {
                            JSONObject post2 = posts2.optJSONObject(i1);
                            multipleimages=post2.getString("id");
                            images.add(post2.getString("url"));
                            Log.e("testing12", "multipleimagesid value=" + multipleimagesid);
                            XMENArray.add(post2.getString("url"));
                            //find the group position inside the list
                            //groupPosition = deptList.indexOf(headerInfo);

                        }




                        // }
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


                courses_Adapter = new Adapter_Sizes(Activity_productdetails.this,allItemscourses, mCallback2);
                recyclercoursesoffered.setAdapter(courses_Adapter);


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
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(EndUrl.Offer_Products_URL);


            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node

                    Log.e("testing12", "jsonObj = " + jsonObj);

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
                            headers = post.getString("OfferProduct");

                            Log.e("testing", "name is 11= " + post.getString("OfferProduct"));


                            String Title = post.getString("OfferProduct");

                            SectionDataModel dm = new SectionDataModel();


                            dm.setHeaderTitle(post.getString("OfferProduct"));
                            // dm.setHeaderid(post.getString("categoryId"));

                            JSONArray posts2 = post.optJSONArray("Products");
                            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);


                               /*
                                String Title2 = post2.getString("title");
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

                                singleItem.add(new SingleItemModel(post2.getString("productId"), post2.getString("categoryName"), post2.getString("brandName"), post2.getString("title"), post2.getString("price"), post2.getString("discountValue"), post2.getString("afterDiscount"), finalimg, post2.getString("stockQuantity")));


                            }

                            dm.setAllItemsInSection(singleItem);

                            allSampleData.add(dm);

                        }

                    }


                } catch (final JSONException e) {
                    Activity_productdetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( Activity_productdetails.this,
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Activity_productdetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText( Activity_productdetails.this,
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
          /*  if (pDialog.isShowing())
                pDialog.dismiss();
            *//**
             * Updating parsed JSON data into ListView
             * *//*
            pDialog.dismiss();*/
            adapter = new RecyclerViewDataAdapter( Activity_productdetails.this, allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager( Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);
        }

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

                        Intent intent = new Intent(Activity_productdetails.this, Activity_cart.class);

                        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("userid", "" + UserId);
                        prefeditor.putString("productid", "" + _pid);
                        prefeditor.putString("addtocart", "" + strbuttonstatus);

                        prefeditor.commit();
                        startActivity(intent);


                    }else if (strbuttonstatus.equals("booknow")){

                        //   Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(Activity_productdetails.this, Activity_address.class);

                        SharedPreferences prefuserdata = getSharedPreferences("regcart", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("userid", "" + UserId);
                        prefeditor.putString("productid", "" + _pid);
                        prefeditor.putString("bookstatus", "" + strbuttonstatus);

                        prefeditor.commit();
                        startActivity(intent);

                    }

                }else {

                    // Toast.makeText(Activity_productdetails.this, ""+message, Toast.LENGTH_LONG).show();
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

                String price  = post.getString("price ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

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
            userpramas.add(new BasicNameValuePair(EndUrl.PostWhishlist_product_id, Productid));

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



            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_product_id, strsizeid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_product_name, strsizename));
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
