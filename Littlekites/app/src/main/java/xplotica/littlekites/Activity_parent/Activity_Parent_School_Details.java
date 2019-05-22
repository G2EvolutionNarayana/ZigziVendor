package xplotica.littlekites.Activity_parent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_School_Details extends AppCompatActivity {


    ImageView Banner;
    TextView SchoolName,Contactno,textemailid,Address;
    ImageView back;

    JSONParser jsonParser = new JSONParser();

    String schoolname,contactno,schoolEmail,schoolAddress, address,id, strfilepath, imagestr;

    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;

    Context context;
    String Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__parent__school__details);
        context = this;

        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");


        Log.e("testing","sharepreference========="+mobile);
        Log.e("testing","sharepreference======="+School_id);
        Log.e("testing","sharepreference"+School_name);
        Log.e("testing","sharepreference"+Student_id);
        Log.e("testing","sharepreference"+Sectionid);
        Log.e("testing","sharepreference"+Classid);
        Log.e("testing","sharepreference"+mobile);


        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent intent = new Intent(getApplicationContext(),Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        Banner=(ImageView)findViewById(R.id.banner);
        SchoolName =(TextView)findViewById(R.id.textschoolname);
        Contactno=(TextView)findViewById(R.id.textcontactno);
        textemailid=(TextView)findViewById(R.id.textemailid);
        Address=(TextView)findViewById(R.id.textaddress);



        registerUser();

      /*  ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        if (cd.isConnectingToInternet()) {

            new TopTrend().execute();

           // registerUser();

        } else {

            Toast.makeText(getApplicationContext(), "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }*/
    }


    private class TopTrend extends AsyncTask<Void, Void, String> {

        String responce;
        Context context;

        @Override
        protected String doInBackground(Void... params) {

            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            ArrayList<String> images = new ArrayList<String>();

            Log.e("testing", "jsonParser startedkljhk");


            userpramas.add(new BasicNameValuePair(End_Urls.SCHOOL_MOBILENO,mobile ));
            userpramas.add(new BasicNameValuePair(End_Urls.SCHOOL_TYPE, type));
            JSONObject json = jsonParser.makeHttpRequest(End_Urls.SCHOOL_DETAILS, "POST", userpramas);

            Log.e("testing", "jsonParser" + json);

            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            } else {
                Log.e("testing", "jon2222222222222");
                // DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    JSONObject response = new JSONObject(json.toString());

                    Log.e("testing", "jsonParser2222" + json);

                    //JSONObject jsonArray1 = new JSONObject(json.toString());
                    // Result = response.getString("status");
                    JSONArray posts = response.optJSONArray("saloon_detalis");
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

                            // headers = post.getString("cat_name");
                            schoolname =post.getString("schoolName");
                            contactno = post.getString("mobile");
                         //   address = post.getString("address");
                            id=post.optString("school_id");


                            Log.e("testing","school name"+ schoolname);
                            Log.e("testing","id"+ id);
                            Log.e("testing","contact no" +contactno);
                            Log.e("testing","address" +address);
/*

                            JSONArray posts2 = post.optJSONArray("multiple_images");
                            for (int i1 = 0; i1 < posts2.length(); i1++) {
                                JSONObject post2 = posts2.optJSONObject(i1);

                                images.add(post2.getString("gallery_images"));

                                Log.e("san image array ","image"+post2.getString("gallery_images"));


                            }
*/

                        }

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }

        }

        @Override
        protected void onPostExecute(String args) {

            SchoolName.setText(schoolname);
            Contactno.setText(contactno);
           // Address.setText(address);


           /* Picasso.
                    with(Activity_Parent_School_Details.this).
                    load(bannerimage).
                    into(Bannerimage);
*/

        }


    }


    private void registerUser() {


        com.android.volley.toolbox.StringRequest stringRequest = new com.android.volley.toolbox.StringRequest(Request.Method.POST,End_Urls.SCHOOL_DETAILS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response = " + response);

                        try {

                            //JSONArray jsonArray = new JSONArray(response);

                            //for(int i=0;i<jsonArray.length();i++){
                            //JSONObject jresponse = 	jsonArray.getJSONObject(i);
                            JSONObject jsonArray1 = new JSONObject(response);


                            Result = jsonArray1.getString("status");
                          //  Message = jsonArray1.getString("response");

                            schoolname = jsonArray1.getString("schoolName");
                            contactno = jsonArray1.getString("schoolContactNumber");
                            schoolEmail = jsonArray1.getString("schoolEmail");
                            schoolAddress = jsonArray1.getString("schoolAddress");
                           // address = jsonArray1.getString("address");
                            id = jsonArray1.getString("school_id");
                            strfilepath = jsonArray1.getString("filepath");
                            imagestr = jsonArray1.getString("section_name");



                            Log.e("testing", "Result == " + Result);

                            //Log.e("testing","RegisterName == "+JsonName);
                            //Log.e("testing","RegisterId == "+JsonId);


                            //}
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //pDialog.dismiss();



                        if (Result.equals("success")) {

                            // Toast.makeText(Edit_Profile.this, Message, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message111==" + Result);

                            String strimage = strfilepath + "" + imagestr;

                        /*    if (imagestr == null || imagestr.equals("")||imagestr.equals(null)||imagestr.equals("null")){

                                Banner.setImageResource(R.drawable.banner);
                            }else{


                                Glide.with(context)
                                        .load(Uri.parse(strimage))
                                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                                        .skipMemoryCache(true)
                                        .into(Banner);
                            }
*/



                            SchoolName.setText(schoolname);
                            Contactno.setText(contactno);
                            textemailid.setText(schoolEmail);
                            Address.setText(schoolAddress);



                        } else {
                            Toast.makeText(Activity_Parent_School_Details.this, Result, Toast.LENGTH_LONG).show();
                            Log.e("testing", "Message222==" + Result);


                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Activity_Parent_School_Details.this, Result, Toast.LENGTH_LONG).show();
                        Log.e("testing", "error response == " +
                                "" + error);

                        //Intent intent = new Intent(getActivity(),MainActivity.class);
                        //intent.putExtra(KEY_PHONENO, phoneno);
                        //startActivity(intent);


                    }
                })

        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(End_Urls.SCHOOL_MOBILENO,mobile);
                params.put(End_Urls.SCHOOL_LOGINID, Student_id);
                params.put(End_Urls.SCHOOL_TYPE, "2");

                Log.e("testing","mobileno"+mobile);
                Log.e("testing","schooltype"+type);
                Log.e("testing", "params11==" + params);

                return params;

            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(Activity_Parent_School_Details.this);
        requestQueue.add(stringRequest);
    }
}
