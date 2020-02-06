package g2evolution.Boutique.Fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import g2evolution.Boutique.Adapter.Adapter_BookingDeliveryHistory;
import g2evolution.Boutique.EndUrl;
import g2evolution.Boutique.R;
import g2evolution.Boutique.Utility.JSONParser;
import g2evolution.Boutique.entit.Entity_BookingDeliveryHistory;

public class Fragment_BookingDeliveryHistory extends Fragment implements Adapter_BookingDeliveryHistory.OnItemClick{

    String []OrderId =new String[]{"155112KH","155112KH","155112KH","155112KH","155112KH","155112KH","155112KH","155112KH"};
    String []Date =new String[]{"29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30"};

    String []fromlocation = new String[]{"#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
    };

    String []tolocation = new String[]{"#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
            "#43, G2evolution, Marathalli, Bangalore",
    };


    String []DeliveryMode =new String[]{"Normal Delivery","Delivery with Altration","Delivery with Altration","Normal Delivery","Normal Delivery","Normal Delivery","Normal Delivery","Normal Delivery"};
    String []PickupDate =new String[]{"29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30","29-01-2020 11:30"};


    JSONParser jsonParser = new JSONParser();

    private ArrayList<Entity_BookingDeliveryHistory> allItems1 = new ArrayList<Entity_BookingDeliveryHistory>();

    Dialog dialogfilter;
    LinearLayout lineardialog;
    Button submit_filter;
    TextView from_date_text, to_date_text;
    ImageView imgdialogfiltercancel;


    String strfinalfromdata = "";
    String strfinaltodata = "";

    Integer mYear, mMonth, mDay, mWeek;
    String date11 = "";
    String date22 = "";

    String UserId;

    FloatingActionButton fab;

    RecyclerView mRecyclerView;
    Adapter_BookingDeliveryHistory mAdapter;
    private Adapter_BookingDeliveryHistory.OnItemClick mCallback;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_bookingdeliveryhistory, container, false);


        SharedPreferences prefuserdata = getActivity().getSharedPreferences("regId", 0);
        UserId = prefuserdata.getString("UserId", "");

        mRecyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
       // mRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        fab=(FloatingActionButton)rootView.findViewById(R.id.fab);

       // setUpReccyler();

        mCallback = this;


        dialogfilter = new Dialog(getActivity(),R.style.MyAlertDialogStyle);
        dialogfilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filter();



            }
        });

        new LoadBookingList().execute();

        return rootView;
    }
    private void setUpReccyler() {
        allItems1 =new ArrayList<Entity_BookingDeliveryHistory>();

        for(int i=0;i<OrderId.length;i++){
            Entity_BookingDeliveryHistory feedInfo = new Entity_BookingDeliveryHistory();
            feedInfo.setOrderid(OrderId[i]);
            feedInfo.setDate(Date[i]);
            feedInfo.setFromlocation(fromlocation[i]);
            feedInfo.setTolocation(tolocation[i]);
            feedInfo.setDeliverymode(DeliveryMode[i]);
            feedInfo.setPickupdate(PickupDate[i]);
            allItems1.add(feedInfo);
        }
        mAdapter = new Adapter_BookingDeliveryHistory(getActivity(),allItems1, mCallback);
        mRecyclerView.setAdapter(mAdapter);




    }
    private void filter() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        final View convertView = (View) inflater.inflate(R.layout.dialog_filter_bottom_date, null);
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


        lineardialog = (LinearLayout) convertView.findViewById(R.id.lineardialog);

        from_date_text = (TextView) convertView.findViewById(R.id.from_date_text);
        to_date_text = (TextView) convertView.findViewById(R.id.to_date_text);

        RelativeLayout from_layout = (RelativeLayout) convertView.findViewById(R.id.from_layout);
        RelativeLayout to_layout = (RelativeLayout) convertView.findViewById(R.id.to_layout);

        imgdialogfiltercancel = (ImageView) convertView.findViewById(R.id.imgcancel);
        imgdialogfiltercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revealShow(convertView, false, dialogfilter);
            }
        });


        from_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startdatepicker();
            }
        });

        to_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strfromdat = from_date_text.getText().toString();

                Log.e("testing","date11 = "+date11);

                if (strfromdat == null || strfromdat.trim().length() == 0 || strfromdat.trim().length() == 0 || strfromdat.trim().equals("From Date")){
                    Toast.makeText(getActivity(), "Please select From Date", Toast.LENGTH_SHORT).show();
                }else{
                    enddatepicker();
                }

                Log.e("testing","date11 = "+date11);


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



                //bottomSheetDialog.cancel();

                // dialogfilter.dismiss();
                selecteddata();






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

    private void startdatepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mWeek = c.get(Calendar.WEEK_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String strfromdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("testing","strfromdate = "+strfromdate);
                        //  date1 = Fromdate.getText().toString().trim();
                        date11 = strfromdate;
                        date22 = to_date_text.getText().toString().trim();

                        int diffInDays2 = 0;

                        SimpleDateFormat dfDate2  = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d3 = null;
                        java.util.Date d4 = null;
                        Calendar cal2 = Calendar.getInstance();
                        try {
                            d3 = dfDate2.parse(date22);
                            d4 = dfDate2.parse(date11);//Returns 15/10/2012
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        if (d3 == null || d4 == null){
                            // Display Selected date in textbox
                            from_date_text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            from_date_text.setTextColor(getResources().getColor(R.color.black));
                            Log.e("testing", "event_date" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            Log.e("testing", "week of day = " + dayOfWeek);


                        }else{
                            diffInDays2 = (int) ((d3.getTime() - d4.getTime())/ (1000 * 60 * 60 * 24));
                            System.out.println(diffInDays2);
                            Log.e("testing","date difference  = "+diffInDays2);

                            if (diffInDays2 < 0) {
                                Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
                            } else {


                                // Display Selected date in textbox
                                from_date_text.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                from_date_text.setTextColor(getResources().getColor(R.color.black));
                                Log.e("testing", "event_date" + dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                Log.e("testing", "week of day = " + dayOfWeek);

                            }
                        }
                    }
                }, mYear, mMonth, mDay);

        // dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();
    }


    private void enddatepicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mWeek = c.get(Calendar.WEEK_OF_MONTH);
        // Launch Date Picker Dialog
        DatePickerDialog dpd = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {


                        String strtodate =  year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        Log.e("testing","strtodate = "+strtodate);
                        date11 = from_date_text.getText().toString().trim();
                        date22 = strtodate;
                        //   date2 = Todate.getText().toString().trim();

                        int diffInDays2 = 0;

                        SimpleDateFormat dfDate2  = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date d3 = null;
                        java.util.Date d4 = null;
                        Calendar cal2 = Calendar.getInstance();
                        try {
                            d3 = dfDate2.parse(date22);
                            d4 = dfDate2.parse(date11);//Returns 15/10/2012
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }

                        if (d3 == null || d4 == null){




                            // Display Selected date in textbox
                            to_date_text.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            to_date_text.setTextColor(getResources().getColor(R.color.black));
                            Log.e("testing","event_date"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year, monthOfYear, dayOfMonth);
                            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                            Log.e("testing","week of day = "+dayOfWeek);


                        }else{
                            diffInDays2 = (int) ((d3.getTime() - d4.getTime())/ (1000 * 60 * 60 * 24));
                            System.out.println(diffInDays2);
                            Log.e("testing","date difference  = "+diffInDays2);

                            if (diffInDays2 < 0) {
                                Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
                            } else {


                                // Display Selected date in textbox
                                to_date_text.setText( year+ "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                to_date_text.setTextColor(getResources().getColor(R.color.black));

                                //  new TopTrend().execute();



                                Log.e("testing","event_date"+dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                Calendar calendar = Calendar.getInstance();
                                calendar.set(year, monthOfYear, dayOfMonth);
                                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                                Log.e("testing","week of day = "+dayOfWeek);

                            }
                        }
                    }
                }, mYear, mMonth, mDay);

        // dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();

    }

    private void selecteddata() {

        strfinalfromdata = from_date_text.getText().toString();
        strfinaltodata = to_date_text.getText().toString();

        if (strfinalfromdata == null || strfinalfromdata.trim().length() == 0 || strfinalfromdata.equals("null")){
            Toast.makeText(getActivity(), "Please Select From Date", Toast.LENGTH_SHORT).show();
        }else{
            if (strfinaltodata == null || strfinaltodata.trim().length() == 0 || strfinaltodata.equals("null")){
                Toast.makeText(getActivity(), "Please Select To Date", Toast.LENGTH_SHORT).show();
            }else{
                dialogfilter.dismiss();
                // Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                 new LoadBookingList().execute();


            }
        }






    }


    @Override
    public void onClickedItem(int pos, String qty, int status) {

    }

    class LoadBookingList extends AsyncTask<String, String, String>
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            // progressbarloading.setVisibility(View.VISIBLE);
        }

        public String doInBackground(String... args) {

            //  product_details_lists = new ArrayList<Product_list>();

            allItems1 =new ArrayList<Entity_BookingDeliveryHistory>();
            List<NameValuePair> userpramas = new ArrayList<NameValuePair>();


            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingList_user_id, UserId));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingList_from_date, strfinalfromdata));
            userpramas.add(new BasicNameValuePair(EndUrl.GetBookingList_to_date, strfinaltodata));

            JSONObject json = jsonParser.makeHttpRequest(EndUrl.GetBookingList_URL, "GET", userpramas);

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
                        if (responcearray == null){

                        }else{
                            for (int i = 0; i < responcearray.length(); i++) {

                                JSONObject post = responcearray.getJSONObject(i);

                                Entity_BookingDeliveryHistory feedInfo = new Entity_BookingDeliveryHistory();
                                feedInfo.setOrderid(post.getString("order_number"));
                                feedInfo.setDate(post.getString("ordered_date"));
                                feedInfo.setFromlocation(post.getString("from_address"));
                                feedInfo.setTolocation(post.getString("to_address"));
                                feedInfo.setPickupdate(post.getString("pickup_time"));


                                String strresponsedelivery_module = post.getString("delivery_module");
                                JSONObject  jsonobjectdelivery_module = new JSONObject(strresponsedelivery_module);

                                feedInfo.setDeliverymode(jsonobjectdelivery_module.getString("name"));

                                allItems1.add(feedInfo);
                            }
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
                Log.e("testing123", "allItems1===" + allItems1);


                mAdapter = new Adapter_BookingDeliveryHistory(getActivity(),allItems1, mCallback);
                mRecyclerView.setAdapter(mAdapter);




            }
            else {


                mAdapter = new Adapter_BookingDeliveryHistory(getActivity(),allItems1, mCallback);
                mRecyclerView.setAdapter(mAdapter);



            }



        }

    }

}
