package g2evolution.Boutique.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import g2evolution.Boutique.Adapter.PersonAdapter;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.FeederInfo.Person;
import g2evolution.Boutique.Home_Activity;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;

public class Activity_BookingDelivery2 extends AppCompatActivity {

    TextView textpickdatetime;
    private int mYear, mMonth, mDay, mHour, mMinute;


    JSONParser jsonParser = new JSONParser();
    private PersonAdapter adapter;
    private List<Person> feedItemList = new ArrayList<Person>();
    private RecyclerView mRecyclerView;

    Dialog dialogfilter;
    LinearLayout lineardialog;
    Button submit_filter;
    ImageView imgdialogfiltercancel;

    String strdate, strtime, strdatetime;
    String strdistance, strprice;
    String strpickdate;
    String strpickdatetime;
    String shippingid;

    String strfinalpickdatetime;

    String UserId;
    String strpicklatitude, strpicklongitude, strpickaddress, strdroplatitude, strdroplongitude, strdropaddress;

    TextView textcontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_delivery2);

        SharedPreferences prefuserdata = getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");



        SharedPreferences prefuserdata2 = getSharedPreferences("booklocation", 0);
        strpicklatitude = prefuserdata2.getString("strpicklatitude", "");
        strpicklongitude = prefuserdata2.getString("strpicklongitude", "");
        strpickaddress = prefuserdata2.getString("strpickaddress", "");
        strdroplatitude = prefuserdata2.getString("strdroplatitude", "");
        strdroplongitude = prefuserdata2.getString("strdroplongitude", "");
        strdropaddress = prefuserdata2.getString("strdropaddress", "");



        SharedPreferences prefuserdata3 = getSharedPreferences("productadapter", 0);
        SharedPreferences.Editor prefeditor3 = prefuserdata3.edit();

        prefeditor3.putString("id", "");

        prefeditor3.clear();
        prefeditor3.commit();

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textcontinue = (TextView) findViewById(R.id.textcontinue);
        textpickdatetime = (TextView) findViewById(R.id.textpickdatetime);
        textpickdatetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datepicker();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.feedrecycler);
        /*List<Person> persons = Arrays.asList(
                new Person("Larry"),
                new Person("Moe"),
                new Person("Curly"));
*/
        //mRecyclerView.setAdapter(new PersonAdapter(this, persons));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        new LoadBookingmethod().execute();

        dialogfilter = new Dialog(Activity_BookingDelivery2.this,R.style.MyAlertDialogStyle);
        dialogfilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        textcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strpickdatetime = textpickdatetime.getText().toString().trim();

                SharedPreferences prefuserdata2 = getSharedPreferences("productadapter", 0);
                shippingid = prefuserdata2.getString("shippingid", "");


                if (strpickdatetime == null || strpickdatetime.trim().length() == 0 || strpickdatetime.trim().equals("null")){
                    Toast.makeText(Activity_BookingDelivery2.this, "Please Select Pick Date and Time", Toast.LENGTH_SHORT).show();
                }else{
                    if (shippingid == null || shippingid.trim().length() == 0 || shippingid.trim().equals("null")){
                        Toast.makeText(Activity_BookingDelivery2.this, "Please Select Delivery Mode", Toast.LENGTH_SHORT).show();
                    }else{

                        Log.e("testing","strpickdatetime = "+strpickdatetime);
                        Log.e("testing","shippingid = "+shippingid);
                        Log.e("testing","strpicklatitude = "+strpicklatitude);
                        Log.e("testing","strpicklongitude = "+strpicklongitude);
                        Log.e("testing","strpickaddress = "+strpickaddress);
                        Log.e("testing","strdroplatitude = "+strdroplatitude);
                        Log.e("testing","strdroplongitude = "+strdroplongitude);
                        Log.e("testing","strdropaddress = "+strdropaddress);

                        new LoadRates().execute();

                    }
                }


            }
        });
    }

    private void continuedialog(String strdistance, String strprice) {


            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity_BookingDelivery2.this.LAYOUT_INFLATER_SERVICE);
            final View convertView = (View) inflater.inflate(R.layout.dialog_bookingconfirmation, null);
            //StartSmartAnimation.startAnimation(convertView.findViewById(R.id.logoutdialoglay), AnimationType.ZoomIn, 500, 0, true, 100);

            dialogfilter.setContentView(convertView);

            // StartSmartAnimation.startAnimation(findViewById(R.id.loginconfirm), AnimationType.Bounce, 800, 0, true, 100);
            dialogfilter.setCanceledOnTouchOutside(false);
            dialogfilter.setCancelable(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialogfilter.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.BOTTOM;
            dialogfilter.getWindow().setAttributes(lp);
            dialogfilter.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            submit_filter=(Button) convertView.findViewById(R.id.submit_filter);
            final String strrs = getResources().getString(R.string.Rs);

            TextView textdistance=(TextView) convertView.findViewById(R.id.textdistance);
            TextView textprice=(TextView) convertView.findViewById(R.id.textprice);

            textdistance.setText(strdistance+" Km");
            textprice.setText(strrs+strprice);

            lineardialog = (LinearLayout) convertView.findViewById(R.id.lineardialog);



            imgdialogfiltercancel = (ImageView) convertView.findViewById(R.id.imgcancel);
            imgdialogfiltercancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    revealShow(convertView, false, dialogfilter);
                }
            });




            dialogfilter.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    revealShow(convertView, true, null);
                }
            });

            dialogfilter.setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                    if (i == KeyEvent.KEYCODE_BACK){

                        revealShow(convertView, false, dialogfilter);
                        return true;
                    }

                    return false;
                }
            });



            /*    LayoutInflater inflater1 = getLayoutInflater();
                final View viewMyLayout = inflater1.inflate(R.layout.dialog_filter_bottom_sheet, null);
                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(getActivity());
                // this is set the view from XML inside AlertDialog
                alert.setView(viewMyLayout);
                dialog1 = alert.create();
                dialog1.show();*/




            submit_filter.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                @Override
                public void onClick(View v) {


                    new StoreBookingDelivery().execute();

                    //bottomSheetDialog.cancel();

                    // dialogfilter.dismiss();
                  //  selecteddata();






                }
            });

            dialogfilter.show();




    }

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {

        final View view = dialogView.findViewById(R.id.lineardialog);

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

            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);

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
    private void datepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                       // textpickdatetime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        strdate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                        strpickdate = year+"-"+ (monthOfYear + 1) + "-" + dayOfMonth;
                        timepicker();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();



    }

    private void timepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        String strcurrentdate = +mYear+ "-" + (mMonth + 1) + "-" + mDay;

                        Log.e("testing","currentdate = "+mYear+ "-" + (mMonth + 1) + "-" + mDay);
                        Log.e("testing","strpickdate = "+strpickdate);

                        int diffInDays2 = 0;
                        SimpleDateFormat dfDate2  = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d3 = null;
                        java.util.Date d4 = null;


                        try {
                            d3 = dfDate2.parse(strpickdate);
                            d4 = dfDate2.parse(strcurrentdate);//Returns 15/10/2012
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        if (d3 == null || d4 == null){

                        }else{
                            diffInDays2 = (int) ((d3.getTime() - d4.getTime())/ (1000 * 60 * 60 * 24));
                            Log.e("testing","diffInDays2 = "+diffInDays2);
                            if (diffInDays2 < 0) {
                               // Toast.makeText(Activity_BookingDelivery2.this, "Please select correct date", Toast.LENGTH_SHORT).show();
                            } else if (diffInDays2 == 0){
                                if (hourOfDay>mHour+1){
                                    // textpickdatetime.setText(hourOfDay + ":" + minute);
                                    strtime = hourOfDay + ":" + minute;
                                    strdatetime = strdate+" : "+strtime;
                                    Log.e("testing","strdatetime = "+strdatetime);
                                    strfinalpickdatetime = strpickdate+" "+strtime;
                                    textpickdatetime.setText(strdatetime);

                                }else{
                                    Toast.makeText(Activity_BookingDelivery2.this, "Time must be After 2 hours from current time.", Toast.LENGTH_SHORT).show();
                                    timepicker();
                                }
                            }else if (diffInDays2 > 0){
                                    // textpickdatetime.setText(hourOfDay + ":" + minute);
                                    strtime = hourOfDay + ":" + minute;
                                    strdatetime = strdate+" : "+strtime;
                                    Log.e("testing","strdatetime = "+strdatetime);
                                    strfinalpickdatetime = strpickdate+" "+strtime;
                                    textpickdatetime.setText(strdatetime);

                            }else{

                            }
                        }


                    }
                }, mHour+2, mMinute, false);
        timePickerDialog.show();
        /*TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                textpickdatetime.setText(hour + ":" + minute);
                strtime = hour + ":" + minute;
                strdatetime = strdate+" : "+strtime;
                Log.e("testing","strdatetime = "+strdatetime);

            }
        };
        Calendar c = Calendar.getInstance();

        final TimePickerDialog timePickerDialog = new TimePickerDialog(Activity_BookingDelivery2.this,timePickerListener,
                c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE)+5,false);
        timePickerDialog.show();*/

    }

    class LoadRates extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_BookingDelivery2.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingRates_from_latitude, strpicklatitude));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingRates_from_longitude, strpicklongitude));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingRates_to_latitude, strdroplatitude));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingRates_to_longitude, strdroplongitude));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingRates_delivery_module_id, shippingid));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.GetBookingRates_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


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
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");



                    String strresponsedata = json.getString("data");
                    JSONObject  jsonobjectstrresponsedata = new JSONObject(strresponsedata);
                    strdistance = jsonobjectstrresponsedata.getString("distance");
                    strprice = jsonobjectstrresponsedata.getString("price");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {

                continuedialog(strdistance, strprice);


            } else if (status.equals("failure")) {
                Toast.makeText(Activity_BookingDelivery2.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{

            }


        }

    }
    class StoreBookingDelivery extends AsyncTask<String, String, String> {
        String responce;
        JSONArray responcearccay;
        String status;
        String strresponse;
        String strdata;
        ProgressDialog mProgress;
        String strcode, strtype, strmessage;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(Activity_BookingDelivery2.this);
            mProgress.setMessage("Fetching data...");
            mProgress.show();
            mProgress.setCancelable(false);

           /* pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading.....");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();*/


        }

        public String doInBackground(String... args) {
            // Create an array

            // Retrieve JSON Objects from the given URL address
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();



            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_latitude, strpicklatitude));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_longitude, strpicklongitude));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_latitude, strdroplatitude));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_longitude, strdroplongitude));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_delivery_module_id, shippingid));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_from_address, strpickaddress));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_to_address, strdropaddress));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_user_id, UserId));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_price, strprice));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_pickup_time, strfinalpickdatetime));
            userpramas.add(new BasicNameValuePair(EndUrl.StoreBookingDelivery_distance, strdistance));

            Log.e("testing", "userpramas = " + userpramas);

            String strurl = EndUrl.StoreBookingDelivery_URL;
            Log.e("testing", "strurl = " + strurl);
            JSONObject json = jsonParser.makeHttpRequest(strurl, "POST", userpramas);


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
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return responce;
            }
        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);
            mProgress.dismiss();
            Log.e("testing","status = "+status);
            Log.e("testing","strresponse = "+strresponse);
            Log.e("testing","strmessage = "+strmessage);

            if (status == null || status.length() == 0){

            }else if (status.equals("success")) {
                dialogfilter.dismiss();
                Toast.makeText(Activity_BookingDelivery2.this, strmessage, Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(Activity_BookingDelivery2.this, Home_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();



            } else if (status.equals("failure")) {
                Toast.makeText(Activity_BookingDelivery2.this, strmessage, Toast.LENGTH_SHORT).show();
                //  alertdialog(strtype, strmessage);
            }else{
                Toast.makeText(Activity_BookingDelivery2.this, strmessage, Toast.LENGTH_SHORT).show();
            }


        }

    }
    class LoadBookingmethod extends AsyncTask<String, String, String>
            //implements RemoveClickListner
    {


        String status;
        String response;
        String strresponse;
        String strcode, strtype, strmessage;

        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Activity_BookingDelivery2.this);
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            feedItemList =new ArrayList<Person>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


              userpramas.add(new BasicNameValuePair(EndUrl.GetBookingMethod_user_id, UserId));


            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetBookingMethod_URL, "GET", userpramas);

            Log.e("testing", "userpramas result = " + userpramas);
            Log.e("testing", "json result = " + json);

            if (json == null) {

            } else {
                Log.e("testing", "jon2222222222222");
                try {


                    status = json.getString("status");
                    strresponse = json.getString("response");
                    JSONObject  jsonobject = new JSONObject(strresponse);
                    strcode = jsonobject.getString("code");
                    strtype = jsonobject.getString("type");
                    strmessage = jsonobject.getString("message");
                    if (status.equals("success")) {

                        status = json.getString("status");
                        strresponse = json.getString("response");
                        String arrayresponse = json.getString("data");
                        Log.e("testing", "adapter value=" + arrayresponse);




                        JSONArray responcearray = new JSONArray(arrayresponse);
                        Log.e("testing", "responcearray value=" + responcearray);

                        for (int i = 0; i < responcearray.length(); i++) {

                            JSONObject post = responcearray.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<String, String>();

                            Person item = new Person();
                            item.setMpid(post.optString("id"));
                            item.setmName(post.optString("name"));
                            item.setMprice(post.optString("description"));


                            feedItemList.add(item);









                        }
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return response;


        }

        @Override
        protected void onPostExecute(String responce) {
            super.onPostExecute(responce);

            //  progressbarloading.setVisibility(View.GONE);
            pDialog.dismiss();
            if (status == null || status.trim().length() == 0 || status.equals("null")){

            }else if (status.equals("success")) {

                adapter = new PersonAdapter(Activity_BookingDelivery2.this, feedItemList);
                mRecyclerView.setAdapter(adapter);






            }
            else {


                adapter = new PersonAdapter(Activity_BookingDelivery2.this, feedItemList);
                mRecyclerView.setAdapter(adapter);






            }



        }

    }



}
