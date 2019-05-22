package g2evolution.GMT.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import g2evolution.GMT.Activity.Activity_Address_Navigation;
import g2evolution.GMT.Activity.Activity_Profile;
import g2evolution.GMT.Activity.Activity_cart;
import g2evolution.GMT.Activity.Activity_productdetails;
import g2evolution.GMT.Activity.Login;
import g2evolution.GMT.Activity.My_Orders;
import g2evolution.GMT.Adapter.Adapter_address_Navigation;
import g2evolution.GMT.Adapter.Adapter_home_category;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_home_category;
import g2evolution.GMT.MainActivity;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.HttpHandler;
import g2evolution.GMT.Utility.JSONParser;

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

    ProgressDialog pDialog;

    String Username,Usermail,Usermob,UserId;
    String details,total_record;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_new, container, false);


        SharedPreferences prefuserdata2 = getActivity().getSharedPreferences("pincode", 0);
        pincode = prefuserdata2.getString("pincode", "");


        SharedPreferences prefuserdata =getActivity(). getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");


        mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view1);
        //mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(getActivity(), 3);
        //  rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //  rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        mFeedRecyler.setLayoutManager(lLayout);
        //  mFeedRecyler.setHasFixedSize(true);


        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);

        sliderLayout = (SliderLayout) rootView.findViewById(R.id.slider);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(2000);
        mDemoSlider.addOnPageChangeListener(this);


        if (pincode == null || pincode.length() == 0 || pincode.equals("null") || pincode.equals("0")) {

            Toast.makeText(getActivity(), "Select Pincode", Toast.LENGTH_SHORT).show();

        } else {

            ConnectionDetector cd = new ConnectionDetector(getActivity());
            if (cd.isConnectingToInternet()) {

                new SliderImage().execute();
                new GetCategories().execute();

            } else {

                Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }

        }

        BottomNavigationView navigation = (BottomNavigationView) rootView.findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        return rootView;

    }

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

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.SliderImage_URL, "GET", userpramas);


            Log.e("santanu", "json result =mDemoSlider " + json);

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

            } catch (JSONException e)

            {
                e.printStackTrace();
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

                    return true;
                case R.id.cart:

                    if (UserId.equals("") || UserId.equals("null") || UserId.equals(null) || UserId.equals("0")){

                        ShowLogoutAlert1("Please login");

                    }else {

                        Intent intent1 = new Intent(getActivity(), Activity_cart.class);
                        startActivity(intent1);

                    }
                    return true;

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
        alertDialog.setTitle("Login to GMT");
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

}
