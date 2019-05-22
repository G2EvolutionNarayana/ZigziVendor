package g2evolution.GMT.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

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
import java.util.Locale;

import g2evolution.GMT.Adapter.Adapter_address_Navigation;
import g2evolution.GMT.Adapter.PlaceArrayAdapter;
import g2evolution.GMT.Adapter.spinnerAdapter;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.FeederInfo.FeederInfo_address;
import g2evolution.GMT.FeederInfo.WorldPopulation;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;
import g2evolution.GMT.Utility.JSONfunctions1;

public class Activity_Address_Navigation extends AppCompatActivity implements Adapter_address_Navigation.OnItemClick,GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks{

    TextView editaddress;

    ImageView back;

    private Adapter_address_Navigation.OnItemClick mCallback;

    String streditaddress;

    EditText editcross,editname,editmob,editimpnotice,updateaddress;

    //------------------searchable spinner----------------
    JSONObject jsonobject;
    JSONArray jsonarray;
    ArrayList<WorldPopulation> world;//spinapartment
    ArrayList<String> worldlist;//spinapartment
    Spinner spinapartment;
    String capartmentid_id;

    String strname,strcross,stradd,strmob, strpincode ,strimpnotice,stremailid;

    String status,message,userid,Apay;

    // TextView addcross,addname,address,addno;
    //  String _addcross,_addname,_address,_addno, _addpincode;


    String Username,Usermail,Usermob;

    ArrayList<HashMap<String, String>> arraylist1;
    private ArrayList<FeederInfo_address> allItems1 = new ArrayList<FeederInfo_address>();
    Context context;
    private RecyclerView mFeedRecyler;
    private ArrayList<FeederInfo_address> mListFeederInfo;

    Adapter_address_Navigation mAdapterFeeds;
    RecyclerView rView;

    String addressid;
    String []name =new String[]{"jana","janardhan","reddy"};
    String []saddress =new String[]{"marathalli","hsr","btm"};
    String []landmark =new String[]{"beside axis bank","beside  sfddsf","opposite watertank"};
    String []mobileno =new String[]{"8050044482","8886075422","7799944412"};
    String []pincode =new String[]{"560037","560035","560034"};

    double latitude, longitude;

    String latitudestr,edit_string_remove;

    TextView edit_setting_adress,edit_setting_remove;
    RelativeLayout lineardialog;
    ImageView imgdialogfiltercancel;

    String getid,getTextname,getTextlandmark,getTextmobileno,getGetTextlandmark1,getTextaddress,getimpnotice,getemailid;

    Dialog logindialog12,logindialog123;


    AutoCompleteTextView mAutocompleteTextView;


    private static final String LOG_TAG = "MainActivity";

    private static final int GOOGLE_API_CLIENT_ID = 0;
    private GoogleApiClient mGoogleApiClient;
    private PlaceArrayAdapter mPlaceArrayAdapter;
    private static final LatLngBounds BOUNDS_MOUNTAIN_VIEW = new LatLngBounds(
            new LatLng(37.398160, -122.180831), new LatLng(37.430610, -121.972090));

    String strname1, straddress, strcity,
            strpostal, strcountry, strstate, strlanti, strlong, strsublocality, stradminarea, strsubadminarea;

    View convertView,convertView1;
    EditText editlandmark;
    String strlandmrk;

    ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__address_navigation);

        logout=(ImageView)findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog logoutdialog = new Dialog(Activity_Address_Navigation.this);
                logoutdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
                View convertView = (View) inflater.inflate(R.layout.logout_dialog, null);
                //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

                logoutdialog.setContentView(convertView);
                LinearLayout logoutdialoglay = (LinearLayout) convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logoutdialog.setCanceledOnTouchOutside(false);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logoutdialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logoutdialog.getWindow().setAttributes(lp);

                TextView yesselect = (TextView) convertView.findViewById(R.id.yes);

                yesselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences sharedPreferences = getSharedPreferences("registerUser", 0);
                        SharedPreferences.Editor edit = sharedPreferences.edit();
                        edit.clear();
                        edit.commit();



                       /* SharedPreferences prefuserdata1=getSharedPreferences("Logintype",0);
                        SharedPreferences.Editor edit1 = prefuserdata1.edit();
                        edit1.clear();
                        edit1.commit();*/

                        Intent i = new Intent(Activity_Address_Navigation.this, Login.class);
                        startActivity(i);
                        finish();
                    }
                });

                TextView noselect = (TextView) convertView.findViewById(R.id.no);
                noselect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        logoutdialog.dismiss();

                    }
                });

                logoutdialog.show();

            }
        });

        editaddress = (TextView)findViewById(R.id.editaddress);
        //  selectadd = (TextView)findViewById(R.id.selectadd);
        back = (ImageView)findViewById(R.id.back);
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
        userid = prefuserdata.getString("UserId","");
        Username = prefuserdata.getString("Username", "");
        Usermail = prefuserdata.getString("Usermail", "");
        Usermob = prefuserdata.getString("Usermob", "");

        Log.e("testing","edit_string_remove==="+edit_string_remove);
        SharedPreferences prefuserdata1 = this.getSharedPreferences("pay", 0);
        Apay = prefuserdata1.getString("Apay", "");

        mCallback=this;
        mPlaceArrayAdapter = new PlaceArrayAdapter(Activity_Address_Navigation.this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mGoogleApiClient = new GoogleApiClient.Builder(Activity_Address_Navigation.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(Activity_Address_Navigation.this, GOOGLE_API_CLIENT_ID, Activity_Address_Navigation.this)
                .addConnectionCallbacks(Activity_Address_Navigation.this)
                .build();


        mFeedRecyler = (RecyclerView) findViewById(R.id.recycler_view);
        mFeedRecyler.setLayoutManager(new LinearLayoutManager(Activity_Address_Navigation.this));
        //setUpRecycler();
        // context = this;
        ///  lLayout = new GridLayoutManager(Activity_cart.this,2);
        rView = (RecyclerView) findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        //  rView.setLayoutManager(lLayout);
        //   mFeedRecyler.setLayoutManager(lLayout);
        mFeedRecyler.setHasFixedSize(true);


        // setUpRecycler();
        //-----------------Auto Complete Map Places-----------------------------//

   /*     mGoogleApiClient = new GoogleApiClient.Builder(Activity_Address_Navigation.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
*/
      /*  mAutocompleteTextView = (AutoCompleteTextView) findViewById(R.id
                .locationsedt);
        mAutocompleteTextView.setThreshold(2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);

*/

/*

        mAutocompleteTextView = (AutoCompleteTextView) convertView1.findViewById(R.id.locationsedt);
        mAutocompleteTextView.setThreshold(2);
        mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceArrayAdapter = new PlaceArrayAdapter(this, android.R.layout.simple_list_item_1,
                BOUNDS_MOUNTAIN_VIEW, null);
        mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);
*/


        ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
        if (cd.isConnectingToInternet()) {

            new Address().execute();

        } else {


            Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

      /*  selectadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (_addcross == null){

                    Toast.makeText(Activity_address.this, "Address not found,please add address", Toast.LENGTH_LONG).show();

                }
                else if (_addname == null) {
                }
                else if (_address == null) {
                }
                else if (_addno == null) {
                }
                else {

                    Intent i = new Intent(Activity_address.this,Activity_PaymentMode.class);
                    startActivity(i);

                }
            }
        });*/




        editaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog logindialog = new Dialog(Activity_Address_Navigation.this);
                logindialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater)Activity_Address_Navigation.this.getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
                 convertView = (View) inflater.inflate(R.layout.edit_address, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

                logindialog.setContentView(convertView);
                //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
                // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
                logindialog.setCanceledOnTouchOutside(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(logindialog.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;
                logindialog.getWindow().setAttributes(lp);




                mAutocompleteTextView = (AutoCompleteTextView)convertView. findViewById(R.id
                        .locationsedt);
                mAutocompleteTextView.setThreshold(2);
                mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);

                mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);


                editcross = (EditText)convertView.findViewById(R.id.editcross);
                editlandmark = (EditText)convertView.findViewById(R.id.editlandmark);
                updateaddress = (EditText)convertView.findViewById(R.id.editaddress);
                editimpnotice = (EditText)convertView.findViewById(R.id.impnotice);
                editname = (EditText)convertView.findViewById(R.id.editname);

                mAutocompleteTextView = (AutoCompleteTextView)convertView. findViewById(R.id
                        .locationsedt);
                editmob = (EditText)convertView.findViewById(R.id.editmob);

                spinapartment = (Spinner) convertView.findViewById(R.id.spinapartment);
                Button addsubmit = (Button)convertView.findViewById(R.id.addsubmit);

                ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
                if (cd.isConnectingToInternet()) {

                    new DownloadJSON().execute();

                } else {


                    Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

                addsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        submit();

                        Log.e("testing","strpincode===="+strpincode);
                        Log.e("testing","strpostal===="+strpostal);
                        Log.e("testing","capartmentid_id===="+capartmentid_id);


                    }
                });

                logindialog.show();

            }
        });

    }

    private void submit() {


        if (!Validatename()) {
            return;
        }
        if (updateaddress.getText().toString().trim() == null || updateaddress.getText().toString().trim().length() == 0){



        }else{

            if (!validateEmail()) {
                return;
            }
        }



        if (!validateaddress()) {
            return;
        }

        if (!validateaddress()) {
            return;
        }
        if (!validatelandmark()) {
            return;
        }
       /* if (!validatemoibile()) {
            return;
        }*/
        if (capartmentid_id == null || capartmentid_id.length() == 0 || spinapartment.getSelectedItem().toString().equals("Select Pincode")){
            Toast.makeText(Activity_Address_Navigation.this, "Please select Pincode", Toast.LENGTH_SHORT).show();

        }
        else {

            if (capartmentid_id.equals(strpostal)){

                streditaddress=updateaddress.getText().toString();
                strname = editname.getText().toString();
                strimpnotice = editimpnotice.getText().toString();
                stradd = mAutocompleteTextView.getText().toString();
                strcross = editcross.getText().toString();
                strmob = editmob.getText().toString();
                strlandmrk=editlandmark.getText().toString();
                capartmentid_id = spinapartment.getSelectedItem().toString();

                ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
                if (cd.isConnectingToInternet()) {

                    //  new TopTrend().execute();
                    new Loader().execute();
                } else {

                    Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

            }else {

                Toast.makeText(Activity_Address_Navigation.this, "Pincode and Address Mismatch", Toast.LENGTH_SHORT).show();

            }
        }


    }


    //-----------------------Validating email--------------------
    private boolean validateEmail() {

        String email = updateaddress.getText().toString().trim();
        if ( !isValidateEmail(email)) {
            Toast.makeText(Activity_Address_Navigation.this, "Your email  is invalid. Please enter a valid emailid", Toast.LENGTH_LONG).show();
            //edt_email.setError(getString(R.string.reg_validation_email));
            updateaddress.requestFocus();
            return false;

        } else {
            // inputLayoutemail.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidateEmail(String email) {

        return  Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void submit1() {

        if (!Validatename()) {
            return;
        }

        if (updateaddress.getText().toString().trim() == null || updateaddress.getText().toString().trim().length() == 0){


        }else{

            if (!validateEmail()) {
                return;
            }
        }
        if (!validateaddress()) {
            return;
        }
        if (!validatelandmark()) {
            return;
        }
      /*  if (!validatemoibile()) {
            return;
        }*/
        if (capartmentid_id == null || capartmentid_id.length() == 0 || spinapartment.getSelectedItem().toString().equals("Select Pincode")){


            Toast.makeText(Activity_Address_Navigation.this, "Please select Pincode", Toast.LENGTH_SHORT).show();

        }

        else {

            if (strpostal == null || strpostal.length() == 0 || strpostal.equals("null")) {


                Toast.makeText(Activity_Address_Navigation.this, "Address Incorrect", Toast.LENGTH_SHORT).show();


            } else {

                if (capartmentid_id.equals(strpostal)) {


                    streditaddress = updateaddress.getText().toString();
                    strname = editname.getText().toString();
                    strimpnotice = editimpnotice.getText().toString();
                    stradd = mAutocompleteTextView.getText().toString();
                    strcross = editcross.getText().toString();
                    strmob = editmob.getText().toString();
                    strlandmrk=editlandmark.getText().toString();
                    capartmentid_id = spinapartment.getSelectedItem().toString();

                    ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
                    if (cd.isConnectingToInternet()) {


                        //  new TopTrend().execute();
                        new UpdateAddress().execute();


                    } else {

                        Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }

                }else {


                    Toast.makeText(Activity_Address_Navigation.this, "Pincode and Address Mismatch", Toast.LENGTH_SHORT).show();

                }


            }
        }


    }

    private void setUpRecycler() {

        mListFeederInfo = new ArrayList<FeederInfo_address>();

        for (int i = 0; i < name .length; i++)
        {
            FeederInfo_address feedInfo = new FeederInfo_address();

            feedInfo.setTextname(name[i]);
            feedInfo.setTextaddress(saddress[i]);
            feedInfo.setTextlandmark(landmark[i]);
            feedInfo.setTextmobileno(mobileno[i]);
            feedInfo.setTextpincode(pincode[i]);

            mListFeederInfo.add(feedInfo);
        }

        mAdapterFeeds= new Adapter_address_Navigation(this , mListFeederInfo, mCallback);
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

    private boolean validatelandmark() {

        if (editcross.getText().toString().trim().isEmpty()) {

            Toast.makeText(Activity_Address_Navigation.this, "Enter Address Details", Toast.LENGTH_LONG).show();
            //edt_username.setError(getString(R.string.reg_validation_username));
            editcross.requestFocus();
            return false;

        } else {

            // inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatemoibile() {

        String phone = editmob.getText().toString();

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {

            Toast.makeText(Activity_Address_Navigation.this, "Enter Mobileno", Toast.LENGTH_LONG).show();
            //edt_mobile.setError(getText(R.string.reg_validation_phonenumber));
            editmob.requestFocus();
            return false;

        } else {
            // inputLayoutmobile.setErrorEnabled(false);
        }

        return true;

    }

  /*  private boolean validatepincode() {

        String phone = editpincode.getText().toString();
        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches() || phone.length() != 6) {

            Toast.makeText(Activity_address.this, "Enter Pincode", Toast.LENGTH_LONG).show();
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
        Log.e("DMen", "Pos:"+ pos + "Qty:"+qty);
        Log.e("testing","status  = "+status);
        Log.e("testing","title inm  = "+mListFeederInfo.get(pos).getId());

        getid=mListFeederInfo.get(pos).getId();
        getTextname=mListFeederInfo.get(pos).getTextname();
        getTextlandmark=mListFeederInfo.get(pos).getTextlandmark();
        getTextmobileno=mListFeederInfo.get(pos).getTextmobileno();
        getGetTextlandmark1=mListFeederInfo.get(pos).getLandMark1();
        getimpnotice=mListFeederInfo.get(pos).getImpnotice();
        strpostal=mListFeederInfo.get(pos).getTextpincode();
        getTextaddress=mListFeederInfo.get(pos).getTextaddress();
        getemailid=mListFeederInfo.get(pos).getEmailId();
        strlanti=mListFeederInfo.get(pos).getLatitude();
        strlong=mListFeederInfo.get(pos).getLogitude();

        addressid = String.valueOf(qty);

        Log.e("testing","testing=================quantity"+qty);
        Log.e("testing","testing=================getid"+getid);
        Log.e("testing","testing=================getTextpincode==========="+strpostal);
        Log.e("testing","testing=================getTextpincode==========="+getGetTextlandmark1);
        // new Loader2().execute();


        logindialog123 = new Dialog(Activity_Address_Navigation.this);
        logindialog123.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
        final View convertView = (View) inflater.inflate(R.layout.edit_setting, null);

                /* StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);*/

        logindialog123.setContentView(convertView);
        //LinearLayout logoutdialoglay=(LinearLayout)convertView.findViewById(R.id.logoutdialoglay);
        // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
        logindialog123.setCanceledOnTouchOutside(true);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(logindialog123.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        logindialog123.getWindow().setAttributes(lp);
        logindialog123.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        lineardialog = (RelativeLayout) convertView.findViewById(R.id.logout_dialog);

        imgdialogfiltercancel = (ImageView) convertView.findViewById(R.id.imgcancel);
        edit_setting_adress = (TextView)convertView.findViewById(R.id.edit_setting);
        edit_setting_remove = (TextView)convertView.findViewById(R.id.remove);

        imgdialogfiltercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(convertView, false, logindialog123);
            }
        });

        logindialog123.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(convertView, true, null);
            }
        });

        logindialog123.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK){

                    revealShow(convertView, false, logindialog123);
                    return true;
                }

                return false;
            }
        });



        edit_setting_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logindialog123.dismiss();
                Log.e("testing","edit=edit_setting_adress=="+addressid);


                logindialog12 = new Dialog(Activity_Address_Navigation.this);
                logindialog12.requestWindowFeature(Window.FEATURE_NO_TITLE);
                LayoutInflater inflater = (LayoutInflater)Activity_Address_Navigation.this.getSystemService(Activity_Address_Navigation.this.LAYOUT_INFLATER_SERVICE);
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

                editimpnotice = (EditText)convertView1.findViewById(R.id.impnotice);
                updateaddress = (EditText)convertView1.findViewById(R.id.editaddress);

                editlandmark = (EditText)convertView1.findViewById(R.id.editlandmark);
                editcross = (EditText)convertView1.findViewById(R.id.editcross);
                editname = (EditText)convertView1.findViewById(R.id.editname);

                editmob = (EditText)convertView1.findViewById(R.id.editmob);



              /*  mGoogleApiClient = new GoogleApiClient.Builder(Activity_Address_Navigation.this)
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage(Activity_Address_Navigation.this, GOOGLE_API_CLIENT_ID, Activity_Address_Navigation.this)
                        .addConnectionCallbacks(Activity_Address_Navigation.this)
                        .build();*/


                mAutocompleteTextView = (AutoCompleteTextView)convertView1. findViewById(R.id
                        .locationsedt);
                mAutocompleteTextView.setThreshold(2);
                mAutocompleteTextView.setOnItemClickListener(mAutocompleteClickListener);
             /*   mPlaceArrayAdapter = new PlaceArrayAdapter(Activity_Address_Navigation.this, android.R.layout.simple_list_item_1,
                        BOUNDS_MOUNTAIN_VIEW, null);*/
                mAutocompleteTextView.setAdapter(mPlaceArrayAdapter);


                mAutocompleteTextView.setText(getTextaddress);
                updateaddress.setText(getemailid);
                editname.setText(getTextname);
                editcross.setText(getTextlandmark);
                editmob.setText(getTextmobileno);
                editimpnotice.setText(getimpnotice);
                editlandmark.setText(getGetTextlandmark1);
                spinapartment = (Spinner) convertView1.findViewById(R.id.spinapartment);
                Button addsubmit = (Button)convertView1.findViewById(R.id.addsubmit);


                ConnectionDetector cd = new ConnectionDetector(Activity_Address_Navigation.this);
                if (cd.isConnectingToInternet()) {

                    new DownloadJSON().execute();

                } else {


                    Toast.makeText(Activity_Address_Navigation.this, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

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

                Log.e("testing","edit==edit_setting_remove="+addressid);

                new Loader2().execute();
            }
        });


        logindialog123.show();

    }


    class Loader extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject(EndUrl.ADDdeliveryAddress_URL, makingJson());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                    //  Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Activity_Address_Navigation.this, Activity_Address_Navigation.class);

                  /*  SharedPreferences prefuserdata = getSharedPreferences("regotp", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("otpid", "" + userid);
                    prefeditor.putString("mobile", "" + strmobileno);
                    //   prefeditor.putString("designationId", "" + designationId);

                    prefeditor.commit();*/
                    startActivity(intent);
                    finish();

                }else {
                    // Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.ADDdeliveryAddress_Id,userid);
            object.put(EndUrl.ADDdeliveryAddress_name,strname);
            object.put(EndUrl.ADDdeliveryAddress_address,stradd);
            object.put(EndUrl.ADDdeliveryAddress_landmark,strcross);
            object.put(EndUrl.ADDdeliveryAddress_mobile,strmob);
            object.put(EndUrl.ADDdeliveryAddress_pincode,capartmentid_id);
            object.put(EndUrl.ADDdeliveryAddress_imp_note,strimpnotice);
            object.put(EndUrl.ADDdeliveryAddress_streditaddress,streditaddress);

            object.put(EndUrl.ADDdeliveryAddress_add_landmark,strlandmrk);

            object.put(EndUrl.ADDdeliveryAddress_latitude,strlanti);
            object.put(EndUrl.ADDdeliveryAddress_longitude,strlong);

            //if you want to modify some value just do like this.

            details.put(EndUrl.ADDdeliveryAddressjsonobject_outside_otp,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());

            Log.e("testing","details===Address="+details);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

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


    class UpdateAddress extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            return postJsonObject5(EndUrl.ADDdeliveryAddress_Updte_URL, makingJson5());

        }

        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                    //   Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(Activity_Address_Navigation.this, Activity_Address_Navigation.class);

                  /*  SharedPreferences prefuserdata = getSharedPreferences("regotp", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();
                    prefeditor.putString("otpid", "" + userid);
                    prefeditor.putString("mobile", "" + strmobileno);
                    //   prefeditor.putString("designationId", "" + designationId);

                    prefeditor.commit();*/

                    startActivity(intent);
                    finish();
                    logindialog12.dismiss();


                }else {
                    //  Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();

            }
        }

    }

    public JSONObject makingJson5() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {


            object.put(EndUrl.ADDdeliveryAddress__latitude,strlanti);
            object.put(EndUrl.ADDdeliveryAddress__longitude,strlong);
            object.put(EndUrl.ADDdeliveryAddress_Updte_Id,userid);
            object.put(EndUrl.ADDdeliveryAddress_Updte_address_Id,addressid);
            object.put(EndUrl.ADDdeliveryAddress_Updte_name,strname);
            object.put(EndUrl.ADDdeliveryAddress_Updte_address,stradd);
            object.put(EndUrl.ADDdeliveryAddress_Updte_landmark,strcross);
            object.put(EndUrl.ADDdeliveryAddress_Updte_pincode,strpostal);
            object.put(EndUrl.ADDdeliveryAddress_Updte_mobile,strmob);
            object.put(EndUrl.ADDdeliveryAddress__imp_note,strimpnotice);
            object.put(EndUrl.ADDdeliveryAddress__streditaddress,streditaddress);

            object.put(EndUrl.ADDdeliveryAddress_addlandmark,strlandmrk);

            //if you want to modify some value just do like this.

            details.put(EndUrl.ADDdeliveryAddressjsonobject_Updte_outside_otp,object);
            Log.d("json",details.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return details;

    }



    public JSONObject postJsonObject5(String url, JSONObject loginJobj){
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
                //  userid  = post.getString("userid ");

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    //--------------------------------------------------------------------------------------------------------------

    class Address extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
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

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){


                    mAdapterFeeds= new Adapter_address_Navigation(Activity_Address_Navigation.this , mListFeederInfo, mCallback);
                    mFeedRecyler.setAdapter(mAdapterFeeds);
                 /*   addcross.setText(_addcross);
                    addname.setText(_addname);
                    address.setText(_address);
                    addno.setText(_addno);

*/
                    // Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();


                }else {
                    // Toast.makeText(Activity_address.this, ""+message, Toast.LENGTH_LONG).show();
                }

            }else {
                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson1() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();

        try {

            object.put(EndUrl.DeliveryAddress_userId,userid);



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

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
//-----------------------------------------------------------------------------------------------------





    class Loader2 extends AsyncTask<Void, Void, JSONObject> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (android.os.Build.VERSION.SDK_INT >= 11) {
                dialog = new ProgressDialog(Activity_Address_Navigation.this, ProgressDialog.THEME_HOLO_LIGHT);
            }else{
                dialog = new ProgressDialog(Activity_Address_Navigation.this);
            }
            dialog.setMessage(Html.fromHtml("<b>"+"Loading..."+"</b>"));
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

            if (result!=null) {
                dialog.dismiss();

                Log.e("testing","result in post execute========="+result);

                if (status.equals("success")){

                    //  Toast.makeText(Activity_address.this, message, Toast.LENGTH_LONG).show();
                    logindialog123.dismiss();
                    new Address().execute();

                   // logindialog12.dismiss();

                }else {
                    //  Toast.makeText(Activity_address.this, message, Toast.LENGTH_LONG).show();

                }

            }else {
                Toast.makeText(Activity_Address_Navigation.this, "Something went wrong", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        }

    }

    public JSONObject makingJson2() {

        JSONObject details = new JSONObject();
        //  JSONObject jobj = new JSONObject();
        JSONObject object = new JSONObject();



        try {

            object.put(EndUrl.AddressDelete_AddressId,addressid);

            Log.d("json",object.toString());
            Log.e("testing","json"+details.toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;

    }



    public JSONObject postJsonObject2(String url, JSONObject loginJobj){
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
            if(inputStream != null)

                result = convertInputStreamToString2(inputStream);
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

            // String arrayresponce = json.getString("data");
            //  Log.e("testing", "adapter value=" + arrayresponce);



        } catch (JSONException e) {
            e.printStackTrace();
        }
        // 11. return result

        return json;

    }

    private String convertInputStreamToString2(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }


    private class DownloadJSON extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            // Locate the WorldPopulation Class
            world = new ArrayList<WorldPopulation>();
            // Create an array to populate the spinner
            worldlist = new ArrayList<String>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // JSON file URL address
            jsonobject = JSONfunctions1
                    .getJSONfromURL(EndUrl.Pincode_URL, "GET",userpramas);


            Log.e("testing","apartments = "+jsonobject);

            try {
                // Locate the NodeList name
                jsonarray = jsonobject.getJSONArray("data");
                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonobject = jsonarray.getJSONObject(i);

                    WorldPopulation worldpop = new WorldPopulation();

                    worldpop.setAname(jsonobject.optString("pincode"));
                    worldpop.setAid(jsonobject.optString("pincodeId "));
				/*	worldpop.setPopulation(jsonobject.optString("population"));
					worldpop.setFlag(jsonobject.optString("flag"));*/
                    world.add(worldpop);

                    // Populate spinner with country names
                    worldlist.add(jsonobject.optString("pincode"));

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


            spinnerAdapter adapter = new spinnerAdapter(Activity_Address_Navigation.this, R.layout.spinner_item);
            adapter.addAll(worldlist);
            adapter.add("Select Pincode");
            spinapartment.setAdapter(adapter);
            spinapartment.setSelection(adapter.getCount());

            /*CurrentLocation
                    .setAdapter(new ArrayAdapter<String>(Signup_Tutor.this,
                            R.layout.spinner_item,
                            worldlist));*/

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

                                capartmentid_id = world.get(position).getAname();

                              //  capartmentid_id=strpincode;
                                Log.e("ugender", "ugender = " + capartmentid_id);
                                // new DownloadJSON4().execute();
                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                            // TODO Auto-generated method stub
                        }
                    });


        }
    }

    //-------------------------------Autotext------------------------------------------------------------------

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final PlaceArrayAdapter.PlaceAutocomplete item = mPlaceArrayAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(LOG_TAG, "Selected: " + item.description);
            Log.e("testing","seelcted description = "+item.description);
            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            Log.i(LOG_TAG, "Fetching details for ID: " + item.placeId);
        }
    };
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            Log.e("testing","places ========= "+places);
            if (!places.getStatus().isSuccess()) {
                Log.e(LOG_TAG, "Place query did not complete. Error: " +
                        places.getStatus().toString());
                return;
            }
            final Place place = places.get(0);


            LatLng latLng = place.getLatLng();

            strname1 = place.getName().toString();
            straddress = place.getAddress().toString();
            Log.e("testing","latitude"+latLng.latitude);
            Log.e("testing","longitude"+latLng.longitude);


            Geocoder geocoder = new Geocoder(Activity_Address_Navigation.this, Locale.getDefault());
            String result = null;
            try {
                List<android.location.Address> addressList = geocoder.getFromLocation(
                        latLng.latitude, latLng.longitude, 1);
                if (addressList != null && addressList.size() > 0) {
                    android.location.Address address = addressList.get(0);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                        sb.append(address.getAddressLine(i)).append("\n");
                    }
                    sb.append(address.getLocality()).append("\n");
                    sb.append(address.getPostalCode()).append("\n");
                    sb.append(address.getCountryName());
                    result = sb.toString();
                    strcity = address.getLocality();
                    strpostal = address.getPostalCode();
                    strcountry = address.getCountryName();
                    strstate = address.getAdminArea();
                    strsublocality = address.getSubLocality();
                    stradminarea = address.getAdminArea();
                    strsubadminarea = address.getSubAdminArea();
                    Double doulatitude = address.getLatitude();
                    Double doulongitude = address.getLongitude();

                    Log.e("testing","location doulatitude"+doulatitude);
                    Log.e("testing","location doulongitude"+doulongitude);
                    Log.e("testing","location strpostal"+strpostal);

                    strlanti = doulatitude.toString();
                    strlong = doulongitude.toString();


                    Log.e("testing1","strlanti======"+strlanti);
                    Log.e("testing1","strlong======"+strlong);


                /*    SharedPreferences prefuserdata = getActivity(). getSharedPreferences("Autocompletext", 0);
                    SharedPreferences.Editor prefeditor =prefuserdata.edit();
                    prefeditor.putString("Latitudevalue", "" + latitude);
                    prefeditor.putString("Longitudevalue", "" + longitude);
                    prefeditor.putString("postalcode", "" + strpostal);
                    prefeditor.putString("firstdate", "" + firstdate);
                    prefeditor.putString("seconddate", "" + seconddate);
                    prefeditor.commit();*/

                   /* Log.e("testing","strlanti = "+strlanti);
                    Log.e("testing","strlong = "+strlong);
                    Log.e("testing","strpostal = "+strpostal);
                    Log.e("testing","strcity = "+strcity);
                    Log.e("testing","strcountry = "+strcountry);
                    Log.e("testing","strstate = "+strstate);
                    Log.e("testing","strsublocality = "+strsublocality);
                    Log.e("testing","stradminarea = "+stradminarea);
                    Log.e("testing","strsubadminarea = "+strsubadminarea);
                    Log.e("testing","straddress = "+straddress);
                    Log.e("testing","strname = "+strname);*/


                }
            } catch (IOException e) {
                // Log.e(TAG, "Unable connect to Geocoder", e);
            }


           /*
            // Selecting the first object buffer.
            final Place place = places.get(2);
            CharSequence attributions = places.getAttributions();

            mNameTextView.setText(Html.fromHtml(place.getName() + ""));
            mAddressTextView.setText(Html.fromHtml(place.getAddress() + ""));
            mIdTextView.setText(Html.fromHtml(place.getId() + ""));
            mPhoneTextView.setText(Html.fromHtml(place.getPhoneNumber() + ""));
            mWebTextView.setText(place.getWebsiteUri() + "");
            if (attributions != null) {
                mAttTextView.setText(Html.fromHtml(attributions.toString()));
            }*/
        }
    };

    @Override
    public void onConnected(Bundle bundle) {
        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
        Log.i(LOG_TAG, "Google Places API connected.");

        Log.e("testing","LOG_TAG====api connected");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
                + connectionResult.getErrorCode());

        Toast.makeText(Activity_Address_Navigation.this, "Google Places API connection failed with error code:" +
                        connectionResult.getErrorCode(),
                Toast.LENGTH_LONG).show();

        Log.e("testing","LOG_TAG====api failed");
    }

    @Override
    public void onConnectionSuspended(int i) {
        mPlaceArrayAdapter.setGoogleApiClient(null);
        Log.e(LOG_TAG, "Google Places API connection suspended.");
        Log.e("testing","LOG_TAG====api suspend");
    }
  /*  @Override
    public void onPause() {
        super.onPause();
        mGoogleApiClient.stopAutoManage(Activity_Address_Navigation.this);
        mGoogleApiClient.disconnect();
    }*/
    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.stopAutoManage((Activity) context);
            mGoogleApiClient.disconnect();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.logout_dialog);

        int w = view.getWidth();
        int h = view.getHeight();

        int endRadius = (int) Math.hypot(w, h);

        int cx = (int) (imgdialogfiltercancel.getX() + (imgdialogfiltercancel.getWidth()/2));
        int cy = (int) (imgdialogfiltercancel.getY())+ imgdialogfiltercancel.getHeight() + 56;


        if(b){
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx,cy, 0, endRadius);

            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {

            Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);

                }
            });
            anim.setDuration(700);
            anim.start();
        }

    }
}
