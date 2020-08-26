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
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import g2evolution.Boutique.Adapter.Adapter_Generalinfo;
import g2evolution.Boutique.Adapter.Adapter_ProductVariations;
import g2evolution.Boutique.Adapter.Adapter_ProductVariationsSelection;
import g2evolution.Boutique.Adapter.Adapter_Productdescription;
import g2evolution.Boutique.Adapter.Image_Slider_Adapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.SingleItemModel;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.CartCount;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.Utils;
import g2evolution.Boutique.entit.Entity_Generalinfo;
import g2evolution.Boutique.entit.Entity_Sizes;
import g2evolution.Boutique.entit.Entity_descriptionchild;
import g2evolution.Boutique.entit.Entity_descriptionheader;
import g2evolution.Boutique.entit.Entity_weightchild;
import g2evolution.Boutique.entit.Entity_weightheader;
import me.relex.circleindicator.CircleIndicator;


public class Activity_productdetails extends AppCompatActivity implements RecyclerViewDataAdapter.OnItemClick, Adapter_Generalinfo.OnItemClickcourses, Adapter_ProductVariationsSelection.OnItemClickchildadapter {

    private static final String[] XMEN = {""};
    private static ViewPager mPager;
    private static int currentPage = 0;
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;
    ArrayList<Entity_weightheader> allSampleDatavariables;
    ArrayList<Entity_Generalinfo> allSampleDataGeneralInfo;
    ArrayList<Entity_descriptionheader> allSampleDatadescription;
    RecyclerViewDataAdapter adapter;
    ImageView back, details_image;
    TextView quantity, addcart, booknow, pdtitle, pdsubtitle, pdprice, pdsubprice, textrating, textreviews, textratingreviews, butdecrement, butincrement, textdiscount;
    int currentNos;
    String multipleimages, multipleimagesid, strbuttonstatus, categoryName, Productid, UserId, cart_id, qty, headers, _pid, _pdimage, _pdtitle, _pdsubtitle, _pdprice, _pdsubprice, _pdabout, __shipppingAmount, _TaxandPrice, _NetAmount, _descvalue, _totalReviewCount, _avgRating;
    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler;
    RelativeLayout relativemain;
    HashMap<String, String> url_maps = new HashMap<String, String>();
    WebView webviewdesc;
    ArrayList<String> images = new ArrayList<String>();
    String strpincode, finalprice;
    TextView check_text;
    EditText editpincode;
    String category, subcategoryname, catid, viewUserId;
    String[] Name = new String[]{"506244", "576244", "596244", "506244",};
    TextView discount_price;
    RelativeLayout relativelayoutimages;
    TextView wishlist;
    RecyclerView recyclergeneralinfo;
    RecyclerView recyclercoursesoffered;
    RecyclerView recyclerviewdescription;
    String[] strtitle = new String[]{"Bangalore", "Lucknow", "Pune", "Trivandrum", "Kochi"};
    Adapter_Generalinfo courses_Adapter;
    Adapter_Generalinfo.OnItemClickcourses mCallback2;
    String strvariantid;
    String strsizeid, strsizename, amount;
    List<String> arrayListkey = new ArrayList<String>();
    List<String> arrayListvalue = new ArrayList<String>();
    String id;
    CartCount cartCount;
    private RecyclerViewDataAdapter.OnItemClick mCallback;
    private Adapter_ProductVariationsSelection.OnItemClickchildadapter mCallbackchildadapter;
    private ProgressDialog pDialog;
    private String TAG = Activity_productdetails.class.getSimpleName();
    private ArrayList<String> XMENArray = new ArrayList<String>();
    private ArrayList<Entity_Sizes> allItemscourses = new ArrayList<Entity_Sizes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);


        Bundle bundle = this.getIntent().getExtras();
        HashMap hashMap = (HashMap) bundle.getSerializable("HashMap");
        arrayListkey = new ArrayList<String>();
        arrayListvalue = new ArrayList<String>();
        Iterator myVeryOwnIterator = hashMap.keySet().iterator();
        while (myVeryOwnIterator.hasNext()) {
            String key = (String) myVeryOwnIterator.next();
            String value = (String) hashMap.get(key);
            Log.e("testing", "Key: " + key + " Value: " + value);
            if (key.equals("id")) {
            }
            arrayListkey.add(key);
            arrayListvalue.add(value);
        }

        wishlist = (TextView) findViewById(R.id.wishlist);
        wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("testing", "strvariantid = " + strvariantid);
                new PostWhislist().execute();
                //Toast.makeText(Activity_productdetails.this, "Added to WishList", Toast.LENGTH_SHORT).show();
            }
        });
       /* cartImage = (ImageView) findViewById(R.id.cart_image);
        wishlistImage = (ImageView) findViewById(R.id.wish_list_image);
        searchImage = (ImageView) findViewById(R.id.search_image);*/

        strbuttonstatus = "";

        mCallback = this;
        mCallbackchildadapter = this;
        allSampleData = new ArrayList<SectionDataModel>();
        allSampleDatavariables = new ArrayList<Entity_weightheader>();
        allSampleDatadescription = new ArrayList<Entity_descriptionheader>();
        my_recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);


        SharedPreferences prefuserdata23 = getSharedPreferences("regId", 0);
        viewUserId = prefuserdata23.getString("UserId", "");
        Log.e("testing", "viewUserId = " + viewUserId);

        SharedPreferences prefuserdata = this.getSharedPreferences("category", 0);
        category = prefuserdata.getString("category", "");
        catid = prefuserdata.getString("catid", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");

        Log.e("testing", "catid = " + catid);


        check_text = (TextView) findViewById(R.id.check_text);
        editpincode = (EditText) findViewById(R.id.editpincode);
        relativelayoutimages = (RelativeLayout) findViewById(R.id.relativelayoutimages);
        mPager = (ViewPager) findViewById(R.id.pager);
        check_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpincode = editpincode.getText().toString();

                if (strpincode == null || strpincode.trim().length() != 6 || strpincode.trim().equals("null")) {
                    Toast.makeText(Activity_productdetails.this, "Please Enter Correct Pincode", Toast.LENGTH_SHORT).show();
                } else {

                    new CheckPincode().execute();

                }
            }
        });

        mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (images == null || images.size() == 0) {

                } else {
                    Intent intent = new Intent(Activity_productdetails.this, Gallery_Images.class);
                    intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME, images);
                    Log.e("testing12345", "images=====multiple image" + images);
                    startActivity(intent);
                }

            }
        });

        recyclergeneralinfo = (RecyclerView) findViewById(R.id.recyclergeneralinfo);
        recyclergeneralinfo.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager11 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManager12 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        recyclergeneralinfo.setLayoutManager(mLayoutManager11);
        mCallback2 = this;


        recyclercoursesoffered = (RecyclerView) findViewById(R.id.recyclerviewsizes);
        recyclercoursesoffered.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);
        recyclercoursesoffered.setLayoutManager(mLayoutManager);

        recyclerviewdescription = (RecyclerView) findViewById(R.id.recyclerviewdescription);
        recyclerviewdescription.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false);

        recyclerviewdescription.setLayoutManager(mLayoutManager3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty = getResources().getString(R.string.whatsappnumber);
                String toNumber = "+91" + qty; // Replace with mobile phone number without +Sign or leading zeros.
                String text = "You are requesting chat from Product Details Page please continue chatting...";// Replace with your message.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=" + toNumber + "&text=" + text));
                startActivity(intent);


            }
        });

        textrating = (TextView) findViewById(R.id.textrating);
        textreviews = (TextView) findViewById(R.id.textreviews);
        textratingreviews = (TextView) findViewById(R.id.textratingreviews);

        butdecrement = (TextView) findViewById(R.id.butdecrement);
        butincrement = (TextView) findViewById(R.id.butincrement);
        details_image = (ImageView) findViewById(R.id.details_image);


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
                intent.putStringArrayListExtra(Gallery_Images.EXTRA_NAME, images);
                Log.e("testing12345", "images=====multiple image" + images);
                startActivity(intent);

            }
        });

        back = (ImageView) findViewById(R.id.back);
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
        textdiscount = (TextView) findViewById(R.id.textdiscount);
        pdtitle = (TextView) findViewById(R.id.pdtitle);
        pdsubtitle = (TextView) findViewById(R.id.pdsubtitle);
        pdprice = (TextView) findViewById(R.id.pdprice);
        pdsubprice = (TextView) findViewById(R.id.pdsubprice);
        relativemain = (RelativeLayout) findViewById(R.id.relativemain);
        relativemain.setVisibility(View.GONE);


        SharedPreferences prefuserdata3 = this.getSharedPreferences("ProDetails", 0);
        Productid = prefuserdata3.getString("Proid", "");
        categoryName = prefuserdata3.getString("categoryName", "");


        Log.e("testing", "Productid = " + Productid);

        SharedPreferences prefuserdata5 = getSharedPreferences("regId", 0);
        UserId = prefuserdata5.getString("UserId", "");
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "addtocart";
                qty = quantity.getText().toString();

                Log.e("testing", "strvariantid = " + strvariantid);

                if (qty == null || qty.equals("0") || qty.length() == 0) {

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                } else if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);
                    finish();

                } else {
                    new PostAddtocart().execute();
                }
            }
        });

        booknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                strbuttonstatus = "booknow";
                qty = quantity.getText().toString();


                if (qty == null || qty.equals("0") || qty.length() == 0) {

                    Toast.makeText(Activity_productdetails.this, "Select Quantity", Toast.LENGTH_LONG).show();

                } else if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    Intent intent = new Intent(Activity_productdetails.this, Login.class);
                    startActivity(intent);
                    finish();

                } else {


                }

            }
        });


        textratingreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")) {

                    ShowLogoutAlert1("Please login");

                } else {

                    Intent i = new Intent(Activity_productdetails.this, Activity_review.class);

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

                try {
                    currentNos
                            = Integer.parseInt(quantity.getText().toString());
                } catch (NumberFormatException numberEx) {
                    System.out.print(numberEx);
                }

                quantity.setText(String.valueOf(++currentNos));
                qty = quantity.getText().toString();
            }
        });

        butdecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int currentNos = Integer.parseInt(quantity.getText().toString());
                if (currentNos <= 1) {


                } else {
                    quantity.setText(String.valueOf(--currentNos));
                }


                qty = quantity.getText().toString();

            }
        });

        allSampleData = new ArrayList<SectionDataModel>();
        allSampleDatavariables = new ArrayList<Entity_weightheader>();
        allSampleDatadescription = new ArrayList<Entity_descriptionheader>();
        my_recycler = (RecyclerView) findViewById(R.id.recycler_view);
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


    @Override
    public void onStop() {

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

    @Override
    public void onClickedItemchildadapter(int pos, String qty, HashMap hashMap, int status) {
        Intent intent = new Intent(Activity_productdetails.this, Activity_productdetails.class);
        Bundle extras = new Bundle();
        extras.putSerializable("HashMap", (Serializable) hashMap);
        intent.putExtras(extras);
        startActivity(intent);
        finish();
    }


    class LoadProductList extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;
        Integer imagelength;
        private ProgressDialog pDialog;

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
            Map<String, String> map2 = null;
            //  product_details_lists = new ArrayList<Product_list>();
            allSampleDataGeneralInfo = new ArrayList<Entity_Generalinfo>();


            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            // userpramas.add(new BasicNameValuePair(EndUrl.GetProductsDetails_id, Productid));
            if (arrayListkey.size() != 0) {
//                id = arrayListvalue.get(0);
                for (int i = 0; i < arrayListkey.size(); i++) {
                    if (arrayListkey.get(i).equals("id")) {
                        id = arrayListvalue.get(i);
                    }
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
                    JSONObject jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {

                        status = json.getString("status");
                        strresponse = json.getString("response");
                        String arrayresponse = json.getString("data");
                        Log.e("testing", "adapter value=" + arrayresponse);
                        JSONObject post = new JSONObject(arrayresponse);
                        _pdtitle = post.getString("name");
                        // _pdsubtitle  = post.getString("sku");
                        //  _pdimage = post.getString("image");
                        //   _totalReviewCount = post.getString("rating_count");
                        JSONArray postsgeneralinfo = post.optJSONArray("general_info");
                        if (postsgeneralinfo == null) {

                        } else {
                            for (int i = 0; i < postsgeneralinfo.length(); i++) {
                                JSONObject postgeneralinfo = postsgeneralinfo.optJSONObject(i);

                                Map<String, String> map3 = new HashMap<String, String>();
                                Iterator iter = postgeneralinfo.keys();
                                while (iter.hasNext()) {
                                    String key = (String) iter.next();
                                    String value = postgeneralinfo.getString(key);

                                    Log.e("testing", "Key :" + key + "  Value :" + value);
                                    Entity_Generalinfo item = new Entity_Generalinfo();

                                    item.setProductName(key);
                                    item.setProductValue(value);


                                    allSampleDataGeneralInfo.add(item);
                                    map3.put(key, value);
                                }


                            }
                        }

                        String strresponseparameters = post.getString("parameters");
                        JSONArray responcearrayparameters = new JSONArray(strresponseparameters);
                        Log.e("testing", "responcearray value=" + responcearrayparameters);

                        for (int i2 = 0; i2 < responcearrayparameters.length(); i2++) {
                            JSONObject postparameters = responcearrayparameters.getJSONObject(i2);


                            map2 = new HashMap<String, String>();
                            Iterator iter = postparameters.keys();
                            while (iter.hasNext()) {
                                String key = (String) iter.next();
                                String value = postparameters.getString(key);

                                Log.e("testing", "Key :" + key + "  Value :" + value);
                                map2.put(key, value);
                            }


                        }


                        JSONArray posts2 = post.optJSONArray("images");
                        if (posts2 == null) {

                        } else {
                            imagelength = posts2.length();
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);
                                _pdimage = post2.getString("url");
                                multipleimages = post2.getString("id");
                                images.add(post2.getString("url"));
                                Log.e("testing12", "multipleimagesid value=" + multipleimagesid);
                                XMENArray.add(post2.getString("url"));
                                //find the group position inside the list
                                //groupPosition = deptList.indexOf(headerInfo);

                            }
                        }

                        if (post.has("actual_price")) {
                            _pdprice = post.getString("actual_price");
                        } else {

                        }
                        if (post.has("offer_value")) {
                            _descvalue = post.getString("offer_value");
                        } else {

                        }

                        if (post.has("discount_price")) {
                            _pdsubprice = post.getString("discount_price");
                        } else {

                        }

                        if (post.has("final_price")) {
                            finalprice = post.getString("final_price");
                        } else {

                        }


                        if (post.has("variant_id")) {

                            strvariantid = post.getString("variant_id").trim();
                            if (strvariantid == null || strvariantid.trim().length() == 0 || strvariantid.trim().equals("")) {
                                _pid = post.getString("id");
                            } else {
                                _pid = strvariantid;
                            }

                        } else {
                            _pid = post.getString("id");
                        }

                        strvariantid = _pid;

                        JSONArray postsvariants = post.optJSONArray("variations");
                        if (postsvariants == null) {

                        } else {
                            for (int i2 = 0; i2 < postsvariants.length(); i2++) {

                                Entity_weightheader dm = new Entity_weightheader();
                                JSONObject postvariants = postsvariants.optJSONObject(i2);

                                dm.setHeaderTitle(postvariants.getString("name"));
                                dm.setHeadervalue(postvariants.getString("code"));
                                JSONArray postsproducts = postvariants.optJSONArray("options");
                                ArrayList<Entity_weightchild> singleItem = new ArrayList<Entity_weightchild>();

                                if (postsproducts == null) {

                                } else {
                                    for (int i1 = 0; i1 < postsproducts.length(); i1++) {
                                        JSONObject post2 = postsproducts.optJSONObject(i1);

                                        if (i1 == 0) {

                                        } else {

                                        }
                                        singleItem.add(new Entity_weightchild(postvariants.getString("code"), post2.getString("name"), post2.getString("value"), post2.getString("is_selected"), map2));

                                    }
                                }
                                dm.setAllItemsInSection(singleItem);
                                allSampleDatavariables.add(dm);
                            }

                        }

                        JSONArray postsdescription = post.optJSONArray("description");
                        if (postsdescription == null) {

                        } else {
                            for (int i2 = 0; i2 < postsdescription.length(); i2++) {

                                Entity_descriptionheader dm = new Entity_descriptionheader();
                                JSONObject postvariants = postsdescription.optJSONObject(i2);

                                dm.setHeaderTitle(postvariants.getString("name"));
                                JSONArray postsproducts = postvariants.optJSONArray("attributes");
                                ArrayList<Entity_descriptionchild> singleItem = new ArrayList<Entity_descriptionchild>();

                                if (postsproducts == null) {

                                } else {
                                    for (int i1 = 0; i1 < postsproducts.length(); i1++) {
                                        JSONObject post2 = postsproducts.optJSONObject(i1);

                                        if (i1 == 0) {

                                        } else {

                                        }
                                        singleItem.add(new Entity_descriptionchild(post2.getString("key"), post2.getString("value")));

                                    }
                                }
                                dm.setAllItemsInSection(singleItem);
                                allSampleDatadescription.add(dm);
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


                relativemain.setVisibility(View.VISIBLE);
                textdiscount.setVisibility(View.GONE);


                if (_descvalue == null || _descvalue.length() == 0 || _descvalue.equals("NA")) {

                    textdiscount.setVisibility(View.INVISIBLE);

                } else {
                    textdiscount.setVisibility(View.VISIBLE);
                    textdiscount.setText((_descvalue + "%"));
                }


                pdtitle.setText(_pdtitle);
                pdsubtitle.setText(_pdsubtitle);
                pdprice.setText(_pdprice);
                pdsubprice.setText(_pdsubprice);
                final String mimeType = "text/html";
                final String encoding = "UTF-8";
                webviewdesc.loadDataWithBaseURL("", _pdabout, mimeType, encoding, "");


                textrating.setText(_avgRating);

                if (_totalReviewCount == null || _totalReviewCount.length() == 0 || _totalReviewCount.equals("null")) {

                    textreviews.setText("0" + " Rating and Reviews");
                } else {

                    textreviews.setText(_totalReviewCount + " Rating and Reviews");

                }

                if (_pdimage == null || _pdimage.length() == 0 || _pdimage.equals("null")) {

                } else {
                    Glide.with(Activity_productdetails.this)
                            .load(Uri.parse(_pdimage))
                            .error(R.drawable.car)
                            .into(details_image);

                }


                Log.e("testing", "_pdprice = " + _pdprice);
                if (_descvalue == null || _descvalue.trim().length() == 0 || _descvalue.trim().equals("NA") || _descvalue.trim().equals("0")) {
                    final String strrs = getResources().getString(R.string.Rs);
                    pdsubprice.setText(strrs + " " + _pdprice);
                    amount = _pdprice;
                    pdprice.setVisibility(View.GONE);
                } else {
                    discount_price.setText("(" + "-" + _descvalue + "%" + ")");
                    pdprice.setVisibility(View.VISIBLE);
                    final String strrs = getResources().getString(R.string.Rs);
                    pdsubprice.setText(strrs + " " + _pdsubprice);
                    amount = _pdprice;

                    pdprice.setText(strrs + " " + _pdprice);
                    pdprice.setPaintFlags(pdprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }

                mPager.setAdapter(new Image_Slider_Adapter(Activity_productdetails.this, XMENArray));
                CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
                indicator.setViewPager(mPager);


                courses_Adapter = new Adapter_Generalinfo(Activity_productdetails.this, allSampleDataGeneralInfo, mCallback2);
                recyclergeneralinfo.setAdapter(courses_Adapter);

                Log.e("testing", "allSampleDatavariables = " + allSampleDatavariables);
                Adapter_ProductVariations adapter = new Adapter_ProductVariations(Activity_productdetails.this, allSampleDatavariables, mCallbackchildadapter);
                recyclercoursesoffered.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                recyclercoursesoffered.setAdapter(adapter);

                Adapter_Productdescription adapter2 = new Adapter_Productdescription(Activity_productdetails.this, allSampleDatadescription);

                recyclerviewdescription.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

                recyclerviewdescription.setAdapter(adapter2);


            } else {


            }


        }

    }

//-----------------------------------------------------------------------------------------------------


    private class Getproducts extends AsyncTask<Void, Void, Void> {

        String headers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //    HttpHandler sh = new HttpHandler();

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            JSONObject jsonObj = jsonParser.makeHttpRequest(EndUrl.Offer_Products_URL, "GET", userpramas);

            if (jsonObj != null) {

                try {


                    Log.e("testing12", "jsonObj = " + jsonObj);



                    JSONArray posts = jsonObj.optJSONArray("data");
                    Log.e("testing", "jsonParser3333" + posts);

                    if (posts == null) {
                        Log.e("testing", "jon11111111111111111");

                    } else {

                        Log.e("testing", "jon122222211");
                        Log.e("testing", "jsonParser4444" + posts);


                        Log.e("testing", "" + posts);


                        headers = "Offer Products";


                        SectionDataModel dm = new SectionDataModel();
                        dm.setHeaderTitle("Offer Products");
                        ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
                        for (int i1 = 0; i1 < posts.length(); i1++) {
                            JSONObject post2 = posts.optJSONObject(i1);

                            String finalimg = null;

                            finalimg = post2.getString("image");


                            Log.e("testing", "product_id" + post2.getString("product_id"));
                            Log.e("testing", "name" + post2.getString("name"));
                            Log.e("testing", "brand" + post2.getString("name"));
                            Log.e("testing", "price" + post2.getString("price"));
                            Log.e("testing", "offer" + post2.getString("offer"));
                            Log.e("testing", "discount_price" + post2.getString("discount_price"));


                            singleItem.add(new SingleItemModel(post2.getString("product_id"), post2.getString("name"), post2.getString("name"), post2.getString("sku"), post2.getString("price"), post2.getString("offer"), post2.getString("discount_price"), finalimg, "2"));


                        }

                        dm.setAllItemsInSection(singleItem);

                        allSampleData.add(dm);


                    }


                } catch (final JSONException e) {
                    Activity_productdetails.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                Activity_productdetails.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Log.e("testing", "allSampleData = " + allSampleData);


            adapter = new RecyclerViewDataAdapter(Activity_productdetails.this, allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(Activity_productdetails.this, LinearLayoutManager.VERTICAL, false));

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
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);
        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.PostWhishlist_User_id, viewUserId));
            userpramas.add(new BasicNameValuePair(EndUrl.PostWhishlist_product_id, id));

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
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();


            } else if (status.equals("failure")) {
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            } else {

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
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);


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

            if (strmessage == null || strmessage.trim().length() == 0) {

            } else {
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
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
//            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_product_id, id));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_product_id, strvariantid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_user_id, viewUserId));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_quantity, qty));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_proid, id));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_amount, amount));
            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_final_price, finalprice));

            userpramas.add(new BasicNameValuePair(EndUrl.AddToCart_type, "1"));
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


                if (!strmessage.equals("Already this item added into that cart.")) {
                    Utils.Cart_Count = Utils.Cart_Count + 1;

                }
                Toast.makeText(getApplicationContext(), strmessage, Toast.LENGTH_LONG).show();


            } else if (status.equals("failure")) {
                Toast.makeText(Activity_productdetails.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);

            } else {

            }


        }

    }

}
