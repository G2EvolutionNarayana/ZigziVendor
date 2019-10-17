package xplotica.littlekites.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter.GroupChatAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.GroupChatEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;


public class Activity_Groupchat extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    Dialog dialog1;
    EditText _USERNAME,_MOBILE;

    RecyclerView rView1;
    private ArrayList<GroupChatEntity> mListFeederInfo;
    GroupChatAdapter mAdapterFeeds;

    String[] Desc = new String[]{"Hi", "Hello", "How are you ?", "I am fine"};
    String[] Date = new String[]{"26-03-2017", "27-03-2017", "26-03-2017", "26-03-2017"};

    String name, Result, Message;
    String strloginid, strloginname, strlogintype;
    String strtgroupid, strhomename;
    Context context;

    String strusername, strmobileno;

    EditText chat_edit_text1;
    ImageButton enter_chat1;
    String strmessage;

    ImageView imgattachment, imgsettings;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Bitmap bitmap;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private ProgressDialog dialog;
    private String filepath = null;
    MultipartEntity entity;

    TextView _textviewcontact, _textviewmedia;

    String schoolid,type,loginid;
    String groupid,groupname;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {

        public void run() {
            final ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {

                new LoadSpinnerdata().execute();

            } else {

                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


            handler.postDelayed(this, 60000);

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        context = this;

        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        loginid = prefuserdata.getString("staffid", "");
        type = prefuserdata.getString("type", "");

        Log.e("testing","scoolid = "+schoolid);
        Log.e("testing","loginid = "+loginid);
        Log.e("testing","type = "+type);


        SharedPreferences prefuserdata2 = this.getSharedPreferences("group", 0);
        groupid = prefuserdata2.getString("groupid", "");
        groupname = prefuserdata2.getString("homename", "");

        Log.e("testing","groupid = "+groupid);
        Log.e("testing","groupname = "+groupname);



        enter_chat1 = (ImageButton) findViewById(R.id.enter_chat1);
        chat_edit_text1 = (EditText) findViewById(R.id.chat_edit_text1);

        rView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        rView1.setLayoutManager(new LinearLayoutManager(this));
        rView1.setHasFixedSize(true);
        handler.postDelayed(runnable, 60000);
        //setUpRecycler();
        imgattachment = (ImageView) findViewById(R.id.imgattachment);
        imgsettings = (ImageView) findViewById(R.id.imgsettings);
        // ------------------------------Action bar title and back button action----------------------------
        // TextView texttitle = (TextView) findViewById(R.id.textview_title1);
        // texttitle.setText(strhomename);
        ImageView Back = (ImageView) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);

                onBackPressed();


            }
        });


        final ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

            new LoadSpinnerdata().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }
        imgattachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });
        dialog1 = new Dialog(context);
        imgsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.setContentView(R.layout.dialog_list2);


                _textviewcontact = (TextView) dialog1.findViewById(R.id.textviewcontact);
                _textviewmedia = (TextView) dialog1.findViewById(R.id.textviewmedia);


                // set the custom dialog components - text, image and button
                //TextView text = (TextView) dialog.findViewById(R.id.text);
                //text.setText("Android custom dialog example!");
                //ImageView image = (ImageView) dialog.findViewById(R.id.image);
                //image.setImageResource(R.drawable.user);

                _textviewcontact.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, Activity_GroupInfo.class);
                        startActivity(intent);
                        dialog1.dismiss();
                    }
                });
                _textviewmedia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Activity_Group_Gallery.class);
                        startActivity(intent);
                        dialog1.dismiss();
                    }
                });








                dialog1.show();
            }
        });

        enter_chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmessage = chat_edit_text1.getText().toString();
                if (strmessage == null || strmessage.equals("") || strmessage.equals(null)){

                    Toast.makeText(Activity_Groupchat.this, "Type a Message", Toast.LENGTH_SHORT).show();
                }else{
                    ConnectionDetector cd = new ConnectionDetector(context);
                    if (cd.isConnectingToInternet()) {

                        new LoadSpinnerdata2().execute();

                    } else {

                        Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }

                }
            }
        });



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        }
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            selectImage();
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Groupchat.this);
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
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
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


        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImageUri = data.getData();
            filepath = getPath(selectedImageUri);


            ConnectionDetector cd = new ConnectionDetector(context);
            if (cd.isConnectingToInternet()) {


                new ImageUploadTask().execute();

                //wishcount
                //new LoadSpinnerdata().execute();


            } else {


                Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

            }


            Bitmap bitmap = BitmapFactory.decodeFile(filepath);
            //imageview.setVisibility(View.VISIBLE);
            //imageview.setImageBitmap(bitmap);

            Log.e("filepath", "filepath = " + filepath);

            // main.setText(filepath);


        }


        else if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);

        /*if(data!=null){
            onCaptureImageResult(data);
        }

*/


    }
    private void onCaptureImageResult(Intent data) {
        Bundle extras = data.getExtras();
        bitmap = (Bitmap) extras.get("data");
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

        filepath = destination.toString();
        Log.e("testing","destination" + destination);
        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {


            new ImageUploadTask().execute();

            //wishcount
            //new LoadSpinnerdata().execute();


        } else {


            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        //profileimage.setImageBitmap(bitmap);
        //uploadImage();
        //  Log.e("testing", "image path == " + profileimage);
    }
    @SuppressWarnings("deprecation")
    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = Activity_Groupchat.this.getContentResolver().query(uri, projection, null, null, null);
        //Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(runnable);
        super.onBackPressed();
    }

    class ImageUploadTask extends AsyncTask<String, Void, String> {

        String sResponse = null;
        //final String scode = Suppliercode.getText().toString();
        //final String area = Area.getText().toString();

        //final String pgram = pergram.getText().toString();
        //final String ptula = pertula.getText().toString();


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(Activity_Groupchat.this, "Uploading",
                    "Please wait...", true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //file2 = new File(listString1);

            //Log.e("DO file2 listString1", "map array th is = " + file2);




            try {

                //String url = "http://androidappfirst.com/b2b/app/postadvertisement.php";
                String url = End_Urls.ProfilephotoGroupUpload_Url;




                Bitmap xyz = decodeFile(filepath);

                //Bitmap xyz = decodeFile1(map1.get(i1));
                Log.e("xyz listString1", " main image = " + xyz);


                //Bitmap bitmap = decodeFile(map.get(i1));
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(url);

                //FileBody bin1 = new FileBody(file2);
                entity = new MultipartEntity();


                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                xyz.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                byte[] data = bos.toByteArray();


//**



                entity.addPart(End_Urls.ProfilephotoGroup_Schoolid, new StringBody(schoolid));
                entity.addPart(End_Urls.ProfilephotoGroupUpload_Senderid, new StringBody(loginid));
                entity.addPart(End_Urls.ProfilephotoGroupUpload_Groupid, new StringBody(groupid));
                entity.addPart(End_Urls.ProfilephotoGroup_GroupImage, new ByteArrayBody(data, "image/jpeg", filepath));

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost, localContext);
                sResponse = EntityUtils.getContentCharSet(response.getEntity());

                System.out.println("sResponse : " + sResponse);
            } catch (Exception e) {
                if (dialog.isShowing())
                    dialog.dismiss();
                Log.e(e.getClass().getName(), e.getMessage(), e);

            }
            return sResponse;
        }

        @Override
        protected void onPostExecute(String sResponse) {

            dialog.dismiss();
            Toast.makeText(Activity_Groupchat.this,
                    "Product Data with Images uploaded successfully",
                    Toast.LENGTH_SHORT).show();
            new LoadSpinnerdata().execute();
            /*try {
                if (dialog.isShowing())
                    dialog.dismiss();

                if (sResponse != null) {
                    Toast.makeText(getActivity(),
                            sResponse + " Photo uploaded successfully",
                            Toast.LENGTH_SHORT).show();
                    count++;
                    if (count < map.size()) {
                        new ImageUploadTask().execute(count + "", "hm" + count
                                + ".jpg");
                    }
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }*/

        }
    }
    public Bitmap decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;
        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);
        return bitmap;
    }





    private void setUpRecycler() {
        mListFeederInfo = new ArrayList<GroupChatEntity>();

        for (int i = 0; i < Desc.length; i++) {
            GroupChatEntity feedInfo = new GroupChatEntity();
            feedInfo.setHomegroupname(Desc[i]);
            feedInfo.setHomedate(Date[i]);

            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds= new GroupChatAdapter(this,mListFeederInfo);
        rView1.setAdapter(mAdapterFeeds);
    }

    class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String status;


        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            mListFeederInfo = new ArrayList<GroupChatEntity>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "strloginid value=" + groupid);
            userpramas.add(new BasicNameValuePair(End_Urls.ADGroupMessageFetch_Groupid, groupid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.ADGroupMessageFetch_Url, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");

                    String arrayresponce = json.getString("GroupName");

                    Log.e("testing", "adapter value=" + arrayresponce);

                    JSONArray responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);

                    if (arrayresponce == null) {
                        Log.e("testing", "jon11111111111111111");

                        //Toast.makeText(getContext(),"No data found",Toast.LENGTH_LONG).show();
                    } else {
                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();

                            GroupChatEntity item = new GroupChatEntity();
                            item.setHomemessageid(post.optString("groupmessageId"));
                            item.setHomeusertype(post.optString("login_user_type"));
                            item.setHomeloginid(post.optString("senderId"));
                            item.setHomegroupid(post.optString("receiver_groupId"));
                            item.setHomegroupmessage(post.optString("groupMessage"));
                            item.setHomegroupimage(post.optString("groupImage"));
                            item.setHomedate(post.optString("created_date"));
                            item.setHomegroupmessagestatus(post.optString("group_message_status"));
                            item.setHomegroupname(post.optString("groupName"));
                            item.setHomesendername(post.optString("senderName"));




                            // Log.e("testing", currentobj.getString("services"));

                            mListFeederInfo.add(item);
                        }
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
            Log.e("testing", "result is = " + responce);



            if (status.equals("Success")){


                mAdapterFeeds= new GroupChatAdapter(context, mListFeederInfo);
                rView1.setAdapter(mAdapterFeeds);





            }else{

                // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }

    class LoadSpinnerdata2 extends AsyncTask<String, String, String> {
        String responce;
        String message;
        String status;

        String strresult1, strresult2, strresult3;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();



        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "schoolid value=" + schoolid);
            Log.e("testing", "loginid value=" + loginid);
            Log.e("testing", "groupid value=" + groupid);
            Log.e("testing", "strmessage value=" + strmessage);
            userpramas.add(new BasicNameValuePair(End_Urls.ADGroupMessage_Schoolid, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.ADGroupMessage_Senderid, loginid));
            userpramas.add(new BasicNameValuePair(End_Urls.ADGroupMessage_Groupid, groupid));
            userpramas.add(new BasicNameValuePair(End_Urls.ADGroupMessage_Groupmessage, strmessage));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.ADGroupMessage_Url, "POST", userpramas);




            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");


                try {
                    Result = json.getString("status");






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
            Log.e("testing", "result is = " + responce);



            if (Result.equals("Success")){


             /*   Intent intent = new Intent(context, Activity_Singlechat.class);
                startActivity(intent);
                finish();
*/

                chat_edit_text1.setText("");
                new LoadSpinnerdata().execute();


            }else{

                Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }




}
