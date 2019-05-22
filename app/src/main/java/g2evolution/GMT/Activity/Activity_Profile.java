package g2evolution.GMT.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;

import de.hdodenhof.circleimageview.CircleImageView;
import g2evolution.GMT.EndUrl;
import g2evolution.GMT.R;
import g2evolution.GMT.Utility.JSONParser;

import static android.graphics.BitmapFactory.decodeFile;

public class Activity_Profile extends AppCompatActivity {


    ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();


    ImageView imgdelivery, imgchangepassword;

    String userid,pid,shname,shemailid,shmobileno;

    TextView name_text,txt_email_id, txt_mobile_no;

    RelativeLayout relativeprofile;

    MultipartEntity entity;
    private ProgressDialog dialog;
    private String filepathimage = null;

    private Bitmap bitmap;

    CircleImageView user_pic;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    int REQUEST_CAMERA1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences prefuserdata1 =  getSharedPreferences("regId", 0);
        userid = prefuserdata1.getString("UserId", "");
        shname = prefuserdata1.getString("Username", "");
        shemailid = prefuserdata1.getString("Usermail", "");
        shmobileno = prefuserdata1.getString("Usermob", "");

        Log.e("testing","userid======"+userid);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView textview_title1 = (TextView) findViewById(R.id.textview_title1);
        textview_title1.setText("Profile");

        name_text = (TextView) findViewById(R.id.name_text);
        txt_email_id = (TextView) findViewById(R.id.txt_email_id);
        txt_mobile_no = (TextView) findViewById(R.id.txt_mobile_no);
        txt_email_id.setText(shemailid);
        txt_mobile_no.setText(shmobileno);

        String upperString = shname.substring(0,1).toUpperCase() + shname.substring(1);
        name_text.setText(upperString);

        relativeprofile = (RelativeLayout) findViewById(R.id.relativeprofile);

        user_pic=(CircleImageView)findViewById(R.id.userimage);

        imgdelivery = (ImageView) findViewById(R.id.imgdelivery);
        imgchangepassword = (ImageView) findViewById(R.id.imgchangepassword);

        imgdelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Activity_Profile.this, Activity_Address_Navigation.class);
                startActivity(intent);


            }
        });

        imgchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Profile.this, Activity_ChangePassword.class);
                startActivity(intent);
            }
        });

        relativeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
            }
        });

        new GetProfile().execute();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Log.e("testing","image2");
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectImage();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.e("testing","image1");
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            selectImage();
        }
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Profile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA1);
                } else if (items[item].equals("Choose from Library")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

        if (requestCode == 1 && resultCode == RESULT_OK && requestCode==SELECT_FILE) {
            Uri selectedImageUri = data.getData();
            filepathimage = getPath(selectedImageUri);

            Bitmap bitmap = decodeFile(filepathimage);
            //imageview.setVisibility(View.VISIBLE);
            //imageview.setImageBitmap(bitmap);

            Log.e("filepathimage", "filepathimage = " + filepathimage);

          //  txt_attach.setText(filepathimage);

            new UploadProfile().execute();

        }
        else if (requestCode == REQUEST_CAMERA1)
        {
            if(data ==null){
                //onCaptureImageResult(data);

                Toast.makeText(Activity_Profile.this,"please select photo",Toast.LENGTH_LONG).show();
            }
            else {
                onCaptureImageResult1(data);
            }

        }
        //   new UploadTaskImage().execute();


    /*    if (requestCode == REQUEST_CAMERA)
            onCaptureImageResult(data);*/


    }
    private void onCaptureImageResult1(Intent data) {
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

        filepathimage = destination.toString();
        Log.e("testing","destination" + destination);

        Log.e("filepathimage", "filepathimage = " + filepathimage);

       // txt_attach.setText(filepathimage);
        new UploadProfile().execute();

    }
    public String getPath(Uri uri) {

        String[] projection = { MediaStore.MediaColumns.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor =  managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else

            return null;

    }

    class GetUser_Data extends AsyncTask<String, String, String> {

        String responce;
        JSONArray responcearray;
        String status;
        String strresponse;
        String strmessage;
        String strdata;
        String struser_id, struserName, struserEmail, struserMobile, struserPassword, struserprofilePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();



            pDialog = new ProgressDialog(Activity_Profile.this);
            pDialog.setMessage("Loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.Get_UserData_user_id, userid.trim()));


            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.Get_UserData_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(Activity_MyAccount.this,"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");

                try {


                    status = json.getString("status");
                    strmessage = json.getString("message");
                    struser_id = json.getString("user_id");
                    struserName = json.getString("userName");
                    struserEmail = json.getString("userEmail");
                    struserMobile = json.getString("userMobile");
                    struserPassword = json.getString("userPassword");
                    struserprofilePath = json.getString("userprofilePath");




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);
            pDialog.dismiss();
            if (status == null || status.length() == 0){





            }else if (status.equals("success")) {
                pDialog.dismiss();


                if (struserprofilePath==null||struserprofilePath.length()==0||struserprofilePath.equals("")){


                }else {


                    Glide.with(Activity_Profile.this)
                            .load( Uri.parse(String.valueOf(struserprofilePath)))
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .error(R.drawable.logo)
                            //  .placeholder(R.drawable.spa)
                            //  .skipMemoryCache(true)
                            .into(user_pic);


                }



            } else if (status.equals("failure")) {
                pDialog.dismiss();
                Toast.makeText(Activity_Profile.this, strmessage, Toast.LENGTH_SHORT).show();

            }else{
                pDialog.dismiss();
            }

        }

    }



    class UploadProfile extends AsyncTask<String, Void, String> {

//        String strspecialist = spinspecialist.getSelectedItem().toString();
        //    String strquestion = Productname.getText().toString();

        String responseBody = "failure";
        String sResponse = null;
        String strResult, strresponse, action_type, strMessage;
        //final String scode = Suppliercode.getText().toString();
        //final String area = Area.getText().toString();
//        final String pname = Productname.getText().toString();
        //   final String desc = Desc.getText().toString();
        //final String pgram = pergram.getText().toString();
        //final String ptula = pertula.getText().toString();


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(Activity_Profile.this, "Uploading",
                    "Please wait...", true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //file2 = new File(listString1);

            //Log.e("DO file2 listString1", "map array th is = " + file2);




            try {

                // String url = "http://www.brprojects.co.in/second_doctor/customer_queries.php";
                String url = EndUrl.Update_UserProfile_URL;

                /*for (i = 0; i < map.size(); i++) {
                    bitmap[i] = decodeFile(map.get(i));

                    Log.e("decodeFile1", "decodeFile " + decodeFile(map.get(i)));

                    //bitmap[i] = decodeFile(map1.get(i));

                }
*/

                //  Bitmap xyz = decodeFile(filepath);

                //Bitmap xyz = decodeFile1(map1.get(i1));
                //Log.e("xyz listString1", " main image = " + xyz);


                //Bitmap bitmap = decodeFile(map.get(i1));
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(url);

                //FileBody bin1 = new FileBody(file2);
                entity = new MultipartEntity();


                // ByteArrayOutputStream bos = new ByteArrayOutputStream();
                // xyz.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                // byte[] data = bos.toByteArray();


               /* MultipartEntity mpEntity = new MultipartEntity();
                ContentBody cbFile = new FileBody(file);
                mpEntity.addPart("your_file", cbFile);*/


                entity.addPart(EndUrl.Update_UserProfile_Userid, new StringBody(userid));



                if (filepathimage == null || filepathimage.length() == 0){



                }else{

                    String imageformat = filepathimage.substring(filepathimage.lastIndexOf(".") + 1);
                    if (imageformat.equals("png")){

                        Bitmap xyz = decodeFile(filepathimage);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        xyz.compress(Bitmap.CompressFormat.PNG, 100, bos);
                        byte[] data = bos.toByteArray();
                        entity.addPart(EndUrl.Update_UserProfile_Profileimage, new ByteArrayBody(data, "image/jpeg", filepathimage));

                    }else if (imageformat.equals("jpg")){

                        Bitmap xyz = decodeFile(filepathimage);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        xyz.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                        byte[] data = bos.toByteArray();
                        entity.addPart(EndUrl.Update_UserProfile_Profileimage, new ByteArrayBody(data, "image/jpeg", filepathimage));


                    }else{

                        Bitmap xyz = decodeFile(filepathimage);
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        xyz.compress(Bitmap.CompressFormat.WEBP, 100, bos);
                        byte[] data = bos.toByteArray();
                        entity.addPart(EndUrl.Update_UserProfile_Profileimage, new ByteArrayBody(data, "image/jpeg", filepathimage));


                    }




                }

                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost, localContext);
                sResponse = EntityUtils.toString(response.getEntity());
                //responseBody = EntityUtils.toString(httpClient.execute(httpPost).getEntity(), "UTF-8");
                Log.e("testing","response============"+response);
                Log.e("testing","sResponse============"+sResponse);
                Log.e("testing", "responseBody============" + responseBody);

                JSONObject object = new JSONObject(sResponse);


                strResult = object.getString("status");
              //  strresponse = object.getString("response");
                //action_type = object.getString("action_type");
                strMessage = object.getString("message");


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
            if (strResult.equals("success")) {


                Toast.makeText(Activity_Profile.this, strMessage, Toast.LENGTH_SHORT).show();
                new GetProfile().execute();
            }else if (strResult.equals("failure")){
                Toast.makeText(Activity_Profile.this,
                        strMessage,
                        Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Activity_Profile.this,
                        strMessage,
                        Toast.LENGTH_SHORT).show();
            }


        }
    }

    class GetProfile extends AsyncTask<String, Void, String> {

//        String strspecialist = spinspecialist.getSelectedItem().toString();
        //    String strquestion = Productname.getText().toString();

        String responseBody = "failure";
        String sResponse = null;
        String strResult, strresponse, strmessage, strMessage;
        //final String scode = Suppliercode.getText().toString();
        //final String area = Area.getText().toString();
//        final String pname = Productname.getText().toString();
        //   final String desc = Desc.getText().toString();
        //final String pgram = pergram.getText().toString();
        //final String ptula = pertula.getText().toString();


        String struser_id, struserName, struserEmail, struserMobile, struserPassword, struserprofilePath;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dialog = ProgressDialog.show(Activity_Profile.this, "Fetching",
                    "Please wait...", true);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            //file2 = new File(listString1);

            //Log.e("DO file2 listString1", "map array th is = " + file2);




            try {

                // String url = "http://www.brprojects.co.in/second_doctor/customer_queries.php";
                String url = EndUrl.Get_UserData_URL;

                /*for (i = 0; i < map.size(); i++) {
                    bitmap[i] = decodeFile(map.get(i));

                    Log.e("decodeFile1", "decodeFile " + decodeFile(map.get(i)));

                    //bitmap[i] = decodeFile(map1.get(i));

                }
*/

                //  Bitmap xyz = decodeFile(filepath);

                //Bitmap xyz = decodeFile1(map1.get(i1));
                //Log.e("xyz listString1", " main image = " + xyz);


                //Bitmap bitmap = decodeFile(map.get(i1));
                HttpClient httpClient = new DefaultHttpClient();
                HttpContext localContext = new BasicHttpContext();
                HttpPost httpPost = new HttpPost(url);

                //FileBody bin1 = new FileBody(file2);
                entity = new MultipartEntity();


                // ByteArrayOutputStream bos = new ByteArrayOutputStream();
                // xyz.compress(Bitmap.CompressFormat.JPEG, 100, bos);

                // byte[] data = bos.toByteArray();


               /* MultipartEntity mpEntity = new MultipartEntity();
                ContentBody cbFile = new FileBody(file);
                mpEntity.addPart("your_file", cbFile);*/


                entity.addPart(EndUrl.Get_UserData_user_id, new StringBody(userid));




                httpPost.setEntity(entity);
                HttpResponse response = httpClient.execute(httpPost, localContext);
                sResponse = EntityUtils.toString(response.getEntity());
                //responseBody = EntityUtils.toString(httpClient.execute(httpPost).getEntity(), "UTF-8");
                Log.e("testing","response============"+response);
                Log.e("testing","sResponse============"+sResponse);
                Log.e("testing", "responseBody============" + responseBody);

                JSONObject object = new JSONObject(sResponse);


                strResult = object.getString("status");
                strmessage = object.getString("message");
                struser_id = object.getString("user_id");
                struserName = object.getString("userName");
                struserEmail = object.getString("userEmail");
                struserMobile = object.getString("userMobile");
                struserPassword = object.getString("userPassword");
                struserprofilePath = object.getString("userprofilePath");


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
            if (strResult.equals("success")) {



                if (struserprofilePath==null||struserprofilePath.length()==0||struserprofilePath.equals("")){


                }else {


                    Glide.with(Activity_Profile.this)
                            .load( Uri.parse(String.valueOf(struserprofilePath)))
                           // .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .centerCrop()
                            .error(R.drawable.logo)
                            //  .placeholder(R.drawable.spa)
                            //  .skipMemoryCache(true)
                            .into(user_pic);


                }



            }else if (strResult.equals("failure")){
                Toast.makeText(Activity_Profile.this,
                        strmessage,
                        Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(Activity_Profile.this,
                        strmessage,
                        Toast.LENGTH_SHORT).show();
            }


        }
    }
}
