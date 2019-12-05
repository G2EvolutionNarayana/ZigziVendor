package g2evolution.Boutique.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ScrollView;
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
import java.util.LinkedHashMap;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_home_category;
import g2evolution.Boutique.Adapter.MyListAdapter;
import g2evolution.Boutique.Adapter.RecyclerViewDataAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.DetailInfo;
import g2evolution.Boutique.FeederInfo.FeedDetail;
import g2evolution.Boutique.FeederInfo.FeedHeader;
import g2evolution.Boutique.FeederInfo.FeederInfo_home_category;
import g2evolution.Boutique.FeederInfo.HeaderInfo;
import g2evolution.Boutique.FeederInfo.SectionDataModel;
import g2evolution.Boutique.FeederInfo.SingleItemModel;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.HttpHandler;
import g2evolution.Boutique.Utility.JSONParser;

/**
 * Created by G2e Android on 31-01-2018.
 */

public class fragment_home extends Fragment implements RecyclerViewDataAdapter.OnItemClick,ViewPagerEx.OnPageChangeListener,BaseSliderView.OnSliderClickListener {

    List<FeedHeader> listDataHeader;
    List<FeedDetail> feedDetail1 = new ArrayList<FeedDetail>();
    HashMap<String, List<FeedDetail>> listDataChild;


    private LinkedHashMap<String, HeaderInfo> myDepartments = new LinkedHashMap<String, HeaderInfo>();
    private ArrayList<HeaderInfo> deptList = new ArrayList<HeaderInfo>();

    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    //our child listener
    private ExpandableListView.OnChildClickListener myListItemClicked = new ExpandableListView.OnChildClickListener() {

        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = deptList.get(groupPosition);
            //get the child info
            DetailInfo detailInfo = headerInfo.getProductList().get(childPosition);
            //display it or do something with it
            /*Toast.makeText(getActivity(), "Clicked on Detail " + headerInfo.getName()
                    + "/" + detailInfo.getName(), Toast.LENGTH_LONG).show();*/
            return false;
        }

    };


    //our group listener
    private ExpandableListView.OnGroupClickListener myListGroupClicked = new ExpandableListView.OnGroupClickListener() {

        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id) {

            //get the group header
            HeaderInfo headerInfo = deptList.get(groupPosition);
            //display it or do something with it
            /*Toast.makeText(getActivity(), "Child on Header " + headerInfo.getName(),
                    Toast.LENGTH_LONG).show();*/

            return false;

        }

    };

    String message;

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_home_category> allItems1 = new ArrayList<FeederInfo_home_category>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_home_category> mListFeederInfo;
    Adapter_home_category mAdapterFeeds;
    RecyclerView rView;
    GridLayoutManager lLayout;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    RecyclerView my_recycler_view;
    ArrayList<SectionDataModel> allSampleData;
    private RecyclerViewDataAdapter.OnItemClick mCallback;
    RecyclerViewDataAdapter adapter;

    String status;
    String headers;
    String pincode;

    private SliderLayout mDemoSlider;
    SliderLayout sliderLayout;
    HashMap<String, String> url_maps = new HashMap<String, String>();

    ScrollView scrollview1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences prefuserdata2 = getActivity().getSharedPreferences("pincode", 0);
        pincode = prefuserdata2.getString("pincode", "");

        Log.e("testing", "pincode=============fragment_home=============" + pincode);
        mCallback = this;
        allSampleData = new ArrayList<SectionDataModel>();
        my_recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view1);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setVisibility(View.VISIBLE);
        my_recycler_view.setNestedScrollingEnabled(false);

        //mFeedRecyler = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        //mFeedRecyler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //setUpRecycler();
        // context = this;
        lLayout = new GridLayoutManager(getActivity(), 2);
      //  rView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
      //  rView.setHasFixedSize(true);
      //  rView.setLayoutManager(lLayout);
       // mFeedRecyler.setLayoutManager(lLayout);
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
                new userdata().execute();
                new Getproducts().execute();

                } else {

                    Toast.makeText(getActivity(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

            }




        //listener for child row click
        //myList.setOnChildClickListener(myListItemClicked);
        //listener for group heading click
        //  myList.setOnGroupClickListener(myListGroupClicked);


        //get reference to the ExpandableListView
        myList = (ExpandableListView) rootView.findViewById(R.id.myList);
        myList.setChildDivider(getResources().getDrawable(R.color.white));
      //  myList.setDivider(getResources().getDrawable(R.color.white));
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(getActivity(), deptList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);

      //  scrollview1 = (ScrollView)  rootView.findViewById(R.id.scrollview1);
    /*    myList.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollview1.requestDisallowInterceptTouchEvent(true);

                int action = event.getActionMasked();

                switch (action) {
                    case MotionEvent.ACTION_UP:
                        scrollview1.requestDisallowInterceptTouchEvent(false);
                        break;
                }

                return false;
            }
        });*/
        //expand all Groups
        expandAll();
        //scrollview1.requestDisallowInterceptTouchEvent(false);
    /*    int totalHeight = 0;
        ViewGroup.LayoutParams params = myList.getLayoutParams();
        int height = totalHeight
                + (myList.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
      //  params.height = height;
        myList.setLayoutParams(params);
        myList.requestLayout();*/

       // scrollview1.requestDisallowInterceptTouchEvent(true);
        myList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int position, long id) {
                Log.e("testing","parent: = "+parent);
                Log.e("testing","position: = "+position);
               // setListViewHeight(parent, position);
                return false;
            }
        });

        return rootView;

    }
  /*  private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }*/

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {

            if (getActivity()!=null) {

                View groupItem = listAdapter.getGroupView(i, false, null, listView);
                groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                totalHeight += groupItem.getMeasuredHeight();

                if (((listView.isGroupExpanded(i)) && (i != group))
                        || ((!listView.isGroupExpanded(i)) && (i == group))) {
                    for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                        View listItem = listAdapter.getChildView(i, j, false, null,
                                listView);
                        listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                        totalHeight = listItem.getMeasuredHeight();

                    }
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }


    @Override
    public void onClickedItem(int pos, String category, int status) {
        Log.e("DMen", "Pos:" + pos + "category:" + category);
        Log.e("testing", "status  = " + status);

        SharedPreferences prefuserdata = getActivity().getSharedPreferences("category", 0);
        SharedPreferences.Editor prefeditor = prefuserdata.edit();
        prefeditor.putString("category", "" + category);

        Log.e("testing", "category  = " + category);

        prefeditor.commit();

        my_recycler_view.setVisibility(View.GONE);

     /*   Fragment_Categories fragment3 = new Fragment_Categories();
        FragmentTransaction fragmentTransaction3 =
                getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction3.replace(R.id.fragment_container, fragment3);
        fragmentTransaction3.commit();*/

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
            String jsonStr = sh.makeServiceCall(EndUrl.GetCategorywise_URL);


            if (jsonStr != null) {

                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node

                    Log.e("testing", "jsonObj = " + jsonObj);

                    status = jsonObj.getString("status");
                    message = jsonObj.getString("message");

                    String total_record = jsonObj.getString("total_record");

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
                        Log.e("testing", "setCategoryimage" + post.optString("catImage"));

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
          /*  if (pDialog.isShowing())
                pDialog.dismiss();
            *//**
             * Updating parsed JSON data into ListView
             * *//*
            pDialog.dismiss();*/
            adapter = new RecyclerViewDataAdapter(getActivity(), allSampleData, mCallback);

            my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            my_recycler_view.setAdapter(adapter);
        }

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


            Log.e("testing", "json result =mDemoSlider " + json);

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

                    Log.e("testing", "Eventdetailsimage" + post.getString("advImage"));

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

                mDemoSlider.addSlider(textSliderView);

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




    //-----------------------expandable------------------------//



    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.expandGroup(i);
        }
    }


    //method to collapse all groups
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            myList.collapseGroup(i);
        }
    }


    //here we maintain our products in various departments
    private int addProduct(String department, String product) {

        int groupPosition = 0;

        //check the hash map if the group already exists
        HeaderInfo headerInfo = myDepartments.get(department);
        //add the group if doesn't exists
        if (headerInfo == null) {

            headerInfo = new HeaderInfo();
            headerInfo.setName(department);
            myDepartments.put(department, headerInfo);
            deptList.add(headerInfo);

        }

        //get the children for the group
        ArrayList<DetailInfo> productList = headerInfo.getProductList();
        //size of the children list
        int listSize = productList.size();
        //add to the counter
        listSize++;

        //create a new child and add that to the group
        DetailInfo detailInfo = new DetailInfo();
        detailInfo.setSequence(String.valueOf(listSize));
        detailInfo.setName(product);
        productList.add(detailInfo);
        headerInfo.setProductList(productList);

        //find the group position inside the list
        groupPosition = deptList.indexOf(headerInfo);
        return groupPosition;


    }


    public class userdata extends AsyncTask<String, String, String> {

        String responce;
        String message;
        String headers;
        String childs;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/

        }
        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            Log.e("testing", "jsonParser startedkljhk");
            //userpramas.add(new BasicNameValuePair("feader_reg_id", id));
            //  Log.e("testing", "feader_reg_id" + id);

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.fragment_home_expandable, "GET", userpramas);


            Log.e("testing1", "jsonParser" + json);


            if (json == null) {

                Log.e("testing1", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing1", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {

                    JSONObject response = new JSONObject(json.toString());

                    Log.e("testing1", "jsonParser2222" + json);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    // Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("data");
                    Log.e("testing1", "jsonParser3333" + posts);



              /* if (posts.equals(null)){
                   listDataHeader = new ArrayList<FeedHeader>();
                   listDataChild= new HashMap<String, FeedDetail>();
               }else{
                   listDataHeader.clear();
                   listDataChild.clear();
               }*/
            /*Initialize array if null*/
                  /*  if (null == listDataHeader || null == listDataChild) {
                        listDataHeader = new ArrayList<FeedHeader>();
                        // listDataChild = new ArrayList<FeedDetail>();
                        listDataChild = new HashMap<String, List<FeedDetail>>();
                    } else {
                        listDataHeader.clear();
                        listDataChild.clear();
                    }*/

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

                            FeedHeader item = new FeedHeader();
                            //     String Rowid = post.getString("category_id");
                            String Title = post.getString("categoryName");
                            String date = post.getString("catId");
                            String image = post.getString("catImage");
                            String Subcategorylist = post.getString("Subcategorylist");
                            //     String desc = post.getString("category_id");
                            //     String time = post.getString("category_id");
                            //   String uploads = post.getString("cat_image");
                            // item.setHeaderName(Title);
                            // item.setRowid(Rowid);
                            //   item.setDescription(desc);
                            //  item.setUpload(uploads);
                            //  item.setTime(time);

                            Log.e("testing","test cat id========"+date);
                            // listDataHeader.add(item);


                            HeaderInfo headerInfo;

                            headerInfo = new HeaderInfo();
                            headerInfo.setName(Title);
                            headerInfo.setImage(image);
                            headerInfo.setTextchild(Subcategorylist);
                            myDepartments.put(Title, headerInfo);
                            deptList.add(headerInfo);

                            //get the children for the group
                            ArrayList<DetailInfo> productList = headerInfo.getProductList();
                            //size of the children list
                            int listSize = productList.size();
                            //add to the counter
                            listSize++;

                            // FeedDetail feedDetail = new FeedDetail(Title);
                            // listDataHeader.add(item);
                            // listDataChild.put(item.getHeaderName(), feedDetail);

                            JSONArray posts2 = post.optJSONArray("details");

                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);
                                FeedDetail item2 = new FeedDetail();
                                //String[] planets = new String[]
                                String subcatid = post2.getString("subcatId");
                                String Title2 = post2.getString("subcatname");
                                String Title4 = post2.getString("catId");
                                String Title3 = post2.getString("categoryName");


                                childs = post2.getString("subcatname");

                                Log.e("testing","Title2======"+Title2);

                                /*FeedDetail feedDetail = new FeedDetail(Title2);
                                item2.setTitle(Title2);
                                listDataChild.put(item.getHeaderName(), feedDetail);*/

                                item2.setTitle(Title2);
                                //listDataChild.add(item2);
                                // Log.e("mahi--------------", "mahi--------------------- " + item2.setTitle(Title2));

                                feedDetail1.add(item2);
                                Log.e("mahi--------------", "mahi--------------------- " + feedDetail1.add(item2));

                                //   listDataChild.put(item.getHeaderName(), feedDetail1);
                                ///listDataChild.put(listDataHeader.get(0), wk);*/

                                // listDataChild.add(item2);


                                //create a new child and add that to the group
                                DetailInfo detailInfo = new DetailInfo();
                                detailInfo.setSequence(String.valueOf(listSize));
                                Log.e("testing","listSize==="+listSize);
                                detailInfo.setName(Title2);
                                detailInfo.setCategoryname(Title3);
                                detailInfo.setSubcatid(subcatid);
                                detailInfo.setCategoryid(Title4);
                                productList.add(detailInfo);
                                headerInfo.setProductList(productList);

                                //find the group position inside the list
                                //groupPosition = deptList.indexOf(headerInfo);
                            }

                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return responce;
            }


        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            pDialog.dismiss();
            Log.e("testing", "result is === " + result);

            // addProduct(listDataHeader, listDataChild);
            // mEListAdapter.setData(listDataHeader, listDataChild);

            //expListView.setAdapter(mEListAdapter);
            // listviewmyorder.setAdapter(adapter);
            Log.e("testing", "adapter");
            Log.e("testing", "adapter===="+deptList);

            listAdapter = new MyListAdapter(getActivity(), deptList);

//attach the adapter to the list
            myList.setAdapter(listAdapter);
          //  setListViewHeight(myList);
            setListViewHeight(myList, 0);
        }


    }
}