package xplotica.littlekites.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import xplotica.littlekites.Adapter.MessageAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo.MessageEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_MessageChat extends AppCompatActivity {

    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist;

    Dialog dialog1;
    EditText _USERNAME,_MOBILE;

    RecyclerView rView1;
    private ArrayList<MessageEntity> mListFeederInfo;
    MessageAdapter mAdapterFeeds;
    String name, mobile, Result, Message;
    String strloginid, strloginname, strlogintype;
    ImageView back;
    String strusername, strmobileno;

    EditText editmobileno;
    ImageView imagesearch;
    String strseachmobileno;
    String schoolid,staffid,type,classid,sectionid;
    String[] Name = new String[]{"Jana", "Mithun", "Santanu", "Rohit", "Jana", "Mithun", "Santanu", "Rohit"};

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_chat);
        SharedPreferences prefuserdata = this.getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");
        context = this;

        rView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        rView1.setLayoutManager(new LinearLayoutManager(context));
        rView1.setHasFixedSize(true);
        // setUpRecycler();

        editmobileno = (EditText) findViewById(R.id.editmobileno);
        imagesearch = (ImageView) findViewById(R.id.imagemobileno);

        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
               /* Intent intent = new Intent(getApplicationContext(), Activity_home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });

        imagesearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strseachmobileno = editmobileno.getText().toString();

                ConnectionDetector cd = new ConnectionDetector(context);
                if (cd.isConnectingToInternet()) {






                    new LoadSpinnerdata().execute();


                } else {


                    Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }
                /*
                strseachmobileno = editmobileno.getText().toString();
                if (strseachmobileno == null || strseachmobileno.equals("") || strseachmobileno.equals("null")){

                    Toast.makeText(getActivity(), "Enter Mobile no", Toast.LENGTH_SHORT).show();

                }else{

                }*/

            }
        });

        ConnectionDetector cd = new ConnectionDetector(context);
        if (cd.isConnectingToInternet()) {






            new LoadSpinnerdata().execute();


        } else {


            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

        }


    }

    private void setUpRecycler() {
        mListFeederInfo = new ArrayList<MessageEntity>();

        for (int i = 0; i < Name.length; i++) {
            MessageEntity feedInfo = new MessageEntity();
            feedInfo.setFirstname(Name[i]);

            mListFeederInfo.add(feedInfo);
        }
        mAdapterFeeds= new MessageAdapter(context,mListFeederInfo);
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
            mListFeederInfo = new ArrayList<MessageEntity>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            Log.e("testing", "strloginid value=" + strloginid);
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEARCH_SCHOOLID, schoolid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEARCH_CLASSID, classid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEARCH_SECTIONID, sectionid));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEARCH_ROLLNO, strseachmobileno));
            userpramas.add(new BasicNameValuePair(End_Urls.PARENT_CHATSEARCH_LOGINTYPE, type));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.PARENT_CHATSEARCH, "POST", userpramas);


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


                            MessageEntity item = new MessageEntity();
                            item.setSchoolid(post.optString("school_id"));
                            item.setSchoolname(post.optString("schoolName"));
                            item.setStudentid(post.optString("student_id"));
                            item.setFirstname(post.optString("firstName"));
                            item.setRollno(post.optString("rollNumber"));
                            item.setClassid(post.optString("classId"));
                            item.setClassnem(post.optString("className"));
                            item.setSectionid(post.optString("sectionId"));
                            item.setSectionname(post.optString("sectionName"));




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


                mAdapterFeeds= new MessageAdapter(context,mListFeederInfo);
                rView1.setAdapter(mAdapterFeeds);





            }else{

                mAdapterFeeds= new MessageAdapter(context,mListFeederInfo);
                rView1.setAdapter(mAdapterFeeds);

                // Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();




            }



        }

    }
}
