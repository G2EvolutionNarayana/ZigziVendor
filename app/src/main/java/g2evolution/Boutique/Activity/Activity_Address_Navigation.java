package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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

import g2evolution.Boutique.Adapter.Adapter_address_Navigation;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_address;
import g2evolution.Boutique.FeederInfo.WorldPopulation;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_Address_Navigation extends AppCompatActivity implements Adapter_address_Navigation.OnItemClick, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String LOG_TAG = "MainActivity";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    //  private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    // String streditaddress;
    TextView editaddress;
    ImageView back,xIvAddress;
    EditText editname, editmob, editcity, editstate, editmob1, editpincode, edithouseno;
    //------------------searchable spinner----------------
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<WorldPopulation> world;//spinapartment
    ArrayList<String> worldlist;//spinapartment
    // Spinner spinapartment;
    String capartmentid_id;

    // TextView addcross,addname,address,addno;
    //  String _addcross,_addname,_address,_addno, _addpincode;
    String strname, stradd, strmob, strmob1, strhouseno, strpincode, stremailid;
    String status, message, userid, Apay;
    String Username, Usermail, Usermob;
    ArrayList<HashMap<String, String>> arraylist1;
    Context context;
    Adapter_address_Navigation mAdapterFeeds;
    RecyclerView rView;
    String addressid;
    String[] name = new String[]{"jana", "janardhan", "reddy"};
    String[] saddress = new String[]{"marathalli", "hsr", "btm"};
    String[] landmark = new String[]{"beside axis bank", "beside  sfddsf", "opposite watertank"};
    String[] mobileno = new String[]{"8050044482", "8886075422", "7799944412"};
    String[] pincode = new String[]{"560037", "560035", "560034"};
    double latitude, longitude;
    JSONParser jsonParser = new JSONParser();
    String latitudestr, edit_string_remove;
    TextView edit_setting_adress, edit_setting_remove;
    String getid, getTextname, getTextlandmark, getTextmobileno, getTextstate, getTextcity, getTextmob1, getTextpincode, getTexthouseno, getGetTextlandmark1, getTextaddress, getimpnotice, getemailid;
    Dialog logindialog12, logindialog123;
    AutoCompleteTextView mAutocompleteTextView;
    Dialog logindialog;
    String strname1, straddress, strcity,
            strpostal, strcountry, strstate, strlanti, strlong, strsublocality, stradminarea, strsubadminarea;
    View convertView, convertView1;
    EditText editlandmark;
    String strlandmrk;
    private Adapter_address_Navigation.OnItemClick mCallback;
    private ArrayList<FeederInfo_address> allItems1 = new ArrayList<FeederInfo_address>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_address> mListFeederInfo;
    private GoogleApiClient mGoogleApiClient;

    private static boolean isValidateEmail(String email) {

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__address_navigation);


        editaddress = (TextView) findViewById(R.id.editaddress);
        //  selectadd = (TextView)findViewById(R.id.selectadd);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        userid = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        Log.e("testing", "edit_string_remove===" + edit_string_remove);
        SharedPreferences prefuserdata1 = this.getSharedPreferences("pay", 0);
        Apay = prefuserdata1.getString("Apay", "");

        mCallback = this;

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_Address_Navigation.this));

        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        mFeedRecyler.setHasFixedSize(true);

        ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
        if (cd.isConnectingToInternet()) {

            new LoadAddress().execute();

        } else {


            Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                logindialog = new Dialog(Activity_Address_Navigation.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) Activity_Address_Navigation.this.getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
                convertView = (View) inflater.inflate(R.layout.edit_address, null);


                logindialog.setContentView(convertView);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp);


                xIvAddress = (ImageView) convertView.findViewById(R.id.xIvAddress);
                xIvAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Activity_Address_Navigation.this, AddressSelectionActivity.class);
                        startActivityForResult(i, 1);
                        logindialog.cancel();
                    }
                });
                mAutocompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id
                        .locationsedt);
                editlandmark = (EditText) convertView.findViewById(R.id.editlandmark);
                editname = (EditText) convertView.findViewById(R.id.editname);

                editmob = (EditText) convertView.findViewById(R.id.editmob);
                editcity = (EditText) convertView.findViewById(R.id.editcity);
                editstate = (EditText) convertView.findViewById(R.id.editstate);
                editmob1 = (EditText) convertView.findViewById(R.id.editmob1);
                editpincode = (EditText) convertView.findViewById(R.id.editpincode);
                edithouseno = (EditText) convertView.findViewById(R.id.edithouseno);
                Button addsubmit = (Button) convertView.findViewById(R.id.addsubmit);

                addsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        submit();

                        Log.e("testing", "strpincode====" + strpincode);
                        Log.e("testing", "strpostal====" + strpostal);
                        Log.e("testing", "capartmentid_id====" + capartmentid_id);


                    }
                });

                logindialog.show();

            }
        });

    }

    private void submit() {

        strname = editname.getText().toString();
        stradd = mAutocompleteTextView.getText().toString();
        strmob = editmob.getText().toString();
        strcity = editcity.getText().toString();
        strstate = editstate.getText().toString();
        strmob1 = editmob1.getText().toString();
        strpincode = editpincode.getText().toString();
        strhouseno = edithouseno.getText().toString();
        strlandmrk = editlandmark.getText().toString();
        if (!Validatename()) {
            return;
        }

        if (!validateaddress()) {
            return;
        }

        if (stradd.equals(null) || stradd.isEmpty()) {
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter Area", Toast.LENGTH_LONG).show();
            return;
        }
        if (strlandmrk.equals(null) || strlandmrk.isEmpty()) {
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter Landmark", Toast.LENGTH_LONG).show();
            return;
        }
        if (strhouseno.equals(null)||strhouseno.isEmpty()){
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter House No", Toast.LENGTH_LONG).show();
            return;
        }
        if (strcity.equals(null)||strcity.isEmpty()){
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter City", Toast.LENGTH_LONG).show();
            return;
        }

        if (strstate.equals(null)||strstate.isEmpty()){
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter State", Toast.LENGTH_LONG).show();
            return;
        }

        if (strmob1.equals(null) || strmob1.isEmpty()||strmob1.length()!=10) {
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter Mobile number", Toast.LENGTH_LONG).show();
            return;
        }
        if (strpincode.equals(null)||strpincode.isEmpty()||strpincode.length()!=6){
            Toast.makeText(Activity_Address_Navigation.this, "Please Enter Pincode", Toast.LENGTH_LONG).show();
            return;
        }
        //  capartmentid_id = spinapartment.getSelectedItem().toString();

        ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
        if (cd.isConnectingToInternet()) {

            //  new TopTrend().execute();
            new AddAddress().execute();
        } else {

            Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

         /*   }else {

                Toast.makeText(Activity_Address_Navigation.this, "Pincode and Address Mismatch", Toast.LENGTH_SHORT).show();

            }
        }*/


    }

    private void submit1() {

        if (!Validatename()) {
            return;
        }


        if (!validateaddress()) {
            return;
        }

      /*  if (!validatemoibile()) {
            return;
        }*/
     /*   if (capartmentid_id == null || capartmentid_id.length() == 0 || spinapartment.getSelectedItem().toString().equals("Select Pincode")){


            Toast.makeText(Activity_Address_Navigation.this, "Please select Pincode", Toast.LENGTH_SHORT).show();

        }

        else {*/

          /*  if (strpostal == null || strpostal.length() == 0 || strpostal.equals("null")) {


                Toast.makeText(Activity_Address_Navigation.this, "Address Incorrect", Toast.LENGTH_SHORT).show();


            } else {

                if (capartmentid_id.equals(strpostal)) {*/


        strname = editname.getText().toString();
        stradd = mAutocompleteTextView.getText().toString();
        strmob = editmob.getText().toString();
        strcity = editcity.getText().toString();
        strstate = editstate.getText().toString();
        strmob1 = editmob1.getText().toString();
        strpincode = editpincode.getText().toString();
        strhouseno = edithouseno.getText().toString();
        strlandmrk = editlandmark.getText().toString();
        //  capartmentid_id = spinapartment.getSelectedItem().toString();

        ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
        if (cd.isConnectingToInternet()) {


            //  new TopTrend().execute();
            new AddAddressUpdate().execute();


        } else {

            Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

                /*}else {


                    Toast.makeText(Activity_Address_Navigation.this, "Pincode and Address Mismatch", Toast.LENGTH_SHORT).show();

                }


            }*/
        // }


    }

    private void setUpRecycler() {

        mListFeederInfo = new ArrayList<FeederInfo_address>();

        for (int i = 0; i < name.length; i++) {
            FeederInfo_address feedInfo = new FeederInfo_address();

            feedInfo.setTextname(name[i]);
            feedInfo.setTextaddress(saddress[i]);
            feedInfo.setTextlandmark(landmark[i]);
            feedInfo.setTextmobileno(mobileno[i]);
            feedInfo.setTextpincode(pincode[i]);

            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds = new Adapter_address_Navigation(this, mListFeederInfo, mCallback);
        mFeedRecyler.setAdapter(mAdapterFeeds);

    }


    private boolean Validatename() {

        if (editname.getText().toString().trim().isEmpty()) {

            Toast.makeText(Activity_Address_Navigation.this, "Enter name", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            editname.requestFocus();
            return false;

        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;

    }

    private boolean validateaddress() {

        if (mAutocompleteTextView.getText().toString().trim().isEmpty()) {

            Toast.makeText(Activity_Address_Navigation.this, "Please Select Area", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            mAutocompleteTextView.requestFocus();
            return false;


        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;

    }





  /*  private boolean validatepincode() {

        String phone = editpincode.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 6) {

            Toast.makeText(Activity_Address_Navigation.this, "Enter Pincode", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            editpincode.requestFocus();
            return false;
        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;
    }*/


    @Override
    public void onClickedItem(int pos, int qty, int status) {
        Log.e("DMen", "Pos:" + pos + "Qty:" + qty);
        Log.e("testing", "status  = " + status);
        Log.e("testing", "title inm  = " + mListFeederInfo.get(pos).getId());

        getid = mListFeederInfo.get(pos).getId();
        getTextname = mListFeederInfo.get(pos).getTextname();
        getTextlandmark = mListFeederInfo.get(pos).getTextlandmark();
        getTextmobileno = mListFeederInfo.get(pos).getTextmobileno();
        getTextcity = mListFeederInfo.get(pos).getCity();
        getTextstate = mListFeederInfo.get(pos).getState();
        getTextmob1 = mListFeederInfo.get(pos).getAlternatemobileno();
        getTextpincode = mListFeederInfo.get(pos).getTextpincode();
        getTexthouseno = mListFeederInfo.get(pos).getHouseno();
        getGetTextlandmark1 = mListFeederInfo.get(pos).getLandMark1();
        getimpnotice = mListFeederInfo.get(pos).getImpnotice();
        strpostal = mListFeederInfo.get(pos).getTextpincode();
        getTextaddress = mListFeederInfo.get(pos).getTextaddress();
        getemailid = mListFeederInfo.get(pos).getEmailId();
        strlanti = mListFeederInfo.get(pos).getLatitude();
        strlong = mListFeederInfo.get(pos).getLogitude();

        addressid = String.valueOf(qty);

        Log.e("testing", "testing=================quantity" + qty);
        Log.e("testing", "testing=================getid" + getid);
        Log.e("testing", "testing=================getTextpincode===========" + strpostal);
        Log.e("testing", "testing=================getTextpincode===========" + getGetTextlandmark1);
        // new Loader2().execute();


        logindialog123 = new Dialog(Activity_Address_Navigation.this);
        logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
        View convertView = (View) inflater.inflate(R.layout.edit_setting, null);

        /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

        logindialog123.setContentView(convertView);
        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        logindialog123.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(logindialog123.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        logindialog123.getWindow().setAttributes(lp);
        edit_setting_adress = (TextView) convertView.findViewById(R.id.edit_setting);
        edit_setting_remove = (TextView) convertView.findViewById(R.id.remove);

        edit_setting_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logindialog123.dismiss();
                Log.e("testing", "edit=edit_setting_adress==" + addressid);


                logindialog12 = new Dialog(Activity_Address_Navigation.this);
                logindialog12.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) Activity_Address_Navigation.this.getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
                convertView1 = (View) inflater.inflate(R.layout.edit_address, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                logindialog12.setContentView(convertView1);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog12.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog12.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog12.getWindow().setAttributes(lp);


                editlandmark = (EditText) convertView1.findViewById(R.id.editlandmark);
                editname = (EditText) convertView1.findViewById(R.id.editname);

                editmob = (EditText) convertView1.findViewById(R.id.editmob);
                editcity = (EditText) convertView1.findViewById(R.id.editcity);
                editstate = (EditText) convertView1.findViewById(R.id.editstate);
                editmob1 = (EditText) convertView1.findViewById(R.id.editmob1);
                editpincode = (EditText) convertView1.findViewById(R.id.editpincode);
                edithouseno = (EditText) convertView1.findViewById(R.id.edithouseno);



             /*   mGoogleApiClient = new GoogleApiClient.Builder(Activity_Address_Navigation.this)
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage(Activity_Address_Navigation.this, GOOGLE_API_CLIENT_ID, Activity_Address_Navigation.this)
                        .addConnectionCallbacks(Activity_Address_Navigation.this)
                        .build();*/

                mAutocompleteTextView = (AutoCompleteTextView) convertView1.findViewById(R.id
                        .locationsedt);
             /*   mAutocompleteTextView = (AutoCompleteTextView)convertView1. findViewById(R.id
                        .locationsedt);
                mAutocompleteTextView.setThreshold(2);
                mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);*/
            /*    mPlaceArrayAdapter = new PlaceArrayAdapter(Activity_Address_Navigation.this, android.R.layout.simple_list_item_1,
                        BOUNDS_MOUNTAIN_VIEW, null);
                mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);*/


                mAutocompleteTextView.setText(getTextaddress);
                editname.setText(getTextname);
                editmob.setText(getTextmobileno);
                editcity.setText(getTextcity);
                editstate.setText(getTextstate);
                editmob1.setText(getTextmob1);
                editpincode.setText(getTextpincode);
                edithouseno.setText(getTexthouseno);
                editlandmark.setText(getTextlandmark);
                // spinapartment = (Spinner) convertView1.findViewById(R.id.spinapartment);
                Button addsubmit = (Button) convertView1.findViewById(R.id.addsubmit);


               /* ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
                if (cd.isConnectingToInternet()) {

                    new DownloadJSON().execute();

                } else {


                    Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }*/

                addsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        submit1();

                    }
                });


          /*    editcross.setText(_addcross);
              editname.setText(_addname);
              editadd.setText(_address);
              editmob.setText(_addno);
              editpincode.setText(_addpincode);*/

                logindialog12.show();

            }
        });

        edit_setting_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("testing", "edit==edit_setting_remove=" + addressid);

                new DeteleAddressUpdate().execute();
            }
        });


        logindialog123.show();

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.ADDdeliveryAddress_Id, userid);
            object.put(EndUrl.ADDdeliveryAddress_name, strname);
            object.put(EndUrl.ADDdeliveryAddress_address, stradd);
            object.put(EndUrl.ADDdeliveryAddress_mobile, strmob);
            object.put(EndUrl.ADDdeliveryAddress_pincode, capartmentid_id);

            object.put(EndUrl.ADDdeliveryAddress_add_landmark, strlandmrk);

            object.put(EndUrl.ADDdeliveryAddress_latitude, strlanti);
            object.put(EndUrl.ADDdeliveryAddress_longitude, strlong);

            //if you want to modify some value just do like this.

            details.put(EndUrl.ADDdeliveryAddressjsonobject_outside_otp, object);
            Log.d("json", details.toString());
            Log.e("testing", "json" + details.toString());

            Log.e("testing", "details===Address=" + details);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

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
            Log.e("testing", "result in post message=========" + json.getString("message"));
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
                //  userid  = post.getString("userid ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    public JSONObject makingJson5() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {


            object.put(EndUrl.ADDdeliveryAddress__latitude, strlanti);
            object.put(EndUrl.ADDdeliveryAddress__longitude, strlong);
            object.put(EndUrl.ADDdeliveryAddress_Updte_Id, userid);
            object.put(EndUrl.ADDdeliveryAddress_Updte_address_Id, addressid);
            object.put(EndUrl.ADDdeliveryAddress_Updte_name, strname);
            object.put(EndUrl.ADDdeliveryAddress_Updte_address, stradd);
            object.put(EndUrl.ADDdeliveryAddress_Updte_pincode, strpostal);
            object.put(EndUrl.ADDdeliveryAddress_Updte_mobile, strmob);

            object.put(EndUrl.ADDdeliveryAddress_addlandmark, strlandmrk);

            //if you want to modify some value just do like this.

            details.put(EndUrl.ADDdeliveryAddressjsonobject_Updte_outside_otp, object);
            Log.d("json", details.toString());
            Log.e("testing", "json" + details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }

    public JSONObject postJsonObject5(String url, JSONObject loginJobj) {
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
            Log.e("testing", "result in post message=========" + json.getString("message"));
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
                //  userid  = post.getString("userid ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    public JSONObject makingJson1() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.DeliveryAddress_userId, userid);


            //if you want to modify some value just do like this.

         /*   details.put(EndUrl.SIGNUPjsonobject_outside_register,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());
*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

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
            Log.e("testing", "result in post message=========" + json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");
            //  total_record = json.getString("total_record");


            String arrayresponce = json.getString("data");


            Log.e("testing", "adapter value=" + arrayresponce);

            JSONArray responcearray = new JSONArray(arrayresponce);
            Log.e("testing", "responcearray value=" + responcearray);

            for (int i = 0; i < responcearray.length(); i++) {

                JSONObject post = responcearray.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();

                FeederInfo_address feedInfo = new FeederInfo_address();


                feedInfo.setId(post.optString("addressId"));
                feedInfo.setTextname(post.optString("name"));
                feedInfo.setTextaddress(post.optString("deliveryAddress"));
                feedInfo.setTextlandmark(post.optString("landMark"));
                feedInfo.setTextmobileno(post.optString("mobile"));
                feedInfo.setTextpincode(post.optString("pincode"));
                feedInfo.setImpnotice(post.optString("imp_note"));
                feedInfo.setEmailId(post.optString("emailId"));
                feedInfo.setLatitude(post.optString("latitude"));
                feedInfo.setLogitude(post.optString("longitude"));
                feedInfo.setLandMark1(post.optString("landmark1"));

                mListFeederInfo.add(feedInfo);

                // empId = post.getString("empId");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    //--------------------------------------------------------------------------------------------------------------

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public JSONObject makingJson2() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();


        try {

            object.put(EndUrl.AddressDelete_AddressId, addressid);

            Log.d("json", object.toString());
            Log.e("testing", "json" + details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }

    public JSONObject postJsonObject2(String url, JSONObject loginJobj) {
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
            Log.e("testing", "testing in json result=======" + result);
            Log.e("testing", "testing in json result json=======" + json);
            Log.e("testing", "result in post status=========" + json.getString("status"));
            Log.e("testing", "result in post message=========" + json.getString("message"));
            status = json.getString("status");
            message = json.getString("message");

            // String arrayresponce = json.getString("data");
            //  Log.e("testing", "adapter value=" + arrayresponce);


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

    @Override
    public void onConnected(Bundle bundle) {
        //  mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

        Log.e("testing", "LOG_TAG====api connected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(Activity_Address_Navigation.this, "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

        Log.e("testing", "LOG_TAG====api failed");
    }

    @Override
    public void onConnectionSuspended(int i) {
        //  mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
        Log.e("testing", "LOG_TAG====api suspend");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((Activity) context);
            mGoogleApiClient.disconnect();
        }
    }






    class Address extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            mListFeederInfo = new ArrayList<FeederInfo_address>();
            return postJsonObject1(EndUrl.DeliveryAddress_URL, makingJson1());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {


                    mAdapterFeeds = new Adapter_address_Navigation(Activity_Address_Navigation.this, mListFeederInfo, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);


                } else {
                }

            } else {
                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    class Loader2 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            } else {
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>" + "Loading..." + "</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject2(EndUrl.AddressDelete_URL, makingJson2());
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result != null) {
                dialog.dismiss();

                Log.e("testing", "result in post execute=========" + result);

                if (status.equals("success")) {

                    //  Toast.makeText(Activity_Address_Navigation.this, message, Toast.LENGTH_LONG).show();
                    logindialog123.dismiss();
                    new Address().execute();

                    // logindialog12.dismiss();

                } else {
                    //  Toast.makeText(Activity_Address_Navigation.this, message, Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    class AddAddress extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_Address_Navigation.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_address_type, ""));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_name, strname));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_phone, strmob1));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Alternate_phone, strmob));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_House_no, strhouseno));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Locality, stradd));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Landmark, strlandmrk));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_City, strcity));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_State, strstate));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_pin, strpincode));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.AddAddress_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");

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
                logindialog.dismiss();
                new LoadAddress().execute();

            } else if (status.equals("failure")) {
                Toast.makeText(Activity_Address_Navigation.this, strmessage, Toast.LENGTH_SHORT).show();
            } else {

            }


        }

    }

    class AddAddressUpdate extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_Address_Navigation.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_id, addressid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_address_type, ""));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_name, strname));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_phone, strmob));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Alternate_phone, strmob1));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_House_no, strhouseno));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Locality, stradd));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_Landmark, strlandmrk));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_City, strcity));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_State, strstate));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_pin, strpincode));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.AddAddress_URL;
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
                logindialog123.dismiss();
                new LoadAddress().execute();

            } else if (status.equals("failure")) {
                Toast.makeText(Activity_Address_Navigation.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            } else {

            }


        }

    }

    class DeteleAddressUpdate extends AsyncTask<String, String, String> {
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
            mProgress = new ProgressDialog(Activity_Address_Navigation.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);




        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.DeleteAddress_id, addressid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.DeleteAddress_URL;
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
                logindialog123.dismiss();
                new LoadAddress().execute();

            } else if (status.equals("failure")) {
                Toast.makeText(Activity_Address_Navigation.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            } else {

            }


        }

    }

    class LoadAddress extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(Activity_Address_Navigation.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            mListFeederInfo = new ArrayList<FeederInfo_address>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetAddressList_user_id, userid));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetAddressList_URL, "GET", userpramas);

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


                        JSONArray responcearray = new JSONArray(arrayresponse);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();

                            FeederInfo_address feedInfo = new FeederInfo_address();


                            feedInfo.setId(post.optString("id"));
                            feedInfo.setTextname(post.optString("name"));
                            feedInfo.setTextaddress(post.optString("locality"));
                            feedInfo.setTextlandmark(post.optString("landmark"));
                            feedInfo.setTextmobileno(post.optString("phone"));
                            feedInfo.setAlternatemobileno(post.optString("alternate_phone"));
                            feedInfo.setHouseno(post.optString("house_no"));
                            feedInfo.setCity(post.optString("city"));
                            feedInfo.setState(post.optString("state"));
                            feedInfo.setTextpincode(post.optString("pin"));
                            // feedInfo.setImpnotice(post.optString("imp_note"));

                            mListFeederInfo.add(feedInfo);


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

            //  progressbarloading.setVisibility(View.GONE);
            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")) {

            } else if (status.equals("success")) {


                mAdapterFeeds = new Adapter_address_Navigation(Activity_Address_Navigation.this, mListFeederInfo, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);


            } else {


                mAdapterFeeds = new Adapter_address_Navigation(Activity_Address_Navigation.this, mListFeederInfo, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);


            }


        }

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                mAutocompleteTextView.setText(data.getStringExtra("Address"));
                editcity.setText(data.getStringExtra("city"));
                editstate.setText(data.getStringExtra("state"));
                editpincode.setText(data.getStringExtra("postalCode"));
                editlandmark.setText(data.getStringExtra("knownName"));

                logindialog.show();
            }
        }
    }

}
