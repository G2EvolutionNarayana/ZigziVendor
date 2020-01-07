package g2evolution.Boutique.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_resourse;
import g2evolution.Boutique.Adapter.Adapter_resourselist;
import g2evolution.Boutique.Adapter.spinnerAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Retrofit.Api;
import g2evolution.Boutique.Retrofit.ApiClient;
import g2evolution.Boutique.Retrofit.CategoryList.CategoryListJson;
import g2evolution.Boutique.Retrofit.ResourceList.ResourceListJson;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.JSONfunctions;
import g2evolution.Boutique.entit.Entity_ResourseList;
import g2evolution.Boutique.entit.Pojo_Category;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ResourcesList extends AppCompatActivity implements Adapter_resourselist.OnItemClickcropNames {



    public  static RecyclerView dashboard_reccyler;
    public  static
    Adapter_resourselist.OnItemClickcropNames mCallbackcropnames;
    public  static ArrayList<Entity_ResourseList> openings_entity=new ArrayList<Entity_ResourseList>();
    public  static Adapter_resourselist openings_adapter;

     JSONParser jsonParser = new JSONParser();

    String[] TextDesignation=new String[]{"Tailor",
            "Supplier",
            "Business Planer"};
    String[] TextEducation=new String[]{"Graduation",
            "Graduation",
            "Post Graduation"};
    String[] TextExpectedSalary=new String[]{"20000-30000",
            "15000-20000",
            "35000-40000"};

    String[] TextDesc=new String[]{"Good Knowledge in Designing and stiching",
            "Experience in marketing and Fast Supplying",
            "Have Good Knowledge on Designing, Marketing Plan and Speak well in English and Hindi Languages"};

    String[] TextDaysAgo=new String[]{"0 Days Ago",
            "1 Day Ago",
            "5 Days Ago"};

    LinearLayout linearfilter, linearsort;

    String ProductId, Productsubscriptionid;

    String strsortby = "";

    //------------------searchable main spinner----------------
    JSONObject jsonobjectmain;
    JSONArray jsonarraymain;
    ArrayList<Pojo_Category> worldmain;//spincategory
    ArrayList<String> worldlistmain;//spincategory
    Spinner spincategory;
    String cmainshop_id = "";
    String cmainshop_name = "";

    //------------------searchable Qualification spinner----------------
    JSONObject jsonobjectmainqualification;
    JSONArray jsonarraymainqualification;
    ArrayList<Pojo_Category> worldmainqualification;//spincategory
    ArrayList<String> worldlistmainqualification;//spincategory
    Spinner spinqualification;
    String cmainqualificationshop_id = "";
    String cmainqualificationshop_name = "";

    //------------------searchable Language spinner----------------
    JSONObject jsonobjectmainlanguage;
    JSONArray jsonarraymainlanguage;
    ArrayList<Pojo_Category> worldmainlanguage;//spincategory
    ArrayList<String> worldlistmainlanguage;//spincategory
    Spinner spinlanguage;
    String cmainlanguageshop_id = "";
    String cmainlanguageshop_name = "";


    String maxPrice="";
    String  minPrice="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resourceslist);

        SharedPreferences prefuserdata3 = getSharedPreferences("ProductIDDetails", 0);
        ProductId = prefuserdata3.getString("ProductId", "");
        Productsubscriptionid = prefuserdata3.getString("Productsubscriptionid", "");


        Log.e("testing","Productsubscriptionid = "+Productsubscriptionid);


        dashboard_reccyler=(RecyclerView)findViewById(R.id.dashboard_reccyler);
        dashboard_reccyler.setHasFixedSize(true);
        dashboard_reccyler.setLayoutManager(new LinearLayoutManager(Activity_ResourcesList.this));
        // GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(),2);
        //  dashboard_reccyler.setLayoutManager(mLayoutManager1);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        linearfilter = (LinearLayout) findViewById(R.id.linearfilter);
        linearsort = (LinearLayout) findViewById(R.id.linearsort);
        linearfilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filtering();

            }
        });
        linearsort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sorting();
            }
        });
       // setUpReccyler();

        new LoadResourceList().execute();
        new DownloadCategory().execute();
        new DownloadEducation().execute();
        new DownloadLanguage().execute();

    }

    private void filtering() {

        cmainshop_id = "";
        cmainshop_name = "";
        cmainqualificationshop_id = "";
        cmainqualificationshop_name = "";
        cmainlanguageshop_id = "";
        cmainlanguageshop_name = "";

        final List<String> points = new ArrayList<>();

        final Dialog logindialog = new Dialog(Activity_ResourcesList.this);
        logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater1 = (LayoutInflater) Activity_ResourcesList.this.getSystemService(Activity_ResourcesList.this.LAYOUT_INFLATER_SERVICE);
        View convertView1 = (View) inflater1.inflate(R.layout.dialog_resourcefilter, null);

        //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

        logindialog.setContentView(convertView1);
        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
        spincategory = (Spinner) convertView1.findViewById(R.id.spincategory);
        spinqualification = (Spinner) convertView1.findViewById(R.id.spinqualification);
        spinlanguage = (Spinner) convertView1.findViewById(R.id.spinlanguage);
        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        logindialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
        lp1.copyFrom(logindialog.getWindow().getAttributes());
        lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp1.gravity = Gravity.CENTER;
        logindialog.getWindow().setAttributes(lp1);

        TextView tvMin = (TextView) convertView1.findViewById(R.id.textMin1);
        TextView tvMax = (TextView) convertView1.findViewById(R.id.textMax1);

        // get seekbar from view
        final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) convertView1.findViewById(R.id.rangeSeekbar1);
        rangeSeekbar.setMinValue(0);
        rangeSeekbar.setMaxValue(100000);
// set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
                maxPrice=String.valueOf(maxValue);
                minPrice=String.valueOf(minValue);
                System.out.println(maxPrice+minPrice);
                // setUpRecycler1();
                Log.e("testing","min = "+minPrice);
                Log.e("testing","max = "+maxPrice);
            }
        });





        TextView text_submit = (TextView) convertView1.findViewById(R.id.text_submit);
        text_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logindialog.dismiss();

                new LoadResourceList().execute();

            }
        });




        spinnerAdapter adapter = new spinnerAdapter(Activity_ResourcesList.this, R.layout.spinner_item);
        adapter.addAll(worldlistmain);
        adapter.add("Select Category");
        spincategory.setAdapter(adapter);
        spincategory.setSelection(adapter.getCount());
        spincategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml

                if (cmainshop_id==null||cmainshop_id.length()==0||cmainshop_id.equals("")){
                    if(spincategory.getSelectedItem() == "Select Category")
                    {
                        //Do nothing.
                    }
                    else{
                        cmainshop_id = worldmain.get(position).getId();
                        cmainshop_name = worldmain.get(position).getName();

                        Log.e("ugender", "capartmentid_id = " + cmainshop_id);
                        Log.e("ugender", "capartmentid_name = " + cmainshop_name);

                    }
                }else{

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerAdapter adapter2 = new spinnerAdapter(Activity_ResourcesList.this, R.layout.spinner_item);
        adapter2.addAll(worldlistmainqualification);
        adapter2.add("Select Qualification");
        spinqualification.setAdapter(adapter2);
        spinqualification.setSelection(adapter2.getCount());
        spinqualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml

                if (cmainqualificationshop_id==null||cmainqualificationshop_id.length()==0||cmainqualificationshop_id.equals("")){
                    if(spinqualification.getSelectedItem() == "Select Qualification")
                    {
                        //Do nothing.
                    }
                    else{
                        cmainqualificationshop_id = worldmainqualification.get(position).getId();
                        cmainqualificationshop_name = worldmainqualification.get(position).getName();

                        Log.e("ugender", "capartmentid_id = " + cmainqualificationshop_id);
                        Log.e("ugender", "capartmentid_name = " + cmainqualificationshop_name);

                    }
                }else{

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        spinnerAdapter adapter3 = new spinnerAdapter(Activity_ResourcesList.this, R.layout.spinner_item);
        adapter3.addAll(worldlistmainlanguage);
        adapter3.add("Select Language");
        spinlanguage.setAdapter(adapter3);
        spinlanguage.setSelection(adapter3.getCount());
        spinlanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0,
                                       View arg1, int position, long arg3) {
                // TODO Auto-generated method stub
                // Locate the textviews in activity_main.xml

                if (cmainlanguageshop_id==null||cmainlanguageshop_id.length()==0||cmainlanguageshop_id.equals("")){
                    if(spinlanguage.getSelectedItem() == "Select Language")
                    {
                        //Do nothing.
                    }
                    else{
                        cmainlanguageshop_id = worldmainlanguage.get(position).getId();
                        cmainlanguageshop_name = worldmainlanguage.get(position).getName();

                        Log.e("ugender", "capartmentid_id = " + cmainlanguageshop_id);
                        Log.e("ugender", "capartmentid_name = " + cmainlanguageshop_name);

                    }
                }else{

                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        logindialog.show();

    }

    private void sorting() {
        final Dialog logindialog = new Dialog(Activity_ResourcesList.this);
        logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater1 = (LayoutInflater) Activity_ResourcesList.this.getSystemService(Activity_ResourcesList.this.LAYOUT_INFLATER_SERVICE);
        View convertView1 = (View) inflater1.inflate(R.layout.dialog_resourcesort, null);

        //  StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

        logindialog.setContentView(convertView1);
        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);

        TextView textsalaryhightolow = (TextView) convertView1.findViewById(R.id.textsalaryhightolow);
        TextView textsalarylowtohigh = (TextView) convertView1.findViewById(R.id.textsalarylowtohigh);
        TextView textrecentlyadded = (TextView) convertView1.findViewById(R.id.textrecentlyadded);



        textsalaryhightolow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strsortby = "high_to_low";
                logindialog.dismiss();
                new LoadResourceList().execute();
            }
        });
        textsalarylowtohigh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strsortby = "low_to_high";
                logindialog.dismiss();
                new LoadResourceList().execute();
            }
        });
        textrecentlyadded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strsortby = "newly_added";
                logindialog.dismiss();
                new LoadResourceList().execute();
            }
        });

        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        logindialog.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp1 = new WindowManager.LayoutParams();
        lp1.copyFrom(logindialog.getWindow().getAttributes());
        lp1.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp1.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp1.gravity = Gravity.CENTER;
        logindialog.getWindow().setAttributes(lp1);



       // lowtohigh = (TextView) convertView1.findViewById(R.id.lowtohigh);


        logindialog.show();
    }

    @Override
    public void OnItemClickcropNames(int pos, String qty, int status) {

    }




    private void setUpReccyler() {
        openings_entity = new ArrayList<Entity_ResourseList>();

        for (int i = 0; i < TextDesignation.length; i++) {
            Entity_ResourseList feedInfo = new Entity_ResourseList();
            // feedInfo.setImages(productImage[i]);
            feedInfo.setTextdesignation(TextDesignation[i]);
            feedInfo.setTexteducation(TextEducation[i]);
            feedInfo.setTextexpectedsalary(TextExpectedSalary[i]);
            feedInfo.setTextdesc(TextDesc[i]);
            feedInfo.setTextdaysago(TextDaysAgo[i]);

            openings_entity.add(feedInfo);
        }
        openings_adapter = new Adapter_resourselist(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
        dashboard_reccyler.setAdapter(openings_adapter);
    }

    private class DownloadCategory extends AsyncTask<Void, Void, Void> {

        String status;

        ProgressDialog mProgress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_ResourcesList.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }
        @Override
        protected Void doInBackground(Void... params) {
            // Locate the Pojo_Topiccategory Class
            worldmain = new ArrayList<Pojo_Category>();
            // Create an array to populate the spinner
            worldlistmain = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address

               userpramas.add(new BasicNameValuePair(EndUrl.CategoryList_CategoryId, ProductId));

            Log.e("testing","userpramas = "+userpramas);
            jsonobjectmain = JSONfunctions
                    .getJSONfromURL(EndUrl.CategoryList_URL, "GET",userpramas);


            Log.e("testing","apartments = "+jsonobjectmain);

            try {

                status = jsonobjectmain.getString("status");

                if (status.equals("success")) {

                    // Locate the NodeList name
                    jsonarraymain = jsonobjectmain.getJSONArray("data");

                    if (jsonarraymain == null || jsonarraymain.length() == 0){

                    }else{
                        for (int i = 0; i < jsonarraymain.length(); i++) {
                            jsonobjectmain = jsonarraymain.getJSONObject(i);

                            Pojo_Category worldpop = new Pojo_Category();

                            worldpop.setName(jsonobjectmain.optString("name"));
                            worldpop.setId(jsonobjectmain.optString("id"));
				/*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*/
                            worldmain.add(worldpop);

                            // Populate spinner with country names
                            worldlistmain.add(jsonobjectmain.optString("name"));

                        }
                    }
                }else{

                }


            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void args) {



            // Locate the spinner in activity_main.xml
            // Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

/*
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.checkbox1);
            multiSelectionSpinner.setItems(students1);
            // multiSelectionSpinner.setSelection(new int[]{2, 6});
            multiSelectionSpinner.setListener(this);*/


            // -----------------------------spinner for age-----------------------------------

            if (status == null || status.length() == 0){
                mProgress.dismiss();
            }else if (status.equals("success")) {
                mProgress.dismiss();

            }else{
                mProgress.dismiss();
            }



            /*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*/

            // Spinner on item click listener


        }
    }


    private class DownloadEducation extends AsyncTask<Void, Void, Void> {

        String status;

        ProgressDialog mProgress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_ResourcesList.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }
        @Override
        protected Void doInBackground(Void... params) {
            // Locate the Pojo_Topiccategory Class
            worldmainqualification = new ArrayList<Pojo_Category>();
            // Create an array to populate the spinner
            worldlistmainqualification = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address

          //  userpramas.add(new BasicNameValuePair(EndUrl.CategoryList_CategoryId, ProductId));

            Log.e("testing","userpramas = "+userpramas);
            jsonobjectmainqualification = JSONfunctions
                    .getJSONfromURL(EndUrl.Qualification_URL, "GET",userpramas);


            Log.e("testing","apartments = "+jsonobjectmainqualification);

            try {

                status = jsonobjectmainqualification.getString("status");

                if (status.equals("success")) {

                    // Locate the NodeList name
                    jsonarraymainqualification = jsonobjectmainqualification.getJSONArray("data");

                    if (jsonarraymainqualification == null || jsonarraymainqualification.length() == 0){

                    }else{
                        for (int i = 0; i < jsonarraymainqualification.length(); i++) {
                            jsonobjectmainqualification = jsonarraymainqualification.getJSONObject(i);

                            Pojo_Category worldpop = new Pojo_Category();

                            worldpop.setName(jsonobjectmainqualification.optString("name"));
                            worldpop.setId(jsonobjectmainqualification.optString("id"));
				/*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*/
                            worldmainqualification.add(worldpop);

                            // Populate spinner with country names
                            worldlistmainqualification.add(jsonobjectmainqualification.optString("name"));

                        }
                    }
                }else{

                }


            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void args) {



            // Locate the spinner in activity_main.xml
            // Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

/*
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.checkbox1);
            multiSelectionSpinner.setItems(students1);
            // multiSelectionSpinner.setSelection(new int[]{2, 6});
            multiSelectionSpinner.setListener(this);*/


            // -----------------------------spinner for age-----------------------------------

            if (status == null || status.length() == 0){
                mProgress.dismiss();
            }else if (status.equals("success")) {
                mProgress.dismiss();

            }else{
                mProgress.dismiss();
            }



            /*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*/

            // Spinner on item click listener



        }
    }

    private class DownloadLanguage extends AsyncTask<Void, Void, Void> {

        String status;

        ProgressDialog mProgress;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_ResourcesList.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }
        @Override
        protected Void doInBackground(Void... params) {
            // Locate the Pojo_Topiccategory Class
            worldmainlanguage = new ArrayList<Pojo_Category>();
            // Create an array to populate the spinner
            worldlistmainlanguage = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address

           // userpramas.add(new BasicNameValuePair(EndUrl.CategoryList_CategoryId, ProductId));

            Log.e("testing","userpramas = "+userpramas);
            jsonobjectmainlanguage = JSONfunctions
                    .getJSONfromURL(EndUrl.Language_URL, "GET",userpramas);


            Log.e("testing","apartments = "+jsonobjectmainlanguage);

            try {

                status = jsonobjectmainlanguage.getString("status");

                if (status.equals("success")) {

                    // Locate the NodeList name
                    jsonarraymainlanguage = jsonobjectmainlanguage.getJSONArray("data");

                    if (jsonarraymainlanguage == null || jsonarraymainlanguage.length() == 0){

                    }else{
                        for (int i = 0; i < jsonarraymainlanguage.length(); i++) {
                            jsonobjectmainlanguage = jsonarraymainlanguage.getJSONObject(i);

                            Pojo_Category worldpop = new Pojo_Category();

                            worldpop.setName(jsonobjectmainlanguage.optString("name"));
                            worldpop.setId(jsonobjectmainlanguage.optString("id"));
				/*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*/
                            worldmainlanguage.add(worldpop);

                            // Populate spinner with country names
                            worldlistmainlanguage.add(jsonobjectmainlanguage.optString("name"));

                        }
                    }
                }else{

                }


            } catch (Exception e) {
                //Log.e("Error", e.getMessage());
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(Void args) {



            // Locate the spinner in activity_main.xml
            // Spinner mySpinner = (Spinner) findViewById(R.id.my_spinner);

/*
            MultiSelectionSpinner multiSelectionSpinner = (MultiSelectionSpinner) findViewById(R.id.checkbox1);
            multiSelectionSpinner.setItems(students1);
            // multiSelectionSpinner.setSelection(new int[]{2, 6});
            multiSelectionSpinner.setListener(this);*/


            // -----------------------------spinner for age-----------------------------------

            if (status == null || status.length() == 0){
                mProgress.dismiss();
            }else if (status.equals("success")) {
                mProgress.dismiss();

            }else{
                mProgress.dismiss();
            }



            /*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*/

            // Spinner on item click listener



        }
    }



    private void RetrofitCategorylist(String ProductId) {

        Log.e("testing","ProductId = "+ProductId);

        final ProgressDialog pProgressDialog;
        pProgressDialog = new ProgressDialog(Activity_ResourcesList.this);
        pProgressDialog.setMessage("Please Wait ...");
        pProgressDialog.setIndeterminate(false);
        pProgressDialog.setCancelable(false);
        pProgressDialog.show();

        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<CategoryListJson> login = api.CategoryListJson(ProductId);

        login.enqueue(new Callback<CategoryListJson>() {
            @Override
            public void onResponse(Call<CategoryListJson> call, Response<CategoryListJson> response) {
                pProgressDialog.dismiss();
                Log.e("testing","status = "+response.body().getStatus());
                Log.e("testing","response = "+response.body().getResponse().getType());
                //  Log.e("testing","response = "+response.body().getData().getPageContent());

                if (response.body().getStatus() == null || response.body().getStatus().length() == 0){

                }else if (response.body().getStatus().equals("success")) {
                    if (response.body().getResponse() == null ){

                    }else if (response.body().getResponse().getType().equals("data_found")){


                        spinnerAdapter adapter = new spinnerAdapter(Activity_ResourcesList.this, R.layout.spinner_item);
                        adapter.addAll(worldlistmain);
                        adapter.add("Select Category");


                        spincategory.setAdapter(adapter);
                        spincategory.setSelection(adapter.getCount());


                      /*  openings_entity = response.body().getData();
                        openings_adapter = new Adapter_resourse(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
                        dashboard_reccyler.setAdapter(openings_adapter);*/

                    }else{
                        Toast.makeText(Activity_ResourcesList.this, response.body().getResponse().getType(), Toast.LENGTH_SHORT).show();
                    }








                    //  Toast.makeText(Activity_Event_Details.this, message, Toast.LENGTH_SHORT).show();


                } else  {
                    Log.e("testing","error");
                    Toast.makeText(Activity_ResourcesList.this, response.body().getResponse().getType(), Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<CategoryListJson> call, Throwable t) {
                Toast.makeText(Activity_ResourcesList.this,t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                pProgressDialog.dismiss();

            }
        });





    }

    public class LoadResourceList extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String headers;
        String childs;
        String Result;

        String status;
        String strresponse;
        String strdata;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_ResourcesList.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        protected String doInBackground(String... args) {
            Integer result = 0;
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            openings_entity = new ArrayList<Entity_ResourseList>();

            Log.e("testing", "jsonParser startedkljhk");
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_subscription_id, Productsubscriptionid));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_category_id, cmainshop_id));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_education_id, cmainqualificationshop_id));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_language_id, cmainlanguageshop_id));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_min_salary, minPrice));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_max_salary, maxPrice));
            userpramas.add(new BasicNameValuePair(EndUrl.GetResourceList_sort_by, strsortby));
            //  Log.e("testing", "feader_reg_id" + id);
            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetResourceList_URL, "GET", userpramas);

            Log.e("testing", "userpramas = " + userpramas);
            Log.e("testing", "jsonParser = " + json);


            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                        strdata = json.getString("data");

                        JSONArray responcearray = new JSONArray(strdata);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);



                            Entity_ResourseList feedInfo = new Entity_ResourseList();
                          //  feedInfo.setTextdesignation(post.getString("name"));
                            feedInfo.setTextexpectedsalary(post.getString("min_salary")+"-"+post.getString("max_salary"));

                            feedInfo.setTextmobileno(post.getString("mobile"));
                            feedInfo.setTextemailid(post.getString("email"));
                            feedInfo.setTextdesc(post.getString("description"));
                            feedInfo.setTextresume(post.getString("resume"));
                            feedInfo.setTextdaysago(post.getString("updated_at"));

                            String streducation = post.getString("education");
                            JSONObject jsonobjecteducation = new JSONObject(streducation);
                            feedInfo.setTexteducation(jsonobjecteducation.getString("name"));

                            String strcategory = post.getString("category");
                            JSONObject jsonobjectcategory = new JSONObject(strcategory);
                            feedInfo.setTextcategory(jsonobjectcategory.getString("name"));
                            Log.e("testing", "strcategory value=" + strcategory);

                            openings_entity.add(feedInfo);
                        }

                    } else {
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
            pDialog.dismiss();
            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                if (strresponse == null || strresponse.length() == 0){

                }else if (strtype.equals("data_found")){

                    openings_adapter = new Adapter_resourselist(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
                    dashboard_reccyler.setAdapter(openings_adapter);

                }else{
                    Toast.makeText(Activity_ResourcesList.this, strmessage, Toast.LENGTH_SHORT).show();
                    openings_adapter = new Adapter_resourselist(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
                    dashboard_reccyler.setAdapter(openings_adapter);
                }



            }else{
                Toast.makeText(Activity_ResourcesList.this, strmessage, Toast.LENGTH_SHORT).show();
                openings_adapter = new Adapter_resourselist(Activity_ResourcesList.this, openings_entity, mCallbackcropnames);
                dashboard_reccyler.setAdapter(openings_adapter);
            }



        }


    }


}
