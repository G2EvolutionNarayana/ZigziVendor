package g2evolution.Boutique.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.Boutique.Activity.Activity_Profile;
import g2evolution.Boutique.Activity.Activity_productdetails;
import g2evolution.Boutique.Activity.Login;
import g2evolution.Boutique.Activity.My_Orders;
import g2evolution.Boutique.Adapter.Adapter_Category;
import g2evolution.Boutique.Adapter.Adapter_CategoryBlog;
import g2evolution.Boutique.Adapter.Adapter_home_category;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_home_category;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.MainActivity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_Category;
import g2evolution.Boutique.entit.Entity_CategoryBlog;

public class Fragment_Home_New extends Fragment implements ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener{

    private SliderLayout mDemoSlider;
    SliderLayout sliderLayout;
    HashMap<String, String> url_maps = new HashMap<String, String>();
    JSONParser jsonParser = new JSONParser();
    String message;

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_home_category> allItems1 = new ArrayList<FeederInfo_home_category>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_home_category> mListFeederInfo;
    Adapter_home_category mAdapterFeeds;
    RecyclerView rView;
    GridLayoutManager lLayout;
    String status,pincode;

    private ArrayList<Entity_CategoryBlog> allItemsgridblog = new ArrayList<Entity_CategoryBlog>();
    private RecyclerView mFeedRecylerblog;
    Adapter_CategoryBlog mAdaptergridblog;

    ProgressDialog pDialog;

    String Username,Usermail,Usermob,UserId;
    String details,total_record;


    Adapter_Category mAdaptergrid;
    private ArrayList<Entity_Category> clearedListgrid;
    private ArrayList<Entity_Category> allItemsgrid = new ArrayList<Entity_Category>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);


        SharedPreferences prefuserdata2 = getActivity().getSharedPreferences("pincode", 0);
        pincode = prefuserdata2.getString("pincode", "");

        Log.e("testing","pincode = "+pincode);

        SharedPreferences prefuserdata =getActivity(). getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view1);
        //mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(getActivity(), 2);
        //  rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //  rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        mFeedRecyler.setLayoutManager(lLayout);
        //  mFeedRecyler.setHasFixedSize(true);

        mFeedRecylerblog = (RecyclerView) rootView.findViewById(R.id.recycler_view2);
        //mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(getActivity(), 2);
        //  rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //  rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        mFeedRecylerblog.setLayoutManager(lLayout);
        //  mFeedRecyler.setHasFixedSize(true);

        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);

        sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);

        FloatingActionButton fab=(FloatingActionButton)rootView.findViewById(R.id.fab);
        fab.setBackgroundColor(Color.parseColor("#3963d6"));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String qty = getResources().getString(R.string.whatsappnumber);
                String toNumber = "+91"+qty; // Replace with mobile phone number without +Sign or leading zeros.
                String text = "You are requesting chat from Category page please continue chatting...";// Replace with your message.

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+toNumber +"&text="+text));
                startActivity(intent);

            }
        });

      /*  if (pincode == null || pincode.length() == 0 || pincode.equals("null") || pincode.equals("0")) {

            Toast.makeText(getActivity(), "Select Pincode", Toast.LENGTH_SHORT).show();

        } else {

            ConnectionDetector cd = new ConnectionDetector(getActivity());
            if (cd.isConnectingToInternet()) {

                new SliderImage().execute();
                //  new GetCategories().execute();
                new LoadCategory().execute();

            } else {

                Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }*/
        new SliderImage().execute();
        //  new GetCategories().execute();
        new LoadCategory().execute();
        new LoadCategoryBlog().execute();
        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return rootView;

    }

    private class SliderImage1 extends AsyncTask<String, String, String> implements BaseSliderView.OnSliderClickListener

    {
        String responce;
        String status;
        String total_count;
        private ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.hide();
        }

        public String doInBackground(String... args) {
            // Create an array


            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            url_maps = new HashMap<String, String>();

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.SliderImage_URL, "GET", userpramas);


            Log.e("santanu", "json result =mDemoSlider " + json);
            if (json != null) {
                try {


                    status = json.getString("status");
                    total_count = json.getString("total_record");
                    String arrayresponce = json.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray" + "" + " value=" + responcearray);

                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        url_maps.put(post.getString("advId"), post.getString("advImage"));

                        Log.e("santanu", "Eventdetailsimage" + post.getString("advImage"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return responce;
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();

            Log.e("testing", "SliderViewresult is = " + responce);

            for (final String name : url_maps.keySet()) {
                final TextSliderView textSliderView = new TextSliderView(getActivity());
                // initialize a SliderLayout

                textSliderView
                        .description("")
                        .image(url_maps.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);
                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                mDemoSlider.addSlider(textSliderView);

                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        String bannerid = textSliderView.getBundle().getString("extra");
                        Log.e("testing","strsdf = "+bannerid);


                        if (bannerid==null||bannerid.length()==0||bannerid.equals("")){

                            Log.e("santanu","krfnsgldrhl===null");

                            Toast.makeText(getActivity(), "No Product Details Found", Toast.LENGTH_SHORT).show();


                        }else {

                            Intent intent =new Intent(getActivity(),Activity_productdetails.class);

                            SharedPreferences prefuserdata = getActivity().getSharedPreferences("ProDetails", 0);
                            SharedPreferences.Editor prefeditor = prefuserdata.edit();
                            prefeditor.putString("Proid", "" + bannerid);

                            prefeditor.commit();
                            startActivity(intent);

                        }
                        //  new Sliderweblink().execute();
                    }
                });
            }

        }

        @Override
        public void onSliderClick(BaseSliderView slider) {

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {


    }
    //for brand logos
    private class SliderImage extends AsyncTask<String, String, String> implements BaseSliderView.OnSliderClickListener

    {
        String responce;
        String status;
        String total_count;
        private ProgressDialog pDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading Details");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.hide();
        }

        public String doInBackground(String... args) {
            // Create an array

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            url_maps = new HashMap<String, String>();

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.SliderImgEcom_URL, "GET", userpramas);

            if(json==null){

                return responce;
            }else {

                Log.e("testing", "json result =mDemoSlider " + json);

                try {
                    status = json.getString("status");

                    String arrayresponce = json.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray" + "" + " value=" + responcearray);

                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        url_maps.put(post.getString("name"), post.getString("image"));

                        Log.e("testing", "Eventdetailsimage" + post.getString("image"));

                    }

                } catch (JSONException e)

                {
                    e.printStackTrace();
                }
            }


            return responce;
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();


            if (status==null){


            }else if (status.equals("success")){

                Log.e("testing12312", "SliderViewresult is 12= " + responce);

                for (String name : url_maps.keySet()) {

                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    // initialize a SliderLayout

                    textSliderView
                            .description("")
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(this);
                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra", name);

                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override public void onSliderClick(BaseSliderView slider) {
                           /* Log.e("MyActivity", "index selected:" + brand_slider.getCurrentPosition());

                            Intent intent=new Intent(getActivity(), Activity_Brand_List.class);
                            startActivity(intent);*/
                            //  Toast.makeText(getActivity(), "clicking happening", Toast.LENGTH_SHORT).show();
                        }
                    });
                  //  banner_slider.addSlider(textSliderView);
                    mDemoSlider.addSlider(textSliderView);

                }


            }else if (status.equals("fail")){


            }else{

            }

        }

        @Override
        public void onSliderClick(BaseSliderView slider) {

        }
    }

    private class GetCategories extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(EndUrl.fragment_home_expandable);


            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node

                    Log.e("testing", "jsonObj = " + jsonObj);

                    status = jsonObj.getString("status");
                    message = jsonObj.getString("message");
                    total_record = jsonObj.getString("total_record");


                    String arrayresponce = jsonObj.getString("data");
                    Log.e("testing", "adapter value=" + arrayresponce);


                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);


                    for (int i = 0; i < responcearray.length(); i++) {

                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();

                        FeederInfo_home_category item = new FeederInfo_home_category();

                        //  item.setId(post.optString("id"));

                        item.setCategoryname(post.optString("categoryName"));
                        item.setId(post.optString("catId"));
                        item.setCategoryimage(post.optString("catImage"));

                        details=post.getString("details");


                        JSONArray jsonArray=new JSONArray(details);

                        for (int j=0;j<jsonArray.length();j++){

                            JSONObject jsonObject=jsonArray.getJSONObject(j);

                            if (j==0){

                                item.setSubcateid(jsonObject.optString("subcatId"));

                            }else {


                            }


                        }
                        allItems1.add(item);

                    }

                } catch (final JSONException e) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
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

            Log.e("testing", "status===============" + status);
            if (status == null || status.length() == 0 || status.equals("null")) {



            } else if (status.equals("success")) {

                pDialog.dismiss();
                Log.e("testing", "allItems1===============" + allItems1);

                mAdapterFeeds = new Adapter_home_category(getActivity(), allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);


            } else if (status.equals("error")) {

                allItems1.clear();

                mAdapterFeeds = new Adapter_home_category(getActivity(), allItems1);
                mFeedRecyler.setAdapter(mAdapterFeeds);

                Toast.makeText(getActivity(), "no data found", Toast.LENGTH_LONG).show();
            }
        }

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.home:

                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                    return true;
                /*case R.id.cart:

                    if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                        ShowLogoutAlert1("Please login");

                    }else {

                        Intent intent1 = new Intent(getActivity(), Activity_cart.class);
                        startActivity(intent1);

                    }
                    return true;*/

                case R.id.orderhistory:


                    if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                        ShowLogoutAlert1("Please login");

                    }else {

                        Intent intent111 = new Intent(getActivity(), My_Orders.class);
                        startActivity(intent111);

                    }

                    return true;
                case R.id.profile:

                    if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                        ShowLogoutAlert1("Please login");


                    }else {

                        Intent intent1 = new Intent(getActivity(), Activity_Profile.class);
                        startActivity(intent1);

                    }

                    return true;

            }

            return false;
        }
    };

    private void ShowLogoutAlert1(String data) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Login to Boutique");
        alertDialog.setMessage(data);
        //  alertDialog.setBackgroundResource(R.color.dialog_color);
        // alertDialog.setIcon(R.drawable.exit);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                getActivity(). finish();
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
    class LoadCategory extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItemsgrid =new ArrayList<Entity_Category>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            // userpramas.add(new BasicNameValuePair(EndUrls.GetBookLaterList_URL_user_id, registeruserid));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.Get_Category_URL, "GET", userpramas);

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




                            Entity_Category item = new Entity_Category();
                            item.setId(post.optString("id"));
                            item.setTitle(post.optString("name"));
                            item.setImage(post.optString("image"));





                            allItemsgrid.add(item);




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

                mAdaptergrid = new Adapter_Category(getActivity(),allItemsgrid);
                mFeedRecyler.setAdapter(mAdaptergrid);






            }
            else {


            /*    product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/




            }



        }

    }
    class LoadCategoryBlog extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItemsgridblog =new ArrayList<Entity_CategoryBlog>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            // userpramas.add(new BasicNameValuePair(EndUrls.GetBookLaterList_URL_user_id, registeruserid));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.Get_CategoryBlog_URL, "GET", userpramas);

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




                            Entity_CategoryBlog item = new Entity_CategoryBlog();
                            item.setId(post.optString("id"));
                            item.setTitle(post.optString("name"));
                            item.setImage(post.optString("image"));





                            allItemsgridblog.add(item);




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
                Log.e("testing123", "allItems1===" + allItemsgridblog);


/*
                 RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                 prodcuts_recycler.setLayoutManager(mLayoutManager);
                product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/

                mAdaptergridblog = new Adapter_CategoryBlog(getActivity(),allItemsgridblog);
                mFeedRecylerblog.setAdapter(mAdaptergridblog);






            }
            else {


            /*    product_details_adapter = new Products_Adapter(getActivity(), product_details_lists, mCallback);
                prodcuts_recycler.setAdapter(product_details_adapter);*/




            }



        }

    }
}
