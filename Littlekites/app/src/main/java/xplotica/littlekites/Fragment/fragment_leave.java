package xplotica.littlekites.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import xplotica.littlekites.Activity.Activity_home;
import xplotica.littlekites.End_Urls;
import xplotica.littlekites.R;
import xplotica.littlekites.Utilities.ConnectionDetector;
import xplotica.littlekites.Utilities.JSONParser;

/**
 * Created by G2evolution on 10/21/2017.
 */

public class fragment_leave extends Fragment implements View.OnClickListener{


    JSONParser jsonParser = new JSONParser();

    private DatePickerDialog toDatePickerDialog;
    private DatePickerDialog toDatePickerDialog2;
    public Calendar newDate = Calendar.getInstance();
    public Calendar newDate2 = Calendar.getInstance();
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormatter2;

    EditText Description;
    TextView select_date;
    Button Send_request;
    String description;
    String Result,Message;
    Integer mDay,mMonth,mYear;
    String strdate;
    String strdate2;
    TextView textpickdate, texttodate;


    String date, date2, nowAsString;
    private int myear;
    private int mmonth;
    private int mday;
    String datenow;

    static final int DATE_DIALOG_ID = 999;
    String schoolid,staffid,type,classid,sectionid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_leave, container, false);

        SharedPreferences prefuserdata = getActivity().getSharedPreferences("registerUser", 0);
        schoolid = prefuserdata.getString("schoolid", "");
        staffid = prefuserdata.getString("staffid", "");
        classid = prefuserdata.getString("classid", "");
        sectionid = prefuserdata.getString("sectionid", "");
        type = prefuserdata.getString("type", "");

        Description=(EditText)rootView.findViewById(R.id.description);
        Send_request=(Button)rootView.findViewById(R.id.submit);

        textpickdate = (TextView)rootView.findViewById(R.id.fromdate);
        texttodate = (TextView)rootView.findViewById(R.id.todate);


        Send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                date = textpickdate.getText().toString().trim();
                date2 = texttodate.getText().toString().trim();

//-----------------From date----------------------
                SimpleDateFormat dfDate1  = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date d1 = null;
                java.util.Date d2 = null;
                Calendar cal1 = Calendar.getInstance();
                try {
                    d1 = dfDate1.parse(date);
                    d2 = dfDate1.parse(dfDate1.format(cal1.getTime()));//Returns 15/10/2012
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                int diffInDays1 = (int) ((d1.getTime() - d2.getTime())/ (1000 * 60 * 60 * 24));
                System.out.println(diffInDays1);
                Log.e("testing","date difference  = "+diffInDays1);

//-----------------To Date---------------------------------
                SimpleDateFormat dfDate2  = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date d3 = null;
                java.util.Date d4 = null;
                Calendar cal2 = Calendar.getInstance();
                try {
                    d3 = dfDate2.parse(date2);
                    d4 = dfDate2.parse(date);//Returns 15/10/2012
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }

                int diffInDays2 = (int) ((d3.getTime() - d4.getTime())/ (1000 * 60 * 60 * 24));
                System.out.println(diffInDays2);
                Log.e("testing","date difference  = "+diffInDays2);


                if (diffInDays1 < 0){
                    Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
                }else if (diffInDays2 < 0){
                    Toast.makeText(getActivity(), "Please select correct date", Toast.LENGTH_SHORT).show();
                }else{
                    Submit();
                }

            }
        });

      /*  select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });*/



        Date now = new Date();
        Date alsoNow = Calendar.getInstance().getTime();
        strdate = new SimpleDateFormat("yyyy-MM-dd").format(now);
        Log.e("testing","dateeeeeeeeeeeeeeeeeeeeeeeeeee ="+alsoNow);
        Log.e("testing","strdate ="+strdate);
        textpickdate.setText(strdate);

        textpickdate.setOnClickListener(this);
        textpickdate.setInputType(InputType.TYPE_NULL);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeField();

        Date now2 = new Date();
        Date alsoNow2 = Calendar.getInstance().getTime();
        strdate2 = new SimpleDateFormat("yyyy-MM-dd").format(now2);
        Log.e("testing","dateeeeeeeeeeeeeeeeeeeeeeeeeee ="+alsoNow2);
        Log.e("testing","strdate ="+strdate);
        texttodate.setText(strdate2);

        texttodate.setOnClickListener(this);
        texttodate.setInputType(InputType.TYPE_NULL);
        dateFormatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        setDateTimeFieldto();



        return rootView;
    }


    private void setDateTimeField() {
        textpickdate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();


        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate.set(year, monthOfYear, dayOfMonth);
                textpickdate.setText(dateFormatter.format(newDate.getTime()));
                Log.e("testing", "date = " + date);




            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }

    private void setDateTimeFieldto() {
        texttodate.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();


        toDatePickerDialog2 = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                newDate2.set(year, monthOfYear, dayOfMonth);
                texttodate.setText(dateFormatter2.format(newDate2.getTime()));




            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }


    public void Submit() {
        if (!validate()) {
            onUpdateFailed();
            return;
        }



        savingData();

    }

    public boolean validate(){
        boolean valid =true;


        // topic =Topic.getText().toString();
        description =Description.getText().toString();

       /* if (topic.isEmpty() ) {
            Topic.setError("Please enter Topic");
            valid = false;
        } else {
            Topic.setError(null);
        }



      *//*  if (email.isEmpty()  ||!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Email.setError("enter a valid Email Address");
            valid = false;
        } else {
            Email.setError(null);
        }*//*
*/

        if (description.isEmpty()) {
            Description.setError("Please enter the description");
            valid = false;
        } else {
            Description.setError(null);
        }

        return valid;

    }

    private void onUpdateFailed(){

        Toast.makeText(getActivity(), "Enter Description", Toast.LENGTH_LONG).show();
    }

    private void savingData() {

        date = textpickdate.getText().toString().trim();
        date2 = texttodate.getText().toString().trim();
        description = Description.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, End_Urls.LEAVEMANAGEMENT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("testing", "json response111==" + response);

                        try {

                            JSONObject jsonArray1 = new JSONObject(response);
                            Result = jsonArray1.getString("status");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("testing", "Result == " + Result);

                        Log.e("testing", "json response222==" + response);

                        if (Result.equals("success")) {

                            Toast.makeText(getActivity(), "Successfully Applied", Toast.LENGTH_LONG).show();

                            Log.e("testing", "Message111==" + Result);

                            Intent intent = new Intent(getActivity(), Activity_home.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();

                        } else {

                            Log.e("testing", "json response == " + response);
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("testing", "error response == " + error);
                        Toast.makeText(getActivity(), "No network connection", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                map.put(End_Urls.LEAVEMANAGEMENT_TEACHERID,staffid);
                map.put(End_Urls.LEAVEMANAGEMENT_FROMDATE,date);
                map.put(End_Urls.LEAVEMANAGEMENT_TODATE,date2);
                map.put(End_Urls.LEAVEMANAGEMENT_SCHOOLID,schoolid);
                map.put(End_Urls.LEAVEMANAGEMENT_CLASSID,classid);
                map.put(End_Urls.LEAVEMANAGEMENT_SCETIONID,sectionid);
                map.put(End_Urls.LEAVEMANAGEMENT_LEANEDESC,description);

                Log.e("testing","map"+map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if (v == textpickdate) {

            toDatePickerDialog.show();
            //  showTruitonTimePickerDialog(v);
            // showTruitonDatePickerDialog(v);
        }
        if (v == texttodate) {

            toDatePickerDialog2.show();
            //  showTruitonTimePickerDialog(v);
            // showTruitonDatePickerDialog(v);
        }
    }
}
