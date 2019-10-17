package xplotica.littlekites.Activity_parent;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import xplotica.littlekites.Adapter_parent.HolidayAdapter;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.FeederInfo_parent.HolidayEntity;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

public class Activity_Parent_Holiday extends AppCompatActivity {

    Context context;
    Date greenstrDate;
    ImageView back;
    String strfinalmonth;
    String strfinaldate;
    private boolean undo = false;
    private CaldroidFragment caldroidFragment;
    private CaldroidFragment dialogCaldroidFragment;
    private TextView mTextView;
    private Date mCurrentSelectDate = null;
    SimpleDateFormat formatter;
    String arrayresponce;
    ArrayList<HashMap<String, String>> arraylist;
    JSONParser jsonParser = new JSONParser();
    JSONArray responcearray;
    String type,School_id,School_name,Student_id,Classid,Sectionid,mobile;
    RecyclerView rView1;
    private ArrayList<HolidayEntity> mListFeederInfo;
    HolidayAdapter mAdapterFeeds;
    SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    SimpleDateFormat format = new SimpleDateFormat(
            "EEE MMM dd HH:mm:ss 'GMT' yyyy", Locale.US);
    private void setCustomResourceForDates() {
        Calendar cal = Calendar.getInstance();
        Date todaysDate = cal.getTime();
        Log.e("testing","todaysDate"+todaysDate);
        mTextView.setText(formatter.format(todaysDate));
        Log.e("testing","todaysDate"+formatter.format(todaysDate));

        // Min date is last 7 days
        cal.add(Calendar.DATE, -7);
        Log.e("testing","caldate = "+cal);
        Date blueDate = cal.getTime();
        Log.e("testing","blueDate"+blueDate);

        // Max date is next 7 days
        cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 7);
        Date greenDate = cal.getTime();


        Log.e("testing","todaysDate"+greenDate);

        if (caldroidFragment != null) {

            Log.e("testing","bluedate = "+blueDate);
            Log.e("testing","greenDate = "+greenDate);

            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            ColorDrawable green = new ColorDrawable(Color.GREEN);
            caldroidFragment.setBackgroundDrawableForDate(blue, blueDate);
            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
            caldroidFragment.setTextColorForDate(R.color.white, blueDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_parent_holiday);
        SharedPreferences prefuserdata= getSharedPreferences("registerUser",0);
        type=prefuserdata.getString("type","2");
        School_id=prefuserdata.getString("schoolid","");
        School_name=prefuserdata.getString("schoolname","");
        Student_id=prefuserdata.getString("studentid","");
        Classid=prefuserdata.getString("classid","");
        Sectionid=prefuserdata.getString("sectionid","");
        mobile=prefuserdata.getString("mobile","");




        Log.e("testing","type====="   +  type);
        Log.e("testing","studentid====="   +  Student_id);
        Log.e("testing","schoolname====="   +  School_name);
        Log.e("testing","School_id====="   +  School_id);
        Log.e("testing","Classid====="   +  Classid);
        Log.e("testing","Sectionid====="   +  Sectionid);

        context = this;

        rView1 = (RecyclerView) findViewById(R.id.recycler_view1);
        rView1.setLayoutManager(new LinearLayoutManager(context));
        rView1.setHasFixedSize(true);

        mTextView = (TextView) findViewById(R.id.textview);
        formatter = new SimpleDateFormat("dd MMM yyyy");
        // Setup caldroid fragment
        // **** If you want normal CaldroidFragment, use below line ****
        caldroidFragment = new CaldroidFragment();



        // //////////////////////////////////////////////////////////////////////
        // **** This is to show customized fragment. If you want customized
        // version, uncomment below line ****
//		 caldroidFragment = new CaldroidSampleCustomFragment();

        // Setup arguments

        // If Activity is created after rotation
        if (savedInstanceState != null) {
            caldroidFragment.restoreStatesFromKey(savedInstanceState,
                    "CALDROID_SAVED_STATE");
        }
        // If activity is created from fresh
        else {
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            // Uncomment this to customize startDayOfWeek
            args.putInt(CaldroidFragment.START_DAY_OF_WEEK,
                    CaldroidFragment.SUNDAY); // Tuesday

            // Uncomment this line to use Caldroid in compact mode
            // args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);

            // Uncomment this line to use dark theme
//            args.putInt(CaldroidFragment.THEME_RESOURCE, com.caldroid.R.style.CaldroidDefaultDark);

            caldroidFragment.setArguments(args);
        }
        //new TopTrend().execute();
        // setCustomResourceForDates2();

        // Attach to the activity
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar1, caldroidFragment);
        t.commit();

        // Setup listener
        final CaldroidListener listener = new CaldroidListener() {

            @Override
            public void onSelectDate(Date date, View view) {
                //  L.t(getApplicationContext(), formatter.format(date));
               /* mTextView.setText(formatter.format(date));
                if(mCurrentSelectDate != null){
                    ColorDrawable white = new ColorDrawable(Color.WHITE);
                    caldroidFragment.setBackgroundDrawableForDate(white, mCurrentSelectDate);
                    caldroidFragment.setTextColorForDate(R.color.black, mCurrentSelectDate);
                }
                ColorDrawable red = new ColorDrawable(Color.RED);
                caldroidFragment.setBackgroundDrawableForDate(red, date);
                caldroidFragment.setTextColorForDate(R.color.white, date);
                mCurrentSelectDate = date;*/
            }

            @Override
            public void onChangeMonth(int month, int year) {
                String text = "month: " + month + " year: " + year;
                //String dtStart = Integer.toString(month);
                String strmonth = String.valueOf(month);
                String strfinalyear = String.valueOf(year);

                if (strmonth.equals("1")) {
                    strfinalmonth = "01";

                }else if (strmonth.equals("2")){
                    strfinalmonth = "02";
                }else if (strmonth.equals("3")){
                    strfinalmonth = "03";
                }else if (strmonth.equals("4")){
                    strfinalmonth = "04";
                }else if (strmonth.equals("5")){
                    strfinalmonth = "05";
                }else if (strmonth.equals("6")){
                    strfinalmonth = "06";
                }else if (strmonth.equals("7")){
                    strfinalmonth = "07";
                }else if (strmonth.equals("8")){
                    strfinalmonth = "08";
                }else if (strmonth.equals("9")){
                    strfinalmonth = "09";
                }else if (strmonth.equals("10")){
                    strfinalmonth = "10";
                }else if (strmonth.equals("11")){
                    strfinalmonth = "11";
                }else if (strmonth.equals("12")){
                    strfinalmonth = "12";
                }

                strfinaldate = strfinalyear + "-" + strfinalmonth;
                //Toast.makeText(Activity_Parent_Attendance_Calender.this, "strfinaldate = "+strfinaldate, Toast.LENGTH_SHORT).show();
                ConnectionDetector cd = new ConnectionDetector(context);
                if (cd.isConnectingToInternet()) {

                    rView1.setVisibility(View.GONE);
                    new TopTrend().execute();

                } else {

                    Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();

                }

                FragmentTransaction t = getSupportFragmentManager().beginTransaction();
                t.replace(R.id.calendar1, caldroidFragment);
                t.commit();
                // Toast.makeText(Activity_Parent_Attendance_Calender.this, "date"+strfinalmonth+ "Year"+strfinalyear, Toast.LENGTH_SHORT).show();
                // Toast.makeText(Activity_Parent_Attendance_Calender.this, ""+text, Toast.LENGTH_SHORT).show();
                //    L.t(getApplicationContext(), text);
            }

            @Override
            public void onLongClickDate(Date date, View view) {
                //   L.t(getApplicationContext(),
                //         "Long click " + formatter.format(date));
            }

            @Override
            public void onCaldroidViewCreated() {
                if (caldroidFragment.getLeftArrowButton() != null) {
                    // L.t(getApplicationContext(),
                    //       "Caldroid view is created");
                }
            }

        };

        // Setup Caldroid
        caldroidFragment.setCaldroidListener(listener);


        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              /*  Intent intent = new Intent(getApplicationContext(),Activity_Parent_Home.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);*/
            }
        });
    }

    /**
     * Save current states of the Caldroid here
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);

        if (caldroidFragment != null) {
            caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

   /* class LoadSpinnerdata extends AsyncTask<String, String, String> {
        String responce;

        String status;
        String Message;

        String strresult1, strresult2, strresult3;
        String img;
        String textname, textRent, textDeposit;
        // String glbarrstr_package_cost[];
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_Parent_Attendance_Calender.this);
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();

        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();
            // Log.e("testing", "user_id value=" + id);
            //  userpramas.add(new BasicNameValuePair(End_Urls.CARTDETAILS_Userid, id));

            userpramas.add(new BasicNameValuePair("schoolName", "5"));
            userpramas.add(new BasicNameValuePair("className", "2"));
            userpramas.add(new BasicNameValuePair("sectionName", "4"));
            userpramas.add(new BasicNameValuePair("loginType", "2"));
            userpramas.add(new BasicNameValuePair("loginId", "1"));
            JSONObject json = jsonParser.makeHttpRequest("http://androidappfirst.com/school/School_android/getAbsentList", "POST", userpramas);



            Log.e("testing", "json result = " + json);
            if (json == null) {

                Log.e("testing", "jon11111111111111111");
                // Toast.makeText(getActivity(),"Data is not Found",Toast.LENGTH_LONG);

                return responce;
            }else {
                Log.e("testing", "jon2222222222222");

                try {
                    status = json.getString("status");
                    String arrayresponce = json.getString("absentList");


                    Log.e("testing", "adapter value=" + arrayresponce);

                    responcearray = new JSONArray(arrayresponce);
                    Log.e("testing", "responcearray value=" + responcearray);





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


                for (int i = 0; i < responcearray.length(); i++) {


                    try {
                        JSONObject post = responcearray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();


                        parent_HomeWork_feederInfo item = new parent_HomeWork_feederInfo();


                        //  item.setDairyid(post.optString("diary_id"));
                        item.set_topicName(post.optString("studentAbsentDate"));
                        Date greenDate;
                        String dtStart = post.optString("studentAbsentDate");

                        *//*try {
                            greenDate = format.parse(dtStart);
                            System.out.println(greenDate);
                            Log.e("testing","date in green color"+greenDate);
                            ColorDrawable green = new ColorDrawable(Color.GREEN);
                            caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
                            caldroidFragment.setTextColorForDate(R.color.white, greenDate);
                        }  catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }*//*

                        greenDate = format.parse(dtStart);
                        System.out.println(greenDate);
                        Log.e("testing","date in green color"+greenDate);
                        ColorDrawable green = new ColorDrawable(Color.GREEN);
                        caldroidFragment.setBackgroundDrawableForDate(green, greenDate);
                        caldroidFragment.setTextColorForDate(R.color.white, greenDate);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }


                }

            }else{
                Toast.makeText(Activity_Parent_Attendance_Calender.this, Message, Toast.LENGTH_LONG).show();


            }



        }

    }*/


    class TopTrend extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {
        String responce;
        String status;
        // String glbarrstr_package_cost[];
        //  private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          /*  pDialog = new ProgressDialog(Activity_Parent_Attendance_Calender.this);
            pDialog.setMessage("Loading Services");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/

        }

        public String doInBackground(String... args) {
            // Create an array
            arraylist = new ArrayList<HashMap<String, String>>();
            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();

            mListFeederInfo = new ArrayList<HolidayEntity>();
            //userpramas.add(new BasicNameValuePair("customerId", RId));

            Log.e("testing","School_id "+School_id);
            Log.e("testing","Classid "+Classid);
            Log.e("testing","Sectionid "+Sectionid);
            Log.e("testing","type "+type);
            Log.e("testing","Student_id "+Student_id);
            Log.e("testing","strfinaldate "+strfinaldate);
            userpramas.add(new BasicNameValuePair(End_Urls.HOLIDAY_DETAILS_SCHOOLID, School_id));
            userpramas.add(new BasicNameValuePair(End_Urls.HOLIDAY_DETAILS_HOLIDAYMONTH, strfinaldate));

            JSONObject json = jsonParser.makeHttpRequest(End_Urls.HOLIDAY_DETAILS, "POST", userpramas);



            Log.e("testing", "json result = " + json);

            Log.e("testing", "jon2222222222222");
            try {
                status = json.getString("status");
                String arrayresponce = json.getString("HolidayList");
                Log.e("testing", "adapter value=" + arrayresponce);

                responcearray = new JSONArray(arrayresponce);


                Log.e("testing", "responcearray value=" + responcearray);



            } catch (JSONException e) {
                e.printStackTrace();
            }

            return responce;

        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);


if (responcearray == null){

}else{
    for (int i = 0; i < responcearray.length(); i++) {

        JSONObject post = null;
        try {
            post = responcearray.getJSONObject(i);

            HashMap<String, String> map = new HashMap<String, String>();

            // String dtStart = "2017-04-24";
            String dtStart = post.optString("holidayDate");

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Log.e("testind","dtstart"+dtStart);
            greenstrDate = null;
            try {
                greenstrDate = format.parse(dtStart);
                Log.e("testing","date in format 111 = "+greenstrDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            caldroidFragment.refreshView();
            if (caldroidFragment != null) {


                ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.orange));
                caldroidFragment.setBackgroundDrawableForDate(blue, greenstrDate);
                caldroidFragment.setTextColorForDate(R.color.white, greenstrDate);
            }


            HolidayEntity item = new HolidayEntity();
            item.setSchoolid(post.optString("school_id"));
            item.setSchoolname(post.optString("schoolName"));
            item.setHolidayid(post.optString("holidayId"));
            item.setHolidaytitle(post.optString("holidayTitle"));
            item.setHolidaydesc(post.optString("holydayDescription"));
            item.setHolidaydate(post.optString("holidayDate"));




            // Log.e("testing", currentobj.getString("services"));

            mListFeederInfo.add(item);


                  /*  String dtStart = "Sun Apr 23 17:19:54 GMT+05:30 2017";
                    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
                    Log.e("testind","dtstart"+dtStart);
                    greenstrDate = null;
                    try {
                        greenstrDate = format.parse(dtStart);
                        Log.e("testing","date in format 111 = "+greenstrDate);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }


                    if (caldroidFragment != null) {


                        ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
                        caldroidFragment.setBackgroundDrawableForDate(blue, greenstrDate);
                        caldroidFragment.setTextColorForDate(R.color.white, greenstrDate);
                    }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}


            if (status.equals("Success")){
                rView1.setVisibility(View.VISIBLE);
                mAdapterFeeds= new HolidayAdapter(context,mListFeederInfo);
                rView1.setAdapter(mAdapterFeeds);
            }else{

                rView1.setVisibility(View.GONE);
            }




            // pDialog.dismiss();
           /* new CountDownTimer(700, 100) {
                public void onTick(long millisUntilFinished) {
                }
                public void onFinish() {
                    try {
                       // pDialog.dismiss();
                       // pDialog = null;
                    } catch (Exception e) {
                        //TODO: Fill in exception
                    }
                }
            }.start();
*/

            Log.e("testing", "result is = " + responce);

            //ProductitemsAdapter productitemadapter;

            /*productitemadapter = new ProductitemsAdapter(getActivity(), allItems1);
            rView.setAdapter(productitemadapter);*/

        }
    }

    private void setCustomResourceForDates2() {

        String dtStart = "Sun Apr 23 17:19:54 GMT+05:30 2017";
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        Log.e("testind","dtstart"+dtStart);
        greenstrDate = null;
        try {
            greenstrDate = format.parse(dtStart);
            Log.e("testing","date in format 111 = "+greenstrDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }


        if (caldroidFragment != null) {


            ColorDrawable blue = new ColorDrawable(getResources().getColor(R.color.blue));
            caldroidFragment.setBackgroundDrawableForDate(blue, greenstrDate);
            caldroidFragment.setTextColorForDate(R.color.white, greenstrDate);
        }
    }


}
