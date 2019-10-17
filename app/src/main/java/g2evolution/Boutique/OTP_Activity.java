package g2evolution.Boutique;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OTP_Activity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.firstPinView)
    PinView firstPinView;
    @BindView(R.id.otp_text)
    TextView otp_text;
    @BindView(R.id.otp_text2)
    TextView otp_text2;
    @BindView(R.id.submit_text)
    TextView submit_text;
    @BindView(R.id.resend_text)
    TextView resend_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// hide statusbar of Android
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_boutotp);

        ButterKnife.bind(this);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "arial.ttf");

        ((TextView) findViewById(R.id.otp_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.otp_text2)).setTypeface(typeface);
        ((TextView) findViewById(R.id.submit_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.resend_text)).setTypeface(typeface);
        ((TextView) findViewById(R.id.firstPinView)).setTypeface(typeface);

        otp_text.setText("Enter the code ");
        otp_text2.setText("from the mobile we just sent you.");


        listeners();
    }
    private void listeners() {

        submit_text.setOnClickListener(this);
        resend_text.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.submit_text:

                Intent intent1=new Intent(OTP_Activity.this,Home_Activity.class);
                startActivity(intent1);
                break;


            case R.id.resend_text:

                break;




            default:


        }

    }
}
