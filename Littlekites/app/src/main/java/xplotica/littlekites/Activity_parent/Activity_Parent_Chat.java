package xplotica.littlekites.Activity_parent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Adapter_parent.Parent_ChatAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.Parent_ChatEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_Chat extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;
    RecyclerView rView1;
    private ArrayList<Parent_ChatEntity> mListFeederInfo;
    Parent_ChatAdapter mAdapterFeeds;
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile,classTeacherId, classTeacherName;


    String strmessage;
    ImageButton enter_chat1;
    EditText chat_edit_text1;
    Context context;
    String Result;
    //ImageButton imagemaplocation;
    String[] Desc = new String[]{"Hi", "Hello", "How are you ?", "I am fine"};
    String[] Date = new String[]{"26-03-2017", "27-03-2017", "26-03-2017", "26-03-2017"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_chat);
        getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");
        classTeacherId=prefuserdata.getString("ClassTeacheId","");
        classTeacherName=prefuserdata.getString("ClassTeacherName","");



        Log.e("testing","type====="   +  type);
        Log.e("testing","studentid====="   +  Student_id);
        Log.e("testing","schoolname====="   +  School_name);
        Log.e("testing","School_id====="   +  School_id);
        Log.e("testing","Classid====="   +  Classid);
        Log.e("testing","Sectionid====="   +  Sectionid);
        Log.e("testing","classTeacherId====="   +  classTeacherId);
        Log.e("testing","classTeacherName====="   +  classTeacherName);

        context = this;


        rView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        rView1.setLayoutManager(new LinearLayoutManager(this));
        rView1.setHasFixedSize(true);
        //setUpRecycler();

        ImageView Back = (ImageView) findViewById(R.id.back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                onBackPressed();


            }
        });

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {

            new LoadSpinnerdata().execute();

        } else {

            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }

        chat_edit_text1 = (EditText) findViewById(R.id.chat_edit_text1);
        enter_chat1 = (ImageButton) findViewById(R.id.enter_chat1);

        enter_chat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strmessage = chat_edit_text1.getText().toString();
                if (strmessage == null || strmessage.equals("") || strmessage.equals(null)){

                    Toast.makeText(Activity_Parent_Chat.this, "Type a Message", Toast.LENGTH_SHORT).show();
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

       /* imagemaplocation = (ImageButton)findViewById(R.id.maplocation);

        imagemaplocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Activity_MapLoc.class);
                startActivity(intent);
            }
        });*/

    }
    private void setUpRecycler() {
        mListFeederInfo = new ArrayList<Parent_ChatEntity>();

        for (int i = 0; i < Desc.length; i++) {
            Parent_ChatEntity feedInfo = new Parent_ChatEntity();
            feedInfo.setMessage(Desc[i]);
            feedInfo.setDate(Date[i]);

            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds= new Parent_ChatAdapter(this,mListFeederInfo);
        rView1.setAdapter(mAdapterFeeds);
    }

    class LoadSpinnerdata extends AsyncTask<String, String, String> {
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
            mListFeederInfo = new ArrayList<Parent_ChatEntity>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "type value=" + type);
            Log.e("testing", "Student_id value=" + Student_id);
            Log.e("testing", "classTeacherId value=" + classTeacherId);
            Log.e("testing", "School_id value=" + School_id);
            Log.e("testing", "Classid value=" + Classid);
            Log.e("testing", "Sectionid value=" + Sectionid);
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_LOGINTYPE, type));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_SENDERID, Student_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_RECEIVERID, classTeacherId));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_SCHOOLID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_CLASSID, Classid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATFETCH_SECTIONID, Sectionid));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENT_CHATFETCH, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");
                try {
                    status = json.getString("status");

                    String arrayresponce = json.getString("StudentList");

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

                            Parent_ChatEntity item = new Parent_ChatEntity();
                            item.setSchoolid(post.optString("school_id"));
                            item.setSchoolname(post.optString("schoolName"));
                            item.setMessageid(post.optString("messageId"));
                            item.setClassid(post.optString("classId"));
                            item.setClassname(post.optString("className"));
                            item.setSectionid(post.optString("sectionId"));
                            item.setSectionname(post.optString("sectionName"));
                            item.setSenderid(post.optString("senderId"));
                            item.setReceiverid(post.optString("receiverId"));
                            item.setReceivername(post.optString("receiverName"));
                            item.setMessage(post.optString("Message"));
                            item.setLogintype(post.optString("loginType"));
                            item.setDate(post.optString("createdDate"));


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


                mAdapterFeeds= new Parent_ChatAdapter(context, mListFeederInfo);
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
            Log.e("testing", "School_id value=" + School_id);
            Log.e("testing", "Classid value=" + Classid);
            Log.e("testing", "Sectionid value=" + Sectionid);
            Log.e("testing", "Student_id value=" + Student_id);
            Log.e("testing", "classTeacherId value=" + classTeacherId);
            Log.e("testing", "strmessage value=" + strmessage);
            Log.e("testing", "type value=" + type);
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_SCHOOLID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_CLASSID, Classid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_SECTIONID, Sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_SENDERID, Student_id));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_RECEIVERID, classTeacherId));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_TEXTMESSAGE, strmessage));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEND_LOGINTYPE, type));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENT_CHATSEND, "POST", userpramas);


            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");


                try {
                    Result = json.getString("Status");






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

