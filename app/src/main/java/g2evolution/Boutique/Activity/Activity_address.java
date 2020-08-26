package g2evolution.Boutique.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import g2evolution.Boutique.Adapter.Adapter_address;
import g2evolution.Boutique.Adapter.spinnerAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.FeederInfo_address;
import g2evolution.Boutique.FeederInfo.WorldPopulation;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.ConnectionDetector;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.Utility.JSONfunctions1;

/**
 * Created by G2e Android on 05-02-2018.
 */

public class Activity_address extends AppCompatActivity implements Adapter_address.OnItemClick, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final int GOOGLE_API_CLIENT_ID = 0;
    // private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final String LOG_TAG = "MainActivity";
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    // String streditaddress;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    TextView editaddress;
    ImageView back;
    EditText editname, editmob, edithouseno, editcity, editstate, editmob1, editpincode;
    //------------------searchable spinner----------------
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<WorldPopulation> world;//spinapartment
    ArrayList<String> worldlist;//spinapartment
    Spinner spinapartment;

    // TextView addcross,addname,address,addno;
    //  String _addcross,_addname,_address,_addno, _addpincode;
    String capartmentid_id;
    String strname, stradd, strmob, strmob1, strhouseno, strpincode, stremailid;
    String status, message, userid, Apay;
    String Username, Usermail, Usermob;
    ArrayList<HashMap<String, String>> arraylist1;
    Context context;
    Adapter_address mAdapterFeeds;
    RecyclerView rView;

    String addressid;
    String[] name = new String[]{"jana", "janardhan", "reddy"};
    String[] saddress = new String[]{"marathalli", "hsr", "btm"};
    String[] landmark = new String[]{"beside axis bank", "beside  sfddsf", "opposite watertank"};
    String[] mobileno = new String[]{"8050044482", "8886075422", "7799944412"};
    String[] pincode = new String[]{"560037", "560035", "560034"};

    String edit_string_remove;

    TextView edit_setting_adress, edit_setting_remove;

    ImageView xIvAddress;

    String getid, getTextname, getTextlandmark, getTextmobileno, getTexthouseno, getTextcity, getTextstate, getTextmob1, getTextpincode, getTextaddress, getimpnotice;

    Dialog logindialog;

    Dialog logindialog12, logindialog123;

    AutoCompleteTextView mAutocompleteTextView;
    String regId;
    double latitude;
    double longitude;
    JSONParser jsonParser = new JSONParser();
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String strname1, straddress, strcity, getGetTextlandmark1,
            strpostal, strcountry, strstate, strlanti, strlong, strsublocality, stradminarea, strsubadminarea;
    EditText editlandmark;
    String strlandmrk;
    private Adapter_address.OnItemClick mCallback;
    private ArrayList<FeederInfo_address> allItems1 = new ArrayList<FeederInfo_address>();
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_address> mListFeederInfo;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address);


        editaddress = (TextView) findViewById(R.id.editaddress);
        //  selectadd = (TextView)findViewById(R.id.selectadd);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // addcross = (TextView)findViewById(R.id.addcross);
        //  addname = (TextView)findViewById(R.id.addname);
        //  address = (TextView)findViewById(R.id.address);
        //  addno = (TextView)findViewById(R.id.addno);

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        userid = prefuserdata.getString("UserId", "");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        Log.e("testing", "edit_string_remove===" + edit_string_remove);
//        SharedPreferences prefuserdata1 = this.getSharedPreferences("pay", 0);
//        Apay = prefuserdata1.getString("Apay", "");

        mCallback = this;

        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_address.this));
        //setUpRecycler();
        // context = this;
        ///  lLayout = new GridLayoutManager(Activity_cart.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);

        // setUpRecycler();

        ConnectionDetector cd = new ConnectionDetector(Activity_address.this);
        if (cd.isConnectingToInternet()) {

            new LoadAddress().execute();

        } else {


            Toast.makeText(Activity_address.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


        // addres edit dialog open
        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                logindialog = new Dialog(Activity_address.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) Activity_address.this.getSystemService(Activity_address.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.edit_address, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                logindialog.setContentView(convertView);

                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp);

                xIvAddress = (ImageView) convertView.findViewById(R.id.xIvAddress);
                editname = (EditText) convertView.findViewById(R.id.editname);
                editmob = (EditText) convertView.findViewById(R.id.editmob);
                editcity = (EditText) convertView.findViewById(R.id.editcity);
                editstate = (EditText) convertView.findViewById(R.id.editstate);
                editmob1 = (EditText) convertView.findViewById(R.id.editmob1);
                editpincode = (EditText) convertView.findViewById(R.id.editpincode);
                edithouseno = (EditText) convertView.findViewById(R.id.edithouseno);

                editlandmark = (EditText) convertView.findViewById(R.id.editlandmark);

                mAutocompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id.locationsedt);

                Button addsubmit = (Button) convertView.findViewById(R.id.addsubmit);
                xIvAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Activity_address.this, AddressSelectionActivity.class);
                        startActivityForResult(i, 1);
                        logindialog.cancel();
                    }
                });

                addsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        submit();


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
        strhouseno = edithouseno.getText().toString();
        strcity = editcity.getText().toString();
        strstate = editstate.getText().toString();
        strmob1 = editmob1.getText().toString();
        strpincode = editpincode.getText().toString();
        strlandmrk = editlandmark.getText().toString();
        if (!Validatename()) {
            return;
        }

        if (!validateaddress()) {
            return;
        }


        if (stradd.equals(null) || stradd.isEmpty()) {
            Toast.makeText(Activity_address.this, "Please Enter Area", Toast.LENGTH_LONG).show();
            return;
        }
        if (strlandmrk.equals(null) || strlandmrk.isEmpty()) {
            Toast.makeText(Activity_address.this, "Please Enter Landmark", Toast.LENGTH_LONG).show();
            return;
        }
        if (strhouseno.equals(null) || strhouseno.isEmpty()) {
            Toast.makeText(Activity_address.this, "Please Enter House No", Toast.LENGTH_LONG).show();
            return;
        }
        if (strcity.equals(null) || strcity.isEmpty()) {
            Toast.makeText(Activity_address.this, "Please Enter City", Toast.LENGTH_LONG).show();
            return;
        }

        if (strstate.equals(null) || strstate.isEmpty()) {
            Toast.makeText(Activity_address.this, "Please Enter State", Toast.LENGTH_LONG).show();
            return;
        }

        if (!validatemoibile()) {
            return;
        }
        if (strpincode.equals(null) || strpincode.isEmpty() || strpincode.length() != 6) {
            Toast.makeText(Activity_address.this, "Please Enter Pincode", Toast.LENGTH_LONG).show();
            return;
        }

        ConnectionDetector cd = new ConnectionDetector(Activity_address.this);
        if (cd.isConnectingToInternet()) {

            //  new TopTrend().execute();
            new AddAddress().execute();
        } else {

            Toast.makeText(Activity_address.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


    }

    private void submit1() {

        if (!Validatename()) {
            return;
        }

        if (!validateaddress()) {
            return;
        }

        strname = editname.getText().toString();
        stradd = mAutocompleteTextView.getText().toString();
        strmob = editmob.getText().toString();
        strhouseno = edithouseno.getText().toString();
        strcity = editcity.getText().toString();
        strstate = editstate.getText().toString();
        strmob1 = editmob1.getText().toString();
        strpincode = editpincode.getText().toString();
        strlandmrk = editlandmark.getText().toString();
        //  capartmentid_id = spinapartment.getSelectedItem().toString();

        ConnectionDetector cd = new ConnectionDetector(Activity_address.this);
        if (cd.isConnectingToInternet()) {

            //  new TopTrend().execute();
            new AddAddressUpdate().execute();

        } else {

            Toast.makeText(Activity_address.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

    }


    private boolean Validatename() {

        if (editname.getText().toString().trim().isEmpty()) {

            Toast.makeText(Activity_address.this, "Enter name", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            editname.requestFocus();
            return false;

        } else {

        }

        return true;

    }

    private boolean validateaddress() {

        if (mAutocompleteTextView.getText().toString().trim().isEmpty()) {

            Toast.makeText(Activity_address.this, "Please Select Area", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            mAutocompleteTextView.requestFocus();
            return false;


        } else {

        }

        return true;

    }

    private boolean validatemoibile() {

        String phone = editmob1.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {

            Toast.makeText(Activity_address.this, "Enter Mobileno", Toast.LENGTH_LONG).show();
            editmob1.requestFocus();
            return false;

        } else {
        }

        return true;

    }


    @Override
    public void onClickedItem(int pos, int qty, int status) {
        Log.e("DMen", "Pos:" + pos + "Qty:" + qty);
        Log.e("testing", "status  = " + status);
        Log.e("testing", "title inm  = " + mListFeederInfo.get(pos).getId());

        getid = mListFeederInfo.get(pos).getId();
        getTextname = mListFeederInfo.get(pos).getTextname();
        getTextlandmark = mListFeederInfo.get(pos).getTextlandmark();
        getTextmobileno = mListFeederInfo.get(pos).getTextmobileno();
        getTexthouseno = mListFeederInfo.get(pos).getHouseno();
        getTextcity = mListFeederInfo.get(pos).getCity();
        getTextstate = mListFeederInfo.get(pos).getState();
        getTextmob1 = mListFeederInfo.get(pos).getAlternatemobileno();
        getimpnotice = mListFeederInfo.get(pos).getImpnotice();
        getTextpincode = mListFeederInfo.get(pos).getTextpincode();
        getTextaddress = mListFeederInfo.get(pos).getTextaddress();
        strlanti = mListFeederInfo.get(pos).getLatitude();
        strlong = mListFeederInfo.get(pos).getLogitude();
        getGetTextlandmark1 = mListFeederInfo.get(pos).getLandMark1();

        addressid = String.valueOf(qty);

        Log.e("testing", "testing=================quantity" + qty);
        Log.e("testing", "testing=================getid" + getid);
        // new Loader2().execute();


        logindialog123 = new Dialog(Activity_address.this);
        logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_address.this.LAYOUT_INFLATER_SERVICE);
        View convertView = (View) inflater.inflate(R.layout.edit_setting, null);
        logindialog123.setContentView(convertView);
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

                Log.e("testing", "edit=edit_setting_adress==" + addressid);


                logindialog12 = new Dialog(Activity_address.this);
                logindialog12.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) Activity_address.this.getSystemService(Activity_address.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.edit_address, null);
                logindialog12.setContentView(convertView);
                logindialog12.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog12.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog12.getWindow().setAttributes(lp);
                TextView xTvEditAddress=(TextView) convertView.findViewById(R.id.xTvEditAddress);
                xTvEditAddress.setText("Edit Address");
                ImageView xIvAddress=(ImageView)convertView.findViewById(R.id.xIvAddress);
                xIvAddress.setVisibility(View.GONE);
                editname = (EditText) convertView.findViewById(R.id.editname);
                editmob = (EditText) convertView.findViewById(R.id.editmob);
                edithouseno = (EditText) convertView.findViewById(R.id.edithouseno);
                editstate = (EditText) convertView.findViewById(R.id.editstate);
                editmob1 = (EditText) convertView.findViewById(R.id.editmob1);
                editpincode = (EditText) convertView.findViewById(R.id.editpincode);
                editcity = (EditText) convertView.findViewById(R.id.editcity);
                editlandmark = (EditText) convertView.findViewById(R.id.editlandmark);


              /*  mGoogleApiClient = new GoogleApiClient.Builder(Activity_address.this)
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage(Activity_address.this, GOOGLE_API_CLIENT_ID, Activity_address.this)
                        .addConnectionCallbacks(Activity_address.this)
                        .build();*/

                mAutocompleteTextView = (AutoCompleteTextView) convertView.findViewById(R.id
                        .locationsedt);
                mAutocompleteTextView.setText(getTextaddress);
                editname.setText(getTextname);
                editmob.setText(getTextmobileno);
                edithouseno.setText(getTexthouseno);
                editcity.setText(getTextcity);
                editstate.setText(getTextstate);
                editmob1.setText(getTextmob1);
                editpincode.setText(getTextpincode);

                editlandmark.setText(getTextlandmark);
                Button addsubmit = (Button) convertView.findViewById(R.id.addsubmit);

                addsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        submit1();

                    }
                });

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
            object.put(EndUrl.ADDdeliveryAddress_pincode, strpostal);

            object.put(EndUrl.ADDdeliveryAddress_add_landmark, strlandmrk);
            //if you want to modify some value just do like this.

            object.put(EndUrl.ADDdeliveryAddress_latitude, strlanti);
            object.put(EndUrl.ADDdeliveryAddress_longitude, strlong);


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



    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
//-----------------------------------------------------------------------------------------------------

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

        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        // mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(Activity_address.this, "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
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
            mProgress = new ProgressDialog(Activity_address.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);

        }

        public String doInBackground(String... args) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_user_id, userid));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_address_type, ""));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_name, strname));
            userpramas.add(new BasicNameValuePair(EndUrl.AddAddress_phone, strmob1));
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
                logindialog.dismiss();
                new LoadAddress().execute();
            } else if (status.equals("failure")) {
                Toast.makeText(Activity_address.this, strmessage, Toast.LENGTH_SHORT).show();
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
            mProgress = new ProgressDialog(Activity_address.this);
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
            Log.e("testing", "status = " + status);
            Log.e("testing", "strresponse = " + strresponse);
            Log.e("testing", "strmessage = " + strmessage);

            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {
                logindialog123.dismiss();
                new LoadAddress().execute();


            } else if (status.equals("failure")) {
                Toast.makeText(Activity_address.this, strmessage, Toast.LENGTH_SHORT).show();
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
            mProgress = new ProgressDialog(Activity_address.this);
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
                Toast.makeText(Activity_address.this, strmessage, Toast.LENGTH_SHORT).show();
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
            pDialog = new ProgressDialog(Activity_address.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public String doInBackground(String... args) {


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

                mAdapterFeeds = new Adapter_address(Activity_address.this, mListFeederInfo, mCallback);
                mFeedRecyler.setAdapter(mAdapterFeeds);

            } else {

                mAdapterFeeds = new Adapter_address(Activity_address.this, mListFeederInfo, mCallback);
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






