package g2evolution.Boutique.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
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

import java.util.Calendar;

import g2evolution.Boutique.R;

public class Activity_BookingDelivery2 extends AppCompatActivity {

    TextView textpickdatetime;
    private int mYear, mMonth, mDay, mHour, mMinute;

    Dialog dialogfilter;
    LinearLayout lineardialog;
    Button submit_filter;
    ImageView imgdialogfiltercancel;

    String strdate, strtime, strdatetime;

    TextView textcontinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_delivery2);

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

        dialogfilter = new Dialog(Activity_BookingDelivery2.this,R.style.MyAlertDialogStyle);
        dialogfilter.requestWindowFeature(Window.FEATURE_NO_TITLE);
        textcontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                continuedialog();

            }
        });
    }

    private void continuedialog() {


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




                        if (hourOfDay>mHour+1){
                           // textpickdatetime.setText(hourOfDay + ":" + minute);
                            strtime = hourOfDay + ":" + minute;
                            strdatetime = strdate+" : "+strtime;
                            Log.e("testing","strdatetime = "+strdatetime);
                            textpickdatetime.setText(strdatetime);

                        }else{
                            Toast.makeText(Activity_BookingDelivery2.this, "Time must be After 2 hours from current time.", Toast.LENGTH_SHORT).show();
                            timepicker();
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
}
