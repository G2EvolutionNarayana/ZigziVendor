package xplotica.littlekites;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xplotica.littlekites.Activity.Activity_home;
import xplotica.littlekites.Activity_parent.Activity_Parent_Home;
import xplotica.littlekites.FeederInfo.Details_feederInfo;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 5/10/2017.
 */

public class Details_Adapter  extends RecyclerView.Adapter<Details_ViewHolder> {


    JSONParser jsonParser = new JSONParser();
    ArrayList<HashMap<String, String>> arraylist1;

    private ArrayList<Details_feederInfo> mListFeeds;
    private LayoutInflater mInflater;
    private int mPreviousPosition = 0;
    private Context mContext;

    String Pid,name,company,price,photo;
    String rating;
    String regId;

    String strloginid, strlogintype;


    String School_id,Student_id,School_name,ClassId,SectionId,mobile,logo,filepath;



    public Details_Adapter(Context context, ArrayList<Details_feederInfo> feedList){
        mContext = context;
        // mInflater = LayoutInflater.from(context);
        mListFeeds=feedList;

        SharedPreferences prefuserdata= mContext.getSharedPreferences("tokenid",0);
        regId=prefuserdata.getString("regId","");


    }

    public void setData( ArrayList<Details_feederInfo> feedList){
        mListFeeds=feedList;
        notifyDataSetChanged();
    }


    @Override
    public Details_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_details, null);
        Details_ViewHolder rcv = new Details_ViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(final Details_ViewHolder holder, int position) {
        final Details_feederInfo feederInfo = mListFeeds.get(position);


        String feedDesc = null;


        holder.student.setText(feederInfo.getLoginname());
        holder.school.setText(feederInfo.getSchoolname());
        holder.rollno.setText( "Register No: "+feederInfo.getIdentifyno());
        //holder.rate.setRating(Float.parseFloat(feederInfo.get_rating()));



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.e("testing","login name ========== "+feederInfo.getLoginname());
                if (feederInfo.getLogintype().equals("Teacher")){

                    SharedPreferences prefuserdata = mContext.getSharedPreferences("registerUser", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("schoolid", "" + feederInfo.getSchoolid());
                    prefeditor.putString("schoolname", "" + feederInfo.getSchoolname());
                    prefeditor.putString("staffid", "" + feederInfo.getLoginid());
                    prefeditor.putString("classid", "" + feederInfo.getClassid());
                    prefeditor.putString("sectionid", "" + feederInfo.getSectionid());
                    prefeditor.putString("type", "" + "1");
                    prefeditor.putString("classname", "" + ""+ feederInfo.getClassname());
                    prefeditor.putString("sectionname", "" + ""+ feederInfo.getSectionname());
                    prefeditor.putString("teachername", "" + ""+ feederInfo.getLoginname());
                    prefeditor.putString("mobileno", "" + ""+ feederInfo.getMobileno());


                    prefeditor.commit();

                    strloginid = feederInfo.getLoginid();
                    strlogintype = "1";

                    ConnectionDetector cd = new ConnectionDetector(mContext);
                    if (cd.isConnectingToInternet()) {


                        new TopTrend().execute();

                    } else {

                        Toast.makeText(mContext, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }





                }else if (feederInfo.getLogintype().equals("Student")){

                    Log.e("testing","login name ========== "+feederInfo.getLoginname());
                    SharedPreferences prefuserdata = mContext.getSharedPreferences("registerUser", 0);
                    SharedPreferences.Editor prefeditor = prefuserdata.edit();

                    prefeditor.putString("schoolid", "" + feederInfo.getSchoolid());
                    prefeditor.putString("studentid", "" + feederInfo.getLoginid());
                    prefeditor.putString("schoolname", "" + feederInfo.getSchoolname());
                    prefeditor.putString("classid", "" + feederInfo.getClassid());
                    prefeditor.putString("sectionid", "" + feederInfo.getSectionid());
                    prefeditor.putString("mobile", "" + feederInfo.getMobileno());
                    prefeditor.putString("logo", "" + feederInfo.getClass());
                    prefeditor.putString("filepath", "" + feederInfo.getClass());
                    prefeditor.putString("ClassTeacheId", "" + feederInfo.getClassteacherid());
                    prefeditor.putString("ClassTeacherName", "" + feederInfo.getClassteachername());
                    prefeditor.putString("type", "" + "2");

                    prefeditor.commit();


                    strloginid = feederInfo.getLoginid();
                    strlogintype = "2";

                    ConnectionDetector cd = new ConnectionDetector(mContext);
                    if (cd.isConnectingToInternet()) {


                        new TopTrend2().execute();

                    } else {

                        Toast.makeText(mContext, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                    }



                }else{

                }
            }
        });
        mPreviousPosition = position;

    }

    @Override
    public int getItemCount() {
        return mListFeeds.size();
    }



    public  class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;
        String response;

        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_LOGINTYPE, strlogintype));
            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_LOGINID, strloginid));
            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_FCMTOKEN, regId));


            Log.e("testing","strlogintype = "+strlogintype);
            Log.e("testing","strloginid = "+strloginid);
            Log.e("testing","regId = "+regId);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.FCM_DETAILS_URL, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                response = json.getString("response");




            } catch (JSONException e) {
                e.printStackTrace();
            }

            return responce;

        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            new CountDownTimer(700, 100) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    try {
                        pDialog.dismiss();
                        pDialog = null;
                    } catch (Exception e) {
                        //TODO: Fill in exception
                    }
                }
            }.start();





            if (status.equals("Success")){

                Intent intent = new Intent(mContext, Activity_home.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);

            }else {


                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();

            }

        }

    }


    public  class TopTrend2 extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String message;
        String status;
        String response;

        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist1 = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_LOGINTYPE, strlogintype));
            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_LOGINID, strloginid));
            userpramas.add(new BasicNameValuePair(End_Urls.FCM_DETAILS_FCMTOKEN, regId));


            Log.e("testing","strlogintype = "+strlogintype);
            Log.e("testing","strloginid = "+strloginid);
            Log.e("testing","regId = "+regId);

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.FCM_DETAILS_URL, "POST", userpramas);


            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                response = json.getString("response");




            } catch (JSONException e) {
                e.printStackTrace();
            }

            return responce;

        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            new CountDownTimer(700, 100) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    try {
                        pDialog.dismiss();
                        pDialog = null;
                    } catch (Exception e) {
                        //TODO: Fill in exception
                    }
                }
            }.start();





            if (status.equals("Success")){

                Intent intent = new Intent(mContext, Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);


            }else {


                Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();

            }

        }

    }

}

