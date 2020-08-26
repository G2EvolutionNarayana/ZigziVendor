package g2evolution.Boutique;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import g2evolution.Boutique.Activity.Activity_BookDelivery;
import g2evolution.Boutique.Activity.Activity_Profile;
import g2evolution.Boutique.Adap.Bottom_Navigation_Adapter;
import g2evolution.Boutique.Retrofit.Api;
import g2evolution.Boutique.Retrofit.ApiClient;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Bottom_Navigation_List;
import g2evolution.Boutique.frag.Contact_Us_Fragment;
import g2evolution.Boutique.frag.E_Commerce_Fragment;
import g2evolution.Boutique.frag.EmployHireFragmet;
import g2evolution.Boutique.frag.Home_Fragment;
import g2evolution.Boutique.frag.Profile_Fragment;
import g2evolution.Boutique.frag.Resource_Management_Fragment;
import g2evolution.Boutique.frag.View_Pager_Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        Home_Fragment.OnFragmentInteractionListener,
        Profile_Fragment.OnFragmentInteractionListener,
        Contact_Us_Fragment.OnFragmentInteractionListener,
        E_Commerce_Fragment.OnFragmentInteractionListener,
        Resource_Management_Fragment.OnFragmentInteractionListener,
        Bottom_Navigation_Adapter.OnItemlevelsinside {


    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private static final int INTENT_REQUEST_CODE = 100;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    LocationManager locationManager;
    String provider;
    String strjsonusername, strjsonemailid, strjsonmobileno, strjsonprofilePath;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    NavigationView navigationView;
    RecyclerView navigation_recycler;
    String userid, shname, shemailid, shmobileno;
    RelativeLayout relativelayout1;
    Boolean isPermissionGranted = false;
    boolean doubleBackToExitPressedOnce = false;
    Bitmap bitmap;
    Uri selectedImage;

    Bottom_Navigation_Adapter.OnItemlevelsinside mCallback2;
    Bottom_Navigation_Adapter bottom_navigation_adapter;
    JSONParser jsonParser = new JSONParser();
    String[] Name = new String[]{"Materials", "Machine Equipment", "Employees Hiring", "Cloth Materials"};
    String[] Id = new String[]{"1", "2", "3", "4"};
    LinearLayout linearbottom;
    ActionBar actionBar;
    BottomNavigationView bottomNavigation;
    String mediaPath;
    TextView textzigzi;
    ImageView imageViewprofile;
    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment fragment;

                    switch (item.getItemId()) {

                        case R.id.navigation_Home:
                            fragment = new View_Pager_Fragment();
                            openFragment(fragment);
                            return true;
                        case R.id.navigation_hire:
                            fragment = new EmployHireFragmet();
                            openFragment(fragment);
                            return true;
                        case R.id.navigation_eqip:

                            return true;
                        case R.id.navigation_material:
//                            fragment = new AccountFragment();
//                            openFragment(fragment);
                            return true;
                    }
                    return false;
                }
            };
    private String mImageUrl = "";
    private int GALLERY = 1, CAMERA = 2;
    private ArrayList<Bottom_Navigation_List> bottom_navigation_lists = new ArrayList<Bottom_Navigation_List>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mCallback2 = this;
        SharedPreferences prefuserdata1 = getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Hidesoftkeyboard();

        textzigzi = (TextView) findViewById(R.id.textzigzi);
        TextView drawer_title_name = (TextView) findViewById(R.id.drawer_title_name);
        TextView drawer_titlenumber = (TextView) findViewById(R.id.drawer_titlenumber);
        drawer_title_name.setText(shname);
        drawer_titlenumber.setText(shmobileno);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        bottomNavigation = (BottomNavigationView) findViewById(R.id.navigationBottom);
        setSupportActionBar(toolbar);
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Home</font>"));
        imageViewprofile = (ImageView) findViewById(R.id.imageViewprofile);

        //   toolbar.setNavigationIcon(R.drawable.menu);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setTitle("Boutique");
        }
        navigationView = (NavigationView) findViewById(R.id.navigation);
        linearbottom = (LinearLayout) findViewById(R.id.linearbottom);
        relativelayout1 = (RelativeLayout) findViewById(R.id.relativelayout1);
        navigation_recycler = (RecyclerView) findViewById(R.id.navigation_recycler);
        navigation_recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Home_Activity.this, LinearLayoutManager.HORIZONTAL, false);
        navigation_recycler.setLayoutManager(mLayoutManager);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, new View_Pager_Fragment()).commit();

        setUpReccyler2();
        checkLocationPermission();
        new LoadProfile().execute();
        relativelayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermmission()) {
                    showPictureDialog();

//                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(galleryIntent, 0);

                }


            }
        });

    }

    private boolean checkPermmission() {
        boolean isPermissionGranted = false;

        if (ContextCompat.checkSelfPermission(Home_Activity.this, Manifest.permission.CAMERA) + ContextCompat.checkSelfPermission(
                Home_Activity.this, Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat.checkSelfPermission(
                Home_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)

                != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(
                    Home_Activity.this,
                    new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CODE
            );
            isPermissionGranted = false;

        } else {
            isPermissionGranted = true;
            // Do something, when permissions are already granted
        }
        return isPermissionGranted;
    }

    private void showPictureDialog() {
        androidx.appcompat.app.AlertDialog.Builder pictureDialog = new androidx.appcompat.app.AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
//                            case 1:
//                                takePhotoFromCamera();
//                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);

                    selectedImage = data.getData();

                    mediaPath = getRealPathFromURI(data.getData());

                    uploadImage();
//                    uploadImage();8888

                } catch (IOException e) {
                    e.printStackTrace();
//                    Toast.makeText(MainActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");

            // Get the Image from data
            selectedImage = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "pic_"+ String.valueOf(System.currentTimeMillis()) + ".jpg"));

//            selectedImage = data.getData();
            mediaPath = getRealPathFromURI(selectedImage);

            uploadImage();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private void uploadImage() {


        final ProgressDialog pProgressDialog;
        pProgressDialog = new ProgressDialog(Home_Activity.this);
        pProgressDialog.setMessage("Please Wait ...");
        pProgressDialog.setIndeterminate(false);
        pProgressDialog.setCancelable(false);
        pProgressDialog.show();

        //call retrofit
        //making api call
        Api api = ApiClient.getClient().create(Api.class);
        File file1 = new File(mediaPath);

        RequestBody request_id_body = RequestBody.create(MediaType.parse("text/plain"), userid);

        //creating request body for file
        RequestBody affected_image_body = RequestBody.create(MediaType.parse(getContentResolver().getType(selectedImage)), file1);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", file1.getName(), affected_image_body);
        Call<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example> login = api.uploadImage(request_id_body, part);

        login.enqueue(new Callback<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example>() {
            @Override
            public void onResponse(Call<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example> call, Response<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example> response) {
                pProgressDialog.dismiss();
                if (response.body().getMessage() == null || response.body().getMessage().length() == 0) {

                } else if (response.body().getType().equals("success")) {
                    Log.e("testing", "success");

                    imageViewprofile.setImageBitmap(BitmapFactory.decodeFile(mediaPath));


                } else {
                    Log.e("testing", "error");
                    Toast.makeText(getApplicationContext(), response.body().getType(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<g2evolution.Boutique.Retrofit.ImageUploadDataModel.Example> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.e("testing", t.getLocalizedMessage());
                pProgressDialog.dismiss();

            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.home) {
            Intent intent = new Intent(Home_Activity.this, Home_Activity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.profile) {

            Intent intent = new Intent(Home_Activity.this, Activity_Profile.class);
            startActivity(intent);

        } else if (id == R.id.bookdelivery) {

            linearbottom.setVisibility(View.GONE);
          /*  E_Commerce_Fragment fragment2 = new E_Commerce_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();*/

            Intent intent = new Intent(Home_Activity.this, Activity_BookDelivery.class);
            startActivity(intent);
        } else if (id == R.id.ecommerce) {

            linearbottom.setVisibility(View.GONE);
          /*  E_Commerce_Fragment fragment2 = new E_Commerce_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();*/

            Intent intent = new Intent(Home_Activity.this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.lisense) {
            textzigzi.setText("Employee Hiring");
            linearbottom.setVisibility(View.GONE);
            EmployHireFragmet fragment2 = new EmployHireFragmet();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();
        } else if (id == R.id.contact_us) {
            linearbottom.setVisibility(View.GONE);
            Contact_Us_Fragment fragment2 = new Contact_Us_Fragment();
            FragmentTransaction fragmentTransaction2 =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction2.replace(R.id.fragment_container, fragment2);
            fragmentTransaction2.commit();
        } else if (id == R.id.policy) {
            linearbottom.setVisibility(View.GONE);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW);
            browserIntent.setData(Uri.parse("http://google.in/"));
            startActivity(browserIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Hidesoftkeyboard() {

        View focusedView = Home_Activity.this.getCurrentFocus();
        if (focusedView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) Home_Activity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private void setUpReccyler2() {
        bottom_navigation_lists = new ArrayList<Bottom_Navigation_List>();

        for (int i = 0; i < Name.length; i++) {
            Bottom_Navigation_List feedInfo = new Bottom_Navigation_List();
            feedInfo.setName(Name[i]);
            feedInfo.setId(Id[i]);

            bottom_navigation_lists.add(feedInfo);
        }
        bottom_navigation_adapter = new Bottom_Navigation_Adapter(Home_Activity.this, bottom_navigation_lists, mCallback2);
        navigation_recycler.setAdapter(bottom_navigation_adapter);
    }

    @Override
    public void OnItemlevelsinside(int pos, String qty, int status) {

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission nedded")
                        .setMessage("Location Permission nedded")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Home_Activity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        //  locationManager.requestLocationUpdates(provider, 400, 1, (android.location.LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if ((grantResults.length > 0) && (grantResults[0] + grantResults[1] + grantResults[2] == PackageManager.PERMISSION_GRANTED)) {
                    // Permissions are granted
                    Toast.makeText(Home_Activity.this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, 0);

                } else {
                    // Permissions are denied
//                    finish();
                }
                return;
            }


        }
    }

    class LoadProfile extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  mProgress = new ProgressDialog(Activity_Profile.this);
            mProgress.setMessage("Please wait");
            mProgress.show();
            mProgress.setCancelable(false);
*/
            pDialog = new ProgressDialog(Home_Activity.this);
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();


        }

        public String doInBackground(String... args) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetProfileDetails_user_id, userid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.GetProfileDetails_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "GET", userpramas);


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
                    if (status.equals("success")) {
                        status = json.getString("status");
                        strresponse = json.getString("response");
                        strdata = json.getString("data");


                        JSONObject jsonobjectdata = new JSONObject(strdata);
                        strjsonusername = jsonobjectdata.getString("name");
                        strjsonemailid = jsonobjectdata.getString("email");
                        strjsonmobileno = jsonobjectdata.getString("phone");
                        strjsonprofilePath = jsonobjectdata.getString("image");


                    } else {
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            pDialog.dismiss();
            Log.e("testing", "status = " + status);
            Log.e("testing", "strresponse = " + strresponse);
            Log.e("testing", "strmessage = " + strmessage);

            if (status == null || status.length() == 0) {

            } else if (status.equals("success")) {

                TextView drawer_title_name = (TextView) findViewById(R.id.drawer_title_name);
                TextView drawer_titlenumber = (TextView) findViewById(R.id.drawer_titlenumber);
                drawer_title_name.setText(strjsonusername);
                drawer_titlenumber.setText(strjsonmobileno);
                SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
                SharedPreferences.Editor prefeditor = prefuserdata.edit();
                prefeditor.putString("Username", "" + strjsonusername);
                prefeditor.putString("Usermail", "" + strjsonemailid);
                prefeditor.putString("Usermob", "" + strjsonmobileno);
                prefeditor.putString("image", "" + strjsonprofilePath);
                prefeditor.commit();


                if (strjsonprofilePath == null || strjsonprofilePath.length() == 0 || strjsonprofilePath.equals("")) {


                } else {


                    Glide.with(Home_Activity.this)
                            .load(Uri.parse(String.valueOf(strjsonprofilePath)))
                            .centerCrop()
                            .error(R.drawable.ic_user)
                            .into(imageViewprofile);


                }


            } else if (status.equals("failure")) {
                Toast.makeText(Home_Activity.this, strmessage, Toast.LENGTH_SHORT).show();
            } else {

            }


        }

    }


}
