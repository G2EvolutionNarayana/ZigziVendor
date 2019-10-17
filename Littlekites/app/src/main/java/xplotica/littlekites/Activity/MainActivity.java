package xplotica.littlekites.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import xplotica.littlekites.Activity_Login;
import xplotica.littlekites.Adapter.DrawerItemCustomAdapter;
import xplotica.littlekites.Fragment.fragment_aboutus;
import xplotica.littlekites.Fragment.fragment_contactus;
import xplotica.littlekites.Fragment.fragment_home;
import xplotica.littlekites.R;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{


    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.fragment_container)
    FrameLayout mViewContainer;
    @Bind(R.id.left_drawer)
    ListView mDrawerMenu;
    private MenuItem mMenuBellItem;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView textName,textProfile;
    CircleImageView profilephoto;
    Context context;
    private MenuItem menuItemcart;
    String Username,Id,Email,Mobile;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;
    String Imagepro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        setupToolBar();

        textName =(TextView)findViewById(R.id.profile_name);
        textProfile =(TextView)findViewById(R.id.profile);
       // profilephoto =(CircleImageView)findViewById(R.id.pofilephoto);
        // textName.setText("  " + Username);

     /*   Picasso.
                with(MainActivity.this).
                load("http://brprojects.co.in/fertilizers/fetch_profile_pic.php").
                into(profilephoto);
*/


        mToolbar.setTitle("Home");
        fragment_home fragment0 = new fragment_home();
        androidx.core.app.FragmentTransaction fragmentTransaction0 =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction0.replace(R.id.fragment_container, fragment0);
        fragmentTransaction0.commit();


        setupDrawer();
        context = this;


      /*  ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {


            //  new Fetchprofileimage().execute();


        } else {


            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }*/

      /*  mToolbar.setTitle("Home");
        fragment_home fragment2 = new fragment_home();
        android.support.v4.app.FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.fragment_container, fragment2);
        fragmentTransaction2.commit();*/

      /*  profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionDetector cd = new ConnectionDetector(context);
                if (cd.isConnectingToInternet()) {
                        //selectImage();

                } else {


                    Toast.makeText(getApplicationContext(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }
                //selectImage();

            }
        });
*/

    }


    private void setupToolBar() {
        //set the Toolbar as ActionBar
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        // List the Drawer Items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[8];



        drawerItem[0] = new ObjectDrawerItem(R.drawable.home4,"Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.home4,"Attendance");
        drawerItem[2] =new ObjectDrawerItem(R.drawable.home4,"Diary");
        drawerItem[3] =new ObjectDrawerItem(R.drawable.notification,"Home work");
        drawerItem[4] =new ObjectDrawerItem(R.drawable.notification,"Message");
        drawerItem[5] =new ObjectDrawerItem(R.drawable.home4,"Contact us");
        drawerItem[6] = new ObjectDrawerItem(R.drawable.home4,"About Us");
        drawerItem[7] = new ObjectDrawerItem(R.drawable.home4,"Logout");


        // Declare a new instance of our Custom drawer Adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(MainActivity.this, R.layout.listview_drawer_item_row, drawerItem);

        // Set the Adapter and the Listener on the ListView
        mDrawerMenu.setAdapter(adapter);
        mDrawerMenu.setOnItemClickListener(this);


    }




    /*   @Override
       public boolean onCreateOptionsMenu(Menu menu) {

           getMenuInflater().inflate(R.menu.cart, menu);

           menuItemcart = menu.findItem(R.id.cartitem);


           return true;

       }

       private void setNotifCount(int count) {

           invalidateOptionsMenu();
       }


       @Override
       public boolean onOptionsItemSelected(MenuItem item) {

           switch (item.getItemId()) {

               case R.id.cartitem:

                   Intent intent = new Intent(this, shopping_cart.class);
                   startActivity(intent);

                   return true;

               default:
                   mDrawerToggle.onOptionsItemSelected(item);
                   return super.onOptionsItemSelected(item);
           }
       }
   */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        switch (position) {
            case 0:
                mToolbar.setTitle("Home");
                Log.e("testing", "Case:0");
                fragment_home fragment0 = new fragment_home();
                androidx.core.app.FragmentTransaction fragmentTransaction0 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction0.replace(R.id.fragment_container, fragment0);
                fragmentTransaction0.commit();

                break;
            case 1:
                mToolbar.setTitle("Attendance");
                Log.e("testing", "Case:1");

                Intent intent = new Intent(MainActivity.this,Activity_attendance.class);
                startActivity(intent);

                break;
            case 2:
                mToolbar.setTitle("Diary");
                Log.e("testing", "Case:2");

                Intent intent1 = new Intent(MainActivity.this,Activity_Dairy.class);
                startActivity(intent1);


                break;
            case 3:
                mToolbar.setTitle("Home work");
                Log.e("testing", "Case:1");

                Intent intent2 = new Intent(MainActivity.this,Activity_Home_work.class);
                startActivity(intent2);

                /*fragment_notification fragment1 = new fragment_notification();
                android.support.v4.app.FragmentTransaction fragmentTransaction1 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.fragment_container, fragment1);
                fragmentTransaction1.commit();*/

                break;
            case 4:
                mToolbar.setTitle("Message");
                Log.e("testing", "Case:4");

                Intent intent3 = new Intent(MainActivity.this,Activity_message.class);
                startActivity(intent3);

               /* fragment_notification fragment4 = new fragment_notification();
                android.support.v4.app.FragmentTransaction fragmentTransaction4 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.fragment_container, fragment4);
                fragmentTransaction4.commit();*/

                break;

            case 5:
                mToolbar.setTitle("Contact us");
                Log.e("testing", "Case:5");
                fragment_contactus fragment5 = new fragment_contactus();
                androidx.core.app.FragmentTransaction fragmentTransaction5 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction5.replace(R.id.fragment_container, fragment5);
                fragmentTransaction5.commit();

                break;

            case 6:
                mToolbar.setTitle("About Us");
                Log.e("testing", "Case:6");
                fragment_aboutus fragment6 = new fragment_aboutus();
                androidx.core.app.FragmentTransaction fragmentTransaction6 =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction6.replace(R.id.fragment_container, fragment6);
                fragmentTransaction6.commit();

                break;


            case 7:
                Log.e("testing", "Case:6");
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Logout ");
                builder.setMessage("Are you Sure Want to Logout");
                builder.setNegativeButton("NO",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Toast.makeText(getApplicationContext(), "No is clicked", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setPositiveButton("YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                Intent intent = new Intent(MainActivity.this,Activity_Login.class);

                                SharedPreferences prefuserdata = getSharedPreferences("registerUser", 0);
                                SharedPreferences.Editor prefeditor = prefuserdata.edit();

                                prefeditor.putString("id", "");

                                prefeditor.clear();
                                prefeditor.commit();
                                //Toast.makeText(getApplicationContext(), "Yes is clicked", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                        });
                builder.show();

                break;


        }
    }

   /* private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image*//*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        bitmap = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

       *//* profilephoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        profilephoto.setOval(true);
*//*
        profilephoto.setImageBitmap(bitmap);
        uploadImage();
        Log.e("testing", "image path == " + profilephoto);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(selectedImagePath, options);

       *//* profilephoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
        profilephoto.setOval(true);
*//*
        profilephoto.setImageBitmap(bitmap);
        Log.e("testing", "bitmap path == " + bitmap);
        uploadImage();
        Log.e("testing", "image path == " + profilephoto);
    }


    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.e("testing", "encodedImage path == " + encodedImage);
        return encodedImage;
    }
    private void uploadImage(){
        class UploadImage extends AsyncTask<Bitmap,Void,String> {

            ProgressDialog loading;

            RequestHandler rh = new RequestHandler();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(MainActivity.this, "Uploading Image", "Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Bitmap... params) {
                Bitmap bitmap = params[0];
                String uploadImage = getStringImage(bitmap);
                //String uploadImage = bitmap.toString();

                HashMap<String,String> data = new HashMap<>();
                data.put(End_Urls.PROFILE_IMAGE, uploadImage);
                data.put(End_Urls.PROFILE_ID, Id);
                Log.e("testing", "uploadImage path == " + uploadImage);
                Log.e("testing", "id for image sending == " + Id);


                String result = rh.sendPostRequest(End_Urls.PROFILE_URL, data);
                Log.e("testing", "resut for image sending == " + result);



                return result;
            }
        }
        UploadImage ui = new UploadImage();
        ui.execute(bitmap);
        //Toast.makeText(context,"completed",Toast.LENGTH_LONG).show();
    }

    class Fetchprofileimage extends AsyncTask<String, String, String> {




        private ProgressDialog pDialog;

        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Processing");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }
        protected String doInBackground(String... params) {


            {

                List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



                userpramas.add(new BasicNameValuePair(End_Urls.PROFILEFETCH_ID,Id));


                JSONObject json = jsonParser.makeHttpRequest(End_Urls.PROFILEFETCH_URL, "POST", userpramas);

                Log.e("testing", "json result = " + json);

                Log.e("testing", "jon2222222222222");
                try {

                    Status = json.getString("status");
                    Status = json.getString("response");


                } catch (JSONException e) {
                    e.printStackTrace();
                }



                return null;
            }



        }


        *//**
     * After completing background task Dismiss the progress dialog
     * **//*
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pDialog.dismiss();


            Log.e("testing","Image in Dynamic="+Imagepro);



*//*

            // You can set image with resource id.

          *//**//*  iv1.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv1.setOval(true);*//**//*

            Log.e("testing","Image in Dynamic="+Imagepro);
            Glide.with(MainActivity.this)
                    .load(Uri.parse(Imagepro))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(profilephoto);

             Picasso.
              with(Profile.this).
              load("http://www.androidappfirst.com/booziee/boozie_app/product_images/14775725253504.png").
              into(imageview);




        *//*
        }
    }*/
}