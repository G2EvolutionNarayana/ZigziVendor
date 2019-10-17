package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_Product_List;
import g2evolution.Boutique.Adapter.Adapter_Subcategory_list;
import g2evolution.Boutique.Adapter.Adapter_child_subcategory_list;
import g2evolution.Boutique.Adapter.Brands_Adapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.Brands_List;
import g2evolution.Boutique.FeederInfo.FeederInfo_Product_List;
import g2evolution.Boutique.FeederInfo.FeederInfo_Subcategory_list;
import g2evolution.Boutique.FeederInfo.FeederInfo_child_subcategory_list;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class Product_List extends AppCompatActivity implements Adapter_Subcategory_list.OnItemClick ,Adapter_child_subcategory_list.OnItemClick1,Brands_Adapter.OnItemClickbrands {

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_Subcategory_list> allItems1 = new ArrayList<FeederInfo_Subcategory_list>();
    private RecyclerView mFeedRecyler;
    Adapter_Subcategory_list mAdapterFeeds;

    GridLayoutManager lLayout;

    private Adapter_Subcategory_list.OnItemClick mCallback;


    private Adapter_child_subcategory_list.OnItemClick1 mCallback1;

    String  products,total_record;

    private ArrayList<FeederInfo_child_subcategory_list> allItems2 = new ArrayList<FeederInfo_child_subcategory_list>();
    private RecyclerView mFeedRecyler1;
    Adapter_child_subcategory_list mAdapterFeeds1;

    GridLayoutManager lLayout1;


    static String UserId, Username, Usermail, Usermob;

    private ArrayList<FeederInfo_Product_List> allItems3 = new ArrayList<FeederInfo_Product_List>();
    private RecyclerView mFeedRecyler2;
    Adapter_Product_List mAdapterFeeds2;

    GridLayoutManager lLayout2;
    String categoryname, childid, categoryname_heading;
    String subcategoryname = "";
    ImageView back;
    Integer introwposition;
    TextView lowtohigh,hightolow,newest,offer;
    String searchType;
    ImageView filter;
    ImageView imgsort;
    String strlowtohigh = "";
    String strhightolow = "";
    String strnewest = "";
    String strofferproduct = "";
    String strpricefilter = "";
    String strminprice = "";
    String strmaxprice = "";


    JSONParser jsonParser = new JSONParser();
    String message;
    String response;

    Brands_Adapter.OnItemClickbrands mCallbackbrands;
    private ArrayList<Brands_List> brands_lists = new ArrayList<Brands_List>();
    private String liste ;
    Brands_Adapter brands_adapter;

    ArrayList<String> arrayListbrands = new ArrayList<String>();
    RecyclerView recyclerViewdialog;

    static RelativeLayout counterValuePanel;
    static TextView count1;

    public  static  String cartmenucount;
    ImageView imgsearch;
    static ImageView imgcart;
    static String status;
    String strlist;
    String strlistbrnadid;
     TextView txt_brands;
    static Context context;
     TextView textnodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);
        context = this;
        SharedPreferences prefuserdata = getSharedPreferences("ProDetails", 0);
        categoryname = prefuserdata.getString("category", "");
        subcategoryname = prefuserdata.getString("subcategoryname", "");
        categoryname_heading = prefuserdata.getString("categoryname", "");


        SharedPreferences prefuserdata2 = getSharedPreferences("regId", 0);
        UserId = prefuserdata2.getString("UserId", "");
        Username = prefuserdata2.getString("Username", "");
        Usermail = prefuserdata2.getString("Usermail", "");
        Usermob = prefuserdata2.getString("Usermob", "");


        Log.e("testing123","subcategoryname"+subcategoryname);
        Log.e("testing123","categoryname_heading"+categoryname_heading);
        childid = "All";

        Log.e("testing123", "categoryid=====productlist = ===" + categoryname);
        Log.e("testing123", "subcategoryid====productlits==== = " + subcategoryname);


        textnodata = (TextView) findViewById(R.id.textnodata);
        textnodata.setVisibility(View.GONE);
        counterValuePanel = (RelativeLayout) findViewById(R.id.counterValuePanel);
        count1 = (TextView) findViewById(R.id.count1);
        imgsearch = (ImageView) findViewById(R.id.imgsearch);
        imgcart = (ImageView) findViewById(R.id.imgcart);
        imgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Product_List.this, Activity_search.class);
                SharedPreferences prefuserdata = getSharedPreferences("searchparam", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("category_id", "" + categoryname);
                prefeditor.putString("sub_category_id", "" + subcategoryname);
                prefeditor.putString("child_category_id", "" + childid);


                prefeditor.commit();
                startActivity(i);



            }
        });

        imgcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Product_List.this, Activity_cart.class);
                startActivity(intent);
            }
        });

        filter =(ImageView)findViewById(R.id.filter);
        imgsort =(ImageView)findViewById(R.id.imgsort);
        imgsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strlowtohigh = "";
                strhightolow = "";
                strnewest = "";
                strofferproduct = "";
                final List<String> points = new ArrayList<>();

                final Dialog logindialog = new Dialog(Product_List.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater1 = (LayoutInflater) Product_List.this.getSystemService(Product_List.this.LAYOUT_INFLATER_SERVICE);
                View convertView1 = (View) inflater1.inflate(R.layout.filter, null);

                //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logindialog.setContentView(convertView1);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(logindialog.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp1.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp1);



                lowtohigh = (TextView) convertView1.findViewById(R.id.lowtohigh);
                hightolow = (TextView) convertView1.findViewById(R.id.hightolow);
                newest = (TextView) convertView1.findViewById(R.id.newest);
                offer = (TextView)convertView1.findViewById(R.id.offer);



                lowtohigh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        searchType = "lowTOhigh";

                        strlowtohigh = "required";
                        strhightolow = "";
                        strnewest = "";
                        strofferproduct = "";
                        logindialog.dismiss();
                        new PRODUCT_LIST().execute();

                     /*   Intent intent=new Intent(Product_List.this,fragment_filter.class);
                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" + "lowTOhigh");

                        Log.e("testing123", "categoryid=====searchtype = ===" + categoryname);
                        Log.e("testing123", "subcategoryid====searchtype==== = " + subcategoryname);

                        prefeditor.commit();
                        startActivity(intent);
                        logindialog.dismiss();*/

                    }
                });

                hightolow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchType = "highTOlow";

                        strhightolow = "required";
                        strlowtohigh = "";
                        strnewest = "";
                        strofferproduct = "";
                        logindialog.dismiss();
                        new PRODUCT_LIST().execute();
                    /*    Intent intent=new Intent(Product_List.this,fragment_filter.class);
                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" +"highTOlow");
                        Log.e("testing123", "categoryid=====searchtype = ===" + categoryname);
                        Log.e("testing123", "subcategoryid====searchtype==== = " + subcategoryname);

                        prefeditor.commit();
                        startActivity(intent);
                         logindialog.dismiss();*/


                    }
                });

                newest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchType = "newest";

                        strnewest = "required";
                        strhightolow = "";
                        strlowtohigh = "";
                        strofferproduct = "";
                        logindialog.dismiss();
                        new PRODUCT_LIST().execute();

                       /* Intent intent=new Intent(Product_List.this,fragment_filter.class);
                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" + "newest");
                        Log.e("testing123", "categoryid=====searchtype = ===" + categoryname);
                        Log.e("testing123", "subcategoryid====searchtype==== = " + subcategoryname);

                        prefeditor.commit();

                        startActivity(intent);
                        logindialog.dismiss();*/



                    }
                });
                offer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchType = "newest";

                        strofferproduct = "required";
                        strnewest = "";
                        strhightolow = "";
                        strlowtohigh = "";
                        logindialog.dismiss();
                        new PRODUCT_LIST().execute();

                       /* Intent intent=new Intent(Product_List.this,fragment_filter.class);
                        SharedPreferences prefuserdata = getSharedPreferences("searchtype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("searchtype", "" + "newest");
                        Log.e("testing123", "categoryid=====searchtype = ===" + categoryname);
                        Log.e("testing123", "subcategoryid====searchtype==== = " + subcategoryname);

                        prefeditor.commit();

                        startActivity(intent);
                        logindialog.dismiss();*/



                    }
                });

                logindialog.show();

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> points = new ArrayList<>();

                final Dialog logindialog2 = new Dialog(Product_List.this);
                logindialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater1 = (LayoutInflater) Product_List.this.getSystemService(Product_List.this.LAYOUT_INFLATER_SERVICE);
                View convertView2 = (View) inflater1.inflate(R.layout.dialog_filter, null);

                //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logindialog2.setContentView(convertView2);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                    // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog2.setCanceledOnTouchOutside(false);
                logindialog2.setCancelable(false);
                WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
                lp1.copyFrom(logindialog2.getWindow().getAttributes());
                lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp1.gravity = Gravity.CENTER;
                logindialog2.getWindow().setAttributes(lp1);

                  txt_brands = (TextView) convertView2.findViewById(R.id.txt_brands);

                ImageView imgcancel = (ImageView) convertView2.findViewById(R.id.imgcancel);
                CrystalRangeSeekbar rangeSeekbar1 = (CrystalRangeSeekbar) convertView2.findViewById(R.id.rangeSeekbar1);
                rangeSeekbar1.setMinValue(50);
                rangeSeekbar1.setMaxValue(1500);
                final TextView textMin1 = (TextView) convertView2.findViewById(R.id.textMin1);
                final TextView textMax1 = (TextView) convertView2.findViewById(R.id.textMax1);
                final TextView text_submit = (TextView) convertView2.findViewById(R.id.text_submit);

                rangeSeekbar1.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                    @Override
                    public void valueChanged(Number minValue, Number maxValue) {
                        textMin1.setText(String.valueOf(minValue));
                        textMax1.setText(String.valueOf(maxValue));

                        String minvalue=String.valueOf(minValue);
                        String maxvalue=String.valueOf(maxValue);


                        Log.e("testing","minvalue" +minvalue);
                        Log.e("testing","maxvalue" +maxvalue);



                    }
                });



                imgcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logindialog2.dismiss();
                    }
                });
                strminprice = "";
                strmaxprice = "";
                text_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strpricefilter = "required";
                        strminprice = textMin1.getText().toString();
                        strmaxprice = textMax1.getText().toString();
                        Log.e("testing","arrayListbrands123 = "+arrayListbrands);
                     /*   Intent intent=new Intent(Product_List.this,Sort_Activity.class);
                        SharedPreferences prefuserdata = getSharedPreferences("brnadFiltertype", 0);
                        SharedPreferences.Editor prefeditor = prefuserdata.edit();
                        prefeditor.putString("Filtertype", "" + strlistbrnadid);
                        Log.e("testing1234", "Filtertype=====Filtertype = ===" + strlistbrnadid);
                        prefeditor.commit();
                        startActivity(intent);*/
                        logindialog2.dismiss();

                        new PRODUCT_LIST().execute();

                    }
                });
                txt_brands.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayListbrands.clear();
                        final Dialog uploaddialog = new Dialog(Product_List.this);
                        uploaddialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
                        View convertView = (View) inflater.inflate(R.layout.dialog_recyclerviewbrands, null);

                        //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                        uploaddialog.setContentView(convertView);
                        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                        uploaddialog.setCanceledOnTouchOutside(false);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(uploaddialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        // uploaddialog.getWindow().setAttributes(lp);
                        uploaddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        ImageView imgcancel = (ImageView) convertView.findViewById(R.id.imgcancel);

                        imgcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                uploaddialog.dismiss();
                            }
                        });
                        TextView textdialogsubmit = (TextView) convertView.findViewById(R.id.textsubmit);
                        recyclerViewdialog = (RecyclerView) convertView.findViewById(R.id.recycler_view);


                        recyclerViewdialog.setHasFixedSize(true);
                        recyclerViewdialog.setLayoutManager(new LinearLayoutManager(Product_List.this));

                        new BrandListLoader().execute();
                        // preparetime();



                        textdialogsubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String strlist = "";


                                for (Brands_List model : brands_lists){
                                    if (model.isSelected()){
                                        liste=(model.getBrandId());
                                        if (strlist == null || strlist.length() == 0){
                                            strlist = model.getBrandName();


                                          //  strlistbrnadid = model.getBrandId();

                                        }else{
                                            strlist = strlist+", "+model.getBrandName();
                                        }
                                        strlistbrnadid = model.getBrandId();
                                        arrayListbrands.add(model.getBrandId());
                                        Log.e("testing","strlistbrnadid = "+strlistbrnadid);
                                        Log.e("testing","arrayListbrands = "+arrayListbrands);


                                            /*else{
                                            strlistbrnadid = strlistbrnadid+", "+model.getBrandId();
                                            strlist = strlist+", "+model.getBrandName();


                                            Log.e("testing","strlist"+ strlist);
                                            Log.e("testing","strlistbrnadid"+ strlistbrnadid);
                                        }
*/

                                        txt_brands.setText(strlist);





                                    }
                                }

                               // txt_brands.setText(strlist);


                                uploaddialog.dismiss();
                            }
                        });
                        uploaddialog.show();
                    }
                });

                logindialog2.show();

            }
        });
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        TextView Categoryname_heading = (TextView) findViewById(R.id.categoryname_heading);
        Categoryname_heading.setText(categoryname_heading);
        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setHasFixedSize(true);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Product_List.this));
        mFeedRecyler.setLayoutManager(lLayout);
        mCallback = this;
        mCallback1 = this;





        mFeedRecyler1 = (RecyclerView) findViewById(R.id.recycler_view1);
        mFeedRecyler1.setHasFixedSize(true);
        //  mFeedRecyler.setLayoutManager(new LinearLayoutManager(Product_List.this));
        mFeedRecyler1.setLayoutManager(lLayout1);


        mFeedRecyler2 = (RecyclerView) findViewById(R.id.recycler_view2);
        mFeedRecyler2.setHasFixedSize(true);
        mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Product_List.this));

        // mFeedRecyler2.setLayoutManager(lLayout2);

        //   new Child_SubCategory_List().execute();
        //   new PRODUCT_LIST().execute();



        new SubCategory_List().execute();
        new Child_SubCategory_List().execute();
        new PRODUCT_LIST().execute();

        ConnectionDetector cd1 = new ConnectionDetector(Product_List.this);
        if (cd1.isConnectingToInternet()) {

            new productcartcount().execute();

        } else {


            Toast.makeText(Product_List.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onClickedItem(int pos, int qty, int status) {

        Log.e("DMen", "Pos:" + pos + "Qty:" + qty);
        Log.e("testing", "status  = " + status);
        Log.e("testing", "title inm  = " + allItems1.get(pos).getId());

        subcategoryname = allItems1.get(pos).getId();
        Log.e("testing", "getid====" + subcategoryname);
        new Child_SubCategory_List().execute();

        new PRODUCT_LIST().execute();

        childid = "All";

    }

    @Override
    public void onClickedItem1(int pos, int qty, int status) {

        Log.e("DMen", "Pos:" + pos + "Qty:" + qty);
        Log.e("testing", "status  = " + status);
        // Log.e("testing","title inm  = "+allItems1.get(pos).getId());

        childid = allItems2.get(pos).getId();
        Log.e("testing", "getid====" + subcategoryname);
        new PRODUCT_LIST().execute();
        // childid = "All";

    }

    @Override
    public void onClickedItembrands(int pos, String qty, String BrandName,int status) {

        if(status==1)
        {
          //  strlist=BrandName;
          //  strlistbrnadid = qty;
           // txt_brands.setText(strlist);

        }
    }


    class SubCategory_List extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Product_List.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Product_List.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems1.clear();
            allItems1 = new ArrayList<FeederInfo_Subcategory_list>();
            return postJsonObject(EndUrl.Get_SubCategory_List_URL, makingJson());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    SharedPreferences prefuserdata = getSharedPreferences("introwposition", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("introwposition", "" + introwposition);

                    prefeditor.commit();

                    mAdapterFeeds = new Adapter_Subcategory_list(Product_List.this, allItems1, mCallback);

                    mFeedRecyler.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.HORIZONTAL, false));
                    mFeedRecyler.setAdapter(mAdapterFeeds);
//.
//                    mFeedRecyler.smoothScrollToPosition(introwposition);


                } else if (status.equals("fail")) {

                    allItems2.clear();
                    allItems1.clear();

                    mAdapterFeeds = new Adapter_Subcategory_list(Product_List.this, allItems1, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);

                    Toast.makeText(Product_List.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(Product_List.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Get_CatId, categoryname);


        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.e("testing", "json object " + jobj);
        return jobj;


    }


    public JSONObject postJsonObject(String url, JSONObject loginJobj) {

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
            if (inputStream != null)

                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing", "testing in json result=======" + result);
            Log.e("testing", "testing in json result json=======" + json);
            Log.e("testing", "result in post status=========" + json.getString("status"));
            status = json.getString("status");
            message = json.getString("message");
            String Totalrecord = json.getString("total_record");

            // data = json.getString("data");

            String arrayresponce = json.getString("data");
            Log.e("testing", "adapter value=" + arrayresponce);


            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                Log.e("testing33", "position = " + i);
                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                //   headers = post.getString("categoryName");
                // products = post.getString("Products");

                FeederInfo_Subcategory_list item = new FeederInfo_Subcategory_list();

                Log.e("testing33", "subcategoryname = " + subcategoryname);
                Log.e("testing33", "subcategoryname 2= " + post.optString("subcatId"));
                if (subcategoryname.equals(post.optString("subcatId"))) {

                    introwposition = i;

                } else {




                }


                item.setId(post.optString("subcatId"));
                item.setName(post.optString("subcatName"));
                item.setImage(post.optString("subcat_Img"));
                item.setRowposition(i);


                allItems1.add(item);

            /*    JSONArray responcearray2 = new JSONArray(products);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);

                }
*/
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result
        return json;

    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;


    }


    class Child_SubCategory_List extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Product_List.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Product_List.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems2.clear();
            allItems2 = new ArrayList<FeederInfo_child_subcategory_list>();
            return postJsonObject1(EndUrl.Get_Child_SubCategory_List, makingJson1());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    allItems3.clear();
                    mAdapterFeeds1 = new Adapter_child_subcategory_list(Product_List.this, allItems2, mCallback1);

                    mFeedRecyler1.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.HORIZONTAL, false));
                    mFeedRecyler1.setAdapter(mAdapterFeeds1);

                } else if (status.equals("fail")) {

                    allItems2.clear();
                    allItems3.clear();

                    mAdapterFeeds1 = new Adapter_child_subcategory_list(Product_List.this, allItems2, mCallback1);
                    mFeedRecyler1.setAdapter(mAdapterFeeds1);

                    Toast.makeText(Product_List.this, "no data found", Toast.LENGTH_LONG).show();

                }

            } else {

                Toast.makeText(Product_List.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson1() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        try {

            jobj.put(EndUrl.Get_SubCat_CatId, subcategoryname);


        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.e("testing", "json object " + jobj);
        return jobj;


    }


    public JSONObject postJsonObject1(String url, JSONObject loginJobj) {
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
            if (inputStream != null)

                result = convertInputStreamToString1(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing", "testing in json result=======" + result);
            Log.e("testing", "testing in json result json=======" + json);
            Log.e("testing", "result in post status=========" + json.getString("status"));
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

                FeederInfo_child_subcategory_list item = new FeederInfo_child_subcategory_list();

                item.setId(post.optString("childcatId"));
                item.setName(post.optString("childCat_name"));


                allItems2.add(item);

/*
                JSONArray responcearray2 = new JSONArray(products);
                Log.e("testing", "responcearray value=" + responcearray);

                for (int i2 = 0; i2 < responcearray2.length(); i2++) {
                    JSONObject post2 = responcearray2.getJSONObject(i2);

                }
*/

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result
        return json;

    }

    private String convertInputStreamToString1(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }


    class PRODUCT_LIST extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Product_List.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Product_List.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();

        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            allItems3.clear();
            allItems3 = new ArrayList<FeederInfo_Product_List>();
            return postJsonObject2(EndUrl.GetCategoryChildProductList_URL, makingJson2());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {
                    textnodata.setVisibility(View.GONE);
                    Log.e("testing", "result in post execute=========" + allItems3);

                    mAdapterFeeds2 = new Adapter_Product_List(Product_List.this, allItems3);
                    //  mFeedRecyler2.setLayoutManager(new LinearLayoutManager(Product_List.this, LinearLayoutManager.VERTICAL, true));
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                } else if (status.equals("fail")) {
                    textnodata.setVisibility(View.VISIBLE);
                    allItems3.clear();

                    mAdapterFeeds2 = new Adapter_Product_List(Product_List.this, allItems3);
                    mFeedRecyler2.setAdapter(mAdapterFeeds2);

                    Toast.makeText(Product_List.this, "no data found", Toast.LENGTH_LONG).show();

                }else{
                    textnodata.setVisibility(View.VISIBLE);
                }

            } else {
                textnodata.setVisibility(View.VISIBLE);
                Toast.makeText(Product_List.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject jobj = new JSONObject();
        // user_id = edt_mobileno.getText().toString();

        if (subcategoryname == null || subcategoryname.trim().length() == 0 || subcategoryname.trim().equals("null")){
            subcategoryname = "";
        }else{

        }

        try {
            jobj.put(EndUrl.Get_subcatId, subcategoryname);
            jobj.put(EndUrl.Get_childCatId, childid);
            jobj.put(EndUrl.Get_lowtoHigh, strlowtohigh);
            jobj.put(EndUrl.Get_hightoLow, strhightolow);
            jobj.put(EndUrl.Get_newestFirst, strnewest);
            jobj.put(EndUrl.Get_offerProduct, strofferproduct);
            jobj.put(EndUrl.Get_priceFilter, strpricefilter);
            jobj.put(EndUrl.Get_minPrice, strminprice);
            jobj.put(EndUrl.Get_maxPrice, strmaxprice);

            if (arrayListbrands.size()!=0){
                for (int i5 = 0; i5 < arrayListbrands.size(); i5++) {

                    String strservice2 =EndUrl.Get_brands + "[" + i5 + "]";
                    jobj.put(strservice2, arrayListbrands.get(i5));

                    Log.e("testing", "" + strservice2);
                    Log.e("testing", "" + arrayListbrands.get(i5));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();

        }

        Log.e("testing", "json object222 " + jobj);
        return jobj;


    }


    public JSONObject postJsonObject2(String url, JSONObject loginJobj) {
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
            if (inputStream != null)

                result = convertInputStreamToString2(inputStream);
            else
                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing", "testing in json result222=======" + result);
            Log.e("testing", "testing in json result json=======" + json);
            Log.e("testing", "result in post status=========" + json.getString("status"));
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

                 /*   Log.e("testing","productId in json = "+post2.optString("productId"));
                    Log.e("testing","title in json = "+post2.optString("title"));
                    Log.e("testing","price in json = "+post2.optString("price"));
                    Log.e("testing","image in json = "+post2.optString("image"));
*/
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
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;


    }


    class BrandListLoader extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Product_List.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Product_List.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {

            brands_lists = new ArrayList<Brands_List>();


            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();




            JSONObject json = jsonParser.makeHttpRequest(EndUrl.getbrands_URL, "GET", userpramas);
            Log.e("testing", "json result = " + json);

            if (json == null) {

            } else {
                Log.e("testing", "jon2222222222222");
                try {

                    status = json.getString("status");
                    message = json.getString("message");
                    total_record = json.getString("total_record");
                    String arrayresponse = json.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponse);

                    JSONArray responcearray = new JSONArray(arrayresponse);
                    Log.e("testing", "responcearray value=" + responcearray);

                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();



                        Brands_List item = new Brands_List();

                        item.setBrandId(post.optString("brandId"));
                        item.setBrandName(post.optString("brandName"));
                        item.setCatId(post.optString("catId"));


                        Log.e("testing","brand_id" + post.optString("brand_id"));
                        Log.e("testing","name" + post.optString("name"));




                        brands_lists.add(item);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;



        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            dialog.dismiss();
            brands_adapter = new Brands_Adapter(Product_List.this,brands_lists, mCallbackbrands);
            recyclerViewdialog.setAdapter(brands_adapter);
            brands_adapter.notifyDataSetChanged();



        }

    }

    public static class productcartcount extends AsyncTask<Void, Void, JSONObject> {

        //  ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(MainActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(MainActivity.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();*/
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObjectcart(EndUrl.GetuserCartDetails_URL, makingJsoncart());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                //  dialog.dismiss();

                Log.e("testing2","result in post execute========="+result);

                if (status.equals("success")){

                    Log.e(" cart count", "cartmenucount==" + cartmenucount);

                    String value2= cartmenucount;

                    if (value2 == null || value2.length() == 0 || value2.equals("null")){

                    }else{

                        int countw = Integer.valueOf(value2);
                        Log.e("mahi class count", "count==" + countw);

                        if (countw == 0) {

                        }else{
                            Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show();
                            String strcount = String.valueOf(countw);
                            counterValuePanel.setVisibility(View.VISIBLE);
                            count1.setText(strcount);
                        }


                    }

/*
                    if (_pdsubprice == null || _pdsubprice.length() == 0){
                        final String strrs = Activity_productdetails.this.getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+_pdsubprice);
                    }else{
                        pdprice.setVisibility(View.VISIBLE);
                        final String strrs = Activity_productdetails.this.getResources().getString(R.string.Rs);
                        pdsubprice.setText(strrs+" "+ _pdsubprice);


                        pdprice.setText(strrs+" "+ _pdsubprice);
                        pdprice.setPaintFlags(pdprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

                    }*/

                    // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_LONG).show();


                }else {

                    // Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_LONG).show();

                }

            }else {


                //  Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                // dialog.dismiss();

            }

        }

    }


    private static Drawable buildCounterDrawable1(int count, int backgroundImageId) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trolleycount, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {

            Log.e("testing2", "wishcount================11111=" + count);
            View counterTextPanel = view.findViewById(R.id.count1);
            View counterTextPanel2 = view.findViewById(R.id.counterBackground);
            counterTextPanel.setVisibility(View.GONE);
            counterTextPanel2.setVisibility(View.GONE);


        } else {

            Log.e("testing2", "wishcount================22222=" + count);
            TextView textView = (TextView) view.findViewById(R.id.count1);
            textView.setText("" + count);

        }


        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(context.getResources(), bitmap);


    }

    public static JSONObject makingJsoncart() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.GetuserCartDetails_Id,UserId);

            Log.e("testing2", "adapter GetCartCount_Id=" + UserId);


            //if you want to modify some value just do like this.

         /*
            details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing2","json"+details.toString());

*/

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;

    }



    public static JSONObject postJsonObjectcart(String url, JSONObject loginJobj){
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

                result = convertInputStreamToStringcart(inputStream);

            else

                result = "Did not work!";


        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        JSONObject json = null;
        try {

            json = new JSONObject(result);
            Log.e("testing2","testing in json result======="+result);
            Log.e("testing2","testing in json result json======="+json);
            Log.e("testing2","result in post status========="+json.getString("status"));
            status = json.getString("status");
            // message = json.getString("message");
            //  total_record = json.getString("total_record");


            String arrayresponce = json.getString("data");



            Log.e("testing2", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing2", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();
                String products = post.getString("products");
                JSONArray responcearray2 = new JSONArray(products);
                cartmenucount = String.valueOf(responcearray2.length());
                Log.e("testing2", "cartcount = "+cartmenucount);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private static String convertInputStreamToStringcart(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
//-----------------------------------------------------------------------------------------------------



}
