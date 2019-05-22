package g2evolution.GMT.Activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import g2evolution.GMT.R;
import g2evolution.GMT.Utility.ConnectionDetector;

public class Activity_ForgotPassword extends AppCompatActivity {

    TextView textmobileno, textemailid, textsubmit;
    CardView cardviewmobileno, cardviewemail;
    EditText editmobileno, editemailid;

    String strstatus;
    String strmobileno;
    String stremailid;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        context = this;

        textmobileno = (TextView) findViewById(R.id.textmobileno);
        textemailid = (TextView) findViewById(R.id.textemailid);
        textsubmit = (TextView) findViewById(R.id.textsubmit);
        editmobileno = (EditText) findViewById(R.id.editmobileno);
        editemailid = (EditText) findViewById(R.id.editemailid);

        textmobileno.setTextColor(Color.BLACK);
        textemailid.setTextColor(Color.WHITE);
        textmobileno.setBackgroundColor(Color.parseColor("#BDBDBD"));
        textemailid.setBackgroundColor(Color.parseColor("#DC2399"));
        textsubmit.setText("Send OTP");

        cardviewmobileno = (CardView) findViewById(R.id.cardviewmobileno);
        cardviewemail = (CardView) findViewById(R.id.cardviewemail);

        strstatus = "1";

        textmobileno.setTextColor(Color.WHITE);
        textemailid.setTextColor(Color.BLACK);
        textmobileno.setBackgroundColor(Color.parseColor("#66AD43"));
        textemailid.setBackgroundColor(Color.parseColor("#BDBDBD"));
        cardviewmobileno.setVisibility(View.VISIBLE);
        cardviewemail.setVisibility(View.GONE);

        textmobileno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strstatus = "1";
                textmobileno.setTextColor(Color.WHITE);
                textemailid.setTextColor(Color.BLACK);
                textmobileno.setBackgroundColor(Color.parseColor("#66AD43"));
                textemailid.setBackgroundColor(Color.parseColor("#BDBDBD"));
                textsubmit.setText("Send OTP");
                cardviewmobileno.setVisibility(View.VISIBLE);
                cardviewemail.setVisibility(View.GONE);
            }
        });

        textemailid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strstatus = "2";
                textmobileno.setTextColor(Color.BLACK);
                textemailid.setTextColor(Color.WHITE);
                textmobileno.setBackgroundColor(Color.parseColor("#BDBDBD"));
                textemailid.setBackgroundColor(Color.parseColor("#66AD43"));
                textsubmit.setText("SUBMIT");
                cardviewmobileno.setVisibility(View.GONE);
                cardviewemail.setVisibility(View.VISIBLE);
            }
        });
        textsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (strstatus.equals("1")){
                    strmobileno = editmobileno.getText().toString();
                    if (strmobileno == null || strmobileno.length() != 10){
                        Toast.makeText(context, "Please enter proper Mobile number", Toast.LENGTH_SHORT).show();
                    }else{
                        ConnectionDetector cd = new ConnectionDetector(context);
                        if (cd.isConnectingToInternet()) {
                           // new SendMobileno().execute();
                        } else {
                            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                        }
                    }

                }else if (strstatus.equals("2")){
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+||[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+\\.+[a-z]+";
                    stremailid = editemailid.getText().toString();
                    if (stremailid.matches(emailPattern) && stremailid.length() > 0){
                        ConnectionDetector cd = new ConnectionDetector(context);
                        if (cd.isConnectingToInternet()) {
                          //  new SendEmailId().execute();
                        } else {
                            Toast.makeText(context, "Internet Connection Not Available Enable Internet And Try Again", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(context, "Enter Email ID", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }

            }
        });

    }
}
